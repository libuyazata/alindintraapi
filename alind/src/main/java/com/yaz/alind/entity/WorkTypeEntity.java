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
 * 
 * Master table
 * It mention, the type of work (Project, Order etc)
 *
 */

@Entity
@Table(name="alind_t_work_types")
public class WorkTypeEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "work_type_id", unique = true, nullable = false)
	private int workTypeId;

	// Project, Order etc
	@Column(name = "work_type")
	private String workType;

	@Column(name = "description")
	private String description;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	// active - 1 or inactive = -1
	@Column(name = "status")
	private int status;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getWorkTypeId() {
		return workTypeId;
	}

	public void setWorkTypeId(int workTypeId) {
		this.workTypeId = workTypeId;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
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
