package com.yaz.alind.model.ui;

public class DepartmentGeneralMessageModel {


	public int deptGeneralMsgId;

	private int genMessageId;

	//To
	private int departmentId;

	private String departmentName;

	// Message viewed or not, if its view then viewStatus = 1 otherwise viewStatus = 0
	private int viewStatus;

	/**
	 *  For grouping the messages, based on "referenceNo"
	 *  Only for UI purpose
	 */
	private String referenceNo;

	private String createdOn;

	private String updatedOn;

	private int senderDeptId;

	private String senderDeptName;

	public int getDeptGeneralMsgId() {
		return deptGeneralMsgId;
	}

	public void setDeptGeneralMsgId(int deptGeneralMsgId) {
		this.deptGeneralMsgId = deptGeneralMsgId;
	}

	public int getGenMessageId() {
		return genMessageId;
	}

	public void setGenMessageId(int genMessageId) {
		this.genMessageId = genMessageId;
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
