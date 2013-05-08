package com.boeing.ssow.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Checklist_Values_Xref")
public class CheckListValue {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "checklistid")
	private String checklistid;

	

	@Column(name = "value")
	private String value;

	;

	
	public CheckListValue() {
		this.id = java.util.UUID.randomUUID().toString();
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getChecklistid() {
		return checklistid;
	}


	public void setChecklistid(String checklistid) {
		this.checklistid = checklistid;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

	



	

	

	

	

	


	
}
