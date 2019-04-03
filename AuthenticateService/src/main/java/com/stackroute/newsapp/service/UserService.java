/**
 * 
 */
package com.stackroute.newsapp.service;

import com.stackroute.newsapp.exceptions.UserAlreadyExistsException;
import com.stackroute.newsapp.exceptions.UserNotFoundException;
import com.stackroute.newsapp.model.User;

/**
 * @author ubuntu
 *
 */
public interface UserService {

	/*
	 * Method definition for saving a new User while Registration process happening
	 * 01.26.2019
	 */
	boolean saveUser(User user) throws UserAlreadyExistsException;

	/*
	 * Method definition for finding an existing User from the database 01.26.2018
	 */
	public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException;;
}
