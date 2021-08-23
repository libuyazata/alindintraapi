package com.yaz.alind.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Keeps, the last number of each document type, based on the Nomenclature
 * @author Libu
 *
 */


@Entity
@Table(name="alind_t_document_number_series")
public class DocumentNumberSeriesEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "document_number_series_id", unique = true, nullable = false)
	private int documentNumberSeriesId;
	@Column(name = "last_document_number")
	private String lastDocumentNumber;
	@Column(name = "document_type_id")
	private int documentTypeId;
	
	public int getDocumentNumberSeriesId() {
		return documentNumberSeriesId;
	}
	public void setDocumentNumberSeriesId(int documentNumberSeriesId) {
		this.documentNumberSeriesId = documentNumberSeriesId;
	}
	public String getLastDocumentNumber() {
		return lastDocumentNumber;
	}
	public void setLastDocumentNumber(String lastDocumentNumber) {
		this.lastDocumentNumber = lastDocumentNumber;
	}
	public int getDocumentTypeId() {
		return documentTypeId;
	}
	public void setDocumentTypeId(int documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

}
