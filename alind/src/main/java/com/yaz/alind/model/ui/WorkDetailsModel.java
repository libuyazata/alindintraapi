package com.yaz.alind.model.ui;


public class WorkDetailsModel {
	
	private int workDetailsId;

	private String workName;

	private int workTypeId;

	// Project, Order etc
	private String workType;

	private String description;

	private int departmentId;

	private String departmentName;

	private int projectCoOrdinatorEmpId;

	private String projectCoOrdinatorCode;
	
	private String projectCoOrdinatorName;

	//On going,Short closed,Completed
	private int workStatusId;

	private String workStatusName;
	
	private String startDate;

	private String endDate;

	private String createdOn;

	private String updatedOn;

	private int createdEmpId;

	private String createdEmpCode;
	
	private String createdEmpName;
	
	private int status;

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

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getProjectCoOrdinatorEmpId() {
		return projectCoOrdinatorEmpId;
	}

	public void setProjectCoOrdinatorEmpId(int projectCoOrdinatorEmpId) {
		this.projectCoOrdinatorEmpId = projectCoOrdinatorEmpId;
	}

	public String getProjectCoOrdinatorName() {
		return projectCoOrdinatorName;
	}

	public void setProjectCoOrdinatorName(String projectCoOrdinatorName) {
		this.projectCoOrdinatorName = projectCoOrdinatorName;
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

	public void setWorkStatusName(String taskStatusName) {
		this.workStatusName = taskStatusName;
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

	public int getCreatedEmpId() {
		return createdEmpId;
	}

	public void setCreatedEmpId(int createdEmpId) {
		this.createdEmpId = createdEmpId;
	}

	public String getCreatedEmpName() {
		return createdEmpName;
	}

	public void setCreatedEmpName(String createdEmpName) {
		this.createdEmpName = createdEmpName;
	}

	public String getProjectCoOrdinatorCode() {
		return projectCoOrdinatorCode;
	}

	public void setProjectCoOrdinatorCode(String projectCoOrdinatorCode) {
		this.projectCoOrdinatorCode = projectCoOrdinatorCode;
	}

	public String getCreatedEmpCode() {
		return createdEmpCode;
	}

	public void setCreatedEmpCode(String createdEmpCode) {
		this.createdEmpCode = createdEmpCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
