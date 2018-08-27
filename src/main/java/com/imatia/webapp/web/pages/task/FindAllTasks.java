package com.imatia.webapp.web.pages.task;


import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.imatia.webapp.model.task.Task;
import com.imatia.webapp.model.taskservice.UserTaskBlock;
import com.imatia.webapp.model.taskservice.UserTaskService;
import com.imatia.webapp.web.services.AuthenticationPolicy;
import com.imatia.webapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
public class FindAllTasks {
	private final static int TASKS_PER_PAGE = 10;
	
	private int startIndex = 0;
	private UserTaskBlock userTaskBlock;
	private Task task;

	@Inject
	private UserTaskService userTaskService;
	
	
	public List<Task> getTasks() {
		return userTaskBlock.getTasks();
	}
	
	public Task getTask() {
		return task;
	}
	
	public void setTask(Task task) {
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
	void onActivate(int startIndex) {
		
		this.startIndex = startIndex;
		userTaskBlock = userTaskService.findAllTasks(startIndex, TASKS_PER_PAGE);
	}

}