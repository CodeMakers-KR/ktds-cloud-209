package com.example.orderservice.exceptions;

import java.util.List;

import org.springframework.validation.FieldError;

public class OrderException extends RuntimeException {

	private static final long serialVersionUID = -7367578227719022064L;

	private List<FieldError> fieldErrors;

	public OrderException(String message) {
		super(message);
	}

	public OrderException(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public boolean isValidException() {
		return this.fieldErrors != null;
	}

	public List<FieldError> getFieldErrors() {
		return this.fieldErrors;
	}
}
