package com.imatia.webapp.web.pages.task;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.imatia.webapp.model.task.Task;
import com.imatia.webapp.model.taskservice.UserTaskService;
import com.imatia.webapp.model.userprofile.UserProfile;
import com.imatia.webapp.model.util.exceptions.InstanceNotFoundException;
import com.imatia.webapp.web.services.AuthenticationPolicy;
import com.imatia.webapp.web.services.AuthenticationPolicyType;
import com.imatia.webapp.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class CreateTask {

	@Property
	private String taskName;
	@Property
	@SessionState(create=false)
	private UserSession userSession;
	
	@Property
	private boolean created = false;
	@Component
	private Form createTaskForm;
	
	@Component(id="taskName")
	private TextField taskNameField;
	
	@Inject
    private Messages messages;
    @Inject
    private UserTaskService userTaskService;
    
    void onValidateFromCreateTaskForm() throws InstanceNotFoundException{
    	if(!createTaskForm.isValid()){
    		return;
    	}
    
			UserProfile userProfile = userTaskService.findUserProfileById(userSession.getUserProfileId());
			userTaskService.createTask(userSession.getUserProfileId(), new Task(userProfile,taskName));
    	
    	
    }
    Object onSuccess() {
    	created = true;
    	return CreateTask.class;

    }
}