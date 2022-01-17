package com.olx.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("tokendoc")
public class AuthTokenDocument {
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	private String authToken;
}
