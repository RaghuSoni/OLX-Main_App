package com.olx.service;


public interface LoginDelegate {

	public boolean validateToken(String authToken);
	
	public String getUserInfo(String authToken);

}
