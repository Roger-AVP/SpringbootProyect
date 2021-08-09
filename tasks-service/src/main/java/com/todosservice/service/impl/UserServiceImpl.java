package com.todosservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todosservice.entity.User;
import com.todosservice.exceptions.TodoException;
import com.todosservice.repository.UserRepository;
import com.todosservice.service.UserService;
import com.todosservice.specification.UserSpecification;
import com.todosservice.util.service.impl.JwtServiceImpl;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtServiceImpl jwtService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = searchByEmail(username);

		if (user == null) {
			return null;
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				new ArrayList<>());
	}

	@Override
	public User searchByEmail(String email) {
		User user = userRepository.findOne(UserSpecification.findByEmail(email)).orElse(null);
		return user;
	}

	@Override
	public User login(String email, String password) throws TodoException {
		User user = new User();

		user = userRepository.findOne(UserSpecification.findByEmail(email)).orElse(null);
		if (user == null) {
			throw new TodoException(HttpStatus.UNAUTHORIZED, "Email is incorrect");
		}

		if (!checkIfPasswordsMatch(password, user.getPassword())) {
			throw new TodoException(HttpStatus.UNAUTHORIZED, "Password is incorrect");
		}

		String token = generateToken(user.getEmail(), password);

		
		
		user.setToken(token);

		return user;
	}

	private boolean checkIfPasswordsMatch(String password, String encryptedPpassword) {
		boolean coinciden = false;
		if (passwordEncoder.matches(password, encryptedPpassword)) {
			coinciden = true;
		}
		return coinciden;
	}

	public String generateToken(String email, String password) throws TodoException {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (Exception e) {
			e.printStackTrace();
			throw new TodoException("Credenciales Incorectas email/Contrseña");
		}

		return jwtService.generateToken(email);
	}

	@Override
	public User create(String name, String email, String password) {
		User user = searchByEmail(email);

		if (user == null) {
			user = new User();
			user.setName(name);
			user.setPassword(passwordEncoder.encode(password));
			user.setEmail(email);
		}

		return userRepository.save(user);
	}

}