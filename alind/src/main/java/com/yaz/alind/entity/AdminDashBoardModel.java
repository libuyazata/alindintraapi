package com.yaz.alind.entity;

import java.util.List;
import java.util.Map;

/**
 *  Only for Dash Board
 * @author Libu
 *
 */

public class AdminDashBoardModel {
	
	private int noOfDepartments;
	private int noOfEmpoyees;
	private Map<String,Integer> empCountBasedOnDept ;
	private Map<String,Integer> docAgaistDept;
//	private Map<String,Integer> projectDetails;
	private Map<String,Integer> workDetails;
	private List<DocumentUsersEntity> documentUsers;
	
	public int getNoOfDepartments() {
		return noOfDepartments;
	}
	public void setNoOfDepartments(int noOfDepartments) {
		this.noOfDepartments = noOfDepartments;
	}
	public int getNoOfEmpoyees() {
		return noOfEmpoyees;
	}
	public void setNoOfEmpoyees(int noOfEmpoyees) {
		this.noOfEmpoyees = noOfEmpoyees;
	}
	public Map<String, Integer> getEmpCountBasedOnDept() {
		return empCountBasedOnDept;
	}
	public void setEmpCountBasedOnDept(Map<String, Integer> empCountBasedOnDept) {
		this.empCountBasedOnDept = empCountBasedOnDept;
	}
//	public Map<String, Integer> getProjectDetails() {
//		return projectDetails;
//	}
//	public void setProjectDetails(Map<String, Integer> projectDetails) {
//		this.projectDetails = projectDetails;
//	}
	public Map<String, Integer> getDocAgaistDept() {
		return docAgaistDept;
	}
	public void setDocAgaistDept(Map<String, Integer> docAgaistDept) {
		this.docAgaistDept = docAgaistDept;
	}
	public List<DocumentUsersEntity> getDocumentUsers() {
		return documentUsers;
	}
	public void setDocumentUsers(List<DocumentUsersEntity> documentUsers) {
		this.documentUsers = documentUsers;
	}
	public Map<String, Integer> getWorkDetails() {
		return workDetails;
	}
	public void setWorkDetails(Map<String, Integer> workDetails) {
		this.workDetails = workDetails;
	}
}
