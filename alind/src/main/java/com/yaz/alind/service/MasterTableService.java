package com.yaz.alind.service;

import java.util.List;

import com.yaz.alind.entity.DocumentTypeEntity;
import com.yaz.alind.model.ui.DocumentCategoryModel;
import com.yaz.alind.model.ui.DocumentTypeModel;
import com.yaz.alind.model.ui.WorkStatusModel;
import com.yaz.alind.model.ui.WorkTypeModel;

public interface MasterTableService {
	
	public WorkStatusModel saveWorkStatus(WorkStatusModel workStatusModel);
	public WorkStatusModel updateWorkStatus(WorkStatusModel workStatusModel);
	public  List<WorkStatusModel> getAllWorkStatus(int status);
	public WorkStatusModel getWorkStatusById(int workStatusId);
	public int deleteWorkStatus(int workStatusId);
	
	public WorkTypeModel saveWorkType(WorkTypeModel model);
	public WorkTypeModel updateWorkType(WorkTypeModel model);
	public List<WorkTypeModel> getAllWorkType(int status);
	public WorkTypeModel getWorkTypeById(int workTypeId);
	public int deleteWorkType(int workTypeId);
	
	public List<DocumentCategoryModel> getAllDocumentCategory(int status);
	public DocumentCategoryModel saveDocumentCategory(DocumentCategoryModel documentCategory);
	public DocumentCategoryModel updateDocumentCategory(DocumentCategoryModel documentCategory);
	public int deleteDocumentCategoryById(int documentTypeId); 
	public DocumentCategoryModel getDocumentCategoryById(int documentTypeId);
	public boolean isDrawingSeriesExists(String drawingSeries);
	
	public List<DocumentTypeModel> getAllDocumentType(int status);
	public DocumentTypeModel saveDocumentType(DocumentTypeModel docType);
	public DocumentTypeModel updateDocumentType(DocumentTypeModel docType); 
	public DocumentTypeModel getDocumentTypeById(int documentTypeId);
	public int deleteDocumentType(int documentTypeId);

}
