package com.imatia.webapp.web.pages.task;


import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.imatia.webapp.model.task.Task;
import com.imatia.webapp.model.taskservice.UserTaskBlock;
import com.imatia.webapp.model.taskservice.UserTaskService;
import com.imatia.webapp.model.util.exceptions.InstanceNotFoundException;
import com.imatia.webapp.web.services.AuthenticationPolicy;
import com.imatia.webapp.web.services.AuthenticationPolicyType;
import com.imatia.webapp.web.util.UserSession;


@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class FindUserTasks {
	
	
	private final static int TASKS_PER_PAGE = 10;
	
	private int startIndex = 0;
	private UserTaskBlock userTaskBlock;
	private Task task;
	
	
	
	@Property
	private List<Task> tasks;
	
	@Property
	@SessionState(create=false)
	private UserSession userSession;
	

	
	@Inject
	private UserTaskService userTaskService;

	
	
	
	public Task getTask(){
		return task;
	}
	
	public void setTask(Task task){
		this.task = task;
	}
	
	
	public Object[] getPreviousLinkContext() {
		
		if (startIndex-TASKS_PER_PAGE >= 0) {
			return new Object[] {startIndex-TASKS_PER_PAGE};
		} else {
			return null;
		}
		
	}
	public Object[] getNextLinkContext() {
		
		if (userTaskBlock.getExistMoreTasks()) {
			return new Object[] { startIndex+TASKS_PER_PAGE};
		} else {
			return null;
		}
		
	}
	Object[] onPassivate() {
		return new Object[] {startIndex};
	}
	void onActivate(int startIndex){

			
		this.startIndex = startIndex;
		userTaskBlock = userTaskService.findTasksByUserId(userSession.getUserProfileId(),startIndex, TASKS_PER_PAGE);
		tasks = new ArrayList<Task>();
		for (Task taskInBlock : userTaskBlock.getTasks()){
			tasks.add(taskInBlock);
		}
		
	}
	

	

	Object onActionFromUpdate(Long taskId,boolean done){
		try {
			userTaskService.markTaskAsDone(taskId, !done);
		} catch (InstanceNotFoundException e) {
			
		}
		return FindUserTasks.class;
		
	}
	Object onActionFromDelete(Long taskId){
		try{
			userTaskService.removeTask(taskId);
		}catch(InstanceNotFoundException e){
			return null;
		}
		return FindUserTasks.class;
	}

}