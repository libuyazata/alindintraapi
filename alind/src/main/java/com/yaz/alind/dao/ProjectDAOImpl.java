package com.yaz.alind.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yaz.alind.entity.DocumentHistoryEntity;
import com.yaz.alind.entity.DocumentNumberSeriesEntity;
import com.yaz.alind.entity.DocumentTypesEntity;
import com.yaz.alind.entity.DocumentUsersEntity;
import com.yaz.alind.entity.ProjectDocumentEntity;
import com.yaz.alind.entity.ProjectInfoEntity;
import com.yaz.alind.entity.ProjectStatusEntity;
import com.yaz.alind.entity.SubTaskEntity;
import com.yaz.alind.entity.WorkDetailsEntity;

@Repository
public class ProjectDAOImpl implements ProjectDAO {

	private static final Logger logger = LoggerFactory.getLogger(ProjectDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public ProjectInfoEntity saveOrUpdateProject(ProjectInfoEntity projectInfo) {
		ProjectInfoEntity prInfo = null;
		try{
			this.sessionFactory.getCurrentSession().saveOrUpdate(projectInfo);
			prInfo = projectInfo;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateProject: "+e.getMessage());
		}
		return prInfo;
	}


	@Override
	@Transactional
	public List<ProjectInfoEntity> getAllProject(int departmentId) {
		List<ProjectInfoEntity> projectInfos = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(ProjectInfoEntity.class);
			cr.addOrder(Order.desc("createdOn"));
			if(departmentId > 0){
				cr.add(Restrictions.eq("departmentId", departmentId));
			}
			projectInfos = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllProject: "+e.getMessage());
		}
		return projectInfos;
	}


