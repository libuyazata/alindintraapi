package com.yaz.alind.model.ui;


public class SubTaskModel {

	private int subTaskId;

	private int workDetailsId;

	private String workName;

	private String subTaskName;

	private String description;

	//On going,Short closed,Completed
	private int workStatusId;

	private String workStatusName;

	private int createdEmpId;

	private String createdEmpCode;

	private String createdEmpName;
	
	private String startDate;

	private String endDate;

	private String createdOn;

	private String updatedOn;
	
	private int status;

	public int getSubTaskId() {
		return subTaskId;
	}

	public void setSubTaskId(int subTaskId) {
		this.subTaskId = subTaskId;
	}

	public int getWorkDetailsId() {
		return workDetailsId;
	}

	public void setWorkDetailsId(int workDetailsId) {
		this.workDetailsId = workDetailsId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getSubTaskName() {
		return subTaskName;
	}

	public void setSubTaskName(String subTaskName) {
		this.subTaskName = subTaskName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public int getCreatedEmpId() {
		return createdEmpId;
	}

	public void setCreatedEmpId(int createdEmpId) {
		this.createdEmpId = createdEmpId;
	}

	public String getCreatedEmpCode() {
		return createdEmpCode;
	}

	public void setCreatedEmpCode(String createdEmpCode) {
		this.createdEmpCode = createdEmpCode;
	}

	public String getCreatedEmpName() {
		return createdEmpName;
	}

	public void setCreatedEmpName(String createdEmpName) {
		this.createdEmpName = createdEmpName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
