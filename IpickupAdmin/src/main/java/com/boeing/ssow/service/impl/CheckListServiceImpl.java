package com.boeing.ssow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boeing.ssow.dao.CheckListDao;
import com.boeing.ssow.model.CheckList;
import com.boeing.ssow.service.CheckListService;

@Service
public class CheckListServiceImpl implements CheckListService {
	
	@Autowired
	private  CheckListDao checklistDao;


	public List<CheckList> getAllCheckList () {
	
		return checklistDao.getAllCheckList();
	}

	public Object get(Class className, String pk) {
		
		return checklistDao.get(className, pk);
	}
	
	public void save ( Object object ) {
		
		checklistDao.save(object);
	}
	
	public String getNextItemNumber() {
	
	 	return checklistDao.getNextItemNumber();
	}

}
