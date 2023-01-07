package com.yaz.alind.service;

import java.io.File;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yaz.alind.dao.UserDAO;
import com.yaz.alind.entity.AuthorizationEntity;
import com.yaz.alind.entity.DepartmentEntity;
import com.yaz.alind.entity.DeputationEntity;
import com.yaz.alind.entity.DeputationHistoryEntity;
import com.yaz.alind.entity.EmployeeEntity;
import com.yaz.alind.entity.EmployeeTypesEntity;
import com.yaz.alind.entity.TokenEntity;
import com.yaz.alind.entity.UserRolesEntity;
import com.yaz.alind.model.ui.DeputationModel;
import com.yaz.alind.model.ui.EmployeeModel;
import com.yaz.alind.util.Iconstants;

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
	public EmployeeModel getAuthentication(String userName, String password) {
		//EmployeeEntity employee = null;
		TokenEntity tokenModel = null;
		boolean timeStatus = false;
		EmployeeModel empModel = null;
		try{
			EmployeeEntity empEnity =  userDAO.getAuthentication(userName, password);
			empModel = createEmployeeModel(empEnity);
			tokenModel = userDAO.getTokenModelByUserId(empModel.getEmployeeId());
			//			System.out.println("UserServiceImpl,getAuthentication,getEmployeeId: "+employee.getEmployeeId()+", token: "+tokenModel.getToken());
			if(empModel != null ){
				if(tokenModel != null){
					//employee.setPassword(null);
					timeStatus = utilService.evaluateSessionTime(tokenModel.getDateTime(), utilService.getCurrentDateTime());
					//				System.out.println("UserServiceImpl,getAuthentication,timeStatus: "+timeStatus);
					String token = utilService.createToken();
					Date date = utilService.getCurrentDateTime();
					tokenModel.setToken(token);
					tokenModel.setDateTime(date);
					tokenModel = userDAO.saveOrUpdateToken(tokenModel);
					//employee.setToken(tokenModel.getToken());
					empModel.setToken(tokenModel.getToken());
				}else{
					tokenModel = new TokenEntity();
					timeStatus = utilService.evaluateSessionTime(tokenModel.getDateTime(), utilService.getCurrentDateTime());
					//				System.out.println("UserServiceImpl,getAuthentication,timeStatus: "+timeStatus);
					String token = utilService.createToken();
					Date date = utilService.getCurrentDateTime();
					tokenModel.setUserId(empModel.getEmployeeId());
					tokenModel.setToken(token);
					tokenModel.setDateTime(date);
					tokenModel = userDAO.saveOrUpdateToken(tokenModel);
					//employee.setToken(tokenModel.getToken());
					empModel.setToken(tokenModel.getToken());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAuthentication: "+e.getMessage());
		}
		return empModel;
		//		return employee;
	}

	@Override
	public TokenEntity getTokenModelByUserId(int userId) {
		return userDAO.getTokenModelByUserId(userId);
	}
	@Override
	public EmployeeModel saveEmployee(EmployeeModel employee){
		EmployeeModel empModel = null;
		EmployeeEntity emp = null;
		try{
			EmployeeEntity lastEmp = userDAO.getLastEmployeeDetails();
			//			System.out.println("Business,saveOrUpdateEmployee,lastEmp, id: "+lastEmp.getEmployeeId());
			Date today = utilService.getTodaysDate();
			EmployeeEntity empEntity = createEmployeeEntity(employee);
			int lastEmpCode = Integer.parseInt(lastEmp.getEmpCode());
			empEntity.setEmpCode(Integer.toString(lastEmpCode+1));
			empEntity.setUserName(Integer.toString(lastEmpCode+1));
			empEntity.setPassword(Integer.toString(lastEmpCode+1));
			empEntity.setIsActive(1);
			empEntity.setCreatedAt(utilService.dateToTimestamp(today));
			empEntity.setUpdatedAt(utilService.dateToTimestamp(today));
			emp = userDAO.saveEmployee(empEntity);
			emp = userDAO.getEmployeeById(emp.getEmployeeId());
			//System.out.println("saveOrUpdateEmployee,Last name: "+emp.getLastName());
			//System.out.println("saveOrUpdateEmployee,EmpolyeeTypeId: "+emp.getEmployeeTypes().getEmpolyeeTypeId());
			empModel = createEmployeeModel(emp);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveEmployee: "+e.getMessage());
		}
		return empModel;
	}

	@Override
	public EmployeeModel updateEmployee(EmployeeModel employee){
		EmployeeModel empModel = null;
		try{
			EmployeeEntity emp = userDAO.getEmployeeById(employee.getEmployeeId());
			Date today = utilService.getTodaysDate();
			EmployeeEntity empEntity = createEmployeeEntity(employee);
			empEntity.setCreatedAt(emp.getCreatedAt());
			empEntity.setUpdatedAt(utilService.dateToTimestamp(today));
			empEntity.setIsActive(1);
			empEntity.setPassword(emp.getPassword());
			empEntity.setUserName(emp.getUserName());
			emp = userDAO.updateEmployee(empEntity);
			emp = userDAO.getEmployeeById(emp.getEmployeeId());
			empModel = createEmployeeModel(emp);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateEmployee: "+e.getMessage());
		}
		return empModel;
	}

	@Override
	public int uploadEmployeeProfilePic(MultipartFile profilePic, int employeeId,String contextPath) {
		int status = -1;
		try{
			String originalFileName = profilePic.getOriginalFilename();
			String fileName = utilService.createFileName(profilePic.getOriginalFilename());
			String fileLocation = Iconstants.EMPLOYEE_PROFILE_PIC_LOCATION+employeeId;
			System.out.println("Business,uploadEmployeeProfilePic, fileLocation: "+fileLocation
					+", fileName: "+fileName+", employeeId: "+employeeId);
			status = utilService.saveFile(profilePic, contextPath, fileLocation);
			if(status > 0){
				EmployeeEntity employee = userDAO.getEmployeeById(employeeId);
				Date today = utilService.getTodaysDate();
				employee.setUpdatedAt(utilService.dateToTimestamp(today));
				employee.setProfilePicPath(fileName);
				employee.setOrginalProfilePicName(originalFileName);
				//				employee = userDAO.saveOrUpdateEmployee(employee);
				employee = userDAO.updateEmployee(employee);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("uploadEmployeeProfilePic: "+e.getMessage());
		}
		return status;
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
	public List<EmployeeModel> getAllEmployees(String token) {
		//		public List<EmployeeEntity> getAllEmployees(String token) {
		List<EmployeeModel> empModelList = null;
		//List<EmployeeEntity> employees = null;
		try{
			empModelList = new ArrayList<EmployeeModel>();
			List<EmployeeEntity> employees = userDAO.getAllEmployees();

			for(EmployeeEntity e: employees){
				EmployeeModel m = createEmployeeModel(e);
				empModelList.add(m);
			}

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

			//			for(int i=0;i<employees.size();i++){
			//				employees.get(i).setPassword(null);
			//			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllEmployees: "+e.getMessage());
		}
		return empModelList;
		//return employees;
	}

	@Override
	public List<DepartmentEntity> getAllActiveDepartments() {
		return userDAO.getAllActiveDepartments();
	}

	@Override
	public List<DepartmentEntity> getAllDepartments(){
		return userDAO.getAllDepartments();
	}

	@Override
	public DepartmentEntity updateDepartment(DepartmentEntity department) {
		DepartmentEntity dept = null;
		try{
			department.setCreatedOn(utilService.getTodaysDate());
			//			System.out.println("Business,getAllEmployees, created on: "+department.getCreatedOn());
			//			department.setIsActive(1);
			dept = userDAO.updateDepartment(department);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateDepartment: "+e.getMessage());
		}
		return dept;
	}

	@Override
	public DepartmentEntity saveDepartment(DepartmentEntity department) {
		DepartmentEntity dept = null;
		try{
			department.setCreatedOn(utilService.getTodaysDate());
			//			System.out.println("Business,getAllEmployees, created on: "+department.getCreatedOn());
			department.setIsActive(1);
			dept = userDAO.saveDepartment(department);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveDepartment: "+e.getMessage());
		}
		return dept;
	}

	@Override
	public List<EmployeeTypesEntity> getAllEmployeeTypes() {
		return userDAO.getAllEmployeeTypes();
	}

	@Override
	public EmployeeModel getEmployeeById(int employeeId) {
		EmployeeModel empModel = null;
		//EmployeeEntity employee = null;
		try{
			EmployeeEntity	employee = userDAO.getEmployeeById(employeeId);
			empModel = createEmployeeModel(employee);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getEmployeeById: "+e.getMessage());
		}
		return empModel;
		//return employee;
	}

	/***
	 * Only for UI purpose
	 * @param employeeId
	 * @return
	 */
	@Override
	public List<EmployeeModel> getEmployeeListTypeById(int employeeId) {
		List<EmployeeModel> empModelList = null;
		try{
			empModelList = new ArrayList<EmployeeModel>();
			EmployeeEntity	employee = userDAO.getEmployeeById(employeeId);
			EmployeeModel empModel = createEmployeeModel(employee);
			empModelList.add(empModel);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getEmployeeById: "+e.getMessage());
		}
		return empModelList;
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
			//			emplEntity = userDAO.saveOrUpdateEmployee(emplEntity);
			emplEntity = userDAO.updateEmployee(emplEntity);
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
			departmentEntity = userDAO.updateDepartment(departmentEntity);
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

	@Override
	public List<EmployeeModel> searchEmployee(String searchKeyWord, int departmentId){
		List<EmployeeModel> empModels = null;
		try{
			empModels = new ArrayList<EmployeeModel>();
			List<EmployeeEntity> empEntityList = userDAO.searchEmployee(searchKeyWord, departmentId);
			for(EmployeeEntity et: empEntityList){
				EmployeeModel eModel = createEmployeeModel(et)   ;
				empModels.add(eModel);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("searchEmployee: "+e.getMessage());
		}
		return empModels;
	}

	@Override
	public List<EmployeeModel> getDeputedEmployeeListByDeptId(int departmentId) {
		List<EmployeeModel> employeeModels = null;
		try{
			employeeModels = new ArrayList<EmployeeModel>();
			List<DeputationEntity> deputationEntities = userDAO.getDeputationListByDeptId(departmentId);
			for(int i=0;i<deputationEntities.size();i++){
				EmployeeEntity emp = deputationEntities.get(i).getEmployee();
				EmployeeModel empModel = createEmployeeModel(emp);
				employeeModels.add(empModel);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDeputedEmployeeListByDeptId: "+e.getMessage());
		}
		return employeeModels;
	}

	private DeputationEntity createDeputationEntity(DeputationModel model){
		DeputationEntity entity = null;
		try{
			entity = new DeputationEntity();
			System.out.println("UserService,CreatedOn: "+model.getCreatedOn());
			entity.setCreatedOn(utilService.stringToDate(model.getCreatedOn()));
			entity.setDeputedDepartmentId(model.getDeputedDepartmentId());
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

	private EmployeeEntity createEmployeeEntity(EmployeeModel model){
		EmployeeEntity enity = null;
		try{
			enity = new EmployeeEntity();
			enity.setAccommodationLocation(model.getAccommodationLocation());
			//enity.setCreatedAt(utilService.stringDateToTimestamp(model.getCreatedAt()));
			enity.setDepartmentId(model.getDepartmentId());
			//enity.setDepartmentName(entity.getDepartment().getDepartmentName());
			enity.setEmailId(model.getEmailId());
			enity.setEmergencyContactName(model.getEmergencyContactName());
			enity.setEmergencyContactPhone(model.getEmergencyContactPhone());
			enity.setEmpCode(model.getEmpCode());
			enity.setEmployeeId(model.getEmployeeId());
			enity.setEmpolyeeTypeId(model.getEmpolyeeTypeId());
			//enity.setEmployeeTypeName(entity.getEmployeeTypes().getEmployeeTypeName());
			enity.setFirstName(model.getFirstName());
			//enity.setEmpFullName(entity.getFirstName()+" "+entity.getLastName());
			//			enity.setEmpFullNameWithEmpCode(entity.getFirstName()+" "+entity.getLastName()+" - "+
			//					entity.getEmpCode());
			enity.setGender(model.getGender());
			enity.setHealthCardNo(model.getHealthCardNo());
			if(enity.getHealthCardValidity() != null){
				enity.setHealthCardValidity(utilService.stringDateToTimestamp(model.getHealthCardValidity()));
			}
			enity.setInsurancePolicyNo(model.getInsurancePolicyNo());
			enity.setIsActive(model.getIsActive());
			enity.setLastName(model.getLastName());
			if(enity.getLastWorkingDay() != null){
				enity.setLastWorkingDay(utilService.stringDateToTimestamp(model.getLastWorkingDay()));
			}
			if(model.getDateOfJoin() != null){
				enity.setDateOfJoin(utilService.stringDateToTimestamp(model.getDateOfJoin()));
			}
			enity.setNationality(model.getNationality());
			enity.setNativeAddress(model.getNativeAddress());
			enity.setOrginalProfilePicName(model.getOrginalProfilePicName());
			enity.setOtherDetails(model.getOtherDetails());
			enity.setPassportNo(model.getPassportNo());
			enity.setPrimaryMobileNo(model.getPrimaryMobileNo());
			//            model.setProfilePicPath(profilePicPath);
			enity.setRelationship(model.getRelationship());
			//enity.setRoleName(entity.getUsrRole().getRoleName());
			enity.setSecondaryEmailId(model.getSecondaryEmailId());
			enity.setSecondaryMobileNo(model.getSecondaryMobileNo());
			enity.setToken(model.getToken());
			//			if(enity.getUpdatedAt() != null){
			//			enity.setUpdatedAt(utilService.stringDateToTimestamp(model.getUpdatedAt()));
			//			}
			enity.setUploadId(model.getUploadId());
			enity.setUserName(model.getUserName());
			enity.setUserRoleId(model.getUserRoleId());

		}catch(Exception e){
			e.printStackTrace();
			logger.error("createEmployeeModel: "+e.getMessage());
		}
		return enity; 
	}

	private EmployeeModel createEmployeeModel(EmployeeEntity entity){
		EmployeeModel model = null;
		try{
			model = new EmployeeModel();
			String profImageLocation = Iconstants.EMPLOYEE_PROFILE_PIC_LOCATION+entity.getEmployeeId();
			model.setAccommodationLocation(entity.getAccommodationLocation());
			if(entity.getCreatedAt() != null){
				model.setCreatedAt(utilService.dateToString(utilService.dateToTimestamp(entity.getCreatedAt())));
			}
			model.setDepartmentId(entity.getDepartmentId());
			model.setDepartmentName(entity.getDepartment().getDepartmentName());
			model.setEmailId(entity.getEmailId());
			model.setEmergencyContactName(entity.getEmergencyContactName());
			model.setEmergencyContactPhone(entity.getEmergencyContactPhone());
			model.setEmpCode(entity.getEmpCode());
			model.setEmployeeId(entity.getEmployeeId());
			model.setEmpolyeeTypeId(entity.getEmpolyeeTypeId());
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
			if(entity.getDateOfJoin() != null){
				model.setDateOfJoin(utilService.dateToString(entity.getDateOfJoin()));
			}
			model.setNationality(entity.getNationality());
			model.setNativeAddress(entity.getNativeAddress());
			model.setOrginalProfilePicName(entity.getOrginalProfilePicName());
			model.setOtherDetails(entity.getOtherDetails());
			model.setPassportNo(entity.getPassportNo());
			//Profile image setting
			if(entity.getProfilePicPath() != null){
				model.setProfilePicPath(profImageLocation+"/"+entity.getProfilePicPath());
			}else{
				model.setProfilePicPath(Iconstants.DEFAULT_PROFILE_IMAGE);
			}
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
			model.setDeputedDepartmentId(entity.getDeputedDepartmentId());
			model.setDeputedDepartmentName(entity.getDeputedDepartment().getDepartmentName());
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


	@Override
	public AuthorizationEntity getAuthorizationByUserRole(int userRoleId) {
		AuthorizationEntity entity = null;
		try{
			entity = userDAO.getAuthorizationByUserRole(userRoleId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAuthorizationByUserRole: "+e.getMessage());
		}
		return entity;
	}

	@Override
	public AuthorizationEntity updateAuthorization(AuthorizationEntity entity) {
		AuthorizationEntity authorizationEntity = null;
		try{
			authorizationEntity = userDAO.updateAuthorization(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateAuthorization: "+e.getMessage());
		}
		return authorizationEntity;
	}

	@Override
	public void getExcellReader() {
		String inputFile = "F:/Yazata/Project/Alind_Intra/Docs/Alind_Employee.xls";
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			ArrayList<EmployeeEntity> empList = new ArrayList<EmployeeEntity>();
			w = Workbook.getWorkbook(inputWorkbook);
			//			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			//			for (int i = 1; i < 66; i++) {
			//			for (int i = 1; i < 10; i++) {
			for (int i = 1; i < 66; i++) {
				EmployeeEntity emp = new EmployeeEntity();
				//				emp.setCreatedAt(createdAt);
				//				emp.setEmpCode(empCode);(Integer.parseInt(jSonEscape(sheet.getCell(0,i).getContents())));
				emp.setEmpCode(jSonEscape(sheet.getCell(0,i).getContents()));
				emp.setUserName(emp.getEmpCode());
				emp.setPassword(emp.getEmpCode());
				emp.setFirstName(jSonEscape(sheet.getCell(1,i).getContents()));
				emp.setUserRoleId(Integer.parseInt(jSonEscape(sheet.getCell(3,i).getContents())));
				emp.setDepartmentId(Integer.parseInt(jSonEscape(sheet.getCell(5,i).getContents())));
				Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sheet.getCell(6,i).getContents());
				//				String dojStr = sheet.getCell(6,i).getContents();
				emp.setGender(jSonEscape(sheet.getCell(7,i).getContents()));
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
				//				Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(sheet.getCell(6,i).getContents());
				emp.setIsActive(1);
				emp.setCreatedAt(utilService.getCurrentDateTimeStamp());
				emp.setUpdatedAt(utilService.getCurrentDateTimeStamp());
				emp.setUploadId(0);
				emp.setEmpolyeeTypeId(1);
				//				utilService.stringDateToTimestamp(dojStr);

				emp.setDateOfJoin(utilService.dateToTimestamp(date1));
				//				System.out.println(" New Format : "+simpleDateFormat.format(date1));
				//				System.out.println("Emp code: "+emp.getEmpCode()+", count: "+i);

				empList.add(emp);
			}
			empList.sort((e1, e2) ->Integer.parseInt(e1.getEmpCode())- Integer.parseInt(e2.getEmpCode()));
			for(EmployeeEntity e: empList){
				//				System.out.println("Emp code: "+e.getEmpCode());
				userDAO.saveEmployee(e);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getExcellReader: "+e.getMessage());
		}
	}

	private String jSonEscape(String str){

		return Normalizer
				.normalize(str, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "");
	}

}
