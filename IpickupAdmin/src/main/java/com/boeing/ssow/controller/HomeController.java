package com.boeing.ssow.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.boeing.ssow.model.UserRole;
import com.boeing.ssow.service.UserService;



/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/home")
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private UserService userService;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Locale locale, Model model , HttpServletRequest request) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("role");
		return "home";
	}
	
	
	
	@RequestMapping(value = "/loginUser", method = RequestMethod.POST , headers="Accept=application/json")
	public @ResponseBody ExtResult loginUser(@RequestParam("username") String username , @ RequestParam("password") String password , 
			HttpServletRequest request  , HttpServletResponse response ) throws Exception{
		logger.info("In the submit method ");
		
	
		
		
		logger.info("*** username " + username);
		logger.info("**  password " + password);
		ExtResult extResult = new ExtResult();
		User user = userService.validateUser(username, password);
		
		if ( user != null ) {
			
			logger.info("*** Usr info " + user.toString());
			if ( user.getRole() != null ) {
				
				if ( UserRole.ADMIN.equals(user.getRole().getDescription()) ||  UserRole.SUPER_USER.equals(user.getRole().getDescription())) {
					// means user is admin or super user 
					// compare passwords 
					logger.info("User is admin ");
					if ( password.equals(user.getPassword())) {
						// passwords matched 
						logger.info("User is admin - password matched ");
						request.getSession().setAttribute("user", user);
						request.getSession().setAttribute("role", user.getRole().getDescription());
						extResult.setData(user);
					}else {
						logger.info("User is admin - password didnt matched ");
						// password didnt matched so is an issue 
						// throw error 
						extResult.setData(null);
						extResult.setSuccess("false");
						extResult.setMsg("User is not valid Boeing Phantom Works SuperUser/Admin  - Password MisMatch");
						extResult.setException(new Exception());
						//response.sendError(response.SC_BAD_REQUEST);
					}
					
				}else {
					logger.info("User is normal user so no password check  ");
					request.getSession().setAttribute("user", user);
					request.getSession().setAttribute("role", user.getRole().getDescription());
					extResult.setData(user);
				}
			}else {
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("User is not valid Boeing Phantom Works User - No User Role defined for this User");
				extResult.setException(new Exception());
				//response.sendError(response.SC_BAD_REQUEST);
			}
			// check here if the user if admin or super user 
			
		}else {
			
			// its a problem - no user found 
			extResult.setData(null);
			extResult.setSuccess("false");
			extResult.setMsg("User is not valid Boeing Phantom Works User");
			extResult.setException(new Exception());
			//response.sendError(response.SC_BAD_REQUEST);
			
		}
		
		
		
		///ListWrapper wrapper = new ListWrapper(list);
		return extResult;
	}
	
	
	
	
}
