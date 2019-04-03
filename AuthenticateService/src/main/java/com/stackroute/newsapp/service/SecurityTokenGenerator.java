/**
 * 
 */
package com.stackroute.newsapp.service;

import java.util.Map;

import com.stackroute.newsapp.model.User;

/**
 * @author ubuntu
 * Interface declaration for JWT Token generation
 * 10.26.2019
 */
public interface SecurityTokenGenerator {

	Map<String, String> generateToken(User user);

}
