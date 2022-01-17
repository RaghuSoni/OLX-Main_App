package com.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olx.entity.StatusEntity;

public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {

}
