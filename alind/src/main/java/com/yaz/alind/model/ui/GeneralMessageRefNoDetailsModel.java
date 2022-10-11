package com.yaz.alind.model.ui;

import java.util.List;

public class GeneralMessageRefNoDetailsModel {

	private String titleRefNo;
	
	private List<GeneralMessageModel> generalMessageModelList;

	public String getTitleRefNo() {
		return titleRefNo;
	}

	public void setTitleRefNo(String titleRefNo) {
		this.titleRefNo = titleRefNo;
	}

	public List<GeneralMessageModel> getGeneralMessageModelList() {
		return generalMessageModelList;
	}

	public void setGeneralMessageModelList(
			List<GeneralMessageModel> generalMessageModelList) {
		this.generalMessageModelList = generalMessageModelList;
	}
	
}
