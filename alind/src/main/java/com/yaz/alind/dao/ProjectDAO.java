package com.yaz.alind.dao;

import java.util.Date;
import java.util.List;

import com.yaz.alind.entity.DepartmentCommunicationMessagesEntity;
import com.yaz.alind.entity.DepartmentGeneralMessageEntity;
import com.yaz.alind.entity.DocumentHistoryEntity;
import com.yaz.alind.entity.DocumentNumberSeriesEntity;
import com.yaz.alind.entity.DocumentUsersEntity;
import com.yaz.alind.entity.EmployeeTaskAllocationEntity;
import com.yaz.alind.entity.GeneralMessageAttachmentEntity;
import com.yaz.alind.entity.GeneralMessageEntity;
import com.yaz.alind.entity.InterCommRefNoEntity;
import com.yaz.alind.entity.InterOfficeCommunicationEntity;
import com.yaz.alind.entity.ProjectDocumentEntity;
import com.yaz.alind.entity.ProjectInfoEntity;
import com.yaz.alind.entity.ProjectStatusEntity;
import com.yaz.alind.entity.SubTaskEntity;
import com.yaz.alind.entity.WorkDetailsEntity;
import com.yaz.alind.entity.WorkDocumentEntity;
import com.yaz.alind.entity.WorkIssuedDetailsEntity;
import com.yaz.alind.entity.WorkMessageAttachmentEntity;
import com.yaz.alind.model.ui.GeneralMessageListModel;
import com.yaz.alind.model.ui.InterOfficeCommunicationListModel;
import com.yaz.alind.model.ui.WorkDetailsModelList;

public interface ProjectDAO {

	public ProjectInfoEntity saveOrUpdateProject(ProjectInfoEntity projectInfo);
	public List<ProjectInfoEntity> getAllProject(int departmentId);
	public ProjectInfoEntity getProjectInfoById(int projectId);

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
	public DocumentNumberSeriesEntity updateDocumentNumberSeries
	(DocumentNumberSeriesEntity documentNumberSeries);

	public WorkDetailsEntity saveWorkDetails(WorkDetailsEntity workDetailsEntity);
	public WorkDetailsEntity updateWorkDetails(WorkDetailsEntity workDetailsEntity);
	public WorkDetailsEntity getWorkDetailsEntityById(int workDetailsId);
	public List<WorkDetailsEntity> getWorkDetailsEntitiesByDeptId(int departmentId,int status);
//	public List<WorkDetailsEntity> getWorkDetailsEntitiesByDeptId(int departmentId,int status,
//			int pageNo,int pageCount);
	public WorkDetailsModelList getWorkDetailsEntitiesByDeptId(int departmentId,int status,
			int pageNo,int pageCount);
	public List<WorkDetailsEntity> getWorkDetailsBySearch(String searchKeyWord, int workTypeId,String departmentName,
			Date startDate,Date endDate);
	public WorkDetailsModelList searchWorkDetails(String searchKeyWord, int workTypeId,String deptmentName,
			Date startDate,Date endDate,int pageNo,	int pageCount);
	public List<WorkDetailsEntity> getWorkDetailsByDate(Date startDate,Date endDate,int departmentId);

	public SubTaskEntity saveSubTaskEntity(SubTaskEntity subTaskEntity);
	public SubTaskEntity updateSubTaskEntity(SubTaskEntity subTaskEntity);
	public SubTaskEntity getSubTaskEntityById(int subTaskId);
	public List<SubTaskEntity> getSubTaskEntitiesByWorkId(int workDetailsId,int status);

	public WorkDocumentEntity saveWorkDocument(WorkDocumentEntity workDocumentEntity);
	public WorkDocumentEntity updateWorkDocument(WorkDocumentEntity workDocumentEntity);
	public WorkDocumentEntity getWorkDocumentById(int workDocumentId);
	public List<WorkDocumentEntity> getWorkDocumentByWorkDetailsId(int workDetailsId);
	public List<WorkDocumentEntity> getWorkDocumentBySubTaskId(int subTaskId);
	public List<WorkDocumentEntity> getAllWorkDocumentByDepartMentId(int departmentId);
	public WorkDocumentEntity getLatestWorkDocument(int subTaskId,int documentCategoryId);

	public EmployeeTaskAllocationEntity saveEmployeeTaskAllocation(EmployeeTaskAllocationEntity employeeTaskAllocation);
	public EmployeeTaskAllocationEntity updateEmployeeTaskAllocation(EmployeeTaskAllocationEntity employeeTaskAllocation);
	public EmployeeTaskAllocationEntity getEmployeeTaskAllocationById(int empTaskAllocationId);
	public List<EmployeeTaskAllocationEntity> getAllEmployeeTaskAllocationBySubTaskId(int subTaskId);
	public List<EmployeeTaskAllocationEntity> getAllEmployeeTaskAllocationByWorkDetailsId(int workDetailsId);

	public WorkIssuedDetailsEntity saveWorkIssuedDetails(WorkIssuedDetailsEntity workIssuedDetails);
	public WorkIssuedDetailsEntity updateWorkIssuedDetails(WorkIssuedDetailsEntity workIssuedDetails);
	public WorkIssuedDetailsEntity getWorkIssuedDetailsEntity(int workIssuedId);
	public List<WorkIssuedDetailsEntity> getWorkIssuedDetailsByDeptId(int departmentId );
	public List<WorkIssuedDetailsEntity> getWorkIssuedDetailsByWorkId(int workDetailsId);

	//Inter Office Communication
	public InterOfficeCommunicationEntity saveInterOfficeCommunicationEntity(InterOfficeCommunicationEntity entity);
	
