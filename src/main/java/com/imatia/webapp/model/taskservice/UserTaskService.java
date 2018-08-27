package com.imatia.webapp.model.taskservice;

import com.imatia.webapp.model.task.Task;
import com.imatia.webapp.model.userprofile.UserProfile;
import com.imatia.webapp.model.util.exceptions.DuplicateInstanceException;
import com.imatia.webapp.model.util.exceptions.InstanceNotFoundException;

public interface UserTaskService {
	/**
	 * Returns a DuplicateInstanceException if the userName is being used
	 * if not an userProfile is created.
	 * 
	 * @param userProfile 
	 * @return userProfile created
	 * @throws DuplicateInstanceException
	 */
	public UserProfile createUserProfile(UserProfile userProfile) throws DuplicateInstanceException;
	
	public UserProfile findUserProfileById(Long usrId) throws InstanceNotFoundException;
	
	 public UserProfile login(String userName, String password)
	    		throws InstanceNotFoundException,IncorrectPasswordException;
    /**
     * Returns a block of tasks. If has
     * no tasks, an empty list is returned.
     *
     * @param startIndex the index (starting from 0) of the first task to
     *        return
     * @param count the maximum number of tasks to return
     * @return the block of tasks
     */
    public UserTaskBlock findTasksByUserId(Long usrId, int startIndex, int count);
    public UserTaskBlock findAllTasks(int startIndex, int count);
    
    /**
     * Create a new task.  If the usrId does no exist, InstanceNotFounException is
     * returned
     * 
     * @param usrId owner of the task
     * @param task task which want to create
     * @return
     * @throws InstanceNotFoundException
     */

    public Task createTask(Long usrId,Task task) throws InstanceNotFoundException;
    
    public Task findTaskById(Long taskId) throws InstanceNotFoundException;
    
    
    public void markTaskAsDone(Long taskId,Boolean done) throws InstanceNotFoundException;
    
    public void removeTask(Long taskId) throws InstanceNotFoundException;
   
    
}
