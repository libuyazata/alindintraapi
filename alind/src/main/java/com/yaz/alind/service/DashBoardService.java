package com.yaz.alind.service;

import com.yaz.alind.entity.AdminDashBoardModel;

public interface DashBoardService {
	
	public AdminDashBoardModel getAdminDashBoardModel(String token);
	public AdminDashBoardModel getDashBoardByDepartId(int deptId);

	
}
