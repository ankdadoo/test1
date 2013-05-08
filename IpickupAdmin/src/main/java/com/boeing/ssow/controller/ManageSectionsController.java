package com.boeing.ssow.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boeing.ssow.model.Audit;
import com.boeing.ssow.model.AuditType;
import com.boeing.ssow.model.ExtResult;
import com.boeing.ssow.model.JsonSection;
import com.boeing.ssow.model.Rules;
import com.boeing.ssow.model.Section;
import com.boeing.ssow.model.SectionTreeXref;
import com.boeing.ssow.model.SectionType;
import com.boeing.ssow.model.User;
import com.boeing.ssow.service.AuditService;
import com.boeing.ssow.service.AuditTypeService;
import com.boeing.ssow.service.RuleService;
import com.boeing.ssow.service.SectionService;
import com.boeing.ssow.service.SectionTreeXrefService;
import com.boeing.ssow.service.SectionTypeService;
import com.boeing.ssow.service.SsowService;
import com.boeing.ssow.utils.SsowSecurityUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/managesections")
public class ManageSectionsController {

	private static final Logger logger = LoggerFactory.getLogger(ManageSectionsController.class);
	
	private Set<Section> sectionTree; 

	@Autowired
	private SectionService sectionService;

	@Autowired
	private SectionTypeService sectionTypeService;

	@Autowired
	private RuleService ruleService;

	@Autowired
	private AuditService auditService;

	@Autowired
	private AuditTypeService auditTypeService;

	@Autowired
	private SectionTreeXrefService sectionTreeXrefService;

	
	@Autowired
	private SsowService ssowService;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String manageSections(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome Admin! the client locale is " + locale.toString() + "in manage section screen ");

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		if (! SsowSecurityUtil.validateUserInSession(request)) {
			return "home";
		}
		
		return "managesections";
	}

