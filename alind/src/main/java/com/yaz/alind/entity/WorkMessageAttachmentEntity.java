package com.yaz.alind.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="alind_t_work_message_attachment")
public class WorkMessageAttachmentEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "work_msg_ath_id", unique = true, nullable = false)
	private int workMsgAthId;

	@Column(name = "office_communication_id", nullable = false)
	private int officeCommunicationId;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="office_communication_id",insertable = false, updatable = false)
	private InterOfficeCommunicationEntity interOfficeCommunicationEntity;

	@Column(name = "file_name", nullable = false)
	private String fileName;
	
	@Column(name = "orginal_file_name")
	private String orginalFileName;

	@Column(name = "content_type")
	private String contentType;

	public int getWorkMsgAthId() {
		return workMsgAthId;
	}
	public void setWorkMsgAthId(int workMsgAthId) {
		this.workMsgAthId = workMsgAthId;
	}
	public int getOfficeCommunicationId() {
		return officeCommunicationId;
	}
	public void setOfficeCommunicationId(int officeCommunicationId) {
		this.officeCommunicationId = officeCommunicationId;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public InterOfficeCommunicationEntity getInterOfficeCommunicationEntity() {
		return interOfficeCommunicationEntity;
	}
	public String getOrginalFileName() {
		return orginalFileName;
	}
	public void setOrginalFileName(String orginalFileName) {
		this.orginalFileName = orginalFileName;
	}
	public void setInterOfficeCommunicationEntity(
			InterOfficeCommunicationEntity interOfficeCommunicationEntity) {
		this.interOfficeCommunicationEntity = interOfficeCommunicationEntity;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	

}
