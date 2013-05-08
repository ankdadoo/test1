package com.boeing.ssow.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CheckList_Program_Xref")
public class CheckListProgramXref {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "checklistid")
	private String checklistid;

	

	@Column(name = "ProgramName")
	private String programName;

	@Column(name = "Applicable")
	private String applicable;

	
	public CheckListProgramXref() {
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


	public String getProgramName() {
		return programName;
	}


	public void setProgramName(String programName) {
		this.programName = programName;
	}


	public String getApplicable() {
		return applicable;
	}


	public void setApplicable(String applicable) {
		this.applicable = applicable;
	}




	

	

	

	

	


	
}
