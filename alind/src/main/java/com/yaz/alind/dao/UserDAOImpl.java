package com.yaz.alind.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yaz.alind.entity.AuthorizationEntity;
import com.yaz.alind.entity.DepartmentEntity;
import com.yaz.alind.entity.DeputationEntity;
import com.yaz.alind.entity.DeputationHistoryEntity;
import com.yaz.alind.entity.EmployeeEntity;
import com.yaz.alind.entity.EmployeeTypesEntity;
import com.yaz.alind.entity.TokenEntity;
import com.yaz.alind.entity.UserRolesEntity;
import com.yaz.alind.model.ui.EmployeeModel;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	@Transactional
	public TokenEntity saveOrUpdateToken(TokenEntity tokenModel) {
		try{
			TokenEntity tModel = getTokenModelByUserId(tokenModel.getUserId());
//			System.out.println("DAO,saveOrUpdateToken,tModel: "+tModel);
			if(tModel != null){
				this.sessionFactory.getCurrentSession().merge(tokenModel);
			}else{
				this.sessionFactory.getCurrentSession().save(tokenModel);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateToken: "+e.getMessage());
		}
		return tokenModel;
	}


	@Override
	@Transactional
	public TokenEntity getTokenModelByUserId(int userId) {
		TokenEntity tokenModel = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(TokenEntity.class);
			cr.add(Restrictions.eq("userId", userId));
			List<TokenEntity> list = cr.list();
			if(list.size() > 0){
				tokenModel = list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getTokenModelByUserId: "+e.getMessage());
		}
		return tokenModel;
	}

/**
	@Override
	@Transactional
	public EmployeeEntity saveOrUpdateEmployee(EmployeeEntity employee) {
		EmployeeEntity emp = null;
		try{
			this.sessionFactory.getCurrentSession().saveOrUpdate(employee);
			System.out.println("DAO, saveOrUpdateUser, EmployeeId: "+employee.getEmployeeId());
			emp = employee;
			System.out.println("DAO, saveOrUpdateUser, lastname: "+emp.getLastName());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateUser: "+e.getMessage());
		}
		return emp;
	}
	**/
	@Override
	public EmployeeEntity saveEmployee(EmployeeEntity employee){
		EmployeeEntity emp = null;
		try{
			this.sessionFactory.getCurrentSession().save(employee);
			System.out.println("DAO, saveEmployee, EmployeeId: "+employee.getEmployeeId());
			emp = employee;
			System.out.println("DAO, saveEmployee, lastname: "+emp.getLastName());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveEmployee: "+e.getMessage());
		}
		return emp;
	}

	@Override
	public EmployeeEntity updateEmployee(EmployeeEntity employee){
		EmployeeEntity emp = null;
		try{
			this.sessionFactory.getCurrentSession().update(employee);
//			this.sessionFactory.getSessionFactory().getCurrentSession().update(employee);
			System.out.println("DAO, updateEmployee, EmployeeId: "+employee.getEmployeeId());
			emp = employee;
			System.out.println("DAO, updateEmployee, lastname: "+emp.getLastName());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateEmployee: "+e.getMessage());
		}
		return emp;
	}

	@Override
	@Transactional
	public EmployeeEntity getAuthentication(String userName,String password) {
		EmployeeEntity employee= null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(EmployeeEntity.class);
			cr.add(Restrictions.eq("userName", userName));
			cr.add(Restrictions.eq("password", password));
			List<EmployeeEntity> list = cr.list();
			System.out.println("DAO,getAuthentication, user size: "+list.size());
			if(list.size() > 0){
				employee = list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAuthentication: "+e.getMessage());
		}
		return employee;
	}


	@Override
	@Transactional
	public TokenEntity getTokenModelByToken(String token) {
		TokenEntity tokenModel = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(TokenEntity.class);
			cr.add(Restrictions.eq("token", token));
			List<TokenEntity> list = cr.list();
			tokenModel = list.get(0);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getTokenModelByToken: "+e.getMessage());
		}
		return tokenModel;
	}


	@Override
	@Transactional
	public List<UserRolesEntity> getAllUserRoles() {
		List<UserRolesEntity> userRoles = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(UserRolesEntity.class);
			userRoles = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllUserRoles: "+e.getMessage());
		}
		return userRoles;
	}


	@Override
	@Transactional
	public UserRolesEntity getUserRoleById(int userRoleId ) {
		UserRolesEntity userRoles = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(UserRolesEntity.class);
			cr.add(Restrictions.eq("userRoleId", userRoleId));
			List<UserRolesEntity> uList = cr.list();
			if(uList != null){
				userRoles = uList.get(0);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getUserRoleById: "+e.getMessage());
		}
		return userRoles;
	}


	@Override
	@Transactional
	public List<EmployeeEntity> getAllEmployees() {
		List<EmployeeEntity> employees = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(EmployeeEntity.class);
			cr.add(Restrictions.eq("isActive",1));
			employees = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllEmployees: "+e.getMessage());
		}
		return employees;
	}


	@Override
	@Transactional
	public List<EmployeeEntity> getAllEmployeesByDept(int departmentId) {
		List<EmployeeEntity> employees = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(EmployeeEntity.class);
			if(departmentId > 0){
				cr.add(Restrictions.eq("departmentId",departmentId));
			}
			cr.addOrder(Order.asc("employeeId"));
			employees = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllEmployeesByDept: "+e.getMessage());
		}
		return employees;
	}
	
	@Override
	@Transactional
	public List<EmployeeEntity> searchEmployee(String searchKeyWord, int departmentId){
		List<EmployeeEntity> employees = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(EmployeeEntity.class);
			if(departmentId > 0){
				cr.add(Restrictions.eq("departmentId",departmentId));
			}
			cr.addOrder(Order.asc("employeeId"));
			Disjunction disj = Restrictions.disjunction();
			if(searchKeyWord != null){
				disj.add(Restrictions.ilike("emailId", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("empCode", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("firstName", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("gender", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("lastName", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("primaryMobileNo", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("secondaryEmailId", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("secondaryMobileNo", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("userName", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("nationality", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("passportNo", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("accommodationLocation", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("insurancePolicyNo", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("nativeAddress", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("emergencyContactName", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("emergencyContactPhone", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("relationship", searchKeyWord,MatchMode.ANYWHERE));
				disj.add(Restrictions.ilike("otherDetails", searchKeyWord,MatchMode.ANYWHERE));
			}
			cr.add(disj);
			employees = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("searchEmployee: "+e.getMessage());
		}
		return employees;
	}

	@Override
	@Transactional
	public DepartmentEntity saveOrUpdateDepartment(DepartmentEntity department) {
		DepartmentEntity dept = null;
		try{
			this.sessionFactory.getCurrentSession().saveOrUpdate(department);
			dept = department;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateDepartment: "+e.getMessage());
		}
		return dept;
	}


	@Override
	@Transactional
	public List<DepartmentEntity> getAllDepartment() {
		List<DepartmentEntity> departments = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(DepartmentEntity.class);
			cr.add(Restrictions.eq("isActive",1));
			departments = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDepartment: "+e.getMessage());
		}
		return departments;
	}


	@Override
	@Transactional
	public EmployeeEntity getLastEmployeeDetails() {
		EmployeeEntity employee = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(EmployeeEntity.class);
			cr.addOrder(Order.desc("employeeId"));
			cr.setMaxResults(1);
			employee = (EmployeeEntity) cr.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getLastEmployeeDetails: "+e.getMessage());
		}
		return employee;
	}


	@Override
	@Transactional
	public List<EmployeeTypesEntity> getAllEmployeeTypes() {
		List<EmployeeTypesEntity> employeeTypes = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(EmployeeTypesEntity.class);
			employeeTypes = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllEmployeeTypes: "+e.getMessage());
		}
		return employeeTypes;
	}


	@Override
	@Transactional
	public EmployeeEntity getEmployeeById(int employeeId) {
		EmployeeEntity employee = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(EmployeeEntity.class);
			cr.add(Restrictions.eq("employeeId",employeeId));
			employee = (EmployeeEntity) cr.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getEmployeeById: "+e.getMessage());
		}
		return employee;
	}


	@Override
	@Transactional
	public DepartmentEntity getDepartmentById(int departmentId) {
		DepartmentEntity department = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(DepartmentEntity.class);
			cr.add(Restrictions.eq("departmentId",departmentId));
			department = (DepartmentEntity) cr.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDepartmentById: "+e.getMessage());
		}
		return department;
	}


	@Override
	public DeputationEntity saveDeputation(DeputationEntity deputation) {
		DeputationEntity entity = null;
		try{
			this.sessionFactory.getCurrentSession().save(deputation);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveDeputation: "+e.getMessage());
		}finally{
			entity = deputation;
		}
		return entity;
	}


	@Override
	public DeputationEntity updateDeputation(DeputationEntity deputation) {
		DeputationEntity entity = null;
		try{
			this.sessionFactory.getCurrentSession().update(deputation);
			entity = deputation;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateDeputation: "+e.getMessage());
		}
		return entity;
	}


	@Override
	public List<DeputationEntity> getDeputationListByDeptId(int departmentId) {
		List<DeputationEntity> deputationEntities = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(DeputationEntity.class);
			if(departmentId > 0){
				cr.add(Restrictions.eq("deputedDepartmentId",departmentId));
			}
			cr.add(Restrictions.eq("status", 1));
			cr.addOrder(Order.desc("createdOn"));
			deputationEntities = cr.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDeputationListByDeptId: "+e.getMessage());
		}
		return deputationEntities;
	}


	@Override
	public DeputationEntity getDeputationById(int deputationId) {
		DeputationEntity entity = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(DeputationEntity.class);
			cr.add(Restrictions.eq("deputationId",deputationId));
			entity = (DeputationEntity) cr.list().get(0);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDeputationById: "+e.getMessage());
		}
		
		return entity;
	}


	@Override
	public DeputationHistoryEntity saveDeputationHistory(
			DeputationHistoryEntity deputationHistory) {
		DeputationHistoryEntity depEntity = null;
		try{
			this.sessionFactory.getCurrentSession().save(deputationHistory);
			depEntity = deputationHistory;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveDeputationHistory: "+e.getMessage());
		}
		return depEntity;
	}


	@Override
	public AuthorizationEntity getAuthorizationByUserRole(int userRoleId) {
		AuthorizationEntity entity = null;
		try{
			Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(AuthorizationEntity.class);
			cr.add(Restrictions.eq("userRoleId",userRoleId));
			entity = (AuthorizationEntity) cr.list().get(0);
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
			this.sessionFactory.getCurrentSession().update(entity);
			authorizationEntity = entity;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("updateAuthorization: "+e.getMessage());
		}
		return authorizationEntity;
	}

}
