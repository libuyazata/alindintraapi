package com.yaz.alind.model.ui;

import java.util.List;

/**
 *  Only for UI purpose, inter office communication 
 * @author Libu Mathew
 *
 */
public class CommunicationMessageFormatModel {

	private String titleDate;
	private List<CommunicationMessageRefNoDetailsModel> comMsgRefList;
	
	public String getTitleDate() {
		return titleDate;
	}
	public void setTitleDate(String titleDate) {
		this.titleDate = titleDate;
	}
	public List<CommunicationMessageRefNoDetailsModel> getComMsgRefList() {
		return comMsgRefList;
	}
	public void setComMsgRefList(
			List<CommunicationMessageRefNoDetailsModel> comMsgRefList) {
		this.comMsgRefList = comMsgRefList;
	}
	
	
	
	
}
