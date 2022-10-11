package com.yaz.alind.model.ui;

import java.util.List;

public class GeneralMessageFormatModel {
	
	private String titleDate;
	private List<GeneralMessageRefNoDetailsModel> generalMessageRefNoDetailsModel;
	
	public String getTitleDate() {
		return titleDate;
	}
	public void setTitleDate(String titleDate) {
		this.titleDate = titleDate;
	}
	public List<GeneralMessageRefNoDetailsModel> getGeneralMessageRefNoDetailsModel() {
		return generalMessageRefNoDetailsModel;
	}
	public void setGeneralMessageRefNoDetailsModel(
			List<GeneralMessageRefNoDetailsModel> generalMessageRefNoDetailsModel) {
		this.generalMessageRefNoDetailsModel = generalMessageRefNoDetailsModel;
	}
	
}
