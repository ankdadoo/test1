package com.boeing.ssow.dao;

import java.util.List;

import com.boeing.ssow.model.CheckListGroup;



public interface CheckListGroupDao {

	public List<CheckListGroup> getAllCheckListGroups ();
	
	public Object get(Class className, String pk);
	
	public void save ( Object object );
	
//	public String getNextItemNumber();
	
}
