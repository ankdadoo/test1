package com.boeing.ssow.model;

import org.springframework.util.StringUtils;


public class JsonRules {


    private String Id;

	private  String name ;
	

	private  String description ;
	

	private  String checklistCriteriaTrueID ;
	

	private  String checklistCriteriaAnyTrueID ;
	

	private  String checklistCriteriaAllTrueID ;
	

	private  String checklistCriteriaAnyNotTrueID ;
	

	private  String rulesCriteriaAnyTrueID ;
	

	private  String rulesCriteriaAllTrueID ;
	

	private  String rulesCriteriaNotTrueID ;
	

	

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getChecklistCriteriaTrueID() {
		
		if (! StringUtils.hasLength(checklistCriteriaTrueID)) {
			return "";
		}
		return checklistCriteriaTrueID;
	}


	public void setChecklistCriteriaTrueID(String checklistCriteriaTrueID) {
		this.checklistCriteriaTrueID = checklistCriteriaTrueID;
	}


	public String getChecklistCriteriaAnyTrueID() {
		
		if (! StringUtils.hasLength(checklistCriteriaAnyTrueID)) {
			return "";
		}
		return checklistCriteriaAnyTrueID;
	}


	public void setChecklistCriteriaAnyTrueID(String checklistCriteriaAnyTrueID) {
		this.checklistCriteriaAnyTrueID = checklistCriteriaAnyTrueID;
	}


	public String getChecklistCriteriaAllTrueID() {
		
		if (! StringUtils.hasLength(checklistCriteriaAllTrueID)) {
			return "";
		}
		return checklistCriteriaAllTrueID;
	}


	public void setChecklistCriteriaAllTrueID(String checklistCriteriaAllTrueID) {
		this.checklistCriteriaAllTrueID = checklistCriteriaAllTrueID;
	}


	public String getChecklistCriteriaAnyNotTrueID() {
		
		if (! StringUtils.hasLength(checklistCriteriaAnyNotTrueID)) {
			return "";
		}
		return checklistCriteriaAnyNotTrueID;
	}


	public void setChecklistCriteriaAnyNotTrueID(
			String checklistCriteriaAnyNotTrueID) {
		this.checklistCriteriaAnyNotTrueID = checklistCriteriaAnyNotTrueID;
	}


	


	public String getRulesCriteriaAllTrueID() {
		if (! StringUtils.hasLength(rulesCriteriaAllTrueID)) {
			return "";
		}
		return rulesCriteriaAllTrueID;
	}


	public void setRulesCriteriaAllTrueID(String rulesCriteriaAllTrueID) {
		this.rulesCriteriaAllTrueID = rulesCriteriaAllTrueID;
	}


	public String getRulesCriteriaNotTrueID() {
		if (! StringUtils.hasLength(rulesCriteriaNotTrueID)) {
			return "";
		}
		return rulesCriteriaNotTrueID;
	}


	public void setRulesCriteriaNotTrueID(String rulesCriteriaNotTrueID) {
		this.rulesCriteriaNotTrueID = rulesCriteriaNotTrueID;
	}


	public String getId() {
		return Id;
	}


	public void setId(String id) {
		Id = id;
	}


	public String getRulesCriteriaAnyTrueID() {
		return rulesCriteriaAnyTrueID;
	}


	public void setRulesCriteriaAnyTrueID(String rulesCriteriaAnyTrueID) {
		this.rulesCriteriaAnyTrueID = rulesCriteriaAnyTrueID;
	}





	

	



	

	

	


	

	
	


	

	
	
	
}

