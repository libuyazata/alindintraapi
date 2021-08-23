package com.yaz.alind.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//@Entity
//@Table(name="alind_t_sub_employee_task__allocation")
public class EmployeeTaskAllocationEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "emp_task_allocation_id", unique = true, nullable = false)
	private int empTaskAllocationId;
	
	@Column(name = "task_details_id")
	private int taskDetailsId;
	
	@ManyToOne
	@JoinColumn(name="task_details_id",insertable = false, updatable = false)
	private WorkDetailsEntity taskDetailsEntity;
	
	@Column(name = "sbu_task_id")
	private int subTaskId;
	
	@ManyToOne
	@JoinColumn(name="sbu_task_id",insertable = false, updatable = false)
	private TaskDetailsEntity subTaskDetailsEntity;
	
	@Column(name="employee_id")
	private int employeeId;

	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity employeeEntity;
	
	@Column(name = "description")
	private String description;
	
	@Column(name="employee_id")
	private int assignedByEmpId;

	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity assignedByEmp;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;
	
	public int getEmpTaskAllocationId() {
		return empTaskAllocationId;
	}

	public void setEmpTaskAllocationId(int empTaskAllocationId) {
		this.empTaskAllocationId = empTaskAllocationId;
	}

	public int getTaskDetailsId() {
		return taskDetailsId;
	}

	public void setTaskDetailsId(int taskDetailsId) {
		this.taskDetailsId = taskDetailsId;
	}

	public WorkDetailsEntity getTaskDetailsEntity() {
		return taskDetailsEntity;
	}

	public void setTaskDetailsEntity(WorkDetailsEntity taskDetailsEntity) {
		this.taskDetailsEntity = taskDetailsEntity;
	}

	public int getSubTaskId() {
		return subTaskId;
	}

	public void setSubTaskId(int subTaskId) {
		this.subTaskId = subTaskId;
	}

	public TaskDetailsEntity getSubTaskDetailsEntity() {
		return subTaskDetailsEntity;
	}

	public void setSubTaskDetailsEntity(TaskDetailsEntity subTaskDetailsEntity) {
		this.subTaskDetailsEntity = subTaskDetailsEntity;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAssignedByEmpId() {
		return assignedByEmpId;
	}

	public void setAssignedByEmpId(int assignedByEmpId) {
		this.assignedByEmpId = assignedByEmpId;
	}

	public EmployeeEntity getAssignedByEmp() {
		return assignedByEmp;
	}

	public void setAssignedByEmp(EmployeeEntity assignedByEmp) {
		this.assignedByEmp = assignedByEmp;
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
