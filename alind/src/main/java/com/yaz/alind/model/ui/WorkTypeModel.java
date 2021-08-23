package com.yaz.alind.model.ui;

import javax.persistence.Column;

public class WorkTypeModel {
	
	private int workTypeId;

	// Project, Order etc
	private String workType;

	@Column(name = "description")
	private String description;

	private String createdOn;

	private String updatedOn;

	// active - 1 or inactive = -1
	private int status;

	public int getWorkTypeId() {
		return workTypeId;
	}

	public void setWorkTypeId(int workTypeId) {
		this.workTypeId = workTypeId;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
