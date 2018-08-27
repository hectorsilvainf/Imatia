package com.imatia.webapp.model.task;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.imatia.webapp.model.userprofile.UserProfile;





@Entity
public class Task {

	private Long taskId;
	private String taskName;
	private Boolean done;
	private UserProfile userTask;
	

	public Task() {
	}

	public Task(UserProfile userTask,String taskName) {
		this.userTask = userTask;
		this.taskName = taskName;
		this.done = false;
	}
	
	@Column(name = "taskId")
	@SequenceGenerator( 
	name = "TaskIdGenerator", 
	sequenceName = "TaskSeq")
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "TaskIdGenerator")
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}
	
	@ManyToOne(optional=false)
    @JoinColumn(name="usrId")
	public UserProfile getUserTask() {
		return userTask;
	}

	public void setUserTask(UserProfile userTask) {
		this.userTask = userTask;
	}
	
	
	
	
	

}
