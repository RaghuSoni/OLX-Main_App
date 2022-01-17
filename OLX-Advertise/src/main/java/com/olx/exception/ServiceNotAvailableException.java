package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class ServiceNotAvailableException extends RuntimeException {

	private String name;

	public ServiceNotAvailableException(String name) {
		super();
		this.name = name;
	}

	public ServiceNotAvailableException() {
		super();
		this.name = "";
	}

	@Override
	public String toString() {
		return "Master Data Service Not Available.Service might be down. Please wait for some time or Retry  after some time " + name;
	}

}
