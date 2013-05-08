package com.boeing.ssow.model;

import java.util.Date;

/**
 * Class to keep history of who created an object and who last updated an object.
 * It does not keep complete history, but only the last change.
 */
public interface Auditable {

    public static final String NULL_USER_STRING = "9999999";
    public static final String CREATED_BY_PROPERTY = "createdBy";
    public static final String CREATED_DATE_PROPERTY = "createdDate";
    public static final String LAST_MODIFIED_BY_PROPERTY = "lastModifiedBy";
    public static final String LAST_MODIFIED_DATE_PROPERTY = "lastModifiedDate";

    public String getCreatedBy();

    public Date getCreatedDate();

    public String getLastModifiedBy();

    public Date getLastModifiedDate();

    public void setCreatedBy(String createdBy);

    public void setCreatedDate(Date date);

    public void setLastModifiedBy(String lastUpdatedBy);

    public void setLastModifiedDate(Date date);

}