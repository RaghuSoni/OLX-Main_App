package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidRecoedNumberException extends RuntimeException {

	private String name;

	public InvalidRecoedNumberException(String name) {
		super();
		this.name = name;
	}

	public InvalidRecoedNumberException() {
		super();
		this.name = "";
	}

	@Override
	public String toString() {
		return "Invalid Status Id " + name;
	}

}
