package com.olx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.olx.dto.*;
import com.olx.service.MasterDataService;

@RestController
@RequestMapping("olx/masterdata")
public class MasterDataController {

	@Autowired
	MasterDataService masterDataService;
	
	
	@GetMapping(value="/advertise/category",produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<Category> getAllCategories(){
		return masterDataService.getAllCategory();
	}
	

	@GetMapping(value="/advertise/status",produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<Status> getAllStatus(){
		return masterDataService.getAllStatus();
	}
	
	
}
