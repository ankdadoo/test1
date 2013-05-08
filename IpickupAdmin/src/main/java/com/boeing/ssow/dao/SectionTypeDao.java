package com.boeing.ssow.dao;

import java.util.List;

import com.boeing.ssow.model.SectionType;



public interface SectionTypeDao {

	public List<SectionType> getSectionTypeList();
	
	public SectionType getSectionType (String id);

}
