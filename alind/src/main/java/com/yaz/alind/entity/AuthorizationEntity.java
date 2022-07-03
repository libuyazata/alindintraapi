package com.yaz.alind.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="alind_t_authorization")
public class AuthorizationEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "authorization_id", unique = true, nullable = false)
	private int authorizationId;
	
	@Column(name = "user_role_id")
	private int userRoleId;
	/**
	 *  Employee
	 */
	@Column(name = "employee_view")
	private boolean employeeView;
	@Column(name = "employee_edit")
	private boolean employeeEdit;
	@Column(name = "employee_delete")
	private boolean employeeDelete;
	
	/**
	 *  Department
	 */
	@Column(name = "department_view")
	private boolean departmentView;
	@Column(name = "department_edit")
	private boolean departmentEdit;
	@Column(name = "department_delete")
	private boolean departmentDelete;
	
	/**
	 *  Work
	 */
	@Column(name = "work_view")
	private boolean workView;
	@Column(name = "work_edit")
	private boolean workEdit;
	@Column(name = "work_delete")
	private boolean workDelete;
	
	/**
	 *  Sub Task
	 */
	@Column(name = "subtask_view")
	private boolean subTaskView;
	@Column(name = "subtask_edit")
	private boolean subTaskEdit;
	@Column(name = "subtask_delete")
	private boolean subTaskDelete;
	
	/**
	 *  Document
	 */
	@Column(name = "doucment_view")
	private boolean doucmentView;
	@Column(name = "doucment_edit")
	private boolean documentEdit;
	@Column(name = "doucment_delete")
	private boolean documentDelete;
	
	/**
	 *  Deputation  
	 */
	@Column(name = "deputation_view")
	private boolean deputationView;
	@Column(name = "deputation_edit")
	private boolean deputationEdit;
	@Column(name = "deputation_delete")
	private boolean deputationDelete;
	
	public int getAuthorizationId() {
		return authorizationId;
	}
	public void setAuthorizationId(int authorizationId) {
		this.authorizationId = authorizationId;
	}
	public int getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}
	public boolean isEmployeeView() {
		return employeeView;
	}
	public void setEmployeeView(boolean employeeView) {
		this.employeeView = employeeView;
	}
	public boolean isEmployeeEdit() {
		return employeeEdit;
	}
	public void setEmployeeEdit(boolean employeeEdit) {
		this.employeeEdit = employeeEdit;
	}
	public boolean isEmployeeDelete() {
		return employeeDelete;
	}
	public void setEmployeeDelete(boolean employeeDelete) {
		this.employeeDelete = employeeDelete;
	}
	public boolean isDepartmentView() {
		return departmentView;
	}
	public void setDepartmentView(boolean departmentView) {
		this.departmentView = departmentView;
	}
	public boolean isDepartmentEdit() {
		return departmentEdit;
	}
	public void setDepartmentEdit(boolean departmentEdit) {
		this.departmentEdit = departmentEdit;
	}
	public boolean isDepartmentDelete() {
		return departmentDelete;
	}
	public void setDepartmentDelete(boolean departmentDelete) {
		this.departmentDelete = departmentDelete;
	}
	public boolean isWorkView() {
		return workView;
	}
	public void setWorkView(boolean workView) {
		this.workView = workView;
	}
	public boolean isWorkEdit() {
		return workEdit;
	}
	public void setWorkEdit(boolean workEdit) {
		this.workEdit = workEdit;
	}
	public boolean isWorkDelete() {
		return workDelete;
	}
	public void setWorkDelete(boolean workDelete) {
		this.workDelete = workDelete;
	}
	public boolean isSubTaskView() {
		return subTaskView;
	}
	public void setSubTaskView(boolean subTaskView) {
		this.subTaskView = subTaskView;
	}
	public boolean isSubTaskEdit() {
		return subTaskEdit;
	}
	public void setSubTaskEdit(boolean subTaskEdit) {
		this.subTaskEdit = subTaskEdit;
	}
	public boolean isSubTaskDelete() {
		return subTaskDelete;
	}
	public void setSubTaskDelete(boolean subTaskDelete) {
		this.subTaskDelete = subTaskDelete;
	}
	public boolean isDoucmentView() {
		return doucmentView;
	}
	public void setDoucmentView(boolean doucmentView) {
		this.doucmentView = doucmentView;
	}
	public boolean isDocumentEdit() {
		return documentEdit;
	}
	public void setDocumentEdit(boolean documentEdit) {
		this.documentEdit = documentEdit;
	}
	public boolean isDocumentDelete() {
		return documentDelete;
	}
	public void setDocumentDelete(boolean documentDelete) {
		this.documentDelete = documentDelete;
	}
	public boolean isDeputationView() {
		return deputationView;
	}
	public void setDeputationView(boolean deputationView) {
		this.deputationView = deputationView;
	}
	public boolean isDeputationEdit() {
		return deputationEdit;
	}
	public void setDeputationEdit(boolean deputationEdit) {
		this.deputationEdit = deputationEdit;
	}
	public boolean isDeputationDelete() {
		return deputationDelete;
	}
	public void setDeputationDelete(boolean deputationDelete) {
		this.deputationDelete = deputationDelete;
	}

}
