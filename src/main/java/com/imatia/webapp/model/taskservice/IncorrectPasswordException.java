package com.imatia.webapp.model.taskservice;

@SuppressWarnings("serial")
public class IncorrectPasswordException extends Exception {
	
	private String userName;

    public IncorrectPasswordException(String userName) {
        super("Incorrect password exception => userName = " + userName);
        this.userName = userName;
    }

    public String getLoginName() {
        return userName;
    }
}
