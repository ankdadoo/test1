package com.boeing.ssow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boeing.ssow.dao.QuestionTypeDao;
import com.boeing.ssow.model.QuestionType;
import com.boeing.ssow.service.QuestionTypeService;

@Service
public class QuestionTypeServiceImpl implements QuestionTypeService {
	
	@Autowired
	private  QuestionTypeDao questiontypeDao;

	public List<QuestionType> getQuestionTypeList (){
	
		return questiontypeDao.getQuestionTypeList();
	}

	public Object get(Class className, String pk) {
		
		return questiontypeDao.get(className, pk);
	}
	
}
