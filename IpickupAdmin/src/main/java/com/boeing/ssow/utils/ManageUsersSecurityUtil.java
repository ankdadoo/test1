package com.boeing.ssow.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.boeing.ssow.model.User;

@Component
public class ManageUsersSecurityUtil {

	public static boolean validateUserInSession(HttpServletRequest request) {

		Object user = request.getSession().getAttribute("user");

		if (user == null) {
			// means user is not logged in and user needs to be rerouted to
			// login screen
			return false;
		} else {
			if (!(user instanceof User)) {
				// we have a problem - object user is not actually a user
				return false;
			}

		}

		return true;

	}
}
