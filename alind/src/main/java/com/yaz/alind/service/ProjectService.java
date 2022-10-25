package com.yaz.alind.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yaz.alind.entity.DepartmentEntity;
import com.yaz.alind.entity.DocumentHistoryEntity;
import com.yaz.alind.entity.DocumentUsersEntity;
import com.yaz.alind.entity.ProjectDocumentEntity;
import com.yaz.alind.entity.ProjectInfoEntity;
import com.yaz.alind.model.ui.CommunicationMessageFormatModel;
import com.yaz.alind.model.ui.DepartmentCommunicationMessagesModel;
import com.yaz.alind.model.ui.DepartmentGeneralMessageModel;
import com.yaz.alind.model.ui.EmployeeModel;
import com.yaz.alind.model.ui.EmployeeTaskAllocationModel;
import com.yaz.alind.model.ui.GeneralMessageFormatModel;
import com.yaz.alind.model.ui.GeneralMessageModel;
import com.yaz.alind.model.ui.InterOfficeCommunicationModel;
import com.yaz.alind.model.ui.SubTaskModel;
import com.yaz.alind.model.ui.WorkDetailsModel;
import com.yaz.alind.model.ui.WorkDocumentModel;
import com.yaz.alind.model.ui.WorkIssuedModel;
import com.yaz.alind.model.ui.WorkMessageAttachmentModel;

public interface ProjectService {

	public ProjectInfoEntity saveOrUpdateProject(ProjectInfoEntity projectInfo);
	public List<ProjectInfoEntity> getAllProject(int departmentId,int projectId,String token);
	public ProjectInfoEntity getProjectInfoById(int projectId);
	
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
	public List<WorkDetailsModel> getWorkDetailsListById(int workDetailsId);
	public List<WorkDetailsModel> getWorkDetailsByDeptId(String token,int departmentId,int status);
	public int deleteWorkDetailsById(int workDetailsId);
	public List<WorkDetailsModel> getWorkDetailsBySearch(String token,String searchKeyWord,
			int workTypeId, String startDate, String endDate);
	public List<WorkDetailsModel> getWorkDetailsByDate(String token,String startDate, String endDate,int departmentId);
	
	public SubTaskModel saveSubTask(SubTaskModel model);
	public SubTaskModel updateSubTask(SubTaskModel model);
	public SubTaskModel getSubTaskById(int subTaskId);
    public List<SubTaskModel> getSubTaskByWorkId(int workDetailsId,int status);
    public int deleteSubTask(int subTaskId);
    
    public WorkDocumentModel saveWorkDocument(MultipartFile file,String token,int documentTypeId,int workDetailsId,
			int subTaskId,String description,int departmentId,String documentName,int documentCategoryId,String contextPath);
    public WorkDocumentModel updateWorkDocument(WorkDocumentModel model,String contextPath);
    public WorkDocumentModel getWorkDocumentById(int workDocumentId,String contextPath);
    public List<WorkDocumentModel> getWorkDocumentByWorkDetailsId(int workDetailsId,String contextPath);
    public List<WorkDocumentModel> getWorkDocumentBySubTaskId(int subTaskId,String contextPath);
    public int deleteWorkDocumentModelById(int workDocumentId);
    public WorkDocumentModel verifyDocument(int workDocumentId,String contextPath);
    public WorkDocumentModel approveDocument(int workDocumentId,String contextPath);
    public ByteArrayInputStream getWorkDocument(int workDocumentId,String token,String contextPath);
    public int getWorkVerificationStatusById(int workDocumentId);
    public WorkDocumentModel getLatestWorkDocument(int subTaskId,int documentCategoryId,String contextPath);
    
    public List<EmployeeTaskAllocationModel> saveEmployeeTaskAllocation(Object object);
    public EmployeeTaskAllocationModel updateEmployeeTaskAllocation(EmployeeTaskAllocationModel employeeTask);
    public EmployeeTaskAllocationModel getEmployeeTaskAllocationById(int empTaskAllocationId);
    public List<EmployeeTaskAllocationModel> getAllEmployeeTaskAllocationBySubTaskId(int subTaskId);
    public List<EmployeeTaskAllocationModel> getAllEmployeeTaskAllocationByWorkDetailsId(int workDetailsId);
    public List<EmployeeModel> getEmployeeListForTaskAllocationByDeptId(int departmentId);
    public int deleteEmployeeFromSubTask(int empTaskAllocationId);
	
    public WorkIssuedModel saveWorkIssuedDetails(WorkIssuedModel workIssuedDetails,String token);
    public WorkIssuedModel updateWorkIssuedDetails(WorkIssuedModel workIssuedDetails);
    public int deleteWorkIssuedDetails(int workIssuedId);
    public List<WorkIssuedModel> getWorkIssuedDetailsByDeptId(int departmentId );
    
  //Inter Office Communication
    public InterOfficeCommunicationModel saveInterOfficeCommunication(InterOfficeCommunicationModel model,String token);
    public InterOfficeCommunicationModel replyInterOfficeCommunication(String token, MultipartFile[] multipartFiles,
    		String contextPath,List<Integer> toDeptList,int workDetailsId,
    		int subTaskId, String subject,String description,String referenceNo);
    public InterOfficeCommunicationModel sendWorkMessage(String token, MultipartFile[] multipartFiles,
    		String contextPath,
    		List<Integer> toDeptList,int workDetailsId,int subTaskId, String subject,String description);
    public InterOfficeCommunicationModel updateInterOfficeCommunication(InterOfficeCommunicationModel model,String token);
    public InterOfficeCommunicationModel getCommunicationById(int officeCommunicationId);
    public List<InterOfficeCommunicationModel> getCommunicationListById(String contextPath,int officeCommunicationId);
    public InterOfficeCommunicationModel deleteCommunicationById(int officeCommunicationId);
    public List<InterOfficeCommunicationModel> getCommunicationListBySubTaskId(int subTaskId);
    public List<InterOfficeCommunicationModel> getCommunicationListByWorkId(int workDetailsId);
    public List<CommunicationMessageFormatModel> getCommunicationListByDeptId(int departmentId );
    public List<CommunicationMessageFormatModel> searchInterDeptCommList(String searchKeyWord,
			String startDate, String endDate,int departmentId );
    public List<CommunicationMessageFormatModel> getInboxMessageByDeptId(int departmentId );
    
    
    
    public DepartmentCommunicationMessagesModel viewUpdateDepartmentCommunicationMessage(int deptCommId,String token);
    
    public List<DepartmentEntity> getDepartmentListByWorkId(int workDetailsId);
    
    public List<WorkMessageAttachmentModel> getWorkMessageAttachmentByOffComId(String realPath,int officeCommunicationId);

    public GeneralMessageModel sendToGeneralMessage(String token, MultipartFile[] multipartFiles,
    		String contextPath,
    		List<Integer> toDeptList, String subject,String description);
    public GeneralMessageModel replyGeneralMessage(String token, MultipartFile[] multipartFiles,
    		String contextPath,
    		List<Integer> toDeptList, String subject,String description, String referenceNo);
    public List<GeneralMessageFormatModel> getSentGeneralMessageListByDeptId(int departmentId );
    public List<GeneralMessageFormatModel> getGeneralInboxByDeptId(int departmentId );
    public GeneralMessageModel getGeneralMessageById(int genMessageId);
    public List<GeneralMessageModel> getGeneralMessageListById(int genMessageId);
    public DepartmentGeneralMessageModel viewUpdateDepartmentGenMessage(int deptGeneralMsgId,String token);
}
