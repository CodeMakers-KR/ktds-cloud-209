package com.example.userservice.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl implements PasswordEncoder {

	@Override
	public @Nullable String encode(@Nullable CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
		return rawPassword.toString().equals(encodedPassword);
	}

}