	@RequestMapping(value = "/getAllRules", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult getAllRules(Locale locale, Model mode) {

		logger.info("In the getAllRules list method "); 

		ExtResult extResult = new ExtResult();
		List returnList = ruleService.getAllRules();
		logger.info("**** Rules list size " + returnList.size());
		extResult.setData(returnList);
		return extResult;
	}

//	getMainSections
	@RequestMapping(value = "/getMainSections", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult getMainSections(Locale locale, Model model) { // , @RequestParam("idTypeHidden") String id) {

		logger.info("In the getMainSections list method ");

		ExtResult extResult = new ExtResult();
		List mainSections = sectionService.getMainSections(); 
		
		List mainSectionList = new ArrayList();
		for (int i=0; i<mainSections.size();i++) {
			Section sectionObj = (Section) mainSections.get(i);
			JsonSection jsonSection = new JsonSection();
			jsonSection.setSectionNumber(sectionObj.getSectionNumber());
			
			mainSectionList.add(jsonSection);
		}
		
		
		logger.info("**** Main Section list size is = " + mainSectionList.size());
		extResult.setData(mainSectionList);
		return extResult;
	}
	

	@RequestMapping(value = "/getSectionTypes", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult getSectionTypes(Locale locale, Model model) {

		logger.info("In the getSectionTypes list  method ");

		ExtResult extResult = new ExtResult();
		List returnList = sectionTypeService.getSectionTypeList();
		logger.info("**** Section Type list size " + returnList.size());
		extResult.setData(returnList);
		return extResult;
	}


	
	
	@RequestMapping(value = "/getChildrenSections", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult getChildrenSections(Locale locale, Model model, @RequestParam("idTypeHidden") String id) {

		logger.info("In the getChildrenSections list method ");

		ExtResult extResult = new ExtResult();
		List sectionList = sectionService.getChildrenSections(id); //tempID);
		logger.info("**** Associated Section list size for ID: " + id + " is = " + sectionList.size());
		extResult.setData(sectionList);
		return extResult;
	}

	@RequestMapping(value = "/getMainSubSections", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult getMainSubSections(Locale locale, Model model, 
			@RequestParam("sectionNumber") String sectionNumber,
			HttpServletRequest request) {

		logger.info("In the getMainSubSections list method ");
		
		// NEW 
		String sectNo = (String) request.getAttribute("sectionNumber");
		sectNo = (String) request.getParameter("sectionNumber");

		String start = (String)request.getAttribute("start");
		String limit = (String) request.getAttribute("limit");
		
		start = (String)request.getParameter("start");
		limit = (String) request.getParameter("limit");
		 
		 
		ExtResult extResult = new ExtResult();
		List sectionList = sectionService.getMainSubSections(sectionNumber);
		
		int totalCount = sectionList.size();
		int startLimit = new Integer(start).intValue();
		int limitLimit = new Integer(limit).intValue();
		int endLimit = startLimit + limitLimit;
		
		if (endLimit > totalCount) {
			endLimit = totalCount;
		}

		List limitedSectionList = sectionList.subList(startLimit, endLimit);
		extResult.setResults(new Integer(totalCount)); // Pagination Effort
		extResult.setData(limitedSectionList);
		logger.info("**** Associated Section list size for sectionNumber: " + sectionNumber + " is = " + sectionList.size());
		
		return extResult;
	}
	

	// RJM - START OF UPDATE
/*	@RequestMapping(value = "/updateSection", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult updateSection(Locale locale, HttpServletRequest request,
			@RequestParam("sectionId2") String oldSectionId,
			@RequestParam("sectionText2") String sectionText, 
			@RequestParam("oldRuleId2") String oldRuleId,
			@RequestParam("idSectionTypeHidden") String sectionTypeId, 
			@RequestParam("reasonForChange") String reasonForChange, 
		    @RequestParam("idRuleHidden") String ruleId) {
		
		logger.info("In the updateSection  method ");
		
		ExtResult extResult = new ExtResult();
		Date date = new Date();
		User user = (User) request.getSession().getAttribute("user");
		
		 // update Audit records of this change 
		Audit newAudit = updateAudit(reasonForChange, user.getFullName(), date);
		
		// STEP #1: initial creation of new Section 
		Section newSection = new Section();
		String newSectionId = newSection.getId();
		
		// STEP #2: update old Section record
		Section oldSection = (Section) sectionService.get(Section.class, oldSectionId);
		oldSection.setActive(0);
		oldSection.setSuccessorId(newSectionId);
		oldSection.setModifiedBy(user.getFullName()); 
		oldSection.setModifiedDate(date);
		oldSection.setAuditID(newAudit);
		sectionService.save(oldSection); // TODO: reactivate when code looks good 

		// STEP #3: complete new Section creation 
		newSection.setText(sectionText);
		newSection.setSectionType(oldSection.getSectionType()); // sectionType);

		// Check if new rule was applied:
		if (!oldRuleId.equalsIgnoreCase(ruleId))
		{
			// the Rule has been changed 
			Rules oldRule = (Rules) ruleService.get(Rules.class, oldRuleId);
			Rules newRule = (Rules) ruleService.get(Rules.class, ruleId);
			Set<Rules> oldRulesTree = oldSection.getRulesSet();
			oldRulesTree.add(newRule);
			oldRulesTree.remove(oldRule);
			newSection.setRulesSet(oldRulesTree);
		}
		else {
			newSection.setRulesSet(oldSection.getRulesSet());
		}
		

		newSection.setActive(1);
		newSection.setOrderNumber(oldSection.getOrderNumber()); 
		newSection.setSectionNumber(oldSection.getSectionNumber()); 
		newSection.setChildrenSet(oldSection.getChildrenSet());
		newSection.setModifiedBy(user.getFullName()); 
		newSection.setModifiedDate(date);
		newSection.setCreatedBy(user.getFullName()); 
		newSection.setCreatedDate(date); //oldSection.getCreatedDate());

		logger.info("Section - Order# " + oldSection.getOrderNumber() + " is updated!");
		sectionService.save(newSection); // TODO: reactivate when code looks good

		 // STEP #4: update Parent Child Section xrefs 
		List parentList = sectionService.getParentXrefs(oldSectionId);
		Iterator parentIter = parentList.iterator();
		while (parentIter.hasNext()) {
			SectionTreeXref parentXref = (SectionTreeXref) parentIter.next();
			String parentId = parentXref.getParentId();
			Section parentSection = (Section) sectionService.get(Section.class, parentId);

			Set<Section> parentTree = parentSection.getChildrenSet();
			parentTree.add(newSection);
			parentTree.remove(oldSection);
			parentSection.setChildrenSet(parentTree);
			sectionService.save(parentSection); // TODO: reactivate when code looks good
			logger.info("Parent Section - ID# " + parentSection.getId() + " is updated!");
		}
		logger.info("Child Section - ID# " + oldSection.getId() + " is updated!");

		extResult.setData(newSection);
		extResult.setMsg("Section is updated!");
		
		return extResult;
	}
*/
	// RJM - START OF ADD
	@RequestMapping(value = "/saveSection", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult saveSection(Locale locale, HttpServletRequest request,
			@RequestParam("sectionId") String currSectionId,
			@RequestParam("sectionStatus") String sectionStatus, 
		//	@RequestParam("sectionNumber2") String sectionNumber,
			@RequestParam("sectionText") String sectionText,
			@RequestParam("idSectionTypeHidden") String sectionType, 
			@RequestParam("auditReason") String reasonForChange, 
			@RequestParam("rulesString") String rulesString) {

		logger.info("In the addSection  method ");

		ExtResult extResult = new ExtResult();
		User user = (User) request.getSession().getAttribute("user");
		
		logger.info("current section id " + currSectionId);
	
		
		
		//if ( currentSection != null ) {
			
			if ( sectionStatus.equals("ADD")) {
				
				Section currentSection = (Section) sectionService.get(Section.class, currSectionId);
			//	Rules rule = (Rules) ruleService.get(Rules.class, ruleId); 
				int nextOrderNumber = sectionService.getNextOrderNumber();
				
				// STEP #1: create new Section 
				Section newSection = new Section();
				newSection.setActive(1);
				newSection.setOrderNumber(nextOrderNumber);
				newSection.setText(sectionText);
				
				// Section Number derivation is based on the Section Type being added: 
				if (sectionType.equalsIgnoreCase(SectionType.INSTRUCTION_SECTION) ||
		             sectionType.equalsIgnoreCase(SectionType.PARAGRAPH_SECTION) ||
					sectionType.equalsIgnoreCase(SectionType.TABLE_SECTION) ) {
						newSection.setSectionNumber("");
				} else if (sectionType.equalsIgnoreCase(SectionType.SUB_SECTION)) {
					String newSectionNumberValue = getNextSectionNumber2(currSectionId);
					newSection.setSectionNumber(newSectionNumberValue);
				} else {
					// Header Section type gets section number of current
					Section tempSection = (Section) sectionService.get(Section.class, currSectionId);
					newSection.setSectionNumber(tempSection.getSectionNumber());
				}
					
		
				if (currentSection.getSectionType().equalsIgnoreCase(SectionType.SUB_SECTION) && 
						sectionType.equalsIgnoreCase(SectionType.HEADER_SECTION)) {
					newSection.setSectionType(currentSection.getSectionType());
				} else {
					newSection.setSectionType(sectionType);
				}
		
				
				
				String[] strings = rulesString.split(";");
				TreeSet ruleSet = new TreeSet();
				for ( int i = 0 ; i < strings.length ; i++) {
					
					logger.info("**** string " + strings[i]);
					
					// split again 
				// get rule by rule name --- 
					
					Rules rule = ssowService.getRuleByName(strings[i]);
					
					if ( rule != null ) {
						ruleSet.add(rule);
					}
				//	and then add that rule to the the new sdrl 
				//	String[] valuePair = strings[i].split("=>") ;
			
				}
		
				newSection.setRulesSet(ruleSet);
				//final Rules newRule = (Rules) ruleService.get(Rules.class, ruleId);
				//Set<Rules> newRulesTree = new HashSet<Rules>() {{ add(newRule); }};
				//newSection.setRulesSet(newRulesTree);
		
				newSection.setModifiedBy(user.getFullName()); 
				newSection.setCreatedBy(user.getFullName()); 
		
				Date date = new Date();
				newSection.setCreatedDate(date);
				newSection.setModifiedDate(date);
				
				 // update Audit records of this change 
				Audit newAudit = updateAudit(reasonForChange, user.getFullName(), date); // TODO: uncomment when truly working
		
				// Update SectionTreeXref based on type of Section selected:
				if (currentSection.getSectionType().equalsIgnoreCase(SectionType.HEADER_SECTION)) 
				{
					updateHeaderSectionTreeXref(currentSection.getSectionType(), currSectionId, sectionType, newSection);
				} 
				else
				{
					updateSubsectionSectionTreeXref(currentSection.getSectionType(), currSectionId, sectionType, newSection);  //TODO: review details before restoring 
				}
		
				newSection.setAuditID(newAudit); // TODO: uncomment when truly working
			// *** SAVE ***									
					sectionService.save(newSection); // TODO: reactivate when code looks good
			
			// 		// NOTE: NOT set at this time  - leave as NULL (RJM - 12/28/12)
			//		newSection.setSuccessorId(null);
					
					logger.info("Section - Order# " + nextOrderNumber + " is updated!");
					extResult.setData(newSection);
					extResult.setMsg("Section has been Added!");
			
			}else if ( sectionStatus.equals("EDIT")) {
			
				Section currentSection = (Section) sectionService.get(Section.class, currSectionId);
			logger.info("*** Going for edit of the section");
			 // update Audit records of this change 
			Audit newAudit = updateAudit(reasonForChange, user.getFullName(), new Date());
			
			// STEP #1: initial creation of new Section 
			Section newSection = new Section();
			String newSectionId = newSection.getId();
			
			// STEP #2: update old Section record
			//Section oldSection = (Section) sectionService.get(Section.class, oldSectionId);
			currentSection.setActive(0);
			currentSection.setSuccessorId(newSectionId);
			currentSection.setModifiedBy(user.getFullName()); 
			currentSection.setModifiedDate(new Date());
			currentSection.setAuditID(newAudit);
			sectionService.save(currentSection); // TODO: reactivate when code looks good 

			// STEP #3: complete new Section creation 
			newSection.setText(sectionText);
			newSection.setSectionType(currentSection.getSectionType()); // sectionType);

			// Check if new rule was applied:
		/*	if (!oldRuleId.equalsIgnoreCase(ruleId))
			{
				// the Rule has been changed 
				Rules oldRule = (Rules) ruleService.get(Rules.class, oldRuleId);
				Rules newRule = (Rules) ruleService.get(Rules.class, ruleId);
				Set<Rules> oldRulesTree = oldSection.getRulesSet();
				oldRulesTree.add(newRule);
				oldRulesTree.remove(oldRule);
				newSection.setRulesSet(oldRulesTree);
			}
			else {
				newSection.setRulesSet(oldSection.getRulesSet());
			}*/
			
			
			
			String[] strings = rulesString.split(";");
			TreeSet ruleSet = new TreeSet();
			for ( int i = 0 ; i < strings.length ; i++) {
				
				logger.info("**** string " + strings[i]);
				
				// split again 
			// get rule by rule name --- 
				
				Rules rule = ssowService.getRuleByName(strings[i]);
				
				if ( rule != null ) {
					ruleSet.add(rule);
				}
			//	and then add that rule to the the new sdrl 
			//	String[] valuePair = strings[i].split("=>") ;
		
			}
	
			newSection.setRulesSet(ruleSet);

			newSection.setActive(1);
			newSection.setOrderNumber(currentSection.getOrderNumber()); 
			newSection.setSectionNumber(currentSection.getSectionNumber()); 
			newSection.setChildrenSet(currentSection.getChildrenSet());
			newSection.setModifiedBy(user.getFullName()); 
			newSection.setModifiedDate(new Date());
			newSection.setCreatedBy(user.getFullName()); 
			newSection.setCreatedDate(new Date()); //oldSection.getCreatedDate());

			logger.info("Section - Order# " + currentSection.getOrderNumber() + " is updated!");
			sectionService.save(newSection); // TODO: reactivate when code looks good

			 // STEP #4: update Parent Child Section xrefs 
			List parentList = sectionService.getParentXrefs(currSectionId);
			Iterator parentIter = parentList.iterator();
			while (parentIter.hasNext()) {
				SectionTreeXref parentXref = (SectionTreeXref) parentIter.next();
				String parentId = parentXref.getParentId();
				Section parentSection = (Section) sectionService.get(Section.class, parentId);

				Set<Section> parentTree = parentSection.getChildrenSet();
				parentTree.add(newSection);
				parentTree.remove(currentSection);
				parentSection.setChildrenSet(parentTree);
				sectionService.save(parentSection); // TODO: reactivate when code looks good
				logger.info("Parent Section - ID# " + parentSection.getId() + " is updated!");
			}
			logger.info("Child Section - ID# " + currentSection.getId() + " is updated!");

			extResult.setData(newSection);
			extResult.setMsg("Section is updated!");
			
			
			
		}else if (sectionStatus.equals("MAIN")){
			
		//	Rules rule = (Rules) ruleService.get(Rules.class, ruleId); 
			int nextOrderNumber = sectionService.getNextOrderNumber();  
			String nextHeaderNumber = sectionService.getNextHeaderNumber();// TODO: get next HeaderNumber instead
			
			// STEP #1: create new Section 
			Section newSection = new Section();
			//String newSectionId = newSection.getId();
			
			newSection.setActive(1);
			newSection.setOrderNumber(nextOrderNumber);
			newSection.setText(sectionText);
			newSection.setSectionNumber(nextHeaderNumber); // sectionNumber);
			
			// sectionTypeId
			newSection.setSectionType(SectionType.HEADER_SECTION);

		//	final Rules newRule = (Rules) ruleService.get(Rules.class, ruleId);
		//	Set<Rules> newRulesTree = new HashSet<Rules>() {{ add(newRule); }};
			//newSection.setRulesSet(newRulesTree);

			
			String[] strings = rulesString.split(";");
			TreeSet ruleSet = new TreeSet();
			for ( int i = 0 ; i < strings.length ; i++) {
				
				logger.info("**** string " + strings[i]);
				
				// split again 
			// get rule by rule name --- 
				
				Rules rule = ssowService.getRuleByName(strings[i]);
				
				if ( rule != null ) {
					ruleSet.add(rule);
				}
			//	and then add that rule to the the new sdrl 
			//	String[] valuePair = strings[i].split("=>") ;
		
			}
	
			newSection.setRulesSet(ruleSet);
			newSection.setModifiedBy(user.getFullName()); 
			newSection.setCreatedBy(user.getFullName()); 

			Date date = new Date();
			newSection.setCreatedDate(date);
			newSection.setModifiedDate(date);

			 // update Audit records of this change 
			Audit newAudit = updateAudit(reasonForChange, user.getFullName(), date);

	 		// NOTE: NOT set at this time  - leave as NULL (RJM - 12/28/12)
//			newSection.setSuccessorId(null);
			
	        newSection.setAuditID(newAudit);
			sectionService.save(newSection); // TODO: uncomment when truly working
			logger.info("Section - Header # " + nextHeaderNumber + " is updated!");

			
			 // STEP #2: update Parent Child Section xrefs 
			String parentId = "0"; // TODO : get this dynamically  
			Section parentSection = (Section) sectionService.get(Section.class, parentId);

			Set<Section> parentTree = parentSection.getChildrenSet();
			parentTree.add(newSection);
			parentSection.setChildrenSet(parentTree);
//			parentSection.setAuditID(newAudit);
			sectionService.save(parentSection); // TODO: uncomment when truly working
			logger.info("Parent Section - ID# " + parentSection.getId() + " is updated!");

			extResult.setData(newSection);
			extResult.setMsg("Main Section " + newSection.getSectionNumber() + " has been created!");
			
		}
		
		
		return extResult;
	}	
	
    private void updateHeaderSectionTreeXref(String currSectionType, String currSectionId, String newSectionType, Section newSection) {

    	// Step #1: set children of new section to children of currently selected children:
		Section parentSection = (Section) sectionService.get(Section.class, currSectionId);
		

		if (newSectionType.equalsIgnoreCase(SectionType.HEADER_SECTION)) {
			// Step #1: Setup new section with parallel parent's children
			Set <Section> oldChildSet = parentSection.getChildrenSet();
			Set <Section> newChildSet = new HashSet();
			
			Iterator childIter = oldChildSet.iterator();
			while (childIter.hasNext()) {
			
				Section tempSection = (Section) childIter.next();
				newChildSet.add(tempSection);
			}
			newSection.setChildrenSet(newChildSet); // NO LONGER useD - SET UP SectionTreeXref records directly
			
			// Step #2: update parent section of current section with new section:
			List parentList = sectionService.getParentXrefs(currSectionId);
			Iterator parentIter = parentList.iterator();
			while (parentIter.hasNext()) {
				SectionTreeXref parentXref = (SectionTreeXref) parentIter.next();
				String parentId = parentXref.getParentId();
				Section parentSection2 = (Section) sectionService.get(Section.class, parentId);
				
				Set<Section> parentTree = parentSection2.getChildrenSet();
				parentTree.add(newSection);
				parentSection2.setChildrenSet(parentTree);
// *** SAVE ***									
				sectionService.save(parentSection2); // TODO: reactivate when code looks good
				logger.info("Parent Section - ID# " + parentSection2.getId() + " is updated!");
			}
		}
		else if (newSectionType.equalsIgnoreCase(SectionType.INSTRUCTION_SECTION) ||
				newSectionType.equalsIgnoreCase(SectionType.PARAGRAPH_SECTION) ||
				newSectionType.equalsIgnoreCase(SectionType.TABLE_SECTION) ||
				newSectionType.equalsIgnoreCase(SectionType.SUB_SECTION)) {
			
			// Step #1: update parent sections of the same section number:
			List parentList = sectionService.getParentXrefs(currSectionId);
			Iterator parentIter = parentList.iterator();
			while (parentIter.hasNext()) {
				SectionTreeXref parentXref = (SectionTreeXref) parentIter.next();
				String parentId = parentXref.getParentId();
				Section parentSection2 = (Section) sectionService.get(Section.class, parentId);
				Set<Section> parentChildList2 = parentSection2.getChildrenSet();
				Iterator parentChildIter2 = parentChildList2.iterator();
				while (parentChildIter2.hasNext()) {
					Section parentChildSection = (Section) parentChildIter2.next();
					if (parentChildSection.getSectionNumber().equalsIgnoreCase(parentSection.getSectionNumber())) {
						Set<Section> parentTree = parentChildSection.getChildrenSet();
						parentTree.add(newSection);
						parentChildSection.setChildrenSet(parentTree);
//						parentSection2.setAuditID(newAudit);
		// *** SAVE ***									
						sectionService.save(parentChildSection); // TODO: reactivate when code looks good
						logger.info("Parent Section - ID# " + parentChildSection.getId() + " is updated!");
					}
				} // while-loop
			} // while-loop
		}
    	
    } // updateHeaderSectionTreeXref
    
    private void updateSubsectionSectionTreeXref(String currSectionType, String currSectionId, String newSectionType, Section newSection) {
    	String sectionType = newSection.getSectionType();
		Section parentSection = (Section) sectionService.get(Section.class, currSectionId);

		if (newSectionType.equalsIgnoreCase(SectionType.HEADER_SECTION)) {
			// Step #1: Setup new sub-section with parallel parent's children
			Set <Section> oldChildSet = parentSection.getChildrenSet();
			Set <Section> newChildSet = new HashSet();
			
			Iterator childIter = oldChildSet.iterator();
			while (childIter.hasNext()) {
			
				Section tempSection = (Section) childIter.next();
				newChildSet.add(tempSection);
			}
			newSection.setChildrenSet(newChildSet); // NO LONGER useD - SET UP SectionTreeXref records directly
			
			// Step #2: update parent section of current section with new section:
			List parentList = sectionService.getParentXrefs(currSectionId);
			Iterator parentIter = parentList.iterator();
			while (parentIter.hasNext()) {
				SectionTreeXref parentXref = (SectionTreeXref) parentIter.next();
				String parentId = parentXref.getParentId();
				Section parentSection2 = (Section) sectionService.get(Section.class, parentId);
				
				Set<Section> parentTree = parentSection2.getChildrenSet();
				parentTree.add(newSection);
				parentSection2.setChildrenSet(parentTree);
//				parentSection2.setAuditID(newAudit);
// *** SAVE ***									
				sectionService.save(parentSection2); // TODO: reactivate when code looks good
				logger.info("Parent Section - ID# " + parentSection2.getId() + " is updated!");
			}
		
		} else 

		if ((sectionType.equalsIgnoreCase(SectionType.INSTRUCTION_SECTION)) ||
			(sectionType.equalsIgnoreCase(SectionType.PARAGRAPH_SECTION)) ||
			(sectionType.equalsIgnoreCase(SectionType.TABLE_SECTION)) ||
			(sectionType.equalsIgnoreCase(SectionType.SUB_SECTION)) )
		{
			// Step #1: update parent sections of the same section number:
			List parentList = sectionService.getParentXrefs(currSectionId);
			
			if (parentList.size() > 0) {
				SectionTreeXref parentXref = (SectionTreeXref) parentList.get(0);
				String parentId = parentXref.getParentId();
				Section parentSection2 = (Section) sectionService.get(Section.class, parentId);
				Set<Section> parentChildList2 = parentSection2.getChildrenSet();
				
				Iterator parentChildIter2 = parentChildList2.iterator();
				while (parentChildIter2.hasNext()) {
					Section parentChildSection = (Section) parentChildIter2.next();
					if (parentChildSection.getSectionNumber().equalsIgnoreCase(parentSection.getSectionNumber())) {
						Set<Section> parentTree = parentChildSection.getChildrenSet();
						parentTree.add(newSection);
						parentChildSection.setChildrenSet(parentTree);
// *** SAVE ***									
						sectionService.save(parentChildSection); // TODO: reactivate when code looks good
						logger.info("Parent Section - ID# " + parentChildSection.getId() + " is updated!");
					}
				} // while-loop
			}
		}
    	
    } // updateSubsectionSectionTreeXref
	
	
	@RequestMapping(value = "/deleteSection", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult deleteSection(Locale locale, HttpServletRequest request,
			//@RequestParam("reasonForChange") String reasonForChange, 
			@RequestParam("sectionId") String oldSectionId) { // ,

		logger.info("In the deleteSection  method ");

		ExtResult extResult = new ExtResult();
		User user = (User) request.getSession().getAttribute("user");

		// STEP #1: update current Section 
		Section oldSection = (Section) sectionService.get(Section.class, oldSectionId);
		oldSection.setActive(0);
		oldSection.setModifiedBy(user.getFullName()); 
		
		Date date = new Date();
		oldSection.setModifiedDate(date);
		
		 // update Audit records of this change 
		Audit newAudit = updateAudit("Section Deleted By The User", user.getFullName(), date); // TODO: reactivate when code looks good
		oldSection.setAuditID(newAudit); // TODO: reactivate when code looks good

		sectionService.save(oldSection); // TODO: activate when all is working properly RJM - 12/30/12

		 // STEP #2: update Parent Child Section xrefs for section types (except for Header & Subsection types)
		String oldSectionType = oldSection.getSectionType();
		if (oldSectionType.equalsIgnoreCase(SectionType.INSTRUCTION_SECTION) ||
			oldSectionType.equalsIgnoreCase(SectionType.PARAGRAPH_SECTION) ||
			oldSectionType.equalsIgnoreCase(SectionType.TABLE_SECTION) ) {

			List parentList = sectionService.getParentXrefs(oldSectionId);
			Iterator parentIter = parentList.iterator();
			while (parentIter.hasNext()) {
				SectionTreeXref parentXref = (SectionTreeXref) parentIter.next();
				String parentId = parentXref.getParentId();
				Section parentSection = (Section) sectionService.get(Section.class, parentId);

				Set<Section> parentTree = parentSection.getChildrenSet();
				parentTree.remove(oldSection);
				parentSection.setChildrenSet(parentTree);
//				parentSection.setAuditID(newAudit);
				sectionService.save(parentSection); // TODO: reactivate when code looks good
				logger.info("Parent Section - ID# " + parentSection.getId() + " is updated!");
			}
			
		}
			
		logger.info("Section - ID# " + oldSection.getId() + " is deleted!");
		extResult.setData(oldSection);
		extResult.setMsg("Section has been Deleted!");
		return extResult;
	}	


	@RequestMapping(value = "/addMainSection", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult addSection(Locale locale, HttpServletRequest request,
			@RequestParam("sectionText2") String sectionText,
			@RequestParam("reasonForChange") String reasonForChange, 
			@RequestParam("idRuleHidden") String ruleId) {

		logger.info("In the addMainSection  method ");

		ExtResult extResult = new ExtResult();
		User user = (User) request.getSession().getAttribute("user");
		
		Rules rule = (Rules) ruleService.get(Rules.class, ruleId); 
		int nextOrderNumber = sectionService.getNextOrderNumber();  
		String nextHeaderNumber = sectionService.getNextHeaderNumber();// TODO: get next HeaderNumber instead
		
		// STEP #1: create new Section 
		Section newSection = new Section();
		String newSectionId = newSection.getId();
		
		newSection.setActive(1);
		newSection.setOrderNumber(nextOrderNumber);
		newSection.setText(sectionText);
		newSection.setSectionNumber(nextHeaderNumber); // sectionNumber);
		
		// sectionTypeId
		newSection.setSectionType(SectionType.HEADER_SECTION);

		final Rules newRule = (Rules) ruleService.get(Rules.class, ruleId);
		Set<Rules> newRulesTree = new HashSet<Rules>() {{ add(newRule); }};
		newSection.setRulesSet(newRulesTree);

		newSection.setModifiedBy(user.getFullName()); 
		newSection.setCreatedBy(user.getFullName()); 

		Date date = new Date();
		newSection.setCreatedDate(date);
		newSection.setModifiedDate(date);

		 // update Audit records of this change 
		Audit newAudit = updateAudit(reasonForChange, user.getFullName(), date);

 		// NOTE: NOT set at this time  - leave as NULL (RJM - 12/28/12)
//		newSection.setSuccessorId(null);
		
        newSection.setAuditID(newAudit);
		sectionService.save(newSection); // TODO: uncomment when truly working
		logger.info("Section - Header # " + nextHeaderNumber + " is updated!");

		
		 // STEP #2: update Parent Child Section xrefs 
		String parentId = "0"; // TODO : get this dynamically  
		Section parentSection = (Section) sectionService.get(Section.class, parentId);

		Set<Section> parentTree = parentSection.getChildrenSet();
		parentTree.add(newSection);
		parentSection.setChildrenSet(parentTree);
//		parentSection.setAuditID(newAudit);
		sectionService.save(parentSection); // TODO: uncomment when truly working
		logger.info("Parent Section - ID# " + parentSection.getId() + " is updated!");

		extResult.setData(newSection);
		extResult.setMsg("Main Section has been created!");
		
		return extResult;
	}	

	@RequestMapping(value = "/undeleteSection", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult undeleteSection(Locale locale, HttpServletRequest request,
			//@RequestParam("reasonForChange") String reasonForChange, 
			@RequestParam("sectionId") String oldSectionId) { // ,

		logger.info("In the undeleteSection  method ");

		ExtResult extResult = new ExtResult();
		User user = (User) request.getSession().getAttribute("user");

		// STEP #1: update current Section 
		Section oldSection = (Section) sectionService.get(Section.class, oldSectionId);
		oldSection.setActive(1);
		oldSection.setModifiedBy(user.getFullName()); 
		
		Date date = new Date();
		oldSection.setModifiedDate(date);

		 // update Audit records of this change 
		Audit newAudit = updateAudit("Section Undeleted By User", user.getFullName(), date);
		
		oldSection.setAuditID(newAudit);
		sectionService.save(oldSection); // TODO: activate when all is working properly RJM - 12/30/12

		logger.info("Section - ID# " + oldSection.getId() + " is undeleted!");
		extResult.setData(oldSection);
		extResult.setMsg("Section has been Undeleted!");
		return extResult;
	}	

	private Audit updateAudit(String reasonForChange, String createdBy, Date createdDate) {
		
		Audit audit = new Audit();
		audit.setReasonForChange(reasonForChange);
		audit.setCreatedBy(createdBy);
		audit.setCreatedDate(createdDate);
		
		AuditType auditType = (AuditType) auditTypeService.get(AuditType.class, AuditType.SECTION);
		audit.setAudType(auditType);
		
		auditService.save(audit);
		logger.info("Audit record#: " + audit.getId() + " was created for a Section change!");
		
		return audit;
		
	}


	private String getNextSectionNumber2(String parentSectionId) {
		
		Section parentSection = (Section) sectionService.get(Section.class, parentSectionId);
		
		int nextNumber = 0;
		Iterator childIter = parentSection.getChildrenSet().iterator();
		while (childIter.hasNext())
		{
			Section section = (Section) childIter.next();
			if (section.getSectionType().equalsIgnoreCase(SectionType.SUB_SECTION)) {
				int currSectionNumber = Integer.parseInt(section.getSectionNumber());
				
				if (currSectionNumber > nextNumber) {
					nextNumber = currSectionNumber;
				}
			}
		}
		nextNumber++;
		String nextNumberValue = nextNumber + "";
		
		return nextNumberValue;
	}

}
