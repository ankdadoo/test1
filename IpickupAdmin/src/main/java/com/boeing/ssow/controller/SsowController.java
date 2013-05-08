package com.boeing.ssow.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.rtf.RTFEditorKit;



import org.apache.commons.lang.StringUtils;
import org.dom4j.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boeing.ssow.model.Acronyms;
import com.boeing.ssow.model.Audit;
import com.boeing.ssow.model.AuditType;
import com.boeing.ssow.model.CheckList;
import com.boeing.ssow.model.CheckListGroup;
import com.boeing.ssow.model.CheckListProgramXref;
import com.boeing.ssow.model.CheckListValue;
import com.boeing.ssow.model.Dictionary;
import com.boeing.ssow.model.ExtResult;
import com.boeing.ssow.model.JsonCheckList;
import com.boeing.ssow.model.JsonChkValue;
import com.boeing.ssow.model.JsonRules;

import com.boeing.ssow.model.Rules;
import com.boeing.ssow.model.RulesCriteriaXref;
import com.boeing.ssow.model.Sdrl;
import com.boeing.ssow.model.Section;
import com.boeing.ssow.model.Ssow;
import com.boeing.ssow.model.SsowCheckListXref;
import com.boeing.ssow.model.SsowSearchCriteria;
import com.boeing.ssow.model.SsowStatus;
import com.boeing.ssow.model.SsowStatusTypes;
import com.boeing.ssow.model.User;
import com.boeing.ssow.service.AuditService;
import com.boeing.ssow.service.AuditTypeService;
import com.boeing.ssow.service.CheckListService;

import com.boeing.ssow.service.QuestionTypeService;
import com.boeing.ssow.service.SectionService;
import com.boeing.ssow.service.SsowService;
import com.boeing.ssow.service.UserService;
import com.boeing.ssow.utils.SsowCacheUtil;
import com.boeing.ssow.utils.SsowSecurityUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/ssow")
public class SsowController {
	
