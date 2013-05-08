package com.boeing.ssow.service;

import java.util.List;

import com.boeing.ssow.model.QuestionType;


public interface QuestionTypeService {

	public List<QuestionType> getQuestionTypeList ();	
	
	public Object get(Class className, String pk);
	
}
