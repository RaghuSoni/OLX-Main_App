package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class AdNotFoundException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	private String name;

	public AdNotFoundException(String name) {
		super();
		this.name = name;
	}

	public AdNotFoundException() {
		super();
		this.name = "";
	}

	@Override
	public String toString() {
		return "Advertisement not found " + name;
	}

}
