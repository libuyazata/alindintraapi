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


@Entity
@Table(name="alind_t_document_users")
public class DocumentUsersEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "document_user_id", unique = true, nullable = false)
	private int documentUserId;
	
	@Column(name="employee_id")
	private int employeeId;
	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity employee;
	
	
	@Column(name = "project_document_id")
	private int projectDocumentId;
	@ManyToOne
	@JoinColumn(name="project_document_id",insertable = false, updatable = false)
	private ProjectDocumentEntity projectDocument;
	
	@Column(name = "read_permission")
	private int readPermission;
	@Column(name = "write_permission")
	private int writePermission;
	@Column(name = "created_on")
	private Date createdOn;
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")
	private Date endDate;
	
	@Transient
	private int slNo;
	
	
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	public int getDocumentUserId() {
		return documentUserId;
	}
	public void setDocumentUserId(int documentUserId) {
		this.documentUserId = documentUserId;
	}
	
	public int getReadPermission() {
		return readPermission;
	}
	public void setReadPermission(int readPermission) {
		this.readPermission = readPermission;
	}
	public int getWritePermission() {
		return writePermission;
	}
	public void setWritePermission(int writePermission) {
		this.writePermission = writePermission;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public EmployeeEntity getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeEntity employee) {
		this.employee = employee;
	}
	public int getProjectDocumentId() {
		return projectDocumentId;
	}
	public void setProjectDocumentId(int projectDocumentId) {
		this.projectDocumentId = projectDocumentId;
	}
	public ProjectDocumentEntity getProjectDocument() {
		return projectDocument;
	}
	public void setProjectDocument(ProjectDocumentEntity projectDocument) {
		this.projectDocument = projectDocument;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getSlNo() {
		return slNo;
	}
	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}
	
	

}
