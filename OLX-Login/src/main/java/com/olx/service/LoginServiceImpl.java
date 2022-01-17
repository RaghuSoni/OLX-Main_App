package com.olx.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.olx.dto.UserDto;
import com.olx.entity.AuthTokenDocument;
import com.olx.entity.UserEntity;
import com.olx.exception.InvalidAuthenticationToken;
import com.olx.repository.AuthTokenRepository;
import com.olx.repository.UserRepo;
import com.olx.security.JwtUtil;


public class LoginServiceImpl implements LoginService {
	@Autowired
	UserRepo userRepository;

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	AuthTokenRepository authTokenRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		List<UserEntity> userEntityList = userRepository.findByUsername(userName);
		if (userEntityList.size() == 0) {
			throw new UsernameNotFoundException(userName);
		}
		UserEntity userEntity = userEntityList.get(0);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(userEntity.getRoles())); //
		UserDetails user2 = new User(userEntity.getUsername(), passwordEncoder.encode(userEntity.getPassword()),
				authorities);
		return user2;
	}

	@Override
	public ResponseEntity<String> loginUser(UserDto authenticationRequest) {

		try {

			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			// login fail

			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		String token = jwtUtil.generateToken(loadUserByUsername(authenticationRequest.getUserName()));
		return new ResponseEntity(token, HttpStatus.OK);

	}

	@Override
	public boolean logoutUser(String authToken) {
		boolean isValidtoken;
		String userName = "";
		try {
			String jwtToken = authToken.substring(7, authToken.length());
			userName = jwtUtil.extractUsername(jwtToken);
			List<UserEntity> userEntityList = userRepository.findByUsername(userName);
			if (userEntityList.size() == 0) {
				throw new UsernameNotFoundException(userName);
			}
			UserEntity userEntity = userEntityList.get(0);
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(userEntity.getRoles())); //
			UserDetails userDetails = new User(userEntity.getUsername(),
					passwordEncoder.encode(userEntity.getPassword()), authorities);
			isValidtoken = jwtUtil.validateToken(jwtToken, userDetails);
			if (isValidtoken) {

				Optional<AuthTokenDocument> authTokenDocument = authTokenRepository.findByauthToken(jwtToken);
				if (authTokenDocument.isPresent()) {

					// authTokenRepository.save(authTokenDocument);// mongo save
					// userEntity.setActive(false);
					// userRepository.save(userEntity);

					throw new InvalidAuthenticationToken("Alredy logged out");

				} else {

					AuthTokenDocument doc = new AuthTokenDocument();
					doc.setAuthToken(jwtToken);
					authTokenRepository.save(doc);
					userEntity.setActive(false);
					userRepository.save(userEntity);
				}

			} else {
				throw new UsernameNotFoundException(userName);
			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public UserDto registerAnUser(UserDto userRequest) {

		UserEntity entity = convertDtoToEntity(userRequest);
		userRepository.save(entity);
		userRequest.setId(entity.getId());
		userRequest.setPassword("");
		userRequest.setRoles("");
		// userRequest.setActive(false);

		return userRequest;
	}

	@Override
	public UserDto returnAUser(String authToken) {
		boolean isValidtoken;
		String userName = "";
		UserDto authenticationRequest = null;
		try {
			String jwtToken = authToken.substring(7, authToken.length());
			userName = jwtUtil.extractUsername(jwtToken);
			List<UserEntity> userEntityList = userRepository.findByUsername(userName);
			if (userEntityList.size() == 0) {
				throw new UsernameNotFoundException(userName);
			}
			UserEntity userEntity = userEntityList.get(0);
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(userEntity.getRoles())); //
			UserDetails userDetails = new User(userEntity.getUsername(),
					passwordEncoder.encode(userEntity.getPassword()), authorities);
			isValidtoken = jwtUtil.validateToken(jwtToken, userDetails);
			if (isValidtoken) {
				authenticationRequest = convertEntityToDto(userEntity);

			} else {
				throw new UsernameNotFoundException(userName);
			}

		} catch (Exception e) {
			//
			throw new UsernameNotFoundException(userName);
		}
		return authenticationRequest;
	}

	@Override
	public ResponseEntity<Boolean> validateToken(String authToken) {
		boolean isValidtoken = false;
		try {
			String jwtToken = authToken.substring(7, authToken.length());
			UserDetails userDetails = loadUserByUsername(jwtUtil.extractUsername(jwtToken));
			isValidtoken = jwtUtil.validateToken(jwtToken, userDetails);
			if (isValidtoken) {
				Optional<AuthTokenDocument> authTokenDocument = authTokenRepository.findByauthToken(jwtToken);
				if (authTokenDocument.isPresent()) {
					AuthTokenDocument doc = authTokenDocument.get();
					if (doc.getAuthToken().equals(jwtToken)) {
						isValidtoken = false;
						throw new InvalidAuthenticationToken();
					} else {
						isValidtoken = true;
					}
				}
			}

		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(isValidtoken, HttpStatus.OK);

	}

	private UserEntity convertDtoToEntity(UserDto authenticationRequest) {
		UserEntity userEntity = modelMapper.map(authenticationRequest, UserEntity.class);
		return userEntity;
	}

	private UserDto convertEntityToDto(UserEntity userEntity) {
		UserDto dto = modelMapper.map(userEntity, UserDto.class);
		dto.setPassword("");
		dto.setRoles("");
		return dto;

	}

	@Override
	public ResponseEntity<String> getUserName(String authToken) {
		String userName = "";

		try {
			String jwtToken = authToken.substring(7, authToken.length());
			UserDetails userDetails = loadUserByUsername(jwtUtil.extractUsername(jwtToken));
			boolean isValidtoken = jwtUtil.validateToken(jwtToken, userDetails);
			if (isValidtoken) {

				userName = userDetails.getUsername();

			} else {
				return new ResponseEntity("Invalid Token", HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(userName, HttpStatus.OK);

	}

}