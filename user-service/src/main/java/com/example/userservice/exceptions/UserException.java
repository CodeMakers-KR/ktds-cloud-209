package com.example.userservice.exceptions;

import java.util.List;

import org.springframework.validation.FieldError;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 7826273396674069446L;
	
	private List<FieldError> fieldErrors;

	public UserException(String message) {
		super(message);
	}

	public UserException(List<FieldError> fieldErrors) {

		this.fieldErrors = fieldErrors;
	}

	public boolean isValidException() {
		return this.fieldErrors != null;
	}

	public List<FieldError> getFieldErrors() {
		return this.fieldErrors;
	}

}
