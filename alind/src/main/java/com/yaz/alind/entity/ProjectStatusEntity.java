package com.yaz.alind.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  Master Table
 * @author Libu
 *
 */

@Entity
@Table(name="alind_t_project_status")
public class ProjectStatusEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="project_status_id", unique = true, nullable = false)
	private int projectStatusId;
	
	/**
	 *  1 - Not started
	 *  2- Started
	 *  3 - Hold
	 *  4 - Cancelled
	 *  5 - Closed
	 */
	
	@Column(name="status")
	private String status;

	public int getProjectStatusId() {
		return projectStatusId;
	}

	public void setProjectStatusId(int projectStatusId) {
		this.projectStatusId = projectStatusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
