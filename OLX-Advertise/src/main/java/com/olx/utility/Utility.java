package com.olx.utility;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import com.olx.dto.Advertise;
import com.olx.dto.Category;
import com.olx.dto.Status;
import com.olx.entity.AdvertisesEntity;

public class Utility {

	@Autowired

	ModelMapper modelMapper;

	@Autowired
	LocalDateAttributeConverter dateConverter;

	public String getCategoryNameFromList(List<Category> list, int id) {
		for (Category category : list) {
			if (category.getId() == id) {
				return category.getCategory();
			}
		}
		return "";
	}

	public String getAdvertiseStatusFromList(List<Status> list, int id) {
		for (Status status : list) {
			if (status.getId() == id) {
				return status.getStatus();
			}
		}
		return "";
	}

	public AdvertisesEntity convertAdvertiseDtoToEntity(Advertise dto) {
		TypeMap<Advertise, AdvertisesEntity> typeMap = this.modelMapper.typeMap(Advertise.class,
				AdvertisesEntity.class);
		typeMap.addMappings(mapper -> {
			mapper.map(source -> source.getCategory(), AdvertisesEntity::setCategory);
			mapper.map(source -> source.getStatus(), AdvertisesEntity::setStatus);
			mapper.map(source -> source.getUserName(), AdvertisesEntity::setPostedBy);

		});
		AdvertisesEntity advertisesEntity = this.modelMapper.map(dto, AdvertisesEntity.class);
		return advertisesEntity;

	}

}
