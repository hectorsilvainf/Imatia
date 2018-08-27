package com.imatia.webapp.web.util;

import org.apache.tapestry5.services.Cookies;

public class CookiesManager {

	private static final String LOGIN_NAME_COOKIE = "userName";
	private static final String PASSWORD_COOKIE = "enPassword";
    private static final int REMEMBER_MY_PASSWORD_AGE =
        30 * 24 * 3600; // 30 days in seconds

	public static void leaveCookies(Cookies cookies, String userName,
			String enPassword) {
		
		cookies.getBuilder(LOGIN_NAME_COOKIE, userName).
			setMaxAge(REMEMBER_MY_PASSWORD_AGE).write();
		cookies.getBuilder(PASSWORD_COOKIE, enPassword).
			setMaxAge(REMEMBER_MY_PASSWORD_AGE).write();

	}

	public static void removeCookies(Cookies cookies) {
		cookies.removeCookieValue(LOGIN_NAME_COOKIE);
		cookies.removeCookieValue(PASSWORD_COOKIE);
	}

	public static String getUserName(Cookies cookies) {
		return cookies.readCookieValue(LOGIN_NAME_COOKIE);
	}

	public static String getEnPassword(Cookies cookies) {
		return cookies.readCookieValue(PASSWORD_COOKIE);
	}

}
