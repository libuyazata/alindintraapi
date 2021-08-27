package com.yaz.alind.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yaz.alind.entity.DocumentHistoryEntity;
import com.yaz.alind.entity.DocumentTypesEntity;
import com.yaz.alind.entity.DocumentUsersEntity;
import com.yaz.alind.entity.ProjectDocumentEntity;
import com.yaz.alind.entity.ProjectInfoEntity;
import com.yaz.alind.entity.SubTaskEntity;
import com.yaz.alind.entity.WorkDetailsEntity;
import com.yaz.alind.model.ui.SubTaskModel;
import com.yaz.alind.model.ui.WorkDetailsModel;

public interface ProjectService {

	public ProjectInfoEntity saveOrUpdateProject(ProjectInfoEntity projectInfo);
	public List<ProjectInfoEntity> getAllProject(int departmentId,int projectId,String token);
	public ProjectInfoEntity getProjectInfoById(int projectId);
	
	public List<DocumentTypesEntity> getAllDocumentTypes();
	public DocumentTypesEntity saveOrUpdateDocumentTypes(DocumentTypesEntity documentTypes);
	public DocumentTypesEntity getDocumentTypeById(int documentTypeId);
	public List<ProjectDocumentEntity> getAllDocumentByProjectId(int projectId,int documentTypeId,String realPath,String token );
	public ProjectDocumentEntity saveOrUpdateDocument(ProjectDocumentEntity document);
	public ProjectDocumentEntity getDocumentById(int documentId);
	
	public List<DocumentHistoryEntity> getAllDocumentHistories(int documentId,int departmentId );
	public DocumentHistoryEntity saveDocumentHistory(DocumentHistoryEntity documentHistory);
	
	public List<DocumentUsersEntity> getAllDocumentUsers(int departmentId,int documentId,int employeeId);
	public DocumentUsersEntity saveOrUpdateDocumentUsers(DocumentUsersEntity documentUsers);
	public DocumentUsersEntity getDocumentUsersById(int documentUserId);
	public ProjectDocumentEntity uploadProjectDocument(MultipartFile fileDetails,int employeeId,
			int documentTypeId,String documentName,String contextPath,int projectId,
			int projectDocumentId,String description);
	public InputStream getProjectDocument(int projectDocumentId,int employeeId);
	public ProjectDocumentEntity getProjectDocumentById(int documentId);
	public List<DocumentUsersEntity> getDocumentUsersList(int projectDocumentId);
	public List<ProjectDocumentEntity> getProjectDocumentsById(int projectId,int documentTypeId);
	public List<DocumentUsersEntity> getAllDocumentUserById(int employeeId);
	public List<ProjectInfoEntity> getAllProjctByEmpId(int employeeId);
	
	public WorkDetailsModel saveWorkDetails(WorkDetailsModel workDetailsModel);
	public WorkDetailsModel updateWorkDetails(WorkDetailsModel workDetailsModel);
	public WorkDetailsModel getWorkDetailsById(int workDetailsId);
	public List<WorkDetailsModel> getWorkDetailsByDeptId(int departmentId,int status);
	public int deleteWorkDetailsById(int workDetailsId);
	public List<WorkDetailsModel> getWorkDetailsBySearch(String searchKeyWord,
			int workTypeId, String startDate, String endDate);
	
	public SubTaskModel saveSubTask(SubTaskModel model);
	public SubTaskModel updateSubTask(SubTaskModel model);
	public SubTaskModel getSubTaskById(int subTaskId);
    public List<SubTaskModel> getSubTaskByWorkId(int workDetailsId,int status);
    public int deleteSubTask(int subTaskId);
	
}
