
package com.imatia.webapp.web.pages.task;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.imatia.webapp.model.task.Task;
import com.imatia.webapp.model.taskservice.UserTaskService;
import com.imatia.webapp.model.userprofile.UserProfile;
import com.imatia.webapp.model.util.exceptions.InstanceNotFoundException;

public class TaskDetails {
	private Long taskId;
	private Task task;
	private UserProfile userProfile;
	
	@Inject
	private UserTaskService userTaskService;
	
	public Task getTask(){
		return task;
	}
	
	public void setTask(Long taskId){
		this.taskId = taskId;
	}
	public UserProfile getUserProfile(){
		return userProfile; 
	}
	void onActivate(Long taskId){
		this.taskId = taskId;
		
		try{
			task = userTaskService.findTaskById(taskId);
			userProfile = userTaskService.findUserProfileById(task.getUserTask().getUsrId());
		}catch(InstanceNotFoundException e){
			
		}
	}
	
	Long onPassivate(){
		return taskId;
	}
}