	@Override
	@Transactional
	public ProjectInfoEntity getProjectInfoById(int projectId) {
		ProjectInfoEntity projectInfo = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(ProjectInfoEntity.class);
			cr.add(Restrictions.eq("projectId", projectId));
			projectInfo = (ProjectInfoEntity) cr.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getProjectInfoById: "+e.getMessage());
		}
		return projectInfo;
	}

	@Override
	@Transactional
	public List<DocumentTypesEntity> getAllDocumentTypes() {
		List<DocumentTypesEntity> documentTypes = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(DocumentTypesEntity.class);
			documentTypes = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDocumentTypes: "+e.getMessage());
		}

		return documentTypes;
	}


	@Override
	@Transactional
	public DocumentTypesEntity saveOrUpdateDocumentTypes(DocumentTypesEntity documentTypes) {
		DocumentTypesEntity docTypes = null;
		try{
			this.sessionFactory.getCurrentSession().saveOrUpdate(documentTypes);
			docTypes = documentTypes;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateDocumentTypes: "+e.getMessage());
		}
		return docTypes;
	}

	@Override
	@Transactional
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
	@Transactional
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


	//	@Override
	//	@Transactional
	//	public List<ProjectDocument> getAllDocumentByProjectId(int projectId) {
	//		List<ProjectDocument> documents = null;
	//		try{
	//			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(ProjectDocument.class);
	//			cr.add(Restrictions.eq("projectId", projectId));
	//			documents = cr.list();
	//		}catch(Exception e){
	//			e.printStackTrace();
	//			logger.error("getDocumentTypeById: "+e.getMessage());
	//		}
	//		return documents;
	//	}


	@Override
	@Transactional
	public ProjectDocumentEntity saveOrUpdateDocument(ProjectDocumentEntity document) {
		ProjectDocumentEntity doc = null;
		try{
			this.sessionFactory.getCurrentSession().saveOrUpdate(document);
			doc = document;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateDocument: "+e.getMessage());
		}
		return doc;
	}


	//	@Override
	//	@Transactional
	//	public ProjectDocument getDocumentById(int documentId) {
	//		ProjectDocument document = null;
	//		try{
	//			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(ProjectDocument.class);
	//			cr.add(Restrictions.eq("documentId", documentId));
	//			document = (ProjectDocument) cr.list().get(0);
	//		}catch(Exception e){
	//			e.printStackTrace();
	//			logger.error("getDocumentById: "+e.getMessage());
	//		}
	//		return document;
	//	}


	@Override
	@Transactional
	public List<DocumentHistoryEntity> getAllDocumentHistories(int documentId,int departmentId ) {
		List<DocumentHistoryEntity> documentHistories = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(DocumentHistoryEntity.class);
			if(documentId > 0){
				cr.add(Restrictions.eq("documentId", documentId));
			}
			if(departmentId >0){
				cr.add(Restrictions.eq("departmentId", departmentId));
			}
			documentHistories =  cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDocumentHistories: "+e.getMessage());
		}
		return documentHistories;
	}


	@Override
	@Transactional
	public DocumentHistoryEntity saveDocumentHistory(DocumentHistoryEntity documentHistory) {
		DocumentHistoryEntity docuHistory = null;
		try{
			this.sessionFactory.getCurrentSession().save(documentHistory);
			docuHistory = documentHistory;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveDocumentHistory: "+e.getMessage());
		}
		return docuHistory;
	}


	@Override
	@Transactional
	public List<DocumentUsersEntity> getAllDocumentUsers(int departmentId,
			int projectDocumentId, int employeeId) {
		List<DocumentUsersEntity>  documentUsers = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(DocumentUsersEntity.class);
			cr.addOrder(Order.desc("createdOn"));
			if(departmentId > 0){
				cr.add(Restrictions.eq("departmentId", departmentId));
			}if(projectDocumentId > 0){
				cr.add(Restrictions.eq("projectDocumentId", projectDocumentId));
			}if(employeeId > 0){
				cr.add(Restrictions.eq("employeeId", employeeId));
			}
			documentUsers = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDocumentUsers: "+e.getMessage());
		}
		return documentUsers;
	}


	@Override
	@Transactional
	public DocumentUsersEntity saveOrUpdateDocumentUsers(DocumentUsersEntity documentUsers) {
		DocumentUsersEntity docUsers = null;
		try{
			this.sessionFactory.getCurrentSession().saveOrUpdate(documentUsers);
			docUsers = documentUsers;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateDocumentUsers: "+e.getMessage());
		}
		return docUsers;
	}


	@Override
	@Transactional
	public DocumentUsersEntity getDocumentUsersById(int documentUserId) {
		DocumentUsersEntity docUsers = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(DocumentUsersEntity.class);
			cr.add(Restrictions.eq("documentUserId", documentUserId));
			docUsers = (DocumentUsersEntity) cr.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateDocumentUsers: "+e.getMessage());
		}
		return docUsers;
	}


	@Override
	@Transactional
	public List<ProjectDocumentEntity> getAllDocumentByProjectId(int projectId,int documentTypeId) {
		List<ProjectDocumentEntity> documents = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(ProjectDocumentEntity.class);
			cr.addOrder(Order.desc("modifiedOn"));
			if(projectId > 0){
				cr.add(Restrictions.eq("projectId", projectId));
			}
			if(documentTypeId > 0){
				cr.add(Restrictions.eq("documentTypeId", documentTypeId));
			}
			documents = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDocumentByProjectId: "+e.getMessage());
		}
		return documents;
	}


	@Override
	@Transactional
	public ProjectDocumentEntity getProjectDocumentById(int projectDocumentId) {
		ProjectDocumentEntity projectDocument = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(ProjectDocumentEntity.class);
			cr.add(Restrictions.eq("projectDocumentId", projectDocumentId));
			List list =  cr.list();
			projectDocument = (ProjectDocumentEntity) list.get(0);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getProjectDocumentById: "+e.getMessage());
		}
		return projectDocument;
	}


	@Override
	@Transactional
	public List<DocumentUsersEntity> getDocumentUsersList(int projectDocumentId) {
		List<DocumentUsersEntity> documentUsers = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(DocumentUsersEntity.class);
			cr.add(Restrictions.eq("projectDocumentId", projectDocumentId));
			documentUsers =  cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDocumentUsersList: "+e.getMessage());
		}
		return documentUsers;
	}


	@Override
	@Transactional
	public List<ProjectDocumentEntity> getProjectDocumentsById(int projectId,int documentTypeId) {
		List<ProjectDocumentEntity> projectDocuments = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(ProjectDocumentEntity.class);
			cr.add(Restrictions.eq("projectId", projectId));
			if(documentTypeId > 0){
				cr.add(Restrictions.eq("documentTypeId", documentTypeId));
			}
			projectDocuments = cr.list();

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getProjectDocumentsById: "+e.getMessage());
		}
		return projectDocuments;
	}


	@Override
	@Transactional
	public List<ProjectStatusEntity> getAllProjectStatus() {
		List<ProjectStatusEntity> projectStatus = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(ProjectStatusEntity.class);
			projectStatus = cr.list();

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllProjectStatus: "+e.getMessage());
		}
		return projectStatus;
	}


	@Override
	@Transactional
	public List<ProjectDocumentEntity> getAllPjtDocByDeptId(int departmentId) {
		List<ProjectDocumentEntity> projectDocuments = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(ProjectDocumentEntity.class);
			cr.addOrder(Order.desc("createdOn"));
			cr.add(Restrictions.eq("departmentId", departmentId));
			projectDocuments = cr.list();

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllPjtDocByDeptId: "+e.getMessage());
		}
		return projectDocuments;
	}


	@Override
	@Transactional
	public List<DocumentUsersEntity> getAllDocumentUserById(int employeeId) {
		List<DocumentUsersEntity> documentUsers = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(DocumentUsersEntity.class);
			cr.add(Restrictions.eq("employeeId", employeeId));
			documentUsers = cr.list();

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDocumentUserById: "+e.getMessage());
		}
		return documentUsers;
	}


	@Override
	@Transactional
	public DocumentNumberSeriesEntity saveOrUpdateDocumentNumberSeries(
			DocumentNumberSeriesEntity documentNumberSeries) {
		DocumentNumberSeriesEntity docuSeries = null;
		try{
			this.sessionFactory.getCurrentSession().saveOrUpdate(documentNumberSeries);
			docuSeries = documentNumberSeries;

		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateDocumentNumberSeries: "+e.getMessage());
		}
		return docuSeries;
	}


	@Override
	@Transactional
	public DocumentNumberSeriesEntity getDocumentNumberSeriesByDocumentTypeId(int documentTypeId) {
		DocumentNumberSeriesEntity documentNumberSeries = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(DocumentNumberSeriesEntity.class);
			cr.add(Restrictions.eq("documentTypeId", documentTypeId));
			List list = cr.list();
			if(list.size() > 0){
				documentNumberSeries = (DocumentNumberSeriesEntity) cr.list().get(0);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDocumentNumberSeriesByDocumentTypeId: "+e.getMessage());
		}
		return documentNumberSeries;
	}


	@Override
	@Transactional
	public WorkDetailsEntity saveWorkDetails(
			WorkDetailsEntity workDetailsEntity) {
		WorkDetailsEntity work = null;
		try{
			//			this.sessionFactory.getCurrentSession().save(workDetailsEntity);
			Session session = this.sessionFactory.openSession();
			int id = (int) session.save(workDetailsEntity);
			workDetailsEntity.setWorkDetailsId(id);
			System.out.println("DAO,saveWorkDetails,Id: "+workDetailsEntity.getWorkDetailsId()+", id: "+id);
			work = workDetailsEntity;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateWorkDetails: "+e.getMessage());
		}
		//		finally{
		//			this.sessionFactory.getCurrentSession().flush();
		//			work = workDetailsEntity;
		//		}
		return workDetailsEntity;
	}

	@Override
	@Transactional
	public WorkDetailsEntity updateWorkDetails(
			WorkDetailsEntity workDetailsEntity) {
		WorkDetailsEntity entity = null;
		try{
			this.sessionFactory.getCurrentSession().update(workDetailsEntity);
//			System.out.println("DAO,updateWorkDetails,Id: "+workDetailsEntity.getWorkDetailsId());
			//			System.out.println("DAO,updateWorkDetails,Id: "+workDetailsEntity.getWorkDetailsId()+
			//					",Pjt Co-ordinate Emp name: "+workDetailsEntity.getProjectCoOrdinatorEmp().getFirstName());
//			entity = workDetailsEntity;
//			tx.commit();

		}catch(Exception e){
//		    tx.rollback();
			e.printStackTrace();
			logger.error("updateWorkDetails: "+e.getMessage());
		}
		finally{
//			session.setFlushMode(FlushMode.ALWAYS );
			entity = workDetailsEntity;
//			System.out.println("DAO,updateWorkDetails,getDescription: "+entity.getDescription());
		}
		return entity;
	}


	@Override
	@Transactional
	public WorkDetailsEntity getWorkDetailsEntityById(int workDetailsId) {
		WorkDetailsEntity workDetailsEntity = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(WorkDetailsEntity.class);
			cr.add(Restrictions.eq("workDetailsId", workDetailsId));
			List<WorkDetailsEntity> list = cr.list();
			if(list.size() > 0){
				workDetailsEntity = list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkDetailsEntityById: "+e.getMessage());
		}
		return workDetailsEntity;
	}


	@Override
	@Transactional
	public List<WorkDetailsEntity> getWorkDetailsEntitiesByDeptId(
			int departmentId,int status) {
		List<WorkDetailsEntity> workDetailsEntities = null;
		try{
			Criteria cr=this.sessionFactory.getCurrentSession().createCriteria(WorkDetailsEntity.class);
			cr.addOrder(Order.desc("createdOn"));
			cr.add(Restrictions.eq("departmentId", departmentId));
			cr.add(Restrictions.eq("status", status));
			workDetailsEntities = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkDetailsEntitiesByDeptId: "+e.getMessage());
		}
		return workDetailsEntities;
	}

	@Override
	@Transactional
	public SubTaskEntity saveSubTaskEntity(SubTaskEntity subTaskEntity) {
		SubTaskEntity entity = null;
		try{
			this.sessionFactory.getCurrentSession().save(subTaskEntity);
			entity = subTaskEntity;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveSubTaskEntity: "+e.getMessage());
		}
		return entity;
	}


	@Override
	@Transactional
	public SubTaskEntity updateSubTaskEntity(SubTaskEntity subTaskEntity) {
		SubTaskEntity entity = null;
		try{
			this.sessionFactory.getCurrentSession().update(subTaskEntity);
			entity = subTaskEntity;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateSubTaskEntity: "+e.getMessage());
		}
		return entity;
	}


	@Override
	@Transactional
	public SubTaskEntity getSubTaskEntityById(int subTaskId) {
		SubTaskEntity entity = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(SubTaskEntity.class);
			cr.add(Restrictions.eq("subTaskId", subTaskId));
			List<SubTaskEntity> list = cr.list();
			if(list.size() > 0){
				entity = list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getSubTaskEntityById: "+e.getMessage());
		}
		return entity;
	}


	@Override
	@Transactional
	public List<SubTaskEntity> getSubTaskEntitiesByWorkId(int workDetailsId,int status) {
		List<SubTaskEntity> entities = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(SubTaskEntity.class);
			cr.add(Restrictions.eq("workDetailsId", workDetailsId));
			cr.add(Restrictions.eq("status", status));
			entities = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getSubTaskEntitiesByWorkId: "+e.getMessage());
		}
		return entities;
	}


	@Override
	@Transactional
	public List<WorkDetailsEntity> getWorkDetailsBySearch(String searchKeyWord,
			int workTypeId, Date startDate, Date endDate) {
		List<WorkDetailsEntity> workDetailsEntities = null;
		try{
//			System.out.println("DAO, getWorkDetailsBySearch,searchKeyWord: "+searchKeyWord
//					+", startDate: "+startDate+", endDate: "+endDate);
			workDetailsEntities = new ArrayList<WorkDetailsEntity>();
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(WorkDetailsEntity.class,"workDetails");
			cr.createAlias("departmentEntity", "departmentEntity"); 
			cr.createAlias("projectCoOrdinatorEmp", "projectCoOrdinatorEmp");
			cr.createAlias("createdEmp", "createdEmp");
			cr.createAlias("workStatusEntity", "workStatusEntity");

			cr.add(Restrictions.eq("status", 1));

			if(workTypeId != 0){
				cr.add(Restrictions.eq("workTypeId", workTypeId));
			}
			if(startDate != null){
				cr.add(Restrictions.ge("startDate", startDate) );
			}
			if(endDate != null){
				cr.add(Restrictions.lt("endDate", endDate) );
			}

			if(!searchKeyWord.isEmpty()){
//				System.out.println("DAO, getWorkDetailsBySearch,searchKeyWord: "+searchKeyWord);
				Criterion description = Restrictions.ilike("workDetails.description", searchKeyWord, MatchMode.ANYWHERE);
				Criterion workName = Restrictions.ilike("workDetails.workName", searchKeyWord, MatchMode.ANYWHERE);

				Criterion departmentName = Restrictions.ilike("departmentEntity.departmentName", searchKeyWord, MatchMode.ANYWHERE);
				Criterion pjtCoEmpCode = Restrictions.ilike("projectCoOrdinatorEmp.empCode", searchKeyWord, MatchMode.ANYWHERE);
				Criterion pjtCoFirstName = Restrictions.ilike("projectCoOrdinatorEmp.firstName", searchKeyWord, MatchMode.ANYWHERE);
				Criterion pjtCoLastName = Restrictions.ilike("projectCoOrdinatorEmp.lastName", searchKeyWord, MatchMode.ANYWHERE);
				Criterion workStatusName = Restrictions.ilike("workStatusEntity.workStatusName", searchKeyWord, MatchMode.ANYWHERE);
				Criterion createEmpCode = Restrictions.ilike("createdEmp.empCode", searchKeyWord, MatchMode.ANYWHERE);
				Criterion createFirstName = Restrictions.ilike("createdEmp.firstName", searchKeyWord, MatchMode.ANYWHERE);
				Criterion createLastName = Restrictions.ilike("createdEmp.lastName", searchKeyWord, MatchMode.ANYWHERE);

				Disjunction disjunction = Restrictions.disjunction();
				disjunction.add(description);
				disjunction.add(workName);
				disjunction.add(departmentName);
				disjunction.add(pjtCoEmpCode);
				disjunction.add(pjtCoFirstName);
				disjunction.add(pjtCoLastName);
				disjunction.add(workStatusName);
				disjunction.add(createEmpCode);
				disjunction.add(createFirstName);
				disjunction.add(createLastName);

				cr.add(disjunction);
			}
			workDetailsEntities = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkDetailsBySearch: "+e.getMessage());
		}
		return workDetailsEntities;
	}



}
