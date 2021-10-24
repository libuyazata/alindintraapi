package com.yaz.alind.model.ui;


public class DocumentTypesModel {
	
	private int documentTypeId;
	// description
	private String type;
	
	private String drawingSeries;
	
	private String createdOn;

	private String updatedOn;
	
	private int status;

	public int getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(int documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDrawingSeries() {
		return drawingSeries;
	}

	public void setDrawingSeries(String drawingSeries) {
		this.drawingSeries = drawingSeries;
	}

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
