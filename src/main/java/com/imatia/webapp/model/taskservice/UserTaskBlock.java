package com.imatia.webapp.model.taskservice;

import java.util.List;

import com.imatia.webapp.model.task.Task;


public class UserTaskBlock {

	
	private List<Task> tasks;
	private boolean existMoreTasks;
	
	  public UserTaskBlock(List<Task> tasks, boolean existMoreTasks) {
	        
	        this.tasks = tasks;
	        this.existMoreTasks = existMoreTasks;

	    }
	    
	    public List<Task> getTasks() {
	        return tasks;
	    }
	    
	    public boolean getExistMoreTasks() {
	        return existMoreTasks;
	    }

}
