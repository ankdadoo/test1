package com.boeing.ssow.service;

import java.util.List;

import com.boeing.ssow.model.UserRole;


public interface UserRoleService {

	public List<UserRole> getUserRoleList ();

	public UserRole getUserRole (String id);
	
}
