package com.yaz.alind.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 
 * @author Libu Mathew
 * Master table,describes the project status (On going,Short closed,Completed etc)
 *
 */

@Entity
@Table(name="alind_t_work_status")
public class WorkStatusEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "work_status_id", unique = true, nullable = false)
	private int workStatusId;
	
	//On going,Short closed,Completed
	@Column(name = "work_status_name")
	private String workStatusName;
	
	@Column(name = "description")
	private String description;
	
	// active - 1 or inactive = -1
	@Column(name = "status")
	private int status;
	
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	public int getTaskStatusId() {
		return workStatusId;
	}

	public void setTaskStatusId(int taskStatusId) {
		this.workStatusId = taskStatusId;
	}

	public String getTaskStatusName() {
		return workStatusName;
	}

	public void setTaskStatusName(String taskStatusName) {
		this.workStatusName = taskStatusName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(int workStatusId) {
		this.workStatusId = workStatusId;
	}

	public String getWorkStatusName() {
		return workStatusName;
	}

	public void setWorkStatusName(String workStatusName) {
		this.workStatusName = workStatusName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
