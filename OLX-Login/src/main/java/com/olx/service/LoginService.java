package com.olx.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.olx.dto.UserDto;

public interface LoginService extends UserDetailsService {

	public ResponseEntity<String> loginUser(UserDto userRequest);

	public boolean logoutUser(String authToken);

	public UserDto registerAnUser(UserDto userRequest);

	public UserDto returnAUser(String authToken);

	public ResponseEntity<Boolean> validateToken(String authToken);

	public ResponseEntity<String> getUserName(String authToken);
}
