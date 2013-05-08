package com.boeing.ssow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "checklist_group")
public class CheckListGroup {

	@Id
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "groupName")
	private String groupName;

	@Column(name = "description")
	private String description;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

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
