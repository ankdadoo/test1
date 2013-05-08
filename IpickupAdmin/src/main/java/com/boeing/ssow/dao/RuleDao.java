package com.boeing.ssow.dao;

import java.util.List;

import com.boeing.ssow.model.Rules;



public interface RuleDao {

	public List<Rules> getAllRules ();

	public Object get(Class className, String pk);

	public void save ( Object object );
	
}
