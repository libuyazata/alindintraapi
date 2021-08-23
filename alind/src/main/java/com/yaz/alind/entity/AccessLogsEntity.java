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
@Table(name="alind_t_access_logs")
public class AccessLogsEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "access_log_id", unique = true, nullable = false)
	private int accessLogId;
	
	@ManyToOne
	@JoinColumn(name="employee_id",insertable = false, updatable = false)
	private EmployeeEntity employee;
	
	@Column(name = "action")
	private String action;
	@Column(name = "message")
	private String message;
	//All, Document Action, System Action
	private String type;
	@Column(name = "created_on")
	private Date createdOn;
	public int getAccessLogId() {
		return accessLogId;
	}
	public void setAccessLogId(int accessLogId) {
		this.accessLogId = accessLogId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
	
}
