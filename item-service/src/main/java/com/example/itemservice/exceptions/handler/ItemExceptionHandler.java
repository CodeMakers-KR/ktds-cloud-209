package com.example.itemservice.exceptions.handler;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.itemservice.exceptions.ItemException;

@RestControllerAdvice
public class ItemExceptionHandler {

	@ExceptionHandler(ItemException.class)
	public ResponseEntity<? extends Object> handleUserException(ItemException itemException) {
		if (itemException.isValidException()) {
			return new ResponseEntity<List<FieldError>>(itemException.getFieldErrors(), HttpStatusCode.valueOf(400));
		}
		return new ResponseEntity<String>(itemException.getMessage(), HttpStatusCode.valueOf(500));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException runtimeException) {
		return new ResponseEntity<String>("Internal Server Error", HttpStatusCode.valueOf(500));
	}

}
