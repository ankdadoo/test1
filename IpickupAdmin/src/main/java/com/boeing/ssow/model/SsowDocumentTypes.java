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
@Table(name = "document_type")
public class SsowDocumentTypes  {
	
	@Id
	@Column(name = "id")
	private String id ;
    
	
	
	@Column(name = "Description")
	private  String description ;
	


	
	
	
	


	

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

