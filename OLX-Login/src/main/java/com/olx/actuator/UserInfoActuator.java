package com.olx.actuator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.InfoContributor;

import com.olx.repository.UserRepo;

public class UserInfoActuator implements InfoContributor {
	
	@Autowired
	UserRepo userRepository;
	

	@Override
	public void contribute(Builder builder) {
	
		Map<String, Object> userDetails = new HashMap<>();
		userDetails.put("total-registered-users", userRepository.count());
		userDetails.put("active-login-count", 350);
		builder.withDetails(userDetails);
	}

}
