package com.yaz.alind.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yaz.alind.entity.DocumentNumberSeriesEntity;
import com.yaz.alind.entity.DocumentTypesEntity;
import com.yaz.alind.entity.WorkStatusEntity;
import com.yaz.alind.entity.WorkTypeEntity;

/**
 * 
 * @author Libu Mathew
 * Its for Managing master tables
 *
 */

@Repository
public class MasterTableDAOImpl implements MasterTableDAO {

	private static final Logger logger = LoggerFactory.getLogger(MasterTableDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	@Transactional
	public WorkStatusEntity saveWorkStatusEntity(
			WorkStatusEntity workStatusEntity) {
		WorkStatusEntity entity = null;
		try{
			this.sessionFactory.getCurrentSession().save(workStatusEntity);
			entity = workStatusEntity;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveWorkStatusEntity: "+e.getMessage());
		}
		return entity;
	}
	
	@Override
	@Transactional
	public WorkStatusEntity updateWorkStatusEntity(
			WorkStatusEntity workStatusEntity) {
		WorkStatusEntity entity = null;
		try{
//			System.out.println("MasterTableDAOImpl,updateWorkStatusEntity,id: "+workStatusEntity.getTaskStatusId());
			this.sessionFactory.getCurrentSession().update(workStatusEntity);
			entity = workStatusEntity;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateWorkStatusEntity: "+e.getMessage());
		}
		return entity;
	}

	@Override
	@Transactional
	public List<WorkStatusEntity> getAllWorkStatusEntity(int status) {
		List<WorkStatusEntity> entities = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(WorkStatusEntity.class);
			cr.add(Restrictions.eq("status", status));
			entities = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllWorkStatusEntity: "+e.getMessage());
		}
		return entities;
	}

	@Override
	@Transactional
	public WorkStatusEntity getWorkStatusEntityById(int workStatusId) {
		WorkStatusEntity entity = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(WorkStatusEntity.class);
			cr.add(Restrictions.eq("workStatusId", workStatusId));
			List<WorkStatusEntity> list = cr.list();
			if(list.size() > 0){
				entity = list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllWorkStatusEntity: "+e.getMessage());
		}
		return entity;
	}

	@Override
	@Transactional
	public WorkTypeEntity saveWorkTypeEntity(WorkTypeEntity workTypeEntity) {
		WorkTypeEntity entity = null;
		try{
			this.sessionFactory.getCurrentSession().save(workTypeEntity);
			entity = workTypeEntity;
			System.out.println("MasterDAO, saveWorkTypeEntity,id: "+workTypeEntity.getWorkTypeId());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveWorkTypeEntity: "+e.getMessage());
		}
		return entity;
	}

	@Override
	@Transactional
	public WorkTypeEntity updateWorkTypeEntity(WorkTypeEntity workTypeEntity) {
		WorkTypeEntity entity = null;
		try{
			System.out.println("MasterDAO, updateWorkTypeEntity,id: "+workTypeEntity.getWorkTypeId());
			this.sessionFactory.getCurrentSession().update(workTypeEntity);
			entity = workTypeEntity;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateWorkTypeEntity: "+e.getMessage());
		}
		return entity;
	}

	@Override
	@Transactional
	public List<WorkTypeEntity> getAllWorkTypeEntities(int status) {
		List<WorkTypeEntity> entities = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(WorkTypeEntity.class);
			cr.add(Restrictions.eq("status", status));
			entities = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllWorkTypeEntities: "+e.getMessage());
		}
		return entities;
	}

	@Override
	@Transactional
	public WorkTypeEntity getWorkTypeEntityById(int workTypeId) {
		WorkTypeEntity entity = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(WorkTypeEntity.class);
			cr.add(Restrictions.eq("workTypeId", workTypeId));
			List<WorkTypeEntity> list = cr.list();
			if(list.size()>0){
				entity = list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkTypeEntityById: "+e.getMessage());
		}
		return entity;
	}

	@Override
	public List<DocumentTypesEntity> getAllDocumentTypes(int status) {
		List<DocumentTypesEntity> documentTypes = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(DocumentTypesEntity.class);
			cr.add(Restrictions.eq("status", status));
			documentTypes = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDocumentTypes: "+e.getMessage());
		}

		return documentTypes;
	}


	@Override
	public DocumentTypesEntity saveDocumentTypes(DocumentTypesEntity documentTypes) {
		DocumentTypesEntity docTypes = null;
		try{
			this.sessionFactory.getCurrentSession().save(documentTypes);
			docTypes = documentTypes;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveDocumentTypes: "+e.getMessage());
		}
		return docTypes;
	}

	@Override
	public DocumentTypesEntity updateDocumentTypes(DocumentTypesEntity documentTypes) {
		DocumentTypesEntity docTypes = null;
		try{
			this.sessionFactory.getCurrentSession().update(documentTypes);
			docTypes = documentTypes;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateDocumentTypes: "+e.getMessage());
		}
		return docTypes;
	}

	@Override
	public boolean isDrawingSeriesExists(String drawingSeries){
		boolean status = false;
		try{

			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(DocumentTypesEntity.class);
			cr.add(Restrictions.eq("drawingSeries", drawingSeries));
			List list = cr.list();
			if(list.size() > 0){
				status = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("isDrawingSeriesExists: "+e.getMessage());
		}
		return status;
	}


	@Override
	public DocumentTypesEntity getDocumentTypeById(int documentTypeId) {
		DocumentTypesEntity docTypes = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(DocumentTypesEntity.class);
			cr.add(Restrictions.eq("documentTypeId", documentTypeId));
			docTypes = (DocumentTypesEntity) cr.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDocumentTypeById: "+e.getMessage());
		}
		return docTypes;
	}
	
}
