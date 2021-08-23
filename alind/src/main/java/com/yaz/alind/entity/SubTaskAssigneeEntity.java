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
@Table(name="alind_t_sub_task_assignees")
public class SubTaskAssigneeEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "sub_task_assignee_id", unique = true, nullable = false)
	public int subTaskAssigneeId;
	
	@Column(name="employee_id")
	private int employeeId; 
	
	@ManyToOne
	@JoinColumn(name="employeeId",insertable = false, updatable = false)
	private EmployeeEntity employeeEntity;
	
	@Column(name = "work_details_id")
	private int workDetailsId;
	
	@ManyToOne
	@JoinColumn(name="work_details_id",insertable = false, updatable = false)
	private WorkDetailsEntity workDetailsEntity;
	
	@Column(name = "sub_task_id")
	private int subTaskId;
	
	@ManyToOne
	@JoinColumn(name="work_details_id",insertable = false, updatable = false)
	private SubTaskEntity subTaskEntity;
	
	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	public int getSubTaskAssigneeId() {
		return subTaskAssigneeId;
	}

	public void setSubTaskAssigneeId(int subTaskAssigneeId) {
		this.subTaskAssigneeId = subTaskAssigneeId;
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

	public int getSubTaskId() {
		return subTaskId;
	}

	public void setSubTaskId(int subTaskId) {
		this.subTaskId = subTaskId;
	}

	public SubTaskEntity getSubTaskEntity() {
		return subTaskEntity;
	}

	public void setSubTaskEntity(SubTaskEntity subTaskEntity) {
		this.subTaskEntity = subTaskEntity;
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

}
