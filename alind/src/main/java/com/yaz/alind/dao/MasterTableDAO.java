package com.yaz.alind.dao;

import java.util.List;

import springfox.documentation.spi.DocumentationType;

import com.yaz.alind.entity.DocumentNumberSeriesEntity;
import com.yaz.alind.entity.DocumentCategoryEntity;
import com.yaz.alind.entity.DocumentTypeEntity;
import com.yaz.alind.entity.WorkStatusEntity;
import com.yaz.alind.entity.WorkTypeEntity;

public interface MasterTableDAO {

	public WorkStatusEntity saveWorkStatusEntity(WorkStatusEntity workStatusEntity);
	public WorkStatusEntity updateWorkStatusEntity(WorkStatusEntity workStatusEntity);
	public  List<WorkStatusEntity> getAllWorkStatusEntity(int status);
	public WorkStatusEntity getWorkStatusEntityById(int workStatusId);
	
	public WorkTypeEntity saveWorkTypeEntity(WorkTypeEntity workTypeEntity);
	public WorkTypeEntity updateWorkTypeEntity(WorkTypeEntity workTypeEntity);
	public List<WorkTypeEntity> getAllWorkTypeEntities(int status);
	public WorkTypeEntity getWorkTypeEntityById(int workTypeId);
	
	public List<DocumentCategoryEntity> getAllDocumentCategory(int status);
	public DocumentCategoryEntity saveDocumentCategory(DocumentCategoryEntity documentCategory);
	public DocumentCategoryEntity updateDocumentCategory(DocumentCategoryEntity documentCategory);
	public DocumentCategoryEntity getDocumentCategoryById(int documentCategoryId);
	public boolean isDrawingSeriesExists(String drawingSeries);
	
	public List<DocumentTypeEntity> getAllDocumentType(int status);
	public DocumentTypeEntity saveDocumentType(DocumentTypeEntity docType);
	public DocumentTypeEntity updateDocumentType(DocumentTypeEntity docType); 
	public DocumentTypeEntity getDocumentTypeById(int documentTypeId);
	
}
