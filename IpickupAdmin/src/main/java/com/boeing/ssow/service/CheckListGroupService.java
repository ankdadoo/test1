package com.boeing.ssow.service;

import java.util.List;

import com.boeing.ssow.model.CheckListGroup;


public interface CheckListGroupService {

	public List<CheckListGroup> getAllCheckListGroups ();	
	
	public Object get(Class className, String pk);
	
	public void save ( Object object );
	
//	public String getNextItemNumber();
	
}
