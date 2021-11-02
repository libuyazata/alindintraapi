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
@Table(name="alind_t_document_history")
public class DocumentHistoryEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "document_history_id", unique = true, nullable = false)
	private int documentHistroyId;
	
	@Column(name = "document_id")
	private int documentId;
	@ManyToOne
	@JoinColumn(name="document_id",insertable = false, updatable = false)
	private ProjectDocumentEntity document;
	
	@Column(name="employee_id")
	private int employeeId;
	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity employee;
	
	@Column(name = "document_type_id")
	private int documentTypeId;
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getDocumentTypeId() {
		return documentTypeId;
	}
	public void setDocumentTypeId(int documentTypeId) {
		this.documentTypeId = documentTypeId;
	}
	@ManyToOne
	@JoinColumn(name="document_type_id",insertable = false, updatable = false)
	private DocumentCategoryEntity documentType;
	
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "department_id")
	private int departmentId;
	@ManyToOne
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity department;
	
	
	@Column(name = "file_path")
	private String filePath;
	@Column(name = "file_size")
	private float fileSize;
	@Column(name = "created_on")
	private Date createdOn;
	
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public int getDocumentHistroyId() {
		return documentHistroyId;
	}
	public void setDocumentHistroyId(int documentHistroyId) {
		this.documentHistroyId = documentHistroyId;
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
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public float getFileSize() {
		return fileSize;
	}
	public void setFileSize(float fileSize) {
		this.fileSize = fileSize;
	}
	public DocumentCategoryEntity getDocumentType() {
		return documentType;
	}
	public void setDocumentType(DocumentCategoryEntity documentType) {
		this.documentType = documentType;
	}
	public DepartmentEntity getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}
	public ProjectDocumentEntity getDocument() {
		return document;
	}
	public void setDocument(ProjectDocumentEntity document) {
		this.document = document;
	}
	public EmployeeEntity getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeEntity employee) {
		this.employee = employee;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
}
