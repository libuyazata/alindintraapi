package com.yaz.alind.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="alind_t_dept_gen_message")
public class DepartmentGeneralMessageEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "dept_gen_msg_id", unique = true, nullable = false)
	public int deptGeneralMsgId;

	@Column(name = "gen_msg_id", nullable = false)
	private int genMessageId;

	@ManyToOne
	@JoinColumn(name="gen_msg_id",insertable = false, updatable = false)
	private GeneralMessageEntity generalMessageEntity;

	//To
	@Column(name = "department_id", nullable = false)
	private int departmentId;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity department;

	// Message viewed or not, if its view then viewStatus = 1 otherwise viewStatus = 0
	@Column(name = "view_status")
	private int viewStatus;
	
	/**
	 *  For grouping the messages, based on "referenceNo"
	 *  Only for UI purpose
	 */
	@Column(name = "reference_no", nullable = true)
	private String referenceNo;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public int getDeptGeneralMsgId() {
		return deptGeneralMsgId;
	}

	public void setDeptGeneralMsgId(int deptGeneralMsgId) {
		this.deptGeneralMsgId = deptGeneralMsgId;
	}

	public int getGenMessageId() {
		return genMessageId;
	}

	public void setGenMessageId(int genMessageId) {
		this.genMessageId = genMessageId;
	}

	public GeneralMessageEntity getGeneralMessageEntity() {
		return generalMessageEntity;
	}

	public void setGeneralMessageEntity(GeneralMessageEntity generalMessageEntity) {
		this.generalMessageEntity = generalMessageEntity;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}

	public int getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(int viewStatus) {
		this.viewStatus = viewStatus;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
	
}
