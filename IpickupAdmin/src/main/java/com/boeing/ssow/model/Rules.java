package com.boeing.ssow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "rules")
public class Rules extends BaseModelObject implements Comparable {


    
	@Column(name = "ruleName")
	private  String name ;
	
	@Column(name = "description")
	private  String description ;
	

//	@ManyToOne(fetch=FetchType.EAGER)
//    @JoinColumn(name = "AuditID")
//	private Audit audit;
//
	@Column(name = "ChecklistCriteriaTrueID")
	private  String checklistCriteriaTrueID ;
	
	@Column(name = "ChecklistCriteriaAnyTrueID")
	private  String checklistCriteriaAnyTrueID ;
	
	@Column(name = "ChecklistCriteriaAllTrueID")
	private  String checklistCriteriaAllTrueID ;
	
	@Column(name = "ChecklistCriteriaAnyNotTrueID")
	private  String checklistCriteriaAnyNotTrueID ;
	
	@Column(name = "RulesCriteriaAnyTrueID")
	private  String rulesCriteriaAnyTrueID ;
	
	@Column(name = "RulesCriteriaAllTrueID")
	private  String rulesCriteriaAllTrueID ;
	
	@Column(name = "RulesCriteriaNotTrueID")
	private  String rulesCriteriaNotTrueID ;
	

	
	@Column(name = "active")
	private  Integer active ;
	
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
		return checklistCriteriaTrueID;
	}


	public void setChecklistCriteriaTrueID(String checklistCriteriaTrueID) {
		this.checklistCriteriaTrueID = checklistCriteriaTrueID;
	}


	public String getChecklistCriteriaAnyTrueID() {
		return checklistCriteriaAnyTrueID;
	}


	public void setChecklistCriteriaAnyTrueID(String checklistCriteriaAnyTrueID) {
		this.checklistCriteriaAnyTrueID = checklistCriteriaAnyTrueID;
	}


	public String getChecklistCriteriaAllTrueID() {
		return checklistCriteriaAllTrueID;
	}


	public void setChecklistCriteriaAllTrueID(String checklistCriteriaAllTrueID) {
		this.checklistCriteriaAllTrueID = checklistCriteriaAllTrueID;
	}


	public String getChecklistCriteriaAnyNotTrueID() {
		return checklistCriteriaAnyNotTrueID;
	}


	public void setChecklistCriteriaAnyNotTrueID(
			String checklistCriteriaAnyNotTrueID) {
		this.checklistCriteriaAnyNotTrueID = checklistCriteriaAnyNotTrueID;
	}


	


	public String getRulesCriteriaAnyTrueID() {
		return rulesCriteriaAnyTrueID;
	}


	public void setRulesCriteriaAnyTrueID(String rulesCriteriaAnyTrueID) {
		this.rulesCriteriaAnyTrueID = rulesCriteriaAnyTrueID;
	}


	public String getRulesCriteriaAllTrueID() {
		return rulesCriteriaAllTrueID;
	}


	public void setRulesCriteriaAllTrueID(String rulesCriteriaAllTrueID) {
		this.rulesCriteriaAllTrueID = rulesCriteriaAllTrueID;
	}


	public String getRulesCriteriaNotTrueID() {
		return rulesCriteriaNotTrueID;
	}


	public void setRulesCriteriaNotTrueID(String rulesCriteriaNotTrueID) {
		this.rulesCriteriaNotTrueID = rulesCriteriaNotTrueID;
	}


	public Integer getActive() {
		return active;
	}


	public void setActive(Integer active) {
		this.active = active;
	}


	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		
		Rules rule = (Rules)o;
		
		if ( this.getId().equals(rule.getId())) {
			return 0;
		}else {
			return 1;
		}
		
		//return 0;
	}
	
	
	

	

	

	

	
	


	

	
	
	
}

