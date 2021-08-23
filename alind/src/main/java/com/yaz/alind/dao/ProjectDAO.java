package com.yaz.alind.dao;

import java.util.Date;
import java.util.List;

import com.yaz.alind.entity.DocumentHistoryEntity;
import com.yaz.alind.entity.DocumentNumberSeriesEntity;
import com.yaz.alind.entity.DocumentTypesEntity;
import com.yaz.alind.entity.DocumentUsersEntity;
import com.yaz.alind.entity.ProjectDocumentEntity;
import com.yaz.alind.entity.ProjectInfoEntity;
import com.yaz.alind.entity.ProjectStatusEntity;
import com.yaz.alind.entity.SubTaskEntity;
import com.yaz.alind.entity.WorkDetailsEntity;

public interface ProjectDAO {

	public ProjectInfoEntity saveOrUpdateProject(ProjectInfoEntity projectInfo);
	public List<ProjectInfoEntity> getAllProject(int departmentId);
	public ProjectInfoEntity getProjectInfoById(int projectId);
	
	public List<DocumentTypesEntity> getAllDocumentTypes();
	public DocumentTypesEntity saveOrUpdateDocumentTypes(DocumentTypesEntity documentTypes);
	public DocumentTypesEntity getDocumentTypeById(int documentTypeId);
	public boolean isDrawingSeriesExists(String drawingSeries);
	
//	public List<ProjectDocument> getAllDocumentByProjectId(int projectId );
	public ProjectDocumentEntity saveOrUpdateDocument(ProjectDocumentEntity document);
	public ProjectDocumentEntity getProjectDocumentById(int documentId);
	public List<ProjectDocumentEntity> getAllDocumentByProjectId(int projectId,int documentTypeId);
	
	public List<DocumentHistoryEntity> getAllDocumentHistories(int documentId,int departmentId );
	public DocumentHistoryEntity saveDocumentHistory(DocumentHistoryEntity documentHistory);
	
	public List<DocumentUsersEntity> getAllDocumentUsers(int departmentId,int documentId,int employeeId);
	public DocumentUsersEntity saveOrUpdateDocumentUsers(DocumentUsersEntity documentUsers);
	public DocumentUsersEntity getDocumentUsersById(int documentUserId);
	
	public List<DocumentUsersEntity> getDocumentUsersList(int projectDocumentId);
	public List<ProjectDocumentEntity> getProjectDocumentsById(int projectId,int documentTypeId);
	public List<ProjectStatusEntity> getAllProjectStatus();
	public List<ProjectDocumentEntity> getAllPjtDocByDeptId(int departmentId);
	public List<DocumentUsersEntity> getAllDocumentUserById(int employeeId);
	
	public DocumentNumberSeriesEntity saveOrUpdateDocumentNumberSeries(DocumentNumberSeriesEntity documentNumberSeries);
	public DocumentNumberSeriesEntity getDocumentNumberSeriesByDocumentTypeId(int documentTypeId);
	
	public WorkDetailsEntity saveWorkDetails(WorkDetailsEntity workDetailsEntity);
	public WorkDetailsEntity updateWorkDetails(WorkDetailsEntity workDetailsEntity);
	public WorkDetailsEntity getWorkDetailsEntityById(int workDetailsId);
	public List<WorkDetailsEntity> getWorkDetailsEntitiesByDeptId(int departmentId);
	public List<WorkDetailsEntity> getWorkDetailsBySearch(String searchKeyWord, int workTypeId,Date startDate,Date endDate);
	
	
	public SubTaskEntity saveSubTaskEntity(SubTaskEntity subTaskEntity);
	public SubTaskEntity updateSubTaskEntity(SubTaskEntity subTaskEntity);
	public SubTaskEntity getSubTaskEntityById(int subTaskId);
    public List<SubTaskEntity> getSubTaskEntitiesByWorkId(int workDetailsId,int status);
	
}
