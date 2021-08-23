package com.yaz.alind.model.ui;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.yaz.alind.entity.EmployeeEntity;
import com.yaz.alind.entity.TaskDetailsEntity;
import com.yaz.alind.entity.WorkDetailsEntity;

public class EmployeeTaskAllocationModel {

	private int empTaskAllocationId;

	private int taskDetailsId;

	private String taskName;

	private int subTaskId;

	private String subTaskName;

	private int employeeId;

	private String empCode;

	private String firstName;

	private String lastName;

	private String description;

	private int status;

	private String createdOn;

	private String modifiedOn;



}
