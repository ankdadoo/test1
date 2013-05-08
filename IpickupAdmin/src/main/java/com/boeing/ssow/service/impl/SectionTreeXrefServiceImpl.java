package com.boeing.ssow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boeing.ssow.dao.SectionTreeXrefDao;
import com.boeing.ssow.service.SectionTreeXrefService;

@Service
public class SectionTreeXrefServiceImpl implements SectionTreeXrefService {
	
	@Autowired
	private  SectionTreeXrefDao sectiontreexrefDao;


	public  void save ( Object object ) {
		
		sectiontreexrefDao.save(object);
	}
	
	
	public Object get ( Class className , String pk ) {
		
		
		return sectiontreexrefDao.get(className, pk);
	}

	
}
