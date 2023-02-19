package com.yaz.alind.model.ui;


public class DepartmentCommunicationMessagesModel {


	public int deptCommId;

	private int officeCommunicationId;

	//To
	private int departmentId;

	private String departmentName;

	// Message viewed or not, if its view then viewStatus = 1 otherwise viewStatus = 0
	private int viewStatus;

	private String createdOn;

	private String updatedOn;

	private String referenceNo;

	private int senderDeptId;
	
	private String senderDeptName;

	public int getDeptCommId() {
		return deptCommId;
	}

	public void setDeptCommId(int deptCommId) {
		this.deptCommId = deptCommId;
	}

	public int getOfficeCommunicationId() {
		return officeCommunicationId;
	}

	public void setOfficeCommunicationId(int officeCommunicationId) {
		this.officeCommunicationId = officeCommunicationId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(int viewStatus) {
		this.viewStatus = viewStatus;
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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public int getSenderDeptId() {
		return senderDeptId;
	}

	public void setSenderDeptId(int senderDeptId) {
		this.senderDeptId = senderDeptId;
	}

	public String getSenderDeptName() {
		return senderDeptName;
	}

	public void setSenderDeptName(String senderDeptName) {
		this.senderDeptName = senderDeptName;
	}

}
