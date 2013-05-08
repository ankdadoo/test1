package com.boeing.ssow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "section_tree_xref")
public class SectionTreeXref  {
    @Id
    @Column(name = "ParentID", nullable = false)
    private String parentId;
	
//    @Id
    @Column(name = "ChildID", nullable = false)
    private String childId;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

    

}

