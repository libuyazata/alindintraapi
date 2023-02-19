package com.yaz.alind.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaz.alind.dao.ProjectDAO;
import com.yaz.alind.dao.UserDAO;
import com.yaz.alind.entity.AdminDashBoardFactory;
import com.yaz.alind.entity.AdminDashBoardModel;
import com.yaz.alind.entity.AuthorizationEntity;
import com.yaz.alind.entity.DepartmentEntity;
import com.yaz.alind.entity.EmployeeEntity;
import com.yaz.alind.entity.ProjectDocumentEntity;
import com.yaz.alind.entity.ProjectInfoEntity;
import com.yaz.alind.entity.SubTaskEntity;
import com.yaz.alind.entity.TokenEntity;
import com.yaz.alind.entity.WorkDetailsEntity;
import com.yaz.alind.entity.WorkDocumentEntity;

@Service
public class DashBoardServiceImpl implements DashBoardService {

	private static final Logger logger = LoggerFactory.getLogger(DashBoardServiceImpl.class);


	@Autowired
	ProjectService projectService;
	@Autowired
	UtilService utilService;
	@Autowired
	UserService userService;
	@Autowired
	UserDAO userDAO;
	@Autowired
	ProjectDAO projectDAO;
	@Autowired
	AdminDashBoardFactory adminDashBoardFactory;


