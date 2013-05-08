package com.boeing.ssow.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rules_criteria_xref")
public class RulesCriteriaXref {

	@Id
	@Column(name = "id")
	private String id;
	
	
	@Column(name = "CriteriaID")
	private String criteriaId;

	
	@Column(name = "DependencyID")
	private String dependencyID;

	

	@Column(name = "DependencyType")
	private String dependencyType;

	
	@Column(name = "Value")
	private String value;

	public String getCriteriaId() {
		return criteriaId;
	}

	public void setCriteriaId(String criteriaId) {
		this.criteriaId = criteriaId;
	}

	public String getDependencyID() {
		return dependencyID;
	}

	public void setDependencyID(String dependencyID) {
		this.dependencyID = dependencyID;
	}

	public String getDependencyType() {
		return dependencyType;
	}

	public void setDependencyType(String dependencyType) {
		this.dependencyType = dependencyType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	

	public RulesCriteriaXref () {
		
		this.id = java.util.UUID.randomUUID().toString();
		
	}
	


	

	

	

	

	


	
}
