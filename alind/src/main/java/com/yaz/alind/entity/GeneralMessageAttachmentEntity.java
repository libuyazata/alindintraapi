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
@Table(name="alind_t_gen_message_ath")
public class GeneralMessageAttachmentEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "gen_msg_ath_id", unique = true, nullable = false)
	private int genMsgAthId;

	@Column(name = "gen_msg_id", nullable = false)
	private int genMessageId;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="gen_msg_id",insertable = false, updatable = false)
	private GeneralMessageEntity generalMessageEntity;

	@Column(name = "file_name", nullable = false)
	private String fileName;
	
	@Column(name = "file_type")
	private String fileType;
	
	@Column(name = "orginal_file_name")
	private String orginalFileName;

	public int getGenMsgAthId() {
		return genMsgAthId;
	}

	public void setGenMsgAthId(int genMsgAthId) {
		this.genMsgAthId = genMsgAthId;
	}

	public int getGenMessageId() {
		return genMessageId;
	}

	public void setGenMessageId(int genMessageId) {
		this.genMessageId = genMessageId;
	}

	public GeneralMessageEntity getGeneralMessageEntity() {
		return generalMessageEntity;
	}

	public void setGeneralMessageEntity(GeneralMessageEntity generalMessageEntity) {
		this.generalMessageEntity = generalMessageEntity;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getOrginalFileName() {
		return orginalFileName;
	}

	public void setOrginalFileName(String orginalFileName) {
		this.orginalFileName = orginalFileName;
	}

}
