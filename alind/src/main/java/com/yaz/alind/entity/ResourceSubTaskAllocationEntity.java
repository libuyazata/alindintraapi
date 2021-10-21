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
@Table(name="alind_t_resource_subtask_allocation")
public class ResourceSubTaskAllocationEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sub_task_allocation_id", unique = true, nullable = false)
	private int subTaskAllocationId;
	
	@Column(name="employee_id")
	private int employeeId;
	
	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity employee;
	
	@Column(name = "sub_task_id")
	private int subTaskId;
	
	@ManyToOne
	@JoinColumn(name="sub_task_id",insertable = false, updatable = false)
	private SubTaskEntity subTask;
	
	@Column(name = "work_details_id")
	private int workDetailsId;

	@ManyToOne
	@JoinColumn(name="work_details_id",insertable = false, updatable = false)
	private WorkDetailsEntity workDetailsEntity;
	
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;
	
	@Column(name = "status")
	private int status;

	public int getSubTaskAllocationId() {
		return subTaskAllocationId;
	}

	public void setSubTaskAllocationId(int subTaskAllocationId) {
		this.subTaskAllocationId = subTaskAllocationId;
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

	public int getSubTaskId() {
		return subTaskId;
	}

	public void setSubTaskId(int subTaskId) {
		this.subTaskId = subTaskId;
	}

	public SubTaskEntity getSubTask() {
		return subTask;
	}

	public void setSubTask(SubTaskEntity subTask) {
		this.subTask = subTask;
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
