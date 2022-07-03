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

/**
 * Its connected with "InterOfficeCommunicationEntity".
 * The message to each of the Department
 *   
 * @author Libu Mathew
 *
 */
@Entity
@Table(name="alind_t_dept_communication")
public class DepartmentCommunicationMessagesEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "dept_comm_id", unique = true, nullable = false)
	public int deptCommId;

	@Column(name = "office_communication_id")
	private int officeCommunicationId;

	//To
	@Column(name = "department_id")
	private int departmentId;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity department;

	// Message viewed or not, if its view then viewStatus = 1 otherwise viewStatus = 0
	@Column(name = "view_status")
	private int viewStatus;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	public int getDeptCommId() {
		return deptCommId;
	}

	public void setDeptCommId(int deptCommId) {
		this.deptCommId = deptCommId;
	}

	public int getOfficeCommunicationId() {
		return officeCommunicationId;
	}

	public void setOfficeCommunicationId(int officeCommunicationId) {
		this.officeCommunicationId = officeCommunicationId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(int viewStatus) {
		this.viewStatus = viewStatus;
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

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}
	
}
