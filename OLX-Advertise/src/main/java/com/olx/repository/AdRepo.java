package com.olx.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.olx.entity.AdvertisesEntity;

public interface AdRepo extends JpaRepository<AdvertisesEntity, Integer> {

	public Optional<AdvertisesEntity> findByUsernameAndId(String userName, int id);

	public List<AdvertisesEntity> findByUsername(String userName);

	@Query("SELECT a FROM AdvertisesEntity a WHERE a.title LIKE %:searchText% or a.category LIKE %:searchText% or a.description LIKE %:searchText%  or a.price LIKE %:searchText% or a.postedBy LIKE %:searchText%"
			+ " or a.createdDate LIKE %:searchText% or a.modifiedDate LIKE %:searchText%")
	public List<AdvertisesEntity> findByText(@Param("searchText") String searchText);

	@Query("SELECT a FROM AdvertisesEntity a WHERE  (:searchText IS NULL OR  a.title LIKE %:searchText% ) AND (:categoryId IS NULL OR category=:categoryId ) AND (:userId IS NULL OR id=:userId ) AND (:dateCondition='equals' AND  a.createdDate=:onDate) OR (:dateCondition='greaterthan' AND  a.createdDate>:fromDate) OR (:dateCondition='lessthan' AND  a.createdDate<:toDate) OR (:dateCondition='between' AND  a.createdDate>:fromDate AND a.createdDate<=:toDate) "
			+ " ORDER BY  CASE WHEN  :sortBy  IS NULL THEN  id  ELSE -id  END ")
	public Page<AdvertisesEntity> findByQuery(@Param("searchText") String searchText,
			@Param("categoryId") Integer categoryId, @Param("userId") Integer userId,
			@Param("dateCondition") String dateCondition, @Param("onDate") Date onDate,
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("sortBy") String sortBy,
			Pageable page);

	
}
