package com.imatia.webapp.web.services;

import java.io.IOException;

import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;

import com.imatia.webapp.model.taskservice.IncorrectPasswordException;
import com.imatia.webapp.model.taskservice.UserTaskService;
import com.imatia.webapp.model.userprofile.UserProfile;
import com.imatia.webapp.model.util.exceptions.InstanceNotFoundException;
import com.imatia.webapp.web.util.CookiesManager;
import com.imatia.webapp.web.util.UserSession;


public class SessionFilter implements RequestFilter {

	
	private ApplicationStateManager applicationStateManager;
	private Cookies cookies;
	private UserTaskService userService;

	public SessionFilter(ApplicationStateManager applicationStateManager,
			Cookies cookies, UserTaskService userService) {

		this.applicationStateManager = applicationStateManager;
		this.cookies = cookies;
		this.userService = userService;

	}

	public boolean service(Request request, Response response,
			RequestHandler handler) throws IOException {

		if (!applicationStateManager.exists(UserSession.class)) {

			String userName = CookiesManager.getUserName(cookies);
			if (userName != null) {

				String enPassword = CookiesManager
						.getEnPassword(cookies);
				if (enPassword != null) {

					try {

						UserProfile userProfile = userService.login(userName,
								enPassword);
						UserSession userSession = new UserSession();
						userSession.setUserProfileId(userProfile
								.getUsrId());
						userSession.setUserName(userProfile.getUserName());
						applicationStateManager.set(UserSession.class,
								userSession);

					} catch (InstanceNotFoundException e) {
						CookiesManager.removeCookies(cookies);
					} catch (IncorrectPasswordException e) {
						CookiesManager.removeCookies(cookies);
					}

				}

			}

		}

		handler.service(request, response);

		return true;
	}
}
