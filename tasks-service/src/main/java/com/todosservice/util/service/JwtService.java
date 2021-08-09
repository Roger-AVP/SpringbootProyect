package com.todosservice.util.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

	public String getUsernameFromToken(String token);
	
	public Boolean validateToken(String token, UserDetails usuario);
	
	public String generateToken(String valor);
	
	public Boolean isTokenExpired(String token);
}
