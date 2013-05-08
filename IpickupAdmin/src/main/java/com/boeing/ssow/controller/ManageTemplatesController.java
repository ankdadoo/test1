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
import org.springframework.web.bind.annotation.ResponseBody;

import com.boeing.ssow.model.ExtResult;

import com.boeing.ssow.utils.SsowSecurityUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/managetemplates")
public class ManageTemplatesController {

	private static final Logger logger = LoggerFactory.getLogger(ManageTemplatesController.class);

	//@Autowired
	//private ProgramService templateService;

//	@Autowired
//	private UserRoleService userRoleService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String manageTemplates(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome Admin! the client locale is " + locale.toString() + "in search screen ");

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		if (! SsowSecurityUtil.validateUserInSession(request)) {
			return "home";
		}
		
		return "managetemplates";
	}

	@RequestMapping(value = "/getAllTemplates", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult getAllTemplates(Locale locale, Model model) {

		logger.info("In the getAllTemplates list method ");

		ExtResult extResult = new ExtResult();
		//List returnList = templateService.getAllTemplates();
		//logger.info("**** Templates list size " + returnList.size());
		//extResult.setData(returnList);
		return extResult;
	}

	
}
