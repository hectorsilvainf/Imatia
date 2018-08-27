package com.imatia.webapp.test.model.userservice;

import static com.imatia.webapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static com.imatia.webapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.imatia.webapp.model.task.Task;
import com.imatia.webapp.model.taskservice.IncorrectPasswordException;
import com.imatia.webapp.model.taskservice.UserTaskBlock;
import com.imatia.webapp.model.taskservice.UserTaskService;
import com.imatia.webapp.model.userprofile.UserProfile;
import com.imatia.webapp.model.util.exceptions.DuplicateInstanceException;
import com.imatia.webapp.model.util.exceptions.InstanceNotFoundException;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class UserTaskServiceTest {
	private final long NON_EXISTENT_USER_ID = -1;
	private final String NON_EXISTENT_USER = "no";
	
   

    @Autowired
    private UserTaskService userTaskService;
    
    private UserProfile createValidUserProfile(String userName){
    	return new UserProfile(userName,"hector");
    }
    
    private Task createValidTask(UserProfile userProfile, String taskName){
    	return new Task(userProfile,taskName);
    }
    @Test
    public void testCreateUserAndFind()
        throws InstanceNotFoundException, DuplicateInstanceException{

        UserProfile user = userTaskService.createUserProfile(createValidUserProfile("hector"));
        UserProfile user2 = userTaskService.findUserProfileById(user.getUsrId());
        assertEquals(user, user2);

    }
    
    @Test(expected = DuplicateInstanceException.class)
    public void testCreateUserWithUserNameDuplicated() throws DuplicateInstanceException{
    	
    	userTaskService.createUserProfile(createValidUserProfile("hector"));
    	userTaskService.createUserProfile(createValidUserProfile("hector"));
    }
    
    @Test(expected = InstanceNotFoundException.class)
    public void testFindNonExistUser() throws InstanceNotFoundException{
    	userTaskService.findUserProfileById(NON_EXISTENT_USER_ID);
    }
    
    @Test
    public void loginUser() throws DuplicateInstanceException, 
    		InstanceNotFoundException, IncorrectPasswordException{
    	
    	UserProfile user = userTaskService.createUserProfile(createValidUserProfile("hector"));
    	UserProfile user2 = userTaskService.login(user.getUserName(), user.getEncryptedPassword());
    	assertEquals(user,user2);
    }
    
    @Test(expected = IncorrectPasswordException.class)
    public void loginUserIncorrectPassword() throws DuplicateInstanceException,
    		InstanceNotFoundException, IncorrectPasswordException{
    	
    	String incorrectPassword = "incorrect";
    	UserProfile user = userTaskService.createUserProfile(createValidUserProfile("hector"));
    	userTaskService.login(user.getUserName(), incorrectPassword);
    }
    @Test(expected = InstanceNotFoundException.class)
    public void loginNonExistUser() throws InstanceNotFoundException, IncorrectPasswordException{
    	userTaskService.login(NON_EXISTENT_USER, "INCORRECT");
    }
    @Test
    public void testAddTask() throws InstanceNotFoundException, DuplicateInstanceException{

    	UserProfile user = userTaskService.createUserProfile(createValidUserProfile("hector"));
    	Task insertedtask = userTaskService.createTask(user.getUsrId(), new Task(user,"new task"));
    	Task findedtask = userTaskService.findTaskById(insertedtask.getTaskId());
    	assertEquals(insertedtask,findedtask);
    	
    }
    private Task createTask(UserProfile user, String taskName) throws InstanceNotFoundException{
    	return userTaskService.createTask(user.getUsrId(), createValidTask(user,taskName));
    }
    @Test
    public void testFinAllTasks() throws InstanceNotFoundException, DuplicateInstanceException{
    	UserProfile user1 = userTaskService.createUserProfile(createValidUserProfile("hector"));
    	UserProfile user2 = userTaskService.createUserProfile(createValidUserProfile("hector2"));
    	
    	int numberoftasks = 11;
    	
    	/*Create tasks for all users that should be found*/
    	List<Task> listTasks = new ArrayList<Task>();
    	for(int i = 0; i<numberoftasks;i++){
    		listTasks.add(createTask(user1,"tarea"));
    	}
    	listTasks.add(createTask(user2,"tarea"));
    	numberoftasks++;
    	
    	UserTaskBlock usertaskblock;
    	int count = 10;
    	int startIndex = 0;
    	
    	short resultIndex = 0;
    	
    	do {

            usertaskblock = userTaskService.findAllTasks(startIndex, count);
            assertTrue(usertaskblock.getTasks().size() <= count);
            for (Task task : usertaskblock.getTasks()) {
                assertTrue(task == listTasks.get(resultIndex++));
            }
            startIndex += count;

        } while (usertaskblock.getExistMoreTasks());
    	
        assertTrue(numberoftasks == (startIndex - count
                + usertaskblock.getTasks().size()));
    	
    }
    @Test
    public void testFindUserTasks() throws DuplicateInstanceException, InstanceNotFoundException{
    	UserProfile user1 = userTaskService.createUserProfile(createValidUserProfile("hector"));
    	UserProfile user2 = userTaskService.createUserProfile(createValidUserProfile("hector2"));
    	
    	int numberoftasks = 11;
    	
    	/*Create tasks for user1 that should be found*/
    	List<Task> listTasks = new ArrayList<Task>();
    	for(int i = 0; i<numberoftasks;i++){
    		listTasks.add(createTask(user1,"tarea"));
    	}
    	/*Create task that not should be found*/
    	createValidTask(user2,"tarea");
    	
    	UserTaskBlock usertaskblock;
    	int count = 10;
    	int startIndex = 0;
    	
    	short resultIndex = 0;
    	
    	do {

            usertaskblock = userTaskService.findTasksByUserId(user1.getUsrId(), startIndex, count);
            assertTrue(usertaskblock.getTasks().size() <= count);
            for (Task task : usertaskblock.getTasks()) {
                assertTrue(task == listTasks.get(resultIndex++));
            }
            startIndex += count;

        } while (usertaskblock.getExistMoreTasks());
    	
    	
        assertTrue(numberoftasks == (startIndex - count
                + usertaskblock.getTasks().size()));
    	
    	
    }
    
    @Test
    public void TestMarkAsDone() throws DuplicateInstanceException, InstanceNotFoundException{
    	UserProfile user1 = userTaskService.createUserProfile(createValidUserProfile("hector"));
    	Task task = createTask(user1,"tarea");
    	
    	userTaskService.markTaskAsDone(task.getTaskId(), true);
    	
    	assertTrue(task.getDone());
    	
    }
    @Test(expected = InstanceNotFoundException.class)
    public void TestRemoveTask() throws InstanceNotFoundException, DuplicateInstanceException{
    	UserProfile user1 = userTaskService.createUserProfile(createValidUserProfile("hector"));
    	Task task = createTask(user1,"tarea");
    	
    	userTaskService.removeTask(task.getTaskId());
    	
    	userTaskService.findTaskById(task.getTaskId());
    	
    	
    }

}
