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
@Table(name="alind_t_department_users")
public class DepartmentUsersEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "department_users_id", unique = true, nullable = false)
	private int departmentUsersId;
	
	@ManyToOne
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity department;
	
	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity employee;
	
	@Column(name = "read")
	private int read;
	@Column(name = "write")
	private int write;
	
	
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getWrite() {
		return write;
	}
	public void setWrite(int write) {
		this.write = write;
	}
	@Column(name = "created_on")
	private Date createdOn;
	
	public int getDepartmentUsersId() {
		return departmentUsersId;
	}
	public void setDepartmentUsersId(int departmentUsersId) {
		this.departmentUsersId = departmentUsersId;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public EmployeeEntity getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeEntity employee) {
		this.employee = employee;
	}
	public DepartmentEntity getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}
	

}
