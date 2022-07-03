package com.yaz.alind.model.ui;


public class WorkIssuedModel {

	private int workIssuedId;

	private int workDetailsId;

	private String workName;

	//On going,Short closed,Completed
	private int workStatusId;

	private String workStatusName;

	private int departmentId;

	private String departmentName;

	private String createdOn;

	private String updatedOn;

	private int createdEmpId;

	private String createdEmpCode;

	private String createdEmpName;

	private int status;

	public int getWorkIssuedId() {
		return workIssuedId;
	}

	public void setWorkIssuedId(int workIssuedId) {
		this.workIssuedId = workIssuedId;
	}

	public int getWorkDetailsId() {
		return workDetailsId;
	}

	public void setWorkDetailsId(int workDetailsId) {
		this.workDetailsId = workDetailsId;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
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
	
}
