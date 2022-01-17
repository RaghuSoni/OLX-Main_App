package com.olx.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.Advertise;
import com.olx.dto.Category;
import com.olx.dto.Status;
import com.olx.entity.AdvertisesEntity;
import com.olx.exception.AdNotFoundException;
import com.olx.exception.InvalidAdvertiseIdException;
import com.olx.exception.InvalidAuthTokenException;
import com.olx.repository.AdRepo;
import com.olx.utility.Constants;
import com.olx.utility.LocalDateAttributeConverter;
import com.olx.utility.Utility;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

	@Autowired
	private AdRepo adRepo;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	LoginDelegate loginDelegate;

	@Autowired
	Utility utility;
	
	@Autowired
	LocalDateAttributeConverter dateCoverter;

	@Autowired
	EntityManager entityManager;

	@Autowired
	MasterDataDelegate masterDataDelegate;

	@Override
	public Advertise getAdvertise(String authToken,int id) {
		if (loginDelegate.validateToken(authToken)) {
			Optional<AdvertisesEntity> optionalEntity = adRepo.findById(id);
			if (optionalEntity.isPresent()) {
				AdvertisesEntity advEntity = optionalEntity.get();
				List<Category> catList = masterDataDelegate.getAllCategories();
				List<Status> advStatusList = masterDataDelegate.getAllStatus();
				int catId = advEntity.getCategory();
				String catName = utility.getCategoryNameFromList(catList, catId);

				int statusId = advEntity.getStatus();
				String status = utility.getAdvertiseStatusFromList(advStatusList, statusId);

				Advertise advertise = convertToDto(advEntity);
				advertise.setCategory(catId);
				advertise.setStatus(statusId);

				return advertise;
			} else {
				throw new InvalidAdvertiseIdException(id + "");
			}

		} else {
			throw new InvalidAuthTokenException("Invalid Auth token");

		}
	}

	@Override
	public List<Advertise> searchAdvertisementByFilter(String searchText) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdvertisesEntity> criteriaQuery = criteriaBuilder.createQuery(AdvertisesEntity.class);
		Root<AdvertisesEntity> rootEntity = criteriaQuery.from(AdvertisesEntity.class);
		List<Predicate> predicateList = new ArrayList<>();

		Predicate titlePredicate = criteriaBuilder.like(rootEntity.get("title"), "%" + searchText + "%");
		Predicate descriptionPredicate = criteriaBuilder.like(rootEntity.get("description"), "%" + searchText + "%");

		Predicate postedPredicate = criteriaBuilder.equal(rootEntity.get("postedBy"), "%" + searchText + "%"); // title=searchText
		Predicate userNamePredicate = criteriaBuilder.like(rootEntity.get("username"), "%" + searchText + "%");
		// Predicate categoryPredicate =
		// criteriaBuilder.equal(rootEntity.get("category"), searchText);

		Predicate postedByPredicate = criteriaBuilder.equal(rootEntity.get("postedBy"), searchText);
		Predicate predicateAnd1 = criteriaBuilder.or(titlePredicate, postedPredicate, postedByPredicate,
				userNamePredicate, descriptionPredicate);
		predicateList.add(predicateAnd1);
		Predicate finalPredicate = criteriaBuilder.or(titlePredicate, postedPredicate, userNamePredicate,
				descriptionPredicate);
		criteriaQuery.where(finalPredicate);
		TypedQuery<AdvertisesEntity> query = entityManager.createQuery(criteriaQuery);
		List<AdvertisesEntity> advertiseEntityList = query.getResultList();
		List<Advertise> advertiseList = getListFromEntities(advertiseEntityList);
		return advertiseList;

	}


	@Override
	public Advertise updateAdvertiseById(String authToken,int advertiseId, Advertise newAdvertise) {
		if (loginDelegate.validateToken(authToken)) {
			String userName = loginDelegate.getUserInfo(authToken);
			List<Category> categoryList = masterDataDelegate.getAllCategories();
			List<Status> advStatusList = masterDataDelegate.getAllStatus();
			int categoryId = newAdvertise.getCategory();
			String categoryName = utility.getCategoryNameFromList(categoryList, categoryId);
			int statusId = newAdvertise.getStatus();
			String status = utility.getAdvertiseStatusFromList(advStatusList, statusId);

			newAdvertise.setUserName(userName);
			newAdvertise.setCategory(categoryId);
			newAdvertise.setStatus(statusId);
			newAdvertise.setModifiedDate(LocalDate.now());
			newAdvertise.setActive(1);
			newAdvertise.setId(advertiseId);

			AdvertisesEntity advEntity = utility.convertAdvertiseDtoToEntity(newAdvertise);
			adRepo.save(advEntity);

			return newAdvertise;

		} else {

			throw new InvalidAuthTokenException("Invalid Auth token");
		}
	}

	@Override
	public Advertise createNewAdvertise(String authToken,Advertise advertise) {
		if (loginDelegate.validateToken(authToken)) {
			String userName = loginDelegate.getUserInfo(authToken);
			List<Category> catList = masterDataDelegate.getAllCategories();
			List<Status> advStatusList = masterDataDelegate.getAllStatus();
			int categoryId = advertise.getCategory();
			String catName = utility.getCategoryNameFromList(catList, categoryId);
			int statusId = advertise.getStatus();
			String status = utility.getAdvertiseStatusFromList(advStatusList, statusId);
			advertise.setUserName(userName);
			advertise.setCategory(categoryId);
			advertise.setStatus(statusId);
			advertise.setCreatedDate(LocalDate.now());
			advertise.setModifiedDate(LocalDate.now());
			advertise.setActive(1);

			AdvertisesEntity advEntity = utility.convertAdvertiseDtoToEntity(advertise);
			adRepo.save(advEntity);
			advertise.setId(advEntity.getId());
			return advertise;

		} else {

			throw new InvalidAuthTokenException("Invalid Auth token");
		}
	}

	@Override
	public boolean deleteAdvertiseById(String authToken,int id) {
		if (loginDelegate.validateToken(authToken)) {

			Optional<AdvertisesEntity> optionalEntity = adRepo.findById(id);
			if (optionalEntity.isPresent()) {
				AdvertisesEntity entity = optionalEntity.get();
				adRepo.delete(entity);
				return true;
			} else {
				throw new AdNotFoundException();
			}

		}

		else {
			throw new InvalidAuthTokenException("Invalid Auth token");

		}
	}

	@Override
	public Advertise getAdvertisementById(String authToken,int advertiseId) {
		if (loginDelegate.validateToken(authToken)) {

			String userName = loginDelegate.getUserInfo(authToken);

			Optional<AdvertisesEntity> optionalEntity = adRepo.findByUsernameAndId(userName,
					advertiseId);
			if (optionalEntity.isPresent()) {
				AdvertisesEntity advEntity = optionalEntity.get();
				List<Category> categoryList = masterDataDelegate.getAllCategories();
				List<Status> advStatusList = masterDataDelegate.getAllStatus();
				int categoryId = advEntity.getCategory();
				String categoryName = utility.getCategoryNameFromList(categoryList, categoryId);

				int statusId = advEntity.getStatus();
				String status = utility.getAdvertiseStatusFromList(advStatusList, statusId);

				Advertise advertise = convertToDto(advEntity);
				advertise.setCategory(categoryId);
				advertise.setStatus(statusId);

				return advertise;
			} else {
				throw new AdNotFoundException();
			}

		}

		else {
			throw new InvalidAuthTokenException("Invalid Auth token");

		}
	}

	@Override
	public List<Advertise> getUserAdvertise(String authToken) {
		if (loginDelegate.validateToken(authToken)) {

			String userName = loginDelegate.getUserInfo(authToken);

			List<AdvertisesEntity> advertisesEntityList = adRepo.findByUsername(userName);
			List<Advertise> advertiseList = getListFromEntities(advertisesEntityList);
			return advertiseList;
		} else {
			throw new InvalidAuthTokenException("Invalid Auth token");

		}
	}
	
	
	@Override
	public List<Advertise> searchAdvertisementByFilter(String searchText, Integer categoryId, String postedBy,
			String dateCondition, LocalDate onDate, LocalDate fromDate, LocalDate toDate, String sortBy,
			Integer startIndex, Integer numOfRecords) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdvertisesEntity> criteriaQuery = criteriaBuilder.createQuery(AdvertisesEntity.class);
		Root<AdvertisesEntity> rootEntity = criteriaQuery.from(AdvertisesEntity.class);
		// criteriaQuery.select(rootEntity);
		List<Predicate> predicateList = new ArrayList<>();
		if (searchText != null && !searchText.isEmpty()) {
			Predicate titlePredicate = criteriaBuilder.like(rootEntity.get("title"), "%" + searchText + "%");
			predicateList.add(titlePredicate);
		}
		if (postedBy != null && postedBy.isEmpty()) {
			Predicate postedByPredicate = criteriaBuilder.equal(rootEntity.get("postedBy"), postedBy);
			predicateList.add(postedByPredicate);
		}

		if (categoryId != null) {
			Predicate categoryPredicate = criteriaBuilder.equal(rootEntity.get("category"), categoryId);
			predicateList.add(categoryPredicate);
		}

		// Predicate dateConditionPredicate = null;
		if (dateCondition != null) {
			switch (dateCondition) {
			case Constants.EQUAL:
				Predicate dateConditionPredicate1 = criteriaBuilder.equal(rootEntity.get("createdDate"), onDate);
				predicateList.add(dateConditionPredicate1);
				break;

			case Constants.GREATERTHAN:
				Predicate dateConditionPredicate2 = criteriaBuilder.greaterThan(rootEntity.get("createdDate"),
						fromDate);
				predicateList.add(dateConditionPredicate2);
				break;

			case Constants.LESSTHAN:
				Predicate dateConditionPredicate3 = criteriaBuilder.lessThan(rootEntity.get("createdDate"), fromDate); //
				predicateList.add(dateConditionPredicate3);
				break;

			case Constants.BETWEEN:
				Predicate dateConditionPredicate4 = criteriaBuilder.between(rootEntity.get("createdDate"), fromDate,
						toDate); //
				predicateList.add(dateConditionPredicate4);
				break;

			default:
				break;
			}

		}

		Predicate[] arr = new Predicate[predicateList.size()];
		criteriaQuery.where(predicateList.toArray(arr));
		if (sortBy != null && sortBy.equalsIgnoreCase("ASC")) {
			criteriaQuery.orderBy(criteriaBuilder.asc(rootEntity.get("createdDate")));
		}
		if (sortBy != null && sortBy.equalsIgnoreCase("DESC")) {
			criteriaQuery.orderBy(criteriaBuilder.desc(rootEntity.get("createdDate")));
		}

		TypedQuery<AdvertisesEntity> query = entityManager.createQuery(criteriaQuery);

		if (startIndex != null && numOfRecords != null) {
			query.setFirstResult(startIndex);
			query.setMaxResults(numOfRecords);
		}
		List<AdvertisesEntity> advertiseEntityList = query.getResultList();
		List<Advertise> advertiseList = getListFromEntities(advertiseEntityList);
		return advertiseList;

	}
	
	private Advertise convertToDto(AdvertisesEntity advertisesEntity) {
		Advertise advDto = mapper.map(advertisesEntity, Advertise.class);
		return advDto;
	}

	
	private List<Advertise> getListFromEntities(List<AdvertisesEntity> advertisesEntitiesList) {

		List<Advertise> adList = new ArrayList<Advertise>();

		List<Category> catList = masterDataDelegate.getAllCategories();
		List<Status> advStatusList = masterDataDelegate.getAllStatus();
		for (AdvertisesEntity advEntity : advertisesEntitiesList) {
			int catId = advEntity.getCategory();
			String catName = utility.getCategoryNameFromList(catList, catId);
			int statusId = advEntity.getStatus();
			String status = utility.getAdvertiseStatusFromList(advStatusList, statusId);
			Advertise advertise = mapper.map(advEntity, Advertise.class);
			advertise.setCategory(catId);
			advertise.setActive(advEntity.getStatus());
			advertise.setStatus(statusId);
			adList.add(advertise);
		}
		return adList;
	}



}
