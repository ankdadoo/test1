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



import com.boeing.ssow.model.ExtResult;
import com.boeing.ssow.model.User;
import com.boeing.ssow.service.SsowService;
import com.boeing.ssow.utils.SsowSecurityUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/ssowQueue")
public class SsowQueueController {
	
	private static final Logger logger = LoggerFactory.getLogger(SsowQueueController.class);

	@Autowired
	private SsowService ssowService;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model , HttpServletRequest request) {
		logger.info("In the SSow Queue screen ");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		
		if (! SsowSecurityUtil.validateUserInSession(request)) {
			return "home";
		}
		return "ssowQueue";
	}
	
	
	
	@RequestMapping(value = "/getQueue", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult getQueue(@RequestParam("queueType") String queueType , 
			HttpServletRequest request) {
		logger.info("In the get queue  method " + queueType);
		
		User  user = (User)request.getSession().getAttribute("user");
		
		
		
		List returnList = null ;
		
		
		
		if ( queueType.equals("User")) {
		
			returnList = ssowService.getSsowList(user);
		}else {
			returnList = ssowService.getSsowList();
		}
		
		
		logger.info("**** Ssoq Queue list size " + returnList.size());
		ExtResult extResult = new ExtResult();
		
		extResult.setData(returnList);
		///ListWrapper wrapper = new ListWrapper(list);
		return extResult;
	}
	
	
	
	@RequestMapping(value = "/getAllQueue", method = RequestMethod.GET , headers="Accept=application/json")
	public @ResponseBody ExtResult getAllQueue() {
		logger.info("In the get queue  method ");
		
		
		
		List returnList = ssowService.getSsowList();
		
		
		
		logger.info("**** Ssoq Queue list size " + returnList.size());
		ExtResult extResult = new ExtResult();
		
		extResult.setData(returnList);
		///ListWrapper wrapper = new ListWrapper(list);
		return extResult;
	}
}
