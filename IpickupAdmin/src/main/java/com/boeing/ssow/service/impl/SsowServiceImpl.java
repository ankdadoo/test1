package com.boeing.ssow.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.boeing.ssow.dao.AcronymsDao;
import com.boeing.ssow.dao.SsowDao;
import com.boeing.ssow.model.Acronyms;
import com.boeing.ssow.model.CheckList;
import com.boeing.ssow.model.Dictionary;
import com.boeing.ssow.model.JsonRules;
import com.boeing.ssow.model.ProgramFile;
import com.boeing.ssow.model.Rules;
import com.boeing.ssow.model.RulesCriteriaXref;
import com.boeing.ssow.model.Sdrl;
import com.boeing.ssow.model.Section;
import com.boeing.ssow.model.SectionType;
import com.boeing.ssow.model.SectionW;
import com.boeing.ssow.model.Ssow;
import com.boeing.ssow.model.SsowCheckListXref;
import com.boeing.ssow.model.SsowSearchCriteria;
import com.boeing.ssow.model.User;
import com.boeing.ssow.service.AcronymsService;
import com.boeing.ssow.service.SectionService;
import com.boeing.ssow.service.SsowService;
import com.boeing.ssow.utils.SsowCacheUtil;
import com.boeing.ssow.utils.SsowWordUtil;
import com.opensymphony.oscache.base.NeedsRefreshException;

@Service
public class SsowServiceImpl implements  SsowService {
	
	@Autowired
	private  SsowDao  ssowDao;


	@Autowired
	private SectionService sectionService;
	public List<Ssow> getSsowList () {
		
		
		
		return ssowDao.getSsowList();
	}
	
	
	
	public List<Ssow> getSearchResults ( SsowSearchCriteria criteria) {
		
		return ssowDao.getSearchResults(criteria);
	}
	
	
	public  void save ( Object object ) {
		
		ssowDao.save(object);
	}
	
	
	public List getCheckListProgramXrefById ( String checkListId) {
		
		return ssowDao.getCheckListProgramXrefById(checkListId);
	}
	
	public Object get ( Class className , String pk ) {
		
		
		return ssowDao.get(className, pk);
	}

	public List getRulesCriteriaXrefById ( String criteriaId) {
		
		
		return ssowDao.getRulesCriteriaXrefById(criteriaId);
	}
	
	
	public String getNextRuleNumber() {
		
		return ssowDao.getNextRuleNumber();
	}
	
	public CheckList getCheckList ( String Id ) {
		
		List list = this.getCheckList();
		
		CheckList returnChk = null ; 
		
		for ( int i = 0 ; i < list.size(); i++ ) {
			
			CheckList chk = (CheckList) list.get(i);
			
			if ( chk.getId().equals(Id)) {
				returnChk = chk ; 
				break ;
			}
			
		}
		
		//System.out.println("****returned check list item " + returnChk.getItemNumber());
		return returnChk;
	}
	
	
	public CheckList getCheckListByNumber ( String number ) {
		
		List list = this.getCheckList();
		
		CheckList returnChk = null ; 
		
		for ( int i = 0 ; i < list.size(); i++ ) {
			
			CheckList chk = (CheckList) list.get(i);
			
			if ( chk.getItemNumber().equals(number)) {
				returnChk = chk ; 
				break ;
			}
			
		}
		
		//System.out.println("****returned check list item " + returnChk.getItemNumber());
		return returnChk;
	}
	public List getRules() {
		
		
		
		List returnList = null ; 
		String cacheKey = SsowCacheUtil.getRulesListKey();
		 
		 try {
			
			returnList = (List) (SsowCacheUtil.getFromCache(cacheKey));
		 } catch (NeedsRefreshException e) {
			// Need to refresh the cache.
		 }
		 
		 
		 if ( returnList == null )  {
			 
			 
			 returnList = ssowDao.getRules();
			 
			 SsowCacheUtil.putInCache(SsowCacheUtil.getRulesListKey(), returnList);
		 }
		
		return returnList;
	}
	
	
	public Rules getRuleByName( String name ) {
		
		List list = this.getRules();
		
		Rules returnChk = null ; 
		
		for ( int i = 0 ; i < list.size(); i++ ) {
			
			Rules chk = (Rules) list.get(i);
			
			if ( chk.getName().equals(name)) {
				returnChk = chk ; 
				break ;
			}
			
		}
		
		//System.out.println("****returned check list item " + returnChk.getItemNumber());
		return returnChk;
		
		
	}
	
	
	
public Rules getRuleById( String id ) {
		
		List list = this.getRules();
		
		Rules returnChk = null ; 
		
		for ( int i = 0 ; i < list.size(); i++ ) {
			
			Rules chk = (Rules) list.get(i);
			
			if ( chk.getId().equals(id)) {
				returnChk = chk ; 
				break ;
			}
			
		}
		
		//System.out.println("****returned check list item " + returnChk.getItemNumber());
		return returnChk;
		
		
	}
	public List<Ssow> getSsowList (User user) {
		
		return ssowDao.getSsowList(user);
	}
	
	
	public List<Section> getSectionByRuleId( String ruleId) {
		
		return ssowDao.getSectionByRuleId(ruleId);
	}
	
	public List getStatuses () {
		
		
		
		
		List returnList = null ; 
		String cacheKey = SsowCacheUtil.getStatusesListKey();
		 
		 try {
			
			returnList = (List) (SsowCacheUtil.getFromCache(cacheKey));
		 } catch (NeedsRefreshException e) {
			// Need to refresh the cache.
		 }
		 
		 
		 if ( returnList == null )  {
			 
			 
			 returnList = ssowDao.getStatuses();
			 
			 SsowCacheUtil.putInCache(SsowCacheUtil.getStatusesListKey(), returnList);
		 }
		
		return returnList;
		
		//return ssowDao.getStatuses();
	}
	
	
	public List getDocumentTypes ()  {
		
		
		
		
		List returnList = null ; 
		String cacheKey = SsowCacheUtil.getDocumentTypesListKey();
		 
		 try {
			
			returnList = (List) (SsowCacheUtil.getFromCache(cacheKey));
		 } catch (NeedsRefreshException e) {
			// Need to refresh the cache.
		 }
		 
		 
		 if ( returnList == null )  {
			 
			 
			 returnList = ssowDao.getDocumentTypes();
			 
			 SsowCacheUtil.putInCache(SsowCacheUtil.getDocumentTypesListKey(), returnList);
		 }
		
		return returnList;
		//return ssowDao.getDocumentTypes();
	}
	
	
	public List getBasicQList () {
		
		
		
		List returnList = null ; 
		String cacheKey = SsowCacheUtil.getCheckListBasicQKey();
		 
		 try {
			
			returnList = (List) (SsowCacheUtil.getFromCache(cacheKey));
		 } catch (NeedsRefreshException e) {
			// Need to refresh the cache.
		 }
		 
		 
		 if ( returnList == null )  {
			 
			 
			 returnList = ssowDao.getBasicQList();
			 
			 SsowCacheUtil.putInCache(SsowCacheUtil.getCheckListBasicQKey(), returnList);
		 }
		
		return returnList;
		//return ssowDao.getBasicQList();
	}

	
	
	
	public List<Sdrl> getSdrlByRuleId( String ruleId)  {
		
		return ssowDao.getSdrlByRuleId(ruleId);
	}

