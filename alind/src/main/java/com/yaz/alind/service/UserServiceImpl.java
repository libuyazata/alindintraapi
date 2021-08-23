package com.yaz.alind.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yaz.alind.dao.UserDAO;
import com.yaz.alind.entity.DepartmentEntity;
import com.yaz.alind.entity.EmployeeEntity;
import com.yaz.alind.entity.EmployeeTypesEntity;
import com.yaz.alind.entity.TokenEntity;
import com.yaz.alind.entity.UserRolesEntity;
import com.yaz.security.Iconstants;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UtilService utilService;
	@Autowired
	UserDAO userDAO;

	@Override
	public TokenEntity saveOrUpdateToken(TokenEntity tokenModel) {
		try{

			String uuid = utilService.createToken();
			tokenModel.setToken(uuid);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateToken: "+e.getMessage());
		}
		return tokenModel;
	}

	@Override
	public EmployeeEntity getAuthentication(String userName, String password) {
		EmployeeEntity employee = null;
		TokenEntity tokenModel = null;
		boolean timeStatus = false;
		try{
			employee = userDAO.getAuthentication(userName, password);
			tokenModel = userDAO.getTokenModelByUserId(employee.getEmployeeId());
			//			System.out.println("UserServiceImpl,getAuthentication,getEmployeeId: "+employee.getEmployeeId()+", token: "+tokenModel.getToken());
			if(employee != null ){
				if(tokenModel != null){
					employee.setPassword(null);
					timeStatus = utilService.evaluateSessionTime(tokenModel.getDateTime(), utilService.getCurrentDateTime());
					//				System.out.println("UserServiceImpl,getAuthentication,timeStatus: "+timeStatus);
					String token = utilService.createToken();
					Date date = utilService.getCurrentDateTime();
					tokenModel.setToken(token);
					tokenModel.setDateTime(date);
					tokenModel = userDAO.saveOrUpdateToken(tokenModel);
					employee.setToken(tokenModel.getToken());
				}else{
					tokenModel = new TokenEntity();
					timeStatus = utilService.evaluateSessionTime(tokenModel.getDateTime(), utilService.getCurrentDateTime());
					//				System.out.println("UserServiceImpl,getAuthentication,timeStatus: "+timeStatus);
					String token = utilService.createToken();
					Date date = utilService.getCurrentDateTime();
					tokenModel.setUserId(employee.getEmployeeId());
					tokenModel.setToken(token);
					tokenModel.setDateTime(date);
					tokenModel = userDAO.saveOrUpdateToken(tokenModel);
					employee.setToken(tokenModel.getToken());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAuthentication: "+e.getMessage());
		}
		return employee;
	}

	@Override
	public TokenEntity getTokenModelByUserId(int userId) {
		return userDAO.getTokenModelByUserId(userId);
	}
	@Override
	public EmployeeEntity saveOrUpdateEmployee(EmployeeEntity employee,MultipartFile profilePic,String contextPath) {

		EmployeeEntity emp = null;
		try{
			EmployeeEntity lastEmp = userDAO.getLastEmployeeDetails();
			//			System.out.println("Business,saveOrUpdateEmployee,lastEmp, id: "+lastEmp.getEmployeeId());
			Date today = utilService.getTodaysDate();
			String originalFileName = profilePic.getOriginalFilename();
			String fileName = utilService.createFileName(profilePic.getOriginalFilename());
			if(employee.getEmpCode() == null){
				int lastEmpCode = Integer.parseInt(lastEmp.getEmpCode());
				employee.setEmpCode(Integer.toString(lastEmpCode+1));
				employee.setUserName(Integer.toString(lastEmpCode+1));
				employee.setPassword(Integer.toString(lastEmpCode+1));
				employee.setCreatedAt(utilService.dateToTimestamp(today));
			}
			employee.setUpdatedAt(utilService.dateToTimestamp(today));
			employee.setProfilePicPath(fileName);
			employee.setOrginalProfilePicName(originalFileName);
			emp = userDAO.saveOrUpdateEmployee(employee);

			String fileLocation = Iconstants.EMPLOYEE_PROFILE_PIC_LOCATION+emp.getEmployeeId();
			int status = utilService.saveFile(profilePic, contextPath, fileLocation);
			if(status < 0){
				emp = null;
			}
			
			emp.setPassword(null);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAuthentication: "+e.getMessage());
		}
		return emp;
	}

	@Override
	public TokenEntity getTokenModelByToken(String token) {
		return userDAO.getTokenModelByToken(token);
	}

	@Override
	public List<UserRolesEntity> getAllUserRoles() {
		return userDAO.getAllUserRoles();
	}

	@Override
	public UserRolesEntity getUserRoleById(int userRoleId) {
		return userDAO.getUserRoleById(userRoleId);
	}

	@Override
	public List<EmployeeEntity> getAllEmployees(String token) {
		List<EmployeeEntity> employees = null;
		try{
			
			employees = userDAO.getAllEmployees();
			/**
			EmployeeEntity employee = getEmployeeByToken(token);
			// Admin
			if(employee.getUserRoleId() == 1){
				employees = userDAO.getAllEmployees();
			}
			// HOD or Coordinator
			if(employee.getUserRoleId() == 2 || employee.getUserRoleId() == 2){
				employees = userDAO.getAllEmployeesByDept(employee.getDepartmentId());
			}**/

			for(int i=0;i<employees.size();i++){
				employees.get(i).setPassword(null);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllEmployees: "+e.getMessage());
		}
		return employees;
	}

	@Override
	public List<DepartmentEntity> getAllDepartment() {
		return userDAO.getAllDepartment();
	}

	@Override
	public DepartmentEntity saveOrUpdateDepartment(DepartmentEntity department) {
		DepartmentEntity dept = null;
		try{
			department.setCreatedOn(utilService.getTodaysDate());
			System.out.println("Business,getAllEmployees, created on: "+department.getCreatedOn());
			dept = userDAO.saveOrUpdateDepartment(department);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllEmployees: "+e.getMessage());
		}
		return dept;
	}

	@Override
	public List<EmployeeTypesEntity> getAllEmployeeTypes() {
		return userDAO.getAllEmployeeTypes();
	}

	@Override
	public EmployeeEntity getEmployeeById(int employeeId) {
		EmployeeEntity employee = null;
		try{
			employee = userDAO.getEmployeeById(employeeId);
			//			employee.setPassword(null);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getEmployeeById: "+e.getMessage());
		}
		return employee;
	}

	@Override
	public EmployeeEntity getEmployeeByToken(String token) {
		EmployeeEntity employee = null;
		try{
			TokenEntity tokenModel = userDAO.getTokenModelByToken(token);
			employee = userDAO.getEmployeeById(tokenModel.getUserId());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getEmployeeByToken: "+e.getMessage());
		}
		return employee;
	}



}
