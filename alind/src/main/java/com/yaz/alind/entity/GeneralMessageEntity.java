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
@Table(name="alind_t_gen_message")
public class GeneralMessageEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "gen_msg_id", unique = true, nullable = false)
	private int genMessageId;

	// From
	@Column(name="employee_id", nullable = false)
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

	
	@Column(name = "subject", nullable = false)
	private String subject;

	@Column(name = "reference_no", nullable = false)
	private String referenceNo;

	@Column(name = "description", nullable = false, length=2000)
	private String description;
	
	
	
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

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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


	public int getAttachementStatus() {
		return attachementStatus;
	}

	public void setAttachementStatus(int attachementStatus) {
		this.attachementStatus = attachementStatus;
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

}
