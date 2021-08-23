package com.yaz.alind.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="alind_t_user_roles")
public class UserRolesEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "user_role_id", unique = true, nullable = false)
	private int userRoleId;
	@Column(name = "role_name")
	private String roleName;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}
	

}
