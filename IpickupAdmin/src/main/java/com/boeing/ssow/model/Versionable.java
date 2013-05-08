package com.boeing.ssow.model;

/**
 * Interface used to support optimistic locking in Hibernate.
 * <a href="http://www.hibernate.org/hib_docs/v3/reference/en/html/transactions.html#transactions-optimistic"
 * Optimistic Concurrency Control</a>
 */
public interface Versionable {

    public Long getVersion();

    public void setVersion(Long version);

}
