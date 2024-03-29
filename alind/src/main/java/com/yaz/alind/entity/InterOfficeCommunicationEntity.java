package com.yaz.alind.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="alind_t_inter_office_communication")
public class InterOfficeCommunicationEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "office_communication_id", unique = true, nullable = false)
	private int officeCommunicationId;
	// From
	@Column(name="employee_id")
	private int employeeId;

	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity employee;
	
	@Column(name = "department_id", nullable = false )
	private int departmentId;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity department;

	@Column(name = "work_details_id", nullable = false)
	private int workDetailsId;
	
	@ManyToOne
	@JoinColumn(name="work_details_id",insertable = false, updatable = false)
	private WorkDetailsEntity workDetailsEntity;

	@Column(name = "sub_task_id", nullable = false)
	private int subTaskId;
	
	@ManyToOne
	@JoinColumn(name="sub_task_id",insertable = false, updatable = false)
	private SubTaskEntity subTaskEntity;
	
	@Column(name = "subject")
	private String subject;

	@Column(name = "reference_no", nullable = false)
	private String referenceNo;

	@Column(name = "description")
	private String description;
	
//	@Column(name = "file_type")
//	private String fileType;
	
//	@Column(name = "orginal_file_name")
//	private String orginalFileName;
	
	/**
	 *  0 -> No attachment
	 *  1 -> File attached
	 */
	@Column(name = "attachement_status",columnDefinition="Int default '0'")
	private int attachementStatus;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_on")
	private Timestamp updatedOn;
	
	public int getAttachementStatus() {
		return attachementStatus;
	}

	public void setAttachementStatus(int attachementStatus) {
		this.attachementStatus = attachementStatus;
	}

	@Column(name="is_active")
	private int isActive;

	public int getOfficeCommunicationId() {
		return officeCommunicationId;
	}

	public void setOfficeCommunicationId(int officeCommunicationId) {
		this.officeCommunicationId = officeCommunicationId;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
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

	public EmployeeEntity getEmployee() {
		return employee;
	}

	public void setEmployeeTypes(EmployeeEntity employee) {
		this.employee = employee;
	}

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}

	public WorkDetailsEntity getWorkDetailsEntity() {
		return workDetailsEntity;
	}

	public void setWorkDetailsEntity(WorkDetailsEntity workDetailsEntity) {
		this.workDetailsEntity = workDetailsEntity;
	}

	public SubTaskEntity getSubTaskEntity() {
		return subTaskEntity;
	}

	public void setSubTaskEntity(SubTaskEntity subTaskEntity) {
		this.subTaskEntity = subTaskEntity;
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

	
}
