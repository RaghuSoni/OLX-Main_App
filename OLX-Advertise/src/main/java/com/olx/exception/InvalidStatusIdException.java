package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidStatusIdException extends RuntimeException {

	private String name;

	public InvalidStatusIdException(String name) {
		super();
		this.name = name;
	}

	public InvalidStatusIdException() {
		super();
		this.name = "";
	}

	@Override
	public String toString() {
		return "Invalid Status Id " + name;
	}

}
