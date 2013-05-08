package com.boeing.ssow.model;

import org.springframework.util.StringUtils;

public class JsonSection extends BaseModelObject {

	private  String sectionNumber ;
	
	private  String text ;
	
	private  Integer active ;
	
	private  Integer orderNumber ;
	
	private  String sectionType ;

//	private  String successorId ;
	
//	private Set<JsonSection> childrenSet;
	
	private String rulesSet;
		
	public String getRulesSet() {
		if (! StringUtils.hasLength(rulesSet)) {
			return "";
		}
		return rulesSet;
	}

	public void setRulesSet(String rulesSet) {
		this.rulesSet = rulesSet;
	}

	public String getSectionNumber() {
		return sectionNumber;
	}

	public void setSectionNumber(String sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

}  // JsonSection

