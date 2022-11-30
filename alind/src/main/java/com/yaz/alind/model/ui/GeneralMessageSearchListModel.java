package com.yaz.alind.model.ui;

import java.util.List;

import com.yaz.alind.entity.GeneralMessageEntity;

public class GeneralMessageSearchListModel {
	
    private List<GeneralMessageEntity> generalMessageEntities;
    private List<GeneralMessageFormatModel> formatModels;
    private int totalCount ;
    
	public List<GeneralMessageEntity> getGeneralMessageEntities() {
		return generalMessageEntities;
	}
	public void setGeneralMessageEntities(
			List<GeneralMessageEntity> generalMessageEntities) {
		this.generalMessageEntities = generalMessageEntities;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<GeneralMessageFormatModel> getFormatModels() {
		return formatModels;
	}
	public void setFormatModels(List<GeneralMessageFormatModel> formatModels) {
		this.formatModels = formatModels;
	}
    
    
}
