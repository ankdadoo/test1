package com.boeing.ssow.dao;

public interface AuditDao {

	public Object get(Class className, String pk);
	
	public void save ( Object object );
	
}