	public List<Acronyms> getAcronymsByRuleId( String ruleId)  {
		return ssowDao.getAcronymsByRuleId(ruleId);
	}


	public List<Dictionary> getDictionaryByRuleId( String ruleId) {
		
		
		return ssowDao.getDictionaryByRuleId(ruleId);
	}
	
	
	public List getSdrlList ()  {
		
		
		List returnList = null ; 
		String cacheKey = SsowCacheUtil.getSdrlListKey();
		 
		 try {
			
			returnList = (List) (SsowCacheUtil.getFromCache(cacheKey));
		 } catch (NeedsRefreshException e) {
			// Need to refresh the cache.
		 }
		 
		 
		 if ( returnList == null )  {
			 
			 
			 returnList = ssowDao.getSdrlList();
			 
			 SsowCacheUtil.putInCache(SsowCacheUtil.getSdrlListKey(), returnList);
		 }
		
		return returnList;
		//return ssowDao.getBasicQList();
	}


	public List getAcronymsList ()  {
		List returnList = null ; 
		String cacheKey = SsowCacheUtil.getAcronymsListKey();
		 
		 try {
			
			returnList = (List) (SsowCacheUtil.getFromCache(cacheKey));
		 } catch (NeedsRefreshException e) {
			// Need to refresh the cache.
		 }
		 
		 
		 if ( returnList == null )  {
			 
			 
			 returnList = ssowDao.getAcronymsList();
			 
			 SsowCacheUtil.putInCache(SsowCacheUtil.getAcronymsListKey(), returnList);
		 }
		
		return returnList;
	}


