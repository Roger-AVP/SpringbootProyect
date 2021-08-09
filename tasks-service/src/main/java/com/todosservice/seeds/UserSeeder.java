package com.todosservice.seeds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.todosservice.service.UserService;

@Component
public class UserSeeder {
	private boolean configurado = false;

	@Autowired
	private UserService userService;

	@EventListener
	public void seed(ContextRefreshedEvent event) {
		if (configurado) {
			return;
		}

		seedUsersTable();

		configurado = true;
	}

	private void seedUsersTable() {

		userService.create("roger2", "roger2@gmail.com", "12345");
		userService.create("alex2", "alex2@gmail.com", "12345");

	}
}
