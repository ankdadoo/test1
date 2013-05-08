package com.boeing.ssow.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "ssow_status")
public class SsowStatus  {
	
	@Id
	@Column(name = "id")
	private String id ;
    
	
	
	@Column(name = "Description")
	private  String description ;
	


	
	public final static String InWork = "In-Work";
	public final static String InReviw = "In-Review";
	public final static String Complete = "Complete";
	
	


	

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}




	
}

