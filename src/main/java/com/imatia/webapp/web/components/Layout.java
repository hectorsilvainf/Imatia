/**
 * 
 */
package com.imatia.webapp.web.components;

import java.util.Locale;

import javax.inject.Inject;


import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.PersistentLocale;

import com.imatia.webapp.web.pages.Index;
import com.imatia.webapp.web.pages.task.CreateTask;
import com.imatia.webapp.web.pages.user.LoginUser;

import com.imatia.webapp.web.services.AuthenticationPolicy;
import com.imatia.webapp.web.services.AuthenticationPolicyType;
import com.imatia.webapp.web.util.CookiesManager;
import com.imatia.webapp.web.util.UserSession;

@Import(library = {"tapestry5/bootstrap/js/collapse.js", "tapestry5/bootstrap/js/dropdown.js"},
stylesheet="tapestry5/bootstrap/css/bootstrap-theme.css")
public class Layout {

	@Property
	@Parameter(required = true, defaultPrefix = "message")
	private String title;
	@Property
    private String language;
	@Parameter(defaultPrefix = "literal")
    private Boolean showTitleInBody;
	
	@Property
	@SessionState(create = false)
	private UserSession userSession;
	
	@Inject
	private Cookies cookies;
	@Inject
    private PersistentLocale persistentLocale;
	public boolean getShowTitleInBody() {
    	
    	if (showTitleInBody == null) {
    		return true;
    	} else {
    		return showTitleInBody;
    	}
    	
    }
	void onActionFromLanguage(){
		persistentLocale.set(new Locale("en"));
	}
	    
	void onActionFromLanguage2(){
		persistentLocale.set(new Locale("es"));
	}
	@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
    Object onActionFromLogout() {
        userSession = null;
        CookiesManager.removeCookies(cookies);
        return Index.class;
    }
	@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
	Object onActionFromAddTask(){
		if(userSession!=null)
			return CreateTask.class;
		else
			return LoginUser.class;
	}
	Object onSuccess(){
		
		persistentLocale.set(new Locale(language));
		
		return Index.class;
	}
	
}