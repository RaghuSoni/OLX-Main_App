package com.olx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginDelegateImpl implements LoginDelegate {

	@Autowired
	RestTemplate restTemplate;

	@Override
	public boolean validateToken(String jwtToken) {
		try {
			HttpHeaders headers = new HttpHeaders();

			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add(HttpHeaders.AUTHORIZATION, jwtToken);
			headers.add("headerInfo", "data");
			HttpEntity<String> entity = new HttpEntity<>(jwtToken, headers);
			return restTemplate.postForObject("http://API-GATEWAY/olx/login/user/validate/token", entity,
					Boolean.class);
		
		}catch (Exception e) {
			return false;
		}

	}


	@Override
	public String getUserInfo(String authToken) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add(HttpHeaders.AUTHORIZATION, authToken);
			headers.add("headerInfo", "data");
			HttpEntity<String> entity = new HttpEntity<>(authToken, headers);
			
			return restTemplate.postForObject("http://API-GATEWAY/olx/login/user/name", entity, String.class);	
		}

		catch (Exception e) {

			return null;
		}

	}
}
