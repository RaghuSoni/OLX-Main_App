package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidOnDateException extends RuntimeException {

	private String name;

	public InvalidOnDateException(String name) {
		super();
		this.name = name;
	}

	public InvalidOnDateException() {
		super();
		this.name = "";
	}

	@Override
	public String toString() {
		return "Invalid OnDate " + name;
	}

}
