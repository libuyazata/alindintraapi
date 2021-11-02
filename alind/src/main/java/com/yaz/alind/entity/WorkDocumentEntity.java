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
@Entity
@Table(name="alind_t_work_document")
public class WorkDocumentEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "work_document_id", unique = true, nullable = false)
	private int workDocumentId;
	
	
	@Column(name = "document_category_id")
	private int documentCategoryId;
	
	@ManyToOne
	@JoinColumn(name="document_category_id",insertable = false, updatable = false)
	private DocumentCategoryEntity documentCategory;
	
	@Column(name = "document_type_id")
	private int documentTypeId;
	
	@ManyToOne
	@JoinColumn(name="document_type_id",insertable = false, updatable = false)
	private DocumentTypeEntity documentTypeEntity;
	
	@Column(name = "work_details_id")
	private int workDetailsId;
	
	@ManyToOne
	@JoinColumn(name="work_details_id",insertable = false, updatable = false)
	private WorkDetailsEntity workDetails;
	
	@Column(name = "sub_task_id")
	private int subTaskId;
	
	@ManyToOne
	@JoinColumn(name="sub_task_id",insertable = false, updatable = false)
	private SubTaskEntity subTask;
	
	@Column(name="employee_id")
	private int employeeId;
	
	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity emplpoyee;

	// Remarks
	@Column(name = "description")
	private String description;
	
	@Column(name = "department_id")
	private int departmentId;
	
	@ManyToOne
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity department;
	
	@Column(name = "document_name")
	private String documentName;
	
	@Column(name = "document_number")
	private String documentnumber;
	
	@Column(name = "file_path")
	private String filePath;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "original_file_name")
	private String originalFileName;
	
	@Column(name = "file_size")
	private float fileSize;
	
	@Column(name = "file_type")
	private String fileType;
	
	// 1 -> verification , 0 - not verification 
	@Column(name = "verification_status")
	private int verificationStatus;
	// 1 -> approved , 0 - not approved 
	@Column(name = "approval_status")
	private int approvalStatus;
	
	// 1 -> Active, 0-> Deleted
	@Column(name = "status")
	private int status;
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;
	
	
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
	public int getWorkDetailsId() {
		return workDetailsId;
	}
	public void setWorkDetailsId(int workDetailsId) {
		this.workDetailsId = workDetailsId;
	}
	public int getSubTaskId() {
		return subTaskId;
	}
	public void setSubTaskId(int subTaskId) {
		this.subTaskId = subTaskId;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	public int getVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(int verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	public int getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(int approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public DocumentCategoryEntity getdocumentCategory() {
		return documentCategory;
	}
	public void setdocumentCategory(DocumentCategoryEntity documentCategory) {
		this.documentCategory = documentCategory;
	}
	public WorkDetailsEntity getWorkDetails() {
		return workDetails;
	}
	public void setWorkDetails(WorkDetailsEntity workDetails) {
		this.workDetails = workDetails;
	}
	public SubTaskEntity getSubTask() {
		return subTask;
	}
	public void setSubTask(SubTaskEntity subTask) {
		this.subTask = subTask;
	}
	public DepartmentEntity getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentEntity department) {
		this.department = department;
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
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public EmployeeEntity getEmplpoyee() {
		return emplpoyee;
	}
	public void setEmplpoyee(EmployeeEntity emplpoyee) {
		this.emplpoyee = emplpoyee;
	}
	public int getDocumentCategoryId() {
		return documentCategoryId;
	}
	public void setDocumentCategoryId(int documentCategoryId) {
		this.documentCategoryId = documentCategoryId;
	}
	public DocumentTypeEntity getDocumentTypeEntity() {
		return documentTypeEntity;
	}
	public void setDocumentTypeEntity(DocumentTypeEntity documentTypeEntity) {
		this.documentTypeEntity = documentTypeEntity;
	}
	
}
