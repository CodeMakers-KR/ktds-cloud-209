package com.example.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegistUserVO {

	private String userId;

	@NotNull(message = "Email을 입력해주세요.")
	@Email(message = "Email 형식으로 입력해주세요.")
	private String email;

	@NotNull(message = "비밀번호를 입력해주세요.")
	@Size(min = 8, message = "8자리 이상 입력해주세요.")
	private String pwd;

	private String salt;

	@NotNull(message = "이름을 입력해주세요.")
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
