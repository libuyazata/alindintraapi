package com.yaz.alind.model.ui;

import java.util.List;

import com.yaz.alind.entity.InterOfficeCommunicationEntity;

public class InterOfficeCommunicationSearchModel {
	
	private List<InterOfficeCommunicationEntity> communicationEntities;
	private List<CommunicationMessageFormatModel> intOffComFromatModList ;
	private int totalCount ;
	public List<InterOfficeCommunicationEntity> getCommunicationEntities() {
		return communicationEntities;
	}
	public void setCommunicationEntities(
			List<InterOfficeCommunicationEntity> communicationEntities) {
		this.communicationEntities = communicationEntities;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<CommunicationMessageFormatModel> getIntOffComFromatModList() {
		return intOffComFromatModList;
	}
	public void setIntOffComFromatModList(
			List<CommunicationMessageFormatModel> intOffComFromatModList) {
		this.intOffComFromatModList = intOffComFromatModList;
	}

}
