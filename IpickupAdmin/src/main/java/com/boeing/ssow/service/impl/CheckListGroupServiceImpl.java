package com.boeing.ssow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boeing.ssow.dao.CheckListGroupDao;
import com.boeing.ssow.model.CheckListGroup;
import com.boeing.ssow.service.CheckListGroupService;

@Service
public class CheckListGroupServiceImpl implements CheckListGroupService {
	
	@Autowired
	private  CheckListGroupDao checklistgroupDao;


	public List<CheckListGroup> getAllCheckListGroups () {
	
		return checklistgroupDao.getAllCheckListGroups();
	}

	public Object get(Class className, String pk) {
		
		return checklistgroupDao.get(className, pk);
	}
	
	public void save ( Object object ) {
		
		checklistgroupDao.save(object);
	}
	
//	public String getNextItemNumber() {
//	
//	 	return checklistDao.getNextItemNumber();
//	}

}
