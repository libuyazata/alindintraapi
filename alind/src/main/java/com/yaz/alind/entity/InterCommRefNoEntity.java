package com.yaz.alind.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="alind_t_inter_comm_ref_no")
public class InterCommRefNoEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "com_ref_no_id", unique = true, nullable = false)
	private int comRefNoId;
	
	@Column(name = "department_id", nullable = false)
	private int departmentId;

	//Sales - SAL , Designs - DES, etc
	@Column(name = "dept_abbreviation")
	private String deptAbbreviation;
	
	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="department_id",insertable = false, updatable = false)
	private DepartmentEntity department;
	
	@Column(name = "current_year")
	private int currentYear;
	
	@Column(name = "no")
	private int no;

	public int getComRefNoId() {
		return comRefNoId;
	}

	public void setComRefNoId(int comRefNoId) {
		this.comRefNoId = comRefNoId;
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
	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getDeptAbbreviation() {
		return deptAbbreviation;
	}

	public void setDeptAbbreviation(String deptAbbreviation) {
		this.deptAbbreviation = deptAbbreviation;
	}

	
}
