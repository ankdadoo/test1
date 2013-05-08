package com.boeing.ssow.dao;

import java.util.List;

import com.boeing.ssow.model.User;



public interface UserDao {

	public List<User> getAllUserList ();
	
	public List<User> getUserList ( String username ,  String firstname ,  String lastname);
	
	public User validateUser ( String username , String password);
	
//	public String addUser (String username, String firstname, String lastname, String password);
	public String addUser (User user);
	
	public String updateUser(User user);
	
//	public boolean checkUserExist (String username);
	public boolean checkUserExistByUserName (String username);
	
	public User getUser(String id); 


}
