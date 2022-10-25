package com.yaz.alind.model.ui;

import java.util.List;



public class InterOfficeCommunicationModel {

	private int officeCommunicationId;
	// From
	private int employeeId;

	private String employeeName;

	private String empCode;

	//To
	private int departmentId;

	private String departmentName;
	
	private int workDetailsId;
	
	private String workName;
	
	private int subTaskId;
	
	private String subTaskName;

	private String referenceNo;
	
	//PS/ANX/09
	private String annexureFormat;
	
	private String subject;

	private String description;
	
	private int attachementStatus;
	
//	private String fileType;
	
//	private String orginalFileName;
	
	private List<WorkMessageAttachmentModel> attachmentModels;
	

	public int getAttachementStatus() {
		return attachementStatus;
	}

	public void setAttachementStatus(int attachementStatus) {
		this.attachementStatus = attachementStatus;
	}

	private String createdOn;

	private String updatedOn;
	private int isActive;
	
	private List<DepartmentCommunicationMessagesModel> deptCommList;

	public int getOfficeCommunicationId() {
		return officeCommunicationId;
	}

	public void setOfficeCommunicationId(int officeCommunicationId) {
		this.officeCommunicationId = officeCommunicationId;
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

	public int getSubTaskId() {
		return subTaskId;
	}

	public void setSubTaskId(int subTaskId) {
		this.subTaskId = subTaskId;
	}

	public String getSubTaskName() {
		return subTaskName;
	}

	public void setSubTaskName(String subTaskName) {
		this.subTaskName = subTaskName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<DepartmentCommunicationMessagesModel> getDeptCommList() {
		return deptCommList;
	}

	public void setDeptCommList(
			List<DepartmentCommunicationMessagesModel> deptCommList) {
		this.deptCommList = deptCommList;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

//	public String getFileType() {
//		return fileType;
//	}
//
//	public void setFileType(String fileType) {
//		this.fileType = fileType;
//	}
//
//	public String getOrginalFileName() {
//		return orginalFileName;
//	}
//
//	public void setOrginalFileName(String orginalFileName) {
//		this.orginalFileName = orginalFileName;
//	}

	public List<WorkMessageAttachmentModel> getAttachmentModels() {
		return attachmentModels;
	}

	public void setAttachmentModels(
			List<WorkMessageAttachmentModel> attachmentModels) {
		this.attachmentModels = attachmentModels;
	}
	
}
