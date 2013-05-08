package com.boeing.ssow.dao;

import java.util.List;

import com.boeing.ssow.model.CheckListGroup;
import com.boeing.ssow.model.QuestionType;



public interface QuestionTypeDao {

	public List<QuestionType> getQuestionTypeList ();
	
	public Object get(Class className, String pk);
	
}
