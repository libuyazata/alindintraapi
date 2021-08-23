package com.yaz.alind.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.yaz.alind.dao.ProjectDAO;
import com.yaz.alind.dao.UserDAO;
import com.yaz.alind.entity.DocumentHistoryEntity;
import com.yaz.alind.entity.DocumentHistoryFactory;
import com.yaz.alind.entity.DocumentNumberSeriesEntity;
import com.yaz.alind.entity.DocumentNumberSeriesFactory;
import com.yaz.alind.entity.DocumentTypesEntity;
import com.yaz.alind.entity.DocumentUsersEntity;
import com.yaz.alind.entity.EmployeeEntity;
import com.yaz.alind.entity.ProjectDocumentEntity;
import com.yaz.alind.entity.ProjectDocumentFactory;
import com.yaz.alind.entity.ProjectInfoEntity;
import com.yaz.alind.entity.SubTaskEntity;
import com.yaz.alind.entity.WorkDetailsEntity;
import com.yaz.alind.model.ui.SubTaskModel;
import com.yaz.alind.model.ui.WorkDetailsModel;
import com.yaz.security.Iconstants;

@Service
public class ProjectServiceImpl implements ProjectService {

	private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Autowired
	ProjectDAO projectDAO;
	@Autowired
	UtilService utilService;
	@Autowired
	UserDAO userDAO;
	@Autowired
	ProjectDocumentFactory projectDocumentFactory;
	@Autowired
	DocumentHistoryFactory documentHistoryFactory;
	@Autowired
	UserService userService;
	@Autowired
	DocumentNumberSeriesFactory documentNumberSeriesFactory;

	@Override
	public ProjectInfoEntity saveOrUpdateProject(ProjectInfoEntity projectInfo) {
		ProjectInfoEntity pjt = null;
		try{
			if(projectInfo.getProjectId() > 0){
				projectInfo.setCreatedOn(utilService.getTodaysDate());
				projectInfo.setProjectId(1);
			}

			pjt = projectDAO.saveOrUpdateProject(projectInfo);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateProject: "+e.getMessage());
		}
		return pjt;
	}

