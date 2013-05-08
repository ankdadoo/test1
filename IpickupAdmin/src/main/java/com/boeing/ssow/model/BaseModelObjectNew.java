package com.boeing.ssow.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
/**
 * Base class for model objects to manage id as a UUID, version, equals and hashcode.
 * <p/>
 * Objects that extend this class expect a backing database table with the following:
 * <ul>
 * <li>"id" database column varchar2(40) not null</li>
 * <li>"version_num" database column number not null</li>
 * <li>"created_date" database column date</li>
 * <li>"created_bemsid" database column varchar2(10)</li>
 * <li>"last_modified_date" database column date</li>
 * <li>"last_modified_bemsid" database column varchar2(10)</li>
 * </ul>
 * <p/>
 * Subclasses may override the default version and id annotations. Note that annotation overrides
 * do not inherit other attributes, such as nullability, so they need to be explicitly restated
 * in the event of an override.
 * <p/>
 * <code>
 *
 * @Entity
 * @Table(name = "EXAMPLE")
 * @AttributeOverride(name = "id", column = @Column(name = "EXAMPLE_ID", nullable = false))
 * @AttributeOverride(name = "version", column = @Column(name = "EXAMPLE_VERSION", nullable = false))
 * public class Example extends BaseModelObject {
 * ....
 * ....
 * } </code>
 */
@MappedSuperclass
public abstract class BaseModelObjectNew implements Identifiable, Versionable, Serializable, Auditable {
    static final long serialVersionUID = -6849294367773166297L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Version
    @Column(name = "version_num", nullable = false)
    private Long version;

    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "ModifiedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Column(name = "ModifiedBy")
    private String lastModifiedBy;

    public BaseModelObjectNew() {
      this.id = java.util.UUID.randomUUID().toString();
    }

    public BaseModelObjectNew(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    //This setter is needed by the HibernateObjectDeserializer of Jackson's ObjectDeserializer
    @SuppressWarnings("unused")
	private void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
	public String getCreatedDateFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		if(createdDate != null){
			return sdf.format(createdDate);
		}
		
		return null;
	}    

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
    
	public String getLastModifiedDateFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		if(lastModifiedDate != null){
			return sdf.format(lastModifiedDate);
		}
		
		return null;
	}    

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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
        if (o == null || !(o instanceof BaseModelObjectNew)) {
            return false;
        }
        return getId().equals(((BaseModelObjectNew) o).getId());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + getId();
    }
}