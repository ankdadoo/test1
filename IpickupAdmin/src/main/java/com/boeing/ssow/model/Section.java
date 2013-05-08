package com.boeing.ssow.model;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "section")
public class Section extends BaseModelObject {

	@Column(name = "sectionNumber")
	private  String sectionNumber ;
	
	@Column(name = "text")
	private  String text ;
	

	@Column(name = "active")
	private  Integer active ;
	
	@Column(name = "orderNumber")
	private  Integer orderNumber ;
	

	@Column(name = "sectionType")
	private  String sectionType ;

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

	@Transient
	private String rulesString ;

	@Column(name = "successorId")
	private  String successorId ;
	
	@Transient
	private String createdDateString ;
	
	@Transient
	private String modifiedDateString ;

	@ManyToMany( fetch=FetchType.EAGER)
	@JsonIgnore
    @JoinTable(
      	name="section_tree_xref", 
    	joinColumns=@JoinColumn(name="ParentID", updatable=false, insertable=false),
    	inverseJoinColumns=@JoinColumn(name="ChildID",  updatable=false, insertable=false)
    )
	@OrderBy("orderNumber")
	private Set<Section> childrenSet;
	
	public Set<Section> getChildrenSet() {
		return childrenSet;
	}

	public void setChildrenSet(Set<Section> childrenSet) {
		this.childrenSet = childrenSet;
	}

	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        	name="section_rules_xref", 
    	joinColumns=@JoinColumn(name="SectionID", updatable=false, insertable=false),
    	inverseJoinColumns=@JoinColumn(name="RulesID",  updatable=false, insertable=false)
    )
	
	private Set<Rules> rulesSet;
		
	public Set<Rules> getRulesSet() {
		return rulesSet;
	}

	public void setRulesSet(Set<Rules> rulesSet) {
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

	public String getSuccessorId() {
		return successorId;
	}

	public void setSuccessorId(String successorId) {
		this.successorId = successorId;
	}

	public String getCreatedDateString() {
		if ( getCreatedDate() != null ) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	    	//queue.setSubmitDate( formatter.format(rec.getCreatedDate()));
	    	return formatter.format(getCreatedDate());
		}
		return "";
	}


	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}


	public String getModifiedDateString() {
		
		if ( getModifiedDate() != null ) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	    	return formatter.format(getModifiedDate());
		}
		return "";
	}


	public void setModifiedDateString(String modifiedDateString) {
		this.modifiedDateString = modifiedDateString;
	}

public String getRulesString() {
		
		String returnString = "";
		if ( this.getRulesSet() != null ) {
		
			Iterator it = getRulesSet().iterator();
			while ( it.hasNext()) {
				Rules rule = (Rules)it.next();
				returnString += rule.getName() + "; ";
			}
		}
		
		
		if (! org.apache.commons.lang.StringUtils.isEmpty(returnString)) {
			
			
			int lastIndex = returnString.lastIndexOf(';');
			if ( lastIndex != 0 ) {
				returnString = returnString.substring(0 ,  lastIndex);
			}
		}
		return returnString;
	}

	public void setRulesString(String rulesString) {
		this.rulesString = rulesString;
	}
	
	
	

}

