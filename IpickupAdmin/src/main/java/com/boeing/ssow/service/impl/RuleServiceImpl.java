package com.boeing.ssow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boeing.ssow.dao.RuleDao;
import com.boeing.ssow.model.Rules;
import com.boeing.ssow.service.RuleService;

@Service
public class RuleServiceImpl implements RuleService {
	
	@Autowired
	private  RuleDao ruleDao;


	public List<Rules> getAllRules() {
	
		return ruleDao.getAllRules();
}

	public  void save ( Object object ) {
		
		ruleDao.save(object);
	}
	
	
	public Object get ( Class className , String pk ) {
		
		
		return ruleDao.get(className, pk);
	}

	
}
