package com.imatia.webapp.model.taskservice;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imatia.webapp.model.task.Task;
import com.imatia.webapp.model.task.TaskDao;
import com.imatia.webapp.model.userprofile.UserProfile;
import com.imatia.webapp.model.userprofile.UserProfileDao;
import com.imatia.webapp.model.util.exceptions.DuplicateInstanceException;
import com.imatia.webapp.model.util.exceptions.InstanceNotFoundException;



@Service("userTaskService")
@Transactional
public class UserTaskServiceImpl implements UserTaskService {
	
	@Autowired
	private TaskDao taskDao;
	
	@Autowired
	private UserProfileDao userProfileDao;
	
	public UserProfile createUserProfile(UserProfile userProfile) 
			throws DuplicateInstanceException{
		try{
			userProfileDao.findByUserName(userProfile.getUserName());
			throw new DuplicateInstanceException(userProfile.getUserName(),
                    UserProfile.class.getName());
		}catch (InstanceNotFoundException e) {
			
			userProfileDao.save(userProfile);
			return userProfile;
		}
	}
	
	@Transactional(readOnly=true)
	public UserProfile findUserProfileById(Long usrId) throws InstanceNotFoundException{
		
		return userProfileDao.find(usrId);
		
	}
	@Transactional(readOnly=true)
	public UserProfile login(String userName, String password)
    		throws InstanceNotFoundException,IncorrectPasswordException{
		
			UserProfile userProfile = userProfileDao.findByUserName(userName);
			if(!password.equals(userProfile.getEncryptedPassword())){
				throw new IncorrectPasswordException(userName);
			}
			return userProfile;
		
	}
	@Transactional(readOnly = true)
	public UserTaskBlock findTasksByUserId(Long usrId, int startIndex, int count){
		/*
		 * Find count+1 tasks to determine if there exist more tasks above
		 * the specified range.
		 */
		List<Task> tasks = taskDao.findTasksByUserId(usrId, startIndex, count+1);

		boolean existMoreTasks = tasks.size() == (count + 1);

		/*
		 * Remove the last task from the returned list if there exist more
		 * tasks above the specified range.
		 */
		if (existMoreTasks) {
			tasks.remove(tasks.size() - 1);
		}

		/* Return AccountBlock. */
		return new UserTaskBlock(tasks, existMoreTasks);
	}
	@Transactional(readOnly = true)
	public UserTaskBlock findAllTasks(int startIndex,int count){
		List<Task> tasks = taskDao.findAllTasks(startIndex, count+1);
		
		boolean existMoreTasks = tasks.size() == (count + 1);
		
		if(existMoreTasks){
			tasks.remove(tasks.size()-1);
		}
		
		return new UserTaskBlock(tasks,existMoreTasks);
	}
	
	public Task createTask(Long usrId,Task task) throws InstanceNotFoundException{
		
		UserProfile user = userProfileDao.find(usrId);
		Task newTask = new Task(user,task.getTaskName());
		taskDao.save(newTask);
		return newTask;
		
	}
	@Transactional(readOnly = true)
	public Task findTaskById(Long taskId) throws InstanceNotFoundException{
		return taskDao.find(taskId);
	}
	
	public void markTaskAsDone(Long taskId,Boolean done) throws InstanceNotFoundException{
		
		Task task = taskDao.find(taskId);
		task.setDone(done);
		
	}
	
	public void removeTask(Long taskId) throws InstanceNotFoundException{
		taskDao.remove(taskId);
	}
}
