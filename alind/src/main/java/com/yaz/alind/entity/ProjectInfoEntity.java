package com.yaz.alind.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="alind_t_project_info")
public class ProjectInfoEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="project_id", unique = true, nullable = false)
	private int projectId;
	@Column(name="project_name")
	private String projectName;
	
	@Column(name="description")
	private String description;
	
	
	@Column(name="employee_id")
	private int employeeId;
	
	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity employee;
	
	@Column(name="department_id")
	private int departmentId;
	
	@ManyToOne
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity department;
	
	@Column(name="start_date")
	private Date startDate;
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="created_on")
	private Date createdOn;
	
	/**
	 *  0- Not started
	 *  1- Started
	 *  2 - Hold
	 *  3 - Closed
	 */
	@ManyToOne
	@JoinColumn(name="project_status_id",insertable = false, updatable = false)
	private ProjectStatusEntity projectStatus;
	
	@Column(name="project_status_id")
	private int projectStatusId;
	
	@Transient
	private int slNo;
	
	public int getSlNo() {
		return slNo;
	}
	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}
	public int getProjectStatusId() {
		return projectStatusId;
	}
	public void setProjectStatusId(int projectStatusId) {
		this.projectStatusId = projectStatusId;
	}
	public ProjectStatusEntity getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(ProjectStatusEntity projectStatus) {
		this.projectStatus = projectStatus;
	}
	@Transient
	private List<ProjectDocumentEntity> documents;
	
	
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
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
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public List<ProjectDocumentEntity> getDocuments() {
		return documents;
	}
	public void setDocuments(List<ProjectDocumentEntity> documents) {
		this.documents = documents;
	}

}
