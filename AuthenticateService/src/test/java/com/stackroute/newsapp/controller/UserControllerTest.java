package com.stackroute.newsapp.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.newsapp.model.User;
import com.stackroute.newsapp.service.SecurityTokenGenerator;
import com.stackroute.newsapp.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private User user;

	@MockBean
	private SecurityTokenGenerator tokenGenerator;

	@InjectMocks
	UserController userController;

	@Before
	public void setUp() throws Exception {
		user = new User("John123","RegUser", new Date(), "Soonu Chandran", "Nigham", "1234");
	}

	@Rollback(false)
	@Test
	public void testRegisterUser() throws Exception {
		when(userService.saveUser(user)).thenReturn(true);
		mockMvc.perform(post("/api/v1/userservice/register").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(jsonToString(user))).andExpect(status().isCreated())
				.andDo(print());
		verify(userService, times(1)).saveUser(Mockito.any(User.class));
		verifyNoMoreInteractions(userService);
	}
	
	//Negative scenario test case while user registration
	@Test
	public void testRegisterUserFailure() throws Exception{
		when(!userService.saveUser(user)).thenReturn(false);
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void testLoginUser() throws Exception {
		String userId = "John123";
		String password = "1234";
		when(userService.saveUser(user)).thenReturn(true);
		when(userService.findByUserIdAndPassword(userId, password)).thenReturn(user);
		when(userService.saveUser(user)).thenReturn(true);
		mockMvc.perform(post("/api/v1/userservice/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(jsonToString(user))).andExpect(status().isOk());
		verify(userService, times(1)).findByUserIdAndPassword(user.getUserId(), user.getPassword());
		verifyNoMoreInteractions(userService);
	}
	
	//negative scenario while the user Loggined
	@Test
	public void testLoginUserFailure() throws Exception{
		String userId = "John123";
		String password = "9876";
		when(userService.findByUserIdAndPassword(userId, password)).thenReturn(user);		
		verifyNoMoreInteractions(userService);
	}

	private static String jsonToString(final Object object) {
		String result;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			result = "Json processing error";
		}
		return result;
	}
}
