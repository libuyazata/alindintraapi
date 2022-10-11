package com.yaz.alind.model.ui;

public class GeneralMessageAttachmentModel {

	private int genMsgAthId;
	
	private int genMessageId;
	
	private String fileName;
	
	private String orginalFileName;
	
	private String fileLocation;
	
	private String createdOn;

	private String updatedOn;

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOrginalFileName() {
		return orginalFileName;
	}

	public void setOrginalFileName(String orginalFileName) {
		this.orginalFileName = orginalFileName;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	
}
