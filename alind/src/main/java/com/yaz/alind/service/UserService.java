package com.yaz.alind.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yaz.alind.entity.DepartmentEntity;
import com.yaz.alind.entity.EmployeeEntity;
import com.yaz.alind.entity.EmployeeTypesEntity;
import com.yaz.alind.entity.TokenEntity;
import com.yaz.alind.entity.UserRolesEntity;

public interface UserService {

	public TokenEntity saveOrUpdateToken(TokenEntity tokenModel);
	public TokenEntity getTokenModelByUserId(int userId);
	public EmployeeEntity saveOrUpdateEmployee(EmployeeEntity employee,MultipartFile profilePic,String contextPath);
	public EmployeeEntity getAuthentication(String userName,String password);
	public TokenEntity getTokenModelByToken(String token);
	public List<UserRolesEntity> getAllUserRoles();
	public UserRolesEntity getUserRoleById(int userRoleId);
	public List<EmployeeEntity> getAllEmployees(String token);
	public List<DepartmentEntity> getAllDepartment();
	public DepartmentEntity saveOrUpdateDepartment(DepartmentEntity department);
	public List<EmployeeTypesEntity> getAllEmployeeTypes();
	public EmployeeEntity getEmployeeById(int employeeId);
	public EmployeeEntity getEmployeeByToken(String token);
}
