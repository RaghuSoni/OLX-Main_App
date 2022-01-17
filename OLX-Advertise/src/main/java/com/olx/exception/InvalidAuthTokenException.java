package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidAuthTokenException extends RuntimeException {

	private String name;

	public InvalidAuthTokenException(String name) {
		super();
		this.name = name;
	}

	public InvalidAuthTokenException() {
		super();
		this.name = "";
	}

	@Override
	public String toString() {
		return "Invalid AuthToken " + name;
	}

}
