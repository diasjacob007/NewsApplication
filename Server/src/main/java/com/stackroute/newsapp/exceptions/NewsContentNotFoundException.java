package com.stackroute.newsapp.exceptions;

/*
 * 01.26.2019
 * Custom exception calls for handling exception if there is no such news content exists
 * 
 */
public class NewsContentNotFoundException extends Exception {

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

	public NewsContentNotFoundException(final String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String toString() {
		return "There is no such movie exists " + message;
	}
}
