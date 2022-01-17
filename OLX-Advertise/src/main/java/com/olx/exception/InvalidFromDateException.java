package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidFromDateException extends RuntimeException {

	private String name;

	public InvalidFromDateException(String name) {
		super();
		this.name = name;
	}

	public InvalidFromDateException() {
		super();
		this.name = "";
	}

	@Override
	public String toString() {
		return "Invalid date Condition " + name;
	}

}
