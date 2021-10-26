package com.yaz.alind.contoller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.yaz.alind.entity.DocumentHistoryEntity;
import com.yaz.alind.entity.DocumentUsersEntity;
import com.yaz.alind.entity.EmployeeEntity;
import com.yaz.alind.entity.ProjectDocumentEntity;
import com.yaz.alind.entity.ProjectInfoEntity;
import com.yaz.alind.model.ui.EmployeeModel;
import com.yaz.alind.model.ui.EmployeeTaskAllocationModel;
import com.yaz.alind.model.ui.SubTaskModel;
import com.yaz.alind.model.ui.WorkDetailsModel;
import com.yaz.alind.model.ui.WorkDocumentModel;
import com.yaz.alind.service.ProjectService;
import com.yaz.alind.service.UserService;
import com.yaz.alind.service.UtilService;
import com.yaz.security.Iconstants;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProjectController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	UserService userService;
	@Autowired
	UtilService utilService;
	@Autowired
	ProjectService projectService;
	@Autowired
	private ServletContext context;

	@RequestMapping(value="/project/saveOrUpdateWorkStatus", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> saveOrUpdateWorkStatus(@RequestHeader("token") String token,
			@RequestBody ProjectInfoEntity projectInfo) throws Exception {
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("saveOrUpdateProject,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				ProjectInfoEntity project= projectService.saveOrUpdateProject(projectInfo);
				resultMap.put("project", project);
				resultMap.put("status", "success");
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("saveOrUpdateWorkStatus, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}


	
	@RequestMapping(value="/project/getAllProject", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getAllProject(@RequestHeader("token") String token
			,@RequestParam String departmentId,@RequestParam String projectId ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getAllProject,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<ProjectInfoEntity> projectInfos= projectService.getAllProject(Integer.parseInt(departmentId),
						Integer.parseInt(projectId),token);
				resultMap.put("projectInfos", projectInfos);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllProject, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/saveOrUpdateDocument", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>>  saveOrUpdateDocument(@RequestHeader("token") String token
			, @RequestBody ProjectDocumentEntity document ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("saveOrUpdateDocument,token: "+token+"file, name: ");
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				ProjectDocumentEntity doc= projectService.saveOrUpdateDocument(document);
				resultMap.put("document", doc);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateDocument, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	/**
	 *  Next

	 */

	@RequestMapping(value="/project/getDocumentById", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getDocumentById(@RequestHeader("token") String token
			,@RequestParam String documentId ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getDocumentById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				ProjectDocumentEntity doc= projectService.getDocumentById(Integer.parseInt(documentId));
				resultMap.put("document", doc);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDocumentById, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/getAllDocumentHistories", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getAllDocumentHistories(@RequestHeader("token") String token
			,@RequestParam String documentId, @RequestParam String departmentId ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getAllDocumentHistories,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<DocumentHistoryEntity> documentHistories= projectService.getAllDocumentHistories(Integer.parseInt(documentId),
						Integer.parseInt(departmentId));
				resultMap.put("documentHistories", documentHistories);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDocumentHistories, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/saveDocumentHistory", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>>  saveDocumentHistory(@RequestHeader("token") String token
			,@RequestBody DocumentHistoryEntity documentHistory ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("saveDocumentHistory,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				DocumentHistoryEntity docHistory= projectService.saveDocumentHistory(documentHistory);
				resultMap.put("documentHistory", docHistory);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveDocumentHistory, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}


	@RequestMapping(value="/project/getAllDocumentUsers", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getAllDocumentUsers(@RequestHeader("token") String token
			,@RequestParam String departmentId,@RequestParam String documentId, @RequestParam String employeeId ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getAllDocumentUsers,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<DocumentUsersEntity> documentUsers= projectService.getAllDocumentUsers(
						Integer.parseInt(departmentId),Integer.parseInt(documentId),Integer.parseInt(employeeId));
				resultMap.put("documentUsers", documentUsers);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDocumentUsers, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	/**
	 *  Add / update resources
	 * @param token
	 * @param documentUsers
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/project/saveOrUpdateDocumentUsers", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>>  saveOrUpdateDocumentUsers(@RequestHeader("token") String token
			,@RequestBody DocumentUsersEntity documentUsers ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("saveOrUpdateDocumentUsers,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				DocumentUsersEntity docUser= projectService.saveOrUpdateDocumentUsers(documentUsers);
				resultMap.put("documentUsers", docUser);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateDocumentUsers, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/getDocumentUsersById", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getDocumentUsersById(@RequestHeader("token") String token
			,@RequestParam String documentUserId ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getDocumentUsersById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				DocumentUsersEntity docUser= projectService.getDocumentUsersById(Integer.parseInt(documentUserId));
				resultMap.put("documentUsers", docUser);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDocumentUsersById, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value = "/project/getProjectInfoById", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getProjectInfoById(@RequestHeader("token") String token,@RequestParam String projectId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getProjectInfoById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				ProjectInfoEntity projectInfo = projectService.getProjectInfoById(Integer.parseInt(projectId));
				resultMap.put("projectInfo", projectInfo);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getProjectInfoById, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	///project/uploadProjectDocument
	@RequestMapping(value = "/project/uploadProjectDocument",
			method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<Map<String,Object>>  uploadProjectDocument(@RequestHeader("token") String token,
			@RequestParam(value = "file", required = false)MultipartFile file,
			@RequestParam(value = "userId", required = false)Integer userId,
			@RequestParam(value = "projectId", required = false)Integer projectId,
			@RequestParam(value = "documentName", required = false) String documentName,
			@RequestParam(value = "documentTypeId", required = false)Integer documentTypeId,
			@RequestParam(value = "projectDocumentId", required = false) Integer projectDocumentId,
			@RequestParam(value = "description", required = false) String description) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("uploadEmployeeDocuments,token: "+token+", projectDocumentId: "+projectDocumentId);
			System.out.println("uploadEmployeeDocuments,token: "+token+", file size: "+file.getSize());
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				String contextPath = context.getRealPath(""); 
				ProjectDocumentEntity projectDocument = projectService.uploadProjectDocument(file,userId,
						documentTypeId, documentName, contextPath, projectId,projectDocumentId,description);
				resultMap.put("projectDocument", projectDocument);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("uploadProjectDocument, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/getAllDocumentByProjectId", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getAllDocumentByProjectId(@RequestHeader("token") String token
			,@RequestParam String projectId,@RequestParam String documentTypeId ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getAllDocumentByProjectId,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				String realPath = context.getRealPath(""); 
				List<ProjectDocumentEntity> projectDocuments= projectService.getAllDocumentByProjectId(
						Integer.parseInt(projectId),Integer.parseInt(documentTypeId),realPath,token);
				resultMap.put("projectDocuments", projectDocuments);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDocumentByProjectId, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}


	@RequestMapping(value="/project/downLoadProjectDocument", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downLoadProjectDocument(@RequestHeader("token") String token,
			@RequestParam String projectDocumentId,@RequestParam String employeeId 	){

		ResponseEntity<PdfContentByte> response = null;
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		PdfContentByte cb = null;
		InputStreamResource inputStreamResource = null;
		HttpHeaders responseHeaders = null;
		float x=0, y=0;
		try{
			System.out.println("downLoadProjectDocument,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				ProjectDocumentEntity projectDocument = projectService.getDocumentById(Integer.parseInt(projectDocumentId));
				EmployeeEntity employee = userService.getEmployeeById(Integer.parseInt(employeeId));
				ByteArrayOutputStream archivo = new ByteArrayOutputStream();

				String contextPath = context.getRealPath(""); 
				String fileLocation = Iconstants.PROJECT_DOCUMENT_LOCATION+projectDocument.getProjectId();
				String[] arrOfStr = contextPath.split(Iconstants.BUILD_NAME, 2); 
				String path = arrOfStr[0]+fileLocation;
				String destination = path+"/"+ projectDocument.getFileName();
				System.out.println("downLoadProjectDocument,destination: "+destination);

				Font alindFont = new Font(Font.FontFamily.HELVETICA, 32, Font.BOLD, new GrayColor(0.85f));
				Font nameFont = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD, new GrayColor(0.85f));
				PdfReader.unethicalreading = true;
				// Create output PDF
				Document document = new Document(PageSize.A4);
				document.open();
				//				PdfReader reader = new PdfReader("C:/Users/dell/Desktop/20200218215702.pdf");
				PdfReader reader = new PdfReader(destination);
				PdfStamper stamper = new PdfStamper(reader, archivo);

				// Copy first page of existing PDF into output PDF
				Rectangle pagesize;


				for(int i=1; i<= reader.getNumberOfPages(); i++){
					document.newPage();

					// get page size and position
					pagesize = reader.getPageSizeWithRotation(i);
					x = (pagesize.getLeft() + pagesize.getRight()) / 2;
					y = (pagesize.getTop() + pagesize.getBottom()) / 2;
					cb = stamper.getOverContent(i);
					cb.saveState();

					// set transparency
					PdfGState state = new PdfGState();
					state.setFillOpacity(0.2f);
					cb.setGState(state);

					ColumnText.showTextAligned(cb,
							Element.ALIGN_CENTER, new Phrase("CONTROLLED COPY", alindFont),
							297.5f, 621, i % 2 == 1 ? 0 : 0);
					ColumnText.showTextAligned(cb,
							Element.ALIGN_CENTER, new Phrase("Issued to - "+" GSS - "+" AS - "+employee.getFirstName()+", "+employee.getEmpCode(), nameFont),
							297.5f, 591, i % 2 == 1 ? 0 : 0);
					ColumnText.showTextAligned(cb,
							Element.ALIGN_CENTER, new Phrase("Issued by - "+" AKS - "+" DD - "+utilService.getCurrentDateTime(), nameFont),
							297.5f, 561, i % 2 == 1 ? 0 : 0);
					cb.restoreState();
				}

				document.close();
				stamper.close();
				reader.close();

				// asume that it was a PDF file
				responseHeaders = new HttpHeaders();
				InputStream pdfStream = new ByteArrayInputStream(archivo.toByteArray()); 
				responseHeaders.setContentLength(archivo.size());
				responseHeaders.setContentType(MediaType.valueOf("application/pdf"));
				inputStreamResource = new InputStreamResource(pdfStream);
				// just in case to support browsers
				responseHeaders.put("Content-Disposition", Collections.singletonList("attachment; filename="+projectDocument.getOriginalFileName()));
			}else{
				return  new ResponseEntity<InputStreamResource> (inputStreamResource,
						responseHeaders,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("downLoadProjectDocument, "+e.getMessage());
			return  new ResponseEntity<InputStreamResource> (inputStreamResource,
					responseHeaders,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<InputStreamResource> (inputStreamResource,
				responseHeaders,
				HttpStatus.OK);
	}

	@RequestMapping(value="/project/getDocumentUsersList", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getDocumentUsersList(@RequestHeader("token") String token
			,@RequestParam String projectDocumentId ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getAllDocumentUsersById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<DocumentUsersEntity> documentUsers= projectService.getDocumentUsersList(
						Integer.parseInt(projectDocumentId));
				resultMap.put("documentUsers", documentUsers);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDocumentUsersList, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}


	@RequestMapping(value="/project/getProjectDocumentsById", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getProjectDocumentsById(@RequestHeader("token") String token
			,@RequestParam String projectId,@RequestParam String documentTypeId ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getProjectDocumentsById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<ProjectDocumentEntity> projectDocuments= projectService.getProjectDocumentsById(
						Integer.parseInt(projectId),Integer.parseInt(documentTypeId));
				resultMap.put("projectDocuments", projectDocuments);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getProjectDocumentsById, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}
	
	@RequestMapping(value="/project/getWorkDocument/{workDocumentId}", method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource>  getProjectDocumentsById(@RequestHeader("token") String token
			,@PathVariable("workDocumentId") int workDocumentId,HttpServletResponse response ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		InputStreamResource input =  null;
		byte[] content = null;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getWorkDocument,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				String contextPath = context.getRealPath(""); 
				ByteArrayInputStream bis = projectService.getWorkDocument(workDocumentId, token, contextPath);
				WorkDocumentModel model = projectService.getWorkDocumentById(workDocumentId, contextPath);
				String newFileName = utilService.createDownLoadFileName();
				content = IOUtils.toByteArray(bis); 
				response.setContentType("application/pdf");
				String fileName = model.getSubTaskName()+"_"+newFileName+".pdf";
				response.setHeader("Content-disposition","attachment; filename="+newFileName+".pdf");
				System.out.println("getWorkDocument,fileName: "+fileName);
//				response.setHeader("Content-disposition","attachment; filename="+model.getSubTaskName()
//						+"_"+newFileName+".pdf");
				input = new InputStreamResource(bis);
				resultMap.put("status", "success");
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("getWorkDocument, "+e.getMessage());
			return  new ResponseEntity<ByteArrayResource>(new ByteArrayResource(content),HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<ByteArrayResource>(new ByteArrayResource(content),HttpStatus.OK);
	}

	@RequestMapping(value="/project/saveWorkDetails", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>>  saveWorkDetails(@RequestHeader("token") String token
			,@RequestBody WorkDetailsModel workDetailsModel ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("saveWorkDetails,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				WorkDetailsModel model= projectService.saveWorkDetails(workDetailsModel);
				if(model != null){
					resultMap.put("model", model);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("saveWorkDetails, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/updateWorkDetails", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>>  updateWorkDetails(@RequestHeader("token") String token
			,@RequestBody WorkDetailsModel workDetailsModel ) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("updateWorkDetails,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				WorkDetailsModel model= projectService.updateWorkDetails(workDetailsModel);
				if(model != null){
					resultMap.put("model", model);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("updateWorkDetails, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/getWorkDetailsById", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getWorkDetailsById(@RequestHeader("token") String token
			,@RequestParam int workDetailsId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getWorkDetailsById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				WorkDetailsModel model = projectService.getWorkDetailsById(workDetailsId);
				if(model != null){
					resultMap.put("model", model);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("getWorkDetailsById, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/getWorkDetailsBySearch", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getWorkDetailsBySearch(@RequestHeader("token") String token
			,@RequestParam String searchKeyWord,
			@RequestParam int workTypeId,@RequestParam String startDate,@RequestParam String endDate) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getWorkDetailsBySearch,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<WorkDetailsModel> models = projectService.getWorkDetailsBySearch(searchKeyWord, workTypeId, startDate, endDate);
				if(models != null){
					resultMap.put("models", models);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("getWorkDetailsBySearch, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/deleteWorkDetailsById", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  deleteWorkDetailsById(@RequestHeader("token") String token
			,@RequestParam int workDetailsId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("deleteWorkDetailsById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				int value = projectService.deleteWorkDetailsById(workDetailsId);
				if(value == 1){
					resultMap.put("value", value);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("deleteWorkDetailsById, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/getWorkDetailsByDeptId", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getWorkDetailsByDeptId(@RequestHeader("token") String token
			,@RequestParam int departmentId, int status) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getWorkDetailsByDeptId,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<WorkDetailsModel> models = projectService.getWorkDetailsByDeptId(departmentId,status);
				if(models != null){
					resultMap.put("models", models);
				}else{
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkDetailsByDeptId, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/saveSubTask", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> saveSubTask(@RequestHeader("token") String token,
			@RequestBody SubTaskModel subTaskModel) throws Exception {
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("saveOrUpdateProject,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				SubTaskModel model= projectService.saveSubTask(subTaskModel);
				resultMap.put("model", model);
				resultMap.put("status", "success");
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("saveSubTask, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/updateSubTask", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> updateSubTask(@RequestHeader("token") String token,
			@RequestBody SubTaskModel subTaskModel) throws Exception {
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("saveOrUpdateProject,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				SubTaskModel model= projectService.updateSubTask(subTaskModel);
				resultMap.put("model", model);
				resultMap.put("status", "success");
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("updateSubTask, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/getSubTaskById", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getSubTaskById(@RequestHeader("token") String token
			,@RequestParam int subTaskId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getSubTaskById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				SubTaskModel model = projectService.getSubTaskById(subTaskId);
				if(model != null){
					resultMap.put("model", model);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("getSubTaskById, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/getSubTaskByWorkId", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getSubTaskById(@RequestHeader("token") String token
			,@RequestParam int workDetailsId, @RequestParam int status) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getSubTaskById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<SubTaskModel> models = projectService.getSubTaskByWorkId(workDetailsId,status);
				if(models != null){
					resultMap.put("models", models);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("getSubTaskByWorkId, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/deleteSubTask", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  deleteSubTask(@RequestHeader("token") String token
			,@RequestParam int subTaskId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getSubTaskById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				int value = projectService.deleteSubTask(subTaskId);
				if(value == 1){
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("deleteSubTask, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value = "/project/saveWorkDocument",
			method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<Map<String,Object>>  saveWorkDocument(@RequestHeader("token") String token,
			@RequestParam(value = "file", required = false)MultipartFile file,
			@RequestParam(value = "documentTypeId", required = false)int documentTypeId,
			@RequestParam(value = "workDetailsId", required = false)int workDetailsId,
			@RequestParam(value = "subTaskId", required = false) int subTaskId,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "departmentId", required = false) int departmentId,
			@RequestParam(value = "documentName", required = false) String documentName
			)throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("uploadEmployeeDocuments,token: "+token+", projectDocumentId: "+projectDocumentId);
			System.out.println("saveWorkDocument,token: "+token+", file size: "+file.getSize());
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				String contextPath = context.getRealPath(""); 
				WorkDocumentModel workDocumentModel = projectService.saveWorkDocument(file,token,
						documentTypeId,workDetailsId,subTaskId,description,departmentId,documentName, contextPath);
				if(workDocumentModel != null){
					resultMap.put("workDocumentModel", workDocumentModel);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveWorkDocument, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/getWorkDocumentBySubTaskId/{subTaskId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getWorkDocumentBySubTaskId(@RequestHeader("token") String token
			,@PathVariable("subTaskId") int subTaskId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getWorkDocumentBySubTaskId,token: "+token);
			tokenStatus = utilService.evaluateToken(token);

			if(tokenStatus){
				String contextPath = context.getRealPath("");
				List<WorkDocumentModel> models= projectService.getWorkDocumentBySubTaskId(subTaskId,contextPath);
				if(models != null){
					resultMap.put("status", "success");
					resultMap.put("models", models);
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("getWorkDocumentBySubTaskId, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/deleteWorkDocumentById/{workDocumentId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  deleteWorkDocumentById(@RequestHeader("token") String token
			,@PathVariable("workDocumentId") int workDocumentId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("deleteWorkDocumentById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);

			if(tokenStatus){
				int value = projectService.deleteWorkDocumentModelById(workDocumentId);
				if(value == 1){
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("getWorkDocumentBySubTaskId, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/verifyDocument/{workDocumentId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  verifyDocument(@RequestHeader("token") String token
			,@PathVariable("workDocumentId") int workDocumentId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("verifyDocument,token: "+token);
			tokenStatus = utilService.evaluateToken(token);

			if(tokenStatus){
				String contextPath = context.getRealPath(""); 
				WorkDocumentModel model = projectService.verifyDocument(workDocumentId,contextPath);
				if(model != null){
					resultMap.put("model", model);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("verifyDocument, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}


	@RequestMapping(value="/project/approveDocument/{workDocumentId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  approveDocument(@RequestHeader("token") String token
			,@PathVariable("workDocumentId") int workDocumentId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("approveDocument,token: "+token);
			tokenStatus = utilService.evaluateToken(token);

			if(tokenStatus){
				String contextPath = context.getRealPath(""); 
				WorkDocumentModel model = projectService.approveDocument(workDocumentId,contextPath);
				if(model != null){
					resultMap.put("model", model);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("approveDocument, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/saveEmployeeTaskAllocation", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> saveEmployeeTaskAllocation(@RequestHeader("token") String token,
			@RequestBody Object object) throws Exception {
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("saveEmployeeTaskAllocation,token: "+token+",object: "+object);
			LinkedHashMap<String, String> lhm = (LinkedHashMap<String, String>) object;

			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<EmployeeTaskAllocationModel> models= projectService.saveEmployeeTaskAllocation(object);
				if(models != null){
					resultMap.put("models", models);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("saveEmployeeTaskAllocation, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/updateEmployeeTaskAllocation", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> updateEmployeeTaskAllocation(@RequestHeader("token") String token,
			@RequestBody EmployeeTaskAllocationModel empAllocationModel) throws Exception {
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("saveEmployeeTaskAllocation,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				EmployeeTaskAllocationModel model= projectService.updateEmployeeTaskAllocation(empAllocationModel);
				resultMap.put("model", model);
				resultMap.put("status", "success");
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("updateEmployeeTaskAllocation, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/getEmployeeTaskAllocationById/{empTaskAllocationId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getEmployeeTaskAllocationById(@RequestHeader("token") String token
			,@PathVariable("empTaskAllocationId") int empTaskAllocationId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("approveDocument,token: "+token);
			tokenStatus = utilService.evaluateToken(token);

			if(tokenStatus){
				EmployeeTaskAllocationModel model = projectService.getEmployeeTaskAllocationById(empTaskAllocationId);
				if(model != null){
					resultMap.put("model", model);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("getEmployeeTaskAllocationById, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}


	@RequestMapping(value="/project/getAllEmployeeTaskAllocationBySubTaskId/{subTaskId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getAllEmployeeTaskAllocationBySubTaskId(@RequestHeader("token") String token
			,@PathVariable("subTaskId") int subTaskId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("approveDocument,token: "+token);
			tokenStatus = utilService.evaluateToken(token);

			if(tokenStatus){
				List<EmployeeTaskAllocationModel> models = projectService.getAllEmployeeTaskAllocationBySubTaskId
						(subTaskId);
				if(models != null){
					resultMap.put("models", models);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("getAllEmployeeTaskAllocationBySubTaskId, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/getAllEmployeeTaskAllocationByWorkDetailsId/{workDetailsId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getAllEmployeeTaskAllocationByWorkDetailsId(@RequestHeader("token") String token
			,@PathVariable("workDetailsId") int workDetailsId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getAllEmployeeTaskAllocationByWorkDetailsId,token: "+token);
			tokenStatus = utilService.evaluateToken(token);

			if(tokenStatus){
				List<EmployeeTaskAllocationModel> models = projectService.getAllEmployeeTaskAllocationByWorkDetailsId
						(workDetailsId);
				if(models != null){
					resultMap.put("models", models);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("getAllEmployeeTaskAllocationByWorkDetailsId, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/getEmployeeListForTaskAllocationByDeptId/{departmentId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getEmployeeListForTaskAllocationByDeptId(@RequestHeader("token") String token
			,@PathVariable("departmentId") int departmentId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("getAllEmployeeTaskAllocationByWorkDetailsId,token: "+token);
			tokenStatus = utilService.evaluateToken(token);

			if(tokenStatus){
				List<EmployeeModel> models = projectService.getEmployeeListForTaskAllocationByDeptId
						(departmentId);
				if(models != null){
					resultMap.put("models", models);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("getEmployeeListForTaskAllocationByDeptId, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/project/deleteEmployeeFromSubTask/{empTaskAllocationId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  deleteEmployeeFromSubTask(@RequestHeader("token") String token
			,@PathVariable("empTaskAllocationId") int empTaskAllocationId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("deleteEmployeeFromSubTask,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				int value = projectService.deleteEmployeeFromSubTask(empTaskAllocationId);
				if(value == 1){
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("deleteEmployeeFromSubTask, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}



}
