package com.yaz.alind.model.ui;

import java.util.List;

import com.yaz.alind.entity.DepartmentCommunicationMessagesEntity;
import com.yaz.alind.entity.InterOfficeCommunicationEntity;

/**
 *  Only for UI Purpose
 * @author Libu Mathew
 *
 */
public class InterOfficeCommunicationListModel {
	
	private List<InterOfficeCommunicationEntity> communicationEntities;
	private List<CommunicationMessageFormatModel> intOffComFromatModList ;
	List<DepartmentCommunicationMessagesEntity> deptCommMesgeList;
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
	public List<DepartmentCommunicationMessagesEntity> getDeptCommMesgeList() {
		return deptCommMesgeList;
	}
	public void setDeptCommMesgeList(
			List<DepartmentCommunicationMessagesEntity> deptCommMesgeList) {
		this.deptCommMesgeList = deptCommMesgeList;
	}
}
