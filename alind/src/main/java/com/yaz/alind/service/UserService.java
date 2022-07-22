package com.yaz.alind.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yaz.alind.entity.AuthorizationEntity;
import com.yaz.alind.entity.DepartmentEntity;
import com.yaz.alind.entity.EmployeeEntity;
import com.yaz.alind.entity.EmployeeTypesEntity;
import com.yaz.alind.entity.TokenEntity;
import com.yaz.alind.entity.UserRolesEntity;
import com.yaz.alind.model.ui.DeputationModel;
import com.yaz.alind.model.ui.EmployeeModel;

public interface UserService {

	public TokenEntity saveOrUpdateToken(TokenEntity tokenModel);
	public TokenEntity getTokenModelByUserId(int userId);
//	public EmployeeEntity saveOrUpdateEmployee(EmployeeEntity employee);
//	public EmployeeModel saveOrUpdateEmployee(EmployeeModel employee);
	
	public EmployeeModel saveEmployee(EmployeeModel employee);
	public EmployeeModel updateEmployee(EmployeeModel employee);
	
//	public EmployeeEntity getAuthentication(String userName,String password);
	public EmployeeModel getAuthentication(String userName,String password);
	public int deleteEmployeeEntity(int employeeId);
	public TokenEntity getTokenModelByToken(String token);
	public List<UserRolesEntity> getAllUserRoles();
	public UserRolesEntity getUserRoleById(int userRoleId);
	//public List<EmployeeEntity> getAllEmployees(String token);
	public List<EmployeeModel> getAllEmployees(String token);
	public List<DepartmentEntity> getAllDepartment();
	public DepartmentEntity saveOrUpdateDepartment(DepartmentEntity department);
	public int deleteDepartmentEntity(int departmentId);
	public List<EmployeeTypesEntity> getAllEmployeeTypes();
	public EmployeeModel getEmployeeById(int employeeId);
	public EmployeeEntity getEmployeeByToken(String token);
	public List<EmployeeModel> getAllEmployeesByDept(int departmentId);
	public List<EmployeeModel> searchEmployee(String searchKeyWord, int departmentId);
	
	public DeputationModel saveDeputation(DeputationModel deputationModel);
	public DeputationModel updateDeputation(DeputationModel deputationModel);
	public List<DeputationModel> getDeputationListByDeptId(int departmentId);
	public List<EmployeeModel> getDeputedEmployeeListByDeptId(int departmentId);
	public int deleteDeputation(int deputationId);
	public DeputationModel getDeputationById(int deputationId);
	
	public AuthorizationEntity getAuthorizationByUserRole(int userRoleId);
	public AuthorizationEntity updateAuthorization(AuthorizationEntity entity);
	
	public int uploadEmployeeProfilePic(MultipartFile profilePic,int employeeId,String contextPath);
}
