package com.yaz.alind.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Deputation History, connected to DeputationEntity
 * @author Libu Mathew
 *
 */

@Entity
@Table(name="alind_t_deputation_history")
public class DeputationHistoryEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "deputation_history_id", unique = true, nullable = false)
	private int deputationHistoryId;
	
	@Column(name = "deputation_id")
	private int deputationId;
	
	@ManyToOne
	@JoinColumn(name="deputation_id",insertable = false, updatable = false)
	private DeputationEntity deputationEntity;
	
	@Column(name="employee_id")
	private int employeeId;
	
	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity employee;
	
	@Column(name = "department_id")
	private int parentDepartmentId;

	@ManyToOne
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity parentDepartment;
	

	public int getDeputationHistoryId() {
		return deputationHistoryId;
	}

	public void setDeputationHistoryId(int deputationHistoryId) {
		this.deputationHistoryId = deputationHistoryId;
	}

	public int getDeputationId() {
		return deputationId;
	}

	public void setDeputationId(int deputationId) {
		this.deputationId = deputationId;
	}

	public DeputationEntity getDeputationEntity() {
		return deputationEntity;
	}

	public void setDeputationEntity(DeputationEntity deputationEntity) {
		this.deputationEntity = deputationEntity;
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
	
	public int getParentDepartmentId() {
		return parentDepartmentId;
	}

	public void setParentDepartmentId(int parentDepartmentId) {
		this.parentDepartmentId = parentDepartmentId;
	}

	public DepartmentEntity getParentDepartment() {
		return parentDepartment;
	}

	public void setParentDepartment(DepartmentEntity parentDepartment) {
		this.parentDepartment = parentDepartment;
	}


}
