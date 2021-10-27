package com.yaz.alind.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.yaz.alind.entity.DepartmentEntity;
import com.yaz.alind.entity.EmployeeEntity;
import com.yaz.alind.entity.EmployeeTypesEntity;
import com.yaz.alind.entity.UserRolesEntity;
import com.yaz.alind.model.ui.DeputationModel;
import com.yaz.alind.service.DashBoardService;
import com.yaz.alind.service.UserService;
import com.yaz.alind.service.UtilService;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;
	@Autowired
	UtilService utilService;
	@Autowired
	private ServletContext context;
	@Autowired
	private DashBoardService dashBoardService;

	@RequestMapping(value="/user/getAuthentication", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> getAuthentication(@RequestBody EmployeeEntity employee) throws Exception {

		Map<String,Object> resultMap = null;
		try{
			System.out.println("UserController,getAuthentication,userName: "+employee.getUserName()+", password: "+employee.getPassword());
			resultMap = new HashMap<String,Object>();
			EmployeeEntity emp = userService.getAuthentication(employee.getUserName(), employee.getPassword());
			resultMap.put("loginDetails", emp);
			//			if(emp!=null){
			//				if(emp.getUserRoleId()==1){
			//					AdminDashBoardModel dashBoard = dashBoardService.getAdminDashBoardModel();
			//					resultMap.put("adminDashBoard", dashBoard);
			//				}
			//			}
			resultMap.put("token", emp.getToken());
			System.out.println("UserController,getAuthentication,loginDetails: "+employee+", token: "+emp.getToken());
			resultMap.put("status", "success");
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("getAuthentication, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}


	@RequestMapping(value="/user/saveOrUpdateEmployee", method = RequestMethod.POST,
			consumes = { "multipart/form-data" })
	public ResponseEntity<Map<String,Object>>  saveOrUpdateEmployee(
			@RequestBody EmployeeEntity employee,
			@RequestParam(value = "profileImage", required = false)MultipartFile profilePic,
			@RequestHeader("token") String token) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("saveOrUpdateUser,token: "+token+", User ID: "+employee.getEmployeeId());
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				String contextPath = context.getRealPath(""); 
				EmployeeEntity emp= userService.saveOrUpdateEmployee(employee,profilePic,contextPath);
				resultMap.put("user", emp);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("saveOrUpdateEmployee, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/user/getAllUserRoles", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getAllUserRoles(@RequestHeader("token") String token) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getAllUserRoles,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<UserRolesEntity> userRoles= userService.getAllUserRoles();
				resultMap.put("userRolesList", userRoles);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllUserRoles, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/user/getUserRoleById", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>>  getUserRoleById(@RequestParam String userRoleId,@RequestHeader("token") String token) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getUserRoleById,token: "+token+",userRoleId: "+userRoleId);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				UserRolesEntity userRoles= userService.getUserRoleById(Integer.parseInt(userRoleId));
				resultMap.put("userRoles", userRoles);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getUserRoleById, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/user/getAllEmployees", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getAllEmployees(@RequestHeader("token") String token) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getAllEmployees,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<EmployeeEntity> employees= userService.getAllEmployees(token);
				resultMap.put("employees", employees);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllEmployees, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}


	@RequestMapping(value="/user/saveOrUpdateDepartment", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>>  saveOrUpdateDepartment(@RequestHeader("token") String token,@RequestBody DepartmentEntity department) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("saveOrUpdateDepartment,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				DepartmentEntity dept= userService.saveOrUpdateDepartment(department);
				resultMap.put("department", dept);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("saveOrUpdateDepartment, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/user/getAllEmployeeTypes", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getAllEmployeeTypes(@RequestHeader("token") String token) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//System.out.println("getAllEmployeeTypes,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<EmployeeTypesEntity> employeeTypes= userService.getAllEmployeeTypes();
				resultMap.put("employeeTypes", employeeTypes);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllEmployeeTypes, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/user/getAllDepartment", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getAllDepartment(@RequestHeader("token") String token) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getAllEmployeeTypes,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<DepartmentEntity> departments= userService.getAllDepartment();
				resultMap.put("departments", departments);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllDepartment, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}


	@RequestMapping(value="/user/getEmployeeById", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getEmployeeById(@RequestHeader("token") String token,
			@RequestParam String employeeId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getEmployeeById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				EmployeeEntity employee= userService.getEmployeeById(Integer.parseInt(employeeId));
				resultMap.put("employee", employee);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getEmployeeById, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/user/deleteEmployee", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  deleteEmployeeEntity(@RequestHeader("token") String token,
			@RequestParam int employeeId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("deleteEmployeeEntity,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				int value= userService.deleteEmployeeEntity(employeeId);
				if(value == 1){
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
			logger.error("deleteEmployeeEntity, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/user/deleteDepartment", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  deleteDepartmentEntity(@RequestHeader("token") String token,
			@RequestParam int departmentId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getEmployeeById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				int value= userService.deleteDepartmentEntity(departmentId);
				if(value == 1){
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
			logger.error("deleteDepartmentEntity, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/user/saveDeputation", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> saveDeputation(@RequestHeader("token") String token,
			@RequestBody DeputationModel deputationModel) throws Exception {
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				DeputationModel model = userService.saveDeputation(deputationModel);
				if(model != null){
					resultMap.put("model", model);
					resultMap.put("status", "success");
				}else{
					resultMap.put("status", "failed");
					return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
				}
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("saveDeputation, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/user/updateDeputation", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> updateDeputation(@RequestHeader("token") String token,
			@RequestBody DeputationModel deputationModel) throws Exception {
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				DeputationModel model = userService.updateDeputation(deputationModel);

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
			logger.error("updateDeputation, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/user/getDeputationListByDeptId/{departmentId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getDeputationListByDeptId(@RequestHeader("token") String token,
			@PathVariable("departmentId") int departmentId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getEmployeeById,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<DeputationModel> models= userService.getDeputationListByDeptId(departmentId);
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
			logger.error("getDeputationListByDeptId, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/user/deleteDeputation/{deputationId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  deleteDeputation(@RequestHeader("token") String token,
			@PathVariable("deputationId") int departmentId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("deleteDeputation,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				int value= userService.deleteDeputation(departmentId);
				if(value == 1){
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
			logger.error("deleteDeputation, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

}