	@Override
	public List<ProjectInfoEntity> getAllProject(int departmentId,int projectId,String token) {
		List<ProjectInfoEntity> projectInfos = null;
		try{

			//			TokenModel tokenModel = userDAO.getTokenModelByToken(token);
			EmployeeEntity employee = userService.getEmployeeByToken(token);
			// Admin
			if(employee.getUserRoleId() == 1){
				projectInfos = projectDAO.getAllProject(-1);
				//				for(int i=0;i<projectInfos.size();i++){
				//					List<ProjectDocument> documents=projectDAO.getAllDocumentByProjectId(projectId);
				//					projectInfos.get(i).getEmployee().setPassword(null);
				//					projectInfos.get(i).setDocuments(documents);
				//				}
			}//if(employee.getUserRoleId() == 1)
			// HOD or Coordinator
			else if(employee.getUserRoleId() == 2 || employee.getUserRoleId() == 4){
				projectInfos = projectDAO.getAllProject(employee.getDepartmentId());
				//				for(int i=0;i<projectInfos.size();i++){
				//					List<ProjectDocument> documents=projectDAO.getAllDocumentByProjectId(projectId);
				//					projectInfos.get(i).getEmployee().setPassword(null);
				//					projectInfos.get(i).setDocuments(documents);
				//				}
			}// HOD or Coordinator
			//Employee
			else if(employee.getUserRoleId() == 3){
				projectInfos = new ArrayList<ProjectInfoEntity>();

				List<DocumentUsersEntity> documentUsers = projectDAO.getAllDocumentUserById(employee.getEmployeeId());
				Hashtable<Integer,DocumentUsersEntity> ht=new Hashtable<Integer,DocumentUsersEntity>(); 
				for(int i=0;i<documentUsers.size();i++){
					ht.put(documentUsers.get(i).getProjectDocument().getProjectId(), documentUsers.get(i));
				}
				for (Map.Entry m:ht.entrySet()) { 
					int pjtId = (int) m.getKey();
					ProjectInfoEntity projectInfo = projectDAO.getProjectInfoById(pjtId);
					projectInfos.add(projectInfo);
				}

			}
			//			System.out.println("getAllProject,pjt size: "+projectInfos.size()
			//					+", emp role: "+employee.getUserRoleId()+" ,emp id: "+employee.getEmployeeId());
			for(int i=0;i<projectInfos.size();i++){
				List<ProjectDocumentEntity> documents=projectDAO.getAllDocumentByProjectId(projectId,0);
				projectInfos.get(i).getEmployee().setPassword(null);
				projectInfos.get(i).setDocuments(documents);
				projectInfos.get(i).setSlNo(i+1);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllProject: "+e.getMessage());
		}
		return projectInfos;
	}

	@Override
	public ProjectInfoEntity getProjectInfoById(int projectId) {
		return projectDAO.getProjectInfoById(projectId);
	}

	@Override
	public List<DocumentTypesEntity> getAllDocumentTypes() {
		return projectDAO.getAllDocumentTypes();
	}

	@Override
	public DocumentTypesEntity saveOrUpdateDocumentTypes(DocumentTypesEntity documentTypes) {
		DocumentTypesEntity docTypes = null;
		try{
			Date today = utilService.getTodaysDate();
			documentTypes.setCreatedAt(utilService.dateToTimestamp(today));
			//Updating the existing data
			if(documentTypes.getDocumentTypeId() > 0){
				docTypes = projectDAO.saveOrUpdateDocumentTypes(documentTypes);
			}else{ // For new entry
				boolean drawingSerExists = projectDAO.isDrawingSeriesExists(documentTypes.getDrawingSeries());
				if(!drawingSerExists){
					docTypes = projectDAO.saveOrUpdateDocumentTypes(documentTypes);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateDocumentTypes: "+e.getMessage());
		}
		return docTypes;
	}

	@Override
	public DocumentTypesEntity getDocumentTypeById(int documentTypeId) {
		return projectDAO.getDocumentTypeById(documentTypeId);
	}

	@Override
	public List<ProjectDocumentEntity> getAllDocumentByProjectId(int projectId,int documentTypeId,String realPath,String token) {
		List<ProjectDocumentEntity> projectDocuments = null;

		try{
			String fileLocation = Iconstants.PROJECT_DOCUMENT_LOCATION+projectId;
			String[] arrOfStr = realPath.split(Iconstants.BUILD_NAME, 2); 
			String path = arrOfStr[0]+fileLocation;
			EmployeeEntity employee = userService.getEmployeeByToken(token);
			System.out.println("getAllDocumentByProjectId, projectId: "+projectId
					+", documentTypeId: "+documentTypeId);
			switch (employee.getUserRoleId()) {
			//Admin
			case 1:
				//				projectDocuments = projectDAO.getAllDocumentByProjectId(-1,documentTypeId);
				projectDocuments = projectDAO.getAllDocumentByProjectId(projectId,documentTypeId);
				break;
				//HOD or Coordinator
			case 2:
			case 4:
				//Employee
			case 3:
				List<ProjectInfoEntity> projectInfos = getAllProject(-1, -1, token);
				projectDocuments = new ArrayList<ProjectDocumentEntity>();
				for(int i=0;i<projectInfos.size();i++){
					List<ProjectDocumentEntity> pjtDocs = projectDAO.getProjectDocumentsById
							(projectInfos.get(i).getProjectId(),documentTypeId);
					ListIterator<ProjectDocumentEntity> iterator = pjtDocs.listIterator(); 
					while (iterator.hasNext()) { 
						projectDocuments.add(iterator.next()) ;
					}
				}
				break;

			}

			for(int i=0;i<projectDocuments.size();i++){
				projectDocuments.get(i).setFilePath(path+"/"+projectDocuments.get(i).getFileName());
				projectDocuments.get(i).setSlNo(i+1);
			}
			System.out.println("getAllDocumentByProjectId, size: "+projectDocuments.size()
					+", emp role: "+employee.getUserRoleId()+" ,emp id: "+employee.getEmployeeId());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDocumentByProjectId: "+e.getMessage());
		}
		return projectDocuments;
	}

	@Override
	public ProjectDocumentEntity saveOrUpdateDocument(ProjectDocumentEntity document) {
		ProjectDocumentEntity doc = null;
		try{
			document.setCreatedOn(utilService.getCurrentDateTime());
			doc = projectDAO.saveOrUpdateDocument(document);

		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateDocument: "+e.getMessage());
		}
		return doc;
	}

	@Override
	public ProjectDocumentEntity getDocumentById(int documentId) {
		return projectDAO.getProjectDocumentById(documentId);
	}

	@Override
	public List<DocumentHistoryEntity> getAllDocumentHistories(int documentId,
			int departmentId) {
		return projectDAO.getAllDocumentHistories(documentId, departmentId);
	}

	@Override
	public DocumentHistoryEntity saveDocumentHistory(DocumentHistoryEntity documentHistory) {
		return projectDAO.saveDocumentHistory(documentHistory);
	}

	@Override
	public List<DocumentUsersEntity> getAllDocumentUsers(int departmentId,
			int documentId, int employeeId) {
		List<DocumentUsersEntity> documentUsers = null;
		try{
			documentUsers = projectDAO.getAllDocumentUsers(departmentId, documentId, employeeId);
			for(int i=0;i<documentUsers.size();i++){
				documentUsers.get(i).setSlNo(i+1);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDocumentUsers: "+e.getMessage());
		}
		return documentUsers;
	}

	@Override
	public DocumentUsersEntity saveOrUpdateDocumentUsers(DocumentUsersEntity documentUsers) {
		DocumentUsersEntity docUsers = null;
		try{
			documentUsers.setCreatedOn(utilService.getTodaysDate());
			// Set project ID from DB
			docUsers = projectDAO.saveOrUpdateDocumentUsers(documentUsers);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateDocument: "+e.getMessage());
		}
		return docUsers;
	}

	@Override
	public DocumentUsersEntity getDocumentUsersById(int documentUserId) {
		DocumentUsersEntity documentUsers = null;
		try{
			documentUsers = projectDAO.getDocumentUsersById(documentUserId);
			documentUsers.getEmployee().setPassword(null);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateDocument: "+e.getMessage());
		}
		return documentUsers;
	}

	@Override
	public ProjectDocumentEntity uploadProjectDocument(MultipartFile multipartFile,
			int employeeId, int documentTypeId, String documentName,String contextPath,int projectId
			,int projectDocumentId,String description) {
		ProjectDocumentEntity projectDocument = null;
		try{
			System.out.println("Business,uploadProjectDocument,contextPath: "+contextPath);
			//			projectDocument = new ProjectDocument();
			projectDocument = projectDocumentFactory.createProjectDocument();
			//			projectDocument = projectDAO.getProjectDocumentById(projectDocumentId);
			String fileLocation = Iconstants.PROJECT_DOCUMENT_LOCATION+projectId;
			int status = utilService.saveFile(multipartFile, contextPath, fileLocation);
			String originalFileName = multipartFile.getOriginalFilename();
			String fileName = utilService.createFileName(multipartFile.getOriginalFilename());
			//			System.out.println("Business,uploadProjectDocument,status: "+status+", projectId: "+projectId+
			//					", documentTypeId: "+documentTypeId);
			if(status > 0){
				ProjectInfoEntity projectInfo = projectDAO.getProjectInfoById(projectId);
				projectDocument.setCreatedOn(utilService.getTodaysDate());
				projectDocument.setDocumentName(documentName);
				projectDocument.setDescription(description);
				projectDocument.setEmployeeId(employeeId);
				projectDocument.setFileName(fileName);
				projectDocument.setOriginalFileName(originalFileName);
				projectDocument.setFileSize((float)multipartFile.getSize());
				projectDocument.setFileType(multipartFile.getContentType());
				projectDocument.setModifiedOn(utilService.getCurrentDateTime());
				projectDocument.setDocumentTypeId(documentTypeId);
				projectDocument.setProjectId(projectId);
				projectDocument.setDepartmentId(projectInfo.getDepartmentId());
				projectDocument.setStatus(1);
				DocumentNumberSeriesEntity documentNumberSeries = projectDAO.getDocumentNumberSeriesByDocumentTypeId(documentTypeId);
				// Whether is a new / project updation
				if(projectDocumentId == 0){
					System.out.println("Business,uploadProjectDocument,documentNumberSeries, : "+documentNumberSeries);
					DocumentTypesEntity documentTypes = projectDAO.getDocumentTypeById(documentTypeId);
					//Check , the existing document series is existing in the DB
					if(documentNumberSeries == null){
						DocumentNumberSeriesEntity docSeries = documentNumberSeriesFactory.createDocumentNumberSeries();
						docSeries.setDocumentTypeId(documentTypeId);
						String documentnumber = utilService.createDocumentNumber(documentTypes.getDrawingSeries(),
								null);
						projectDocument.setDocumentnumber(documentnumber);
						docSeries.setLastDocumentNumber(documentnumber);
						System.out.println("Business,uploadProjectDocument,documentNumberSeries == null, documentnumber: "+documentnumber);
						docSeries = projectDAO.saveOrUpdateDocumentNumberSeries(docSeries);

					}else{

						String documentnumber = utilService.createDocumentNumber(documentTypes.getDrawingSeries(),
								documentNumberSeries.getLastDocumentNumber());
						documentNumberSeries.setLastDocumentNumber(documentnumber);
						System.out.println("Business,uploadProjectDocument,else,documentNumberSeries, : "+
								documentNumberSeries.getLastDocumentNumber()+", updated nUmber: "+documentnumber);
						documentNumberSeries = projectDAO.saveOrUpdateDocumentNumberSeries(documentNumberSeries);
						projectDocument.setDocumentnumber(documentnumber);
					}
				}else{
					ProjectDocumentEntity pDocument = projectDAO.getProjectDocumentById(projectDocumentId);
					System.out.println("Business,uploadProjectDocument,projectDocumentId, id: "+projectDocumentId);
					String updatedDocNumber = utilService.updatedDocumentNumber(pDocument.getDocumentnumber());
					//					documentNumberSeries.setLastDocumentNumber(updatedDocNumber);
					//					documentNumberSeries = projectDAO.saveOrUpdateDocumentNumberSeries(documentNumberSeries);
					System.out.println("Business,uploadProjectDocument,updated, updatedDocNumber: "+documentNumberSeries.getLastDocumentNumber());
					projectDocument.setDocumentnumber(updatedDocNumber);
				}//if(projectDocumentId == 0){

				projectDocument= projectDAO.saveOrUpdateDocument(projectDocument);
				// Saving history
				DocumentHistoryEntity documentHistory = documentHistoryFactory.createDocumentHistory();

				if(projectDocument.getProjectDocumentId() > 1){
					documentHistory.setDescription("updated");
				}else{
					documentHistory.setDescription("created");
				}
				documentHistory.setDocumentId(projectDocument.getProjectDocumentId());
				documentHistory.setDocumentTypeId(documentTypeId);
				documentHistory.setEmployeeId(employeeId);
				//				documentHistory.setFilePath(projectDocument.getFilePath());
				documentHistory.setFileSize(multipartFile.getSize());
				documentHistory.setDepartmentId(projectInfo.getDepartmentId());
				documentHistory.setCreatedOn(utilService.getCurrentDateTime());
				documentHistory = saveDocumentHistory(documentHistory);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("uploadProjectDocument: "+e.getMessage());
		}
		return projectDocument;
	}

	@Override
	public InputStream getProjectDocument(int projectDocumentId,
			int employeeId) {
		InputStream input = null;
		try{

			ProjectDocumentEntity projectDocument = projectDAO.getProjectDocumentById(employeeId);
			EmployeeEntity employee = userDAO.getEmployeeById(employeeId);

			ByteArrayOutputStream archivo = new ByteArrayOutputStream();
			Font alindFont = new Font(Font.FontFamily.HELVETICA, 32, Font.BOLD, new GrayColor(0.85f));
			Font nameFont = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD, new GrayColor(0.85f));
			PdfReader.unethicalreading = true;
			// Create output PDF
			Document document = new Document(PageSize.A4);
			//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:/alind_sample_database.pdf"));
			PdfWriter writer = PdfWriter.getInstance(document, archivo);
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			// Load existing PDF
			PdfReader reader = new PdfReader("C:/Users/dell/Desktop/model_for database.pdf");
			PdfImportedPage page = writer.getImportedPage(reader, 1); 

			// Copy first page of existing PDF into output PDF

			for(int i=1; i<= reader.getNumberOfPages(); i++){
				document.newPage();
				ColumnText.showTextAligned(writer.getDirectContentUnder(),
						Element.ALIGN_CENTER, new Phrase("CONTROLLED COPY", alindFont),
						297.5f, 621, i % 2 == 1 ? 0 : 0);
				ColumnText.showTextAligned(writer.getDirectContentUnder(),
						Element.ALIGN_CENTER, new Phrase("Issued to - "+" GSS - "+" AS - "+employee.getFirstName()+", "+employee.getEmpCode(), nameFont),
						297.5f, 591, i % 2 == 1 ? 0 : 0);
				ColumnText.showTextAligned(writer.getDirectContentUnder(),
						Element.ALIGN_CENTER, new Phrase("Issued by - "+" AKS - "+" DD - "+utilService.getCurrentDateTime(), nameFont),
						297.5f, 561, i % 2 == 1 ? 0 : 0);
				//				ColumnText.showTextAligned(writer.getDirectContentUnder(),
				//						Element.ALIGN_CENTER, new Phrase("Issued to - "+" GSS - "+" AS - "+" 01.06.2019", nameFont),
				//						297.5f, 591, i % 2 == 1 ? 0 : 0);
				//				ColumnText.showTextAligned(writer.getDirectContentUnder(),
				//						Element.ALIGN_CENTER, new Phrase("Issued by - "+" AKS - "+" DD - "+" 27.05.2019", nameFont),
				//						297.5f, 561, i % 2 == 1 ? 0 : 0);
				cb.addTemplate(page, 0, 0);
			}

			document.close();
			writer.close();

			input = new ByteArrayInputStream(archivo.toByteArray()); 

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getProjectDocument: "+e.getMessage());
		}
		return input;
	}

	@Override
	public ProjectDocumentEntity getProjectDocumentById(int documentId) {
		return projectDAO.getProjectDocumentById(documentId);
	}

	@Override
	public List<DocumentUsersEntity> getDocumentUsersList(int projectDocumentId) {

		List<DocumentUsersEntity> documentUsers = null;
		try{
			documentUsers = projectDAO.getDocumentUsersList(projectDocumentId);
			for(int i=0;i<documentUsers.size();i++){
				documentUsers.get(i).getEmployee().setPassword(null);
				documentUsers.get(i).getProjectDocument().getEmployee().setPassword(null);
				documentUsers.get(i).setSlNo(i+1);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDocumentUsersList: "+e.getMessage());
		}

		return documentUsers;
	}

	@Override
	public List<ProjectDocumentEntity> getProjectDocumentsById(int projectId,int documentTypeId) {
		List<ProjectDocumentEntity> projectDocuments = null;
		try{
			projectDocuments = projectDAO.getProjectDocumentsById(projectId,documentTypeId);
			for(int i=0;i<projectDocuments.size();i++){
				//				projectDocuments.get(i).getEmployee().setPassword(null);
				projectDocuments.get(i).setEmployee(null);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getProjectDocumentsById: "+e.getMessage());
		}
		return projectDocuments;
	}

	@Override
	public List<DocumentUsersEntity> getAllDocumentUserById(int employeeId) {
		List<DocumentUsersEntity> documentUsers = null;
		try{
			documentUsers = projectDAO.getAllDocumentUserById(employeeId);
			for(int i=0;i<documentUsers.size();i++){
				documentUsers.get(i).getEmployee().setPassword(null);
				documentUsers.get(i).getProjectDocument().getEmployee().setPassword(null);
				documentUsers.get(i).setSlNo(i+1);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDocumentUserById: "+e.getMessage());
		}
		return documentUsers;
	}

	@Override
	public List<ProjectInfoEntity> getAllProjctByEmpId(int employeeId) {
		List<ProjectInfoEntity> projectInfos = null;
		try{
			projectInfos = new ArrayList<ProjectInfoEntity>();
			List<DocumentUsersEntity> documentUsers = projectDAO.getAllDocumentUserById(employeeId);
			Hashtable<Integer,DocumentUsersEntity> ht=new Hashtable<Integer,DocumentUsersEntity>(); 
			for(int i=0;i<documentUsers.size();i++){
				ht.put(documentUsers.get(i).getProjectDocument().getProjectId(), documentUsers.get(i));
			}
			for (Map.Entry m:ht.entrySet()) { 
				int pjtId = (int) m.getKey();
				ProjectInfoEntity projectInfo = projectDAO.getProjectInfoById(pjtId);
				projectInfo.getEmployee().setPassword(null);
				projectInfos.add(projectInfo);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllProjctByEmpId: "+e.getMessage());
		}
		return projectInfos;
	}

	@Override
	public WorkDetailsModel saveWorkDetails(
			WorkDetailsModel workDetailsModel) {
		WorkDetailsModel model = null;
		WorkDetailsEntity entity = null;
		try{
			entity = createWorkDetailsEntity(workDetailsModel);
			entity.setCreatedOn(utilService.getCurrentDate());
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity.setStatus(1);
			entity = projectDAO.saveWorkDetails(entity);
            entity = projectDAO.getWorkDetailsEntityById(entity.getWorkDetailsId());
			model = createWorkDetailsModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveWorkDetails: "+e.getMessage());
		}
		return model;
	}

	@Override
	public WorkDetailsModel updateWorkDetails(
			WorkDetailsModel workDetailsModel) {
		WorkDetailsModel model = null;
		WorkDetailsEntity entity = null;
		try{
			entity = createWorkDetailsEntity(workDetailsModel);
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity = projectDAO.updateWorkDetails(entity);
			entity = projectDAO.getWorkDetailsEntityById(entity.getWorkDetailsId());
			//			System.out.println("Business,updateWorkDetails,Id: "+entity.getWorkDetailsId());
			model = createWorkDetailsModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateWorkDetails: "+e.getMessage());
		}
		return model;
	}

	@Override
	public WorkDetailsModel getWorkDetailsById(int workDetailsId) {
		WorkDetailsModel model = null;
		try{
			WorkDetailsEntity entity = projectDAO.getWorkDetailsEntityById(workDetailsId);
			model = createWorkDetailsModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateWorkDetails: "+e.getMessage());
		}
		return model;
	}

	@Override
	public List<WorkDetailsModel> getWorkDetailsByDeptId(int departmentId) {
		List<WorkDetailsModel> workDetailsModels = null;
		try{
			List<WorkDetailsEntity> entities = projectDAO.getWorkDetailsEntitiesByDeptId(departmentId);
			workDetailsModels = new ArrayList<WorkDetailsModel>();
			if(entities.size() > 0){
				for(WorkDetailsEntity e: entities){
					WorkDetailsModel m = createWorkDetailsModel(e);
					workDetailsModels.add(m);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateWorkDetails: "+e.getMessage());
		}
		return workDetailsModels;
	}

	@Override
	public int deleteWorkDetailsById(int workDetailsId) {
		int value = 0;
		try{
			WorkDetailsEntity entity = projectDAO.getWorkDetailsEntityById(workDetailsId);
			entity.setStatus(-1);
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity = projectDAO.updateWorkDetails(entity);
			if(entity.getStatus() == -1){
				value = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("deleteWorkDetailsById: "+e.getMessage());
		}
		return value;
	}

	@Override
	public List<WorkDetailsModel> getWorkDetailsBySearch(String searchKeyWord,
			int workTypeId, String startDate, String endDate) {
		List<WorkDetailsModel> models = null;
		Date stDate = null;
		Date enDate = null;
		try{
			models = new ArrayList<WorkDetailsModel>();
//			System.out.println("Bussiness, getWorkDetailsBySearch,startDate: "+startDate.trim().length());
			if( !startDate.trim().isEmpty() && startDate != null ){
				stDate = utilService.stringToDate(startDate);
			}
			if( !endDate.trim().isEmpty() && endDate != null ){
				enDate = utilService.stringToDate(endDate);
			}
			List<WorkDetailsEntity> entities = projectDAO.getWorkDetailsBySearch(searchKeyWord, workTypeId, stDate, enDate);
			for(WorkDetailsEntity e: entities){
				WorkDetailsModel m = createWorkDetailsModel(e);
				models.add(m);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkDetailsBySearch: "+e.getMessage());
		}
		return models;
	}

	private WorkDetailsModel createWorkDetailsModel(WorkDetailsEntity entity){
		WorkDetailsModel model = null;
		try{
			model = new WorkDetailsModel();
			model.setCreatedEmpId(entity.getCreatedEmpId());
			model.setCreatedEmpCode(entity.getCreatedEmp().getEmpCode());
			model.setCreatedEmpName(entity.getCreatedEmp().getFirstName()+ " "+entity.getCreatedEmp().getLastName());
			if(entity.getCreatedOn() != null){
				model.setCreatedOn(utilService.dateToString(entity.getCreatedOn()));
			}
			model.setDepartmentId(entity.getDepartmentId());
			model.setDepartmentName(entity.getDepartmentEntity().getDepartmentName());
			model.setDescription(entity.getDescription());
			if(entity.getEndDate()!= null){
				model.setEndDate(utilService.dateToString(entity.getEndDate()));
			}
			model.setProjectCoOrdinatorEmpId(entity.getProjectCoOrdinatorEmpId());
			model.setProjectCoOrdinatorCode(entity.getProjectCoOrdinatorEmp().getEmpCode());
			model.setProjectCoOrdinatorName(entity.getProjectCoOrdinatorEmp().getFirstName()+" "
					+entity.getProjectCoOrdinatorEmp().getLastName());
			if(entity.getStartDate() != null){
				model.setStartDate(utilService.dateToString(entity.getStartDate()));
			}
			model.setWorkStatusId(entity.getWorkStatusId());
			model.setWorkStatusName(entity.getWorkStatusEntity().getTaskStatusName());
			if(entity.getUpdatedOn()!= null){
				model.setUpdatedOn(utilService.dateToString(entity.getUpdatedOn()));
			}
			//			System.out.println("Business,createWorkDetailsModel,Entity, Id: "+entity.getWorkDetailsId());
			model.setWorkDetailsId(entity.getWorkDetailsId());
			//			System.out.println("Business,createWorkDetailsModel,Model, Id: "+model.getWorkDetailsId());
			model.setWorkName(entity.getWorkName());
			model.setWorkTypeId(entity.getWorkTypeId());
			model.setWorkType(entity.getWorkTypeEntity().getWorkType());
			model.setStatus(entity.getStatus());

		}catch(Exception e){
			e.printStackTrace();
			logger.error("createWorkDetailsModel: "+e.getMessage());
		}
		//		System.out.println("Business,createWorkDetailsModel,Id: "+model.getWorkDetailsId()+", Pjst name: "+model.getWorkName());
		return model;
	}

	private WorkDetailsEntity createWorkDetailsEntity(WorkDetailsModel model){
		WorkDetailsEntity entity = null;
		try{
			entity = new WorkDetailsEntity();

			entity.setCreatedEmpId(model.getCreatedEmpId());
			entity.setDepartmentId(model.getDepartmentId());
			if(model.getCreatedOn() != null){
				entity.setCreatedOn(utilService.stringToDate(model.getCreatedOn()));
			}
			entity.setDescription(model.getDescription());
			entity.setEndDate(utilService.stringToDate(model.getEndDate()));
			entity.setProjectCoOrdinatorEmpId(model.getProjectCoOrdinatorEmpId());
			entity.setStartDate(utilService.stringToDate(model.getStartDate()));
			entity.setWorkStatusId(model.getWorkStatusId());
			entity.setWorkDetailsId(model.getWorkDetailsId());
			entity.setWorkName(model.getWorkName());
			entity.setWorkTypeId(model.getWorkTypeId());
			entity.setStatus(model.getStatus());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createWorkDetailsEntity: "+e.getMessage());
		}
		return entity;
	}

	@Override
	public SubTaskModel saveSubTask(SubTaskModel model) {
		SubTaskModel subModel = null;
		try{
			SubTaskEntity entity = createSubTaskEntity(model);
			entity.setCreatedOn(utilService.getCurrentDate());
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity.setStatus(1);
			entity = projectDAO.saveSubTaskEntity(entity);
			subModel = createSubTaskModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveSubTask: "+e.getMessage());
		}
		return subModel;
	}

	@Override
	public SubTaskModel updateSubTask(SubTaskModel model) {
		SubTaskModel subModel = null;
		try{
			SubTaskEntity entity = createSubTaskEntity(model);
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity = projectDAO.updateSubTaskEntity(entity);
			subModel = createSubTaskModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateSubTask: "+e.getMessage());
		}
		return subModel;
	}

	@Override
	public SubTaskModel getSubTaskById(int subTaskId) {
		SubTaskModel model = null;
		try{
			SubTaskEntity entity = projectDAO.getSubTaskEntityById(subTaskId);
			model = createSubTaskModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getSubTaskById: "+e.getMessage());
		}
		return model;
	}

	@Override
	public List<SubTaskModel> getSubTaskByWorkId(int workDetailsId,int status) {
		List<SubTaskModel> models = null;
		try{
			List<SubTaskEntity> entities = projectDAO.getSubTaskEntitiesByWorkId(workDetailsId,status);
			models = new ArrayList<SubTaskModel>();
			for(SubTaskEntity e: entities){
				SubTaskModel model = createSubTaskModel(e);
				models.add(model);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getSubTaskByWorkId: "+e.getMessage());
		}
		return models;
	}

	@Override
	public int deleteSubTask(int subTaskId){
		int value = 0;
		try{
			SubTaskEntity entity = projectDAO.getSubTaskEntityById(subTaskId);
			entity.setStatus(-1);
			entity = projectDAO.updateSubTaskEntity(entity);
			if(entity.getStatus() == -1){
				value = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("deleteSubTask: "+e.getMessage());
		}
		return value;
	}

	private SubTaskModel createSubTaskModel(SubTaskEntity entity){
		SubTaskModel model = null;
		try{
			model = new SubTaskModel();
			model.setCreatedEmpCode(entity.getCreatedEmp().getEmpCode());
			model.setCreatedEmpName(entity.getCreatedEmp().getFirstName()+ " "
					+entity.getCreatedEmp().getLastName());
			model.setCreatedEmpId(entity.getCreatedEmpId());
			model.setCreatedOn(utilService.dateToString(entity.getCreatedOn()));
			model.setDescription(entity.getDescription());
			model.setEndDate(utilService.dateToString(entity.getEndDate()));
			model.setStartDate(utilService.dateToString(entity.getStartDate()));
			model.setSubTaskId(entity.getSubTaskId());
			model.setSubTaskName(entity.getSubTaskName());
			model.setUpdatedOn(utilService.dateToString(entity.getUpdatedOn()));
			model.setWorkDetailsId(entity.getWorkDetailsId());
			model.setWorkName(entity.getWorkDetailsEntity().getWorkName());
			model.setWorkStatusId(entity.getWorkStatusId());
			model.setWorkStatusName(entity.getWorkStatusEntity().getWorkStatusName());
			model.setStatus(entity.getStatus());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createSubTaskModel: "+e.getMessage());
		}
		return model;
	}

	private SubTaskEntity createSubTaskEntity(SubTaskModel model){
		SubTaskEntity entity = null;
		try{
			entity = new SubTaskEntity();
			entity.setCreatedEmpId(model.getCreatedEmpId());
			if(model.getCreatedOn() != null){
				entity.setCreatedOn(utilService.stringToDate(model.getCreatedOn()));
			}
			entity.setDescription(model.getDescription());
			entity.setEndDate(utilService.stringToDate(model.getEndDate()));
			entity.setStartDate(utilService.stringToDate(model.getStartDate()));
			entity.setSubTaskId(model.getSubTaskId());
			entity.setSubTaskName(model.getSubTaskName());
			if(model.getUpdatedOn() != null){
				entity.setUpdatedOn(utilService.stringToDate(model.getUpdatedOn()));
			}
			entity.setWorkDetailsId(model.getWorkDetailsId());
			entity.setWorkStatusId(model.getWorkStatusId());
			entity.setStatus(model.getStatus());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createSubTaskEntity: "+e.getMessage());
		}
		return entity;
	}



}