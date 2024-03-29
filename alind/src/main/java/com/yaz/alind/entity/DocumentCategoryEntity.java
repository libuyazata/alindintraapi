package com.yaz.alind.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Document nomenclature
 * 
 * @author Libu Mathew
 *
 */
@Entity
@Table(name="alind_t_document_category")
public class DocumentCategoryEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "document_category_id", unique = true, nullable = false)
	private int documentCategoryId;
	// description
	@Column(name = "type")
	private String type;
	@Column(name = "drawing_series", nullable = false)
	private String drawingSeries;
	
	@Column(name="created_at")
	private Date createdAt;
	@Column(name="updated_on")
	private Date updatedOn;
	// active - 1 or inactive = -1
	@Column(name="status")
	private int status;

	public int getDocumentCategoryId() {
		return documentCategoryId;
	}
	public void setDocumentTypeId(int documentTypeId) {
		this.documentCategoryId = documentTypeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getDrawingSeries() {
		return drawingSeries;
	}
	public void setDrawingSeries(String drawingSeries) {
		this.drawingSeries = drawingSeries;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public void setDocumentCategoryId(int documentCategoryId) {
		this.documentCategoryId = documentCategoryId;
	}

}
