package com.boeing.ssow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole {

	
	public final static String ADMIN = "Admin";
	public final static String SUPER_USER = "SuperUser";
	public final static String USER = "User";
	
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "description")
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


//	public UserRole getRole() {
//		return role;
//	}
//
//	public void setRole(UserRole role) {
//		this.role = role;
//	}

}
