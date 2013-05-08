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
import com.boeing.ssow.model.UserRole;
import com.boeing.ssow.service.UserRoleService;
import com.boeing.ssow.service.UserService;
import com.boeing.ssow.utils.SsowSecurityUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/manageusers")
public class ManageUsersController {

	private static final Logger logger = LoggerFactory.getLogger(ManageUsersController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String manageUsers(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome Admin! the client locale is " + locale.toString() + "in search screen ");

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		if (! SsowSecurityUtil.validateUserInSession(request)) {
			return "home";
		}
		
		return "manageusers";
	}

	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult getAllUsers(Locale locale, Model model) {

		logger.info("In the getAllUsers list method ");

		ExtResult extResult = new ExtResult();
		List returnList = userService.getAllUserList();
		logger.info("**** Users list size " + returnList.size());
		extResult.setData(returnList);
		return extResult;
	}

	@RequestMapping(value = "/getUsers", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult getUsers(@RequestParam("username") String username,
			@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname) {

		logger.info("In the getUsers  list  method ");

		ExtResult extResult = new ExtResult();
		List returnList = userService.getUserList(username, firstname, lastname);
		logger.info("**** Users list size " + returnList.size());
		extResult.setData(returnList);
		return extResult;
	}

	@RequestMapping(value = "/getUserRoles", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult getUserRoles(Locale locale, Model model) {

		logger.info("In the getUserRoles list  method ");

		ExtResult extResult = new ExtResult();
		List returnList = userRoleService.getUserRoleList();
		logger.info("**** Users Role list size " + returnList.size());
		extResult.setData(returnList);
		return extResult;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult addUser(Locale locale, HttpServletRequest request,
			@RequestParam("username2") String username,
			@RequestParam("firstname2") String firstname,
			@RequestParam("lastname2") String lastname,
			@RequestParam("password2") String password,
			@RequestParam("idTypeHidden") String userroleType) {

		logger.info("In the addUser method ");

		ExtResult extResult = new ExtResult();
		Date date = new Date();

		boolean checkUserExist = userService.checkUserExistByUserName(username);
		if (checkUserExist) {
			logger.info("User '" + username + "' Already Exists - cannot create user");
			// problem - existing user found with the new username supplied: 
			extResult.setData(null);
			extResult.setSuccess("false");
			extResult.setMsg("User '" + username + "' Already Exists - cannot create user");
			extResult.setException(new Exception());

		} else {

			// get name of user making changes:
			User user = (User) request.getSession().getAttribute("user");
//			String editingUserName = getEditingUserName(request);

			UserRole userRole = userRoleService.getUserRole(userroleType);
			User newUser = new User();
			newUser.setUsername(username);
			newUser.setFirstName(firstname);
			newUser.setLastName(lastname);
			newUser.setPassword(password);
			newUser.setRole(userRole);
			newUser.setActive(1);
			newUser.setCreatedBy(user.getFullName()); //editingUserName);
			newUser.setModifiedBy(user.getFullName()); //editingUserName);
			newUser.setCreatedDate(date);
			newUser.setModifiedDate(date);
			String addStatus = userService.addUser(newUser);
			logger.info("User '" + username + "' created");
			extResult.setData(addStatus);
			extResult.setMsg("User '" + username + "' created!");

		}
		return extResult;
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult updateUser(Locale locale, HttpServletRequest request,
			@RequestParam("userid2") String userid,
			@RequestParam("username2") String username,
			@RequestParam("firstname2") String firstname,
			@RequestParam("lastname2") String lastname,
			@RequestParam("password2") String password,
//			@RequestParam("activeidTypeHidden") int activestatus,
			@RequestParam("idTypeHidden") String userroleType) {
		logger.info("In the updateUser  method ");

		ExtResult extResult = new ExtResult();
		Date date = new Date();
		UserRole userRole = userRoleService.getUserRole(userroleType);

		// get name of user making changes:
		User user = (User) request.getSession().getAttribute("user");
//		String editingUserName = getEditingUserName(request);

		User currentUser = userService.getUser(userid);
		
		if (currentUser != null) {
			boolean checkUserExist = false;
			if (!currentUser.getUsername().equalsIgnoreCase(username)){
				checkUserExist = userService.checkUserExistByUserName(username);
			}
			if (!checkUserExist){
				currentUser.setUsername(username);
				currentUser.setFirstName(firstname);
				currentUser.setLastName(lastname);
				currentUser.setPassword(password);
				currentUser.setModifiedBy(user.getFullName()); // editingUserName); 
				currentUser.setModifiedDate(date);
				currentUser.setRole(userRole);
				
				// make this Non-Editable per conversation with Ankur (RJM 12/17/12)
				// currentUser.setActive(activestatus);
				
				String editStatus = userService.updateUser(currentUser);
				logger.info("User '" + username + "' updated!");
				extResult.setData(editStatus);
				extResult.setMsg("User '" + username + "' updated!");
			}
			else
			{
				logger.info("User '" + username + "' Already Exists - cannot update to this username");
				// problem - existing user found with the new username supplied: 
				extResult.setData(null);
				extResult.setSuccess("false");
				extResult.setMsg("User '" + username + "' Already Exists - cannot update to this username");
				extResult.setException(new Exception());
			}			
		}
		else {
			logger.info("User '" + username + "' not found in the system");
			// problem - existing user found with the new username supplied: 
			extResult.setData(null);
			extResult.setSuccess("false");
			extResult.setMsg("User '" + username + "' not found in the system");
			extResult.setException(new Exception());
		}
			
		return extResult;
	}



	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ExtResult deleteUser(Locale locale,  HttpServletRequest request,
			@RequestParam("username2") String username,
			@RequestParam("userid2") String userid) {

		logger.info("In the deleteUser  method ");

		ExtResult extResult = new ExtResult();
		Date date = new Date();
//		String deleteStatus = "Unsuccessful User Creation";
		User currentUser = userService.getUser(userid);
		if (currentUser != null) {
			// get name of user making changes:
			User user = (User) request.getSession().getAttribute("user");
//			String editingUserName = getEditingUserName(request);

			currentUser.setActive(0);
			currentUser.setModifiedBy(user.getFullName()); //editingUserName); 
			currentUser.setModifiedDate(date);
			String deleteStatus = userService.updateUser(currentUser);
			logger.info("User '" + username + "' deleted");
			extResult.setData(deleteStatus);
			extResult.setMsg("User '" + username + "' deleted!");
		}
		else {
			logger.info("User '" + username + "' not found in the system");
			// problem - existing user found with the new username supplied: 
			extResult.setData(null);
			extResult.setSuccess("false");
			extResult.setMsg("User '" + username + "' not found in the system");
			extResult.setException(new Exception());
		}
		return extResult;
	}
	

//	private String getEditingUserName(HttpServletRequest request) {
//		// get name of user making changes:
//		Object user = request.getSession().getAttribute("user");
//		User editingUser = (User) user;
//		String editingUserName = "UNKNOWN";
//		if (user == null) {
//			// means user is not logged in and user needs to be rerouted to
//			// login screen
//			logger.info("PROBLEM while adding user name for traceability");
//		} else {
//			if (!(user instanceof User)) {
//				// we have a problem - object user is not actually a user
//				logger.info("PROBLEM while adding user name for traceability");
//			} 
//			else
//			{
//				editingUserName = editingUser.getUsername();
//			}
//
//		}
//		return editingUserName;
//	}
//	
}
