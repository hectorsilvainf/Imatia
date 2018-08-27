/**
 * 
 */
package com.imatia.webapp.web.pages.user;



import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.imatia.webapp.web.pages.Index;
import com.imatia.webapp.model.taskservice.UserTaskService;
import com.imatia.webapp.model.userprofile.UserProfile;
import com.imatia.webapp.model.util.exceptions.DuplicateInstanceException;
import com.imatia.webapp.web.services.AuthenticationPolicy;
import com.imatia.webapp.web.services.AuthenticationPolicyType;
import com.imatia.webapp.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class RegisterUser {

	@Property
	private String userName;
	@Property
	private String enPassword;
	@Property
    private String retypePassword;
	@Property
	@SessionState(create=false)
	private UserSession userSession;
   
    
    @Component
    private Form registrationForm;
    
    @Component(id = "userName")
    private TextField userNameField;

    @Component(id = "enPassword")
    private PasswordField passwordField;
    @Inject
    private Messages messages;
    @Inject
    private UserTaskService userTaskService;
   
    private Long userId;
   
    void onValidateFromRegistrationForm() {

        if (!registrationForm.isValid()) {
            return;
        }

        if (!enPassword.equals(retypePassword)) {
            registrationForm.recordError(passwordField, messages
                    .get("error-passwordsDontMatch"));
        } else {

            try {
            	
                UserProfile userProfile = userTaskService.createUserProfile(
                		new UserProfile(userName,enPassword));
                
                userId = userProfile.getUsrId();
            } catch (DuplicateInstanceException e) {
                registrationForm.recordError(userNameField, messages
                        .get("error-loginNameAlreadyExists"));
            }

        }

    }

    Object onSuccess() {
    	/*Cookie creation of the user for this session and redirect to main page*/
        userSession = new UserSession();
        userSession.setUserProfileId(userId);
        userSession.setUserName(userName);
        return Index.class;

    }

    
}