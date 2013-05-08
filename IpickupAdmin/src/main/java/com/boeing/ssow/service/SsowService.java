package com.boeing.ssow.service;

import java.io.File;
import java.util.List;

import com.boeing.ssow.model.Acronyms;
import com.boeing.ssow.model.CheckList;
import com.boeing.ssow.model.Dictionary;
import com.boeing.ssow.model.JsonRules;
import com.boeing.ssow.model.Rules;
import com.boeing.ssow.model.Sdrl;
import com.boeing.ssow.model.Section;
import com.boeing.ssow.model.Ssow;
import com.boeing.ssow.model.SsowSearchCriteria;
import com.boeing.ssow.model.User;


public interface SsowService {


	public Rules getRuleById( String id );
	
	public List<Ssow> getSsowList ();
	
	public List<Ssow> getSsowList (User user);
	
	public List<Ssow> getSearchResults ( SsowSearchCriteria criteria);
	
	public  void save ( Object object );
	
	public void delete ( Object object);
	
	public Object get ( Class className , String pk ) ;


	public Rules getRuleByName( String name ) ;
	
	public List<Ssow> getSsowListBySsowNumer ( String ssowNumber);
	
	public List getStatuses ();
	
	public List getCheckListProgramXrefById ( String checkListId);
	
	public CheckList getCheckList ( String Id );
	
	public File processSsowGenerator (Ssow ssow);
	
	public List getRulesCriteriaXrefList ( String dependencyId);
	
	public List getDocumentTypes () ;
	
	public List getSsowCheckListXrefList ( String checkListId);
	
	
	public List getBasicQList ();
	
	public List getAdvancedQList ();
	
	public List getRules();
	
	
	public List getRulesCriteriaXrefById ( String criteriaId);
	
	public List getCheckList () ;
	
	public List<Section> getSectionByRuleId( String ruleId);
	
	
	public List getSdrlEditableList ();
	
	public JsonRules getJsonRules ( Rules rulesObj);
	
	public CheckList getCheckListByNumber ( String number );
	
	
	public String getNextRuleNumber();
	
	public List<Sdrl> getSdrlByRuleId( String ruleId) ;

	public List<Acronyms> getAcronymsByRuleId( String ruleId) ;


	public List<Dictionary> getDictionaryByRuleId( String ruleId);
	
	
	public List getSdrlList () ;


	public List getAcronymsList () ;



	public List getDictionaryList ();

}