	@Override
	public AdminDashBoardModel getAdminDashBoardModel(String token) {
		AdminDashBoardModel adminDashBoardModel = null;
		List<EmployeeEntity> employees = null;
		List<DepartmentEntity> departments = null;
		List<ProjectInfoEntity> projectInfos = null;
		Map<String,Integer> projectMap = new HashMap<String, Integer>();
		Map<String,Integer> empBasedOnDept = new HashMap<String, Integer>();
		Map<String,Integer> docAgainstDeptMap= new HashMap<String, Integer>();
		try{
			adminDashBoardModel = adminDashBoardFactory.createAdminDashBoardModel();
			TokenEntity tokenModel = userDAO.getTokenModelByToken(token);
			EmployeeEntity employee = userDAO.getEmployeeById(tokenModel.getUserId());
//			System.out.println("DashBoardServiceImpl,getAdminDashBoardModel, role id: "+employee.getUserRoleId());
			// Admin
			if(employee.getUserRoleId() == 1){
				employees=userDAO.getAllEmployeesByDept(-1);
				departments = userDAO.getAllActiveDepartments();
				for(int i=0;i<departments.size();i++){
					List<EmployeeEntity> emp = userDAO.getAllEmployeesByDept(departments.get(i).getDepartmentId());
					empBasedOnDept.put(departments.get(i).getDepartmentName(), emp.size());
					docAgainstDeptMap.put(departments.get(i).getDepartmentName(),
							projectDAO.getAllPjtDocByDeptId(departments.get(i).getDepartmentId()).size());
				}
				adminDashBoardModel.setEmpCountBasedOnDept(empBasedOnDept);
				adminDashBoardModel.setNoOfEmpoyees(employees.size());
				adminDashBoardModel.setNoOfDepartments(departments.size());
				projectInfos = projectDAO.getAllProject(-1);
				//				for(int j=0;j<projectInfos.size();j++){
				////					System.out.println("Dept: "+projectInfos.get(j).getDepartment().getDepartmentName()+
				////							",No of Doc:  "+projectDAO.getAllPjtDocByDeptId(projectInfos.get(j).getDepartmentId()).size());
				//					docAgainstDeptMap.put(projectInfos.get(j).getDepartment().getDepartmentName(),
				//							projectDAO.getAllPjtDocByDeptId(projectInfos.get(j).getDepartmentId()).size());
				//				}
				projectMap.put("totalProjects", projectInfos.size());

				int pjtNotStarted = 0;
				int pjtStarted = 0;
				int pjtClosed = 0;
				for(int i=0;i<projectInfos.size();i++){
					if(projectInfos.get(i).getProjectStatusId() == 1){
						pjtNotStarted = pjtNotStarted + 1;
					}else if(projectInfos.get(i).getProjectStatusId() == 2){
						pjtStarted = pjtStarted + 1;
					}else if(projectInfos.get(i).getProjectStatusId() == 5){
						pjtClosed = pjtClosed + 1;
					}
				}
				projectMap.put("pjtNotStarted",pjtNotStarted);
				projectMap.put("pjtStarted",pjtStarted);
				projectMap.put("pjtStarted",pjtStarted);

				//				List<ProjectDocumentEntity> projectDocuments = projectDAO.getAllDocumentByProjectId(-1,-1);
				//				projectMap.put("totalPjtDoc",projectDocuments.size());

				Map<String,Integer> workDetails = getWorkDetails(token);
				adminDashBoardModel.setWorkDetails(workDetails);

				//				adminDashBoardModel.setProjectDetails(projectMap);
				adminDashBoardModel.setDocAgaistDept(docAgainstDeptMap);
				//Authorization 
				AuthorizationEntity authEntity = userDAO.getAuthorizationByUserRole(1);
				adminDashBoardModel.setAuthorizationEntity(authEntity);
			}
			// HOD or Department coordinator
			if(employee.getUserRoleId() == 2 || employee.getUserRoleId() == 4){
//				System.out.println("DashBoardServiceImpl,getAdminDashBoardModel, role id: "+employee.getUserRoleId());
				employees = userDAO.getAllEmployeesByDept(employee.getDepartmentId());
				adminDashBoardModel.setNoOfEmpoyees(employees.size());

				Map<String,Integer> workDetails = getWorkDetails(token);
				adminDashBoardModel.setWorkDetails(workDetails);

				//				adminDashBoardModel.setProjectDetails(projectMap);
				adminDashBoardModel.setDocAgaistDept(docAgainstDeptMap);
				//Authorization 
				AuthorizationEntity authEntity = userDAO.getAuthorizationByUserRole(2);
				adminDashBoardModel.setAuthorizationEntity(authEntity);
			}
			//			// Employee
			//			if(employee.getUserRoleId() == 3){
			//				
			//				List<DocumentUsers> documentUsers = projectDAO.getAllDocumentUserById(employee.getEmployeeId());
			//				for(int i=0;i<documentUsers.size();i++){
			//					documentUsers.get(i).getEmployee().setPassword(null);
			//					documentUsers.get(i).getProjectDocument().setEmployee(null);
			//					ProjectInfo projectInfo = projectDAO.getProjectInfoById(
			//							documentUsers.get(i).getProjectDocument().getProjectId());
			//					documentUsers.get(i).setProjectName(projectInfo.getProjectName());
			//				}
			//				adminDashBoardModel.setDocumentUsers(documentUsers);
			//			}


		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAdminDashBoardModel, "+e.getMessage());
		}
		return adminDashBoardModel;
	}

	@Override
	public AdminDashBoardModel getDashBoardByDepartId(int deptId){
		AdminDashBoardModel adminDashBoardModel = null;
		try{

			adminDashBoardModel = new AdminDashBoardModel();
			List<EmployeeEntity> employees =  userDAO.getEmployeeListByDept(deptId,1);
			List<DepartmentEntity> deptList = userDAO.getAllActiveDepartments();
			adminDashBoardModel.setNoOfEmpoyees(employees.size());
            adminDashBoardModel.setNoOfDepartments(deptList.size());
			Map<String,Integer> workDetails = getWorkDetailsByDeptId(deptId);

			adminDashBoardModel.setWorkDetails(workDetails);

			//			adminDashBoardModel.setDocAgaistDept(docAgainstDeptMap);
			//Authorization 
			AuthorizationEntity authEntity = userDAO.getAuthorizationByUserRole(2);
			adminDashBoardModel.setAuthorizationEntity(authEntity);

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDashBoardByDepartId, "+e.getMessage());
		}
		return adminDashBoardModel;
	}

