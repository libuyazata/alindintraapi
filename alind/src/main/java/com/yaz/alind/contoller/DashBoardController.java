package com.yaz.alind.contoller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yaz.alind.entity.AdminDashBoardModel;
import com.yaz.alind.service.DashBoardService;
import com.yaz.alind.service.UtilService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class DashBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(DashBoardController.class);
	
	@Autowired
	UtilService utilService;
	@Autowired
	DashBoardService dashBoardService;
	
	
	@RequestMapping(value="/dashBoard/getAdminDashBoard", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>>  getAdminDashBoard(@RequestHeader("token") String token) throws Exception{
		Map<String,Object> resultMap = null;
		boolean tokenStatus = false;
		try{
			resultMap = new HashMap<String,Object>();
			System.out.println("getAdminDashBoard,token: "+token);
			tokenStatus = utilService.evaluateToken(token);
			if(tokenStatus){
				AdminDashBoardModel dashBoard = dashBoardService.getAdminDashBoardModel(token);
				resultMap.put("adminDashBoard", dashBoard);
			}else{
				return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.UNAUTHORIZED);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getAdminDashBoard, "+e.getMessage());
			return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK);
	}

}
