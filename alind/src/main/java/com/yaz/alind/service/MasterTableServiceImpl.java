package com.yaz.alind.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaz.alind.dao.MasterTableDAO;
import com.yaz.alind.entity.DocumentTypesEntity;
import com.yaz.alind.entity.WorkStatusEntity;
import com.yaz.alind.entity.WorkTypeEntity;
import com.yaz.alind.entity.factory.WorkTypeEntityFactory;
import com.yaz.alind.model.ui.DocumentTypesModel;
import com.yaz.alind.model.ui.WorkStatusModel;
import com.yaz.alind.model.ui.WorkTypeModel;

@Service
public  class MasterTableServiceImpl implements MasterTableService {

	private static final Logger logger = LoggerFactory.getLogger(MasterTableServiceImpl.class);
	@Autowired
	MasterTableDAO masterTableDAO;
	@Autowired
	UtilService utilService;
	@Autowired
	WorkTypeEntityFactory workTypeEntityFactory;

	@Override
	public WorkStatusModel saveWorkStatus(
			WorkStatusModel workStatusModel) {
		WorkStatusModel model = null;
		try{
			System.out.println("MasterTableServiceImpl,saveWorkStatus, id: "+workStatusModel.getWorkStatusId());
			WorkStatusEntity entity = createWorkStatusEntity(workStatusModel);
			entity.setCreatedOn(utilService.getCurrentDate());
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity.setStatus(1);
			entity = masterTableDAO.saveWorkStatusEntity(entity);
			model = createWorkStatusModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveWorkStatus: "+e.getMessage());
		}
		return model;
	}


	@Override
	public WorkStatusModel updateWorkStatus(WorkStatusModel workStatusModel) {
		WorkStatusModel model = null;
		try{
			System.out.println("MasterTableServiceImpl,updateWorkStatus, id: "+workStatusModel.getWorkStatusId());
			WorkStatusEntity entity = createWorkStatusEntity(workStatusModel);
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity = masterTableDAO.updateWorkStatusEntity(entity);
			model = createWorkStatusModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateWorkStatus: "+e.getMessage());
		}
		return model;
	}