	private static final Logger logger = LoggerFactory.getLogger(SsowController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private SsowService ssowService;
	
	//@Autowired
	//private ProgramService programService ;
	
	@Autowired 
	private SectionService sectionService;
	
	@Autowired
	private AuditTypeService auditTypeService;
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private QuestionTypeService questionTypeService;
	
	
	@Autowired 
	private CheckListService checkListService ;
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(@RequestParam("action") String action , @RequestParam("ssowId") String ssowId , 
			HttpServletRequest request) {
		logger.info("Going to show ssow screen ");
		
		logger.info("Action " + action);
		
		logger.info("Ssow Id " + ssowId);
		
		request.setAttribute("ssowId" , ssowId);
		request.setAttribute("action", action);
		
		
		if (! SsowSecurityUtil.validateUserInSession(request)) {
			return "home";
		}
		return "ssow";
	}
	
	
	
	@RequestMapping(value = "/loadSsow", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult loadSsow(@RequestParam("ssowId") String ssowId , @ RequestParam("action") String action , 
			HttpServletRequest request) {
		logger.info("In the loading ssow  method ");
		
	
		
		
		logger.info("*** username " + ssowId);
		logger.info("**  password " + action);
		ExtResult extResult = new ExtResult();
		
		User user = (User) request.getSession().getAttribute("user");
		
		
		
		if ( action.equals("edit")) {
			
			// load the ssow with the id and return to the screen 
			
			Ssow ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
			
			if ( ssow != null ) {
				
				extResult.setData(ssow);
				
			}else {
				// throw excepton as cant find the ssow 
				logger.error("Cant find the ssow with Id " + ssowId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be retrieved for ssowId : "+ ssowId + ", Please try again later.");
			}
		}else if ( action.equals("clone")) {
			
			// get the ssow and then copy it and then save the new one and return to the user - 
			// am i going to keep the ssow numbe same and what about rev nbr - I might 
			// get rev Nbr so we need a seperate button - s
			Ssow ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
			
			if ( ssow != null ) {
				
				
				Ssow cloneSsow = ssow.copySsow();
				
				cloneSsow.setActive(new Integer (1));
				cloneSsow.setCreatedBy(user.getFullName());
				cloneSsow.setCreatedDate(new Date());
				cloneSsow.setStatus(SsowStatus.InWork);
				cloneSsow.setModifiedBy(user.getFullName());
				cloneSsow.setModifiedDate(new Date());
				//cloneSsow.setSsowNumber("SSOW-" + System.currentTimeMillis());
				
				
				
				
				ssowService.save(cloneSsow);
				
				
				if ( ssow.getSsowCheckList() != null ) {
					
					//Set newXrefSet = new TreeSet();
					
					// need to copy this out as well 
					
					Iterator it = ssow.getSsowCheckList().iterator();
					
					while ( it.hasNext()) {
						
						
						
						SsowCheckListXref xref = ( SsowCheckListXref)  it.next();
						
						SsowCheckListXref newXref = xref.copyXref();
						newXref.setSsow(cloneSsow);
						ssowService.save(newXref);
					//	newXrefSet.add(newXref);
					}
					
				//	ssow.setSsowCheckList(newXrefSet);
					
				}
				extResult.setData(cloneSsow);
				
			}else {
				// throw excepton as cant find the ssow 
				logger.error("Cant find the ssow with Id " + ssowId);
			}
		}else if ( action.equals("rev")) {
			
			// get the ssow and then copy it and then save the new one and return to the user - 
			// am i going to keep the ssow numbe same and what about rev nbr - I might 
			// get rev Nbr so we need a seperate button - s
			Ssow ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
			
			if ( ssow != null ) {
				
				Ssow cloneSsow = ssow.copySsow();
				
				
				cloneSsow.setRevNbr(new Integer (cloneSsow.getRevNbr().intValue() + 1));
				cloneSsow.setActive(new Integer (1));
				cloneSsow.setCreatedBy(user.getFullName());
				cloneSsow.setCreatedDate(new Date());
				cloneSsow.setModifiedBy(user.getFullName());
				cloneSsow.setModifiedDate(new Date());
				cloneSsow.setStatus(SsowStatus.InWork);
				cloneSsow.setSsowNumber(ssow.getSsowNumber());
				//cloneSsow.setSsowNumber("SSOW-" + System.currentTimeMillis());
				ssowService.save(cloneSsow);
				
				ssow.setActive(new Integer (0));
				
				ssowService.save(ssow);
				
				
				if ( ssow.getSsowCheckList() != null ) {
					
					//Set newXrefSet = new TreeSet();
					
					// need to copy this out as well 
					
					Iterator it = ssow.getSsowCheckList().iterator();
					
					while ( it.hasNext()) {
						
						
						
						SsowCheckListXref xref = ( SsowCheckListXref)  it.next();
						
						SsowCheckListXref newXref = xref.copyXref();
						newXref.setSsow(cloneSsow);
						ssowService.save(newXref);
					//	newXrefSet.add(newXref);
					}
					
				//	ssow.setSsowCheckList(newXrefSet);
					
				}
				extResult.setData(cloneSsow);
				
			}else {
				// throw excepton as cant find the ssow 
				logger.error("Cant find the ssow with Id " + ssowId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be retrieved for ssowId : "+ ssowId + ", Please try again later.");
			}
		}
		
		
		else {
			
			if ( !StringUtils.isEmpty(ssowId) &&  !(( "NULL").equals(ssowId))) {
				
				Ssow ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
				
				if ( ssow != null ) {
					
					extResult.setData(ssow);
				}
			}else {
			
					// create a new one - and save it - 
					Ssow ssow = new Ssow();
					ssow.setRevNbr(new Integer (0));
					//ssow.setId("");
					ssow.setActive(new Integer (1));
					ssow.setCreatedBy(user.getFullName());
					ssow.setCreatedDate(new Date());
					ssow.setModifiedBy(user.getFullName());
					ssow.setModifiedDate(new Date());
					ssow.setStatus(SsowStatus.InWork);
					
					Date currentDate = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmm");
			    	//queue.setSubmitDate( formatter.format(rec.getCreatedDate()));
					
				//	ssow.setSsowNumber("SSOW-PW-" + formatter.format(currentDate));
					ssowService.save(ssow);
					
				//	request.setAttribute("action", "edit");
					extResult.setData(ssow);
				//	extResult.setData(null);
					//extResult.setSuccess("false");
					extResult.setMsg("Ssow created successfully for SSOW# : "+ ssow.getSsowNumber());
			}
		}
		
		
		///ListWrapper wrapper = new ListWrapper(list);
		return extResult;
	}
	
	@RequestMapping(value = "/saveBasicQ", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult saveBasicQ(
			
			
			HttpServletRequest req
			
			) {
		
		
		ExtResult result = new ExtResult();
		
		String ssowId = (String )req.getParameter("ssowId");
		
		logger.info("*** saving basic Q" + ssowId);
		
		User user = (User) req.getSession().getAttribute("user");
		
		
		
		Ssow ssow = null;
		if ( !StringUtils.isEmpty(ssowId)) {
		
			 ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
		
			 logger.info("ssow " + ssow);
		
			 if ( ssow != null ) {
		
		
			
			ssow.setModifiedDate(new Date());
			ssow.setModifiedBy(user.getFullName());
		
			ssowService.save(ssow);
		
		//	boolean item5AnswerY = false ;
			
			this.procesCheckListResponses(ssow, req );
		}
		}
		
		result.setMsg("Ssow# :" + ssow.getSsowNumber() + " Basic Q saved successfully.");
		return result;
		
	}
	
	private SsowCheckListXref getExistingSsowCheckListXrefRecord( Ssow ssow , String checkListId ) {
		
		Set chkSet = ssow.getSsowCheckList();
		
		SsowCheckListXref  returnXref = null;
		if ( chkSet != null ) {
			
			Iterator it = chkSet.iterator();
			
			while ( it.hasNext()) {
				
				SsowCheckListXref xref = ( SsowCheckListXref ) it.next();
				
				if ( xref.getCheckList().getId().equals(checkListId)) {
					
					returnXref =  xref ;
					break;
				}
				
			}
			
			if ( returnXref == null) {
				returnXref =  new SsowCheckListXref();
			}
			
			
		}else {
			returnXref =  new SsowCheckListXref(); 
		}
		
		
		return returnXref;
	
	}
	
	@RequestMapping(value = "/saveAdvancedQ", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult saveAdvancedQ(
			
			//@RequestParam("ssowId") String ssowId,
			HttpServletRequest req
			
			) {
		
		
		ExtResult result = new ExtResult();
		
		String ssowId = (String )req.getParameter("ssowId");
		
		logger.info("*** saving basic Q" + ssowId);
		
		User user = (User) req.getSession().getAttribute("user");
		
		Ssow ssow = null;
		if ( !StringUtils.isEmpty(ssowId)) {
		
			 ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
		
		logger.info("ssow " + ssow);
		
		if ( ssow != null ) {
		
			ssow.setModifiedDate(new Date());
			ssow.setModifiedBy(user.getFullName());
		
			ssowService.save(ssow);
		
		this.procesCheckListResponses(ssow, req);

		}
		}
		
		result.setMsg("Ssow# :" + ssow.getSsowNumber() + " Advanced Q saved successfully.");
		return result;
		
	}
	
	private void procesCheckListResponses  ( Ssow ssow , HttpServletRequest req) {
		
		
		Enumeration enumt = req.getParameterNames(); 
		

		boolean item5AnswerY = false ;
		while ( enumt.hasMoreElements()) {
			
		
			String paramName = (String)enumt.nextElement();
			String value = req.getParameter(paramName);
			if ( !StringUtils.isEmpty(value) ) {
				logger.info("Param has value " + paramName + "  " + value);
				if ( paramName.contains("ssow_") ) {						
				
					
					String tobeput = paramName.replace("ssow_", "");
					
					
					logger.info("Param name " + paramName +"  left over" + tobeput);
				
					
					if (! value.equals("NA")) {
						
						// only want to save it the value is Y or N , NA is equivalent to not selecting answer - 
						CheckList chk = (CheckList)ssowService.get(CheckList.class, tobeput);
						
						if ( chk != null) {
							
							// check if the checklist Q is item 5 and if yes then is its answer Y then we need to set every in the town to be NA 
							System.out.println("*** checklist item number " + chk.getItemNumber());
							if ( chk.getItemNumber().equals("CL005")) {
								System.out.println("*** found CL 005 item number " + value );
								if ( value.equals("Y")) {
								
								// get all the checklist which is advanced - go through it 
								// find if the ssow has answer for it and if yes then just clear it out - simple - 
								 List returnList = ssowService.getAdvancedQList();
									logger.info("**** advanced q list size " + returnList.size());
									
									for ( int i = 0 ; i < returnList.size() ; i++) {
										
										CheckList chktobedeleted = (CheckList)returnList.get(i);
										
										SsowCheckListXref xref = this.getExistingSsowCheckListXrefRecord(ssow, chktobedeleted.getId());
										
										
										
										ssowService.delete(xref);
									}
									
									//refresh ssow so that its clear of other things -  
									//ssow = (Ssow)ssowService.get(Ssow.class , ssow.getId());
								}
								
							}
							
							SsowCheckListXref xref = this.getExistingSsowCheckListXrefRecord(ssow, tobeput);
	
							xref.setSsow(ssow);
							xref.setCheckList(chk);
							xref.setAnswer(value);
							
							ssowService.save(xref);
							
							
						}
					}else {
						
						// if the value is NA then we take the answer existing and delete it 
						CheckList chk = (CheckList)ssowService.get(CheckList.class, tobeput);
						
						if ( chk != null) {
						
							SsowCheckListXref xref = this.getExistingSsowCheckListXrefRecord(ssow, tobeput);
	
							ssowService.delete(xref);
							
							
						}
					}
					
					// take this id - create the new object == or maybe get the checklist by the id 
					// se the value and save - 
				}
			}
		}
	}
	
	@RequestMapping(value = "/saveSsow", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult saveSsow(
			@RequestParam("ssowId") String ssowId,
			@RequestParam("ssowNumberC") String ssowNumber,
			//@RequestParam("ssowRevC") String ssowRev , 
			//@RequestParam("programDDHiddenC") String program , 
		//	@RequestParam("documentTypeDDHiddenC") String documentType , 
			@RequestParam("supplierC") String supplier , 
		//	@RequestParam("statusC") String status,
			@RequestParam("sreC") String sre,
			@RequestParam("procNumberC") String procNumber,
			@RequestParam("leadFocalC") String leadFocal,
			@RequestParam("iptTeamLeadC") String iptTeamLead,
			@RequestParam("iptSupplierMgrC") String iptSupplierMgr ,
			@RequestParam("supplierMgmtMgrC") String supplierMgmtMgr,
			@RequestParam("programManagerC") String programManager,
			@RequestParam("procurementAgentC") String procurementAgent,
			@RequestParam("ssowDescription") String ssowDescription,
			HttpServletRequest request
			) {
				logger.info("In the save ssow  method ");
				
				User user = (User) request.getSession().getAttribute("user");
				
				ExtResult extResult = new ExtResult();
				logger.info("** ssow id " + ssowId);
				logger.info("** ssow number  " + ssowNumber);
				logger.info("** userame " + user.getUsername());
				Ssow ssow = null;
				if ( !StringUtils.isEmpty(ssowId)) {
				
					 ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
				
				logger.info("ssow " + ssow);
				
				if ( ssow != null ) {
					 
					
					
					
					ssow.setSsowNumber(ssowNumber);
					
					
				//	ssow.setRevNbr(new Integer ( ssowRev));
				//	ssow.setModifiedDate(new Date());
				//	ssow.setModifiedBy(user.getFullName());
				//	ssow.setProgram(program);
					ssow.setSupplier(supplier);
				//	ssow.setDocumentType(documentType);
				//	ssow.setStatus(status);
					ssow.setSre(sre);
					ssow.setProcSpecNumber(procNumber);
					ssow.setLeadEngFocal(leadFocal);
					ssow.setIptTeamLead(iptTeamLead);
					ssow.setIptSupplierMgr(iptSupplierMgr);
					ssow.setSupplierMgmtMgr(supplierMgmtMgr);
					ssow.setProgramMgr(programManager);
					ssow.setProcurementAgent(procurementAgent);
					ssow.setDescription(ssowDescription);
					ssow.setModifiedDate(new Date());
					ssow.setModifiedBy(user.getFullName());
				
					
					// check that the ssow number being entered is unique or not - 
					
					List duplicateSsowNumberList = ssowService.getSsowListBySsowNumer(ssowNumber);
					
					logger.info("***** Number of duplicates found " + duplicateSsowNumberList.size());
					if ( duplicateSsowNumberList != null && duplicateSsowNumberList.size() > 0 ) {
						if ( duplicateSsowNumberList.size() > 1 ) {
							// which means there are more than 1 record for the same name so an issue 
							// throw an error and not save - 
							// ERROR condition - 
							extResult.setSuccess("false");
							extResult.setMsg("Ssow could not be saved successfully. Ssow Number is not Unique.  Ssow# : " + ssow.getSsowNumber());
						}else {
							// which means only one record but the possibility that i could be on the same record - so 
							// check if i have the same guy and if yes then save otherwise error again 
							
							Ssow duplicateSsow = (Ssow)duplicateSsowNumberList.get(0);
							if ( duplicateSsow != null ) {
							logger.info("coming here 1 ");
								if ( duplicateSsow.getId().equals(ssow.getId())) {
									// means we are dealing with same sssow so its ok to save 
									logger.info("coming here 3 ");
									ssowService.save(ssow);
									extResult.setMsg("Ssow saved successfully. Ssow# : " + ssow.getSsowNumber());
								}else {
									logger.info("coming here 4");
									// ERROR condition -- 
									extResult.setSuccess("false");
									extResult.setMsg("Ssow could not be saved successfully. Ssow Number is not Unique.  Ssow# : " + ssow.getSsowNumber());
								}
							}else {
								logger.info("coming here 2 ");
								ssowService.save(ssow);
								extResult.setMsg("Ssow saved successfully. Ssow# : " + ssow.getSsowNumber());
							}
							
						}
					}else {
						// ssow number is clear so save it without care 
						ssowService.save(ssow);
						extResult.setMsg("Ssow saved successfully. Ssow# : " + ssow.getSsowNumber());
					}
				//	ssowService.save(ssow);
					//ssowService.save(ssow);
					
					extResult.setData(ssow);
					//extResult.setSuccess("false");
					
					
				}else {
					logger.error("Could not find ssow for the ssow id " + ssowId);
					extResult.setData(null);
					extResult.setSuccess("false");
					extResult.setMsg("Ssow could not be saved for ssowId : "+ ssowId + ", Please try again later.");
				}
				}else {
					logger.error("Could not find ssow for the ssow id " + ssowId);
					extResult.setData(null);
					extResult.setSuccess("false");
					extResult.setMsg("Ssow could not be saved for ssowId : "+ ssowId + ", Please try again later.");
				}
	
		
		
	
	
		return extResult;
	}
	
	
	
	@RequestMapping(value = "/deleteRule", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult deleteRule(	@RequestParam("ruleId") String ruleId ) {
		
		ExtResult extResult = new ExtResult();
		logger.info("** ssow id " + ruleId);
		
		Rules rule  = null;
		if ( !StringUtils.isEmpty(ruleId)) {
			
				// means we are in edit mode ---- 
			logger.info("*** Editing the Existing Rule ");
				 rule = (Rules)ssowService.get(Rules.class , ruleId);
			
			logger.info("ssow " + rule );
			
			if ( rule  != null ) {
				 
				logger.info("*** Rule found " + rule.getName());
				
				rule.setActive(new Integer (0));
				
				
				
				// go through all sdrl and remove the references --- 
				// so go through the sdrl and then remove the rules from the set and save it - and see what happens b 
				
				ssowService.save(rule);
				
				
				List list = ssowService.getSectionByRuleId(rule.getId()) ;
				
				logger.info("*** sdrl found impacted by this rule " + list.size());
				
				
				for ( int i = 0 ; i < list.size() ; i++) {
					
					
				//	Object object = list.get(i);
				//	logger.info("**** ibject class" + object.getClass().toString());
					Section section = (Section)list.get(i);
					
					Set rulesSet = section.getRulesSet();
					
					if ( rulesSet != null ) {
						
						rulesSet.remove(rule); // removed the old rule 
						//rulesSet.add(newRule);
					}
					
					ssowService.save(section);
					
				}
				
				
				 list = ssowService.getSdrlByRuleId(rule.getId()) ;
					
					logger.info("*** sdrl found impacted by this rule " + list.size());
					
					
					for ( int i = 0 ; i < list.size() ; i++) {
						
						
						Object object = list.get(i);
						logger.info("**** ibject class" + object.getClass().toString());
						Sdrl section = (Sdrl)list.get(i);
						
						Set rulesSet = section.getRulesSet();
						
						if ( rulesSet != null ) {
							
							rulesSet.remove(rule); // removed the old rule 
							//rulesSet.add(newRule);
						}
						
						ssowService.save(section);
						
					}
					
					
					 list = ssowService.getDictionaryByRuleId(rule.getId()) ;
					
					logger.info("*** sdrl found impacted by this rule " + list.size());
					
					
					for ( int i = 0 ; i < list.size() ; i++) {
						
						
						Object object = list.get(i);
						logger.info("**** ibject class" + object.getClass().toString());
						Dictionary section = (Dictionary)list.get(i);
						
						Set rulesSet = section.getRulesSet();
						
						if ( rulesSet != null ) {
							
							rulesSet.remove(rule); // removed the old rule 
							//rulesSet.add(newRule);
						}
						
						ssowService.save(section);
						
					}
					
					
					 list = ssowService.getAcronymsByRuleId(rule.getId()) ;
					
					logger.info("*** sdrl found impacted by this rule " + list.size());
					
					
					for ( int i = 0 ; i < list.size() ; i++) {
						
						
						Object object = list.get(i);
						logger.info("**** ibject class" + object.getClass().toString());
						Acronyms section = (Acronyms)list.get(i);
						
						Set rulesSet = section.getRulesSet();
						
						if ( rulesSet != null ) {
							
							rulesSet.remove(rule); // removed the old rule 
							//rulesSet.add(newRule);
						}
						
						ssowService.save(section);
						
					}
				SsowCacheUtil.flush(SsowCacheUtil.getRulesListKey());
				SsowCacheUtil.flush(SsowCacheUtil.getDictionaryListKey());
				SsowCacheUtil.flush(SsowCacheUtil.getSdrlListKey());
				SsowCacheUtil.flush(SsowCacheUtil.getAcronymsListKey());
				
				extResult.setMsg("Rule deleted successfully.Rule# : " + rule.getName());
				extResult.setData(null);
			}else {
				logger.error("Could not find ssow for the ssow id " + ruleId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be saved for ssowId : "+ ruleId + ", Please try again later.");
			}
		}else {
			logger.error("Could not find ssow for the ssow id " + ruleId);
			extResult.setData(null);
			extResult.setSuccess("false");
			extResult.setMsg("Ssow could not be saved for ssowId : "+ ruleId + ", Please try again later.");
		}
		
		
		return extResult;
		
	}
			
			
	
	@RequestMapping(value = "/deleteSdrl", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult deleteSdrl(	@RequestParam("sdrlId") String sdrlId ) {
		
		ExtResult extResult = new ExtResult();
		logger.info("** ssow id " + sdrlId);
		
		Sdrl sdrl  = null;
		if ( !StringUtils.isEmpty(sdrlId)) {
			
				// means we are in edit mode ---- 
			logger.info("*** Editing the Existing Rule ");
				 sdrl = (Sdrl)ssowService.get(Sdrl.class , sdrlId);
			
			logger.info("ssow " + sdrl );
			
			if ( sdrl  != null ) {
				 
				logger.info("*** Rule found " + sdrl.getName());
				
				sdrl.setActive(new Integer (0));
				
				ssowService.save(sdrl);
				
				SsowCacheUtil.flush(SsowCacheUtil.getSdrlListKey());
				extResult.setMsg("Sdrl deleted successfully. Sdrl# : " + sdrl.getName());
				extResult.setData(null);
			}else {
				logger.error("Could not find ssow for the ssow id " + sdrlId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be saved for ssowId : "+ sdrlId + ", Please try again later.");
			}
		}else {
			logger.error("Could not find ssow for the ssow id " + sdrlId);
			extResult.setData(null);
			extResult.setSuccess("false");
			extResult.setMsg("Ssow could not be saved for ssowId : "+ sdrlId + ", Please try again later.");
		}
		
		
		return extResult;
		
	}
			
	@RequestMapping(value = "/deleteDictionary", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult deleteDictionary(	@RequestParam("dictionaryId") String dictionaryId ) {
		
		ExtResult extResult = new ExtResult();
		logger.info("** ssow id " + dictionaryId);
		
		Dictionary sdrl  = null;
		if ( !StringUtils.isEmpty(dictionaryId)) {
			
				// means we are in edit mode ---- 
			logger.info("*** Editing the Existing Rule ");
				 sdrl = (Dictionary)ssowService.get(Dictionary.class , dictionaryId);
			
			logger.info("ssow " + sdrl );
			
			if ( sdrl  != null ) {
				 
				logger.info("*** Rule found " + sdrl.getName());
				
				sdrl.setActive(new Integer (0));
				
				ssowService.save(sdrl);
				
				SsowCacheUtil.flush(SsowCacheUtil.getDictionaryListKey());
				extResult.setMsg("Dictionary deleted successfully. Dictionary# : " + sdrl.getName());
				extResult.setData(null);
			}else {
				logger.error("Could not find ssow for the ssow id " + dictionaryId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be saved for ssowId : "+ dictionaryId + ", Please try again later.");
			}
		}else {
			logger.error("Could not find ssow for the ssow id " + dictionaryId);
			extResult.setData(null);
			extResult.setSuccess("false");
			extResult.setMsg("Ssow could not be saved for ssowId : "+ dictionaryId + ", Please try again later.");
		}
		
		
		return extResult;
		
	}
		
	
	
	
	@RequestMapping(value = "/deleteAcronyms", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult deleteAcronyms(	@RequestParam("acronymsId") String acronymsId ) {
		
		ExtResult extResult = new ExtResult();
		logger.info("** ssow id " + acronymsId);
		
		Acronyms sdrl  = null;
		if ( !StringUtils.isEmpty(acronymsId)) {
			
				// means we are in edit mode ---- 
			logger.info("*** Editing the Existing Rule ");
				 sdrl = (Acronyms)ssowService.get(Acronyms.class , acronymsId);
			
			logger.info("ssow " + sdrl );
			
			if ( sdrl  != null ) {
				 
				logger.info("*** Rule found " + sdrl.getName());
				
				sdrl.setActive(new Integer (0));
				
				ssowService.save(sdrl);
				
				SsowCacheUtil.flush(SsowCacheUtil.getAcronymsListKey());
				extResult.setMsg("Acronyms deleted successfully. Acronym# : " + sdrl.getName());
				extResult.setData(null);
			}else {
				logger.error("Could not find ssow for the ssow id " + acronymsId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be saved for ssowId : "+ acronymsId + ", Please try again later.");
			}
		}else {
			logger.error("Could not find ssow for the ssow id " + acronymsId);
			extResult.setData(null);
			extResult.setSuccess("false");
			extResult.setMsg("Ssow could not be saved for ssowId : "+ acronymsId + ", Please try again later.");
		}
		
		
		return extResult;
		
	}
		
public Audit updateAudit(String reasonForChange, String createdBy, Date createdDate) {
		
		Audit audit = new Audit();
		audit.setReasonForChange(reasonForChange);
		audit.setCreatedBy(createdBy);
		audit.setCreatedDate(createdDate);
		
		AuditType auditType = (AuditType) auditTypeService.get(AuditType.class, AuditType.RULES);
		audit.setAudType(auditType);
		
		auditService.save(audit);
		logger.info("Audit record#: " + audit.getId() + " was created for a Section change!");
		
		return audit;
		
	}
		
	@RequestMapping(value = "/saveRule", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult saveRule(
			@RequestParam("ruleId") String ruleId,
			@RequestParam("criteriaAllTrueSVString") String criteriaAllTrueSVString,
			//@RequestParam("ssowRevC") String ssowRev , 
			@RequestParam("criteriaAnyTrueSVString") String criteriaAnyTrueSVString , 
			@RequestParam("criteriaNotTrueSVString") String criteriaNotTrueSVString , 
			@RequestParam("criteriaTrueSVString") String criteriaTrueSVString , 
			
			@RequestParam("rulesAnyTrueSVString") String rulesAnyTrueSVString , 
			
			@RequestParam("rulesAllTrueSVString") String rulesAllTrueSVString , 
			
			@RequestParam("rulesNotTrueSVString") String rulesNotTrueSVString , 
			@RequestParam("auditReason") String auditReason , 
			HttpServletRequest request
			) {
				logger.info("In the save ssow  method ");
				
				User user = (User) request.getSession().getAttribute("user");
				
				ExtResult extResult = new ExtResult();
				logger.info("** ssow id " + ruleId);
				logger.info("**criteriaAllTrueSVString  " + criteriaAllTrueSVString);
				logger.info("**criteriaAnyTrueSVString  " + criteriaAnyTrueSVString);
				logger.info("**criteriaNotTrueSVString  " + criteriaNotTrueSVString);
				logger.info("**criteriaTrueSVString  " + criteriaTrueSVString);
				logger.info("**criteriaTrueSVString  " + rulesAnyTrueSVString);
				logger.info("**criteriaTrueSVString  " + rulesAllTrueSVString);
				logger.info("**criteriaTrueSVString  " + rulesNotTrueSVString);
				logger.info("*** audit reason " + auditReason);
				//logger.info("** userame " + user.getUsername());
				Rules rule  = null;
				if ( !StringUtils.isEmpty(ruleId)) {
					
					// means we are in edit mode ---- 
				logger.info("*** Editing the Existing Rule ");
					 rule = (Rules)ssowService.get(Rules.class , ruleId);
				
				logger.info("ssow " + rule );
				
				if ( rule  != null ) {
					 
					logger.info("*** Rule found " + rule.getName());
					
					
					
					Rules newRule = new Rules();
					newRule.setActive(new Integer (1));
					newRule.setAuditID(updateAudit(auditReason, user.getFullName(), new Date()));
					newRule.setModifiedBy(user.getFullName());
					newRule.setModifiedDate(new Date());
					// get the rule name ---- 
					
					logger.info("*** rule name " + ssowService.getNextRuleNumber());
					
				
					
					// set the same rule name as old one - 
					newRule.setName(rule.getName());
					//JsonRules jsonR = ssowService.getJsonRules(rule);
					
					// got the json rule 
					
					if (!StringUtils.isEmpty(criteriaTrueSVString)) {
						
				
						newRule.setChecklistCriteriaTrueID(this.createCriteria(criteriaTrueSVString));
					}else {
						newRule.setChecklistCriteriaTrueID(null);
					}
					
					
					
					if (!StringUtils.isEmpty(criteriaAnyTrueSVString)) {
						
						
						newRule.setChecklistCriteriaAnyTrueID(this.createCriteria(criteriaAnyTrueSVString));
					}else {
						newRule.setChecklistCriteriaAnyTrueID(null);
					}
					
					if (!StringUtils.isEmpty(criteriaAllTrueSVString)) {
						
						
						newRule.setChecklistCriteriaAllTrueID(this.createCriteria(criteriaAllTrueSVString));
					}else {
						newRule.setChecklistCriteriaAllTrueID(null);
					}
					
					
					if (!StringUtils.isEmpty(criteriaNotTrueSVString)) {
						
						
						newRule.setChecklistCriteriaAnyNotTrueID(this.createCriteria(criteriaNotTrueSVString));
					}else {
						newRule.setChecklistCriteriaAnyNotTrueID(null);
					}
					
					if (!StringUtils.isEmpty(rulesAllTrueSVString)) {
						
						
						newRule.setRulesCriteriaAllTrueID(this.createRulesCriteria(rulesAllTrueSVString));
					}else {
						newRule.setRulesCriteriaAllTrueID(null);
					}
					
					if (!StringUtils.isEmpty(rulesAnyTrueSVString)) {
						
						
						newRule.setRulesCriteriaAnyTrueID(this.createRulesCriteria(rulesAnyTrueSVString));
					}else {
						newRule.setRulesCriteriaAnyTrueID(null);
					}
					
					if (!StringUtils.isEmpty(rulesNotTrueSVString)) {
						
						
						newRule.setRulesCriteriaNotTrueID(this.createRulesCriteria(rulesNotTrueSVString));
					}else {
						newRule.setRulesCriteriaNotTrueID(null);
					}
					ssowService.save(newRule);
					SsowCacheUtil.flush(SsowCacheUtil.getRulesListKey());
					// inactive old rule 
					rule.setActive(new Integer (0) );
					
					ssowService.save(rule);
					// go through all the sections where this old rule was and replace with new rule id - 
					
					List list = sectionService.getSectionsByRuleId(rule.getId()) ;
					
					logger.info("*** Sections found impacted by this rule " + list.size());
					
					for ( int i = 0 ; i < list.size() ; i++) {
						
						Section section = (Section)list.get(i);
						
						Set rulesSet = section.getRulesSet();
						
						if ( rulesSet != null ) {
							
							rulesSet.remove(rule); // removed the old rule 
							rulesSet.add(newRule);
						}
						
						ssowService.save(section);
						
					}
					
					 list = ssowService.getSdrlByRuleId(rule.getId()) ;
					
					logger.info("*** sdrl found impacted by this rule " + list.size());
					
					
					for ( int i = 0 ; i < list.size() ; i++) {
						
						Sdrl section = (Sdrl)list.get(i);
						
						Set rulesSet = section.getRulesSet();
						
						if ( rulesSet != null ) {
							
							rulesSet.remove(rule); // removed the old rule 
							rulesSet.add(newRule);
						}
						
						ssowService.save(section);
						
					}
					
						 list = ssowService.getAcronymsByRuleId(rule.getId()) ;
					
					logger.info("*** acronyms found impacted by this rule " + list.size());
					
					for ( int i = 0 ; i < list.size() ; i++) {
						
						Acronyms section = (Acronyms)list.get(i);
						
						Set rulesSet = section.getRulesSet();
						
						if ( rulesSet != null ) {
							
							rulesSet.remove(rule); // removed the old rule 
							rulesSet.add(newRule);
						}
						
						ssowService.save(section);
						
					}
					
						 list = ssowService.getDictionaryByRuleId(rule.getId()) ;
					
					logger.info("*** dictionary found impacted by this rule " + list.size());
					
					for ( int i = 0 ; i < list.size() ; i++) {
						
						Dictionary section = (Dictionary)list.get(i);
						
						Set rulesSet = section.getRulesSet();
						
						if ( rulesSet != null ) {
							
							rulesSet.remove(rule); // removed the old rule 
							rulesSet.add(newRule);
						}
						
						ssowService.save(section);
						
					}
					extResult.setMsg("Rule saved successfully.Rule# : " + rule.getName());
					extResult.setData(newRule);
				}else {
					logger.error("Could not find ssow for the ssow id " + ruleId);
					extResult.setData(null);
					extResult.setSuccess("false");
					extResult.setMsg("Ssow could not be saved for ssowId : "+ ruleId + ", Please try again later.");
				}
				}else {
					
					logger.info("*** Creating a new Rule ");
					// means we are in add mode -- so just create a new rule with all the things and done - 
					
					
					
					
					Rules newRule = new Rules();
					newRule.setActive(new Integer (1));
					newRule.setAuditID(updateAudit(auditReason, user.getFullName(), new Date()));
					newRule.setModifiedBy(user.getFullName());
					newRule.setModifiedDate(new Date());
					// get the rule name ---- 
					
					logger.info("*** rule name " + ssowService.getNextRuleNumber());
					
					
					String maxRuleName =  ssowService.getNextRuleNumber();
					
					String maxRuleNumber = maxRuleName.replace("Rule", "");
					
					int maxNumber = new Integer ( maxRuleNumber).intValue();
					
					maxNumber++;
					
					String newRuleName = "Rule" + maxNumber;
					
					newRule.setName(newRuleName);
					//JsonRules jsonR = ssowService.getJsonRules(rule);
					
					// got the json rule 
					
					if (!StringUtils.isEmpty(criteriaTrueSVString)) {
						
				
						newRule.setChecklistCriteriaTrueID(this.createCriteria(criteriaTrueSVString));
					}else {
						newRule.setChecklistCriteriaTrueID(null);
					}
					
					
					
					if (!StringUtils.isEmpty(criteriaAnyTrueSVString)) {
						
						
						newRule.setChecklistCriteriaAnyTrueID(this.createCriteria(criteriaAnyTrueSVString));
					}else {
						newRule.setChecklistCriteriaAnyTrueID(null);
					}
					
					if (!StringUtils.isEmpty(criteriaAllTrueSVString)) {
						
						
						newRule.setChecklistCriteriaAllTrueID(this.createCriteria(criteriaAllTrueSVString));
					}else {
						newRule.setChecklistCriteriaAllTrueID(null);
					}
					
					
					if (!StringUtils.isEmpty(criteriaNotTrueSVString)) {
						
						
						newRule.setChecklistCriteriaAnyNotTrueID(this.createCriteria(criteriaNotTrueSVString));
					}else {
						newRule.setChecklistCriteriaAnyNotTrueID(null);
					}
					
					
					if (!StringUtils.isEmpty(rulesAllTrueSVString)) {
						
						
						newRule.setRulesCriteriaAllTrueID(this.createRulesCriteria(rulesAllTrueSVString));
					}else {
						newRule.setRulesCriteriaAllTrueID(null);
					}
					
					if (!StringUtils.isEmpty(rulesAnyTrueSVString)) {
						
						
						newRule.setRulesCriteriaAnyTrueID(this.createRulesCriteria(rulesAnyTrueSVString));
					}else {
						newRule.setRulesCriteriaAnyTrueID(null);
					}
					
					if (!StringUtils.isEmpty(rulesNotTrueSVString)) {
						
						
						newRule.setRulesCriteriaNotTrueID(this.createRulesCriteria(rulesNotTrueSVString));
					}else {
						newRule.setRulesCriteriaNotTrueID(null);
					}
					ssowService.save(newRule);
					SsowCacheUtil.flush(SsowCacheUtil.getRulesListKey());
					extResult.setData( newRule);
					//logger.error("Could not find ssow for the ssow id " + ruleId);
				//	extResult.setData(null);
					//extResult.setSuccess("false");
					extResult.setMsg("Rule # " + newRule.getName() + " created Successfully.");
				}
	
		
		
	
	
		return extResult;
	}
	
	
	
	
	@RequestMapping(value = "/saveSdrl", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult saveSdrl(
			@RequestParam("sdrlId") String sdrlId,
			@RequestParam("sdrlName") String name,
			//@RequestParam("ssowRevC") String ssowRev , 
			@RequestParam("sdrlDescription") String description , 
			@RequestParam("rulesString") String rulesString , 
			@RequestParam("auditReason") String auditReason , 
			HttpServletRequest request
			) {
				logger.info("In the save ssow  method ");
				
				User user = (User) request.getSession().getAttribute("user");
				
				ExtResult extResult = new ExtResult();
				logger.info("** ssow id " + sdrlId);
				logger.info("**criteriaAllTrueSVString  " + name);
				logger.info("**criteriaAnyTrueSVString  " + description);
				logger.info("**rules String  " + rulesString);
				logger.info("*** audit reason " + auditReason);
				//logger.info("** userame " + user.getUsername());
				Sdrl sdrl  = null;
				if ( !StringUtils.isEmpty(sdrlId)) {
					
					// means we are in edit mode ---- 
				logger.info("*** Editing the Existing Sdrl ");
					 sdrl = (Sdrl)ssowService.get(Sdrl.class , sdrlId);
				
				logger.info("ssow " + sdrl );
				
				if ( sdrl  != null ) {
					 
					logger.info("*** Rule found " + sdrl.getName());
					
					
					
					Sdrl newSdrl = new Sdrl();
					newSdrl.setActive(new Integer (1));
					newSdrl.setAuditID(updateAudit(auditReason, user.getFullName(), new Date()));
					newSdrl.setModifiedBy(user.getFullName());
					newSdrl.setModifiedDate(new Date());
					
					
					newSdrl.setName(name);
					newSdrl.setDescription(description);
					newSdrl.setEditable(new Integer (1));
					
					
				
					// process rules string also - 
					String criteriaId =  java.util.UUID.randomUUID().toString();
					String[] strings = rulesString.split(";");
						
					// need to go through these strings one by one 
					
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
					
					newSdrl.setRulesSet(ruleSet);
					
					
		
					ssowService.save(newSdrl);
					SsowCacheUtil.flush(SsowCacheUtil.getSdrlListKey());
					// inactive old rule 
					//rule.setActive(new Integer (0) );
					sdrl.setActive(new Integer (0));
					ssowService.save(sdrl);
					// go through all the sections where this old rule was and replace with new rule id - 
					
					
					
					
					extResult.setMsg("Sdrl saved successfully.Sdrl # : " + newSdrl.getName());
					extResult.setData(newSdrl);
				}else {
					logger.error("Could not find ssow for the ssow id " + sdrlId);
					extResult.setData(null);
					extResult.setSuccess("false");
					extResult.setMsg("Ssow could not be saved for ssowId : "+ sdrlId + ", Please try again later.");
				}
				}else {
					
					logger.info("*** Creating a new Rule ");
					// means we are in add mode -- so just create a new rule with all the things and done - 
					
					
					
					Sdrl newSdrl = new Sdrl();
					newSdrl.setActive(new Integer (1));
					newSdrl.setAuditID(updateAudit(auditReason, user.getFullName(), new Date()));
					newSdrl.setModifiedBy(user.getFullName());
					newSdrl.setModifiedDate(new Date());
					
					
					newSdrl.setName(name);
					newSdrl.setDescription(description);
					newSdrl.setEditable(new Integer (1));
					
					
				
					String[] strings = rulesString.split(";");
					
					// need to go through these strings one by one 
					
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
					
					newSdrl.setRulesSet(ruleSet);
					
		
					ssowService.save(newSdrl);
					SsowCacheUtil.flush(SsowCacheUtil.getSdrlListKey());
					
					
					
					
					
					
					
				
					extResult.setData( newSdrl);
					//logger.error("Could not find ssow for the ssow id " + ruleId);
				//	extResult.setData(null);
					//extResult.setSuccess("false");
					extResult.setMsg("Rule # " + newSdrl.getName() + " created Successfully.");
				}
	
		
		
	
	
		return extResult;
	}
	
	
	@RequestMapping(value = "/saveAcronyms", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult saveAcronyms(
			@RequestParam("acronymsId") String acronymsId,
			@RequestParam("acronymsName") String name,
			//@RequestParam("ssowRevC") String ssowRev , 
			@RequestParam("acronymsDescription") String description , 
			@RequestParam("rulesString") String rulesString , 
			@RequestParam("auditReason") String auditReason , 
			HttpServletRequest request
			) {
				logger.info("In the save ssow  method ");
				
				User user = (User) request.getSession().getAttribute("user");
				
				ExtResult extResult = new ExtResult();
				logger.info("** ssow id " + acronymsId);
				logger.info("**criteriaAllTrueSVString  " + name);
				logger.info("**criteriaAnyTrueSVString  " + description);
				logger.info("**rules String  " + rulesString);
				logger.info("*** audit reason " + auditReason);
				//logger.info("** userame " + user.getUsername());
				Acronyms sdrl  = null;
				if ( !StringUtils.isEmpty(acronymsId)) {
					
					// means we are in edit mode ---- 
				logger.info("*** Editing the Existing Sdrl ");
					 sdrl = (Acronyms)ssowService.get(Acronyms.class , acronymsId);
				
				logger.info("ssow " + sdrl );
				
				if ( sdrl  != null ) {
					 
					logger.info("*** Rule found " + sdrl.getName());
					
					
					
					Acronyms newSdrl = new Acronyms();
					newSdrl.setActive(new Integer (1));
					newSdrl.setAuditID(updateAudit(auditReason, user.getFullName(), new Date()));
					newSdrl.setModifiedBy(user.getFullName());
					newSdrl.setModifiedDate(new Date());
					
					
					newSdrl.setName(name);
					newSdrl.setDescription(description);
				//	newSdrl.setEditable(new Integer (1));
					
					
				
					// process rules string also - 
					String criteriaId =  java.util.UUID.randomUUID().toString();
					String[] strings = rulesString.split(";");
						
					// need to go through these strings one by one 
					
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
					
					newSdrl.setRulesSet(ruleSet);
					
					
		
					ssowService.save(newSdrl);
					SsowCacheUtil.flush(SsowCacheUtil.getAcronymsListKey());
					// inactive old rule 
					//rule.setActive(new Integer (0) );
					sdrl.setActive(new Integer (0));
					ssowService.save(sdrl);
					// go through all the sections where this old rule was and replace with new rule id - 
					
					
					
					
					extResult.setMsg("Acronym saved successfully. Acronym # : " + newSdrl.getName());
					extResult.setData(newSdrl);
				}else {
					logger.error("Could not find ssow for the ssow id " + acronymsId);
					extResult.setData(null);
					extResult.setSuccess("false");
					extResult.setMsg("Ssow could not be saved for ssowId : "+ acronymsId + ", Please try again later.");
				}
				}else {
					
					logger.info("*** Creating a new Rule ");
					// means we are in add mode -- so just create a new rule with all the things and done - 
					
					
					
					Acronyms newSdrl = new Acronyms();
					newSdrl.setActive(new Integer (1));
					newSdrl.setAuditID(updateAudit(auditReason, user.getFullName(), new Date()));
					newSdrl.setModifiedBy(user.getFullName());
					newSdrl.setModifiedDate(new Date());
					
					
					newSdrl.setName(name);
					newSdrl.setDescription(description);
					//newSdrl.setEditable(new Integer (1));
					
					
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
					
					newSdrl.setRulesSet(ruleSet);
					
					
		
					ssowService.save(newSdrl);
					SsowCacheUtil.flush(SsowCacheUtil.getAcronymsListKey());
					
					
					
					
					
					
					
				
					extResult.setData( newSdrl);
					//logger.error("Could not find ssow for the ssow id " + ruleId);
				//	extResult.setData(null);
					//extResult.setSuccess("false");
					extResult.setMsg("Acronyms # " + newSdrl.getName() + " created Successfully.");
				}
	
		
		
	
	
		return extResult;
	}
	
	
	
	@RequestMapping(value = "/saveDictionary", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult saveDictionary(
			@RequestParam("dictionaryId") String dictionaryId,
			@RequestParam("dictionaryName") String name,
			//@RequestParam("ssowRevC") String ssowRev , 
			@RequestParam("dictionaryDescription") String description , 
			@RequestParam("rulesString") String rulesString , 
			@RequestParam("auditReason") String auditReason , 
			HttpServletRequest request
			) {
				logger.info("In the save ssow  method ");
				
				User user = (User) request.getSession().getAttribute("user");
				
				ExtResult extResult = new ExtResult();
				logger.info("** ssow id " + dictionaryId);
				logger.info("**criteriaAllTrueSVString  " + name);
				logger.info("**criteriaAnyTrueSVString  " + description);
				logger.info("**rules String  " + rulesString);
				logger.info("*** audit reason " + auditReason);
				//logger.info("** userame " + user.getUsername());
				Dictionary sdrl  = null;
				if ( !StringUtils.isEmpty(dictionaryId)) {
					
					// means we are in edit mode ---- 
				logger.info("*** Editing the Existing Sdrl ");
					 sdrl = (Dictionary)ssowService.get(Dictionary.class , dictionaryId);
				
				logger.info("ssow " + sdrl );
				
				if ( sdrl  != null ) {
					 
					logger.info("*** Rule found " + sdrl.getName());
					
					
					
					Dictionary newSdrl = new Dictionary();
					newSdrl.setActive(new Integer (1));
					newSdrl.setAuditID(updateAudit(auditReason, user.getFullName(), new Date()));
					newSdrl.setModifiedBy(user.getFullName());
					newSdrl.setModifiedDate(new Date());
					
					
					newSdrl.setName(name);
					newSdrl.setDescription(description);
				//	newSdrl.setEditable(new Integer (1));
					
					
				
					// process rules string also - 
				//	String criteriaId =  java.util.UUID.randomUUID().toString();
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
					
					newSdrl.setRulesSet(ruleSet);
					
		
					ssowService.save(newSdrl);
					SsowCacheUtil.flush(SsowCacheUtil.getDictionaryListKey());
					// inactive old rule 
					//rule.setActive(new Integer (0) );
					sdrl.setActive(new Integer (0));
					ssowService.save(sdrl);
					// go through all the sections where this old rule was and replace with new rule id - 
					
					
					
					
					extResult.setMsg("Dictionary saved successfully. Dictionary # : " + newSdrl.getName());
					extResult.setData(newSdrl);
				}else {
					logger.error("Could not find ssow for the ssow id " + dictionaryId);
					extResult.setData(null);
					extResult.setSuccess("false");
					extResult.setMsg("Ssow could not be saved for ssowId : "+ dictionaryId + ", Please try again later.");
				}
				}else {
					
					logger.info("*** Creating a new Rule ");
					// means we are in add mode -- so just create a new rule with all the things and done - 
					
					
					
					Dictionary newSdrl = new Dictionary();
					newSdrl.setActive(new Integer (1));
					newSdrl.setAuditID(updateAudit(auditReason, user.getFullName(), new Date()));
					newSdrl.setModifiedBy(user.getFullName());
					newSdrl.setModifiedDate(new Date());
					
					
					newSdrl.setName(name);
					newSdrl.setDescription(description);
				//	newSdrl.setEditable(new Integer (1));
					
					
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
					
					newSdrl.setRulesSet(ruleSet);
					
					
		
					ssowService.save(newSdrl);
					SsowCacheUtil.flush(SsowCacheUtil.getDictionaryListKey());
					
					
					
					
					
					
					
				
					extResult.setData( newSdrl);
					//logger.error("Could not find ssow for the ssow id " + ruleId);
				//	extResult.setData(null);
					//extResult.setSuccess("false");
					extResult.setMsg("Dictionary # " + newSdrl.getName() + " created Successfully.");
				}
	
		
		
	
	
		return extResult;
	}
	private String createCriteria( String criteria ) {
		
		
		
		String criteriaId =  java.util.UUID.randomUUID().toString();
		String[] strings = criteria.split(";");
			
		// need to go through these strings one by one 
		
		for ( int i = 0 ; i < strings.length ; i++) {
			
			logger.info("**** string " + strings[i]);
			
			// split again 
			
			String[] valuePair = strings[i].split("=>") ;
			if ( valuePair.length == 2) {
				String depId = valuePair[0];
				String value = valuePair[1];
				
				
				// need to find the question for this depId
				
				CheckList depChk = ssowService.getCheckListByNumber(depId);
				if ( depChk != null ) {
					RulesCriteriaXref xref = new RulesCriteriaXref();
					xref.setDependencyID(depChk.getId());
					xref.setValue(value);
					xref.setCriteriaId(criteriaId);
					
					ssowService.save(xref);
				}
			}
		}
		
		
		return criteriaId;
		
		
	}
	
	
private String createRulesCriteria( String criteria ) {
		
		
		
		String criteriaId =  java.util.UUID.randomUUID().toString();
		String[] strings = criteria.split(";");
			
		// need to go through these strings one by one 
		
		for ( int i = 0 ; i < strings.length ; i++) {
			
			logger.info("**** string " + strings[i]);
			
			if ( !StringUtils.isEmpty(strings[i])) {		
			
				
				
				// need to find the question for this depId
				
				Rules depChk = ssowService.getRuleByName(strings[i]);
				if ( depChk != null ) {
					RulesCriteriaXref xref = new RulesCriteriaXref();
					xref.setDependencyID(depChk.getId());
					xref.setValue("Y");
					xref.setCriteriaId(criteriaId);
					
					ssowService.save(xref);
				}
			}
		}
		
		
		return criteriaId;
		
		
	}



private String createCheckListProgramXref( String checkListProgramString  , String checkListId) {
	
	
	
	String criteriaId =  java.util.UUID.randomUUID().toString();
	String[] strings = checkListProgramString.split(";");
		
	// need to go through these strings one by one 
	
	for ( int i = 0 ; i < strings.length ; i++) {
		
		logger.info("**** string " + strings[i]);
		
		if ( !StringUtils.isEmpty(strings[i])) {		
		
			
			String[] valuePair = strings[i].split("=>") ;
			if ( valuePair.length == 2) {
				String programName = valuePair[0];
				String applicable = valuePair[1];
			// need to find the question for this depId
			
			//Rules depChk = ssowService.getRuleByName(strings[i]);
			
				CheckListProgramXref xref = new CheckListProgramXref();
				xref.setChecklistid(checkListId);
				xref.setApplicable(applicable);
				xref.setProgramName(programName);
				//RulesCriteriaXref xref = new RulesCriteriaXref();
				//xref.setDependencyID(depChk.getId());
				//xref.setValue("Y");
				//xref.setCriteriaId(criteriaId);
				
				ssowService.save(xref);
			}
		}
	}
	
	
	return criteriaId;
	
	
}
	@RequestMapping(value = "/deleteSsow", method = RequestMethod.GET , headers="Accept=application/json")
	public @ResponseBody ExtResult deleteSsow(@RequestParam("ssowId") String ssowId , 
			HttpServletRequest request) {
		logger.info("In the delete  method ");
		
	
		logger.info("SSow id " + ssowId);
		
		ExtResult extResult = new ExtResult();
		
		
		User user = (User) request.getSession().getAttribute("user");
		
		if ( !StringUtils.isEmpty(ssowId)) {
			Ssow ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
		
			if ( ssow != null ) {
			
				ssow.setActive(new Integer (0));
				ssow.setModifiedDate(new Date());
				ssow.setModifiedBy(user.getFullName());
			
				//ssowService.save(ssow);
				ssowService.save(ssow);
				extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully deleted.");
			}else {
				logger.error("Could not locate Ssow for ssow id " + ssowId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be deleted for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
			}
		}else {
				logger.error("Could not locate Ssow for ssow id " + ssowId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be deleted for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
		}
		
		
		
		return extResult;
	}
	
	
	
	@RequestMapping(value = "/generateSsow")
	public void  generateSsow(@RequestParam("ssowId") String ssowId , 
			HttpServletRequest request , HttpServletResponse response)  {
		logger.info("In the generate  method ");
		
	
		logger.info("SSow id " + ssowId);
		
		//ExtResult extResult = new ExtResult();
		
		
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			if ( !StringUtils.isEmpty(ssowId)) {
				Ssow ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
			
				if ( ssow != null ) {
				
					ssow.setStatus(SsowStatus.InReviw);
				//	ssow.setActive(new Integer (0));
					ssow.setModifiedDate(new Date());
					ssow.setModifiedBy(user.getFullName());
				
					//ssowService.save(ssow);
					ssowService.save(ssow);
					
					
					// call the ssow generator here 
					File file = ssowService.processSsowGenerator(ssow);
					
					logger.info("**** did i reached here and if yes then " + file.getPath());
					 OutputStream responseStream = response.getOutputStream();
					// String path = PropertyManager.getProperty(SmefsConstants.DOCUMENT_DIR) + "/" +
				   //  documentChild.getFilename();
					 response.setContentType("application/msword");
				      response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
				      
				      //this is needed for EPIC-40 Jira issue where 50MB PDF files would go corrupt because of some caching problem
				      if (request.getHeader(new String ("User-Agent"))!=null && request.getHeader(new String ("User-Agent")).contains("Firefox/")) {
				         response.setHeader("Cache-Control","no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0"); //HTTP 1.1
				         response.setHeader("Pragma","no-cache"); //HTTP 1.0
				         response.setDateHeader ("Expires", 0); 
				      }

				      // Write to the FileInputStream for the response's writer.
				      FileInputStream fileStream = new FileInputStream(file);
				      FileCopyUtils.copy(fileStream, responseStream);
				      responseStream.flush();
				      response.flushBuffer();
				      responseStream.close();
				      fileStream.close();
				      
				      logger.info("**** reached the end " );
					///extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully generated.");
				}else {
					logger.error("Could not locate Ssow for ssow id " + ssowId);
					//extResult.setData(null);
					//extResult.setSuccess("false");
					//extResult.setMsg("Ssow could not be deleted for ssowId : "+ ssowId + ", Please try again later.");
					//extResult.setException(new Exception());
				}
			}else {
					logger.error("Could not locate Ssow for ssow id " + ssowId);
				//	extResult.setData(null);
					//extResult.setSuccess("false");
					//extResult.setMsg("Ssow could not be deleted for ssowId : "+ ssowId + ", Please try again later.");
					//extResult.setException(new Exception());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//return null ;
	}
	
	@RequestMapping(value = "/showSsow")
	public void  showSsow(@RequestParam("ssowId") String ssowId , 
			HttpServletRequest request , HttpServletResponse response)  {
		logger.info("In the show  method ");
		
	
		logger.info("SSow id " + ssowId);
		
		//ExtResult extResult = new ExtResult();
		
		
		try {
			User user = (User) request.getSession().getAttribute("user");
			
			if ( !StringUtils.isEmpty(ssowId)) {
				Ssow ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
			
				if ( ssow != null ) {
				
				
					
					File file = new File ( ssow.getFileLocation());
					
					logger.info("**** did i reached here and if yes then " + file.getPath());
					 OutputStream responseStream = response.getOutputStream();
					// String path = PropertyManager.getProperty(SmefsConstants.DOCUMENT_DIR) + "/" +
				   //  documentChild.getFilename();
					 response.setContentType("application/msword");
				      response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
				      
				      //this is needed for EPIC-40 Jira issue where 50MB PDF files would go corrupt because of some caching problem
				      if (request.getHeader(new String ("User-Agent"))!=null && request.getHeader(new String ("User-Agent")).contains("Firefox/")) {
				         response.setHeader("Cache-Control","no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0"); //HTTP 1.1
				         response.setHeader("Pragma","no-cache"); //HTTP 1.0
				         response.setDateHeader ("Expires", 0); 
				      }

				      // Write to the FileInputStream for the response's writer.
				      FileInputStream fileStream = new FileInputStream(file);
				      FileCopyUtils.copy(fileStream, responseStream);
				      responseStream.flush();
				      response.flushBuffer();
				      responseStream.close();
				      fileStream.close();
				      
				      logger.info("**** reached the end " );
					///extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully generated.");
				}else {
					logger.error("Could not locate Ssow for ssow id " + ssowId);
					//extResult.setData(null);
					//extResult.setSuccess("false");
					//extResult.setMsg("Ssow could not be deleted for ssowId : "+ ssowId + ", Please try again later.");
					//extResult.setException(new Exception());
				}
			}else {
					logger.error("Could not locate Ssow for ssow id " + ssowId);
				//	extResult.setData(null);
					//extResult.setSuccess("false");
					//extResult.setMsg("Ssow could not be deleted for ssowId : "+ ssowId + ", Please try again later.");
					//extResult.setException(new Exception());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//return null ;
	}
	
	@RequestMapping(value = "/completeSsow", method = RequestMethod.GET , headers="Accept=application/json")
	public @ResponseBody ExtResult completeSsow(@RequestParam("ssowId") String ssowId, 
			HttpServletRequest request ) {
		logger.info("In the delete  method ");
		
	
		logger.info("SSow id " + ssowId);
		
		ExtResult extResult = new ExtResult();
		
		
		
		User user = (User) request.getSession().getAttribute("user");
		
		if ( !StringUtils.isEmpty(ssowId)) {
			Ssow ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
		
			if ( ssow != null ) {
			
				//ssow.setActive(new Integer (0));
				ssow.setStatus(SsowStatus.Complete);
				ssow.setModifiedDate(new Date());
				ssow.setModifiedBy(user.getFullName());
				
				
				
				
			
			//	ssowService.save(ssow);
				ssowService.save(ssow);
				extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully Completed.");
			}else {
				logger.error("Could not locate Ssow for ssow id " + ssowId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
			}
		}else {
				logger.error("Could not locate Ssow for ssow id " + ssowId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
		}
		
		
		
		return extResult;
	}
	
	
	
	
	
	@RequestMapping(value = "/getRule", method = RequestMethod.GET , headers="Accept=application/json")
	public @ResponseBody ExtResult getRule(@RequestParam("ruleId") String ruleId, 
			HttpServletRequest request ) {
		logger.info("In the delete  method ");
		
	
		logger.info("Rule id " + ruleId);
		
		ExtResult extResult = new ExtResult();
		
		
		
		User user = (User) request.getSession().getAttribute("user");
		
		if ( !StringUtils.isEmpty(ruleId)) {
			Rules rules = (Rules)ssowService.get(Rules.class , ruleId);
		
			if ( rules != null ) {
			
				
				// now once we get the rule then i need to do something with it or not - 
				
				// have seven criteria - 
				
				
				
				extResult.setData(rules);
				
			
			//	ssowService.save(ssow);
			//	ssowService.save(ssow);
			//	extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully Completed.");
			}else {
				logger.error("Could not locate Ssow for ssow id " + ruleId);
				extResult.setData(null);
				extResult.setSuccess("false");
				//extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
			}
		}else {
				logger.error("Could not locate Ssow for ssow id " + ruleId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ ruleId + ", Please try again later.");
				//extResult.setException(new Exception());
		}
		
		
		
		return extResult;
	}
	
	
	@RequestMapping(value = "/getSdrl", method = RequestMethod.GET , headers="Accept=application/json")
	public @ResponseBody ExtResult getSdrl(@RequestParam("sdrlId") String sdrlId, 
			HttpServletRequest request ) {
		logger.info("In the delete  method ");
		
	
		logger.info("Sdrl id " + sdrlId);
		
		ExtResult extResult = new ExtResult();
		
		
		
		//User user = (User) request.getSession().getAttribute("user");
		
		if ( !StringUtils.isEmpty(sdrlId)) {
			Sdrl sdrl = (Sdrl)ssowService.get(Sdrl.class , sdrlId);
		
			if ( sdrl != null ) {
			
				
				// now once we get the rule then i need to do something with it or not - 
				
				// have seven criteria - 
				
				
				
				extResult.setData(sdrl);
				
			
			//	ssowService.save(ssow);
			//	ssowService.save(ssow);
			//	extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully Completed.");
			}else {
				logger.error("Could not locate Ssow for ssow id " + sdrlId);
				extResult.setData(null);
				extResult.setSuccess("false");
				//extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
			}
		}else {
				logger.error("Could not locate Ssow for ssow id " + sdrlId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ sdrlId + ", Please try again later.");
				//extResult.setException(new Exception());
		}
		
		
		
		return extResult;
	}
	
	
	
	@RequestMapping(value = "/getSection", method = RequestMethod.GET , headers="Accept=application/json")
	public @ResponseBody ExtResult getSection(@RequestParam("sectionId") String sectionId, 
			HttpServletRequest request ) {
		logger.info("In the delete  method ");
		
	
		logger.info("Sdrl id " + sectionId);
		
		ExtResult extResult = new ExtResult();
		
		
		
		//User user = (User) request.getSession().getAttribute("user");
		
		if ( !StringUtils.isEmpty(sectionId)) {
			Section sdrl = (Section)ssowService.get(Section.class , sectionId);
		
			if ( sdrl != null ) {
			
				
				// now once we get the rule then i need to do something with it or not - 
				
				// have seven criteria - 
				
				
				
				extResult.setData(sdrl);
				
			
			//	ssowService.save(ssow);
			//	ssowService.save(ssow);
			//	extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully Completed.");
			}else {
				logger.error("Could not locate Ssow for ssow id " + sectionId);
				extResult.setData(null);
				extResult.setSuccess("false");
				//extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
			}
		}else {
				logger.error("Could not locate Ssow for ssow id " + sectionId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ sectionId + ", Please try again later.");
				//extResult.setException(new Exception());
		}
		
		
		
		return extResult;
	}
	
	
	
	@RequestMapping(value = "/getChk", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult getChk(@RequestParam("checkListId") String checkListId, 
			HttpServletRequest request ) {
		logger.info("In the delete  method ");
		
	
		logger.info("Sdrl id " + checkListId);
		
		ExtResult extResult = new ExtResult();
		
		
		
		//User user = (User) request.getSession().getAttribute("user");
		
		if ( !StringUtils.isEmpty(checkListId)) {
			CheckList sdrl = (CheckList)ssowService.getCheckList(checkListId);
		
			if ( sdrl != null ) {
			
				
				// now once we get the rule then i need to do something with it or not - 
				
				// have seven criteria - 
				
				
				
				extResult.setData(sdrl);
				
			
			//	ssowService.save(ssow);
			//	ssowService.save(ssow);
			//	extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully Completed.");
			}else {
				logger.error("Could not locate Ssow for ssow id " + checkListId);
				extResult.setData(null);
				extResult.setSuccess("false");
				//extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
			}
		}else {
				logger.error("Could not locate Ssow for ssow id " + checkListId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ checkListId + ", Please try again later.");
				//extResult.setException(new Exception());
		}
		
		
		
		return extResult;
	}
	

	
	
	@RequestMapping(value = "/getQuestionTypes", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult getQuestionTypes(Locale locale, Model model) {

		logger.info("In the getQuestionTypes list  method ");

		ExtResult extResult = new ExtResult();
		List returnList = questionTypeService.getQuestionTypeList();
		logger.info("**** Question Type list size " + returnList.size());
		extResult.setData(returnList);
		return extResult;
	}
	
	
	@RequestMapping(value = "/getDictionary", method = RequestMethod.GET , headers="Accept=application/json")
	public @ResponseBody ExtResult getDictionary(@RequestParam("dictionaryId") String dictionaryId, 
			HttpServletRequest request ) {
		logger.info("In the delete  method ");
		
	
		logger.info("Sdrl id " + dictionaryId);
		
		ExtResult extResult = new ExtResult();
		
		
		
		//User user = (User) request.getSession().getAttribute("user");
		
		if ( !StringUtils.isEmpty(dictionaryId)) {
			Dictionary sdrl = (Dictionary)ssowService.get(Dictionary.class , dictionaryId);
		
			if ( sdrl != null ) {
			
				
				// now once we get the rule then i need to do something with it or not - 
				
				// have seven criteria - 
				
				
				
				extResult.setData(sdrl);
				
			
			//	ssowService.save(ssow);
			//	ssowService.save(ssow);
			//	extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully Completed.");
			}else {
				logger.error("Could not locate Ssow for ssow id " + dictionaryId);
				extResult.setData(null);
				extResult.setSuccess("false");
				//extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
			}
		}else {
				logger.error("Could not locate Ssow for ssow id " + dictionaryId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ dictionaryId + ", Please try again later.");
				//extResult.setException(new Exception());
		}
		
		
		
		return extResult;
	}
	
	
	@RequestMapping(value = "/getAcronyms", method = RequestMethod.GET , headers="Accept=application/json")
	public @ResponseBody ExtResult getAcronyms(@RequestParam("acronymsId") String acronymsId, 
			HttpServletRequest request ) {
		logger.info("In the delete  method ");
		
	
		logger.info("Sdrl id " + acronymsId);
		
		ExtResult extResult = new ExtResult();
		
		
		
		//User user = (User) request.getSession().getAttribute("user");
		
		if ( !StringUtils.isEmpty(acronymsId)) {
			Acronyms acronyms = (Acronyms)ssowService.get(Acronyms.class , acronymsId);
		
			if ( acronyms != null ) {
			
				
				// now once we get the rule then i need to do something with it or not - 
				
				// have seven criteria - 
				
				
				
				extResult.setData(acronyms);
				
			
			//	ssowService.save(ssow);
			//	ssowService.save(ssow);
			//	extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully Completed.");
			}else {
				logger.error("Could not locate Ssow for ssow id " + acronymsId);
				extResult.setData(null);
				extResult.setSuccess("false");
				//extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
			}
		}else {
				logger.error("Could not locate Ssow for ssow id " + acronymsId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ acronymsId + ", Please try again later.");
				//extResult.setException(new Exception());
		}
		
		
		
		return extResult;
	}
	
	@RequestMapping(value = "/getRules", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult getRules( HttpServletRequest request ) {
		logger.info("In the get rules   method ");
		
	
				String start = (String)request.getAttribute("start");
				String limit = (String) request.getAttribute("limit");
				
				logger.info("**** start value " + start);
				logger.info(" limit value " + limit );
				
				
				 start = (String)request.getParameter("start");
				 limit = (String) request.getParameter("limit");
				 
				 
					logger.info("**** start value " + start);
					logger.info(" limit value " + limit );
					
					
				List rules = ssowService.getRules();
				int totalCount = rules.size();
				
				// NOW get the sub list --- 
				int startLimit = new Integer ( start ).intValue();
				int limitLimit = new Integer ( limit).intValue();
				
				int endLimit = startLimit +limitLimit;
				
				if ( endLimit > rules.size())  {
					endLimit = rules.size();
				}
 				rules  = rules.subList(startLimit, endLimit);
				List returnRulesList = new ArrayList();
				logger.info("***** rules list size " + rules.size());
				for ( int i = 0 ; i < rules.size(); i++ ) {
					

					
					Rules rulesObj = (Rules) rules.get(i);
					
					
					
					returnRulesList.add(ssowService.getJsonRules(rulesObj));
				
					
					
					logger.info("***** return RulesList size " + returnRulesList.size());
					
					
					/// after this , now the next step is to read the sections out of tree and then 
					// apply the rules to the sections and in the end make the list of the final sections list in the right 
					// order and hand it over to the word output - 
					
					
				}
			
	
		
		ExtResult extResult = new ExtResult();
		extResult.setResults(new Integer ( totalCount));
		extResult.setData(returnRulesList);
		
		return extResult;
	}
	
	
	
	@RequestMapping(value = "/getFullRules", method = RequestMethod.GET , headers="Accept=application/json")
	public @ResponseBody ExtResult getFullRules( HttpServletRequest request ) {
		logger.info("In the get rules   method ");
		
					
				List rules = ssowService.getRules();
	
		ExtResult extResult = new ExtResult();
	//	extResult.setResults(new Integer ( totalCount));
		extResult.setData(rules);
		
		return extResult;
	}
	
	@RequestMapping(value = "/getAcronymsList", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult getAcronymsList( HttpServletRequest request ) {
		logger.info("In the get rules   method ");
		
	
				String start = (String)request.getAttribute("start");
				String limit = (String) request.getAttribute("limit");
				
				logger.info("**** start value " + start);
				logger.info(" limit value " + limit );
				
				
				 start = (String)request.getParameter("start");
				 limit = (String) request.getParameter("limit");
				 
				 
					logger.info("**** start value " + start);
					logger.info(" limit value " + limit );
					
					
				List acronyms = ssowService.getAcronymsList();
				int totalCount = acronyms.size();
				
				// NOW get the sub list --- 
				int startLimit = new Integer ( start ).intValue();
				int limitLimit = new Integer ( limit).intValue();
				
				int endLimit = startLimit +limitLimit;
				
				if ( endLimit > acronyms.size())  {
					endLimit = acronyms.size();
				}
				acronyms  = acronyms.subList(startLimit, endLimit);
			//	List returnRulesList = new ArrayList();
				logger.info("***** rules list size " + acronyms.size());
//				for ( int i = 0 ; i < rules.size(); i++ ) {
//					
//
//					
//					Rules rulesObj = (Rules) rules.get(i);
//					
//					
//					
//					returnRulesList.add(ssowService.getJsonRules(rulesObj));
//				
//					
//					
//					logger.info("***** return RulesList size " + returnRulesList.size());
//					
//					
//					/// after this , now the next step is to read the sections out of tree and then 
//					// apply the rules to the sections and in the end make the list of the final sections list in the right 
//					// order and hand it over to the word output - 
//					
//					
//				}
			
	
		
		ExtResult extResult = new ExtResult();
		extResult.setResults(new Integer ( totalCount));
		extResult.setData(acronyms);
		
		return extResult;
	}
	
	
	@RequestMapping(value = "/getDictionaryList", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult getDictionaryList( HttpServletRequest request ) {
		logger.info("In the get rules   method ");
		
	
				String start = (String)request.getAttribute("start");
				String limit = (String) request.getAttribute("limit");
				
				logger.info("**** start value " + start);
				logger.info(" limit value " + limit );
				
				
				 start = (String)request.getParameter("start");
				 limit = (String) request.getParameter("limit");
				 
				 
					logger.info("**** start value " + start);
					logger.info(" limit value " + limit );
					
					
				List dictionary = ssowService.getDictionaryList();
				int totalCount = dictionary.size();
				
				// NOW get the sub list --- 
				int startLimit = new Integer ( start ).intValue();
				int limitLimit = new Integer ( limit).intValue();
				
				int endLimit = startLimit +limitLimit;
				
				if ( endLimit > dictionary.size())  {
					endLimit = dictionary.size();
				}
				dictionary  = dictionary.subList(startLimit, endLimit);
				//List returnRulesList = new ArrayList();
				logger.info("***** rules list size " + dictionary.size());
//				for ( int i = 0 ; i < rules.size(); i++ ) {
//					
//
//					
//					Rules rulesObj = (Rules) dictionary.get(i);
//					
//					
//					
//					returnRulesList.add(ssowService.getJsonRules(rulesObj));
//				
//					
//					
//					logger.info("***** return RulesList size " + returnRulesList.size());
//					
//					
//					/// after this , now the next step is to read the sections out of tree and then 
//					// apply the rules to the sections and in the end make the list of the final sections list in the right 
//					// order and hand it over to the word output - 
//					
//					
//				}
			
	
		
		ExtResult extResult = new ExtResult();
		extResult.setResults(new Integer ( totalCount));
		extResult.setData(dictionary);
		
		return extResult;
	}
	
	
	@RequestMapping(value = "/getSdrlList", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult getSdrlList( HttpServletRequest request ) {
		logger.info("In the get rules   method ");
		
	
				String start = (String)request.getAttribute("start");
				String limit = (String) request.getAttribute("limit");
				
				logger.info("**** start value " + start);
				logger.info(" limit value " + limit );
				
				
				 start = (String)request.getParameter("start");
				 limit = (String) request.getParameter("limit");
				 
				 
					logger.info("**** start value " + start);
					logger.info(" limit value " + limit );
					
					
				List sdrl = ssowService.getSdrlEditableList();
				int totalCount = sdrl.size();
				
				// NOW get the sub list --- 
				int startLimit = new Integer ( start ).intValue();
				int limitLimit = new Integer ( limit).intValue();
				
				int endLimit = startLimit +limitLimit;
				
				if ( endLimit > sdrl.size())  {
					endLimit = sdrl.size();
				}
				sdrl  = sdrl.subList(startLimit, endLimit);
				//List returnRulesList = new ArrayList();
				logger.info("***** rules list size " + sdrl.size());
//				for ( int i = 0 ; i < sdrl.size(); i++ ) {
//					
//
//					
//					Rules rulesObj = (Rules) rules.get(i);
//					
//					
//					
//					returnRulesList.add(ssowService.getJsonRules(rulesObj));
//				
//					
//					
//					logger.info("***** return RulesList size " + returnRulesList.size());
//					
//					
//					/// after this , now the next step is to read the sections out of tree and then 
//					// apply the rules to the sections and in the end make the list of the final sections list in the right 
//					// order and hand it over to the word output - 
//					
//					
//				}
			
	
		
		ExtResult extResult = new ExtResult();
		extResult.setResults(new Integer ( totalCount));
		extResult.setData(sdrl);
		
		return extResult;
	}
	
	
	
	@RequestMapping(value = "/getCheckList", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult getCheckList( HttpServletRequest request ) {
		logger.info("In the get rules   method ");
		
	
				String start = (String)request.getAttribute("start");
				String limit = (String) request.getAttribute("limit");
				
				logger.info("**** start value " + start);
				logger.info(" limit value " + limit );
				
				
				 start = (String)request.getParameter("start");
				 limit = (String) request.getParameter("limit");
				 
				 
					logger.info("**** start value " + start);
					logger.info(" limit value " + limit );
					
					
				List sdrl = ssowService.getCheckList();
				int totalCount = sdrl.size();
				
				// NOW get the sub list --- 
				int startLimit = new Integer ( start ).intValue();
				int limitLimit = new Integer ( limit).intValue();
				
				int endLimit = startLimit +limitLimit;
				
				if ( endLimit > sdrl.size())  {
					endLimit = sdrl.size();
				}
				sdrl  = sdrl.subList(startLimit, endLimit);
				//List returnRulesList = new ArrayList();
				logger.info("***** rules list size " + sdrl.size());
//				for ( int i = 0 ; i < sdrl.size(); i++ ) {
//					
//
//					
//					Rules rulesObj = (Rules) rules.get(i);
//					
//					
//					
//					returnRulesList.add(ssowService.getJsonRules(rulesObj));
//				
//					
//					
//					logger.info("***** return RulesList size " + returnRulesList.size());
//					
//					
//					/// after this , now the next step is to read the sections out of tree and then 
//					// apply the rules to the sections and in the end make the list of the final sections list in the right 
//					// order and hand it over to the word output - 
//					
//					
//				}
			
	
		
		ExtResult extResult = new ExtResult();
		extResult.setResults(new Integer ( totalCount));
		extResult.setData(sdrl);
		
		return extResult;
	}
	
	@RequestMapping(value = "/unCompleteSsow", method = RequestMethod.GET , headers="Accept=application/json")
	public @ResponseBody ExtResult unCompleteSsow(@RequestParam("ssowId") String ssowId ,
			HttpServletRequest request ) {
		logger.info("In the uncomplete ssow -   method ");
		
	
		logger.info("SSow id " + ssowId);
		
		ExtResult extResult = new ExtResult();
		
		User user = (User) request.getSession().getAttribute("user");
		
		if ( !StringUtils.isEmpty(ssowId)) {
			Ssow ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
		
			if ( ssow != null ) {
			
				//ssow.setActive(new Integer (0));
				ssow.setStatus(SsowStatus.InWork);
				ssow.setModifiedDate(new Date());
				ssow.setModifiedBy(user.getFullName());
			
				//ssowService.save(ssow);
				ssowService.save(ssow);
				extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully Completed.");
			}else {
				logger.error("Could not locate Ssow for ssow id " + ssowId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
			}
		}else {
				logger.error("Could not locate Ssow for ssow id " + ssowId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
		}
		
		
		
		return extResult;
	}
	
	@RequestMapping(value = "/getStatuses", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult getAllStatus() {

		logger.info("In the get status  list method ");

		ExtResult extResult = new ExtResult();
		List returnList = ssowService.getStatuses();
		logger.info("**** Programs list size " + returnList.size());
		extResult.setData(returnList);
		return extResult;
	}
	
	@RequestMapping(value = "/getDocumentTypes", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult getAll(Locale locale, Model model) {

		logger.info("In the getAllTemplates list method ");

		ExtResult extResult = new ExtResult();
		List returnList = ssowService.getDocumentTypes();
		logger.info("**** Programs list size " + returnList.size());
		extResult.setData(returnList);
		return extResult;
	}
	
	
	
	
	@RequestMapping(value = "/getBasicQList", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ExtResult getBasicQList(@RequestParam("ssowId" ) String ssowId ) {

		logger.info("In the get basic q list method ");

		//String ssowId =  request.getParameter("ssowId");
		
		logger.info("**** ssow id " + ssowId);
		ExtResult extResult = new ExtResult();
		List returnList = null;
		 List returnJsonList = new ArrayList();
		
		logger.info("*** saving basic Q" + ssowId);
		
		
		Map chkMap = new HashMap();
		
		Ssow ssow = null;
		if ( !StringUtils.isEmpty(ssowId)) {
		
			 ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
		
			logger.info("ssow " + ssow);
			
			if ( ssow != null ) {
				
				
				Set chkList = ssow.getSsowCheckList();
				
				if ( chkList != null ) {
					
					logger.info("**** check list set size " + chkList.size());
					Iterator it = chkList.iterator();
					while ( it.hasNext()) {
						
						SsowCheckListXref xref = (SsowCheckListXref)it.next();
						
						chkMap.put(xref.getCheckList().getId(), xref.getAnswer());
					}
					
					logger.info("*** map values " + chkMap.toString());
				}else {
					logger.info("**** check list null at ssow ");
				}
				
				
				
		
				// for every Q in the basic check list - check if the value exist in the ssow check list and if it does 
				// set that value in the 
			}
		}
		try {
			
			 returnList = ssowService.getBasicQList();
			
			 
			logger.info("**** Programs list size " + returnList.size());
			
			for ( int i = 0 ; i < returnList.size() ; i++) {
				
				CheckList chk = (CheckList)returnList.get(i);
				
				JsonCheckList jsonChk = convertToJson(chk);
				if ( chkMap != null && chkMap.containsKey(chk.getId())) {
					
					
					jsonChk.setValue((String)chkMap.get(chk.getId()));
				}
 			/*	if ( chk.getItemNumber().equals("CL003")) {
					
					// then we got to do something - 
					
					// get all the programs first 
					Set choices = new HashSet();
					List programList = this.programService.getAllPrograms();
					
					for ( int k = 0 ; k < programList.size() ; k++) {
						
						Program program = (Program)programList.get(k);
						
						CheckListValue value = new CheckListValue();
						value.setValue(program.getName());
						choices.add(value);
						
					}
					
				
					
					jsonChk.setChoices(choices);
					
					
				}*/
				
 			returnJsonList.add(jsonChk);
 			
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Exception happened " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		extResult.setData(returnJsonList);
		return extResult;
	}
	
	
	
	
	
	@RequestMapping(value = "/getCheckList", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ExtResult getCheckList( ) {

		logger.info("In the get basic q list method ");

		//String ssowId =  request.getParameter("ssowId");
		
		
		ExtResult extResult = new ExtResult();
		List returnList = null;
		 List returnJsonList = new ArrayList();
		
		

		
	
		
		try {
			
			 returnList = ssowService.getCheckList();
			
			 
			//logger.info("**** Programs list size " + returnList.size());
			
			for ( int i = 0 ; i < returnList.size() ; i++) {
				
				CheckList chk = (CheckList)returnList.get(i);
				
				JsonCheckList jsonChk = convertToJson(chk);
		
 				
				
				returnJsonList.add(jsonChk);
 			
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Exception happened " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		extResult.setData(returnJsonList);
		return extResult;
	}
	
	
	
	
	@RequestMapping(value = "/getCheckListPV", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ExtResult getCheckListPV(@RequestParam("checkListId") String checkListId ) {

		logger.info("in the get checklist PV  method ");

		//String ssowId =  request.getParameter("ssowId");
		
		
		ExtResult extResult = new ExtResult();
		List returnList = null;
		 List returnJsonList = new ArrayList();
		
		

		
	
		
		try {
			
			// returnList = ssowService.getCheckList();
			
			 CheckList chkList = ssowService.getCheckList(checkListId);
			 
			 if ( chkList != null) {
				 
				 
				 logger.info("**** chklist type " + chkList.getQuestionType());
				 
				 if ( "YN".equals(chkList.getQuestionType())) {
					 
					 
					 
					 JsonChkValue val = new JsonChkValue();
					 val.setValue("Y");
					 returnJsonList.add(val);
					 
					 val = new JsonChkValue();
					 val.setValue("N");
					 
					 returnJsonList.add(val);
					 
				 }else {
					 // which means it has to be drop down 
					 
					returnJsonList.addAll( chkList.getChoices());
					 
				 }
			 }
			//logger.info("**** Programs list size " + returnList.size());
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Exception happened " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		extResult.setData(returnJsonList);
		return extResult;
	}
	
	
	
	
	@RequestMapping(value = "/getCheckListProgramList", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ExtResult getCheckListProgramList( ) {

		logger.info("in the get checklist PV  method ");

		//String ssowId =  request.getParameter("ssowId");
		
		
		ExtResult extResult = new ExtResult();
		List returnList = null;
		 List returnJsonList = new ArrayList();
		
		

		
	
		
		try {
			
			// returnList = ssowService.getCheckList();
			
			 CheckList chkList = ssowService.getCheckListByNumber("CL003");
			 
			 if ( chkList != null) {
				 
				 
				 logger.info("**** chklist type " + chkList.getChoices().toString());
				 
				 if (chkList.getChoices() != null) {
					 
					 
					
					 
					extResult.setData(chkList.getChoices());
					 
				
				 }
			 }
			//logger.info("**** Programs list size " + returnList.size());
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Exception happened " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		//extResult.setData(returnJsonList);
		return extResult;
	}
	
	
	
	@RequestMapping(value = "/getCheckListProgramPV", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ExtResult getCheckListProgramPV( ) {

		logger.info("in the get checklist PV  method ");

		//String ssowId =  request.getParameter("ssowId");
		
		
		ExtResult extResult = new ExtResult();
		List returnList = null;
		 List returnJsonList = new ArrayList();
		
		 JsonChkValue val = new JsonChkValue();
		 val.setValue("Y");
		 returnJsonList.add(val);
		 val = new JsonChkValue();
		 val.setValue("N");
		 returnJsonList.add(val);
		
	
		
		
		
		
		
		extResult.setData(returnJsonList);
		return extResult;
	}
	
	@RequestMapping(value = "/saveCheckList", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult saveCheckList( HttpServletRequest request,
			@RequestParam("checkListId") String checklistId,
			@RequestParam("questionText") String questionText, 
			@RequestParam("checkListValueString") String checkListValueString, 
			@RequestParam("checkListProgramString") String checkListProgramString, 
			@RequestParam("auditReason") String reasonForChange, 
		    @RequestParam("questionTypeDD") String questionType) {
		
		logger.info("saving the updateCheckList  method ");
		logger.info("RJM- checklistId = " + checklistId);
		logger.info("RJM- questionText = " + questionText);
		logger.info("RJM- checklsit value string  = " + checkListValueString);
		logger.info("RJM- questionType = " + questionType);
		logger.info("RJM- reasonForChange = " + reasonForChange);
		
		ExtResult extResult = new ExtResult();
		Date date = new Date();
		User user = (User) request.getSession().getAttribute("user");
		
		
		 // update Audit records of this change 
		Audit newAudit = updateAudit(reasonForChange, user.getFullName(), date);
		
		
		if ( !StringUtils.isEmpty(checklistId)) {
			
			// means we are going for update the exisitng check list 
		
		// Step #1: initial creation of new CheckList
			CheckList newCheckList = new CheckList();
			String newCLId = newCheckList.getId();
			logger.info("RJM - new CheckList id = " + newCLId);
			
			// Step #2: inactivate old CheckList record:
			CheckList oldCheckList = (CheckList) ssowService.get(CheckList.class, checklistId);
			oldCheckList.setActive(0);
			oldCheckList.setModifiedBy(user.getFullName());
			oldCheckList.setModifiedDate(date);
		//	oldCheckList.setSuccessorId(newCLId);
			oldCheckList.setAuditID(newAudit);
			ssowService.save(oldCheckList); // TODO call after all values are set properly
	
			// Step #3: complete creation of new CheckList record:
			newCheckList.setActive(1);
			newCheckList.setItemNumber(oldCheckList.getItemNumber());
			//newCheckList.setOdrNbr(oldCheckList.getOdrNbr());
			newCheckList.setModifiedBy(user.getFullName());
			newCheckList.setModifiedDate(date);
			newCheckList.setCreatedBy(user.getFullName());
			newCheckList.setCreatedDate(date);
			
			// update values input by user:
			newCheckList.setQuestion(questionText);
	

			newCheckList.setQuestionType(questionType);
			newCheckList.setBasicQ(oldCheckList.getBasicQ());
		
			
			// deal with the question values 
			
			
			ssowService.save(newCheckList);  // TODO call after all values are set properly
		
			if ( !StringUtils.isEmpty(checkListValueString)) {
				
				String[] strings = checkListValueString.split(";");
				
				
				for ( int i = 0 ; i < strings.length ; i++) {
					
					logger.info("**** string " + strings[i]);
					
				CheckListValue value = new CheckListValue();
				value.setChecklistid(newCheckList.getId());
				value.setValue(strings[i]);
				
				ssowService.save(value);
			
				}
				
				
			}
			
			
			
			this.createCheckListProgramXref(checkListProgramString, newCheckList.getId());
			// now need to take care oof the thing that all the rules which refer to the old check list question , needs to refer to the new one so need to update
						// the question id on all and then do the things - 
						
						List depList  = ssowService.getRulesCriteriaXrefList(oldCheckList.getId()) ;
						
						logger.info("***** dependency list " + depList.size());
						
						
						for ( int i = 0 ; i < depList.size(); i++ ) {
							
							RulesCriteriaXref xref = (RulesCriteriaXref)depList.get(i);
							
							xref.setDependencyID(newCheckList.getId());
							
							ssowService.save(xref);
							
						}
						
						
						
						
						// Need to update all Ssow xref records also which has this record - 
						
						
						List ssowXrefList = ssowService.getSsowCheckListXrefList(oldCheckList.getId());
						
						logger.info("***** dependency list " + ssowXrefList.size());
						
						
						for ( int i = 0 ; i < ssowXrefList.size(); i++ ) {
							
							SsowCheckListXref xref = (SsowCheckListXref)ssowXrefList.get(i);
							
							xref.setCheckList(newCheckList);
							
							ssowService.save(xref);
							
						}
			
			SsowCacheUtil.flush(SsowCacheUtil.getCheckListKey());
			SsowCacheUtil.flush(SsowCacheUtil.getCheckListBasicQKey());
			SsowCacheUtil.flush(SsowCacheUtil.getCheckListAdvancedQKey());
			extResult.setData(newCheckList);
			extResult.setMsg("Check List: " + newCheckList.getItemNumber() + " is updated!");
		}else {
			
			CheckList newCheckList = new CheckList();
		//	String newCLId = newCheckList.getId();
		//	logger.info("RJM - new CheckList id = " + newCLId);
			
			newCheckList.setActive(1);
			String nextItemNumber = checkListService.getNextItemNumber();
			logger.info("**** RJM - nextItemNumber = " + nextItemNumber );
			newCheckList.setItemNumber(nextItemNumber);
			newCheckList.setModifiedBy(user.getFullName());
			newCheckList.setModifiedDate(date);
			newCheckList.setCreatedBy(user.getFullName());
			newCheckList.setCreatedDate(date);
			
		
			newCheckList.setQuestion(questionText);

			
			newCheckList.setQuestionType(questionType);
			
			// TODO: need BasciQ value entered
			newCheckList.setBasicQ(Boolean.FALSE);  // TODO: need value truly captured from user input
			newCheckList.setAuditID(newAudit);
			ssowService.save(newCheckList);  // TODO call after all values are set properly
			
		
			this.createCheckListProgramXref(checkListProgramString, newCheckList.getId());
			if ( !StringUtils.isEmpty(checkListValueString)) {
				
				String[] strings = checkListValueString.split(";");
				
				
				for ( int i = 0 ; i < strings.length ; i++) {
					
					logger.info("**** string " + strings[i]);
					
				CheckListValue value = new CheckListValue();
				value.setChecklistid(newCheckList.getId());
				value.setValue(strings[i]);
				
				ssowService.save(value);
			
				}
				
				
			}
			
			
			
			
			
			SsowCacheUtil.flush(SsowCacheUtil.getCheckListKey());
			SsowCacheUtil.flush(SsowCacheUtil.getCheckListBasicQKey());
			SsowCacheUtil.flush(SsowCacheUtil.getCheckListAdvancedQKey());
			
			extResult.setData(newCheckList);
			extResult.setMsg("Check List: " + newCheckList.getItemNumber() + " was created!");
		}
		return extResult;
	}

	
	
	

	@RequestMapping(value = "/deleteCheckList", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ExtResult deleteCheckList( HttpServletRequest request,
			@RequestParam("checkListId") String checklistId) {

		
		logger.info("In the deleteCheckList  method ");
		logger.info("RJM- checklistId = " + checklistId);
		
		ExtResult extResult = new ExtResult();
		Date date = new Date();
		User user = (User) request.getSession().getAttribute("user");
		
		 // update Audit records of this change 
		String reasonForChange = "User selected specific checklist for Deletion"; 
		Audit newAudit = updateAudit(reasonForChange, user.getFullName(), date);
		
		// Step #1: inactivate old CheckList record:
		CheckList oldCheckList = (CheckList) ssowService.get(CheckList.class, checklistId);
		oldCheckList.setActive(0);
		oldCheckList.setModifiedBy(user.getFullName());
		oldCheckList.setModifiedDate(date);
		oldCheckList.setAuditID(newAudit);
		ssowService.save(oldCheckList); // TODO call after all values are set properly

		
		
		
		List depList  = ssowService.getRulesCriteriaXrefList(oldCheckList.getId()) ;
		
		logger.info("***** dependency list " + depList.size());
		
		
		for ( int i = 0 ; i < depList.size(); i++ ) {
			
			RulesCriteriaXref xref = (RulesCriteriaXref)depList.get(i);
			
			//xref.setDependencyID(newCheckList.getId());
			
			ssowService.delete(xref);
			
		}
		
		
		
		
		// Need to update all Ssow xref records also which has this record - 
		
		
		List ssowXrefList = ssowService.getSsowCheckListXrefList(oldCheckList.getId());
		
		logger.info("***** dependency list " + ssowXrefList.size());
		
		
		for ( int i = 0 ; i < ssowXrefList.size(); i++ ) {
			
			SsowCheckListXref xref = (SsowCheckListXref)ssowXrefList.get(i);
			
			//xref.setCheckList(newCheckList);
			
			ssowService.delete(xref);
			
		}
		SsowCacheUtil.flush(SsowCacheUtil.getCheckListKey());
		SsowCacheUtil.flush(SsowCacheUtil.getCheckListBasicQKey());
		SsowCacheUtil.flush(SsowCacheUtil.getCheckListAdvancedQKey());
		
		extResult.setData(oldCheckList);
		
		
		extResult.setMsg("Check List: " + oldCheckList.getItemNumber() + " was deleted!");
		
		return extResult;
	}

	
	@RequestMapping(value = "/getCheckListSV", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ExtResult getCheckListSV(@RequestParam("criteriaId") String criteriaId  ) {

		logger.info("in the get checklist SV  method ");

		//String ssowId =  request.getParameter("ssowId");
		
		
		ExtResult extResult = new ExtResult();
		
		List returnJsonList = new ArrayList();
		

		
	
		
		try {
			
			
			
				
				
				List list = ssowService.getRulesCriteriaXrefById(criteriaId);
				
				
				logger.info("**** SV list size " + list.size());
				if ( list != null ) {
					
					for ( int k = 0 ; k < list.size(); k++) {
						
						
						RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
						
						// get the check list question 
						
						CheckList chkList = (CheckList) ssowService.getCheckList(xref.getDependencyID());
						
						if ( chkList != null ) {
							
							logger.info("**** question " + chkList.getItemNumber());
							
							JsonChkValue val = new JsonChkValue();
							val.setValue( chkList.getItemNumber() + "=>" + xref.getValue() );
							returnJsonList.add(val);
							
						}
						// now get the dependency id and see if it has a value in the map for it and compare to value i want 
						
						
						
						
					}
				}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Exception happened " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		extResult.setData(returnJsonList);
		return extResult;
	}
	
	
	
	@RequestMapping(value = "/getCheckListProgramSV", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ExtResult getCheckListProgramSV(@RequestParam("checkListId") String checkListId  ) {

		logger.info("in the get checklist SV  method ");

		//String ssowId =  request.getParameter("ssowId");
		
		
		ExtResult extResult = new ExtResult();
		
		List returnJsonList = new ArrayList();
		

		
	
		
		try {
			
			
			
				
				
				List list = ssowService.getCheckListProgramXrefById(checkListId);
				
				
				logger.info("**** SV list size " + list.size());
				if ( list != null ) {
					
					for ( int k = 0 ; k < list.size(); k++) {
						
						
						CheckListProgramXref xref = (CheckListProgramXref)list.get(k);
						
						// get the check list question 
						
					//	CheckList chkList = (CheckList) ssowService.getCheckList(xref.getDependencyID());
						
						//if ( chkList != null ) {
							
						//	logger.info("**** question " + chkList.getItemNumber());
							
							JsonChkValue val = new JsonChkValue();
							val.setValue( xref.getProgramName() + "=>" + xref.getApplicable() );
							returnJsonList.add(val);
							
						}
						// now get the dependency id and see if it has a value in the map for it and compare to value i want 
						
						
						
						
					}
				
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Exception happened " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		extResult.setData(returnJsonList);
		return extResult;
	}
	
	
	@RequestMapping(value = "/getRuleSV", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ExtResult getRuleSV(@RequestParam("criteriaId") String criteriaId  ) {

		logger.info("in the get checklist SV  method ");

		//String ssowId =  request.getParameter("ssowId");
		
		
		ExtResult extResult = new ExtResult();
		
		List returnJsonList = new ArrayList();
		

		
	
		
		try {
			
			
			
				
				
				List list = ssowService.getRulesCriteriaXrefById(criteriaId);
				
				
				logger.info("**** SV list size " + list.size());
				if ( list != null ) {
					
					for ( int k = 0 ; k < list.size(); k++) {
						
						
						RulesCriteriaXref xref = (RulesCriteriaXref)list.get(k);
						
						// get the check list question 
						
						Rules chkList = (Rules) ssowService.getRuleById(xref.getDependencyID());
						
						if ( chkList != null ) {
							
							logger.info("**** question " + chkList.getName());
							
							JsonChkValue val = new JsonChkValue();
							val.setValue( chkList.getName());
							returnJsonList.add(val);
							
						}
						// now get the dependency id and see if it has a value in the map for it and compare to value i want 
						
						
						
						
					}
				}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Exception happened " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		extResult.setData(returnJsonList);
		return extResult;
	}
	
	
	@RequestMapping(value = "/getRulesListBySdrlId", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ExtResult getRulesListBySdrlId(@RequestParam("sdrlId") String sdrlId  ) {

		logger.info("in the get checklist SV  method ");

		//String ssowId =  request.getParameter("ssowId");
		
		
logger.info("Sdrl id " + sdrlId);
		
		ExtResult extResult = new ExtResult();
		
		
		
		//User user = (User) request.getSession().getAttribute("user");
		
		if ( !StringUtils.isEmpty(sdrlId)) {
			Sdrl sdrl = (Sdrl)ssowService.get(Sdrl.class , sdrlId);
		
			if ( sdrl != null ) {
			
				
				// now once we get the rule then i need to do something with it or not - 
				
				// have seven criteria - 
				
				
				
				extResult.setData(sdrl.getRulesSet());
				
			
			//	ssowService.save(ssow);
			//	ssowService.save(ssow);
			//	extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully Completed.");
			}else {
				logger.error("Could not locate Ssow for ssow id " + sdrlId);
				extResult.setData(null);
				extResult.setSuccess("false");
				//extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
			}
		}else {
				logger.error("Could not locate Ssow for ssow id " + sdrlId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ sdrlId + ", Please try again later.");
				//extResult.setException(new Exception());
		}
		
		
		
		return extResult;
	}
	
	
	
	@RequestMapping(value = "/getRulesListByAcronymsId", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ExtResult getRulesListByAcronymsId(@RequestParam("acronymsId") String acronymsId  ) {

		logger.info("in the get checklist SV  method ");

		//String ssowId =  request.getParameter("ssowId");
		
		
logger.info("Sdrl id " + acronymsId);
		
		ExtResult extResult = new ExtResult();
		
		
		
		//User user = (User) request.getSession().getAttribute("user");
		
		if ( !StringUtils.isEmpty(acronymsId)) {
			Acronyms sdrl = (Acronyms)ssowService.get(Acronyms.class , acronymsId);
		
			if ( sdrl != null ) {
			
				
				// now once we get the rule then i need to do something with it or not - 
				
				// have seven criteria - 
				
				
				
				extResult.setData(sdrl.getRulesSet());
				
			
			//	ssowService.save(ssow);
			//	ssowService.save(ssow);
			//	extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully Completed.");
			}else {
				logger.error("Could not locate Ssow for ssow id " + acronymsId);
				extResult.setData(null);
				extResult.setSuccess("false");
				//extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
			}
		}else {
				logger.error("Could not locate Ssow for ssow id " + acronymsId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ acronymsId + ", Please try again later.");
				//extResult.setException(new Exception());
		}
		
		
		
		return extResult;
	}
	
	
	
	@RequestMapping(value = "/getRulesListByDictionaryId", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ExtResult getRulesListByDictionaryId(@RequestParam("dictionaryId") String dictionaryId  ) {

		logger.info("in the get checklist SV  method ");

		//String ssowId =  request.getParameter("ssowId");
		
		
logger.info("Sdrl id " + dictionaryId);
		
		ExtResult extResult = new ExtResult();
		
		
		
		//User user = (User) request.getSession().getAttribute("user");
		
		if ( !StringUtils.isEmpty(dictionaryId)) {
			Dictionary sdrl = (Dictionary)ssowService.get(Dictionary.class , dictionaryId);
		
			if ( sdrl != null ) {
			
				
				// now once we get the rule then i need to do something with it or not - 
				
				// have seven criteria - 
				
				
				
				extResult.setData(sdrl.getRulesSet());
				
			
			//	ssowService.save(ssow);
			//	ssowService.save(ssow);
			//	extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully Completed.");
			}else {
				logger.error("Could not locate Ssow for ssow id " + dictionaryId);
				extResult.setData(null);
				extResult.setSuccess("false");
				//extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
			}
		}else {
				logger.error("Could not locate Ssow for ssow id " + dictionaryId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ dictionaryId + ", Please try again later.");
				//extResult.setException(new Exception());
		}
		
		
		
		return extResult;
	}
	
	
	
	
	@RequestMapping(value = "/getRulesListBySectionId", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ExtResult getRulesListBySectionId(@RequestParam("sectionId") String sectionId  ) {

		logger.info("in the get checklist SV  method ");

		//String ssowId =  request.getParameter("ssowId");
		
		
logger.info("Sdrl id " + sectionId);
		
		ExtResult extResult = new ExtResult();
		
		
		
		//User user = (User) request.getSession().getAttribute("user");
		
		if ( !StringUtils.isEmpty(sectionId)) {
			Section sdrl = (Section)ssowService.get(Section.class , sectionId);
		
			if ( sdrl != null ) {
			
				
				// now once we get the rule then i need to do something with it or not - 
				
				// have seven criteria - 
				
				
				
				extResult.setData(sdrl.getRulesSet());
				
			
			//	ssowService.save(ssow);
			//	ssowService.save(ssow);
			//	extResult.setMsg("Ssow : " + ssow.getSsowNumber() + " has been successfully Completed.");
			}else {
				logger.error("Could not locate Ssow for ssow id " + sectionId);
				extResult.setData(null);
				extResult.setSuccess("false");
				//extResult.setMsg("Ssow could not be completed for ssowId : "+ ssowId + ", Please try again later.");
				//extResult.setException(new Exception());
			}
		}else {
				logger.error("Could not locate Ssow for ssow id " + sectionId);
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("Ssow could not be completed for ssowId : "+ sectionId + ", Please try again later.");
				//extResult.setException(new Exception());
		}
		
		
		
		return extResult;
	}
	
	
	private JsonCheckList convertToJson( CheckList checkList) {
		
		
		JsonCheckList chk = new JsonCheckList();
		
		chk.setId(checkList.getId());
		chk.setItemNumber(checkList.getItemNumber());
		chk.setQuestion(checkList.getQuestion());
		chk.setQuestionType(checkList.getQuestionType());
		chk.setChoices(checkList.getChoices());
		chk.setBasicQ(checkList.getBasicQ());
		return chk;
		
		
		
	}
	@RequestMapping(value = "/getAdvancedQList", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ExtResult getAdvancedQList(@RequestParam("ssowId" ) String ssowId ) {

		logger.info("In the get advanced q list method ");

		//String ssowId =  request.getParameter("ssowId");
		
		//logger.info("**** ssow id " + ssowId);
		ExtResult extResult = new ExtResult();
		List returnList = null;
		
		List returnListFinal = new ArrayList();
		
	//	logger.info("*** saving basic Q" + ssowId);
		
		
		// first get program name -- how -- 
		
		
		String programName = "";
		Map chkMap = new HashMap();
		
		Ssow ssow = null;
		if ( !StringUtils.isEmpty(ssowId)) {
		
			 ssow = (Ssow)ssowService.get(Ssow.class , ssowId);
		
			logger.info("ssow " + ssow);
			
			if ( ssow != null ) {
				
				
				Set chkList = ssow.getSsowCheckList();
				
				if ( chkList != null ) {
					
					//logger.info("**** check list set size " + chkList.size());
					Iterator it = chkList.iterator();
					while ( it.hasNext()) {
						
						SsowCheckListXref xref = (SsowCheckListXref)it.next();
						
						chkMap.put(xref.getCheckList().getId(), xref.getAnswer());
						
						
					}
					
				//	logger.info("*** map values " + chkMap.toString());
				}else {
					//logger.info("**** check list null at ssow ");
				}
				
				
				
		
				// for every Q in the basic check list - check if the value exist in the ssow check list and if it does 
				// set that value in the 
				
				
				// find the program name :
				
				CheckList chk3 = ssowService.getCheckListByNumber("CL003");
				
				if ( chk3 != null ) {
					
					// find the value of the program for it 
					
					String programValue = (String)chkMap.get(chk3.getId());
					
					
					logger.info("*** Progaram name found " + programValue);
					
					if ( !StringUtils.isEmpty(programValue)) {
						programName = programValue;
					}
				}
				
			}
		}
		try {
			
			
			 returnList = ssowService.getAdvancedQList();
			logger.info("**** Programs list size " + returnList.size());
			
			for ( int i = 0 ; i < returnList.size() ; i++) {
				
				CheckList chk = (CheckList)returnList.get(i);
				
				JsonCheckList jsonChk = convertToJson(chk);
				
					
				
				if ( chkMap != null && chkMap.containsKey(chk.getId())) {
					
					
					jsonChk.setValue((String)chkMap.get(chk.getId()));
				}
 			
				
				Set chkProgram = chk.getProgramXrefSet();
				
				if ( chkProgram != null && chkProgram.size() > 0 ) {
				//	logger.info("***** chk program size " + chkProgram.size() + "   for Question " + chk.getItemNumber());
					
					
					// if the list is there then we need to see if the program name on this is same as current one and if yes 
					
					
					// so if the question says i am applicable for uclass only then 
					// if the program and the programname are same and the question is applicable then show it 
					
					
					boolean NotApplicable = false;
					
					Iterator it = chkProgram.iterator();
					while ( it.hasNext()) {
						
					//	logger.info("**** going to iterate ");
						CheckListProgramXref xref = (CheckListProgramXref ) it.next();
						
					///	logger.info("xref applicable " + xref.getApplicable());
					//	logger.info("xref program name " + xref.getProgramName());
						
						Set programSet = new HashSet();
						if ( xref.getApplicable().equals("Y") ) {
						//	logger.info("is applicable logic");
							// it is applicable for this specific program 
							if ( xref.getProgramName().equals(programName)) {
								returnListFinal.add(jsonChk);
							}
						}
						
						 
						
						if ( xref.getApplicable().equals("N") ) {
						//	logger.info(" is not applicable logic ");
							// it is applicable for this specific program 
							if ( xref.getProgramName().equals(programName)) {
								//returnListFinal.add(chk);
								NotApplicable = true ;
							}
						}
						
						
					}
					
					
					if ( !NotApplicable) {
						returnListFinal.add(jsonChk);
					}
					
				}else {
					
					returnListFinal.add(jsonChk);
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Exception happened " + e.getMessage());
			e.printStackTrace();
		}
		
		
		returnListFinal.add(new JsonCheckList());
		returnListFinal.add(new JsonCheckList());
		returnListFinal.add(new JsonCheckList());
		extResult.setData(returnListFinal);
		return extResult;
	}
	
	
}
