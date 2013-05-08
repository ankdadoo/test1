package com.boeing.ssow.dao;

import java.util.List;

import com.boeing.ssow.model.UserRole;



public interface UserRoleDao {

	public List<UserRole> getUserRoleList();
	
	public UserRole getUserRole (String id);

}
