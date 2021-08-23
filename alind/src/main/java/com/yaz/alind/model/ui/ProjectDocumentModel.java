package com.yaz.alind.model.ui;


public class ProjectDocumentModel {
	
	private int projectDocumentId;
	
	private int documentTypeId;
	
	// Remarks
	private String description;
	
	private int departmentId;
	
	private String departmentName;
	
	private String filePath;
	
	private String fileName;
	
	private float fileSize;
	
	private String fileType;
	
	private String createdOn;
	
	private String modifiedOn;
	
	private int projectId;
	
	private String originalFileName;
	
	private String fileLocation;
	
	private int slNo;
	
	
	/**
	 *  0-Draft
	 *  1-Progress
	 *  2-Closed
	 */
	private int status;
	
	private int employeeId;
	
	private String empCode;
	
	private String firstName;
	
	private String lastName;
	
	private String documentType;
	
	private String documentName;
	
	private String documentnumber;

	public int getProjectDocumentId() {
		return projectDocumentId;
	}

	public void setProjectDocumentId(int projectDocumentId) {
		this.projectDocumentId = projectDocumentId;
	}

	public int getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(int documentTypeId) {
		this.documentTypeId = documentTypeId;
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

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public int getSlNo() {
		return slNo;
	}

	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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

}
