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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boeing.ssow.model.ExtResult;
import com.boeing.ssow.model.SsowSearchCriteria;
import com.boeing.ssow.model.User;
import com.boeing.ssow.service.SsowService;
import com.boeing.ssow.utils.SsowSecurityUtil;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/search")
public class SearchController {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private SsowService ssowService ;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model , HttpServletRequest request) {
		logger.info("Welcome home! the client locale is "+ locale.toString() + "in search screen ");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		if (! SsowSecurityUtil.validateUserInSession(request)) {
			return "home";
		}
		
		return "search";
	}
	
	
	
	
	
	@RequestMapping(value = "/searchResults.jsp", method = RequestMethod.GET)
	public String searchResults(Locale locale, Model model , HttpServletRequest request) {
		logger.info("Welcome home! the client locale is "+ locale.toString() + "in search screen ");
		
		logger.info("trying to get to search results .jsp");
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		
		if (! SsowSecurityUtil.validateUserInSession(request)) {
			return "home";
		}
		return "ssowSearchResults";
	}
	@RequestMapping(value = "/doSearch", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult doSearch(
			@RequestParam("ssowNumber") String ssowNumber,
			@RequestParam("ssowDate") String ssowDate , 
			//@RequestParam("programDDHidden") String program , 
			@RequestParam("supplier") String supplier , 
			@RequestParam("statusDDHidden") String status,
			//@RequestParam("documentTypeDDHidden") String documentType,
			@RequestParam("sre") String sre,
			@RequestParam("procNumber") String procNumber,
			@RequestParam("leadFocal") String leadFocal,
			@RequestParam("iptTeamLead") String iptTeamLead,
			@RequestParam("iptSupplierMgr") String iptSupplierMgr ,
			@RequestParam("supplierMgmtMgr") String supplierMgmtMgr,
			@RequestParam("programManager") String programManager,
			@RequestParam("procurementAgent") String procurementAgent,
			HttpServletRequest request
			) {
		logger.info("In the do search  method ");
		
	
		SsowSearchCriteria criteria = new SsowSearchCriteria();
		criteria.setSsowNumber(ssowNumber);
	//	logger.info("**** doing search criteria " + program);
		logger.info("**** doing search criteria " + status);
	//	logger.info("**** doing search criteria " + documentType);
		if ( StringUtils.hasLength(ssowDate)) {
			
		
		criteria.setSsowDate(new Date(ssowDate));
		}
	//	criteria.setProgram(program);
		criteria.setSupplier(supplier);
		criteria.setStatus(status);
		criteria.setSre(sre);
		//criteria.setDocumentType(documentType);
		criteria.setProcNumber(procNumber);
		criteria.setLeadFocal(leadFocal);
		criteria.setIptTeamLead(iptTeamLead);
		criteria.setIptSupplierMgr(iptSupplierMgr);
		criteria.setSupplierMgmtMgr(supplierMgmtMgr);
		criteria.setProgramManager(programManager);
		criteria.setProcurementAgent(procurementAgent);
		request.getSession().setAttribute("ssowSearchCriteria", criteria);
		
		///logger.info("*** username " + username);
		//logger.info("**  password " + password);
		ExtResult extResult = new ExtResult();
		
		
		
		
		///ListWrapper wrapper = new ListWrapper(list);
		return extResult;
	}
	
	
	@RequestMapping(value = "/getSearchResults", method = RequestMethod.GET , headers="Accept=application/json")
	public @ResponseBody ExtResult getQueue(HttpServletRequest request) {
		logger.info("In the get search results   method ");
		
		
		
		SsowSearchCriteria criteria = (SsowSearchCriteria)request.getSession().getAttribute("ssowSearchCriteria");
		ExtResult extResult = new ExtResult();
		if ( criteria != null ) {
			
			
			
		List list = 	ssowService.getSearchResults(criteria);
		
		extResult.setData(list);
		}
		
		//logger.info("**** Ssoq Queue list size " + returnList.size());
		
		
		
		///ListWrapper wrapper = new ListWrapper(list);
		return extResult;
	}
	
	
}
