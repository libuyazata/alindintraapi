package com.yaz.alind.model.ui;



public class WorkDocumentModel {
	
	private int workDocumentId;
	
	private int documentTypeId;
	
	private String documentType;
	
	private int workDetailsId;
	
	private String workName;
	
	private int subTaskId;
	
	private String subTaskName;
	
	private int documentCategoryId;
	
	private String documentCategoryType;

	// Remarks
	private String description;
	
	private int departmentId;
	
	private String departmentName;
	
	private int createdEmpId;

	private String createdEmpCode;

	private String createdEmpName;
	
	private String documentName;
	
	private String documentnumber;
	
	private String filePath;
	
	private String fileName;
	
	private String originalFileName;
	
	private float fileSize;
	
	private String fileType;
	
	// 1 -> verified , 0 - not referred 
	private int verificationStatus;
	
	// Yes / No 
	private String verificationType ;
	
	// 1 -> approved , 0 - not approved 
	private int approvalStatus;
	
	// Yes / No 
	private String approvalType;
	
	// 1 -> Active, 0-> Deleted
	private int status;
	
	private String createdOn;
	
	private String updatedOn;

	public int getWorkDocumentId() {
		return workDocumentId;
	}

	public void setWorkDocumentId(int workDocumentId) {
		this.workDocumentId = workDocumentId;
	}

	public int getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(int documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public float getFileSize() {
		return fileSize;
	}

	public void setFileSize(float fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(int verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public String getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}

	public int getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(int approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
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

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentnumber() {
		return documentnumber;
	}

	public void setDocumentnumber(String documentnumber) {
		this.documentnumber = documentnumber;
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

	public int getDocumentCategoryId() {
		return documentCategoryId;
	}

	public void setDocumentCategoryId(int documentCategoryId) {
		this.documentCategoryId = documentCategoryId;
	}

	public String getDocumentCategoryType() {
		return documentCategoryType;
	}

	public void setDocumentCategoryType(String documentCategoryType) {
		this.documentCategoryType = documentCategoryType;
	}
}
