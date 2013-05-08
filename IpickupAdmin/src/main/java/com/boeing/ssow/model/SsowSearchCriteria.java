package com.boeing.ssow.model;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestParam;

public class SsowSearchCriteria {

	
	 private String ssowNumber;
	 private Date ssowDate ; 
	// private String program ;
	 private String supplier ; 
	 private String status;
	 private String sre;
	 private String procNumber;
	 private String leadFocal;
	 private String iptTeamLead;
	 private String iptSupplierMgr ;
	 private String supplierMgmtMgr;
	 private String programManager;
	 private String procurementAgent;
	// private String documentType;
	 
	public String getSsowNumber() {
		return ssowNumber;
	}
	public void setSsowNumber(String ssowNumber) {
		this.ssowNumber = ssowNumber;
	}
	
	
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSre() {
		return sre;
	}
	public void setSre(String sre) {
		this.sre = sre;
	}
	public String getProcNumber() {
		return procNumber;
	}
	public void setProcNumber(String procNumber) {
		this.procNumber = procNumber;
	}
	public String getLeadFocal() {
		return leadFocal;
	}
	public void setLeadFocal(String leadFocal) {
		this.leadFocal = leadFocal;
	}
	public String getIptTeamLead() {
		return iptTeamLead;
	}
	public void setIptTeamLead(String iptTeamLead) {
		this.iptTeamLead = iptTeamLead;
	}
	public String getIptSupplierMgr() {
		return iptSupplierMgr;
	}
	public void setIptSupplierMgr(String iptSupplierMgr) {
		this.iptSupplierMgr = iptSupplierMgr;
	}
	public String getSupplierMgmtMgr() {
		return supplierMgmtMgr;
	}
	public void setSupplierMgmtMgr(String supplierMgmtMgr) {
		this.supplierMgmtMgr = supplierMgmtMgr;
	}
	public String getProgramManager() {
		return programManager;
	}
	public void setProgramManager(String programManager) {
		this.programManager = programManager;
	}
	public String getProcurementAgent() {
		return procurementAgent;
	}
	public void setProcurementAgent(String procurementAgent) {
		this.procurementAgent = procurementAgent;
	}
	public Date getSsowDate() {
		return ssowDate;
	}
	public void setSsowDate(Date ssowDate) {
		this.ssowDate = ssowDate;
	}
	
	 

	
	
	
}
