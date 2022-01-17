package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidDateFormatException extends RuntimeException {

	private String name;

	public InvalidDateFormatException(String name) {
		super();
		this.name = name;
	}

	public InvalidDateFormatException() {
		super();
		this.name = "";
	}

	@Override
	public String toString() {
		return "Invalid Date Format " + name;
	}

}
