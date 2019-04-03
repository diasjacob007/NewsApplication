package com.stackroute.newsapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.stackroute.newsapp.exceptions.UserAlreadyExistsException;
import com.stackroute.newsapp.exceptions.UserNotFoundException;
import com.stackroute.newsapp.model.User;
import com.stackroute.newsapp.service.SecurityTokenGenerator;
import com.stackroute.newsapp.service.UserService;

@RestController
@EnableWebMvc
@RequestMapping("/api/v1/userservice")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityTokenGenerator tokenGenerator;

	/*
	 * This is the POST controller call that is invoking while performing Registration
	 * of the new User_01.26.2019
	 */
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		ResponseEntity<?> responseEntity;
		try {
			userService.saveUser(user);
			responseEntity = new ResponseEntity<String>("Successfully registered the User", HttpStatus.CREATED);
		} catch (UserAlreadyExistsException e) {
			responseEntity = new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\")",
					HttpStatus.CONFLICT);
		}
		return responseEntity;
	}

	/*
	 * This is the POST Controller call that is invoking while a User being Loggined_01.26.2018
	 */
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User loginDetail) {
		ResponseEntity<?> responseEntity;
		try {
			String userId = loginDetail.getUserId();
			String password = loginDetail.getPassword();

			if (userId == null || password == null) {
				throw new UserNotFoundException("userId or password doesn't exist");
			}

			User user = userService.findByUserIdAndPassword(userId, password);

			if (user == null) {
				throw new UserNotFoundException("The user with given id doesn't exist");
			}

			String pswd = loginDetail.getPassword();
			if (!password.equals(pswd)) {
				throw new Exception("Invalid login credentials. Please check your Username and Password");
			}

			//Here comes the code for generating JWT token corresponding to each loggined users
			Map<String, String> map = tokenGenerator.generateToken(user);
			responseEntity = new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);

		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\")",
					HttpStatus.UNAUTHORIZED);
		}

		return responseEntity;
	}
}
