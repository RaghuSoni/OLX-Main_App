package com.olx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.olx.dto.UserDto;
import com.olx.exception.InvalidAuthenticationToken;
import com.olx.service.LoginService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/olx/login")
public class UserController {

	@Autowired
	@Qualifier(value = "olxlogin1")
	LoginService loginService;

	@ExceptionHandler(value = { InvalidAuthenticationToken.class })
	public ResponseEntity<String> handleInvalidAuthenticationTokenError(RuntimeException exception,
			WebRequest request) {

		return new ResponseEntity<String>("InValid AuthenticationToken", HttpStatus.BAD_REQUEST);

	}

	@PostMapping(value = "/user/authenticate", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "To Login a User")
	public ResponseEntity<String> authenticate(@RequestBody UserDto authenticationRequest) {
		return loginService.loginUser(authenticationRequest);
	}

	@PostMapping(value = "/user/validate/token")
	@ApiOperation(value = "To validate a token")
	public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authToken) {
		return loginService.validateToken(authToken);
	}

	@PostMapping(value = "/user/name")
	@ApiOperation(value = "To validate a token")
	public ResponseEntity<String> getUserName(@RequestHeader("Authorization") String authToken) {
		return loginService.getUserName(authToken);
	}

	@DeleteMapping(value = "/user/logout")
	public ResponseEntity<Boolean> logoutCurrentUser(@RequestHeader("Authorization") String authToken) {
		if (loginService.logoutUser(authToken)) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}

		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);

	}

	@ApiOperation(value = "To Logout  a User")
	public boolean logoutUser(@RequestHeader("Authorization") String authToken) {
		return loginService.logoutUser(authToken);
	}

	@PostMapping(value = "/user", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })

	@ApiOperation(value = "To Register a User")
	public UserDto registerAnUser(@RequestBody UserDto user) {
		return loginService.registerAnUser(user);
	}

	@GetMapping(value = "/user")
	@ApiOperation(value = "To return the user information ")
	public UserDto returnAUser(@RequestHeader("Authorization") String authToken) {
		return loginService.returnAUser(authToken);
	}
}