	public void delete ( Object object) {
		
		ssowDao.delete(object);
	}
	public List getDictionaryList () {
		List returnList = null ; 
		String cacheKey = SsowCacheUtil.getDictionaryListKey();
		 
		 try {
			
			returnList = (List) (SsowCacheUtil.getFromCache(cacheKey));
		 } catch (NeedsRefreshException e) {
			// Need to refresh the cache.
		 }
		 
		 
		 if ( returnList == null )  {
			 
			 
			 returnList = ssowDao.getDictionaryList();
			 
			 SsowCacheUtil.putInCache(SsowCacheUtil.getDictionaryListKey(), returnList);
		 }
		
		return returnList;
	}
	public JsonRules getJsonRules ( Rules rulesObj) {
		
		
		
		JsonRules jsonRule = new JsonRules();
		
		jsonRule.setName(rulesObj.getName());
		jsonRule.setId ( rulesObj.getId());
	//	System.out.println("**** Rule name " + rulesObj.getName());
		
		String criteriaString = "";
		if ( rulesObj.getChecklistCriteriaTrueID() != null ) {
			
			
			List list = this.getRulesCriteriaXrefById(rulesObj.getChecklistCriteriaTrueID());
			
			if ( list != null ) {
				
				for ( int k = 0 ; k < list.size(); k++) {
					
					
					RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
					
					// get the check list question 
					
					CheckList chkList = (CheckList) this.getCheckList(xref.getDependencyID());
					
					if ( chkList != null ) {
						
						//System.out.println("**** question " + chkList.getItemNumber());
						criteriaString += chkList.getItemNumber() + "=>" + xref.getValue() + "; ";
						
					}
					// now get the dependency id and see if it has a value in the map for it and compare to value i want 
					
					
					
					
				}
			}
		}
		
		jsonRule.setChecklistCriteriaTrueID(criteriaString);
		
		
		
		criteriaString = "";
		if ( rulesObj.getChecklistCriteriaAnyTrueID() != null ) {
			
		//	System.out.println("***** any true id " + rulesObj.getChecklistCriteriaAnyTrueID());
			List list = this.getRulesCriteriaXrefById(rulesObj.getChecklistCriteriaAnyTrueID());
			
			if ( list != null ) {
		//	System.out.println("**** list size "+ list.size());	
				for ( int k = 0 ; k < list.size(); k++) {
					
					
					RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
					
					//System.out.println("*** xref dep " + xref.getDependencyID());
					CheckList chkList = (CheckList) this.getCheckList(xref.getDependencyID());
					
					if ( chkList != null ) {
						
					//	System.out.println("**** question " + chkList.getItemNumber());
						criteriaString += chkList.getItemNumber() + "=>" + xref.getValue() + "; ";
						
					}else {
					//	System.out.println("**** ERROR : Could not find a checklist record for id " + xref.getDependencyID());
					}
					
					// now get the dependency id and see if it has a value in the map for it and compare to value i want 
					
					
				}
			}
		}
		
		jsonRule.setChecklistCriteriaAnyTrueID(criteriaString);
		
		criteriaString = "";
		
		if ( rulesObj.getChecklistCriteriaAllTrueID() != null ) {
			
			
			List list = this.getRulesCriteriaXrefById(rulesObj.getChecklistCriteriaAllTrueID());
			
			if ( list != null ) {
				
				for ( int k = 0 ; k < list.size(); k++) {
					
					
					RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
					
					// now get the dependency id and see if it has a value in the map for it and compare to value i want 
					
					CheckList chkList = (CheckList) this.getCheckList(xref.getDependencyID());
					
					if ( chkList != null ) {
						
					//	System.out.println("**** question " + chkList.getItemNumber());
						criteriaString += chkList.getItemNumber() + "=>" + xref.getValue() + "; ";
						
					}
				}
			}
		}
		
		
		jsonRule.setChecklistCriteriaAllTrueID(criteriaString);
		
		 criteriaString = "";
		
		if ( rulesObj.getChecklistCriteriaAnyNotTrueID() != null ) {
			
			
			List list = this.getRulesCriteriaXrefById(rulesObj.getChecklistCriteriaAnyNotTrueID());
			
			if ( list != null ) {
				
				for ( int k = 0 ; k < list.size(); k++) {
					
					
					RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
					
					// now get the dependency id and see if it has a value in the map for it and compare to value i want 
					
					CheckList chkList = (CheckList) this.getCheckList(xref.getDependencyID());
					
					if ( chkList != null ) {
						
					//	System.out.println("**** question " + chkList.getItemNumber());
						criteriaString += chkList.getItemNumber() + "=>" + xref.getValue() + "; ";
						
					}
				}
			}
		}
		
		jsonRule.setChecklistCriteriaAnyNotTrueID(criteriaString);
		
		 criteriaString = "";
		if ( rulesObj.getRulesCriteriaAllTrueID() != null ) {
			
			
			List list = this.getRulesCriteriaXrefById(rulesObj.getRulesCriteriaAllTrueID());
			
			if ( list != null ) {
				
				for ( int k = 0 ; k < list.size(); k++) {
					
					
					RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
					
					// get the check list question 
					
					Rules chkList = (Rules) this.getRuleById(xref.getDependencyID());
					
					if ( chkList != null ) {
						
				//		System.out.println("**** question " + chkList.getName());
						criteriaString += chkList.getName() + "=>Y" + "; ";
						
					}
					// now get the dependency id and see if it has a value in the map for it and compare to value i want 
					
					
					
					
				}
			}
		}
		
		
		jsonRule.setRulesCriteriaAllTrueID(criteriaString);
		
		 criteriaString = "";
			if ( rulesObj.getRulesCriteriaNotTrueID() != null ) {
				
				
				List list = this.getRulesCriteriaXrefById(rulesObj.getRulesCriteriaNotTrueID());
				
				if ( list != null ) {
					
					for ( int k = 0 ; k < list.size(); k++) {
						
						
						RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
						
						// get the check list question 
						
						Rules chkList = (Rules) this.getRuleById(xref.getDependencyID());
						
						if ( chkList != null ) {
							
					//		System.out.println("**** question " + chkList.getName());
							criteriaString += chkList.getName() + "=>Y" + "; ";
							
						}
						// now get the dependency id and see if it has a value in the map for it and compare to value i want 
						
						
						
						
					}
				}
			}
			
			jsonRule.setRulesCriteriaNotTrueID(criteriaString);
			 criteriaString = "";
				if ( rulesObj.getRulesCriteriaAnyTrueID() != null ) {
					
					
					List list = this.getRulesCriteriaXrefById(rulesObj.getRulesCriteriaAnyTrueID());
					
					if ( list != null ) {
						
						for ( int k = 0 ; k < list.size(); k++) {
							
							
							RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
							
							// get the check list question 
							
							Rules chkList = (Rules) this.getRuleById(xref.getDependencyID());
							
							if ( chkList != null ) {
								
							//	System.out.println("**** question " + chkList.getName());
								criteriaString += chkList.getName() + "=>Y" + "; ";
								
							}
							// now get the dependency id and see if it has a value in the map for it and compare to value i want 
							
							
							
							
						}
					}
				}
				
				jsonRule.setRulesCriteriaAnyTrueID(criteriaString);
		
		if (! org.apache.commons.lang.StringUtils.isEmpty(jsonRule.getChecklistCriteriaAllTrueID())) {
			
			
			int lastIndex = jsonRule.getChecklistCriteriaAllTrueID().lastIndexOf(';');
			if ( lastIndex != 0 ) {
				jsonRule.setChecklistCriteriaAllTrueID(jsonRule.getChecklistCriteriaAllTrueID().substring(0 ,  lastIndex));
			}
		}
		
		
		if (! org.apache.commons.lang.StringUtils.isEmpty(jsonRule.getChecklistCriteriaTrueID())) {
			
			
			int lastIndex = jsonRule.getChecklistCriteriaTrueID().lastIndexOf(';');
			if ( lastIndex != 0 ) {
				jsonRule.setChecklistCriteriaTrueID(jsonRule.getChecklistCriteriaTrueID().substring(0 ,  lastIndex));
			}
		}
		
		
		if (! org.apache.commons.lang.StringUtils.isEmpty(jsonRule.getChecklistCriteriaAnyNotTrueID())) {
			
			
			int lastIndex = jsonRule.getChecklistCriteriaAnyNotTrueID().lastIndexOf(';');
			if ( lastIndex != 0 ) {
				jsonRule.setChecklistCriteriaAnyNotTrueID(jsonRule.getChecklistCriteriaAnyNotTrueID().substring(0 ,  lastIndex));
			}
		}


		if (! org.apache.commons.lang.StringUtils.isEmpty(jsonRule.getChecklistCriteriaAnyTrueID())) {


			int lastIndex = jsonRule.getChecklistCriteriaAnyTrueID().lastIndexOf(';');
			if ( lastIndex != 0 ) {
				jsonRule.setChecklistCriteriaAnyTrueID(jsonRule.getChecklistCriteriaAnyTrueID().substring(0 ,  lastIndex));
			}
		}
		
		if (! org.apache.commons.lang.StringUtils.isEmpty(jsonRule.getRulesCriteriaAllTrueID())) {


			int lastIndex = jsonRule.getRulesCriteriaAllTrueID().lastIndexOf(';');
			if ( lastIndex != 0 ) {
				jsonRule.setRulesCriteriaAllTrueID(jsonRule.getRulesCriteriaAllTrueID().substring(0 ,  lastIndex));
			}
		}
		
		
		if (! org.apache.commons.lang.StringUtils.isEmpty(jsonRule.getRulesCriteriaAnyTrueID())) {


			int lastIndex = jsonRule.getRulesCriteriaAnyTrueID().lastIndexOf(';');
			if ( lastIndex != 0 ) {
				jsonRule.setRulesCriteriaAnyTrueID(jsonRule.getRulesCriteriaAnyTrueID().substring(0 ,  lastIndex));
			}
		}
		
		if (! org.apache.commons.lang.StringUtils.isEmpty(jsonRule.getRulesCriteriaNotTrueID())) {


			int lastIndex = jsonRule.getRulesCriteriaNotTrueID().lastIndexOf(';');
			if ( lastIndex != 0 ) {
				jsonRule.setRulesCriteriaNotTrueID(jsonRule.getRulesCriteriaNotTrueID().substring(0 ,  lastIndex));
			}
		}
		//jsonRule.getChecklistCriteriaAllTrueID().
		
		return jsonRule;
	}
	
	
	public List getSsowCheckListXrefList ( String checkListId) {
		
		
		return ssowDao.getSsowCheckListXrefList(checkListId);
	}
	
	
	public List<Ssow> getSsowListBySsowNumer ( String ssowNumber) {
		
		return ssowDao.getSsowListBySsowNumer(ssowNumber);
	}
	public List getCheckList () {
		
		
		
		List returnList = null ; 
		String cacheKey = SsowCacheUtil.getCheckListKey();
		 
		 try {
			
			returnList = (List) (SsowCacheUtil.getFromCache(cacheKey));
		 } catch (NeedsRefreshException e) {
			// Need to refresh the cache.
		 }
		 
		 
		 if ( returnList == null )  {
			 
			// System.out.println("***ARE WE HITTING THE DB FOR CHECKLIST AGAIN AND AGAIN ");
			 returnList = ssowDao.getCheckList();
			 
			 SsowCacheUtil.putInCache(SsowCacheUtil.getCheckListKey(), returnList);
		 }
		
		return returnList;
		//return ssowDao.getBasicQList();
	}

	
	public List getAdvancedQList () {
		
		
		
		List returnList = null ; 
		String cacheKey = SsowCacheUtil.getCheckListAdvancedQKey();
		 
		 try {
			
			returnList = (List) (SsowCacheUtil.getFromCache(cacheKey));
		 } catch (NeedsRefreshException e) {
			// Need to refresh the cache.
		 }
		 
		 
		 if ( returnList == null )  {
			 
			 
			 returnList = ssowDao.getAdvancedQList();
			 
			 SsowCacheUtil.putInCache(SsowCacheUtil.getCheckListAdvancedQKey(), returnList);
		 }
		
		return returnList;
		//return ssowDao.getAdvancedQList();
	}
	
	
	
	
	
