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
@Table(name="alind_t_deputation")
public class DeputationEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "deputation_id", unique = true, nullable = false)
	private int deputationId;
	
	@Column(name="employee_id")
	private int employeeId;
	
	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity employee;
	
	@Column(name = "department_id")
	private int departmentId;

	@ManyToOne
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity deputedDepartment;
	
	@Column(name = "description")
	private String description;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;	
	
//	@Column(name="employee_id")
//	private int assignedBy;
//	
//	@ManyToOne
//	@JoinColumn(name="employee_id",insertable = false, updatable = false)
//	private EmployeeEntity assignedByemployee;
	
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;
	
	@Column(name = "status")
	private int status;

	public int getDeputationId() {
		return deputationId;
	}

	public void setDeputationId(int deputationId) {
		this.deputationId = deputationId;
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

	public DepartmentEntity getDeputedDepartment() {
		return deputedDepartment;
	}

	public void setDeputedDepartment(DepartmentEntity deputedDepartment) {
		this.deputedDepartment = deputedDepartment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

//	public int getAssignedBy() {
//		return assignedBy;
//	}
//
//	public void setAssignedBy(int assignedBy) {
//		this.assignedBy = assignedBy;
//	}
//
//	public EmployeeEntity getAssignedByemployee() {
//		return assignedByemployee;
//	}
//
//	public void setAssignedByemployee(EmployeeEntity assignedByemployee) {
//		this.assignedByemployee = assignedByemployee;
//	}

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
