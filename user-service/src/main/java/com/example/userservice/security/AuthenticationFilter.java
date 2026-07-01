package com.example.userservice.security;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.userservice.service.UserService;
import com.example.userservice.utils.Sha;
import com.example.userservice.vo.ResponseUserVO;
import com.example.userservice.vo.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private String secretKey;
	private UserService userService;

	public AuthenticationFilter(UserService userService
					, AuthenticationManager authenticationManager
					, String secretKey) {
		this.userService = userService;
		this.secretKey = secretKey;
		super.setAuthenticationManager(authenticationManager);
	}

	/** 인증을 수행하기 위한 토큰을 생성함. */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			Map<String, String> creds = new ObjectMapper().readValue(request.getInputStream(), HashMap.class);
			String email = creds.get("email");
			String password = creds.get("password");

			ResponseUserVO userDetails = this.userService.fetchOneUserByEmail(email);
			String salt = userDetails.getSalt();
			password = Sha.getEncrypt(password, salt);

			return getAuthenticationManager()
					.authenticate(new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * attemptAuthentication에서 인증이 성공 할 경우의 후 처리 작업 진행. 예> JWT 발행.
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		UserDetailsImpl userDetail = (UserDetailsImpl) authResult.getPrincipal();
	    Date now = new Date();

	    // 1. 토큰의 유효기간 설정.
	    Date expiredDate = new Date(now.getTime() + Duration.ofDays(30).toMillis());

	    // 2. 토큰의 암호화를 위한 비밀키 생성.
	    SecretKey secretKey = Keys.hmacShaKeyFor(this.secretKey.getBytes());

	    // 3. JsonWebToken 생성 및 반환.
	    String token = Jwts.builder()
	                       .subject(userDetail.getResponseUserVO().getUserId())
	                       .claim("email", userDetail.getUsername()) // email
	                       .claim("roles", "ROLE_USER")
	                       .issuedAt(now) // 토큰을 발행한 날짜와 시간
	                       .expiration(expiredDate) // 토큰이 만료되는 날짜와 시간
	                       .signWith(secretKey) // 암호화에 사용될 키.
	                       .compact();
	 
	    response.addHeader("token", token);
	    response.addHeader("email", userDetail.getUsername());


	}

}