package com.boeing.ssow.dao;

import java.util.List;

import com.boeing.ssow.model.CheckList;



public interface CheckListDao {

	public List<CheckList> getAllCheckList ();
	
	public Object get(Class className, String pk);
	
	public void save ( Object object );
	
	public String getNextItemNumber();
	
}
