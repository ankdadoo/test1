package com.boeing.ssow.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "program_file")
public class ProgramFile {

	@Id
	@Column(name = "id")
	private String id ;

	
	@Column(name = "ProgramName")
	private  String programName ;
	

	@Column(name = "InputTemplateLocation")
	private String inputTemplateLocation;
	
	
	public ProgramFile() {
		this.id = java.util.UUID.randomUUID().toString();
	}



	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}



	public String getProgramName() {
		return programName;
	}



	public void setProgramName(String programName) {
		this.programName = programName;
	}



	public String getInputTemplateLocation() {
		return inputTemplateLocation;
	}



	public void setInputTemplateLocation(String inputTemplateLocation) {
		this.inputTemplateLocation = inputTemplateLocation;
	}



	
	


	
	
	
}

