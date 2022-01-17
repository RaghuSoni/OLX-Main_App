package com.olx.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.olx.entity.AuthTokenDocument;

public interface AuthTokenRepository extends MongoRepository<AuthTokenDocument, Integer> {

	Optional<AuthTokenDocument> findByauthToken(String authToken);
}

