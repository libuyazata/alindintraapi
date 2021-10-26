package com.yaz.alind.model.ui;


public class DeputationModel {

	private int deputationId;

	private int employeeId;

	private String employeeName;
	
	private String empCode;

	private int deputedDepartmentId;

	private String deputedDepartmentName;

	private String description;

	private String startDate;

	private String endDate;	

	private String createdOn;

	private String updatedOn;

	private int status;

	public int getDeputationId() {
		return deputationId;
	}

	public void setDeputationId(int deputationId) {
		this.deputationId = deputationId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public int getDeputedDepartmentId() {
		return deputedDepartmentId;
	}

	public void setDeputedDepartmentId(int deputedDepartmentId) {
		this.deputedDepartmentId = deputedDepartmentId;
	}

	public String getDeputedDepartmentName() {
		return deputedDepartmentName;
	}

	public void setDeputedDepartmentName(String deputedDepartmentName) {
		this.deputedDepartmentName = deputedDepartmentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
