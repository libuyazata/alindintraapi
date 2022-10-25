package com.yaz.alind.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
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
import com.yaz.alind.dao.MasterTableDAO;
import com.yaz.alind.dao.ProjectDAO;
import com.yaz.alind.dao.UserDAO;
import com.yaz.alind.entity.DepartmentCommunicationMessagesEntity;
import com.yaz.alind.entity.DepartmentEntity;
import com.yaz.alind.entity.DepartmentGeneralMessageEntity;
import com.yaz.alind.entity.DocumentCategoryEntity;
import com.yaz.alind.entity.DocumentHistoryEntity;
import com.yaz.alind.entity.DocumentHistoryFactory;
import com.yaz.alind.entity.DocumentNumberSeriesEntity;
import com.yaz.alind.entity.DocumentNumberSeriesFactory;
import com.yaz.alind.entity.DocumentUsersEntity;
import com.yaz.alind.entity.EmployeeEntity;
import com.yaz.alind.entity.EmployeeTaskAllocationEntity;
import com.yaz.alind.entity.GeneralMessageAttachmentEntity;
import com.yaz.alind.entity.GeneralMessageEntity;
import com.yaz.alind.entity.InterCommRefNoEntity;
import com.yaz.alind.entity.InterOfficeCommunicationEntity;
import com.yaz.alind.entity.ProjectDocumentEntity;
import com.yaz.alind.entity.ProjectDocumentFactory;
import com.yaz.alind.entity.ProjectInfoEntity;
import com.yaz.alind.entity.SubTaskEntity;
import com.yaz.alind.entity.WorkDetailsEntity;
import com.yaz.alind.entity.WorkDocumentEntity;
import com.yaz.alind.entity.WorkIssuedDetailsEntity;
import com.yaz.alind.entity.WorkMessageAttachmentEntity;
import com.yaz.alind.model.ui.CommunicationMessageFormatModel;
import com.yaz.alind.model.ui.CommunicationMessageRefNoDetailsModel;
import com.yaz.alind.model.ui.DepartmentCommunicationMessagesModel;
import com.yaz.alind.model.ui.DepartmentGeneralMessageModel;
import com.yaz.alind.model.ui.EmployeeModel;
import com.yaz.alind.model.ui.EmployeeTaskAllocationModel;
import com.yaz.alind.model.ui.GeneralMessageAttachmentModel;
import com.yaz.alind.model.ui.GeneralMessageFormatModel;
import com.yaz.alind.model.ui.GeneralMessageModel;
import com.yaz.alind.model.ui.GeneralMessageRefNoDetailsModel;
import com.yaz.alind.model.ui.InterOfficeCommunicationModel;
import com.yaz.alind.model.ui.SubTaskModel;
import com.yaz.alind.model.ui.WorkDetailsModel;
import com.yaz.alind.model.ui.WorkDocumentModel;
import com.yaz.alind.model.ui.WorkIssuedModel;
import com.yaz.alind.model.ui.WorkMessageAttachmentModel;
import com.yaz.alind.util.Iconstants;

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
	@Autowired
	MasterTableDAO masterTableDAO;

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
	public List<ProjectDocumentEntity> getAllDocumentByProjectId(int projectId,int documentTypeId,String realPath,String token) {
		List<ProjectDocumentEntity> projectDocuments = null;

		try{
			String fileLocation = Iconstants.PROJECT_DOCUMENT_LOCATION+projectId;
			String[] arrOfStr = realPath.split(Iconstants.BUILD_NAME, 2); 
			String path = arrOfStr[0]+fileLocation;
			EmployeeEntity employee = userService.getEmployeeByToken(token);
			//			System.out.println("getAllDocumentByProjectId, projectId: "+projectId
			//					+", documentTypeId: "+documentTypeId);
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
			//			System.out.println("getAllDocumentByProjectId, size: "+projectDocuments.size()
			//					+", emp role: "+employee.getUserRoleId()+" ,emp id: "+employee.getEmployeeId());
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
			//			System.out.println("Business,uploadProjectDocument,contextPath: "+contextPath);
			//			projectDocument = new ProjectDocument();
			projectDocument = projectDocumentFactory.createProjectDocument();
			//			projectDocument = projectDAO.getProjectDocumentById(projectDocumentId);
			String fileLocation = Iconstants.PROJECT_DOCUMENT_LOCATION+projectId;
			int status = utilService.saveFile(multipartFile, contextPath, fileLocation);
			String originalFileName = multipartFile.getOriginalFilename();
			String fileName = utilService.createFileName(multipartFile.getOriginalFilename());
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
					DocumentCategoryEntity documentTypes = masterTableDAO.getDocumentCategoryById(documentTypeId);
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
	public WorkDocumentModel saveWorkDocument(MultipartFile file,String token,int documentTypeId,
			int workDetailsId,int subTaskId,String description,int departmentId,
			String documentName,int documentCategoryId,String contextPath) {
		WorkDocumentModel documentModel = null;
		try{
			WorkDocumentEntity entity = new WorkDocumentEntity();
			EmployeeEntity employee = userService.getEmployeeByToken(token);
			DocumentCategoryEntity documentTypes = masterTableDAO.getDocumentCategoryById(documentTypeId);
			// Getting the latest Work Document for taking the drawing series number.
			WorkDocumentModel latestWorkDocumentModel = getLatestWorkDocument(subTaskId, documentCategoryId, contextPath);
			DocumentNumberSeriesEntity documentNumberSeries = projectDAO.getDocumentNumberSeriesByDocumentTypeId(documentTypeId);
			String documentnumber = null;
			// If the respective Number Series not in the DB
			//			if(documentNumberSeries == null){
			if(latestWorkDocumentModel == null){
				documentnumber = utilService.createDocumentNumber(documentTypes.getDrawingSeries(),
						documentnumber);
				documentNumberSeries = documentNumberSeriesFactory.createDocumentNumberSeries();
				documentNumberSeries.setDocumentTypeId(documentTypeId);
				documentNumberSeries.setLastDocumentNumber(documentnumber);
				documentNumberSeries = projectDAO.saveOrUpdateDocumentNumberSeries(documentNumberSeries);
			}else{
				//				documentnumber = utilService.createDocumentNumber(documentTypes.getDrawingSeries(),
				//						documentNumberSeries.getLastDocumentNumber());
				String latestDocumentnumber = latestWorkDocumentModel.getDocumentnumber();
				documentnumber = utilService.updatedDocumentNumber(latestDocumentnumber);

			}
			//			documentNumberSeries.setLastDocumentNumber(documentnumber);
			//			documentNumberSeries = projectDAO.saveOrUpdateDocumentNumberSeries(documentNumberSeries);

			String fileLocation = Iconstants.PROJECT_DOCUMENT_LOCATION + workDetailsId +"/"+ subTaskId;
			//System.out.println("Business,saveWorkDocument,fileLocation : "+fileLocation);
			int status = utilService.saveFile(file, contextPath, fileLocation);
			//			String originalFileName = file.getOriginalFilename();
			String fileName = utilService.createFileName(file.getOriginalFilename());

			entity.setApprovalStatus(0);
			entity.setWorkDetailsId(workDetailsId);
			entity.setDepartmentId(departmentId);
			entity.setDescription(description);
			entity.setDocumentName(documentName);
			entity.setDocumentnumber(documentnumber);
			entity.setDocumentTypeId(documentTypeId);
			entity.setDocumentCategoryId(documentCategoryId);
			entity.setEmployeeId(employee.getEmployeeId());
			entity.setFileSize(file.getSize());
			entity.setFileType(file.getContentType());
			entity.setOriginalFileName(file.getOriginalFilename());
			entity.setStatus(1);
			entity.setSubTaskId(subTaskId);
			entity.setCreatedOn(utilService.getCurrentDate());
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity.setVerificationStatus(0);

			entity.setFileName(fileName);
			entity = projectDAO.saveWorkDocument(entity);
			entity = projectDAO.getWorkDocumentById(entity.getWorkDocumentId());
			documentModel = createWorkDocumentModel(entity,contextPath);


		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateWorkDocument: "+e.getMessage());
		}
		return documentModel;
	}

	/**
	 *  Getting the Latest Work Document, based on CategoryId & subTaskId
	 */
	@Override
	public WorkDocumentModel getLatestWorkDocument(int subTaskId,int documentCategoryId,String contextPath){
		WorkDocumentModel model = null;
		try{
			WorkDocumentEntity entity = projectDAO.getLatestWorkDocument(subTaskId, documentCategoryId);
			if(entity != null){
				model = createWorkDocumentModel(entity, contextPath);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getLatestWorkDocument: "+e.getMessage());
		}
		return model;
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

	/**
	 *  Water Mark with download
	 */
	@Override
	public ByteArrayInputStream getWorkDocument(int workDocumentId,String token,String contextPath){
		ByteArrayInputStream byteArrayInputStream = null;
		try{
			EmployeeEntity employee = userService.getEmployeeByToken(token);
			WorkDocumentEntity workDocument = projectDAO.getWorkDocumentById(workDocumentId);

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
			//			PdfReader reader = new PdfReader("C:/Users/dell/Desktop/model_for database.pdf");
			//			String filePath="D:/alind.pdf";
			String[] arrOfStr = contextPath.split(Iconstants.BUILD_NAME, 2); 
			String fileLocation = Iconstants.PROJECT_DOCUMENT_LOCATION + 
					workDocument.getWorkDetailsId() +"/"+ workDocument.getSubTaskId();
			String path = arrOfStr[0]+fileLocation;
			String filePath = path+"/"+workDocument.getFileName();

			PdfReader reader = new PdfReader(filePath);
			PdfImportedPage page = writer.getImportedPage(reader, 1); 

			// Copy first page of existing PDF into output PDF
			if(workDocument.getApprovalStatus() == 1){
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
			}else{
				for(int i=1; i<= reader.getNumberOfPages(); i++){
					document.newPage();
					ColumnText.showTextAligned(writer.getDirectContentUnder(),
							Element.ALIGN_CENTER, new Phrase("DRAFT", alindFont),
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
			}

			document.close();
			writer.close();

			byteArrayInputStream = new ByteArrayInputStream(archivo.toByteArray());


		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkDocument: "+e.getMessage());
		}
		return byteArrayInputStream;
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
	@Transactional
	public WorkDetailsModel updateWorkDetails(
			WorkDetailsModel workDetailsModel) {
		WorkDetailsModel model = null;
		WorkDetailsEntity entity = null;
		try{
			System.out.println("Business,updateWorkDetails,CreatedOn: "+workDetailsModel.getCreatedOn());
			entity = createWorkDetailsEntity(workDetailsModel);
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity.setStatus(1);
			entity = projectDAO.updateWorkDetails(entity);
			//			entity = projectDAO.getWorkDetailsEntityById(entity.getWorkDetailsId());
			//			System.out.println("Business,updateWorkDetails,getDescription: "+entity.getDescription());
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

	/**
	 *  This is only for UI purpose
	 * @param workDetailsId
	 * @return
	 */
	@Override
	public List<WorkDetailsModel> getWorkDetailsListById(int workDetailsId) {
		List<WorkDetailsModel> list = null;
		try{
			list = new ArrayList<WorkDetailsModel>();
			WorkDetailsEntity entity = projectDAO.getWorkDetailsEntityById(workDetailsId);
			WorkDetailsModel model = createWorkDetailsModel(entity);
			list.add(model);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkDetailsListById: "+e.getMessage());
		}
		return list;
	}

	@Override
	public List<WorkDetailsModel> getWorkDetailsByDeptId(String token,int departmentId,int status) {
		List<WorkDetailsModel> workDetailsModels = null;
		List<WorkDetailsEntity> entities = null;
		try{
			EmployeeEntity employeeEntity = userService.getEmployeeByToken(token);
			if(employeeEntity.getUserRoleId() == 1){
				entities = projectDAO.getWorkDetailsEntitiesByDeptId(0,status);
			}else{
				entities = projectDAO.getWorkDetailsEntitiesByDeptId(employeeEntity.getDepartmentId(),status);
			}
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
	public List<WorkDetailsModel> getWorkDetailsBySearch(String token,String searchKeyWord,
			int workTypeId, String startDate, String endDate) {
		List<WorkDetailsModel> models = null;
		Date stDate = null;
		Date enDate = null;
		List<WorkDetailsEntity> entities = null;
		try{
			models = new ArrayList<WorkDetailsModel>();
			EmployeeEntity employeeEntity = userService.getEmployeeByToken(token);
			String departmentName = employeeEntity.getDepartment().getDepartmentName();
			//			System.out.println("Bussiness, getWorkDetailsBySearch,startDate: "+startDate.trim().length());
			if( !startDate.trim().isEmpty() && startDate != null ){
				stDate = utilService.stringToDate(startDate);
			}
			if( !endDate.trim().isEmpty() && endDate != null ){
				enDate = utilService.stringToDate(endDate);
			}

			if(employeeEntity.getUserRoleId() == 1){
				entities = projectDAO.getWorkDetailsBySearch(searchKeyWord, workTypeId,null,
						stDate, enDate);
			}else{
				entities = projectDAO.getWorkDetailsBySearch(searchKeyWord, workTypeId,departmentName,
						stDate, enDate);
			}
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

	public List<WorkDetailsModel> getWorkDetailsByDate(String token,
			String startDate, String endDate,int departmentId){
		List<WorkDetailsModel> models = null;
		List<WorkDetailsEntity> entities = null;
		try{
			models = new ArrayList<WorkDetailsModel>();
			Date stDate = utilService.getFirstDayOfYear(utilService.getDateFromString(startDate));
			Date enDate = utilService.getLastDayOfYear(utilService.getDateFromString(startDate));
			//			System.out.println("Project business, getWorkDetailsByDate, stDate: "+utilService.dateToString(stDate)
			//					+", enDate: "+utilService.dateToString(enDate));
			entities = projectDAO.getWorkDetailsByDate(stDate, enDate, departmentId);
			for(WorkDetailsEntity e: entities){
				WorkDetailsModel m = createWorkDetailsModel(e);
				models.add(m);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkDetailsByDate: "+e.getMessage());
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
			entity.setCreatedOn(utilService.getCurrentDateTimeStamp());
			entity.setUpdatedOn(utilService.getCurrentDateTimeStamp());
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
			entity.setUpdatedOn(utilService.getCurrentDateTimeStamp());
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


	@Override
	public WorkDocumentModel updateWorkDocument(
			WorkDocumentModel model,String contextPath) {
		WorkDocumentModel documentModel = null;
		try{
			WorkDocumentEntity entity = createWorkDocumentEntity(model);
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity = projectDAO.updateWorkDocument(entity);
			model = createWorkDocumentModel(entity,contextPath);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateWorkDocument: "+e.getMessage());
		}
		return documentModel;
	}

	@Override
	public WorkDocumentModel getWorkDocumentById(int workDocumentId,String contextPath) {
		WorkDocumentModel model = null;
		try{
			WorkDocumentEntity entity = projectDAO.getWorkDocumentById(workDocumentId);
			model = createWorkDocumentModel(entity,contextPath);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkDocumentById: "+e.getMessage());
		}
		return model;
	}

	@Override
	public List<WorkDocumentModel> getWorkDocumentByWorkDetailsId(int workDetailsId,String contextPath){
		List<WorkDocumentModel> models = null;
		try{
			models = new ArrayList<WorkDocumentModel>();
			List<WorkDocumentEntity> entities = projectDAO.getWorkDocumentByWorkDetailsId(workDetailsId);
			for(WorkDocumentEntity entity: entities){
				WorkDocumentModel model = createWorkDocumentModel(entity,contextPath);
				models.add(model);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkDocumentByWorkDetailsId: "+e.getMessage());
		}
		return models;
	}

	@Override
	public List<WorkDocumentModel> getWorkDocumentBySubTaskId(int subTaskId,String contextPath) {
		List<WorkDocumentModel> models = null;
		try{
			models = new ArrayList<WorkDocumentModel>();
			List<WorkDocumentEntity> entities = projectDAO.getWorkDocumentBySubTaskId(subTaskId);
			System.out.println("Business,getWorkDocumentBySubTaskId, size: "+entities.size()+", subTaskId: "+subTaskId);
			for(WorkDocumentEntity entity: entities){
				WorkDocumentModel model = createWorkDocumentModel(entity,contextPath);
				models.add(model);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkDocumentBySubTaskId: "+e.getMessage());
		}
		return models;
	}

	@Override
	public int deleteWorkDocumentModelById(int workDocumentId){
		int status = 0;
		try{
			WorkDocumentEntity entity = projectDAO.getWorkDocumentById(workDocumentId);
			if(entity.getApprovalStatus() == 0){
				entity.setStatus(0);
				entity = projectDAO.updateWorkDocument(entity);
				status = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("deleteWorkDocumentModelById: "+e.getMessage());
		}
		return status ;
	}

	private WorkDocumentEntity createWorkDocumentEntity(WorkDocumentModel model){
		WorkDocumentEntity entity = null;
		try{
			entity = new WorkDocumentEntity();
			entity.setApprovalStatus(model.getApprovalStatus());
			if(model.getCreatedOn() != null){
				entity.setCreatedOn(utilService.stringToDate(model.getCreatedOn()));
			}
			entity.setDepartmentId(model.getDepartmentId());
			entity.setDescription(model.getDescription());
			entity.setDocumentTypeId(model.getDocumentTypeId());
			entity.setDocumentName(model.getDocumentName());
			entity.setDocumentnumber(model.getDocumentnumber());
			entity.setFileName(model.getFileName());
			entity.setFilePath(model.getFilePath());
			entity.setFileSize(model.getFileSize());
			entity.setFileType(model.getFileType());
			entity.setOriginalFileName(model.getOriginalFileName());
			entity.setStatus(model.getStatus());
			entity.setSubTaskId(model.getSubTaskId());
			entity.setDocumentCategoryId(model.getDocumentCategoryId());
			if(model.getUpdatedOn() != null){
				entity.setUpdatedOn(utilService.stringToDate(model.getUpdatedOn()));
			}
			entity.setVerificationStatus(model.getVerificationStatus());
			entity.setWorkDetailsId(model.getWorkDetailsId());

		}catch(Exception e){
			e.printStackTrace();
			logger.error("createWorkDocumentEntity: "+e.getMessage());
		}
		return entity;
	}

	private WorkDocumentModel createWorkDocumentModel(WorkDocumentEntity entity,String contextPath){
		WorkDocumentModel model = null;
		try{
			model = new WorkDocumentModel();
			model.setApprovalStatus(entity.getApprovalStatus());
			if(model.getApprovalStatus() == 0){
				model.setApprovalType("No");
			} else{
				model.setApprovalType("Yes");
			}
			model.setCreatedOn(utilService.dateToString(entity.getCreatedOn()));
			model.setCreatedEmpCode(entity.getEmplpoyee().getEmpCode());
			model.setCreatedEmpId(entity.getEmplpoyee().getEmployeeId());
			model.setCreatedEmpName(entity.getEmplpoyee().getFirstName()+" "+entity.getEmplpoyee().getLastName());
			model.setDepartmentId(entity.getDepartmentId());
			model.setDepartmentName(entity.getDepartment().getDepartmentName());
			model.setDescription(entity.getDescription());
			model.setDocumentType(entity.getDocumentTypeEntity().getType());
			model.setDocumentTypeId(entity.getDocumentTypeId());
			model.setDocumentName(entity.getDocumentName());
			model.setDocumentnumber(entity.getDocumentnumber());
			model.setFileName(entity.getFileName());
			model.setDocumentCategoryType(entity.getdocumentCategory().getType());
			model.setDocumentCategoryId(entity.getDocumentCategoryId());
			// Setting file path / location
			String[] arrOfStr = contextPath.split(Iconstants.BUILD_NAME, 2); 
			String fileLocation = Iconstants.PROJECT_DOCUMENT_LOCATION + 
					entity.getWorkDetailsId() +"/"+ entity.getSubTaskId();
			String path = arrOfStr[0]+fileLocation;
			model.setFilePath(path+"/"+entity.getFileName());

			model.setFileSize(entity.getFileSize());
			model.setFileType(entity.getFileType());
			model.setOriginalFileName(entity.getOriginalFileName());
			model.setStatus(entity.getStatus());
			model.setSubTaskId(entity.getSubTaskId());
			model.setSubTaskName(entity.getSubTask().getSubTaskName());
			model.setUpdatedOn(utilService.dateToString(entity.getUpdatedOn()));
			model.setVerificationStatus(entity.getVerificationStatus());
			if(entity.getVerificationStatus() == 1){
				model.setVerificationType("Yes");
			}else{
				model.setVerificationType("No");
			}
			model.setWorkDocumentId(entity.getWorkDocumentId());
			model.setWorkDetailsId(entity.getWorkDetailsId());
			model.setWorkName(entity.getWorkDetails().getWorkName());

		}catch(Exception e){
			e.printStackTrace();
			logger.error("createSubTaskModel: "+e.getMessage());
		}
		return model;

	}

	private SubTaskModel createSubTaskModel(SubTaskEntity entity){
		SubTaskModel model = null;
		try{
			model = new SubTaskModel();
			model.setCreatedEmpCode(entity.getCreatedEmp().getEmpCode());
			model.setCreatedEmpName(entity.getCreatedEmp().getFirstName()+ " "
					+entity.getCreatedEmp().getLastName());
			model.setCreatedEmpId(entity.getCreatedEmpId());
			//			model.setCreatedOn(utilService.dateToString(entity.getCreatedOn()))
			model.setCreatedOn(utilService.timeStampToString(entity.getCreatedOn()));
			model.setDescription(entity.getDescription());
			//			model.setEndDate(utilService.dateToString(entity.getEndDate()));
			model.setEndDate(utilService.timeStampToString(entity.getEndDate()));
			model.setStartDate(utilService.timeStampToString(entity.getStartDate()));
			model.setSubTaskId(entity.getSubTaskId());
			model.setSubTaskName(entity.getSubTaskName());
			model.setUpdatedOn(utilService.timeStampToString(entity.getUpdatedOn()));
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
				entity.setCreatedOn(utilService.stringToTimestamp(model.getCreatedOn()));
			}
			entity.setDescription(model.getDescription());
			entity.setEndDate(utilService.stringToTimestamp(model.getEndDate()));
			entity.setStartDate(utilService.stringToTimestamp(model.getStartDate()));
			entity.setSubTaskId(model.getSubTaskId());
			entity.setSubTaskName(model.getSubTaskName());
			if(model.getUpdatedOn() != null){
				entity.setUpdatedOn(utilService.stringToTimestamp(model.getUpdatedOn()));
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

	@Override
	public WorkDocumentModel verifyDocument(int workDocumentId,String contextPath) {
		WorkDocumentModel model = null;
		try{
			WorkDocumentEntity entity = projectDAO.getWorkDocumentById(workDocumentId);
			entity.setVerificationStatus(1);
			entity = projectDAO.updateWorkDocument(entity);
			if(entity.getStatus() == 1){
				model = createWorkDocumentModel(entity, contextPath);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("verifyDocument: "+e.getMessage());
		}
		return model;
	}

	@Override
	public WorkDocumentModel approveDocument(int workDocumentId,String contextPath) {
		WorkDocumentModel model = null;
		try{
			WorkDocumentEntity entity = projectDAO.getWorkDocumentById(workDocumentId);
			entity.setApprovalStatus(1);
			entity = projectDAO.updateWorkDocument(entity);
			if(entity.getStatus() == 1){
				model = createWorkDocumentModel(entity, contextPath);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("approveDocument: "+e.getMessage());
		}
		return model;
	}


	@Override
	public List<EmployeeTaskAllocationModel> saveEmployeeTaskAllocation(Object object) {
		List<EmployeeTaskAllocationModel> allocationModels = null;
		try{
			allocationModels = new ArrayList<EmployeeTaskAllocationModel>();
			LinkedHashMap<String, String> lhm = (LinkedHashMap<String, String>) object;
			LinkedHashMap<String, String> objAllotT=null;
			// Generating a Set of entries
			Set set = lhm.entrySet();
			// Displaying elements of LinkedHashMap
			Iterator iterator = set.iterator();
			while(iterator.hasNext()) {
				Map.Entry me = (Map.Entry)iterator.next();
				//				System.out.print("Key is: "+ me.getKey() + 
				//						"& Value is: "+me.getValue()+"\n");
				objAllotT = (LinkedHashMap<String, String>) me.getValue();
			}
			String workDetailsId = String.valueOf(objAllotT.get("workDetailsId"));
			String subTaskId = String.valueOf(objAllotT.get("subTaskId"));
			String employyArray = String.valueOf(objAllotT.get("employeeList"));

			//			System.out.println("Business,saveEmployeeTaskAllocation,workDetailsId: "+workDetailsId+","
			//					+ "employyArray: "+employyArray);

			List<Integer> empList = new ArrayList<Integer>();
			String[] words = employyArray.split("\\s+");
			//			System.out.println("Business,saveEmployeeTaskAllocation,words, size: "+words.length);
			for(int i=0;i<words.length;i++) {
				if( words[i].contains("employeeId")){
					//					System.out.println("contains,employeeId");
					String strId =words[i].substring(words[i].indexOf("=")+1  , words[i].length()-1);
					//					System.out.println("Id: "+strId);
					empList.add(Integer.parseInt(strId));
				}
			}
			Date today = utilService.getCurrentDate();
			for(int j=0;j<empList.size();j++){
				EmployeeTaskAllocationEntity employeeTask = new EmployeeTaskAllocationEntity();
				employeeTask.setStatus(1);
				employeeTask.setCreatedOn(today);
				employeeTask.setUpdatedOn(today);
				employeeTask.setEmployeeId(empList.get(j));
				employeeTask.setSubTaskId(Integer.parseInt(subTaskId));
				employeeTask.setWorkDetailsId(Integer.parseInt(workDetailsId));
				//				EmployeeTaskAllocationEntity entity = createEmployeeTaskAllocationEntity(employeeTask);
				employeeTask = projectDAO.saveEmployeeTaskAllocation(employeeTask);
				employeeTask = projectDAO.getEmployeeTaskAllocationById(employeeTask.getEmpTaskAllocationId());
				EmployeeTaskAllocationModel model = createEmployeeTaskAllocationModel(employeeTask);
				allocationModels.add(model);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveEmployeeTaskAllocation: "+e.getMessage());
		}
		return allocationModels;
	}

	@Override
	public EmployeeTaskAllocationModel updateEmployeeTaskAllocation
	(EmployeeTaskAllocationModel employeeTask) {
		EmployeeTaskAllocationModel model = null;
		try{
			Date date = utilService.getCurrentDate();
			String strDate = utilService.dateToString(date);
			employeeTask.setUpdatedOn(strDate);
			EmployeeTaskAllocationEntity entity = createEmployeeTaskAllocationEntity(employeeTask);
			entity = projectDAO.saveEmployeeTaskAllocation(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateEmployeeTaskAllocation: "+e.getMessage());
		}
		return model;
	}

	@Override
	public EmployeeTaskAllocationModel getEmployeeTaskAllocationById(
			int empTaskAllocationId) {
		EmployeeTaskAllocationModel model = null;
		try{
			EmployeeTaskAllocationEntity entity = projectDAO.getEmployeeTaskAllocationById
					(empTaskAllocationId);
			model = createEmployeeTaskAllocationModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getEmployeeTaskAllocationById: "+e.getMessage());
		}
		return model;
	}

	@Override
	public List<EmployeeTaskAllocationModel> getAllEmployeeTaskAllocationBySubTaskId(
			int subTaskId) {
		List<EmployeeTaskAllocationModel> models = null;
		try{
			models = new ArrayList<EmployeeTaskAllocationModel>();
			List<EmployeeTaskAllocationEntity> entities = projectDAO.getAllEmployeeTaskAllocationBySubTaskId(subTaskId);
			for(EmployeeTaskAllocationEntity e: entities){
				EmployeeTaskAllocationModel m = createEmployeeTaskAllocationModel(e);
				models.add(m);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllEmployeeTaskAllocationBySubTaskId: "+e.getMessage());
		}
		return models;
	}

	@Override
	public List<EmployeeTaskAllocationModel> getAllEmployeeTaskAllocationByWorkDetailsId(
			int workDetailsId) {
		List<EmployeeTaskAllocationModel> models = null;
		try{
			models = new ArrayList<EmployeeTaskAllocationModel>();
			List<EmployeeTaskAllocationEntity> entities = projectDAO.getAllEmployeeTaskAllocationByWorkDetailsId
					(workDetailsId);
			for(EmployeeTaskAllocationEntity e: entities){
				EmployeeTaskAllocationModel m = createEmployeeTaskAllocationModel(e);
				models.add(m);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllEmployeeTaskAllocationByWorkDetailsId: "+e.getMessage());
		}
		return models;
	}

	@Override
	public int deleteEmployeeFromSubTask(int empTaskAllocationId){
		int value = 0;
		try{
			EmployeeTaskAllocationEntity entity = projectDAO.getEmployeeTaskAllocationById
					(empTaskAllocationId);
			entity.setStatus(-1);
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity = projectDAO.updateEmployeeTaskAllocation(entity);
			if(entity.getStatus() == -1){
				value = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("deleteEmployeeFromSubTask: "+e.getMessage());
		}
		return value;
	}

	/**
	 *   Allocation of employees for various Sub tasks
	 * @param departmentId
	 * @return
	 */
	@Override
	public List<EmployeeModel> getEmployeeListForTaskAllocationByDeptId(
			int departmentId) {
		List<EmployeeModel> empModels = null;
		try{
			HashMap<Integer, EmployeeModel> empMap = new HashMap<Integer, EmployeeModel>();
			List<EmployeeModel> employeeModelsTemp = new ArrayList<EmployeeModel>();
			employeeModelsTemp = userService.getAllEmployeesByDept(departmentId);
			//Getting details from Deputation table
			List<EmployeeModel> deputedEmpList = userService.getDeputedEmployeeListByDeptId(departmentId);
			for(EmployeeModel m: deputedEmpList){
				employeeModelsTemp.add(m);
			}
			for(int i=0;i<employeeModelsTemp.size();i++){
				empMap.put(employeeModelsTemp.get(i).getEmployeeId(), employeeModelsTemp.get(i));
			}
			empModels = new ArrayList<EmployeeModel>(empMap.values());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getEmployeeListForTaskAllocationByDeptId: "+e.getMessage());
		}
		return empModels;
	}

	@Override
	public int getWorkVerificationStatusById(int workDocumentId) {
		int verificationStatus = -1; 
		try{
			WorkDocumentEntity docEntity = projectDAO.getWorkDocumentById(workDocumentId);
			verificationStatus = docEntity.getVerificationStatus();
		}catch(Exception e){
			verificationStatus = -1;
			e.printStackTrace();
			logger.error("getWorkVerificationStatusById: "+e.getMessage());
		}
		return verificationStatus;
	}

	private EmployeeTaskAllocationModel createEmployeeTaskAllocationModel(EmployeeTaskAllocationEntity entity){
		EmployeeTaskAllocationModel model = null;
		try{
			model = new EmployeeTaskAllocationModel();
			model.setCreatedOn(utilService.dateToString(entity.getCreatedOn()));
			//			model.setDescription(entity.getDescription());
			model.setEmpCode(entity.getEmployeeEntity().getEmpCode());
			model.setEmployeeId(entity.getEmployeeId());
			model.setEmpName(entity.getEmployeeEntity().getFirstName()+" "+entity.getEmployeeEntity().getLastName());
			model.setEmpTaskAllocationId(entity.getEmpTaskAllocationId());
			model.setUpdatedOn(utilService.dateToString(entity.getUpdatedOn()));
			model.setWorkDetailsId(entity.getWorkDetailsId());
			model.setWorkName(entity.getWorkDetailsEntity().getWorkName());
			model.setStatus(entity.getStatus());
			model.setSubTaskId(entity.getSubTaskId());
			model.setSubTaskName(entity.getSubTaskEntity().getSubTaskName());

		}catch(Exception e){
			e.printStackTrace();
			logger.error("createEmployeeTaskAllocationModel: "+e.getMessage());
		}
		return model;
	}


	private EmployeeTaskAllocationEntity createEmployeeTaskAllocationEntity
	(EmployeeTaskAllocationModel model){
		EmployeeTaskAllocationEntity entity = null;
		try{
			entity = new EmployeeTaskAllocationEntity();
			entity.setCreatedOn(utilService.stringToDate(model.getCreatedOn()));
			//			entity.setDescription(model.getDescription());
			entity.setEmployeeId(model.getEmployeeId());
			entity.setEmpTaskAllocationId(model.getEmpTaskAllocationId());
			entity.setUpdatedOn(utilService.stringToDate(model.getUpdatedOn()));
			entity.setWorkDetailsId(model.getWorkDetailsId());
			entity.setStatus(model.getStatus());
			entity.setSubTaskId(model.getSubTaskId());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createEmployeeTaskAllocationEntity: "+e.getMessage());
		}
		return entity;
	}

	/**
	 * For sharing the work with another departments
	 */
	@Override
	public WorkIssuedModel saveWorkIssuedDetails(
			WorkIssuedModel workIssuedDetails,String token) {
		WorkIssuedModel model = null;
		try{
			EmployeeEntity employee = userService.getEmployeeByToken(token);
			String dateStr = utilService.dateToString(utilService.getCurrentDate());
			workIssuedDetails.setCreatedEmpId(employee.getEmployeeId());
			workIssuedDetails.setStatus(1);
			workIssuedDetails.setCreatedOn(dateStr);
			workIssuedDetails.setUpdatedOn(dateStr);
			//			System.out.println("ProjectBusiness,saveWorkIssuedDetails,dateStr: "+dateStr);
			WorkIssuedDetailsEntity entity = createWorkIssuedDetailsEntity(workIssuedDetails);
			entity = projectDAO.saveWorkIssuedDetails(entity);
			System.out.println("ProjectBusiness,saveWorkIssuedDetails,WorkIssuedId: "+entity.getWorkIssuedId());
			model = createWorkIssuedModel(entity);

		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveWorkIssuedDetails: "+e.getMessage());
		}
		return model;
	}

	@Override
	public WorkIssuedModel updateWorkIssuedDetails(
			WorkIssuedModel workIssuedDetails) {
		WorkIssuedModel model = null;
		try{
			String dateStr = utilService.dateToString(utilService.getCurrentDate());
			workIssuedDetails.setStatus(1);
			workIssuedDetails.setUpdatedOn(dateStr);
			WorkIssuedDetailsEntity entity = createWorkIssuedDetailsEntity(workIssuedDetails);
			entity = projectDAO.updateWorkIssuedDetails(entity);
			model = createWorkIssuedModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveWorkIssuedDetails: "+e.getMessage());
		}
		return model;
	}

	@Override
	public int  deleteWorkIssuedDetails(
			int workIssuedId) {
		int status = -1;
		WorkIssuedModel model = null;
		try{
			String dateStr = utilService.dateToString(utilService.getCurrentDate());
			WorkIssuedDetailsEntity entity = projectDAO.getWorkIssuedDetailsEntity(workIssuedId);
			entity.setStatus(0);
			entity.setUpdatedOn(utilService.getDateFromString(dateStr));
			entity = projectDAO.updateWorkIssuedDetails(entity);
			model = createWorkIssuedModel(entity);
			status = model.getStatus();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("deleteWorkIssuedDetails: "+e.getMessage());
		}
		return status;
	}

	@Override
	public List<WorkIssuedModel> getWorkIssuedDetailsByDeptId(int departmentId) {
		List<WorkIssuedModel> workIssuedModels = null;
		try{
			List<WorkIssuedDetailsEntity> entities = projectDAO.getWorkIssuedDetailsByDeptId(departmentId);
			workIssuedModels = new ArrayList<WorkIssuedModel>();
			for(WorkIssuedDetailsEntity e: entities){
				WorkIssuedModel m = createWorkIssuedModel(e);
				workIssuedModels.add(m);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkIssuedDetailsByDeptId: "+e.getMessage());
		}
		return workIssuedModels;
	}


	private WorkIssuedModel createWorkIssuedModel(WorkIssuedDetailsEntity entity){
		WorkIssuedModel model = null;
		try{
			model = new WorkIssuedModel();
			model.setCreatedOn(utilService.dateToString(entity.getCreatedOn()));
			model.setCreatedEmpCode(entity.getEmployeeEntity().getEmpCode());
			model.setCreatedEmpId(entity.getEmployeeEntity().getEmployeeId());
			model.setCreatedEmpName(entity.getEmployeeEntity().getFirstName()
					+" "+entity.getEmployeeEntity().getLastName() );
			model.setDepartmentId(entity.getDepartmentEntity().getDepartmentId());
			model.setDepartmentName(entity.getDepartmentEntity().getDepartmentName());
			model.setStatus(entity.getStatus());
			model.setUpdatedOn(utilService.dateToString(entity.getUpdatedOn()));
			model.setWorkDetailsId(entity.getWorkDetailsId());
			model.setWorkIssuedId(entity.getWorkIssuedId());
			model.setWorkName(entity.getWorkDetailsEntity().getWorkName());
			model.setWorkStatusId(entity.getWorkDetailsEntity().getWorkStatusId());
			model.setWorkStatusName(entity.getWorkDetailsEntity().getWorkStatusEntity().getTaskStatusName());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createWorkIssuedModel: "+e.getMessage());
		}
		return model;
	}

	private WorkIssuedDetailsEntity createWorkIssuedDetailsEntity(WorkIssuedModel model){
		WorkIssuedDetailsEntity entity = null;
		try{
			entity =new WorkIssuedDetailsEntity();
			entity.setCreatedEmpId(model.getCreatedEmpId());
			entity.setCreatedOn(utilService.getDateFromString(model.getCreatedOn()));
			entity.setIssuedDeptId(model.getDepartmentId());
			entity.setStatus(model.getStatus());
			entity.setUpdatedOn(utilService.getDateFromString(model.getUpdatedOn()));
			entity.setWorkDetailsId(model.getWorkDetailsId());
			entity.setWorkIssuedId(model.getWorkIssuedId());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createWorkIssuedDetailsEntity: "+e.getMessage());
		}
		return entity;
	}

	// Reply for the messages
	@Override
	public InterOfficeCommunicationModel replyInterOfficeCommunication(String token, MultipartFile[] multipartFiles,
			String contextPath,List<Integer> toDeptList,int workDetailsId,
			int subTaskId, String subject,String description,String referenceNo){
		InterOfficeCommunicationModel commModel = null;
		WorkMessageAttachmentEntity woEnity = null;
		int imageSavestatus = 0;
		try{
			woEnity = new WorkMessageAttachmentEntity();
			EmployeeEntity employee = userService.getEmployeeByToken(token);
			commModel = new InterOfficeCommunicationModel();
			commModel.setWorkDetailsId(workDetailsId);
			commModel.setSubTaskId(subTaskId);
			commModel.setSubject(subject);
			commModel.setReferenceNo(referenceNo);
			commModel.setDepartmentId(employee.getDepartmentId());
			commModel.setDescription(description);
			commModel.setEmployeeId(employee.getEmployeeId());
			//			System.out.println("Business, saveWorkIssuedDetails,Dept Id: "
			//			+model.getDepartmentId()+",Description: "+model.getDescription());
			//			Date today = utilService.getTodaysDate();
			Timestamp timestamp = utilService.getCurrentDateTimeStamp();
			commModel.setCreatedOn(utilService.timeStampToString(timestamp));
			commModel.setUpdatedOn(utilService.timeStampToString(timestamp));
			commModel.setIsActive(1);
			/**
			if(multipartFile != null){
				//					String fileType = multipartFile.getContentType();
				String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
				commModel.setFileType(extension);
				commModel.setOrginalFileName(multipartFile.getOriginalFilename());
			}**/
			List<DepartmentCommunicationMessagesModel> deptMesgList = new ArrayList<DepartmentCommunicationMessagesModel>();
			InterOfficeCommunicationEntity entity = createInterOfficeCommunicationEntity(commModel);
			entity = projectDAO.saveInterOfficeCommunicationEntity(entity);
			InterOfficeCommunicationEntity entityUpdated = projectDAO.getCommunicationEntityById(entity.getOfficeCommunicationId());
			for(int deptId: toDeptList){
				DepartmentCommunicationMessagesModel model = new DepartmentCommunicationMessagesModel();
				model.setDepartmentId(deptId);
				//					System.out.println("Business, sendWorkMessage, deptId: "+deptId);
				deptMesgList.add(model);
			}
			List<DepartmentCommunicationMessagesEntity> departCommEntities = 
					saveDepartmentCommunicationMessagesEntity(deptMesgList,entity.getOfficeCommunicationId());
			departCommEntities = projectDAO.getDepartmentCommunicationMessagesByOffCommId(entity.getOfficeCommunicationId());

			for(DepartmentCommunicationMessagesEntity dc: departCommEntities){
				DepartmentCommunicationMessagesModel m = createDepartmentCommunicationMessagesModel(dc);
				deptMesgList.add(m);
			}

			// File saving to the sever location
			if(multipartFiles != null){
				for(MultipartFile multipartFile: multipartFiles){
					String fileName = utilService.createFileName(multipartFile.getOriginalFilename());
					woEnity.setFileName(fileName);
					woEnity.setOfficeCommunicationId(entityUpdated.getOfficeCommunicationId());
					woEnity = projectDAO.saveWorkMessageAttachment(woEnity);
					String fileLocation = Iconstants.WORK_MESSAGE_DOCUMENT+woEnity.getWorkMsgAthId();
					imageSavestatus = utilService.saveFile(multipartFile, contextPath, fileLocation);
				}
			}
			if(woEnity.getWorkMsgAthId() > 0 && imageSavestatus == 1 ){
				entityUpdated.setAttachementStatus(1);
				entityUpdated = projectDAO.updateInterOfficeCommunicationEntity(entityUpdated);
			}
			List<WorkMessageAttachmentEntity> workAthList = projectDAO.
					getWorkMessageAttachmentByByOffCommId(entityUpdated.getOfficeCommunicationId());
			List<WorkMessageAttachmentModel> woAthModelList = new ArrayList<WorkMessageAttachmentModel>();
			for(WorkMessageAttachmentEntity e: workAthList){
				WorkMessageAttachmentModel model = createWorkMessageAttachmentModel(e);
				woAthModelList.add(model);
			}
			commModel = createInterOfficeCommunicationModel(entityUpdated);
			commModel.setAttachmentModels(woAthModelList);
			commModel.setDeptCommList(deptMesgList);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("replyInterOfficeCommunication: "+e.getMessage());
		}
		return commModel;
	}


	/**
	 *  Send message to other departments, based on the project / work
	 */
	@Override
	public InterOfficeCommunicationModel sendWorkMessage(String token, MultipartFile[] multipartFiles,
			String contextPath,List<Integer> toDeptList,int workDetailsId, int subTaskId, String subject,String description){
		InterOfficeCommunicationModel commModel = null;
		int imageSavestatus = 0;
		WorkMessageAttachmentEntity woEnity = null;
		try{
			woEnity = new WorkMessageAttachmentEntity();
			EmployeeEntity employee = userService.getEmployeeByToken(token);
			commModel = new InterOfficeCommunicationModel();
			commModel.setEmployeeId(employee.getEmployeeId());
			commModel.setWorkDetailsId(workDetailsId);
			commModel.setSubTaskId(subTaskId);
			commModel.setSubject(subject);
			commModel.setDescription(description);
			String refNo = getInterCommRefNo(employee.getDepartmentId());
			Timestamp timestamp = utilService.getCurrentDateTimeStamp();
			commModel.setCreatedOn(utilService.timeStampToString(timestamp));
			commModel.setUpdatedOn(utilService.timeStampToString(timestamp));
			commModel.setReferenceNo(refNo);
			commModel.setDepartmentId(employee.getDepartmentId());
			commModel.setIsActive(1);
			commModel.setAttachementStatus(0);
			//			if(multipartFile != null){
			//				String fileType = multipartFile.getContentType();
			//				commModel.setFileType(fileType);
			//			}

			List<DepartmentCommunicationMessagesModel> deptMesgList = new ArrayList<DepartmentCommunicationMessagesModel>();
			InterOfficeCommunicationEntity entity = createInterOfficeCommunicationEntity(commModel);
			entity = projectDAO.saveInterOfficeCommunicationEntity(entity);
			//	System.out.println("Business, sendWorkMessage, OfficeCommunicationId: "+entity.getOfficeCommunicationId());
			InterOfficeCommunicationEntity entityUpdated = projectDAO.getCommunicationEntityById(entity.getOfficeCommunicationId());

			for(int deptId: toDeptList){
				DepartmentCommunicationMessagesModel model = new DepartmentCommunicationMessagesModel();
				model.setDepartmentId(deptId);
				System.out.println("Business, sendWorkMessage, deptId: "+deptId);
				deptMesgList.add(model);
			}

			List<DepartmentCommunicationMessagesEntity> departCommEntities = 
					saveDepartmentCommunicationMessagesEntity(deptMesgList,entity.getOfficeCommunicationId());
			departCommEntities = projectDAO.getDepartmentCommunicationMessagesByOffCommId(entityUpdated.getOfficeCommunicationId());

			for(DepartmentCommunicationMessagesEntity dc: departCommEntities){
				DepartmentCommunicationMessagesModel m = createDepartmentCommunicationMessagesModel(dc);
				deptMesgList.add(m);
			}
			// File saving to the sever location
			if(multipartFiles != null){
				for(MultipartFile multFile: multipartFiles){
					String fileName = utilService.createFileName(multFile.getOriginalFilename());
					woEnity.setFileName(fileName);
					woEnity.setOrginalFileName(multFile.getOriginalFilename());
					woEnity.setOfficeCommunicationId(entityUpdated.getOfficeCommunicationId());
					woEnity.setContentType(multFile.getContentType());
					woEnity = projectDAO.saveWorkMessageAttachment(woEnity);
					String fileLocation = Iconstants.WORK_MESSAGE_DOCUMENT+woEnity.getWorkMsgAthId();
					imageSavestatus = utilService.saveFile(multFile, contextPath, fileLocation);
				}
			}
			if(woEnity.getWorkMsgAthId() > 0 && imageSavestatus == 1 ){
				entityUpdated.setAttachementStatus(1);
				entityUpdated = projectDAO.updateInterOfficeCommunicationEntity(entityUpdated);
			}
			commModel = createInterOfficeCommunicationModel(entityUpdated);
			List<WorkMessageAttachmentEntity> woEnityList = 
					projectDAO.getWorkMessageAttachmentByByOffCommId(entityUpdated.getOfficeCommunicationId());
			List<WorkMessageAttachmentModel> wAthList = new ArrayList<WorkMessageAttachmentModel>();
			for(WorkMessageAttachmentEntity e : woEnityList){
				WorkMessageAttachmentModel m = createWorkMessageAttachmentModel(e);
				wAthList.add(m);
			}
			commModel.setDeptCommList(deptMesgList);
			commModel.setAttachmentModels(wAthList);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("sendWorkMessage: "+e.getMessage());
		}
		return commModel;
	}

	@Override
	public InterOfficeCommunicationModel saveInterOfficeCommunication(
			InterOfficeCommunicationModel model,String token) {
		InterOfficeCommunicationModel commModel = null;
		try{
			EmployeeEntity employee = userService.getEmployeeByToken(token);
			model.setEmployeeId(employee.getEmployeeId());
			String refNo = getInterCommRefNo(model.getDepartmentId());
			//			System.out.println("Business, saveWorkIssuedDetails,Dept Id: "
			//			+model.getDepartmentId()+",Description: "+model.getDescription());
			//			Date today = utilService.getTodaysDate();
			//			model.setCreatedOn(utilService.dateToString(today));
			//			model.setUpdatedOn(utilService.dateToString(today));
			Timestamp timestamp = utilService.getCurrentDateTimeStamp();
			model.setCreatedOn(utilService.timeStampToString(timestamp));
			model.setUpdatedOn(utilService.timeStampToString(timestamp));
			model.setReferenceNo(refNo);
			model.setIsActive(1);
			List<DepartmentCommunicationMessagesModel> deptMesgList = new ArrayList<DepartmentCommunicationMessagesModel>();

			InterOfficeCommunicationEntity entity = createInterOfficeCommunicationEntity(model);
			entity = projectDAO.saveInterOfficeCommunicationEntity(entity);
			InterOfficeCommunicationEntity entityUpdated = projectDAO.getCommunicationEntityById(entity.getOfficeCommunicationId());
			//System.out.println("Business, saveInterOfficeCommunication,DepartmentName: "+entityUpdated.getDepartment().getDepartmentName());

			List<DepartmentCommunicationMessagesEntity> departCommEntities = 
					saveDepartmentCommunicationMessagesEntity(model.getDeptCommList(),entity.getOfficeCommunicationId());
			departCommEntities = projectDAO.getDepartmentCommunicationMessagesByOffCommId(entityUpdated.getOfficeCommunicationId());

			for(DepartmentCommunicationMessagesEntity dc: departCommEntities){
				DepartmentCommunicationMessagesModel m = createDepartmentCommunicationMessagesModel(dc);
				deptMesgList.add(m);
			}
			commModel = createInterOfficeCommunicationModel(entityUpdated);
			commModel.setDeptCommList(deptMesgList);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveInterOfficeCommunication: "+e.getMessage());
		}
		return commModel;
	}
	/**
	// Reply for the messages
	@Override
	public InterOfficeCommunicationModel replyInterOfficeCommunication
	(InterOfficeCommunicationModel model,String token){
		InterOfficeCommunicationModel commModel = null;
		try{
			EmployeeEntity employee = userService.getEmployeeByToken(token);
			model.setEmployeeId(employee.getEmployeeId());
			//String refNo = getInterCommRefNo(model.getDepartmentId());
			//			System.out.println("Business, saveWorkIssuedDetails,Dept Id: "
			//			+model.getDepartmentId()+",Description: "+model.getDescription());
			//			Date today = utilService.getTodaysDate();
			Timestamp timestamp = utilService.getCurrentDateTimeStamp();
			//			model.setCreatedOn(utilService.dateToString(today));
			//			model.setUpdatedOn(utilService.dateToString(today));
			model.setCreatedOn(utilService.timeStampToString(timestamp));
			model.setUpdatedOn(utilService.timeStampToString(timestamp));
			model.setIsActive(1);
			List<DepartmentCommunicationMessagesModel> deptMesgList = new ArrayList<DepartmentCommunicationMessagesModel>();
			//			System.out.println("Business, saveWorkIssuedDetails,deptMessageList size: "+deptMessageList.size());
			//			for(DepartmentCommunicationMessagesModel dept: deptMessageList){
			//				System.out.println("Business, saveWorkIssuedDetails,deptId: "+dept.getDepartmentId());
			//			}
			InterOfficeCommunicationEntity entity = createInterOfficeCommunicationEntity(model);
			entity = projectDAO.saveInterOfficeCommunicationEntity(entity);
			entity = projectDAO.getCommunicationEntityById(entity.getOfficeCommunicationId());
			//			System.out.println("Business, replyInterOfficeCommunication,OfficeCommunicationId: "+entity.getOfficeCommunicationId()
			//					+", Dept Id: "+entity.getDepartmentId()+",EmployeeId: "+entity.getEmployeeId());
			//			entity = projectDAO.getCommunicationEntityById(entity.getOfficeCommunicationId());
			//			System.out.println("Business, replyInterOfficeCommunication,OfficeCommunicationId: "+entity.getOfficeCommunicationId()
			//					+", "+entity.getDepartmentId()+",EmployeeId: "+entity.getEmployeeId());
			List<DepartmentCommunicationMessagesEntity> departCommEntities = 
					saveDepartmentCommunicationMessagesEntity(model.getDeptCommList(),entity.getOfficeCommunicationId());
			departCommEntities = projectDAO.getDepartmentCommunicationMessagesByOffCommId(entity.getOfficeCommunicationId());

			for(DepartmentCommunicationMessagesEntity dc: departCommEntities){
				DepartmentCommunicationMessagesModel m = createDepartmentCommunicationMessagesModel(dc);
				deptMesgList.add(m);
			}
			commModel = createInterOfficeCommunicationModel(entity);
			commModel.setDeptCommList(deptMesgList);

		}catch(Exception e){
			e.printStackTrace();
			logger.error("replyInterOfficeCommunication: "+e.getMessage());
		}
		return commModel;
	}
	 **/
	/**
	 * Searching for the InterOfficeCommunication messages
	 * @param searchKeyWord
	 * @param startDate
	 * @param endDate
	 * @param departmentId
	 * @return
	 */
	@Override
	public List<CommunicationMessageFormatModel> searchInterDeptCommList(String searchKeyWord,
			String startDate, String endDate,int departmentId ){
		List<CommunicationMessageFormatModel> commMsgList = new ArrayList<CommunicationMessageFormatModel>();
		List<InterOfficeCommunicationModel> interOffComList = null;
		Date stDate =null;
		Date eDate = null;

		try{
			interOffComList = new ArrayList<InterOfficeCommunicationModel>();
			if(startDate != null){
				stDate = utilService.getDateFromString(startDate);
			}
			if( endDate != null){
				eDate = utilService.getDateFromString(endDate);
			}
			if( searchKeyWord == null){
				searchKeyWord = "";
			}
			List<InterOfficeCommunicationEntity> entityList = 
					projectDAO.searchInterDeptCommList(searchKeyWord, stDate, eDate, departmentId);
			for(InterOfficeCommunicationEntity ie: entityList){
				InterOfficeCommunicationModel model = createInterOfficeCommunicationModel(ie);
				List<DepartmentCommunicationMessagesEntity> deptCommList = projectDAO.
						getDepartmentCommunicationMessagesByDeptId(ie.getDepartmentId());
				List<DepartmentCommunicationMessagesModel> depModel = new ArrayList<DepartmentCommunicationMessagesModel>();
				for(DepartmentCommunicationMessagesEntity dc: deptCommList){
					DepartmentCommunicationMessagesModel m = createDepartmentCommunicationMessagesModel(dc);
					depModel.add(m);
				}
				model.setDeptCommList(depModel);
				interOffComList.add(model);

				commMsgList = getCommunicationMessageFormatModel(interOffComList);
			}
			//			Collections.reverse(commMsgList);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("searchInterDeptCommList: "+e.getMessage());
		}
		//		return interOffComList;
		return commMsgList;
	}


	private List<DepartmentCommunicationMessagesEntity> saveDepartmentCommunicationMessagesEntity
	(List<DepartmentCommunicationMessagesModel> deptMessageList,int officeCommunicationId){
		List<DepartmentCommunicationMessagesEntity> departCommEntities = null;
		try{
			departCommEntities = new ArrayList<DepartmentCommunicationMessagesEntity>();
			//logger.info("Business,saveDepartmentCommunicationMessagesEntity, deptMessageList.size: "+deptMessageList.size());
			if(officeCommunicationId > 0){
				Date today = utilService.getTodaysDate();
				Timestamp timestamp = utilService.getCurrentDateTimeStamp();
				//				model.setCreatedOn(utilService.dateToString(today));
				//				model.setUpdatedOn(utilService.dateToString(today));
				for(DepartmentCommunicationMessagesModel dc: deptMessageList){
					dc.setOfficeCommunicationId(officeCommunicationId);
					dc.setDepartmentId(dc.getDepartmentId());
					//					dc.setCreatedOn(utilService.dateToString(today));
					//					dc.setUpdatedOn(utilService.dateToString(today));
					dc.setCreatedOn(utilService.timeStampToString(timestamp));
					dc.setUpdatedOn(utilService.timeStampToString(timestamp));
					DepartmentCommunicationMessagesEntity e = createDepartmentCommunicationMessagesEntity(dc);
					departCommEntities.add(e);
				}
				//	System.out.println("Business,saveDepartmentCommunicationMessagesEntity, departCommEntities,size: "+departCommEntities.size());
				departCommEntities = projectDAO.saveDepartmentCommunicationMessages(departCommEntities);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveDepartmentCommunicationMessagesEntity: "+e.getMessage());
		}
		return departCommEntities;
	}

	@Override
	public InterOfficeCommunicationModel updateInterOfficeCommunication(
			InterOfficeCommunicationModel model,String token) {
		InterOfficeCommunicationModel commModel = null;
		try{
			Date today = utilService.getTodaysDate();
			//			System.out.println("updateInterOfficeCommunication, date :"+today);
			model.setUpdatedOn(utilService.dateToString(today));
			model.setIsActive(1);
			InterOfficeCommunicationEntity entity = createInterOfficeCommunicationEntity(model);
			entity = projectDAO.updateInterOfficeCommunicationEntity(entity);
			entity = projectDAO.getCommunicationEntityById(entity.getOfficeCommunicationId());
			commModel = createInterOfficeCommunicationModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateInterOfficeCommunication: "+e.getMessage());
		}
		return commModel;
	}

	@Override
	public InterOfficeCommunicationModel getCommunicationById(
			int officeCommunicationId) {
		InterOfficeCommunicationModel commModel = null;
		try{
			InterOfficeCommunicationEntity entity = projectDAO.getCommunicationEntityById(officeCommunicationId);
			List<DepartmentCommunicationMessagesEntity> depMesgList = projectDAO.getDepartmentCommunicationMessagesByOffCommId
					(entity.getOfficeCommunicationId());
			//	System.out.println("Bussiness, getCommunicationById, CommunicationId: "+entity.getOfficeCommunicationId());
			commModel = createInterOfficeCommunicationModel(entity);
			if(depMesgList != null){
				List<DepartmentCommunicationMessagesModel> mList = new ArrayList<DepartmentCommunicationMessagesModel>();
				for(DepartmentCommunicationMessagesEntity e: depMesgList){
					DepartmentCommunicationMessagesModel m = createDepartmentCommunicationMessagesModel(e);
					mList.add(m);
				}
				commModel.setDeptCommList(mList);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getCommunicationById: "+e.getMessage());
		}
		return commModel;
	}

	// Only for UI purpose
	@Override
	public List<InterOfficeCommunicationModel> getCommunicationListById(String contextPath, int officeCommunicationId){
		List<InterOfficeCommunicationModel> list = null;
		try{
			list = new ArrayList<InterOfficeCommunicationModel>();
			InterOfficeCommunicationEntity entity = projectDAO.getCommunicationEntityById(officeCommunicationId);
			List<WorkMessageAttachmentModel> messageAttacmentModelList = getWorkMessageAttachmentByOffComId(contextPath,
					officeCommunicationId);

			List<DepartmentCommunicationMessagesEntity> depMesgList = projectDAO.getDepartmentCommunicationMessagesByOffCommId
					(entity.getOfficeCommunicationId());
			//System.out.println("Bussiness, getCommunicationById, CommunicationId: "+entity.getOfficeCommunicationId());
			InterOfficeCommunicationModel commModel = createInterOfficeCommunicationModel(entity);
			commModel.setAttachmentModels(messageAttacmentModelList);
			if(depMesgList != null){
				List<DepartmentCommunicationMessagesModel> mList = new ArrayList<DepartmentCommunicationMessagesModel>();
				for(DepartmentCommunicationMessagesEntity e: depMesgList){
					DepartmentCommunicationMessagesModel m = createDepartmentCommunicationMessagesModel(e);
					mList.add(m);
				}
				commModel.setDeptCommList(mList);
			}
			list.add(commModel);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getCommunicationListById: "+e.getMessage());
		}
		return list;
	}

	@Override
	public InterOfficeCommunicationModel deleteCommunicationById(
			int officeCommunicationId) {
		return null;
	}

	@Override
	public List<InterOfficeCommunicationModel> getCommunicationListBySubTaskId(
			int subTaskId) {
		List<InterOfficeCommunicationModel> commList = null;
		try{
			commList = new ArrayList<InterOfficeCommunicationModel>();
			List<InterOfficeCommunicationEntity> entityList = projectDAO.getCommunicationEntityBySubTaskId(subTaskId);
			//        	System.out.println("Service,getCommunicationListBySubTaskId,entityList: "+entityList.size());
			for(InterOfficeCommunicationEntity e: entityList){
				InterOfficeCommunicationModel m = createInterOfficeCommunicationModel(e);
				// Dept Message
				List<DepartmentCommunicationMessagesEntity> depMesgList = projectDAO.getDepartmentCommunicationMessagesByOffCommId
						(e.getOfficeCommunicationId());

				if(depMesgList != null){
					List<DepartmentCommunicationMessagesModel> mList = new ArrayList<DepartmentCommunicationMessagesModel>();
					for(DepartmentCommunicationMessagesEntity deptMsg: depMesgList){
						DepartmentCommunicationMessagesModel mod = createDepartmentCommunicationMessagesModel(deptMsg);
						mList.add(mod);
					}
					m.setDeptCommList(mList);
				}
				commList.add(m);
			}


		}catch(Exception e){
			e.printStackTrace();
			logger.error("getCommunicationListBySubTaskId: "+e.getMessage());
		}
		return commList;
	}

	@Override
	public List<InterOfficeCommunicationModel> getCommunicationListByWorkId(
			int workDetailsId) {
		List<InterOfficeCommunicationModel> commList = null;
		try{
			commList = new ArrayList<InterOfficeCommunicationModel>();
			List<InterOfficeCommunicationEntity> entityList = projectDAO.getCommunicationEntityByWorkId(workDetailsId);
			for(InterOfficeCommunicationEntity e: entityList){
				InterOfficeCommunicationModel m = createInterOfficeCommunicationModel(e);
				//commList.add(m);
				// Dept Message
				List<DepartmentCommunicationMessagesEntity> depMesgList = projectDAO.getDepartmentCommunicationMessagesByOffCommId
						(e.getOfficeCommunicationId());

				if(depMesgList != null){
					List<DepartmentCommunicationMessagesModel> mList = new ArrayList<DepartmentCommunicationMessagesModel>();
					for(DepartmentCommunicationMessagesEntity deptMsg: depMesgList){
						DepartmentCommunicationMessagesModel mod = createDepartmentCommunicationMessagesModel(deptMsg);
						mList.add(mod);
					}
					m.setDeptCommList(mList);
				}
				commList.add(m);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getCommunicationListByWorkId: "+e.getMessage());
		}
		return commList;
	}

	/**
	 *  Inbox / Incoming messages
	 */
	public List<CommunicationMessageFormatModel> getInboxMessageByDeptId(int departmentId ){
		List<CommunicationMessageFormatModel> messageList = null;

		try{
			messageList = new ArrayList<CommunicationMessageFormatModel>();
			List<InterOfficeCommunicationModel> commList = null;
			commList = new ArrayList<InterOfficeCommunicationModel>();

			List<DepartmentCommunicationMessagesEntity> deptMessageList = projectDAO.
					getDepartmentCommunicationMessagesByDeptId(departmentId);

			for(DepartmentCommunicationMessagesEntity deptCommMesg: deptMessageList){
				InterOfficeCommunicationEntity entity = deptCommMesg.getInterOfficeCommunicationEntity();

				InterOfficeCommunicationModel interOffCommModel = createInterOfficeCommunicationModel(entity);
				List<DepartmentCommunicationMessagesEntity> deptMsgEnyList = 
						projectDAO.getDepartmentCommunicationMessagesByOffCommId(interOffCommModel.getOfficeCommunicationId());
				List<DepartmentCommunicationMessagesModel> deptMsgModelList = new ArrayList<DepartmentCommunicationMessagesModel>();
				for(DepartmentCommunicationMessagesEntity e: deptMsgEnyList){
					DepartmentCommunicationMessagesModel deptModel = createDepartmentCommunicationMessagesModel(e);
					deptMsgModelList.add(deptModel);
				}
				interOffCommModel.setDeptCommList(deptMsgModelList);
				commList.add(interOffCommModel);
			}

			messageList = getCommunicationMessageFormatModel(commList);
			Collections.reverse(messageList);
			//			System.out.println("Business, getInboxMessageByDeptId: ");
			//			for(CommunicationMessageFormatModel model : messageList){
			//				System.out.println("model, TitleDate: "+model.getTitleDate());
			//			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getInboxMessageByDeptId: "+e.getMessage());
		}
		return messageList;
	}

	private List<CommunicationMessageFormatModel> getCommunicationMessageFormatModel
	(List<InterOfficeCommunicationModel> commList){
		List<CommunicationMessageFormatModel> messageList = null;
		try{
			messageList = new ArrayList<CommunicationMessageFormatModel>();
			TreeMap<String, HashMap<String, List<InterOfficeCommunicationModel>>> sortedTreeSet = new TreeMap<>(Collections.reverseOrder());
			HashMap<String,HashMap<String, List<InterOfficeCommunicationModel>>> dateMap = 
					new HashMap<String, HashMap<String,List<InterOfficeCommunicationModel>>>();
			HashMap<String, List<InterOfficeCommunicationModel>> 
			referenceNoMap = new HashMap<String, List<InterOfficeCommunicationModel>>();
			commList.sort((e1, e2) -> e1.getOfficeCommunicationId()- e2.getOfficeCommunicationId());
			Collections.reverse(commList);
			// Groping based on the reference number.
			for(InterOfficeCommunicationModel cm: commList){
				if(referenceNoMap.containsKey(cm.getReferenceNo())){
					List<InterOfficeCommunicationModel> list = referenceNoMap.get(cm.getReferenceNo());
					list.add(cm);
					referenceNoMap.put(cm.getReferenceNo(), list);
				}else{
					List<InterOfficeCommunicationModel> list = new ArrayList<InterOfficeCommunicationModel>();
					list.add(cm);
					referenceNoMap.put(cm.getReferenceNo(), list);
				}
			}//for(InterOfficeCommunicationModel cm: commList)

			for (Map.Entry<String, List<InterOfficeCommunicationModel>> map : referenceNoMap.entrySet()){
				List<InterOfficeCommunicationModel> list = map.getValue();
				// Sorting , acending order
				list.sort((e1, e2) -> e1.getOfficeCommunicationId()- e2.getOfficeCommunicationId());
				Collections.reverse(list);
				HashMap<String, List<InterOfficeCommunicationModel>>  datMap = new HashMap<String, List<InterOfficeCommunicationModel>>();
				datMap.put(map.getKey(), list);
				dateMap.put(list.get(0).getCreatedOn(), datMap);
				//				dateMap.put(list.get(0).getCreatedOn(), referenceNoMap);
				//				System.out.println("Bussiness,getCommunicationMessageFormatModel,Date: "+list.get(0).getCreatedOn()
				//						+", Ref no: "+map.getKey());
			}

			sortedTreeSet.putAll(dateMap);
			sortedTreeSet.descendingKeySet();
			for (Map.Entry<String, HashMap<String, List<InterOfficeCommunicationModel>>>
			entry : sortedTreeSet.entrySet()){
				CommunicationMessageFormatModel msgFormat = new CommunicationMessageFormatModel();
				msgFormat.setTitleDate(entry.getKey());
				//System.out.println("------Title date: "+entry.getKey()+"----------");
				HashMap<String, List<InterOfficeCommunicationModel>> refMap = entry.getValue();
				List< CommunicationMessageRefNoDetailsModel> comMsgRefList = 
						new ArrayList<CommunicationMessageRefNoDetailsModel>();
				for (Map.Entry<String, List<InterOfficeCommunicationModel>> rMap :
					refMap.entrySet()) {
					CommunicationMessageRefNoDetailsModel model = new CommunicationMessageRefNoDetailsModel();
					model.setTitleRefNo(rMap.getKey());
					//System.out.println("Title date: "+entry.getKey()+", TitleRefNo: "+rMap.getKey()+", Date : "+entry.getKey());
					model.setOffCommList(rMap.getValue());
					comMsgRefList.add(model);
				}
				msgFormat.setComMsgRefList(comMsgRefList);
				//				msgFormat.setComMsgRefList(comMsgRefList);
				messageList.add(msgFormat);
				//System.out.println("entry.getKey: "+entry.getKey()+", msgFormat, TitleDate: "+msgFormat.getTitleDate());
			}//for (Map.Entry<String, HashMap<String, List<InterOfficeCommunicationModel>>>

			//			commList.sort((e1, e2) -> e1.getCreatedOn().compareTo(e2.getCreatedOn()));
			//messageList.sort((e1, e2) -> e1.getTitleDate().compareTo(e2.getTitleDate()));
			Collections.reverse(messageList);
			//			Collections.reverse(commList);
			//			for(InterOfficeCommunicationModel model : commList){
			//				System.out.println("ReferenceNo: "+model.getReferenceNo()+"------------- ");
			//			}

			//			for(CommunicationMessageFormatModel model : messageList){
			//				System.out.println("------- TitleDate: "+model.getTitleDate()+"------------- ");
			//				System.out.println("------- TitleDate: "+model.getTitleDate()+"------------- ");
			//				for(int i=0;i<model.getComMsgRefList().size();i++){
			//					System.out.println("-------TitleRefNo: "+model.getComMsgRefList().get(i).getTitleRefNo()+"------------- ");
			//					for(int j=0;j<model.getComMsgRefList().get(i).getOffCommList().size();j++){
			//						InterOfficeCommunicationModel m = model.getComMsgRefList().get(i).getOffCommList().get(j);
			//						System.out.println("CommunicationId: "+m.getOfficeCommunicationId()+",Date : "+m.getCreatedOn());
			//					}
			//				}
			//			}
			//			System.out.println("End - getCommunicationMessageFormatModel:------------ ");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getCommunicationMessageFormatModel: "+e.getMessage());
		}
		return messageList;
	}


	/**
	 * Sent message details to other departments
	 */

	@Override
	public List<CommunicationMessageFormatModel> getCommunicationListByDeptId(int departmentId ){
		List<CommunicationMessageFormatModel> messageList = null;
		try{
			messageList = new ArrayList<CommunicationMessageFormatModel>();
			List<InterOfficeCommunicationModel> commList = new ArrayList<InterOfficeCommunicationModel>();
			HashMap<String, List<InterOfficeCommunicationModel>> referenceNoMap = null;
			commList = new ArrayList<InterOfficeCommunicationModel>();
			referenceNoMap = new HashMap<String, List<InterOfficeCommunicationModel>>();
			HashMap<String,HashMap<String, List<InterOfficeCommunicationModel>>> dateMap = 
					new HashMap<String, HashMap<String,List<InterOfficeCommunicationModel>>>();
			TreeMap<String, HashMap<String, List<InterOfficeCommunicationModel>>> sortedTreeSet = new TreeMap<>(Collections.reverseOrder());
			int status = 1;
			List<InterOfficeCommunicationEntity> entityList = projectDAO.getCommunicationEntityByDeptId(departmentId);
			for(InterOfficeCommunicationEntity e: entityList){
				InterOfficeCommunicationModel intOffComMod = createInterOfficeCommunicationModel(e);
				// Dept Message
				List<DepartmentCommunicationMessagesEntity> depMesgList = projectDAO.getDepartmentCommunicationMessagesByOffCommId
						(e.getOfficeCommunicationId());
				if(depMesgList != null){
					List<DepartmentCommunicationMessagesModel> deptComMsgList = new ArrayList<DepartmentCommunicationMessagesModel>();
					for(DepartmentCommunicationMessagesEntity deptMsg: depMesgList){
						DepartmentCommunicationMessagesModel departComMod = createDepartmentCommunicationMessagesModel(deptMsg);
						deptComMsgList.add(departComMod);
					}
					intOffComMod.setDeptCommList(deptComMsgList);
				}
				commList.add(intOffComMod);
				messageList = getCommunicationMessageFormatModel(commList);

			}
			Collections.reverse(messageList);
			//			System.out.println("Business, getCommunicationListByDeptId: ");
			//			for(CommunicationMessageFormatModel model : messageList){
			//				System.out.println("model, TitleDate: "+model.getTitleDate());
			//			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getCommunicationListByDeptId: "+e.getMessage());
		}

		return messageList;
	}


	private DepartmentCommunicationMessagesModel createDepartmentCommunicationMessagesModel
	(DepartmentCommunicationMessagesEntity entity){
		DepartmentCommunicationMessagesModel model = null;
		try{
			model = new DepartmentCommunicationMessagesModel();
			model.setCreatedOn(utilService.timeStampToString(entity.getCreatedOn()));
			model.setDepartmentId(entity.getDepartmentId());
			model.setDepartmentName(entity.getDepartment().getDepartmentName());
			model.setDeptCommId(entity.getDeptCommId());
			model.setOfficeCommunicationId(entity.getOfficeCommunicationId());
			//			model.setUpdatedOn(utilService.dateToString(entity.getUpdatedOn()));
			model.setUpdatedOn(utilService.timeStampToString(entity.getCreatedOn()));
			model.setViewStatus(entity.getViewStatus());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createDepartmentCommunicationMessagesModel: "+e.getMessage());
		}
		return model;
	}

	private DepartmentCommunicationMessagesEntity createDepartmentCommunicationMessagesEntity
	(DepartmentCommunicationMessagesModel model){
		DepartmentCommunicationMessagesEntity entity = null;
		try{
			entity = new DepartmentCommunicationMessagesEntity();
			//			entity.setCreatedOn(utilService.getDateFromString(model.getCreatedOn()));
			entity.setCreatedOn(utilService.stringDateToTimestamp(model.getCreatedOn()));
			entity.setDepartmentId(model.getDepartmentId());
			entity.setOfficeCommunicationId(model.getOfficeCommunicationId());
			//			entity.setUpdatedOn(utilService.getDateFromString(model.getUpdatedOn()));
			entity.setUpdatedOn(utilService.stringDateToTimestamp(model.getUpdatedOn()));
			entity.setViewStatus(model.getViewStatus());
			//			System.out.println("Business,createDepartmentCommunicationMessagesEntity, time stamp str: "+model.getCreatedOn()
			//					+", Timestamp: "+entity.getCreatedOn());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createDepartmentCommunicationMessagesEntity: "+e.getMessage());
		}
		return entity;
	}

	private InterOfficeCommunicationModel createInterOfficeCommunicationModel
	(InterOfficeCommunicationEntity entity){
		InterOfficeCommunicationModel model = null;
		try{
			EmployeeModel emp = userService.getEmployeeById(entity.getEmployeeId());
			model = new InterOfficeCommunicationModel();
			model.setAnnexureFormat(Iconstants.ANNEXURE_FORMAT);
			if(entity.getCreatedOn() != null){
				model.setCreatedOn(utilService.timeStampToString(entity.getCreatedOn()));
			}
			model.setDepartmentId(entity.getDepartment().getDepartmentId());
			model.setDepartmentName(entity.getDepartment().getDepartmentName());
			model.setDescription(entity.getDescription());
			model.setEmpCode(emp.getEmpCode());
			model.setEmployeeId(entity.getEmployeeId());
			model.setEmployeeName(emp.getFirstName()+" "+emp.getLastName());
			model.setOfficeCommunicationId(entity.getOfficeCommunicationId());
			model.setReferenceNo(entity.getReferenceNo());
			model.setUpdatedOn(utilService.timeStampToString(entity.getUpdatedOn()));
			model.setSubTaskId(entity.getSubTaskId());
			model.setSubTaskName(entity.getSubTaskEntity().getSubTaskName());
			model.setWorkDetailsId(entity.getWorkDetailsId());
			model.setWorkName(entity.getWorkDetailsEntity().getWorkName());
			model.setSubject(entity.getSubject());
			model.setAttachementStatus(entity.getAttachementStatus());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createInterOfficeCommunicationModel: "+e.getMessage());
		}
		return model;
	}

	private InterOfficeCommunicationEntity createInterOfficeCommunicationEntity
	(InterOfficeCommunicationModel model){
		InterOfficeCommunicationEntity entity = null;
		try{
			entity = new InterOfficeCommunicationEntity();
			if(model.getCreatedOn() != null){
				//				entity.setCreatedOn(utilService.getDateFromString(model.getCreatedOn()));
				entity.setCreatedOn(utilService.stringToTimestamp(model.getCreatedOn()));
			}
			entity.setDepartmentId(model.getDepartmentId());
			entity.setDescription(model.getDescription());
			entity.setEmployeeId(model.getEmployeeId());
			entity.setOfficeCommunicationId(model.getOfficeCommunicationId());
			entity.setReferenceNo(model.getReferenceNo());
			//			entity.setUpdatedOn(utilService.getDateFromString(model.getUpdatedOn()));
			entity.setUpdatedOn(utilService.stringToTimestamp(model.getUpdatedOn()));
			entity.setSubTaskId(model.getSubTaskId());
			entity.setWorkDetailsId(model.getWorkDetailsId());
			entity.setSubject(model.getSubject());
			entity.setAttachementStatus(model.getAttachementStatus());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createInterOfficeCommunicationModel: "+e.getMessage());
		}
		return entity;
	}

	@Override
	public List<DepartmentEntity> getDepartmentListByWorkId(int workDetailsId) {
		List<DepartmentEntity> departmentList = null;
		try{

			List<WorkIssuedDetailsEntity> workEnity = projectDAO.getWorkIssuedDetailsByWorkId(workDetailsId);
			Map<Integer ,DepartmentEntity > map = new HashMap<Integer ,DepartmentEntity >();
			for(WorkIssuedDetailsEntity work: workEnity){
				map.put(work.getIssuedDeptId(), work.getDepartmentEntity());
			}
			if(map.size() > 0){
				departmentList = new ArrayList<>(map.values());
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDepartmentListByWorkId: "+e.getMessage());
		}

		return departmentList;
	}

	/**
	 *  Once the message is open by the Dept head, then view status change to 1
	 */
	@Override
	public DepartmentCommunicationMessagesModel viewUpdateDepartmentCommunicationMessage(
			int deptCommId, String token) {
		DepartmentCommunicationMessagesModel model = null;
		//		System.out.println("viewUpdateDepartmentCommunicationMessage,deptCommId: "+utilService.getCurrentDateTimeStamp());
		try{
			DepartmentCommunicationMessagesEntity entity = projectDAO.getDepartmentCommunicationMessagesById(deptCommId);
			//			System.out.println("viewUpdateDepartmentCommunicationMessage,DepartmentId: "+entity.getDepartment().getDepartmentId());
			EmployeeEntity emp = userService.getEmployeeByToken(token);
			int roleId = emp.getEmpolyeeTypeId();
			//			System.out.println("");
			// Admin, HOD or Co-ordinator 
			
			switch (roleId) {
			case 1:
			case 2:
			case 4:
				entity.setViewStatus(1); 
				entity.setUpdatedOn(utilService.getCurrentDateTimeStamp());
				entity = projectDAO.updateDepartmentCommunicationMessage(entity);
				break;

			default:
				break;
			}
			/**
			if(roleId == 1 || roleId == 2 || roleId == 4){
				entity.setViewStatus(1); //
				//entity.setUpdatedOn(utilService.getCurrentDateTime());
				entity.setUpdatedOn(utilService.getCurrentDateTimeStamp());
				//				System.out.println("viewUpdateDepartmentCommunicationMessage,CurrentDateTimeStamp: "+utilService.getCurrentDateTimeStamp());
				entity = projectDAO.updateDepartmentCommunicationMessage(entity);
			}**/
			model = createDepartmentCommunicationMessagesModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("viewUpdateDepartmentCommunicationMessage: "+e.getMessage());
		}
		return model;
	}

	@Override
	public DepartmentGeneralMessageModel viewUpdateDepartmentGenMessage(int deptGeneralMsgId,String token){
		DepartmentGeneralMessageModel model = null;
		try{
			DepartmentGeneralMessageEntity entity = projectDAO.getDepartmentGeneralMessageListById(deptGeneralMsgId);
		//	System.out.println("Business, viewUpdateDepartmentGenMessage, deptGeneralMsgId: "+deptGeneralMsgId);
			EmployeeEntity emp = userService.getEmployeeByToken(token);
			int roleId = emp.getEmpolyeeTypeId();
			// Admin, HOD or Co-ordinator 
			switch (roleId) {
			case 1:
			case 2:
			case 4:
				entity.setViewStatus(1); 
				entity.setUpdatedOn(utilService.getCurrentDateTimeStamp());
				entity = projectDAO.updateDepartmentGeneralMessageEntity(entity);
				break;

			default:
				break;
			}
			model = createDepartmentGenMessageModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("viewUpdateDepartmentGenMessage: "+e.getMessage());
		}
		return model;

	}

	// Reference number for Intercommunication message
	private String getInterCommRefNo(int departmentId){
		String refNo = null;
		try{
			InterCommRefNoEntity interCommRefNoEntity = projectDAO.getInterCommRefByDeptId(departmentId);

			interCommRefNoEntity = createInterComRefNo(interCommRefNoEntity);

			//			System.out.println("getInterCommRefNo,DepartmentId: "+interCommRefNoEntity.getDepartmentId()+
			//					", DeptAbbreviation(): "+interCommRefNoEntity.getDeptAbbreviation()+
			//					", CurrentYear: "+interCommRefNoEntity.getCurrentYear()+
			//					", No: "+interCommRefNoEntity.getNo() );

			String depAbr = interCommRefNoEntity.getDeptAbbreviation();
			String curtYr = String.valueOf(interCommRefNoEntity.getCurrentYear());
			String no = String.valueOf(interCommRefNoEntity.getNo());

			refNo = depAbr+"/"+curtYr+"/"+no;
			System.out.println("getInterCommRefNo,refNo: "+refNo);
			if(interCommRefNoEntity != null){
				interCommRefNoEntity = projectDAO.updateInterCommRefNo(interCommRefNoEntity);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getInterCommRefNo: "+e.getMessage());
		}
		return refNo;
	}

	/**
	 * Creating the RefNo for Inter communication
	 * @param refNo
	 * @return
	 */
	private InterCommRefNoEntity createInterComRefNo(InterCommRefNoEntity refNo) {
		InterCommRefNoEntity refNum = null;
		int number = 0;
		try{
			Date today = utilService.getCurrentDate();
			int currentYear = utilService.getYearByDate(today);
			if( currentYear == refNo.getCurrentYear()){
				number = refNo.getNo() + 1;
			}else{
				number = 1;
				refNo.setCurrentYear(currentYear);
			}
			refNo.setNo(number);
			refNum = refNo;
			//System.out.println("createInterComRefNo,number: "+refNo.getNo()+",Curr yr: "+refNo.getCurrentYear());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createInterComRefNo: "+e.getMessage());
		}
		return refNum;
	}

	@Override
	public List<WorkMessageAttachmentModel> getWorkMessageAttachmentByOffComId(String realPath, int officeCommunicationId){
		List<WorkMessageAttachmentModel> modelList = null;
		try{
			modelList = new ArrayList<WorkMessageAttachmentModel>();
			List<WorkMessageAttachmentEntity> workMsgList = projectDAO.getWorkWorkMessageAttachmentByOffComId(officeCommunicationId);
			//			System.out.println("Business, getWorkMessageAttachmentByOffComId, offComId: "+workMsg.getWorkMsgAthId());
			for(WorkMessageAttachmentEntity entity: workMsgList){
				WorkMessageAttachmentModel model = createWorkMessageAttachmentModel(entity);
				//				String fileLocation = Iconstants.WORK_MESSAGE_DOCUMENT+entity.getWorkMsgAthId();
				//				String documentLocation = fileLocation+"/"+entity.getFileName();
				//				//			String documentLocation = "D:/KSEB.docx";
				//				//			String documentLocation = "D:/watermark.pdf";
				//				//			String documentLocation = "D:/HelloWorld-Stamped.pdf";
				//				model.setFileName(entity.getFileName());
				//				String fileType = entity.getInterOfficeCommunicationEntity().getFileType();
				//				model.setFileType(fileType);
				//				model.setOfficeCommunicationId(entity.getOfficeCommunicationId());
				//				model.setOrginalFileName(entity.getInterOfficeCommunicationEntity().getOrginalFileName());
				//				model.setWorkMsgAthId(entity.getWorkMsgAthId());
				//				model.setFileLocation(documentLocation);
				modelList.add(model);
			}
			//			System.out.println("Business, getWorkMessageAttachmentByOffComId, contentType: "+contentType
			//					+", fileType: "+fileType);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkMessageAttachmentByOffComId: "+e.getMessage());
		}

		return modelList;
	}

	private WorkMessageAttachmentModel createWorkMessageAttachmentModel(WorkMessageAttachmentEntity entity){
		WorkMessageAttachmentModel model = new WorkMessageAttachmentModel();
		try{
			model = new WorkMessageAttachmentModel();
			String fileLocation = Iconstants.WORK_MESSAGE_DOCUMENT+entity.getWorkMsgAthId();
			//			String[] arrOfStr = realPath.split(Iconstants.BUILD_NAME, 2); 
			//			String path = arrOfStr[0]+fileLocation;
			String documentLocation = fileLocation+"/"+entity.getFileName();
			//			String documentLocation = "D:/KSEB.docx";
			//			String documentLocation = "D:/watermark.pdf";
			//			String documentLocation = "D:/HelloWorld-Stamped.pdf";
			model.setFileName(entity.getFileName());
			String fileType = entity.getContentType();
			model.setFileType(fileType);
			model.setOfficeCommunicationId(entity.getOfficeCommunicationId());
			model.setOrginalFileName(entity.getOrginalFileName());
			model.setWorkMsgAthId(entity.getWorkMsgAthId());
			model.setFileLocation(documentLocation);

		}catch(Exception e){
			e.printStackTrace();
			logger.error("createWorkMessageAttachmentModel: "+e.getMessage());
		}
		return model;
	}//createWorkMessageAttachmentModel

	@Override
	public GeneralMessageModel sendToGeneralMessage(String token,
			MultipartFile[] multipartFiles, String contextPath,
			List<Integer> toDeptList, String subject, String description) {
		GeneralMessageModel model = null;
		int imageSavestatus = 0;
		try{
			model = new GeneralMessageModel();
			EmployeeEntity employee = userService.getEmployeeByToken(token);
			Timestamp timestamp = utilService.getCurrentDateTimeStamp();
			GeneralMessageEntity genMsgentity = new GeneralMessageEntity();
			String refNo = getInterCommRefNo(employee.getDepartmentId());
			genMsgentity.setCreatedOn(timestamp);
			genMsgentity.setDepartmentId(employee.getDepartmentId());
			genMsgentity.setDescription(description);
			genMsgentity.setEmployeeId(employee.getEmployeeId());
			genMsgentity.setReferenceNo("GEN/"+refNo);
			genMsgentity.setSubject(subject);
			genMsgentity.setUpdatedOn(timestamp);

			genMsgentity = projectDAO.saveGeneralMessage(genMsgentity);
			genMsgentity = projectDAO.getGeneralMessageById(genMsgentity.getGenMessageId());
			List<DepartmentGeneralMessageEntity> deptMsgEnyList = new ArrayList<DepartmentGeneralMessageEntity>();

			for(int deptId: toDeptList){
				DepartmentGeneralMessageEntity deptMsg = new DepartmentGeneralMessageEntity();
				deptMsg.setDepartmentId(deptId);
				deptMsg.setGenMessageId(genMsgentity.getGenMessageId());
				deptMsg.setViewStatus(0);
				deptMsg.setCreatedOn(timestamp);
				deptMsg.setUpdatedOn(timestamp);
				deptMsgEnyList.add(deptMsg);
			}

			deptMsgEnyList = projectDAO.saveDepartmentGeneralMessageList(deptMsgEnyList);
			//			System.out.println("Business,sendGeneralMessage, multipartFiles size: "+multipartFiles.length);
			// File saving to the sever location
			if(multipartFiles != null){
				List<GeneralMessageAttachmentEntity> genAthList = new ArrayList<GeneralMessageAttachmentEntity>();
				for(MultipartFile file: multipartFiles){
					GeneralMessageAttachmentEntity genAth = new GeneralMessageAttachmentEntity();
					String fileName = utilService.createFileName(file.getOriginalFilename());
					genAth.setFileName(fileName);
					genAth.setGenMessageId(genMsgentity.getGenMessageId());
					String fileLocation = Iconstants.GENERAL_MESSAGE_DOCUMENT+genMsgentity.getGenMessageId();
					imageSavestatus = utilService.saveFile(file, contextPath, fileLocation);
					if(imageSavestatus > 0){
						genAthList.add(genAth);
					}
				}
				genAthList = projectDAO.saveGeneralMessageAttachment(genAthList);
			}
			if(genMsgentity.getGenMessageId() > 0 && imageSavestatus == 1 ){
				genMsgentity.setAttachementStatus(1);
				genMsgentity = projectDAO.updateGeneralMessage(genMsgentity);
			}
			model = getGeneralMessageById(genMsgentity.getGenMessageId());
			List<GeneralMessageAttachmentEntity> genAthList = projectDAO.
					getGeneralMessageAttachmentByGenMessageId(genMsgentity.getGenMessageId());
			List<GeneralMessageAttachmentModel> genModelList = new ArrayList<GeneralMessageAttachmentModel>();
			
			for(GeneralMessageAttachmentEntity e: genAthList){
				GeneralMessageAttachmentModel m = createGeneralMessageAttachmentModel(e);
				genModelList.add(m);
			}
			model.setGeneralMessageAttachmentModels(genModelList);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("sendToGeneralMessage: "+e.getMessage());
		}
		return model;
	}

	@Override
	public GeneralMessageModel replyGeneralMessage(String token, MultipartFile[] multipartFiles,
			String contextPath,
			List<Integer> toDeptList, String subject,String description, String referenceNo){

		GeneralMessageModel model = null;
		int imageSavestatus = 0;
		try{
			model = new GeneralMessageModel();
			EmployeeEntity employee = userService.getEmployeeByToken(token);
			Timestamp timestamp = utilService.getCurrentDateTimeStamp();
			GeneralMessageEntity genMsgentity = new GeneralMessageEntity();
			genMsgentity.setCreatedOn(timestamp);
			genMsgentity.setDepartmentId(employee.getDepartmentId());
			genMsgentity.setDescription(description);
			genMsgentity.setEmployeeId(employee.getEmployeeId());
			genMsgentity.setReferenceNo(referenceNo);
			genMsgentity.setSubject(subject);
			genMsgentity.setUpdatedOn(timestamp);

			genMsgentity = projectDAO.saveGeneralMessage(genMsgentity);
			genMsgentity = projectDAO.getGeneralMessageById(genMsgentity.getGenMessageId());
			List<DepartmentGeneralMessageEntity> deptMsgEnyList = new ArrayList<DepartmentGeneralMessageEntity>();

			for(int deptId: toDeptList){
				DepartmentGeneralMessageEntity deptMsg = new DepartmentGeneralMessageEntity();
				deptMsg.setDepartmentId(deptId);
				deptMsg.setGenMessageId(genMsgentity.getGenMessageId());
				deptMsg.setViewStatus(0);
				deptMsg.setCreatedOn(timestamp);
				deptMsg.setUpdatedOn(timestamp);
				deptMsgEnyList.add(deptMsg);
			}

			deptMsgEnyList = projectDAO.saveDepartmentGeneralMessageList(deptMsgEnyList);
			//			System.out.println("Business,sendGeneralMessage, multipartFiles size: "+multipartFiles.length);
			// File saving to the sever location
			if(multipartFiles != null){
				List<GeneralMessageAttachmentEntity> genAthList = new ArrayList<GeneralMessageAttachmentEntity>();
				for(MultipartFile file: multipartFiles){
					GeneralMessageAttachmentEntity genAth = new GeneralMessageAttachmentEntity();
					String fileName = utilService.createFileName(file.getOriginalFilename());
					genAth.setFileName(fileName);
					genAth.setGenMessageId(genMsgentity.getGenMessageId());
					String fileLocation = Iconstants.GENERAL_MESSAGE_DOCUMENT+genMsgentity.getGenMessageId();
					imageSavestatus = utilService.saveFile(file, contextPath, fileLocation);
					if(imageSavestatus > 0){
						genAthList.add(genAth);
					}
				}
				genAthList = projectDAO.saveGeneralMessageAttachment(genAthList);
			}
			if(genMsgentity.getGenMessageId() > 0 && imageSavestatus == 1 ){
				genMsgentity.setAttachementStatus(1);
				genMsgentity = projectDAO.updateGeneralMessage(genMsgentity);
			}
			model = getGeneralMessageById(genMsgentity.getGenMessageId());
			
			List<GeneralMessageAttachmentEntity> genAthList = projectDAO.
					getGeneralMessageAttachmentByGenMessageId(genMsgentity.getGenMessageId());
			List<GeneralMessageAttachmentModel> genModelList = new ArrayList<GeneralMessageAttachmentModel>();
			
			for(GeneralMessageAttachmentEntity e: genAthList){
				GeneralMessageAttachmentModel m = createGeneralMessageAttachmentModel(e);
				genModelList.add(m);
			}
			model.setGeneralMessageAttachmentModels(genModelList);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("replyGeneralMessage: "+e.getMessage());
		}
		return model;
	}

	@Override
	public List<GeneralMessageFormatModel> getSentGeneralMessageListByDeptId(
			int departmentId) {
		List<GeneralMessageFormatModel> generalMessageFormatModels = null;
		try{
			generalMessageFormatModels = new ArrayList<GeneralMessageFormatModel>();
			List<GeneralMessageModel> generalMessageModels = new ArrayList<GeneralMessageModel>();
			List<GeneralMessageEntity> generalMessageEntities = projectDAO.getGeneralMessageListByDeptId(departmentId);
			//			System.out.println("Business,getSentGeneralMessageListByDeptId,generalMessageEntities.size: "+generalMessageEntities.size());

			for(GeneralMessageEntity entity: generalMessageEntities){
				GeneralMessageModel model = createGeneralMessageModel(entity);
				List<DepartmentGeneralMessageEntity> deptGenMsgList = projectDAO.getDepartmentGeneralMessageListByGenMsgId(entity.getGenMessageId());
				List<DepartmentGeneralMessageModel> modelList = new ArrayList<DepartmentGeneralMessageModel>();
				for(DepartmentGeneralMessageEntity dep: deptGenMsgList){
					DepartmentGeneralMessageModel depModel = createDepartmentGenMessageModel(dep);
					modelList.add(depModel);
				}
				model.setDepartmentGeneralMessageModels(modelList);
				generalMessageModels.add(model);
			}
			generalMessageFormatModels = getGeneralMessageFormatModel(generalMessageModels);
			Collections.reverse(generalMessageFormatModels);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getSentGeneralMessageListByDeptId: "+e.getMessage());
		}
		return generalMessageFormatModels;
	}

	private List<GeneralMessageFormatModel> getGeneralMessageFormatModel
	(List<GeneralMessageModel> genMessageList){
		//		System.out.println("Business,getGeneralMessageFormatModel, size: "+genMessageList.size());
		List<GeneralMessageFormatModel> genMsgList = null;
		try{
			genMsgList = new ArrayList<GeneralMessageFormatModel>();
			TreeMap<String, HashMap<String, List<GeneralMessageModel>>> sortedTreeSet = new TreeMap<>(Collections.reverseOrder());
			HashMap<String,HashMap<String, List<GeneralMessageModel>>> dateMap = 
					new HashMap<String, HashMap<String,List<GeneralMessageModel>>>();
			HashMap<String, List<GeneralMessageModel>> 
			referenceNoMap = new HashMap<String, List<GeneralMessageModel>>();
			genMessageList.sort((e1, e2) -> e1.getGenMessageId()- e2.getGenMessageId());
			Collections.reverse(genMsgList);
			// Groping based on the reference number.
			for(GeneralMessageModel cm: genMessageList){
				if(referenceNoMap.containsKey(cm.getReferenceNo())){
					List<GeneralMessageModel> list = referenceNoMap.get(cm.getReferenceNo());
					list.add(cm);
					referenceNoMap.put(cm.getReferenceNo(), list);
				}else{
					List<GeneralMessageModel> list = new ArrayList<GeneralMessageModel>();
					list.add(cm);
					referenceNoMap.put(cm.getReferenceNo(), list);
				}
			}//for(InterOfficeCommunicationModel cm: commList)
			//System.out.println("------referenceNoMap size: "+referenceNoMap.size());
			HashMap<String, List<GeneralMessageModel>>  datMap = new HashMap<String, List<GeneralMessageModel>>();
			for (Map.Entry<String, List<GeneralMessageModel>> map : referenceNoMap.entrySet()){
				List<GeneralMessageModel> list = map.getValue();
				// Sorting , acending order
				list.sort((e1, e2) -> e1.getGenMessageId()- e2.getGenMessageId());
				Collections.reverse(list);
				//				HashMap<String, List<GeneralMessageModel>>  datMap = new HashMap<String, List<GeneralMessageModel>>();
				datMap.put(map.getKey(), list);
				//				System.out.println("Bussiness,getGeneralMessageFormatModel,Key: "+map.getKey()+", size:"+list.size());
				//				System.out.println("Bussiness,getGeneralMessageFormatModel,datMap size: "+datMap.size());
				dateMap.put(list.get(0).getCreatedOn(), datMap);
				//				System.out.println("Bussiness,getGeneralMessageFormatModel,Date: "+list.get(0).getCreatedOn()
				//						+", Ref no: "+map.getKey());
			}

			sortedTreeSet.putAll(dateMap);
			sortedTreeSet.descendingKeySet();
			//			System.out.println("------dateMap size: "+dateMap.size()+",sortedTreeSet size: "+sortedTreeSet.size());
			for (Map.Entry<String, HashMap<String, List<GeneralMessageModel>>>
			entry : sortedTreeSet.entrySet()){
				GeneralMessageFormatModel msgFormat = new GeneralMessageFormatModel();
				msgFormat.setTitleDate(entry.getKey());
				//				System.out.println("------Title date: "+entry.getKey()+", Value size: "+entry.getValue().size());
				HashMap<String, List<GeneralMessageModel>> refMap = entry.getValue();
				List< GeneralMessageRefNoDetailsModel> comMsgRefList = 
						new ArrayList<GeneralMessageRefNoDetailsModel>();
				for (Map.Entry<String, List<GeneralMessageModel>> rMap :
					refMap.entrySet()) {
					GeneralMessageRefNoDetailsModel model = new GeneralMessageRefNoDetailsModel();
					model.setTitleRefNo(rMap.getKey());
					//					System.out.println("Title date: "+entry.getKey()+", TitleRefNo: "+rMap.getKey()+", Date : "+entry.getKey());
					model.setGeneralMessageModelList(rMap.getValue());
					comMsgRefList.add(model);
				}
				msgFormat.setGeneralMessageRefNoDetailsModel(comMsgRefList);
				//				msgFormat.setComMsgRefList(comMsgRefList);
				genMsgList.add(msgFormat);
				//System.out.println("entry.getKey: "+entry.getKey()+", msgFormat, TitleDate: "+msgFormat.getTitleDate());
			}//for (Map.Entry<String, HashMap<String, List<InterOfficeCommunicationModel>>>

			//			commList.sort((e1, e2) -> e1.getCreatedOn().compareTo(e2.getCreatedOn()));
			//messageList.sort((e1, e2) -> e1.getTitleDate().compareTo(e2.getTitleDate()));
			Collections.reverse(genMsgList);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getGeneralMessageFormatModel: "+e.getMessage());
		}
		return genMsgList;
	}


	private GeneralMessageModel createGeneralMessageModel(GeneralMessageEntity entity){
		GeneralMessageModel model = null;
		try{
			model = new GeneralMessageModel();
			//			model.setAnnexureFormat(entity.get);
			model.setAttachementStatus(entity.getAttachementStatus());
			model.setDepartmentId(entity.getDepartmentId());
			model.setDepartmentName(entity.getDepartment().getDepartmentName());
			model.setDescription(entity.getDescription());
			model.setEmpCode(entity.getEmployee().getEmpCode());
			model.setEmployeeId(entity.getEmployeeId());
			model.setEmployeeName(entity.getEmployee().getFirstName()+" "+entity.getEmployee().getLastName());
			model.setGenMessageId(entity.getGenMessageId());
			model.setReferenceNo(entity.getReferenceNo());
			model.setSubject(entity.getSubject());
			if(entity.getCreatedOn() != null){
				model.setCreatedOn(utilService.timeStampToString(entity.getCreatedOn()));
			}
			if(entity.getUpdatedOn() != null){
				model.setUpdatedOn(utilService.timeStampToString(entity.getUpdatedOn()));
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createGeneralMessageModel: "+e.getMessage());
		}
		return model;
	}

	private DepartmentGeneralMessageModel createDepartmentGenMessageModel
	(DepartmentGeneralMessageEntity entity){
		DepartmentGeneralMessageModel model = null;
		try{
			model = new DepartmentGeneralMessageModel();
			if(entity.getCreatedOn() != null){
				model.setCreatedOn(utilService.dateToString(entity.getCreatedOn()));
			}
			if(entity.getUpdatedOn() != null){
				model.setUpdatedOn(utilService.dateToString(entity.getUpdatedOn()));
			}
			model.setDepartmentId(entity.getDepartmentId());
			model.setDepartmentName(entity.getDepartment().getDepartmentName());
			model.setDeptGeneralMsgId(entity.getDeptGeneralMsgId());
			model.setGenMessageId(entity.getGenMessageId());
			model.setViewStatus(entity.getViewStatus());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createDepartmentGenMessageModel: "+e.getMessage());
		}
		return model;
	}

	/**
	 *  General message, Inbox / Incoming messages
	 */
	@Override
	public List<GeneralMessageFormatModel> getGeneralInboxByDeptId(
			int departmentId) {
		List<GeneralMessageFormatModel> generalMessageFormatModels = null;
		try{
			List<GeneralMessageModel> genMsgModelList = new ArrayList<GeneralMessageModel>();
			List<DepartmentGeneralMessageEntity> deptGenMsgList = projectDAO.getDepartmentGeneralMessageListByDeptId(departmentId);
			HashMap<Integer, List<DepartmentGeneralMessageEntity>> deptMsgMap = 
					new HashMap<Integer, List<DepartmentGeneralMessageEntity>>();
			for(DepartmentGeneralMessageEntity entity: deptGenMsgList){
				if(deptMsgMap.containsKey(entity.getGenMessageId())){
					List<DepartmentGeneralMessageEntity> list = deptMsgMap.get(entity.getGenMessageId());
					list.add(entity);
					deptMsgMap.put(entity.getGenMessageId(), list);
				}else{
					List<DepartmentGeneralMessageEntity> newList = new ArrayList<DepartmentGeneralMessageEntity>();
					newList.add(entity);
					deptMsgMap.put(entity.getGenMessageId(),newList);
				}
			}
			//			System.out.println("Business,getGeneralInboxByDeptId, deptMsgMap, size: "+deptMsgMap.size());
			for (Map.Entry<Integer, List<DepartmentGeneralMessageEntity>> entry : deptMsgMap.entrySet())  {
				//				System.out.println("Business,getGeneralInboxByDeptId, MessageId: "+entry.getKey());
				GeneralMessageEntity genMsg = projectDAO.getGeneralMessageById(entry.getKey());
				List<GeneralMessageAttachmentEntity> athEnityList = projectDAO.
						getGeneralMessageAttachmentByGenMessageId(entry.getKey());
				List<GeneralMessageAttachmentModel> athModelList = new ArrayList<GeneralMessageAttachmentModel>();
				if(athEnityList != null){
					for(GeneralMessageAttachmentEntity athE: athEnityList){
						GeneralMessageAttachmentModel m = createGeneralMessageAttachmentModel(athE);
						athModelList.add(m);
					}
				}
				GeneralMessageModel genMsgModel = createGeneralMessageModel(genMsg);
				List<DepartmentGeneralMessageEntity> depGenList = entry.getValue();
				List<DepartmentGeneralMessageModel> modList = new ArrayList<DepartmentGeneralMessageModel>();
				for(DepartmentGeneralMessageEntity e: depGenList){
					DepartmentGeneralMessageModel dModel = createDepartmentGenMessageModel(e);
					modList.add(dModel);
				}
				genMsgModel.setGeneralMessageAttachmentModels(athModelList);
				genMsgModel.setDepartmentGeneralMessageModels(modList);
				genMsgModelList.add(genMsgModel);
			} 

			generalMessageFormatModels = getGeneralMessageFormatModel(genMsgModelList);
			Collections.reverse(generalMessageFormatModels);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getGeneralInboxByDeptId: "+e.getMessage());
		}
		return generalMessageFormatModels;
	}

	@Override
	public GeneralMessageModel getGeneralMessageById(int genMessageId) {
		GeneralMessageModel model = null;
		try{
			GeneralMessageEntity entity = projectDAO.getGeneralMessageById(genMessageId);
			List<DepartmentGeneralMessageModel> depMsgModelList = new ArrayList<DepartmentGeneralMessageModel>();
			List<GeneralMessageAttachmentModel> attachModel = new ArrayList<GeneralMessageAttachmentModel>();
			List<DepartmentGeneralMessageEntity> depMsgList = 
					projectDAO.getDepartmentGeneralMessageListByGenMsgId(genMessageId);
			List<GeneralMessageAttachmentEntity> attchementEntity = projectDAO.getGeneralMessageAttachmentByGenMessageId(genMessageId);

			for(DepartmentGeneralMessageEntity e: depMsgList){
				DepartmentGeneralMessageModel m = createDepartmentGenMessageModel(e);
				depMsgModelList.add(m);
			}
			for(GeneralMessageAttachmentEntity e: attchementEntity){
				GeneralMessageAttachmentModel mod = createGeneralMessageAttachmentModel(e);
				attachModel.add(mod);
			}
			model = createGeneralMessageModel(entity);
			model.setDepartmentGeneralMessageModels(depMsgModelList);
			model.setGeneralMessageAttachmentModels(attachModel);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getGeneralMessageById: "+e.getMessage());
		}
		return model;
	}

	/*
	 * Only for UI purpose
	 * @see com.yaz.alind.service.ProjectService#getGeneralMessageListById(int)
	 */
	@Override
	public List<GeneralMessageModel> getGeneralMessageListById(int genMessageId){
		List<GeneralMessageModel> list = null;
		try{
			list = new ArrayList<GeneralMessageModel>();
			GeneralMessageModel genMsgModel = getGeneralMessageById(genMessageId);
			list.add(genMsgModel);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getGeneralMessageListById: "+e.getMessage());
		}
		return list;
	}


	private GeneralMessageAttachmentModel createGeneralMessageAttachmentModel
	(GeneralMessageAttachmentEntity entity){
		GeneralMessageAttachmentModel model = null;
		try{
			model = new GeneralMessageAttachmentModel();
			String fileLocation = Iconstants.GENERAL_MESSAGE_DOCUMENT+entity.getGenMessageId();
			model.setFileLocation(fileLocation+"/"+entity.getFileName());
			model.setFileName(entity.getFileName());
			model.setGenMessageId(entity.getGenMessageId());
			model.setOrginalFileName(entity.getOrginalFileName());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("createGeneralMessageAttachmentModel: "+e.getMessage());
		}
		return model;
	}


}
