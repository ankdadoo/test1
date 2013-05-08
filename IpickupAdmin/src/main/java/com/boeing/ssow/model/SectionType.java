package com.boeing.ssow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "section_type")
public class SectionType {

	public final static String HEADER_SECTION = "H";
	public final static String INSTRUCTION_SECTION = "I";
	public final static String PARAGRAPH_SECTION = "P";
	public final static String SUB_SECTION = "S";
	public final static String TABLE_SECTION = "T";
	public final static String TABLE_HEADER_SECTION = "TH";
	public final static String TABLE_ROW_SECTION = "TR";
	
	
//	public final static String HEAD_SECTION = "Head Section";
//	public final static String INSTRUCTION = "Instruction";
//	public final static String PARAGRAPH = "Paragraph";
//	public final static String SUBSECTION = "Subsection";
//	public final static String TABLE_SECTION = "Table";

//	public final static String HEADER_SECTION = "1";
//	public final static String INSTRUCTION_SECTION = "2";
//	public final static String PARAGRAPH_SECTION = "3";
//	public final static String SUB_SECTION = "4";
//	public final static String TABLE_SECTION = "5";
//	
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "description")
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