	private Map<String,Integer> getWorkDetailsByDeptId(int deptId){
		Map<String,Integer> workDetailsMap = null;
		try{
			workDetailsMap = new HashMap<String, Integer>();
			List<WorkDetailsEntity> workDetailsEntities = projectDAO.getWorkDetailsEntitiesByDeptId(deptId,1 );
			List<SubTaskEntity> subTaskList = projectDAO.getSubTaskEntitiesByWorkId(deptId, 1);
			List<WorkDocumentEntity> workDocs = projectDAO.getAllWorkDocumentByDepartMentId(deptId);

			List<Integer> pendingVerificationList = workDocs.stream().
					filter(work -> work.getVerificationStatus() == 0)
					.map(w -> w.getVerificationStatus())
					.collect(Collectors.toList());

			List<Integer> pendingApprovalList = workDocs.stream().
					filter(workApp -> workApp.getApprovalStatus() == 0)
					.map(wApp -> wApp.getApprovalStatus())
					.collect(Collectors.toList());

			workDetailsMap.put("noOfWork", workDetailsEntities.size());
			workDetailsMap.put("noOfSubTasks", subTaskList.size());
			workDetailsMap.put("pendingVerification", pendingVerificationList.size());
			workDetailsMap.put("pendingApproval", pendingApprovalList.size());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkDetailsByDeptId, "+e.getMessage());
		}
		return workDetailsMap;
	}

	private Map<String,Integer> getWorkDetails(String token){
		Map<String,Integer> workDetailsMap = null;
		try{
			TokenEntity tokenModel = userDAO.getTokenModelByToken(token);
			EmployeeEntity employee = userDAO.getEmployeeById(tokenModel.getUserId());
			workDetailsMap = new HashMap<String, Integer>();
			//Admin
			if(employee.getUserRoleId() == 1){
				List<WorkDetailsEntity> workDetailsEntities = projectDAO.getWorkDetailsEntitiesByDeptId(0, 1);
				List<SubTaskEntity> subTaskList = projectDAO.getSubTaskEntitiesByWorkId(0, 1);
				List<WorkDocumentEntity> workDocs = projectDAO.getAllWorkDocumentByDepartMentId(0);

				List<Integer> pendingVerificationList = workDocs.stream().
						filter(work -> work.getVerificationStatus() == 0)
						.map(w -> w.getVerificationStatus())
						.collect(Collectors.toList());

				List<Integer> pendingApprovalList = workDocs.stream().
						filter(workApp -> workApp.getApprovalStatus() == 0)
						.map(wApp -> wApp.getApprovalStatus())
						.collect(Collectors.toList());

				workDetailsMap.put("noOfWork", workDetailsEntities.size());
				workDetailsMap.put("noOfSubTasks", subTaskList.size());
				workDetailsMap.put("pendingVerification", pendingVerificationList.size());
				workDetailsMap.put("pendingApproval", pendingApprovalList.size());
			}
			// HOD or Department coordinator
			if(employee.getUserRoleId() == 2 || employee.getUserRoleId() == 4){
				List<WorkDetailsEntity> workDetailsEntities = projectDAO.getWorkDetailsEntitiesByDeptId(employee.getDepartmentId(),1 );
				List<SubTaskEntity> subTaskList = projectDAO.getSubTaskEntitiesByWorkId(employee.getDepartmentId(), 1);
				List<WorkDocumentEntity> workDocs = projectDAO.getAllWorkDocumentByDepartMentId(employee.getDepartmentId());

				List<Integer> pendingVerificationList = workDocs.stream().
						filter(work -> work.getVerificationStatus() == 0)
						.map(w -> w.getVerificationStatus())
						.collect(Collectors.toList());

				List<Integer> pendingApprovalList = workDocs.stream().
						filter(workApp -> workApp.getApprovalStatus() == 0)
						.map(wApp -> wApp.getApprovalStatus())
						.collect(Collectors.toList());

				workDetailsMap.put("noOfWork", workDetailsEntities.size());
				workDetailsMap.put("noOfSubTasks", subTaskList.size());
				workDetailsMap.put("pendingVerification", pendingVerificationList.size());
				workDetailsMap.put("pendingApproval", pendingApprovalList.size());
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkDetails, "+e.getMessage());
		}
		return workDetailsMap;

	}

}
