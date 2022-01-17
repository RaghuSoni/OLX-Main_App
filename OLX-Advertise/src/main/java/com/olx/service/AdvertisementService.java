package com.olx.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import com.olx.dto.Advertise;
import com.olx.dto.Status;

public interface AdvertisementService {

	public Advertise getAdvertise(String authToken,int id);
	
	public List<Advertise> searchAdvertisementByFilter(String searchText);
	
	public List<Advertise> searchAdvertisementByFilter(String searchText,Integer categoryId,String postedBy,
			String dateCondition,LocalDate onDate,LocalDate fromDate, LocalDate toDate, String sortBy,
			Integer startIndex, Integer numOfRecords);
	
	public Advertise updateAdvertiseById(String authToken,int advertiseId,Advertise newAdvertise);
	
	public Advertise createNewAdvertise(String authToken,Advertise advertise);
	
	public boolean deleteAdvertiseById(String authToken,int id);
	
	public Advertise getAdvertisementById(String authToken,int advertiseId);
	
	public List<Advertise> getUserAdvertise(String authToken);
	
	
	
	
}
