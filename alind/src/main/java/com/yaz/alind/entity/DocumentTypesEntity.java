package com.yaz.alind.entity;

import java.sql.Timestamp;

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
@Table(name="alind_t_document_types")
public class DocumentTypesEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "document_type_id", unique = true, nullable = false)
	private int documentTypeId;
	// description
	@Column(name = "type")
	private String type;
	@Column(name="created_at")
	private Timestamp createdAt;
	@Column(name = "drawing_series", nullable = false)
	private String drawingSeries;
	
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
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public String getDrawingSeries() {
		return drawingSeries;
	}
	public void setDrawingSeries(String drawingSeries) {
		this.drawingSeries = drawingSeries;
	}
	
	

}