	@Override
	public List<WorkStatusModel> getAllWorkStatus(int status) {
		List<WorkStatusModel> models = null;
		try{
			List<WorkStatusEntity> entities = masterTableDAO.getAllWorkStatusEntity(status);
			models = new ArrayList<WorkStatusModel>();
			for(WorkStatusEntity e: entities){
				WorkStatusModel m = createWorkStatusModel(e);
				models.add(m);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllWorkStatus: "+e.getMessage());
		}
		return models;
	}

	@Override
	public WorkStatusModel getWorkStatusById(int workStatusId) {
		WorkStatusModel model = null;
		try{
			WorkStatusEntity entity = masterTableDAO.getWorkStatusEntityById(workStatusId);
			model = createWorkStatusModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkStatusById: "+e.getMessage());
		}
		return model;
	}

	@Override
	public int deleteWorkStatus(int workStatusId){
		int value = 0;
		try{
			WorkStatusEntity entity = masterTableDAO.getWorkStatusEntityById(workStatusId);
			entity.setStatus(-1);
			entity = masterTableDAO.updateWorkStatusEntity(entity);
			if(entity.getStatus() == -1){
				value = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("deleteWorkStatus: "+e.getMessage());
		}
		return value;
	}

	private WorkStatusModel createWorkStatusModel(WorkStatusEntity entity){
		WorkStatusModel model = null;
		try{
			model = new WorkStatusModel();
			if(entity.getCreatedOn() != null){
				model.setCreatedOn(utilService.dateToString(entity.getCreatedOn()));
			}
			model.setDescription(entity.getDescription());
			model.setStatus(entity.getStatus());
			if(entity.getUpdatedOn() != null){
				model.setUpdatedOn(utilService.dateToString(entity.getUpdatedOn()));
			}
			model.setWorkStatusId(entity.getWorkStatusId());
			model.setWorkStatusName(entity.getWorkStatusName());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createWorkStatusModel: "+e.getMessage());
		}
		return model;
	}

	private WorkStatusModel lamdaTestcreateWorkStatusModel(WorkStatusEntity entity){
		WorkStatusModel model = null;
		try{
			model = new WorkStatusModel();
			if(entity.getCreatedOn() != null){
				model.setCreatedOn(utilService.dateToString(entity.getCreatedOn()));
			}
			model.setDescription(entity.getDescription());
			model.setStatus(entity.getStatus());
			if(entity.getUpdatedOn() != null){
				model.setUpdatedOn(utilService.dateToString(entity.getUpdatedOn()));
			}
			model.setWorkStatusId(entity.getWorkStatusId());
			model.setWorkStatusName(entity.getWorkStatusName());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createWorkStatusModel: "+e.getMessage());
		}
		return model;
	}

	private WorkStatusEntity createWorkStatusEntity(WorkStatusModel model){
		WorkStatusEntity entity = null;
		try{
			entity = new WorkStatusEntity();

			entity.setDescription(model.getDescription());
			if(model.getCreatedOn() != null){
				entity.setCreatedOn(utilService.stringToDate(model.getCreatedOn()));
			}
			if(model.getUpdatedOn() != null){
				entity.setUpdatedOn(utilService.stringToDate(model.getUpdatedOn()));
			}
			entity.setStatus(model.getStatus());
			entity.setWorkStatusId(model.getWorkStatusId());
			entity.setWorkStatusName(model.getWorkStatusName());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createWorkStatusEntity: "+e.getMessage());
		}
		return entity;
	}


	@Override
	public WorkTypeModel saveWorkType(WorkTypeModel workTypeModel) {
		WorkTypeModel model = null;
		try{
			WorkTypeEntity entity = createWorkTypeEntity(workTypeModel);
			entity.setCreatedOn(utilService.getCurrentDate());
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity = masterTableDAO.saveWorkTypeEntity(entity);
			entity.setStatus(1);
			model = createWorkTypeModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveWorkType: "+e.getMessage());
		}
		return model;
	}


	@Override
	public WorkTypeModel updateWorkType(WorkTypeModel workTypeModel) {
		WorkTypeModel model = null;
		try{
			WorkTypeEntity entity = createWorkTypeEntity(workTypeModel);
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity = masterTableDAO.updateWorkTypeEntity(entity);
			model = createWorkTypeModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateWorkType: "+e.getMessage());
		}
		return model;
	}


	@Override
	public List<WorkTypeModel> getAllWorkType(int status) {
		List<WorkTypeModel> models = null;
		try{
			models = new ArrayList<WorkTypeModel>();
			List<WorkTypeEntity> entities = masterTableDAO.getAllWorkTypeEntities(status);
			//			entities.forEach(e -> {
			//				WorkTypeModel m= new WorkTypeModel();
			//				m = createWorkTypeModel(e);
			//			});
			for(WorkTypeEntity e: entities){
				WorkTypeModel m= new WorkTypeModel();
				m = createWorkTypeModel(e);
				models.add(m);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllWorkType: "+e.getMessage());
		}
		return models;
	}


	@Override
	public WorkTypeModel getWorkTypeById(int workTypeId) {
		WorkTypeModel model = null;
		try{
			WorkTypeEntity entity = masterTableDAO.getWorkTypeEntityById(workTypeId);
			model = createWorkTypeModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkTypeById: "+e.getMessage());
		}
		return model;
	}

	@Override
	public int deleteWorkType(int workTypeId){
		int value = 0;
		try{
			WorkTypeEntity entity = masterTableDAO.getWorkTypeEntityById(workTypeId);
			entity.setStatus(-1);
			entity = masterTableDAO.updateWorkTypeEntity(entity);
			if(entity.getStatus() == -1){
				value = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("deleteWorkType: "+e.getMessage());
		}
		return value;
	}

	private WorkTypeModel createWorkTypeModel(WorkTypeEntity entity){
		WorkTypeModel model = null;
		try{
			model = new WorkTypeModel();
			if(entity.getCreatedOn() != null){
				model.setCreatedOn(utilService.dateToString(entity.getCreatedOn()));
			}
			model.setDescription(entity.getDescription());
			model.setStatus(entity.getStatus());
			if(entity.getUpdatedOn() != null){
				model.setUpdatedOn(utilService.dateToString(entity.getUpdatedOn()));
			}
			model.setWorkType(entity.getWorkType());
			model.setWorkTypeId(entity.getWorkTypeId());
			model.setStatus(entity.getStatus());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createWorkTypeModel: "+e.getMessage());
		}

		return model;		
	}

	private WorkTypeEntity createWorkTypeEntity(WorkTypeModel model){
		WorkTypeEntity entity = null;
		try{
			entity = workTypeEntityFactory.createWorkTypeEntity();
			// Java-8 feature,Null Reference Template
			Optional<String> createDate = Optional.ofNullable(model.getCreatedOn());
			if(createDate.isPresent()){
				entity.setCreatedOn(utilService.stringToDate(model.getCreatedOn()));
			}
			//			if(model.getCreatedOn() != null){
			//				entity.setCreatedOn(utilService.stringToDate(model.getCreatedOn()));
			//			}
			entity.setDescription(model.getDescription());
			entity.setStatus(model.getStatus());
			Optional<String> updatedDate = Optional.ofNullable(model.getUpdatedOn());
			if(updatedDate.isPresent()){
				entity.setUpdatedOn(utilService.stringToDate(model.getUpdatedOn()));
			}

			//			if(entity.getUpdatedOn() != null){
			//				entity.setUpdatedOn(utilService.stringToDate(model.getUpdatedOn()));
			//			}
			entity.setWorkType(model.getWorkType());
			entity.setWorkTypeId(model.getWorkTypeId());
			entity.setStatus(1);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createWorkTypeModel: "+e.getMessage());
		}

		return entity;		
	}
	

	@Override
	public List<DocumentTypesModel> getAllDocumentTypes(int status) {
		List<DocumentTypesModel> models = null;
		try{
			models = new ArrayList<DocumentTypesModel>();
			List<DocumentTypesEntity> entities = masterTableDAO.getAllDocumentTypes(status);
			for(DocumentTypesEntity e: entities){
				DocumentTypesModel m = createDocumentTypesModel(e);
				models.add(m);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDocumentTypes: "+e.getMessage());
		}
		return models;
	}

	@Override
	public DocumentTypesModel saveDocumentTypes(DocumentTypesModel documentTypes) {
		DocumentTypesModel docTypes = null;
		try{
			Date date = utilService.getCurrentDate();
			documentTypes.setCreatedOn(utilService.dateToString(date));
			documentTypes.setUpdatedOn(utilService.dateToString(date));
			documentTypes.setStatus(1);
			DocumentTypesEntity entity = createDocumentTypesEntity(documentTypes);
			entity = masterTableDAO.saveDocumentTypes(entity);
			docTypes = createDocumentTypesModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveDocumentTypes: "+e.getMessage());
		}
		return docTypes;
	}

	@Override
	public DocumentTypesModel updateDocumentTypes(DocumentTypesModel documentTypes) {
		DocumentTypesModel docTypes = null;
		try{
			Date date = utilService.getCurrentDate();
			documentTypes.setCreatedOn(utilService.dateToString(date));
			documentTypes.setUpdatedOn(utilService.dateToString(date));
			documentTypes.setStatus(1);
			DocumentTypesEntity entity = createDocumentTypesEntity(documentTypes);
			entity = masterTableDAO.updateDocumentTypes(entity);
			docTypes = createDocumentTypesModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateDocumentTypes: "+e.getMessage());
		}
		return docTypes;
	}
	
	@Override
	public DocumentTypesModel getDocumentTypeById(int documentTypeId) {
		DocumentTypesModel docTypes = null;
		try{
			DocumentTypesEntity entity = masterTableDAO.getDocumentTypeById(documentTypeId);
			entity = masterTableDAO.updateDocumentTypes(entity);
			docTypes = createDocumentTypesModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDocumentTypeById: "+e.getMessage());
		}
		return docTypes;
	}

	@Override
	public int deleteDocumentTypesById(int documentTypeId){
		int status = 0;
		try{
			DocumentTypesEntity docType = masterTableDAO.getDocumentTypeById(documentTypeId);
			docType.setUpdatedOn(utilService.getTodaysDate());
			docType.setStatus(-1);
			docType = masterTableDAO.updateDocumentTypes(docType);
			if(docType.getStatus() == -1){
				status = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("deleteDocumentTypesById: "+e.getMessage());
		}
		return status;
	}


	private DocumentTypesModel createDocumentTypesModel(DocumentTypesEntity entity){
		DocumentTypesModel model = null;
		try{
			model = new DocumentTypesModel();
			model.setCreatedOn(utilService.dateToString(entity.getCreatedAt()));
			model.setDocumentTypeId(entity.getDocumentTypeId());
			model.setStatus(entity.getStatus());
			model.setType(entity.getType());
			model.setUpdatedOn(utilService.dateToString(entity.getUpdatedOn()));
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createDocumentTypesModel: "+e.getMessage());
		}
		return model;
	}
	
	private DocumentTypesEntity createDocumentTypesEntity(DocumentTypesModel model){
		DocumentTypesEntity  entity = null;
		try{
			entity = new DocumentTypesEntity();
			entity.setCreatedAt(utilService.stringDateToTimestamp(model.getCreatedOn()));
			entity.setDocumentTypeId(entity.getDocumentTypeId());
			entity.setStatus(entity.getStatus());
			entity.setType(entity.getType());
			entity.setUpdatedOn(utilService.stringToDate(model.getUpdatedOn()));
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createDocumentTypesModel: "+e.getMessage());
		}
		return entity;
		
	}


	@Override
	public boolean isDrawingSeriesExists(String drawingSeries) {
		// TODO Auto-generated method stub
		return false;
	}


	



}
