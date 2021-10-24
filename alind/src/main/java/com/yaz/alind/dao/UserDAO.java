package com.yaz.alind.dao;

import java.util.List;

import com.yaz.alind.entity.DepartmentEntity;
import com.yaz.alind.entity.DeputationEntity;
import com.yaz.alind.entity.DeputationHistoryEntity;
import com.yaz.alind.entity.EmployeeEntity;
import com.yaz.alind.entity.EmployeeTypesEntity;
import com.yaz.alind.entity.TokenEntity;
import com.yaz.alind.entity.UserRolesEntity;

public interface UserDAO {

	public TokenEntity saveOrUpdateToken(TokenEntity tokenModel);
	public TokenEntity getTokenModelByUserId(int userId);
	public TokenEntity getTokenModelByToken(String token);
	public EmployeeEntity saveOrUpdateEmployee(EmployeeEntity employee);
	public EmployeeEntity getAuthentication(String userName,String password);
	public List<EmployeeEntity> getAllEmployees();
	public List<EmployeeEntity> getAllEmployeesByDept(int departmentId);
	public List<UserRolesEntity> getAllUserRoles();
	public List<EmployeeTypesEntity> getAllEmployeeTypes();
	public List<DepartmentEntity> getAllDepartment();
	public UserRolesEntity getUserRoleById(int userRoleId);
	public DepartmentEntity saveOrUpdateDepartment(DepartmentEntity department);
	public DepartmentEntity getDepartmentById(int departmentId );
	public EmployeeEntity getLastEmployeeDetails();
	public EmployeeEntity getEmployeeById(int employeeId);
	
	public DeputationEntity saveDeputation(DeputationEntity deputation);
	public DeputationEntity updateDeputation(DeputationEntity deputation);
	public List<DeputationEntity> getDeputationListByDeptId(int departmentId);
	public DeputationEntity getDeputationById(int deputationId);
	
	public DeputationHistoryEntity saveDeputationHistory(DeputationHistoryEntity deputationHistory);
	
}
