package com.boeing.ssow.dao;

import java.util.List;

import com.boeing.ssow.model.Section;
import com.boeing.ssow.model.SectionTreeXref;



public interface SectionDao {

	public Object get(Class className, String pk);

	public void save ( Object object );
	
	public int getNextOrderNumber();
	
	public String getNextHeaderNumber();
	
	public List<Section> getChildrenSections(String pk);

	public List<SectionTreeXref> getParentXrefs(String pk);

	public List getMainSections();

	public List<Section> getMainSubSections(String sectionNumber);
	
	public List<Section> getSectionsByRuleId( String ruleId);

}
