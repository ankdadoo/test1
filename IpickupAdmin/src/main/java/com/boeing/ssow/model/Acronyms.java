package com.boeing.ssow.model;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "acronyms")
public class Acronyms extends BaseModelObject {
	

    
	@Column(name = "Name")
	private  String name ;
	
	@Column(name = "Description")
	private  String description ;
	


	@Column(name = "Active")
	private  Integer active ;
	
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.EAGER)
    @JoinTable(
    	name="acronyms_rules_xref", 
    	joinColumns=@JoinColumn(name="AcronymsID", updatable=false, insertable=false),
    	inverseJoinColumns=@JoinColumn(name="RulesID",  updatable=false, insertable=false)
    )
	
	private Set<Rules> rulesSet;
	
	
	
	
	
	@Transient
	private String rulesString ;
	
	public Set<Rules> getRulesSet() {
		return rulesSet;
	}

	public void setRulesSet(Set<Rules> rulesSet) {
		this.rulesSet = rulesSet;
	}


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




	public Integer getActive() {
		return active;
	}


	public void setActive(Integer active) {
		this.active = active;
	}

	public String getRulesString() {
		
		String returnString = "";
		if ( this.getRulesSet() != null ) {
		
			Iterator it = getRulesSet().iterator();
			while ( it.hasNext()) {
				Rules rule = (Rules)it.next();
				returnString += rule.getName() + ";";
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

