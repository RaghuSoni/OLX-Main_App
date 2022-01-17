package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidCategoryIdException extends RuntimeException {

	private String name;

	public InvalidCategoryIdException(String name) {
		super();
		this.name = name;
	}

	public InvalidCategoryIdException() {
		super();
		this.name = "";
	}

	@Override
	public String toString() {
		return "Invalid Category Id " + name;
	}

}
