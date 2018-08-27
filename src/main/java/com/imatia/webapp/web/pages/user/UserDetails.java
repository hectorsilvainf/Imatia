
package com.imatia.webapp.web.pages.user;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.imatia.webapp.model.taskservice.UserTaskService;
import com.imatia.webapp.model.userprofile.UserProfile;
import com.imatia.webapp.model.util.exceptions.InstanceNotFoundException;
import com.imatia.webapp.web.services.AuthenticationPolicy;
import com.imatia.webapp.web.services.AuthenticationPolicyType;
import com.imatia.webapp.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class UserDetails {

	private UserProfile userProfile;
	
	@Property
	@SessionState(create=false)
	private UserSession userSession;
	public UserProfile getUserProfile(){
		return userProfile;
	}
	
	
	@Inject
	private UserTaskService userTaskService;
	
	void onActivate(){
		
		try {
			userProfile = userTaskService.findUserProfileById(userSession.getUserProfileId());
		} catch (InstanceNotFoundException e) {
		}
	}
	
}