package com.olx.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.Advertise;
import com.olx.dto.Category;
import com.olx.dto.Status;
import com.olx.service.AdvertisementService;
import com.olx.service.LoginDelegate;
import com.olx.service.MasterDataDelegate;

import io.swagger.annotations.ApiOperation;




@RestController
@RequestMapping("olx/advertise")
public class AdvertiseController {

	@Autowired
	AdvertisementService advertisementService;
	
	@Autowired
	LoginDelegate loginDelegate;

	@Autowired
	MasterDataDelegate masterDataDelegate;
	
	

	@PostMapping(value = "/advertise", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Posts New Advertise")
	public Advertise postsNewAdvertise(@RequestHeader("Authorization") String authToken,
			@RequestBody Advertise adv) {
		return advertisementService.createNewAdvertise(authToken, adv);
	}
 
	@PostMapping(value = "/validate", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },

			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Posts New Advertise")
	public boolean validateJWTToken(@RequestHeader("Authorization") String authToken) {

		return loginDelegate.validateToken(authToken);

	}

	@PutMapping(value = "/advertise/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Updates Existing Advertise")
	public Advertise updatesexistingAdvertise(@RequestHeader("Authorization") String authToken,
			@PathVariable("id") int advertisementId, @RequestBody Advertise advrt) {
		return advertisementService.updateAdvertiseById(authToken, advertisementId, advrt);
	}

	@GetMapping(value = "/user/advertise", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Reads All Advertisements Posted By User")
	public List<Advertise> readsAllAdvertisementsPostedbyUser(@RequestHeader("Authorization") String authToken

	) {

		return advertisementService.getUserAdvertise(authToken);

	}

	@GetMapping(value = "/user/advertise/{advertiseId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = " Reads Specific Advertisement PosedBy Loggedin User")
	public Advertise readsSpecificAdvertisementPosedByLoggedinUser(@RequestHeader("Authorization") String authToken,
			@PathVariable("advertiseId") int id) {

		return advertisementService.getAdvertisementById(authToken, id);

	}

	@DeleteMapping(value = "/user/advertise/{advertiseId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Deletes Specific Advertisement PostedBy LoggedIn User")
	public boolean deletesSpecificAdvertisementPostedByLoggedInUser(@RequestHeader("Authorization") String authToken,
			@PathVariable("advertiseId") int id) {

		return advertisementService.deleteAdvertiseById(authToken, id);

	}

	@GetMapping(value = "/advertise/{advertiseId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Return Advertise Details")
	public Advertise returnAdvertiseDetails(@RequestHeader("Authorization") String authToken,
			@PathVariable("advertiseId") int id) {

		return advertisementService.getAdvertisementById(authToken, id);

	}

	@GetMapping(value = "/advertise/search/filtercriteria", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Search Filter Service Ads")
	public List<Advertise> searchAdvertisementsBasedUpOnGivenFilterCriteria(
			@RequestParam(name = "searchText", required = false) String searchText,
			@RequestParam(name = "categoryId", required = false) Integer categoryId,
			@RequestParam(name = "postedBy", required = false) String postedBy,
			@RequestParam(name = "dateCondition", required = false) String dateCondition,
			@RequestParam(name = "onDate", required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)  LocalDate onDate,
			@RequestParam(name = "fromDate", required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@RequestParam(name = "toDate", required = false)  @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate toDate,
			@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "startIndex", required = false) Integer startIndex,
			@RequestParam(name = "records", required = false) Integer numOfRecords) {

		return advertisementService.searchAdvertisementByFilter(searchText, categoryId, postedBy, dateCondition,
				onDate, fromDate, toDate, sortBy, startIndex, numOfRecords);

	}

	@GetMapping(value = "/advertise/search", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Advertisement Search")
	public List<Advertise> searchAdvertisementsUsingTheProvidedSearchTextWithinAllFieldsOfAnAdvertise(
			@RequestParam(name = "searchText", required = false) String searchText) {

		return advertisementService.searchAdvertisementByFilter(searchText);
	}

	

	@GetMapping(value = "/get/cat", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Advertisement Search")
	public List<Category> mastercatgoriesSatus() {

		return masterDataDelegate.getAllCategories();
	}

	@GetMapping(value = "/get/advstatus", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Advertisement Search")
	public List<Status> masterStatus() {
		return masterDataDelegate.getAllStatus();
	}
	
	
}
