package com.boeing.ssow.service;



public interface AuditService {

	public Object get(Class className, String pk);
	
	public void save ( Object object );
	
}
