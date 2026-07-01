package com.example.orderservice.exceptions.handler;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.orderservice.exceptions.OrderException;

@RestControllerAdvice
public class OrderExceptionHandler {

	@ExceptionHandler(OrderException.class)
	public ResponseEntity<? extends Object> handleUserException(OrderException orderException) {
		if (orderException.isValidException()) {
			return new ResponseEntity<List<FieldError>>(orderException.getFieldErrors(), HttpStatusCode.valueOf(400));
		}
		return new ResponseEntity<String>(orderException.getMessage(), HttpStatusCode.valueOf(500));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException runtimeException) {
		return new ResponseEntity<String>("Internal Server Error", HttpStatusCode.valueOf(500));
	}

}
