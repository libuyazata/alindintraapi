package com.yaz.alind.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yaz.alind.dao.UserDAO;
import com.yaz.alind.entity.DepartmentEntity;
import com.yaz.alind.entity.DeputationEntity;
import com.yaz.alind.entity.DeputationHistoryEntity;
import com.yaz.alind.entity.EmployeeEntity;
import com.yaz.alind.entity.EmployeeTypesEntity;
import com.yaz.alind.entity.TokenEntity;
import com.yaz.alind.entity.UserRolesEntity;
import com.yaz.alind.model.ui.DeputationModel;
import com.yaz.alind.model.ui.EmployeeModel;
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

	@Override
	public int deleteEmployeeEntity(int employeeId) {
		int status = 0;
		try{
			EmployeeEntity emplEntity = userDAO.getEmployeeById(employeeId);
			emplEntity.setIsActive(-1);
			emplEntity = userDAO.saveOrUpdateEmployee(emplEntity);
			if(emplEntity.getIsActive() == -1){
				status = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("deleteEmployeeEntity: "+e.getMessage());
		}
		return status;
	}

	@Override
	public int deleteDepartmentEntity(int departmentId) {
		int status = 0;
		try{
			DepartmentEntity departmentEntity = userDAO.getDepartmentById(departmentId);
			departmentEntity.setIsActive(-1);
			departmentEntity = userDAO.saveOrUpdateDepartment(departmentEntity);
			if(departmentEntity.getIsActive() == -1){
				status = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("deleteDepartmentEntity: "+e.getMessage());
		}
		return status;
	}

	@Override
	public DeputationModel saveDeputation(DeputationModel deputationModel) {
		DeputationModel model = null;
		try{
			Date date = utilService.getCurrentDate();
			String strDate = utilService.dateToString(date);
			//			System.out.println("Business, saveDeputation, date: "+date+", strDate: "+strDate);
			deputationModel.setCreatedOn(strDate);
			deputationModel.setUpdatedOn(strDate);
			deputationModel.setStatus(1);
			DeputationEntity entity = createDeputationEntity(deputationModel);
			entity = userDAO.saveDeputation(entity);
			// Deputation History
			entity = userDAO.getDeputationById(entity.getDeputationId());
			DeputationHistoryEntity history = new DeputationHistoryEntity();
			history.setDeputationId(entity.getDeputationId());
			history.setEmployeeId(entity.getEmployeeId());
			history.setParentDepartmentId(entity.getEmployee().getDepartmentId());
			//			System.out.println("Business, saveDeputation, DeputationId: "+history.getDeputationId()
			//					+", EmployeeId: "+history.getEmployeeId()+",ParentDepartmentId:  "+history.getParentDepartmentId());
			history = userDAO.saveDeputationHistory(history);

			model = createDeputationModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveDeputation: "+e.getMessage());
		}
		return model;
	}

	@Override
	public DeputationModel updateDeputation(DeputationModel deputationModel) {
		DeputationModel model = null;
		try{
			Date date = utilService.getCurrentDate();
			deputationModel.setUpdatedOn(utilService.dateToString(date));
			DeputationEntity entity = createDeputationEntity(model);
			entity = userDAO.updateDeputation(entity);
			model = createDeputationModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateDeputation: "+e.getMessage());
		}
		return model;
	}

	@Override
	public List<DeputationModel> getDeputationListByDeptId(int departmentId) {
		List<DeputationModel> models = null;
		try{
			List<DeputationEntity> entities = userDAO.getDeputationListByDeptId(departmentId);
			models = new ArrayList<DeputationModel>();
			for(DeputationEntity e: entities){
				DeputationModel m = createDeputationModel(e);
				models.add(m);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDeputationListByDeptId: "+e.getMessage());
		}
		return models;
	}

	@Override
	public int deleteDeputation(int deputationId) {
		int value = 0;
		try{
			DeputationEntity entity = userDAO.getDeputationById(deputationId);
			entity.setStatus(0);
			entity.setUpdatedOn(utilService.getCurrentDate());
			entity = userDAO.updateDeputation(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("deleteDeputation: "+e.getMessage());
		}
		return value;
	}

	@Override
	public DeputationModel getDeputationById(int deputationId) {
		DeputationModel model = null;
		try{
			DeputationEntity entity = userDAO.getDeputationById(deputationId);
			model = createDeputationModel(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDeputationById: "+e.getMessage());
		}
		return model;
	}

	@Override
	public List<EmployeeModel> getAllEmployeesByDept(int departmentId) {
		List<EmployeeModel> empModels = null;
		try{
			empModels = new ArrayList<EmployeeModel>();
			List<EmployeeEntity> entities = userDAO.getAllEmployeesByDept(departmentId);
			for(EmployeeEntity e: entities){
				EmployeeModel m = createEmployeeModel(e);
				empModels.add(m);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllEmployeesByDept: "+e.getMessage());
		}
		return empModels;
	}

	private DeputationEntity createDeputationEntity(DeputationModel model){
		DeputationEntity entity = null;
		try{
			entity = new DeputationEntity();
			System.out.println("UserService,CreatedOn: "+model.getCreatedOn());
			entity.setCreatedOn(utilService.stringToDate(model.getCreatedOn()));
			entity.setDepartmentId(model.getDepartmentId());
			entity.setDeputationId(model.getDeputationId());
			entity.setDescription(model.getDescription());
			entity.setEmployeeId(model.getEmployeeId());
			entity.setStartDate(utilService.stringToDate(model.getEndDate()));
			entity.setEndDate(utilService.stringToDate(model.getStartDate()));
			entity.setStatus(model.getStatus());
			entity.setUpdatedOn(utilService.stringToDate(model.getUpdatedOn()));

		}catch(Exception e){
			e.printStackTrace();
			logger.error("createDeputationModel: "+e.getMessage());
		}
		return entity;
	}

	private EmployeeModel createEmployeeModel(EmployeeEntity entity){
		EmployeeModel model = null;
		try{
			model = new EmployeeModel();
			model.setAccommodationLocation(entity.getAccommodationLocation());
			model.setCreatedAt(utilService.dateToString(entity.getCreatedAt()));
			model.setDepartmentId(entity.getDepartmentId());
			model.setDepartmentName(entity.getDepartment().getDepartmentName());
			model.setEmailId(entity.getEmailId());
			model.setEmergencyContactName(entity.getEmergencyContactName());
			model.setEmergencyContactPhone(entity.getEmergencyContactPhone());
			model.setEmpCode(entity.getEmpCode());
			model.setEmployeeId(entity.getEmployeeId());
			model.setEmployeeTypeName(entity.getEmployeeTypes().getEmployeeTypeName());
			model.setFirstName(entity.getFirstName());
			model.setEmpFullName(entity.getFirstName()+" "+entity.getLastName());
			model.setEmpFullNameWithEmpCode(entity.getFirstName()+" "+entity.getLastName()+" - "+
													entity.getEmpCode());
			model.setGender(entity.getGender());
			model.setHealthCardNo(entity.getHealthCardNo());
			if(entity.getHealthCardValidity() != null){
				model.setHealthCardValidity(utilService.dateToString(entity.getHealthCardValidity()));
			}
			model.setInsurancePolicyNo(entity.getInsurancePolicyNo());
			model.setIsActive(entity.getIsActive());
			model.setLastName(entity.getLastName());
			if(entity.getLastWorkingDay() != null){
				model.setLastWorkingDay(utilService.dateToString(entity.getLastWorkingDay()));
			}
			model.setNationality(entity.getNationality());
			model.setNativeAddress(entity.getNativeAddress());
			model.setOrginalProfilePicName(entity.getOrginalProfilePicName());
			model.setOtherDetails(entity.getOtherDetails());
			model.setPassportNo(entity.getPassportNo());
			model.setPrimaryMobileNo(entity.getPrimaryMobileNo());
			//            model.setProfilePicPath(profilePicPath);
			model.setRelationship(entity.getRelationship());
			model.setRoleName(entity.getUsrRole().getRoleName());
			model.setSecondaryEmailId(entity.getSecondaryEmailId());
			model.setSecondaryMobileNo(entity.getSecondaryMobileNo());
			model.setToken(entity.getToken());
			if(entity.getUpdatedAt() != null){
				model.setUpdatedAt(utilService.dateToString(entity.getUpdatedAt()));
			}
            model.setUploadId(entity.getUploadId());
            model.setUserName(entity.getUserName());
            model.setUserRoleId(entity.getUserRoleId());

		}catch(Exception e){
			e.printStackTrace();
			logger.error("createEmployeeModel: "+e.getMessage());
		}
		return model; 
	}

	private DeputationModel createDeputationModel(DeputationEntity entity){
		DeputationModel model = null;
		try{
			model = new DeputationModel();
			//			model.setAssignedByemployeeName(entity.getAssignedByemployee().getFirstName()
			//					+" "+entity.getAssignedByemployee().getLastName());
			//			model.setAssignedEmployeeId(entity.getAssignedBy());
			model.setCreatedOn(utilService.dateToString(entity.getCreatedOn()));
			model.setDepartmentId(entity.getDepartmentId());
			model.setDepartmentName(entity.getDeputedDepartment().getDepartmentName());
			model.setDeputationId(entity.getDeputationId());
			model.setDescription(entity.getDescription());
			model.setEmpCode(entity.getEmployee().getEmpCode());
			model.setEmployeeId(entity.getEmployeeId());
			model.setEmployeeName(entity.getEmployee().getFirstName()+" "+
					entity.getEmployee().getLastName());
			model.setEndDate(utilService.dateToString(entity.getEndDate()));
			model.setStartDate(utilService.dateToString(entity.getStartDate()));
			model.setStatus(entity.getStatus());
			model.setUpdatedOn(utilService.dateToString(entity.getUpdatedOn()));

		}catch(Exception e){
			e.printStackTrace();
			logger.error("createDeputationModel: "+e.getMessage());
		}
		return model;
	}





}
