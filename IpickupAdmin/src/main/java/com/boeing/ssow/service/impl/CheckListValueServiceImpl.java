package com.boeing.ssow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boeing.ssow.dao.CheckListValueDao;
import com.boeing.ssow.model.CheckListValue;
import com.boeing.ssow.service.CheckListValueService;

@Service
public class CheckListValueServiceImpl implements CheckListValueService {
	
	@Autowired
	private  CheckListValueDao checkListValueDao;


	public List<CheckListValue> getAllCheckListValues() {

		return checkListValueDao.getAllCheckListValues();
	}
	
	
}
