package com.boeing.ssow.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.boeing.ssow.model.CheckList;
import com.boeing.ssow.model.CheckListGroup;
import com.boeing.ssow.model.ExtResult;
import com.boeing.ssow.model.User;
import com.boeing.ssow.service.AuditService;
import com.boeing.ssow.service.AuditTypeService;
import com.boeing.ssow.service.CheckListGroupService;
import com.boeing.ssow.service.CheckListService;

import com.boeing.ssow.service.QuestionTypeService;
import com.boeing.ssow.service.SsowService;
import com.boeing.ssow.utils.SsowCacheUtil;
import com.boeing.ssow.utils.SsowSecurityUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/managechecklist")
public class ManageCheckListController {

	private static final Logger logger = LoggerFactory.getLogger(ManageCheckListController.class);
	



	@Autowired
	private CheckListService checklistService;

	@Autowired
	private CheckListGroupService checklistGroupService;

	@Autowired
	private QuestionTypeService questionTypeService;
	
	@Autowired
	private AuditService auditService;

	@Autowired
	private AuditTypeService auditTypeService;

	//@Autowired
	//private ProgramService programService;
	
	@Autowired
	private SsowService ssowService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String manageChecklist( HttpServletRequest request) {
		
logger.info("Showing manage checklist screen ");
		if (! SsowSecurityUtil.validateUserInSession(request)) {
			return "home";
		}
		
		return "managechecklist";
	}

	@RequestMapping(value = "/getAllCheckList", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult getAllCheckList(Locale locale, Model model) {

		logger.info("In the getAllPrograms list method ");

		ExtResult extResult = new ExtResult();
	//	List returnList = ssowService.getCheckList();
		List returnList = checklistService.getAllCheckList();
		logger.info("**** CheckList size " + returnList.size());
		extResult.setData(returnList);
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
	
	

	
	

	
	
	

	@RequestMapping(value = "/deleteCheckList", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult deleteCheckList(Locale locale, HttpServletRequest request,
			@RequestParam("checklistId") String checklistId) {
//			@RequestParam("checklistId2") String checklistId,
//			@RequestParam("reasonForChange") String reasonForChange) { 
		
		logger.info("In the deleteCheckList  method ");
		logger.info("RJM- checklistId = " + checklistId);
		
		ExtResult extResult = new ExtResult();
		Date date = new Date();
		User user = (User) request.getSession().getAttribute("user");
		
		 // update Audit records of this change 
		String reasonForChange = "User selected specific checklist for Deletion"; 
		Audit newAudit = updateAudit(reasonForChange, user.getFullName(), date);
		
		// Step #1: inactivate old CheckList record:
		CheckList oldCheckList = (CheckList) checklistService.get(CheckList.class, checklistId);
		oldCheckList.setActive(0);
		oldCheckList.setModifiedBy(user.getFullName());
		oldCheckList.setModifiedDate(date);
		oldCheckList.setAuditID(newAudit);
		checklistService.save(oldCheckList); // TODO call after all values are set properly

		
		SsowCacheUtil.flush(SsowCacheUtil.getCheckListKey());
		SsowCacheUtil.flush(SsowCacheUtil.getCheckListBasicQKey());
		SsowCacheUtil.flush(SsowCacheUtil.getCheckListAdvancedQKey());
		extResult.setData(oldCheckList);
		extResult.setMsg("Check List: " + oldCheckList.getItemNumber() + " was deleted!");
		
		return extResult;
	}


	
	
	public Audit updateAudit(String reasonForChange, String createdBy, Date createdDate) {
		
		Audit audit = new Audit();
		audit.setReasonForChange(reasonForChange);
		audit.setCreatedBy(createdBy);
		audit.setCreatedDate(createdDate);
		
		AuditType auditType = (AuditType) auditTypeService.get(AuditType.class, AuditType.CHECKLIST);
		audit.setAudType(auditType);
		
		auditService.save(audit);
		logger.info("Audit record#: " + audit.getId() + " was created for a Section change!");
		
		return audit;
		
	}


	
// RJM - FINISH	
	
}
