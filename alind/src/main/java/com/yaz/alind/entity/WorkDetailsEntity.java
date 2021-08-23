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
@Table(name="alind_t_work_details")
public class WorkDetailsEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "work_details_id", unique = true, nullable = false)
	private int workDetailsId;

	@Column(name = "work_name")
	private String workName;

	@Column(name = "work_type_id")
	private int workTypeId;

	@ManyToOne
	@JoinColumn(name="work_type_id",insertable = false, updatable = false)
	private WorkTypeEntity workTypeEntity;

	@Column(name = "description")
	private String description;

	@Column(name = "department_id")
	private int departmentId;

	@ManyToOne
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity departmentEntity;

	@Column(name="project_co_ordinate_employee_id")
	private int projectCoOrdinatorEmpId;

	@ManyToOne
	@JoinColumn(name="project_co_ordinate_employee_id",insertable = false, updatable = false)
	private EmployeeEntity projectCoOrdinatorEmp;

	//On going,Short closed,Completed
	@Column(name = "work_status_id")
	private int workStatusId;

	@ManyToOne
	@JoinColumn(name="work_status_id",insertable = false, updatable = false)
	private WorkStatusEntity workStatusEntity;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name="employee_id")
	private int createdEmpId;

	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity createdEmp;
	
	@Column(name="status")
	private int status;


	public int getTaskDetailsId() {
		return workDetailsId;
	}

	public void setTaskDetailsId(int taskDetailsId) {
		this.workDetailsId = taskDetailsId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public DepartmentEntity getDepartmentEntity() {
		return departmentEntity;
	}

	public void setDepartmentEntity(DepartmentEntity departmentEntity) {
		this.departmentEntity = departmentEntity;
	}

	public EmployeeEntity getProjectCoOrdinatorEmp() {
		return projectCoOrdinatorEmp;
	}

	public void setProjectCoOrdinatorEmp(EmployeeEntity projectCoOrdinatorEmp) {
		this.projectCoOrdinatorEmp = projectCoOrdinatorEmp;
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

	public EmployeeEntity getCreatedEmp() {
		return createdEmp;
	}

	public void setCreatedEmp(EmployeeEntity createdEmp) {
		this.createdEmp = createdEmp;
	}

	public WorkStatusEntity getWorkStatusEntity() {
		return workStatusEntity;
	}

	public int getWorkDetailsId() {
		return workDetailsId;
	}

	public void setWorkDetailsId(int workDetailsId) {
		this.workDetailsId = workDetailsId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public int getWorkTypeId() {
		return workTypeId;
	}

	public void setWorkTypeId(int workTypeId) {
		this.workTypeId = workTypeId;
	}

	public WorkTypeEntity getWorkTypeEntity() {
		return workTypeEntity;
	}

	public void setWorkTypeEntity(WorkTypeEntity workTypeEntity) {
		this.workTypeEntity = workTypeEntity;
	}

	public int getProjectCoOrdinatorEmpId() {
		return projectCoOrdinatorEmpId;
	}

	public void setProjectCoOrdinatorEmpId(int projectCoOrdinatorEmpId) {
		this.projectCoOrdinatorEmpId = projectCoOrdinatorEmpId;
	}

	public void setWorkStatusEntity(WorkStatusEntity workStatusEntity) {
		this.workStatusEntity = workStatusEntity;
	}

	public int getCreatedEmpId() {
		return createdEmpId;
	}

	public void setCreatedEmpId(int createdEmpId) {
		this.createdEmpId = createdEmpId;
	}

	public int getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(int workStatusId) {
		this.workStatusId = workStatusId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
