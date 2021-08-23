package com.yaz.alind.model.ui;


public class WorkStatusModel {

	private int workStatusId;

	//On going,Short closed,Completed
	private String workStatusName;

	private String description;

	// active - 1 or inactive = -1
	private int status;

	private String createdOn;

	private String updatedOn;

	public int getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(int workStatusId) {
		this.workStatusId = workStatusId;
	}

	public String getWorkStatusName() {
		return workStatusName;
	}

	public void setWorkStatusName(String workStatusName) {
		this.workStatusName = workStatusName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
	
}
