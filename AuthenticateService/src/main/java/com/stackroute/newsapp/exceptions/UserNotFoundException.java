package com.stackroute.newsapp.exceptions;

public class UserNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserNotFoundException(final String message) {
		super(message);
		this.message=message;
	}

	@Override
	public String toString() {
		return "There is no such user exists " + message;
	}
}
