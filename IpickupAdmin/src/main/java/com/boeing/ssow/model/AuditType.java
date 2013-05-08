package com.boeing.ssow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "audit_type")
public class AuditType {

	public final static String ACRONYMS = "1";
	public final static String DICTIONARY = "2";
	public final static String SDRL = "3";
	public final static String CHECKLIST = "4";
	public final static String SECTION = "5";
	public final static String TEMPLATE = "6";
	public final static String RULES = "7";

	
	@Id
	@Column(name = "id")
	private String id ;
	
	@Column(name = "description")
	private  String description ;

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
	
	
	
}

