package com.olx.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.Category;
import com.olx.dto.Status;
import com.olx.entity.CategoryEntity;
import com.olx.entity.StatusEntity;
import com.olx.repository.CategoryRepository;
import com.olx.repository.StatusRepository;

@Service 	
public class MasterDataServiceImpl implements MasterDataService{

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	StatusRepository advStatusRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<Category> getAllCategory() {
		List<CategoryEntity> categoriesEntities = categoryRepository.findAll();
		return convertCatgeoryEntityListToDtoList(categoriesEntities);
	
	}

	@Override
	public List<Status> getAllStatus() {
		List<StatusEntity> advStatusList = advStatusRepository.findAll();
		return convertAdvStatusEntityListToDtoList(advStatusList);
	}
	
	private List<Category> convertCatgeoryEntityListToDtoList(List<CategoryEntity> categoriesEntities) {
		List<Category> categoriesList = new ArrayList<>();

		for (CategoryEntity catEntity : categoriesEntities) {
			Category cat = modelMapper.map(catEntity, Category.class);
			cat.setCategory(catEntity.getName());
			categoriesList.add(cat);
		}
		return categoriesList;
	}

	private List<Status> convertAdvStatusEntityListToDtoList(List<StatusEntity> advoStatusEntities) {
		List<Status> statusList = new ArrayList<>();
		for (StatusEntity advEntity : advoStatusEntities) {
			statusList.add(modelMapper.map(advEntity, Status.class));
		}
		return statusList;
	}

}
