package com.boeing.ssow.dao;

import java.util.List;

import org.hibernate.Query;

import com.boeing.ssow.model.Acronyms;
import com.boeing.ssow.model.Dictionary;
import com.boeing.ssow.model.ProgramFile;
import com.boeing.ssow.model.Sdrl;
import com.boeing.ssow.model.Section;
import com.boeing.ssow.model.Ssow;
import com.boeing.ssow.model.SsowSearchCriteria;
import com.boeing.ssow.model.User;



public interface SsowDao {

	public List<Ssow> getSsowList ();
	
	public List<Ssow> getSearchResults ( SsowSearchCriteria criteria);
	
	public void save ( Object object );
	
	public Object get ( Class className , String pk );
	
	public List<Ssow> getSsowList (User user);
	
	public List getRulesCriteriaXrefById ( String criteriaId);
	
	public List getStatuses ();

	public List getCheckListProgramXrefById ( String checkListId);
	
	public List getRules ();
	
	public List getDocumentTypes () ;
	
	public void delete ( Object object);
	
	public List getBasicQList ();
	
	public List getAdvancedQList ();
	
	public List getCheckList ();
	
	
	public List<Section> getSectionByRuleId( String ruleId);
	
	public List<ProgramFile> getProgramFileList( String programName) ;
	
	public List<Ssow> getSsowListBySsowNumer ( String ssowNumber);
	
	public List getSdrlList () ;


	public List getAcronymsList () ;

	public List getSdrlEditableList ();

	public List getDictionaryList ();
	
	public List getSsowCheckListXrefList ( String checkListId);
	
	
	public String getNextRuleNumber();
	
	public List getRulesCriteriaXrefList ( String dependencyId);
	
	public List<Sdrl> getSdrlByRuleId( String ruleId) ;

	public List<Acronyms> getAcronymsByRuleId( String ruleId) ;


	public List<Dictionary> getDictionaryByRuleId( String ruleId);

	
}