	public InterOfficeCommunicationEntity updateInterOfficeCommunicationEntity(InterOfficeCommunicationEntity entity);
	public InterOfficeCommunicationEntity getCommunicationEntityById(int officeCommunicationId);
	public List<InterOfficeCommunicationEntity> getCommunicationEntityBySubTaskId(int subTaskId);
	public List<InterOfficeCommunicationEntity> getCommunicationEntityByWorkId(int workDetailsId);
	public List<InterOfficeCommunicationEntity> getCommunicationEntityByWorkList(List<WorkDetailsEntity> workList);
	public List<InterOfficeCommunicationEntity> getCommunicationEntityByDeptId(int departmentId);
	public List<WorkMessageAttachmentEntity> getWorkWorkMessageAttachmentByOffComId(int officeCommunicationId);

	public List<DepartmentCommunicationMessagesEntity> saveDepartmentCommunicationMessages
	(List<DepartmentCommunicationMessagesEntity> deptMessages);
	public DepartmentCommunicationMessagesEntity updateDepartmentCommunicationMessage
	(DepartmentCommunicationMessagesEntity deptMessage);
	public List<DepartmentCommunicationMessagesEntity> getDepartmentCommunicationMessagesByDeptId
	(int departmentId);
//	public List<DepartmentCommunicationMessagesEntity> getDepartmentCommunicationMessagesByDeptId
//	(int departmentId,int pageNo, int pageCount);
	public InterOfficeCommunicationListModel getDepartmentCommunicationMessagesByDeptId
	(int departmentId,int pageNo, int pageCount);
	
	public DepartmentCommunicationMessagesEntity getDepartmentCommunicationMessagesById(int deptCommId);
	public List<DepartmentCommunicationMessagesEntity> getDepartmentCommunicationMessagesByOffCommId
	(int officeCommunicationId);
	public List<InterOfficeCommunicationEntity> searchInterDeptCommList(String searchKeyWord,
			Date startDate, Date endDate,int departmentId);
	
	public InterOfficeCommunicationListModel searchInterDeptCommList(String searchKeyWord,
			Date startDate, Date endDate,int departmentId,int pageNo, int pageCount);
	
	public WorkMessageAttachmentEntity saveWorkMessageAttachment(WorkMessageAttachmentEntity entity);
	public List<WorkMessageAttachmentEntity> getWorkMessageAttachmentByByOffCommId(int officeCommunicationId); 
	public int getInterOfficeMessageCountByDeptId(int departmentId);
//	public List<InterOfficeCommunicationEntity> getCommunicationEntityByDeptId(int departmentId,
//			int pageNo, int pageCount);
	public InterOfficeCommunicationListModel getCommunicationEntityByDeptId(int departmentId,
			int pageNo, int pageCount);
	public int getInboxWorkMessagesCount(int departmentId);
	
    public InterCommRefNoEntity updateInterCommRefNo(InterCommRefNoEntity comRefNo);
    public InterCommRefNoEntity getInterCommRefByDeptId(int departmentId);
    
    public GeneralMessageEntity saveGeneralMessage(GeneralMessageEntity entity);
    public GeneralMessageEntity updateGeneralMessage(GeneralMessageEntity entity);
    public GeneralMessageEntity getGeneralMessageById(int genMessageId);
    public List<GeneralMessageEntity> getGeneralMessageListByDeptId(int departmentId);
//    public List<GeneralMessageEntity> getGeneralMessageListByDeptId(int departmentId,
//    		int pageNo, int pageCount);
    public GeneralMessageListModel getGeneralMessageListByDeptId(int departmentId,
			int pageNo, int pageCount);
    public int getGeneralInboxMessageCountByDeptId(int departmentId);
    public int getGeneralMessageCountByDeptId(int departmentId);
    public List<GeneralMessageEntity> searchGeneralMessageList(String searchKeyWord,
			Date startDate, Date endDate,int departmentId);
    public GeneralMessageListModel searchGeneralMessageList(String searchKeyWord,
			Date startDate, Date endDate,int departmentId,int pageNo, int pageCount);
    
    public List<DepartmentGeneralMessageEntity> saveDepartmentGeneralMessageList
    (List<DepartmentGeneralMessageEntity> entities) ;
    public List<DepartmentGeneralMessageEntity> getDepartmentGeneralMessageListByGenMsgId(int genMessageId);
    public DepartmentGeneralMessageEntity getDepartmentGeneralMessageListById(int deptGeneralMsgId);
    public List<DepartmentGeneralMessageEntity> getDepartmentGeneralMessageListByDeptId(int departmentId);
    public DepartmentGeneralMessageEntity updateDepartmentGeneralMessageEntity
    (DepartmentGeneralMessageEntity entity);
//    public List<DepartmentGeneralMessageEntity> getDepartmentGeneralMessageListByDeptId(int departmentId,
//    		int pageNo, int pageCount);
    public GeneralMessageListModel getDepartmentGeneralMessageListByDeptId(int departmentId,
			int pageNo, int pageCount);
    public List<DepartmentGeneralMessageEntity> getDepartmentGeneralMessageListByDeptIdRefNo(int departmentId,
    		String referenceNo );
    public List<DepartmentGeneralMessageEntity> getDepartmentGeneralMessageListByGenIdRefNo(int genMessageId,
			String referenceNo );
    
    public List<GeneralMessageAttachmentEntity> saveGeneralMessageAttachment
    (List<GeneralMessageAttachmentEntity> entities);
    public List<GeneralMessageAttachmentEntity> getGeneralMessageAttachmentByGenMessageId(int genMessageId);
    public GeneralMessageAttachmentEntity getGeneralMessageAttachmentById(int genMsgAthId);
    //Temp
    public void tempUpdateDepartmentGeneralMessageRefNo();
    public void tempUpdateDepartmentCommunicationMessagesRefNo();

}
