package com.example.itemservice.exceptions;

import java.util.List;

import org.springframework.validation.FieldError;

public class ItemException extends RuntimeException {

	private static final long serialVersionUID = 8787464261882206245L;
	
	private List<FieldError> fieldErrors;

	public ItemException(String message) {
		super(message);
	}

	public ItemException(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public boolean isValidException() {
		return this.fieldErrors != null;
	}

	public List<FieldError> getFieldErrors() {
		return this.fieldErrors;
	}

}
