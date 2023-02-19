package com.yaz.alind.model.ui;

import java.util.List;

import com.yaz.alind.entity.WorkDetailsEntity;

public class WorkDetailsModelList {

	private List<WorkDetailsModel> workModelList;
	private List<WorkDetailsEntity> workEntityList;
	private int totalCount ;
	public List<WorkDetailsModel> getWorkModelList() {
		return workModelList;
	}
	public void setWorkModelList(List<WorkDetailsModel> workModelList) {
		this.workModelList = workModelList;
	}
	public List<WorkDetailsEntity> getWorkEntityList() {
		return workEntityList;
	}
	public void setWorkEntityList(List<WorkDetailsEntity> workEntityList) {
		this.workEntityList = workEntityList;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
