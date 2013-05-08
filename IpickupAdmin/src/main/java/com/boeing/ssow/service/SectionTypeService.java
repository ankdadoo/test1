package com.boeing.ssow.service;

import java.util.List;

import com.boeing.ssow.model.SectionType;


public interface SectionTypeService {

	public List<SectionType> getSectionTypeList();
	
	public SectionType getSectionType (String id);
	
}
