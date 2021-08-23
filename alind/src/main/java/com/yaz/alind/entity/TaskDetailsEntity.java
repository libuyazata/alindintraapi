package com.yaz.alind.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//@Entity
//@Table(name="alind_t_task_details")
public class TaskDetailsEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "task_id", unique = true, nullable = false)
	private int taskId;
	
	@Column(name = "task_name")
	private String taskName;
	
	@Column(name = "work_details_id")
	private int workDetailsId;
	
	@ManyToOne
	@JoinColumn(name="work_details_id",insertable = false, updatable = false)
	private WorkDetailsEntity workDetailsEntity;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name="employee_id")
	private int createdBy;
	
	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity createdEmp;
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;


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

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public EmployeeEntity getCreatedEmp() {
		return createdEmp;
	}

	public void setCreatedEmp(EmployeeEntity createdEmp) {
		this.createdEmp = createdEmp;
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

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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
	
}
