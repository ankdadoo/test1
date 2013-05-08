package com.boeing.ssow.service;

import java.util.List;

import com.boeing.ssow.model.Rules;


public interface SectionTreeXrefService {

	public Object get(Class className, String pk);

	public void save ( Object object );
	
}
