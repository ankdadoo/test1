package com.boeing.ssow.model;

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


@Entity
@Table(name = "ssow_checklist_xref")
public class SsowCheckListXref implements Comparable  {
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
	

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "SsowId")
	private Ssow ssow;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "ChecklistQuestionID")
	private CheckList	checkList;
	

	@Column ( name="answer") 
	private String answer;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Ssow getSsow() {
		return ssow;
	}


	public void setSsow(Ssow ssow) {
		this.ssow = ssow;
	}


	public CheckList getCheckList() {
		return checkList;
	}


	public void setCheckList(CheckList checkList) {
		this.checkList = checkList;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	public SsowCheckListXref() {
		this.id = java.util.UUID.randomUUID().toString();
	}

	
	public SsowCheckListXref copyXref() {
		
		
		SsowCheckListXref xref = new SsowCheckListXref();
		xref.setCheckList(getCheckList());
		xref.setAnswer(getAnswer());
		return xref;
	}


	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 1;
	}
	
	
	


}

