package com.stackroute.newsapp.exceptions;

/*
 * Date:01.26.2019
 * Creating this custom exception class for handling exception
 * when we try to add one news which is already exists in the database
 */
public class NewsContentAlreadyExistsException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3850108169013656655L;
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public NewsContentAlreadyExistsException(final String message) {
		super(message);
		this.message=message;
	}
	
	@Override
	public String toString() {
		return "This movie already exists" +message;
	}
}
