package com.yaz.alind.service;

import java.util.List;

import com.yaz.alind.model.ui.DocumentTypesModel;
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
	
	public List<DocumentTypesModel> getAllDocumentTypes(int status);
	public DocumentTypesModel saveDocumentTypes(DocumentTypesModel documentTypes);
	public DocumentTypesModel updateDocumentTypes(DocumentTypesModel documentTypes);
	public int deleteDocumentTypesById(int documentTypeId); 
	public DocumentTypesModel getDocumentTypeById(int documentTypeId);
	public boolean isDrawingSeriesExists(String drawingSeries);

}
