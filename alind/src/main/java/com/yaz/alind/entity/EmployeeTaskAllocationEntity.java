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
@Table(name="alind_t_employee_task_allocation")
public class EmployeeTaskAllocationEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "emp_task_allocation_id", unique = true, nullable = false)
	private int empTaskAllocationId;
	
	@Column(name = "work_details_id")
	private int workDetailsId;
	
	@ManyToOne
	@JoinColumn(name="work_details_id",insertable = false, updatable = false)
	private WorkDetailsEntity workDetailsEntity;
	
	@ManyToOne
	@JoinColumn(name="sub_task_id",insertable = false, updatable = false)
	private SubTaskEntity subTaskEntity;
	
	@Column(name = "sub_task_id")
	private int subTaskId;
	
	@Column(name="employee_id")
	private int employeeId;

	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity employeeEntity;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;
	
	public int getEmpTaskAllocationId() {
		return empTaskAllocationId;
	}

	public void setEmpTaskAllocationId(int empTaskAllocationId) {
		this.empTaskAllocationId = empTaskAllocationId;
	}


	public int getSubTaskId() {
		return subTaskId;
	}

	public void setSubTaskId(int subTaskId) {
		this.subTaskId = subTaskId;
	}


	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getWorkDetailsId() {
		return workDetailsId;
	}

	public void setWorkDetailsId(int workDetailsId) {
		this.workDetailsId = workDetailsId;
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

}
