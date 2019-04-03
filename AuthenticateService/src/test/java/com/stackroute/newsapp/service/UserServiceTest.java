package com.stackroute.newsapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;

import com.stackroute.newsapp.exceptions.UserAlreadyExistsException;
import com.stackroute.newsapp.exceptions.UserNotFoundException;
import com.stackroute.newsapp.model.User;
import com.stackroute.newsapp.repositories.UserRepository;

public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	private User user;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	Optional<User> options;

	@Before
	public void setUpMock() {
		MockitoAnnotations.initMocks(this);
		user = new User("Soonu123","RegUser", new Date(), "Soonu Chandran", "Nigham", "1234567");
		options = Optional.of(user);
	}

	@Rollback(false)
	@Test
	public void testSaveUserSuccess() throws UserAlreadyExistsException {
		when(userRepository.save(user)).thenReturn(user);
		boolean flag = userServiceImpl.saveUser(user);
		assertEquals("Register User failed", true, flag);
		verify(userRepository, times(1)).save(user);
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void testSaveUserFailure() throws UserAlreadyExistsException {
		when(userRepository.findById(user.getUserId())).thenReturn(options);
		when(userRepository.save(user)).thenReturn(user);
		boolean flag = userServiceImpl.saveUser(user);
		assertFalse("Saving User failed", flag);
	}

	@Test
	public void testValidateSuccess() throws UserNotFoundException {
		when(userRepository.findByUserIdAndPassword(user.getUserId(), user.getPassword())).thenReturn(user);
		User userResult = userServiceImpl.findByUserIdAndPassword(user.getUserId(), user.getPassword());
		assertNotNull(userResult);
		assertEquals("Soonu123", userResult.getUserId());
		assertEquals("1234567", userResult.getPassword());
		verify(userRepository, times(1)).findByUserIdAndPassword(user.getUserId(), user.getPassword());
	}

	@Rule
	public ExpectedException usernotfoundExcep = ExpectedException.none();

	@Test
	public void testValidateFailure() throws UserNotFoundException {
		usernotfoundExcep.expect(UserNotFoundException.class);
		userServiceImpl.findByUserIdAndPassword("amal", "amal123");
	}
}
