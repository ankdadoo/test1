package com.boeing.ssow.model;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "dictionary")
public class Dictionary extends BaseModelObject {


	
	@Column(name = "name")
	private  String name ;
	
	@Column(name = "description")
	private  String description ;
	
	@Column(name = "Active")
	private  Integer active ;
	
	@Transient
	private String rulesString ;
	
	@JsonIgnore
	@ManyToMany( fetch=FetchType.EAGER)
    @JoinTable(
    	name="dictionary_rules_xref", 
    	joinColumns=@JoinColumn(name="DictionaryID", updatable=false, insertable=false),
    	inverseJoinColumns=@JoinColumn(name="RulesID",  updatable=false, insertable=false)
    )
	private Set<Rules> rulesSet;
	
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

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}



}

