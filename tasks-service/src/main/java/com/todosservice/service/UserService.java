package com.todosservice.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.todosservice.entity.User;
import com.todosservice.exceptions.TodoException;

public interface UserService extends UserDetailsService {

	User login(String email, String password) throws TodoException;

	User searchByEmail(String email);
	
	User create(String name, String email, String password);
	
	String generateToken(String email, String password) throws TodoException;
}
