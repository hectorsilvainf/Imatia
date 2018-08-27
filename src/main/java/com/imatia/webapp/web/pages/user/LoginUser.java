
package com.imatia.webapp.web.pages.user;



import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import com.imatia.webapp.model.taskservice.IncorrectPasswordException;
import com.imatia.webapp.model.taskservice.UserTaskService;
import com.imatia.webapp.model.userprofile.UserProfile;
import com.imatia.webapp.model.util.exceptions.InstanceNotFoundException;
import com.imatia.webapp.web.pages.Index;
import com.imatia.webapp.web.services.AuthenticationPolicy;
import com.imatia.webapp.web.services.AuthenticationPolicyType;
import com.imatia.webapp.web.util.CookiesManager;
import com.imatia.webapp.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class LoginUser {

	@Property
	private String userName;
	@Property
	private String enPassword;
	@Property
    private boolean rememberMyPassword;
	@Property
	@SessionState(create=false)
	private UserSession userSession;
	
	@Component
	private Form loginForm;
	
	@Inject
	private Cookies cookies;

	@Inject
    private Messages messages;
    @Inject
    private UserTaskService userTaskService;
    
    private UserProfile userProfile = null;
    
    void onValidateFromLoginForm(){
    	
    	if(!loginForm.isValid()){
    		return;
    	}
    	try{
    		userProfile = userTaskService.login(userName, enPassword);
    	}catch (InstanceNotFoundException e){
    		loginForm.recordError(messages.get("error-loginNameNotExists"));
    		
    	}catch (IncorrectPasswordException e){
    		loginForm.recordError(messages.get("error-invalidPassword"));
    		
    	}
    }
    
    Object onSuccess(){
    	/*Cookie creation of the user for this session and redirect to main page*/
        userSession = new UserSession();
        
        userSession.setUserProfileId(userProfile.getUsrId());
        userSession.setUserName(userProfile.getUserName());
        if (rememberMyPassword) {
            CookiesManager.leaveCookies(cookies, userName, userProfile
                    .getEncryptedPassword());
        }
        return Index.class;
    }
	
}