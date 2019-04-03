package com.stackroute.newsapp.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.newsapp.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	public void setRepo(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Rollback(false)
	@Test
	public void testRegisterUserSuccess() throws Exception {
		User user = new User("Soonu123","RegUser", new Date(), "Soonu Chandran", "Nigham", "1234567");
		userRepository.save(user);
		Optional<User> userObject = userRepository.findById(user.getUserId());
		assertThat(userObject.equals(user));
	}
	
	//new negative scenario for handling the user registration
	@Rollback(false)
	@Test
	public void testRegisterUserFailure() throws Exception {
		User user = new User("Admin123","Admin", new Date(), "Amal", "Babu", "98227272");
		userRepository.save(user);
		assertFalse(userRepository.findById(user.getUserId()).equals("Adm456"));
	}
	
}
