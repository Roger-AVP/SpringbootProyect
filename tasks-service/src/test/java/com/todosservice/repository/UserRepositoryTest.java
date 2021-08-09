package com.todosservice.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.todosservice.entity.User;

@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testSaveUser() {
		User user = new User(null, "roger", "alex@gmail.com", "12345", null);
		User userTodo = userRepository.save(user);

		assertNotNull(userTodo);
	}

}
