package com.yaz.alind.model.ui;

import java.util.List;

public class CommunicationMessageRefNoDetailsModel {

	private String titleRefNo;
	private List<InterOfficeCommunicationModel> offCommList ;
	public String getTitleRefNo() {
		return titleRefNo;
	}
	public void setTitleRefNo(String titleRefNo) {
		this.titleRefNo = titleRefNo;
	}
	public List<InterOfficeCommunicationModel> getOffCommList() {
		return offCommList;
	}
	public void setOffCommList(List<InterOfficeCommunicationModel> offCommList) {
		this.offCommList = offCommList;
	}
	
	
}
