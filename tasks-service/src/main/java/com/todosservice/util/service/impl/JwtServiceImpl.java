package com.todosservice.util.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todosservice.util.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Service
@Transactional
public class JwtServiceImpl implements JwtService {

	public static final long EXPIRED_TIME = 99999999;

	private String secret = "secret";

	// retrieve username from jwt token
	@Override
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// generate token for user
	@Override
	public String generateToken(String valor) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, valor);
	}

	// Create token
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 2 * EXPIRED_TIME * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	// validate token
	@Override
	public Boolean validateToken(String token, UserDetails usuario) {
		if (usuario == null) {
			return false;
		}
		final String username = getUsernameFromToken(token);
		return (username.equals(usuario.getUsername()) && !isTokenExpired(token));
	}

	// check if the token has expired
	@Override
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

}
