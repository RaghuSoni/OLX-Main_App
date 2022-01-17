package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidDateConditionException extends RuntimeException {

	private String name;

	public InvalidDateConditionException(String name) {
		super();
		this.name = name;
	}

	public InvalidDateConditionException() {
		super();
		this.name = "";
	}

	@Override
	public String toString() {
		return "Invalid Status Id " + name;
	}

}
