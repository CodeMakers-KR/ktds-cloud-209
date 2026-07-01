package com.example.userservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.userservice.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//	@Value("${token.secret}")
//	private String tokenSecretKey;
	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	private PasswordEncoder passwordEncoder;

	public SecurityConfig() {
		this.passwordEncoder = new PasswordEncoderImpl();
	}

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager = this.getAuthenticationManager(http);

		http.csrf(csrf -> csrf.disable())
			.authenticationManager(authenticationManager)
			.addFilter(getAuthenticationFilter(authenticationManager))
			.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	private AuthenticationManager getAuthenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);

		authenticationManagerBuilder.userDetailsService(this.userService).passwordEncoder(this.passwordEncoder);

		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		return authenticationManager;
	}

	private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
		return new AuthenticationFilter(this.userService, authenticationManager, this.env.getProperty("token.secret"));
	}

}






