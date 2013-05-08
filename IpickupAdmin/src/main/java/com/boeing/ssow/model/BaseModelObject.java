package com.boeing.ssow.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.stereotype.Repository;

/**
 * Base class for model objects to manage id as a UUID, version, equals and
 * hashcode.
 * <p/>
 * Objects that extend this class expect a backing database table with the
 * following:
 * <ul>
 * <li>"id" database column varchar2(40) not null</li>
 * <li>"version_num" database column number not null</li>
 * <li>"created_date" database column date</li>
 * <li>"created_bemsid" database column varchar2(10)</li>
 * <li>"last_modified_date" database column date</li>
 * <li>"last_modified_bemsid" database column varchar2(10)</li>
 * </ul>
 * <p/>
 * Subclasses may override the default version and id annotations. Note that
 * annotation overrides do not inherit other attributes, such as nullability, so
 * they need to be explicitly restated in the event of an override.
 * <p/>
 * <code>
 * 
 * @Entity
 * @Table(name = "EXAMPLE")
 * @AttributeOverride(name = "id", column = @Column(name = "EXAMPLE_ID",
 *                         nullable = false))
 * @AttributeOverride(name = "version", column = @Column(name =
 *                         "EXAMPLE_VERSION", nullable = false)) public class
 *                         Example extends BaseModelObject { .... .... } </code>
 */
@MappedSuperclass
public abstract class BaseModelObject {

	static final long serialVersionUID = -6849294367773166297L;

	@Id
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "CreatedDate")
	private Date createdDate;

	// @Column(name = "createdBy")
	// private User createdBy;
	//
	@Column(name = "createdBy")
	private String createdBy;

	@Column(name = "ModifiedDate")
	private Date modifiedDate;

	// @Column(name = "modifiedBy")
	// private User modifiedBy;
	//
	
	@Column(name = "modifiedBy")
	private String modifiedBy;
	

	// @Column(name = "audit")
	// private Audit audit;
	//
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AuditID")
	private Audit AuditID;

	public BaseModelObject() {
		this.id = java.util.UUID.randomUUID().toString();
	}

	public BaseModelObject(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Audit getAuditID() {
		return AuditID;
	}

	public void setAuditID(Audit auditID) {
		AuditID = auditID;
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : super.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof BaseModelObject)) {
			return false;
		}
		return getId().equals(((BaseModelObject) o).getId());
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	
	
	

}
