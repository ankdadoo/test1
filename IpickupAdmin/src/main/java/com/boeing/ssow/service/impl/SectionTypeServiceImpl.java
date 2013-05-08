package com.boeing.ssow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boeing.ssow.dao.SectionTypeDao;
import com.boeing.ssow.model.SectionType;
import com.boeing.ssow.service.SectionTypeService;

@Service
public class SectionTypeServiceImpl implements  SectionTypeService {
	
	@Autowired
	private  SectionTypeDao sectionTypeDao;


	public List<SectionType> getSectionTypeList() {

		return sectionTypeDao.getSectionTypeList();
	}

	public SectionType getSectionType (String id) {
		
		return sectionTypeDao.getSectionType(id);

	}
	
	
}
