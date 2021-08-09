package com.todosservice.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.todosservice.service.UserService;
import com.todosservice.util.service.JwtService;


@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		String authorizationHeader = null;
		String authorizationParam = null;
		authorizationHeader = httpServletRequest.getHeader("Authorization");

		authorizationParam = httpServletRequest.getParameter("Authorization");
		httpServletRequest.getParameterNames();
		String autorizacion = authorizationHeader != null ? authorizationHeader
				: authorizationParam != null ? authorizationParam : null;

		String token = null;
		String email = null;

		if (autorizacion != null && autorizacion.startsWith("Bearer ")) {
			token = autorizacion.substring(7);
			email = jwtService.getUsernameFromToken(token);
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.loadUserByUsername(email);

			if (jwtService.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);

	}

}
