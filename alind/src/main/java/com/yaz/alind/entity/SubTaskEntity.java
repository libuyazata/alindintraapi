package com.yaz.alind.entity;

import java.sql.Timestamp;
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
@Table(name="alind_t_sub_task")
public class SubTaskEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "sub_task_id", unique = true, nullable = false)
	private int subTaskId;

	@Column(name = "work_details_id")
	private int workDetailsId;

	@ManyToOne
	@JoinColumn(name="work_details_id",insertable = false, updatable = false)
	private WorkDetailsEntity workDetailsEntity;

	@Column(name = "sub_task_name")
	private String subTaskName;

	@Column(name = "description")
	private String description;

	@Column(name = "start_date")
	private Timestamp startDate;

	@Column(name = "end_date")
//	private Date endDate;
	private Timestamp endDate;

	//On going,Short closed,Completed
	@Column(name = "work_status_id")
	private int workStatusId;
	
	@ManyToOne
	@JoinColumn(name="work_status_id",insertable = false, updatable = false)
	private WorkStatusEntity workStatusEntity;
	
	@Column(name="employee_id")
	private int createdEmpId;

	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity createdEmp;

	@Column(name = "created_on")
//	private Date createdOn;
	private Timestamp createdOn;

	@Column(name = "updated_on")
	//private Date updatedOn;
	private Timestamp updatedOn;
	
	@Column(name = "status")
	private int status;

	public int getSubTaskId() {
		return subTaskId;
	}

	public void setSubTaskId(int subTaskId) {
		this.subTaskId = subTaskId;
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

	public String getSubTaskName() {
		return subTaskName;
	}

	public void setSubTaskName(String subTaskName) {
		this.subTaskName = subTaskName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public Date getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//
//	public Date getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}

	public int getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(int workStatusId) {
		this.workStatusId = workStatusId;
	}

	public WorkStatusEntity getWorkStatusEntity() {
		return workStatusEntity;
	}

	public void setWorkStatusEntity(WorkStatusEntity workStatusEntity) {
		this.workStatusEntity = workStatusEntity;
	}

	public EmployeeEntity getCreatedEmp() {
		return createdEmp;
	}

	public void setCreatedEmp(EmployeeEntity createdEmp) {
		this.createdEmp = createdEmp;
	}

//	public Date getCreatedOn() {
//		return createdOn;
//	}
//
//	public void setCreatedOn(Date createdOn) {
//		this.createdOn = createdOn;
//	}
//
//	public Date getUpdatedOn() {
//		return updatedOn;
//	}
//
//	public void setUpdatedOn(Date updatedOn) {
//		this.updatedOn = updatedOn;
//	}
	
	

	public int getStatus() {
		return status;
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

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCreatedEmpId() {
		return createdEmpId;
	}

	public void setCreatedEmpId(int createdEmpId) {
		this.createdEmpId = createdEmpId;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	
}
