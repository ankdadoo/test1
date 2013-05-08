package com.boeing.ssow.model;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table(name = "ssow")
public class Ssow extends BaseModelObject {

	@Column(name = "SsowNumber")
	private  String ssowNumber ;
	
	@Column(name = "description")
	private  String description ;
	
	@Column(name = "revNbr")
	private  Integer revNbr ;
	
	@Column(name = "ProcurementAgent")
	private  String procurementAgent ;
	
	@Column(name = "ProgramMgr")
	private  String programMgr ;
	
	@Column(name = "SupplierMgmtMgr")
	private  String supplierMgmtMgr ;
	
	@Column(name = "IPTSupplierMgr")
	private  String iptSupplierMgr ;
	
	@Column(name = "IPTTeamLead")
	private  String iptTeamLead ;
	
	@Column(name = "LeadEngFocal")
	private  String leadEngFocal ;
	
	@Column(name = "ProcSpecNumber")
	private  String procSpecNumber ;
	
	@Column(name = "SRE")
	private  String sre ;
	
	
	@Column(name = "fileLocation")
	private  String fileLocation ;
	
	//@Column(name = "Program")
	//private  String program ;
	
	@Column(name = "Supplier")
	private  String supplier ;
	
	
	//@Column(name = "documentType")
	//private  String documentType ;
	
	@Column(name = "status")
	private  String status ;
	
	@Column(name = "active")
	private  Integer active ;
	
	
	@OneToMany( mappedBy = "ssow" , fetch=FetchType.EAGER)
	@JsonIgnore
	private Set<SsowCheckListXref> ssowCheckList;
	
	
	@Transient
	private String createdDateString ;
	
	@Transient
	private String modifiedDateString ;
	
	
	

	


	public String getFileLocation() {
		return fileLocation;
	}


	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}


	public Integer getActive() {
		return active;
	}


	public void setActive(Integer active) {
		this.active = active;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getProcurementAgent() {
		return procurementAgent;
	}


	public void setProcurementAgent(String procurementAgent) {
		this.procurementAgent = procurementAgent;
	}


	public String getProgramMgr() {
		return programMgr;
	}


	public void setProgramMgr(String programMgr) {
		this.programMgr = programMgr;
	}


	public String getSupplierMgmtMgr() {
		return supplierMgmtMgr;
	}


	public void setSupplierMgmtMgr(String supplierMgmtMgr) {
		this.supplierMgmtMgr = supplierMgmtMgr;
	}


	public String getIptSupplierMgr() {
		return iptSupplierMgr;
	}


	public void setIptSupplierMgr(String iptSupplierMgr) {
		this.iptSupplierMgr = iptSupplierMgr;
	}


	public String getIptTeamLead() {
		return iptTeamLead;
	}


	public void setIptTeamLead(String iptTeamLead) {
		this.iptTeamLead = iptTeamLead;
	}


	public String getLeadEngFocal() {
		return leadEngFocal;
	}


	public void setLeadEngFocal(String leadEngFocal) {
		this.leadEngFocal = leadEngFocal;
	}


	


	public String getSre() {
		return sre;
	}


	public void setSre(String sre) {
		this.sre = sre;
	}




	public String getSupplier() {
		return supplier;
	}


	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@JsonIgnore
	public Set<SsowCheckListXref> getSsowCheckList() {
		return ssowCheckList;
	}


	@JsonIgnore
	public void setSsowCheckList(Set<SsowCheckListXref> ssowCheckList) {
		this.ssowCheckList = ssowCheckList;
	}


	public String getSsowNumber() {
		return ssowNumber;
	}


	public void setSsowNumber(String ssowNumber) {
		this.ssowNumber = ssowNumber;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getRevNbr() {
		return revNbr;
	}


	public void setRevNbr(Integer revNbr) {
		this.revNbr = revNbr;
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
	    	//queue.setSubmitDate( formatter.format(rec.getCreatedDate()));
	    	return formatter.format(getModifiedDate());
		}
		return "";
	}


	public void setModifiedDateString(String modifiedDateString) {
		this.modifiedDateString = modifiedDateString;
	}


	public String getProcSpecNumber() {
		return procSpecNumber;
	}


	public void setProcSpecNumber(String procSpecNumber) {
		this.procSpecNumber = procSpecNumber;
	}
	
	
	
	public Ssow copySsow () {
		
		Ssow ssow = new Ssow ();
		
	//	ssow.setActive(new Integer (1));
		//ssow.setSsowNumber(ssowNumber);
		ssow.setDescription(description);
		ssow.setIptSupplierMgr(iptSupplierMgr);
		ssow.setIptTeamLead(iptTeamLead);
		ssow.setLeadEngFocal(leadEngFocal);
		ssow.setProcSpecNumber(procSpecNumber);
		ssow.setProcurementAgent(procurementAgent);
		//ssow.setProgram(program);
		ssow.setProgramMgr(programMgr);
		ssow.setRevNbr(revNbr);
		ssow.setSre(sre);
		ssow.setSupplier(supplier);
		ssow.setSupplierMgmtMgr(supplierMgmtMgr);
		//ssow.setSsowCheckList(getSsowCheckList());
		//ssow.setDocumentType(documentType);
		
		
		
		return ssow;
	}


	
	
	
	
	
	
}

