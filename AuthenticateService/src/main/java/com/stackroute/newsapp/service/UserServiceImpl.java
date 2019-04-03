package com.stackroute.newsapp.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.newsapp.exceptions.UserAlreadyExistsException;
import com.stackroute.newsapp.exceptions.UserNotFoundException;
import com.stackroute.newsapp.model.User;
import com.stackroute.newsapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserRepository userRepo;

	public UserServiceImpl(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.stackroute.newsapp.service.UserService#saveUser(com.stackroute.
	 * newsapp.model.User) Overrided method for saving User details Method
	 * implementation for saving a new user while performing the Registration
	 * process_01.26.2019
	 */
	@Override
	public boolean saveUser(User user) throws UserAlreadyExistsException {
		if (null == user.getCreateddate() || user.getCreateddate().equals("")) {
			user.setCreateddate(new Date());
		}
		// Setting this new field value to differentiate between a normal registered
		// user or admin
		System.out.println("Inside saveUser() userRole is: " +user.getUserRole());
		if (null == user.getUserRole() || user.getUserRole().isEmpty() || user.getUserRole().equals("newUser.userRole")) {
			user.setUserRole("RegUser");
		}
		final Optional<User> userObject = userRepo.findById(user.getUserId());
		if (userObject.isPresent()) {
			throw new UserAlreadyExistsException("This User already exists");
		}
		userRepo.save(user);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.stackroute.newsapp.service.UserService#findUserbyIdAndPassword(java.
	 * lang.String, java.lang.String) Overrided method for finding a particular user
	 * using his UserId & Password_01.26.2019
	 */
	@Override
	public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException {
		final User user = userRepo.findByUserIdAndPassword(userId, password);
		if (user == null) {
			throw new UserNotFoundException("UserId Password mismatch.Not able to find this particular User");
		}
		return user;
	}

}
