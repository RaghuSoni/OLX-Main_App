package com.olx.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORIES")
public class CategoryEntity {


	@Id
	@GeneratedValue
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String name;

	private String description;

	public CategoryEntity() {
		super();
	}

	public CategoryEntity(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public CategoryEntity(String name, String description) {

		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {
		return "CategoryEntity [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}
