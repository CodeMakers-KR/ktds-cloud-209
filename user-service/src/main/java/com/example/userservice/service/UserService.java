package com.example.userservice.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.userservice.vo.RegistUserVO;
import com.example.userservice.vo.ResponseUserVO;

public interface UserService extends UserDetailsService {

	ResponseUserVO createNewUser(RegistUserVO registUserVO);

	List<ResponseUserVO> fetchAllUsers();
	
	ResponseUserVO fetchOneUser(String userId);

	ResponseUserVO fetchOneUserByEmail(String email);
	
}
