package com.boeing.ssow.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "audit")
public class Audit {

	@Id
	@Column(name = "id")
	private String id ;
	
	@Column(name = "reasonForChange")
	private  String reasonForChange ;
	
	@Column(name = "CreatedDate")
	private  Date createdDate ;
	

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "AuditTypeID")
	private AuditType audType;
	
	
//	@ManyToOne(fetch=FetchType.EAGER)
//    @JoinColumn(name = "CreatedBy")
//	private User createdBy;
//	
//	public User getCreatedBy() {
//		return createdBy;
//	}
//
//
//	public void setCreatedBy(User createdBy) {
//		this.createdBy = createdBy;
//	}
//	
	
	@Column(name = "createdBy")
	private String createdBy;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Audit() {
		this.id = java.util.UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getReasonForChange() {
		return reasonForChange;
	}


	public void setReasonForChange(String reasonForChange) {
		this.reasonForChange = reasonForChange;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public AuditType getAudType() {
		return audType;
	}


	public void setAudType(AuditType audType) {
		this.audType = audType;
	}


}

