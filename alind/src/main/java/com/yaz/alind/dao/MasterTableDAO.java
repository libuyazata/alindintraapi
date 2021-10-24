package com.yaz.alind.dao;

import java.util.List;

import com.yaz.alind.entity.DocumentNumberSeriesEntity;
import com.yaz.alind.entity.DocumentTypesEntity;
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
	
	public List<DocumentTypesEntity> getAllDocumentTypes(int status);
	public DocumentTypesEntity saveDocumentTypes(DocumentTypesEntity documentTypes);
	public DocumentTypesEntity updateDocumentTypes(DocumentTypesEntity documentTypes);
	public DocumentTypesEntity getDocumentTypeById(int documentTypeId);
	public boolean isDrawingSeriesExists(String drawingSeries);
}
