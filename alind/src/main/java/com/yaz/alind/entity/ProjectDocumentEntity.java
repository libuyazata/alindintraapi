package com.yaz.alind.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;


@Entity
@Table(name="alind_t_project_document")
public class ProjectDocumentEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "project_document_id", unique = true, nullable = false)
	private int projectDocumentId;
	@Column(name = "document_type_id")
	private int documentTypeId;
	
	// Remarks
	@Column(name = "description")
	private String description;
	@Column(name = "department_id")
	private int departmentId;
	
	@ManyToOne
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity department;
	
	@Column(name = "file_path")
	private String filePath;
	@Column(name = "file_name")
	private String fileName;
	@Column(name = "file_size")
	private float fileSize;
	@Column(name = "file_type")
	private String fileType;
	@Column(name = "created_on")
	private Date createdOn;
	@Column(name = "modified_on")
	private Date modifiedOn;
	@Column(name="project_id")
	private int projectId;
	@Column(name = "original_file_name")
	private String originalFileName;
	
	
	
//	@ManyToOne
//	@JoinColumn(name="project_id",insertable = false, updatable = false)
//	private ProjectInfo projectInfo;
	
	@Transient
	private String fileLocation;
	@Transient
	private int slNo;
	
	
	/**
	 *  0-Draft
	 *  1-Progress
	 *  2-Closed
	 */
	@Column(name = "status")
	private int status;
	
	@Column(name="employee_id")
	private int employeeId;
	
	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity employee;
	
	@ManyToOne
	@JoinColumn(name="document_type_id",insertable = false, updatable = false)
	private DocumentTypesEntity documentTypes;
	
	
	@Column(name = "document_name")
	private String documentName;
	
	@Column(name = "document_number")
	private String documentnumber;
	
	public String getDocumentnumber() {
		return documentnumber;
	}
	public void setDocumentnumber(String documentnumber) {
		this.documentnumber = documentnumber;
	}
	@Transient
	private MultipartFile file;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	public int getDocumentTypeId() {
		return documentTypeId;
	}
	public void setDocumentTypeId(int documentTypeId) {
		this.documentTypeId = documentTypeId;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public DepartmentEntity getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public DocumentTypesEntity getDocumentTypes() {
		return documentTypes;
	}
	public void setDocumentTypes(DocumentTypesEntity documentTypes) {
		this.documentTypes = documentTypes;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public EmployeeEntity getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeEntity employee) {
		this.employee = employee;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
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
	public int getProjectDocumentId() {
		return projectDocumentId;
	}
	public void setProjectDocumentId(int projectDocumentId) {
		this.projectDocumentId = projectDocumentId;
	}
	public float getFileSize() {
		return fileSize;
	}
	public void setFileSize(float fileSize) {
		this.fileSize = fileSize;
	}
	public int getSlNo() {
		return slNo;
	}
	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}
	
}
