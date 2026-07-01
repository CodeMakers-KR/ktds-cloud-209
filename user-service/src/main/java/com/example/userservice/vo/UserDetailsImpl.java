package com.example.userservice.vo;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -1095156076804203898L;
	
	private ResponseUserVO responseUserVO;

	public UserDetailsImpl(ResponseUserVO responseUserVO) {
		this.responseUserVO = responseUserVO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		return this.responseUserVO.getPwd();
	}

	@Override
	public String getUsername() {
		return this.responseUserVO.getEmail();
	}

	public ResponseUserVO getResponseUserVO() {
		return this.responseUserVO;
	}

}