package com.todosservice.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.todosservice.entity.User;
import com.todosservice.specification.UserSpecification;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testSaveUser() {
		User user = new User();
		user.setName("rogerv");
		user.setEmail("rogerv@gmail.com");
		user.setPassword(passwordEncoder.encode("12345"));

		User userTodo = userRepository.save(user);
		assertNotNull(userTodo);
	}

	@Test
	void SpecificationFindByEmail() {
		User user = userRepository.findOne(UserSpecification.findByEmail("roger@gmail.com")).orElse(null);
		assertNotNull(user);
	}

}
