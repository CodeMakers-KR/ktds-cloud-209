package com.example.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.userservice.dao.UserDao;
import com.example.userservice.utils.Sha;
import com.example.userservice.vo.RegistUserVO;
import com.example.userservice.vo.ResponseUserVO;
import com.example.userservice.vo.UserDetailsImpl;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public ResponseUserVO createNewUser(RegistUserVO registUserVO) {
		String salt = Sha.generateSalt();
		String password = registUserVO.getPwd();
		String encryptedPassword = Sha.getEncrypt(password, salt);
		registUserVO.setPwd(encryptedPassword);
		registUserVO.setSalt(salt);

		int createCount = this.userDao.insertNewUser(registUserVO);
		if (createCount > 0) {
			return this.userDao.selectOneUserByUserId(registUserVO.getUserId());
		}
		return null;

	}

	@Override
	public List<ResponseUserVO> fetchAllUsers() {
		return this.userDao.selectAllUsers();
	}

	@Override
	public ResponseUserVO fetchOneUser(String userId) {
		return this.userDao.selectOneUserByUserId(userId);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ResponseUserVO userVO = this.userDao.selectOneUserByEmail(username);
		if (userVO == null) {
			throw new UsernameNotFoundException(username);
		}

		return new UserDetailsImpl(userVO);
	}

	@Override
	public ResponseUserVO fetchOneUserByEmail(String email) {
		return this.userDao.selectOneUserByEmail(email);
	}

}
