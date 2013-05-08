package com.boeing.ssow.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


public class JsonCheckList extends BaseModelObject {


	

	private  String itemNumber ;
	

	private  String question ;
	

	private  Boolean basicQ ;


	private CheckListGroup checkListGroup;


	private String questionType;
	
	
	private  String odrNbr ;
	
	

	private  Integer active ;

	

	private String value ;
	
	

	   private Set<CheckListValue> choices;   


	 
	 

	   private Set<CheckListProgramXref> programXrefSet;   
	 
	 
	public String getItemNumber() {
		return itemNumber;
	}


	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	public CheckListGroup getCheckListGroup() {
		return checkListGroup;
	}


	public void setCheckListGroup(CheckListGroup checkListGroup) {
		this.checkListGroup = checkListGroup;
	}


	


	public String getQuestionType() {
		return questionType;
	}


	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}


	public String getOdrNbr() {
		return odrNbr;
	}


	public void setOdrNbr(String odrNbr) {
		this.odrNbr = odrNbr;
	}


	public Integer getActive() {
		return active;
	}


	public void setActive(Integer active) {
		this.active = active;
	}


	public Boolean getBasicQ() {
		return basicQ;
	}


	public void setBasicQ(Boolean basicQ) {
		this.basicQ = basicQ;
	}


	public Set<CheckListValue> getChoices() {
		return choices;
	}


	public void setChoices(Set<CheckListValue> choices) {
		this.choices = choices;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public Set<CheckListProgramXref> getProgramXrefSet() {
		return programXrefSet;
	}


	public void setProgramXrefSet(Set<CheckListProgramXref> programXrefSet) {
		this.programXrefSet = programXrefSet;
	}
	
	
	
	
}

