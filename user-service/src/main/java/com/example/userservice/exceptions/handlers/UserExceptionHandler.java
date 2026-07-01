package com.example.userservice.exceptions.handlers;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.userservice.exceptions.UserException;

public class UserExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<? extends Object> handleUserException(UserException userException) {
		if (userException.isValidException()) {
			return new ResponseEntity<List<FieldError>>(userException.getFieldErrors(), HttpStatusCode.valueOf(400));
		}
		return new ResponseEntity<String>(userException.getMessage(), HttpStatusCode.valueOf(500));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException runtimeException) {
		return new ResponseEntity<String>("Internal Server Error", HttpStatusCode.valueOf(500));
	}

}
