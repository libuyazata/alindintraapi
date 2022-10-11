package com.yaz.alind.model.ui;

import java.util.List;

public class GeneralMessageModel {
	
	private int genMessageId;
	// From
	private int employeeId;

	private String employeeName;

	private String empCode;

	//To
	private int departmentId;

	private String departmentName;
	
	private String referenceNo;
	
	//PS/ANX/09
	private String annexureFormat;
	
	private String subject;

	private String description;
	
	private int attachementStatus;

	private List<GeneralMessageAttachmentModel> generalMessageAttachmentModels;
	
	private List<DepartmentGeneralMessageModel> departmentGeneralMessageModels;
	
	private String createdOn;

	private String updatedOn;
	

	public int getGenMessageId() {
		return genMessageId;
	}

	public void setGenMessageId(int genMessageId) {
		this.genMessageId = genMessageId;
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

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getAnnexureFormat() {
		return annexureFormat;
	}

	public void setAnnexureFormat(String annexureFormat) {
		this.annexureFormat = annexureFormat;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAttachementStatus() {
		return attachementStatus;
	}

	public void setAttachementStatus(int attachementStatus) {
		this.attachementStatus = attachementStatus;
	}

	public List<GeneralMessageAttachmentModel> getGeneralMessageAttachmentModels() {
		return generalMessageAttachmentModels;
	}

	public void setGeneralMessageAttachmentModels(
			List<GeneralMessageAttachmentModel> generalMessageAttachmentModels) {
		this.generalMessageAttachmentModels = generalMessageAttachmentModels;
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

	public List<DepartmentGeneralMessageModel> getDepartmentGeneralMessageModels() {
		return departmentGeneralMessageModels;
	}

	public void setDepartmentGeneralMessageModels(
			List<DepartmentGeneralMessageModel> departmentGeneralMessageModels) {
		this.departmentGeneralMessageModels = departmentGeneralMessageModels;
	}

}
