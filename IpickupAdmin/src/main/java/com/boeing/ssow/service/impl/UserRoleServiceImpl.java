package com.boeing.ssow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boeing.ssow.dao.UserRoleDao;
import com.boeing.ssow.model.UserRole;
import com.boeing.ssow.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements  UserRoleService {
	
	@Autowired
	private  UserRoleDao userRoleDao;


	public List<UserRole> getUserRoleList() {

		return userRoleDao.getUserRoleList();
	}

	public UserRole getUserRole (String id) {
		
		return userRoleDao.getUserRole(id);

	}
	
	
}