	public List getWordSectionsList () {
		
		
	ArrayList returnList  = new ArrayList();
//		
//		
//		//createSection(sectionNumber, text, type)
//		createSection("1"  ,     "Scope" , SectionType.HEADER_SECTION , returnList);
//		createSection("" , "This Supplier Statement of Work (SSOW) defines tasks that shall be performed by the Supplier, under contract to The Boeing Company, hereinafter known as Boeing, as authorized by, and subject to the terms and conditions of the Boeing Purchase Contract (PC).  The Supplier shall design, develop, flight qualify, and produce an XXX for the purpose of technical maturation and risk reduction in anticipation of Customer needs for future unmanned, intelligence, surveillance, reconnaissance, and strike aerial systems, including, but not limited to the United States Navy (USN), Unmanned Carrier Launched Airborne Surveillance and Strike System (UCLASS) program." , SectionType.PARAGRAPH_SECTION , returnList) ;
//		createSection("" , "Work to be performed hereunder by the Supplier shall consist of providing personnel, materials, services, facilities, logistic support, data and management required to design, develop, fabricate, test, document, deliver on schedule, and support the equipment and/or computer programs defined by the Procurement Specification (PS) and Supplier Data Requirements Catalog (SDRC) identified in the PC. In this context, the term “support” refers to the Supplier's effort to assist Boeing with the integration, verification, and usage of the Supplier's delivered product in the system." , SectionType.PARAGRAPH_SECTION , returnList);
//		createSection("" , "Throughout this document, Supplier Data Requirements List (SDRL) data items are noted by referencing specific Supplier Data Sheets (SDS), as defined in Supplier Data Requirements Catalog (SDRC), UISRSTK1SMSDL0001.  The SDRL data items referenced in this SSOW are summarized in Appendix A herein.  Each SDS provides additional definition to the content, format, and digital delivery of a required data item." , SectionType.PARAGRAPH_SECTION , returnList);
//		createSection("" , "Throughout this SSOW, the term “Supplier” shall be interpreted to include Sub-tier Suppliers, as applicable." , SectionType.PARAGRAPH_SECTION , returnList);  
//		createSection("" , "Where paragraphs of this document or any of its referenced documents are cited, the citation shall be understood to include all subparagraphs under the cited paragraphs, unless otherwise noted." , SectionType.PARAGRAPH_SECTION , returnList);
//		createSection("1.1"  , "Product/Service Description" , SectionType.SUB_SECTION, returnList);
//		createSection("" , "The Supplier shall perform the tasks detailed in Section 3 of this SSOW to design, develop and/or provide the product specified in the PS, as referenced in Section 2, herein. The Supplier shall, to the maximum extent possible, use non developmental items (NDIs) for the UCLASS program, while meeting program requirements. For purposes of this contract, NDI is defined in Federal Acquisition Regulation (FAR) Part 2, and includes Commercial and Government Off-the-Shelf (COTS/GOTS) products, or any previously developed product in use by a federal, state, or local agency of the United States or a foreign government. A COTS item is any product which was developed independently by industry to meet market demand and includes newly developed items that do not yet have a market history. Hereinafter, these items will be referred to as NDIs and items that do not meet this definition will be referred to as developmental items (DIs). For DIs, the Supplier shall be responsible for all activities required to design, develop, fabricate, verify, and document the delivered items as defined in this SSOW, the PS, and the SDRL." , SectionType.PARAGRAPH_SECTION , returnList);
//		createSection("1.2" ,      "Period of Performance" , SectionType.SUB_SECTION , returnList);
//		createSection("" , "The period of performance shall be in accordance with the applicable Purchase Contract (PC)." , SectionType.PARAGRAPH_SECTION , returnList) ;
//		createSection("" , "The period of performance for the UCLASS program is anticipated to start." , SectionType.PARAGRAPH_SECTION , returnList);
//		createSection("1.3" ,      "Responsibility in Subcontracting" , SectionType.SUB_SECTION , returnList);
//		createSection("" , "In the event the Supplier subcontracts any portion of its design, manufacture, test, services or data development, the Supplier's subcontracted effort is not exempt from the provisions of this document, the PC and the PS.  The Supplier shall include in subcontractor PCs all necessary elements to ensure complete conformance with these requirements." , SectionType.PARAGRAPH_SECTION , returnList);
//		createSection("" , "The Supplier shall be solely responsible for the performance and quality of the total requirements for items that are subcontracted or purchased." , SectionType.PARAGRAPH_SECTION , returnList);
//
//		
//		createSection("2" ,      "Applicable Documents" , SectionType.HEADER_SECTION , returnList);
//		createSection("" , "The documents listed hereunder form a part of this SSOW to the extent invoked by specific reference in other paragraphs of this SSOW.  If a document is referenced without indicating any specific paragraphs as being applicable, then the document is applicable in its entirety.  Where a specific issue of the document is provided in Section 2, no other issue shall be used without the prior, written approval of the Boeing Procurement Agent.  When documents are referenced herein, a short form citing only the basic number of the document is used and revision letters, amendment indicators, notices, supplements and dates are generally omitted.  If a document is invoked by reference in the text of this SSOW, but not listed in Section 2, it is applicable.  The existence of this situation should be called to the attention of the Boeing Procurement Agent.  The applicable issue of subsidiary documents shall be per the revisions listed in Section 2.1 through Section 2.3 of this document." , SectionType.PARAGRAPH_SECTION , returnList);
//
//		
//		
//		createSection("3" ,      "Requirements" , SectionType.HEADER_SECTION , returnList );
//		createSection("3.1" ,     "General Requirements" , SectionType.SUB_SECTION , returnList);
//		createSection("" , "The Supplier shall be responsible for satisfying all requirements of the Boeing PC, PS, this SSOW and all other applicable requirements.  In the event that the Supplier fails to meet these requirements, the Supplier shall be responsible for all redesign, rework, failure analysis, retesting, and other associated efforts required to bring all equipment, delivered or otherwise, up to the requirements specified.  The Supplier shall provide design, test, and production facilities required to meet the test and delivery schedules specified in the PC." , SectionType.PARAGRAPH_SECTION , returnList); 
//		createSection("3.2"   ,  "Design and Development" , SectionType.SUB_SECTION, returnList);
//		createSection("3.2.1"  ,   "Systems Engineering (SE)" , SectionType.SUB_SECTION , returnList);
//		createSection("" , "The Supplier shall use standard Systems Engineering (SE) processes as outlined in the following paragraphs." , SectionType.PARAGRAPH_SECTION , returnList);  
//		createSection("3.2.1.1" ,      "Requirements Development" , SectionType.SUB_SECTION , returnList);
//		createSection("" , "The Supplier shall collaborate with Boeing to ensure: 1) that there is an understanding of the requirements, and 2) that appropriate management focus and techniques are employed to ensure compliance (e.g., risk, issue, opportunity, affordability, schedule needs or constraints, and technology readiness)." , SectionType.PARAGRAPH_SECTION , returnList);
//		createSection("3.2.1.2",     "Requirements Management and Requirements Metrics" , SectionType.SUB_SECTION , returnList);
//		createSection("3.2.1.2.1" ,      "Requirements Traceability" , SectionType.SUB_SECTION , returnList);
//		createSection("" , "The Supplier shall execute, document and provide an initial traceability product (bi-directional) between a functional requirement and its associated function in the functional architecture, no later than Supplier’s Systems Requirements Review (SRR).  Supplier derived requirements and associated verifications shall be jointly reviewed with Boeing at the Supplier’s System Requirements Review (SRR)." , SectionType.PARAGRAPH_SECTION , returnList);   
//
//		
//
//		
//		
//		
//		createSection("4" ,      "Definitions" , SectionType.HEADER_SECTION , returnList);
//		createSection("" , "Term	|Definition" , SectionType.TABLE_HEADER_SECTION , returnList);
//		createSection("" , "Boeing	|The Boeing Company" , SectionType.TABLE_ROW_SECTION , returnList);
//		createSection("" , "Supplier	|The designer/manufacturer of the equipment/product" , SectionType.TABLE_ROW_SECTION , returnList);
//		createSection("" , "Pre-Production Equipment	|Pre-production equipment is equipment in a configuration suitable for installation. This equipment is completely representative of the production equipment to follow, and is entirely suitable for testing and demonstration to determine if the production equipment will meet the requirements of this specification. This equipment uses the same parts intended for use in production equipment; however, the peculiar parts developed for the equipment may be manufactured using development tooling and methods. Pre-production equipment may be used for flight demonstrations, bench tests, spares, first article tests, engineering development tests, and any other usage as Boeing may determine." , SectionType.TABLE_ROW_SECTION , returnList);
//
//				
//				
//		createSection("5" ,      "Acronyms" , SectionType.HEADER_SECTION , returnList);
//		createSection("" , "Acronym	|Definition" , SectionType.TABLE_HEADER_SECTION , returnList);
//		createSection("" , "ATP	|Acceptance Test Procedure" , SectionType.TABLE_ROW_SECTION , returnList);
//		createSection("" , "BIT	|Built-In Test" , SectionType.TABLE_ROW_SECTION , returnList);
//		createSection("" , "BEST	|Boeing Enterprise Supplier Tool" , SectionType.TABLE_ROW_SECTION , returnList);
//		createSection("" , "BQMS	|Boeing Quality Management System" , SectionType.TABLE_ROW_SECTION , returnList);
//		createSection("" , "CBT	|Computer Based Training" , SectionType.TABLE_ROW_SECTION , returnList);
//		createSection("" , "CCDR	|Contract Cost Data Reporting" , SectionType.TABLE_ROW_SECTION , returnList);
//				

		
		return returnList;
	}
	
	
	public File processSsowGenerator (Ssow ssow) {
		
		
		
		Map chkMap = new HashMap();
		
		Set chkList = ssow.getSsowCheckList();
		
		if ( chkList != null ) {
			
		//	System.out.println("**** check list set size " + chkList.size());
			Iterator it = chkList.iterator();
			while ( it.hasNext()) {
				
				SsowCheckListXref xref = (SsowCheckListXref)it.next();
				
				chkMap.put(xref.getCheckList().getId(), xref.getAnswer());
			}
			
			//System.out.println("*** map values " + chkMap.toString());
		}else {
		//	System.out.println("**** check list null at ssow ");
		}
		
	//	
	//	System.out.println("***** check list map values " + chkMap.toString());
		
		//get the rules list all of them - 
		// then we go through each one of them - so 
		// now go through each rule - compare the question id by criteria and see what value we have stored in each of them 
		
		List rules = this.getRules();
		
	
		Map returnRulesMap = new HashMap();
	//	System.out.println("***** rules list size " + rules.size());
		for ( int i = 0 ; i < rules.size(); i++ ) {
			
			
			
			
			
			
			boolean criteriaTrue = false ; 
			boolean criteriaAnyTrue  = false ; 
			boolean criteriaAllTrue = false ; 
			boolean criteriaNotTrue = false ;
			boolean rulesCriteriaNotTrue = false ;
			boolean rulesCriteriaAnyTrue = false ;
			boolean rulesCriteriaAllTrue = false ;
			
			
			Rules rulesObj = (Rules) rules.get(i);
			
			//System.out.println("**** Rule name " + rulesObj.getName());
			if ( rulesObj.getChecklistCriteriaTrueID() != null ) {
				
				
				List list = this.getRulesCriteriaXrefById(rulesObj.getChecklistCriteriaTrueID());
				
				if ( list != null ) {
					
					for ( int k = 0 ; k < list.size(); k++) {
						
						
						RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
						
						// now get the dependency id and see if it has a value in the map for it and compare to value i want 
						
					//	System.out.println("** depeendency id " + xref.getDependencyID());
						
						if ( chkMap.containsKey(xref.getDependencyID())) {
							
							
							String value = (String) chkMap.get(xref.getDependencyID()) ;
							
							//System.out.println("**** xhecklist value " + value );
							//System.out.println("*** stored value " + xref.getValue());
							
							if ( xref.getValue().equals(value)) {
								
								// so the value matched 
								criteriaTrue = true ;
								break ; 
							}
							
						}else {
							criteriaTrue = false ; 
							
							//	System.out.println("**** breaking out of the loop ");
								break;
						}
					}
				}
			}else {
				criteriaTrue = true;
			}
			
		//	System.out.println("*** Rule name " + rulesObj.getId());
			
		//	System.out.println("*** criteria flag value for true " + criteriaTrue);
			
			if ( rulesObj.getChecklistCriteriaAnyTrueID() != null ) {
				
				
				List list = this.getRulesCriteriaXrefById(rulesObj.getChecklistCriteriaAnyTrueID());
				
				if ( list != null ) {
					
					for ( int k = 0 ; k < list.size(); k++) {
						
						
						RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
						
						// now get the dependency id and see if it has a value in the map for it and compare to value i want 
						
					//	System.out.println("** depeendency id " + xref.getDependencyID());
						
						if ( chkMap.containsKey(xref.getDependencyID())) {
							
							
							String value = (String) chkMap.get(xref.getDependencyID()) ;
							
							//System.out.println("**** xhecklist value " + value );
							//System.out.println("*** stored value " + xref.getValue());
							
							if ( xref.getValue().equals(value)) {
								
								// so the value matched 
								criteriaAnyTrue = true ;
								break ; 
							}
							
						}else {
							criteriaAnyTrue = false ; 
							
							//	System.out.println("**** breaking out of the loop ");
								break;
						}
					}
				}
			}else {
				criteriaAnyTrue = true;
			}
			
			
			
			if ( rulesObj.getRulesCriteriaAnyTrueID() != null ) {
				
				
				List list = this.getRulesCriteriaXrefById(rulesObj.getRulesCriteriaAnyTrueID());
				
				if ( list != null ) {
					
					for ( int k = 0 ; k < list.size(); k++) {
						
						
						RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
						
						// now get the dependency id and see if it has a value in the map for it and compare to value i want 
						
					//	System.out.println("** depeendency id " + xref.getDependencyID());
						
						if ( returnRulesMap.containsKey(xref.getDependencyID())) {
							
							
							//String value = (String) chkMap.get(xref.getDependencyID()) ;
							
							//System.out.println("**** xhecklist value " + value );
							//System.out.println("*** stored value " + xref.getValue());
							
							//if ( xref.getValue().equals(value)) {
								
								// so the value matched 
								rulesCriteriaAnyTrue = true ;
								break ; 
							//}
							
						}else {
							rulesCriteriaAnyTrue = false ; 
							
							//	System.out.println("**** breaking out of the loop ");
								break;
						}
					}
				}
			}else {
				rulesCriteriaAnyTrue = true;
			}
		//	System.out.println("*** criteria flag value for aany true " + criteriaAnyTrue);
			
			if ( rulesObj.getChecklistCriteriaAllTrueID() != null ) {
				
				
				List list = this.getRulesCriteriaXrefById(rulesObj.getChecklistCriteriaAllTrueID());
				
				if ( list != null ) {
					
					for ( int k = 0 ; k < list.size(); k++) {
					//	System.out.println("in the all true logic ");
						
						RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
						
						// now get the dependency id and see if it has a value in the map for it and compare to value i want 
						
						//System.out.println("** depeendency id " + xref.getDependencyID());
						
						if ( chkMap.containsKey(xref.getDependencyID())) {
							
							
							String value = (String) chkMap.get(xref.getDependencyID()) ;
							
						//	System.out.println("**** xhecklist value " + value );
						//	System.out.println("*** stored value " + xref.getValue());
							
							if ( xref.getValue().equals(value)) {
								
								// so the value matched 
								criteriaAllTrue = true ;
								//break ; 
							}else {
								criteriaAllTrue = false ; 
								
							//	System.out.println("**** breaking out of the loop ");
								break;
							}
							
						}else {
							criteriaAllTrue = false ; 
							
							//	System.out.println("**** breaking out of the loop for rule " + rulesObj.getId());
								break;
						}
						
						
					}
				//	System.out.println("done with the loop ");
				}
			}else {
				criteriaAllTrue = true;
			}
			
			
			
			
			if ( rulesObj.getRulesCriteriaAllTrueID() != null ) {
				
				
				List list = this.getRulesCriteriaXrefById(rulesObj.getRulesCriteriaAllTrueID());
				
				if ( list != null ) {
					
					for ( int k = 0 ; k < list.size(); k++) {
					//	System.out.println("in the all true logic ");
						
						RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
						
						// now get the dependency id and see if it has a value in the map for it and compare to value i want 
						
						//System.out.println("** depeendency id " + xref.getDependencyID());
						
						if ( returnRulesMap.containsKey(xref.getDependencyID())) {
							
							
						//	String value = (String) chkMap.get(xref.getDependencyID()) ;
							
						//	System.out.println("**** xhecklist value " + value );
						//	System.out.println("*** stored value " + xref.getValue());
							
							//if ( xref.getValue().equals(value)) {
								
								// so the value matched 
								rulesCriteriaAllTrue = true ;
								//break ; 
							}else {
								rulesCriteriaAllTrue = false ; 
								
							//	System.out.println("**** breaking out of the loop ");
								break;
							}
							
						//}else {
						//	criteriaAllTrue = false ; 
							
							//	System.out.println("**** breaking out of the loop for rule " + rulesObj.getId());
						//		break;
						//}
						
						
					}
				//	System.out.println("done with the loop ");
				}
			}else {
				rulesCriteriaAllTrue = true;
			}
			//System.out.println("*** criteria flag value for all true " + criteriaAllTrue);
			
			if ( rulesObj.getChecklistCriteriaAnyNotTrueID() != null ) {
				
				
				List list = this.getRulesCriteriaXrefById(rulesObj.getChecklistCriteriaAnyNotTrueID());
				
				if ( list != null ) {
					
					for ( int k = 0 ; k < list.size(); k++) {
						
						
						RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
						
						// now get the dependency id and see if it has a value in the map for it and compare to value i want 
						
					//	System.out.println("** depeendency id " + xref.getDependencyID());
						
						if ( chkMap.containsKey(xref.getDependencyID())) {
							
							
							String value = (String) chkMap.get(xref.getDependencyID()) ;
							
						//	System.out.println("**** xhecklist value " + value );
						//	System.out.println("*** stored value " + xref.getValue());
							
							if (! xref.getValue().equals(value)) {
								
								// so the value matched 
								criteriaNotTrue = true ;
								//break ; 
							}else {
								criteriaNotTrue = false ;
								break;
							}
							
						}else {
							criteriaNotTrue = false ; 
							
							//	System.out.println("**** breaking out of the loop ");
								break;
						}
					}
				}
			}else {
				criteriaNotTrue = true;
			}
			
			
			
	if ( rulesObj.getRulesCriteriaNotTrueID() != null ) {
				
				
				List list = this.getRulesCriteriaXrefById(rulesObj.getRulesCriteriaNotTrueID());
				
				if ( list != null ) {
					
					for ( int k = 0 ; k < list.size(); k++) {
						
						
						RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
						
						// now get the dependency id and see if it has a value in the map for it and compare to value i want 
						
					//	System.out.println("** depeendency id " + xref.getDependencyID());
						
						if (! returnRulesMap.containsKey(xref.getDependencyID())) {
							
							
							//String value = (String) chkMap.get(xref.getDependencyID()) ;
							
						//	System.out.println("**** xhecklist value " + value );
						//	System.out.println("*** stored value " + xref.getValue());
							
							//if (! xref.getValue().equals(value)) {
								
								// so the value matched 
								rulesCriteriaNotTrue = true ;
								//break ; 
							}else {
								rulesCriteriaNotTrue = false ;
								break;
							}
							
						//}else {
						///	criteriaNotTrue = false ; 
							
							//	System.out.println("**** breaking out of the loop ");
						//		break;
						//}
					}
				}
			}else {
				rulesCriteriaNotTrue = true;
			}
			
		//	System.out.println("*** criteria flag value for true " + criteriaNotTrue);
			
			//if all the criteria applies then means we take the rule with us - 
			if ( criteriaTrue && criteriaAnyTrue && criteriaAllTrue && criteriaNotTrue
					&& rulesCriteriaNotTrue && rulesCriteriaAllTrue && rulesCriteriaAnyTrue) {
				
				// add the rules to the final bucket 
				//returnRulesList.add(rulesObj);
				returnRulesMap.put(rulesObj.getId(), rulesObj);
			}
		
			
			
			
			
			
			/// after this , now the next step is to read the sections out of tree and then 
			// apply the rules to the sections and in the end make the list of the final sections list in the right 
			// order and hand it over to the word output - 
			
			
			
		
			
			
		}
		
		
//		List returnSectionList = new ArrayList();
//	//	System.out.println("***** return RulesList size " + returnRulesList.size());
	//	System.out.println("***** return RulesList size " + returnRulesMap.size());
	//	System.out.println("***** return RulesList size " + returnRulesMap.keySet().toString());
//		// get whole section list --- 
//		List sections = this.sectionService.getMainSubSections("0");
//		
//		System.out.println("**** size of the section list " + sections.size());
//		
//		for ( int i = 0 ; i < sections.size() ; i++) {
//			
//			
//			
//			Section section = (Section)sections.get(i);
//			
//		//	System.out.println();
//			
//			
//			if ( section.getRulesSet() != null && section.getRulesSet().size() > 0) {
//				
//				ArrayList temp = new ArrayList ( section.getRulesSet());
//				
//				for ( int k = 0 ; k < temp.size() ; k++) {
//					
//				
//				Rules rulesTC = (Rules)temp.get(k);
//			
//					System.out.println("****  section number " + section.getSectionNumber() + "****  rule name applicable  " + rulesTC.getName() );
//					
//					// check to see if the list contains the rules 
//					
//					if ( returnRulesMap.containsKey(rulesTC.getId())) {
//						
//						// if the list contains the rules 
//						
//						// add the section to the output - 
//						returnSectionList.add(section);
//					}
//				}
//		
//			}
//		}
		
		
	//	System.out.println("**** sections which are applicable " + processSections(returnRulesMap).size());
		
		
		
		
		// now need to get the sdl matrix code
		
		// acronyms - 
		
		
		
//		List sdrlList = ssowDao.getSdrlList();
//		
//		List returnSdrlList = new ArrayList();
//		System.out.println("*** sdrl size " + sdrlList.size());
//		
//		for ( int i = 0 ; i < sdrlList.size() ; i++ ) {
//			
//			Sdrl sdrlT = (Sdrl)sdrlList.get(i);
//			
//			if ( sdrlT.getRulesSet() != null && sdrlT.getRulesSet().size() > 0) {
//				
//				ArrayList temp = new ArrayList ( sdrlT.getRulesSet());
//				for ( int k = 0 ; k < temp.size() ; k++) {
//					
//					
//					Rules rulesTC = (Rules)temp.get(k);
//					//System.out.println("****  section number " + section.getSectionNumber() + "****  rule name applicable  " + rulesTC.getName() );
//					//	System.out.println("****   rule name applicable  " + rulesTC.getId() );
//					// check to see if the list contains the rules 
//					
//					if ( returnRulesMap.containsKey(rulesTC.getId())) {
//						
//						// if the list contains the rules 
//						
//						// add the section to the output - 
//						returnSdrlList.add(sdrlT);
//					}
//				}
//		
//			}
//		}
		
		
		
	//	System.out.println("**** Final sdrl list : " + processSdrl(returnRulesMap).size());
//		List acronymsList = ssowDao.getAcronymsList();
//		
//		System.out.println("*** acronyms size " + acronymsList.size());
//		
//		
//		
//		List returnAcronymsList = new ArrayList();
//	//	System.out.println("*** sdrl size " + sdrlList.size());
//		
//		for ( int i = 0 ; i < acronymsList.size() ; i++ ) {
//			
//			Acronyms acronyms  = (Acronyms)acronymsList.get(i);
//			
//			if ( acronyms.getRulesSet() != null && acronyms.getRulesSet().size() > 0) {
//				
//				ArrayList temp = new ArrayList ( acronyms.getRulesSet());
//				for ( int k = 0 ; k < temp.size() ; k++) {
//					
//					
//					Rules rulesTC = (Rules)temp.get(k);
//					//System.out.println("****  section number " + section.getSectionNumber() + "****  rule name applicable  " + rulesTC.getName() );
//					//	System.out.println("****   rule name applicable  " + rulesTC.getId() );
//					// check to see if the list contains the rules 
//					
//					if ( returnRulesMap.containsKey(rulesTC.getId())) {
//						
//						// if the list contains the rules 
//						
//						// add the section to the output - 
//						returnAcronymsList.add(acronyms);
//					}
//				}
//		
//			}
//		}
		
		
	//	System.out.println("**** Final acronyms list : " + processAcronyms(returnRulesMap).size());
		
		
		
		
		
//		List returnDictList = new ArrayList();
//		//	System.out.println("*** sdrl size " + sdrlList.size());
//			
//			for ( int i = 0 ; i < dictionaryList.size() ; i++ ) {
//				
//				Dictionary dictionary  = (Dictionary)dictionaryList.get(i);
//				
//				if ( dictionary.getRulesSet() != null && dictionary.getRulesSet().size() > 0) {
//					
//					ArrayList temp = new ArrayList ( dictionary.getRulesSet());
//					for ( int k = 0 ; k < temp.size() ; k++) {
//						
//						
//						Rules rulesTC = (Rules)temp.get(k);
//				
//					//	System.out.println("****   rule name applicable  " + rulesTC.getId() );
//						
//						// check to see if the list contains the rules 
//						
//						if ( returnRulesMap.containsKey(rulesTC.getId())) {
//							
//							// if the list contains the rules 
//							
//							// add the section to the output - 
//							returnDictList.add(dictionary);
//						}
//					}
//					
//				}
//			}
			
			
		//	System.out.println("**** Final dictionary list : " + processDictionary(returnRulesMap).size());
		// 
		// now we will marshall the section list for s
		
		// once we have the correct list then go through each section and see which rule applies to it and 
		// if its part of my bucket and then we will see - 
		
			ArrayList finalReturnList = processSections(returnRulesMap);
			
			
			
			
			List sdrlList = processSdrl(returnRulesMap);
			
			if ( sdrlList.size() > 0 ) {
				// add the section W for header for sdrl 
				finalReturnList.add(convertToSectionWHeader("APPENDIX A:  SUPPLIER DATA REQUIREMENTS LIST (SDRL)"));
				
				SectionW sectionW = new SectionW();
				sectionW.setSectionNumber("");
				sectionW.setSectionText("<b>Data Item Number</b>||<b>Title</b>");
				sectionW.setSectionType(SectionType.TABLE_SECTION);
				finalReturnList.add(sectionW);
			//	finalReturnList.addAll(acronymsList);
				finalReturnList.addAll(sdrlList);
			}
			
			
			List acronymsList =  processAcronyms(returnRulesMap);
			if ( acronymsList.size() > 0 ) {
				finalReturnList.add(convertToSectionWHeader("APPENDIX B: ACRONYMS"));
				SectionW sectionW = new SectionW();
				sectionW.setSectionNumber("");
				sectionW.setSectionText("<b>Acronyms</b>||<b>Definition</b>");
				sectionW.setSectionType(SectionType.TABLE_SECTION);
				finalReturnList.add(sectionW);
				finalReturnList.addAll(acronymsList);
			}
			
			
			
			
			List defList =  processDictionary(returnRulesMap);
			
			if ( defList.size() > 0 ) {
				finalReturnList.add(convertToSectionWHeader("APPENDIX C:  DEFINITIONS"));
				SectionW sectionW = new SectionW();
				sectionW.setSectionNumber("");
				sectionW.setSectionText("<b>Term</b>||<b>Definition</b>");
				sectionW.setSectionType(SectionType.TABLE_SECTION);
				finalReturnList.add(sectionW);
				finalReturnList.addAll(defList);
			}
			File returnFile = null; 
			List<ProgramFile> programfileList = ssowDao.getProgramFileList("Common");
			
			if ( programfileList != null ) {
				
			//	System.out.println("**** program file list size " + programfileList.size());
				if ( programfileList.size() > 0 ) {
					ProgramFile file = programfileList.get(0);
					///Blob blob = file.getProgramDoc();
					try {
						returnFile = 	SsowWordUtil.processWordDocument( file , ssow , finalReturnList);
						this.save(ssow);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}else {
				//System.out.println("**** Program file list is  empty ");
			}
			
		
		
		
		return returnFile;
	}
	/*private void createSection ( String sectionNumber , String text , String type , List returnList  ) {
		
		
		SectionW sectionW = new SectionW();
		sectionW.setSectionNumber(sectionNumber);
		sectionW.setSectionText(text);
		sectionW.setSectionType(type);
		
		
		returnList.add(sectionW);
		//return sectionW;
		
		
	}*/
	
	
	
	private ArrayList processSections ( Map returnRulesMap ) {
		
		
		
		ArrayList returnSectionList = new ArrayList();
		//	System.out.println("***** return RulesList size " + returnRulesList.size());
		//	System.out.println("***** return RulesList size " + returnRulesMap.size());
		//	System.out.println("***** return RulesList size " + returnRulesMap.keySet().toString());
			// get whole section list --- 
			
			List mainSections = sectionService.getMainSections(); 
			
			if ( mainSections != null ) {
				
				for ( int j= 0 ; j < mainSections.size(); j++) {
					
					
					Section sectionNew = (Section)mainSections.get(j);
					
				//	System.out.println("**** getting for section " + sectionNew.getSectionNumber());
				
					if ( !StringUtils.isEmpty(sectionNew.getSectionNumber())) {
						List sections = this.sectionService.getMainSubSections(sectionNew.getSectionNumber());
						
				//		System.out.println("**** size of the section list " + sections.size());
						
						for ( int i = 0 ; i < sections.size() ; i++) {
							
							
							
							Section section = (Section)sections.get(i);
							
						//	System.out.println();
							
							
							if ( section.getRulesSet() != null && section.getRulesSet().size() > 0) {
								
								ArrayList temp = new ArrayList ( section.getRulesSet());
								
								for ( int k = 0 ; k < temp.size() ; k++) {
									
								
								Rules rulesTC = (Rules)temp.get(k);
							
								//	System.out.println("****  section number " + section.getSectionNumber() + "****  rule name applicable  " + rulesTC.getName() );
									
									// check to see if the list contains the rules 
									
									if ( returnRulesMap.containsKey(rulesTC.getId())) {
										
										// if the list contains the rules 
										
										// add the section to the output - 
										returnSectionList.add(this.convertToSectionW(section));
									}
								}
						
							}
						}
					}
				}
			}
			
			return returnSectionList;
		
	}
	
	
	
	private SectionW convertToSectionW ( Section section )  {
		
		SectionW sectionW = new SectionW();
		sectionW.setSectionNumber(section.getSectionNumber());
		sectionW.setSectionText(section.getText());
		sectionW.setSectionType(section.getSectionType());
		
		return sectionW;
	}
	
	
private SectionW convertToSectionW ( Sdrl sdrl )  {
		
		SectionW sectionW = new SectionW();
		sectionW.setSectionNumber("");
		sectionW.setSectionText(sdrl.getName() + "||" +  sdrl.getDescription());
		sectionW.setSectionType(SectionType.TABLE_SECTION);
		
		return sectionW;
	}

private SectionW convertToSectionW ( Acronyms sdrl )  {
	
	SectionW sectionW = new SectionW();
	sectionW.setSectionNumber("");
	sectionW.setSectionText(sdrl.getName() + "||" +  sdrl.getDescription());
	sectionW.setSectionType(SectionType.TABLE_SECTION);
	
	return sectionW;
}


private SectionW convertToSectionWHeader ( String headerText )  {
	
	SectionW sectionW = new SectionW();
	sectionW.setSectionNumber("");
	sectionW.setSectionText(headerText);
	sectionW.setSectionType(SectionType.HEADER_SECTION);
	
	return sectionW;
}
private SectionW convertToSectionW ( Dictionary sdrl )  {
	
	SectionW sectionW = new SectionW();
	sectionW.setSectionNumber("");
	sectionW.setSectionText(sdrl.getName() + "||" +  sdrl.getDescription());
	sectionW.setSectionType(SectionType.TABLE_SECTION);
	
	return sectionW;
}
	public List getSdrlEditableList () {
		
		return ssowDao.getSdrlEditableList();
		
	}
	
	
	
	public List getRulesCriteriaXrefList ( String dependencyId) {
		
		
		return ssowDao.getRulesCriteriaXrefList(dependencyId);
	}
 	private List processSdrl ( Map returnRulesMap ) {
		
		
		
 		List sdrlList = this.getSdrlEditableList();
		
		List returnSdrlList = new ArrayList();
	//	System.out.println("*** before sdrl size " + sdrlList.size());
		
		for ( int i = 0 ; i < sdrlList.size() ; i++ ) {
			
			Sdrl sdrlT = (Sdrl)sdrlList.get(i);
			
			if ( sdrlT.getRulesSet() != null && sdrlT.getRulesSet().size() > 0) {
				
				ArrayList temp = new ArrayList ( sdrlT.getRulesSet());
				for ( int k = 0 ; k < temp.size() ; k++) {
					
					
					Rules rulesTC = (Rules)temp.get(k);
					//System.out.println("****  section number " + section.getSectionNumber() + "****  rule name applicable  " + rulesTC.getName() );
					//	System.out.println("****   rule name applicable  " + rulesTC.getId() );
					// check to see if the list contains the rules 
					
					if ( returnRulesMap.containsKey(rulesTC.getId())) {
						
						// if the list contains the rules 
						
						// add the section to the output - 
						returnSdrlList.add(convertToSectionW(sdrlT));
					}
				}
		
			}
		}
		
		
	//	System.out.println("*** after sdrl size " + returnSdrlList.size());
		return returnSdrlList;
	}
	
	private List processAcronyms ( Map returnRulesMap ) {
		
		
		
		List acronymsList = this.getAcronymsList();
		
	//	System.out.println("*** acronyms size " + acronymsList.size());
		
		
		
		List returnAcronymsList = new ArrayList();
	//	System.out.println("*** before acronyms size " + acronymsList.size());
		
		for ( int i = 0 ; i < acronymsList.size() ; i++ ) {
			
			Acronyms acronyms  = (Acronyms)acronymsList.get(i);
			
			if ( acronyms.getRulesSet() != null && acronyms.getRulesSet().size() > 0) {
				
				ArrayList temp = new ArrayList ( acronyms.getRulesSet());
				for ( int k = 0 ; k < temp.size() ; k++) {
					
					
					Rules rulesTC = (Rules)temp.get(k);
					//System.out.println("****  section number " + section.getSectionNumber() + "****  rule name applicable  " + rulesTC.getName() );
					//	System.out.println("****   rule name applicable  " + rulesTC.getId() );
					// check to see if the list contains the rules 
					
					if ( returnRulesMap.containsKey(rulesTC.getId())) {
						
						// if the list contains the rules 
						
						// add the section to the output - 
						returnAcronymsList.add(convertToSectionW(acronyms));
					}
				}
		
			}
		}
		
	//	System.out.println("*** after acronyms  size " + returnAcronymsList.size());
		return returnAcronymsList;
		
		
	}
	
	private List processDictionary (Map returnRulesMap) {
		
		List dictionaryList = this.getDictionaryList();
		
	//	System.out.println("*** before dictionary size " + dictionaryList.size());
		
		List returnDictList = new ArrayList();
		//	System.out.println("*** sdrl size " + sdrlList.size());
			
			for ( int i = 0 ; i < dictionaryList.size() ; i++ ) {
				
				Dictionary dictionary  = (Dictionary)dictionaryList.get(i);
				
				if ( dictionary.getRulesSet() != null && dictionary.getRulesSet().size() > 0) {
					
					ArrayList temp = new ArrayList ( dictionary.getRulesSet());
					for ( int k = 0 ; k < temp.size() ; k++) {
						
						
						Rules rulesTC = (Rules)temp.get(k);
				
					//	System.out.println("****   rule name applicable  " + rulesTC.getId() );
						
						// check to see if the list contains the rules 
						
						if ( returnRulesMap.containsKey(rulesTC.getId())) {
							
							// if the list contains the rules 
							
							// add the section to the output - 
							returnDictList.add(convertToSectionW(dictionary));
						}
					}
					
				}
			}
			
			
		//	System.out.println("**** Final dictionary list : " + returnDictList.size());
			
			return returnDictList;
	}
}
