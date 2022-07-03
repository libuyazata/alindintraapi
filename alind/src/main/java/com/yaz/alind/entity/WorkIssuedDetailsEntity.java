package com.yaz.alind.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *    The work from another department is issued to another department
 * @author Libu Mathew
 *
 */

@Entity
@Table(name="alind_t_work_issued_details")
public class WorkIssuedDetailsEntity {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "work_issued_id", unique = true, nullable = false)
	private int workIssuedId;
	
	@Column(name = "work_details_id", nullable = false)
	private int workDetailsId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="work_details_id",insertable = false, updatable = false)
	private WorkDetailsEntity workDetailsEntity;
	
	@Column(name = "department_id", nullable = false)
	private int issuedDeptId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity departmentEntity;
	
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name="employee_id")
	private int createdEmpId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity employeeEntity;
	
	@Column(name="status")
	private int status;

	public int getWorkIssuedId() {
		return workIssuedId;
	}

	public void setWorkIssuedId(int workIssuedId) {
		this.workIssuedId = workIssuedId;
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

	public int getIssuedDeptId() {
		return issuedDeptId;
	}

	public void setIssuedDeptId(int issuedDeptId) {
		this.issuedDeptId = issuedDeptId;
	}

	public DepartmentEntity getDepartmentEntity() {
		return departmentEntity;
	}

	public void setDepartmentEntity(DepartmentEntity departmentEntity) {
		this.departmentEntity = departmentEntity;
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

	public int getCreatedEmpId() {
		return createdEmpId;
	}

	public void setCreatedEmpId(int createdEmpId) {
		this.createdEmpId = createdEmpId;
	}

	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
