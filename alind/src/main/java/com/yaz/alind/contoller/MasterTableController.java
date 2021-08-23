package com.yaz.alind.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yaz.alind.model.ui.WorkStatusModel;
import com.yaz.alind.model.ui.WorkTypeModel;
import com.yaz.alind.service.MasterTableService;
import com.yaz.alind.service.UtilService;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class MasterTableController {

	private static final Logger logger = LoggerFactory.getLogger(MasterTableController.class);

	@Autowired
	MasterTableService masterTableService;
	@Autowired
	UtilService utilService;

	@RequestMapping(value="/masterTable/saveWorkStatus", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> saveWorkStatus(@RequestHeader("token") String token,
			@RequestBody WorkStatusModel workStatusModel) throws Exception {
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("saveOrUpdateProject,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				WorkStatusModel model = masterTableService.saveWorkStatus(workStatusModel);
				resultMap.put("workStatusModel", model);
				resultMap.put("status", "success");
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("saveWorkStatus, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/masterTable/updateWorkStatus", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> updateWorkStatus(@RequestHeader("token") String token,
			@RequestBody WorkStatusModel workStatusModel) throws Exception {
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("saveOrUpdateProject,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				WorkStatusModel model = masterTableService.updateWorkStatus(workStatusModel);
				resultMap.put("workStatusModel", model);
				resultMap.put("status", "success");
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("updateWorkStatus, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/masterTable/getAllWorkStatus", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getAllWorkStatus(@RequestHeader("token") String token,
			@RequestParam int status) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("getAllWorkStatus,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<WorkStatusModel> models= masterTableService.getAllWorkStatus(status);
				resultMap.put("workStatus", models);
				resultMap.put("status", "success");
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllWorkStatus, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/masterTable/getWorkStatusById", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getWorkStatusById(@RequestHeader("token") String token,
			@RequestParam int workStatusId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("getAllWorkStatus,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				WorkStatusModel model= masterTableService.getWorkStatusById(workStatusId);
				resultMap.put("workStatus", model);
				resultMap.put("status", "success");
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkStatusById, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/masterTable/deleteWorkStatus", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  deleteWorkStatus(@RequestHeader("token") String token,
			@RequestParam int workStatusId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("getAllWorkStatus,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				int value= masterTableService.deleteWorkStatus(workStatusId);
				if(value == 1){
					resultMap.put("value", value);
					resultMap.put("status", "success");
				}else{
					resultMap.put("value",value );
					resultMap.put("status", "failed");
				}
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("deleteWorkStatus, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/masterTable/saveWorkType", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> saveWorkType(@RequestHeader("token") String token,
			@RequestBody WorkTypeModel workTypeModel) throws Exception {
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("saveOrUpdateProject,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				WorkTypeModel model = masterTableService.saveWorkType(workTypeModel);
				resultMap.put("workTypeModel", model);
				resultMap.put("status", "success");
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("saveWorkType, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}


	@RequestMapping(value="/masterTable/updateWorkType", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> updateWorkType(@RequestHeader("token") String token,
			@RequestBody WorkTypeModel workTypeModel) throws Exception {
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("saveOrUpdateProject,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				WorkTypeModel model = masterTableService.updateWorkType(workTypeModel);
				resultMap.put("workTypeModel", model);
				resultMap.put("status", "success");
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("status", "failed");
			logger.error("updateWorkType, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/masterTable/getWorkTypeById", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getWorkTypeById(@RequestHeader("token") String token,
			@RequestParam int workTypeId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("getAllWorkStatus,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				WorkTypeModel model= masterTableService.getWorkTypeById(workTypeId);
				resultMap.put("workStatus", model);
				resultMap.put("status", "success");
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getWorkTypeById, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}
	
	@RequestMapping(value="/masterTable/deleteWorkType", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  deleteWorkType(@RequestHeader("token") String token,
			@RequestParam int workTypeId) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("getAllWorkStatus,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				int value= masterTableService.deleteWorkType(workTypeId);
				if(value == 1){
					resultMap.put("value", value);
					resultMap.put("status", "success");
				}else{
					resultMap.put("value",value );
					resultMap.put("status", "failed");
				}
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("deleteWorkType, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

	@RequestMapping(value="/masterTable/getAllWorkType", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getAllWorkType(@RequestHeader("token") String token,
			@RequestParam int status) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			//			System.out.println("getAllWorkStatus,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				List<WorkTypeModel> models= masterTableService.getAllWorkType(status);
				resultMap.put("workStatusList", models);
				resultMap.put("status", "success");
			}else{
				resultMap.put("status", "failed");
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAllWorkType, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

}
