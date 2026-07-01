package com.example.userservice.vo;

import java.util.List;

public class ResponseUserVO {

	private String userId;
	private String email;
	private String name;
	private String pwd;
	private String salt;

	private List<ResponseOrderVO> orders;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public List<ResponseOrderVO> getOrders() {
		return this.orders;
	}

	public void setOrders(List<ResponseOrderVO> orders) {
		this.orders = orders;
	}

}
