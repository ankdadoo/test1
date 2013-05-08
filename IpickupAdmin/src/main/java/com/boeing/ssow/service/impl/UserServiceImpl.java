package com.boeing.ssow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boeing.ssow.dao.UserDao;
import com.boeing.ssow.model.User;
import com.boeing.ssow.service.UserService;

@Service
public class UserServiceImpl implements  UserService {
	
	@Autowired
	private  UserDao userDao;


	public List<User> getAllUserList() {

		return userDao.getAllUserList();
	}
	
	public List<User> getUserList ( String username ,  String firstname ,  String lastname) {
		return userDao.getUserList(username,  firstname ,  lastname);
	}



	
	public User validateUser ( String username , String password) {
		
		
		
		return  userDao.validateUser(username, password);
	}

//	public String addUser (String username, String firstname, String lastname, String password) {
//		
//		userDao.addUser(username, firstname, lastname,  password);
//		return "Success";
//	}
	
	public String addUser(User user) {
		userDao.addUser(user);
		return "Success";
	}

	public String updateUser(User user) {
		userDao.updateUser(user);
		return "Success";
	}
	
//	public boolean checkUserExist(String username)
	public boolean checkUserExistByUserName(String username)
	{
//		return userDao.checkUserExist(username);
		return userDao.checkUserExistByUserName(username);
	}


	public User getUser(String id) 
	{
		return userDao.getUser(id);
	}
	

	
}
