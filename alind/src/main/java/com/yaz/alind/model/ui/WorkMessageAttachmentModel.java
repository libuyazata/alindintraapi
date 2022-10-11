package com.yaz.alind.model.ui;



public class WorkMessageAttachmentModel {


	private int workMsgAthId;

	private int officeCommunicationId;

	private String fileName;
	
//	private byte[] docByte;
	
//	private  InputStream docStream;

	private String fileType;

	private String orginalFileName;
	
	private String contentType;
	
//	private ByteArrayResource docByteArray;
	
	private String fileLocation;

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

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	
}
