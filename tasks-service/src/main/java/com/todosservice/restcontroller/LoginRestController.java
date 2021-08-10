package com.todosservice.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todosservice.dto.UserDto;
import com.todosservice.entity.User;
import com.todosservice.exceptions.TodoException;
import com.todosservice.service.UserService;

@RestController
@RequestMapping("/api/login")
public class LoginRestController {

	@Autowired
	UserService userService;


	@RequestMapping(method = RequestMethod.POST)
	public UserDto login(@RequestBody UserDto user) throws TodoException {
		UserDto newUserDto = new UserDto();

		User loggedUser = userService.login(user.getEmail(), user.getPassword());
		newUserDto.setToken(loggedUser.getToken());
		newUserDto.setUserId(loggedUser.getUserId());
		newUserDto.setName(loggedUser.getName());
		newUserDto.setEmail(loggedUser.getEmail());

		return newUserDto;
	}

}
