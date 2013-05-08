package com.boeing.ssow.dao.impl;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boeing.ssow.dao.QuestionTypeDao;
import com.boeing.ssow.model.QuestionType;



@Repository
@Transactional
public class QuestionTypeDaoImpl implements QuestionTypeDao {
  
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public List<QuestionType> getQuestionTypeList () {
		
		Query query = sessionFactory.getCurrentSession().createQuery("From QuestionType");
		
		return query.list();
		
	}

	public Object get(Class className, String pk) {

		return sessionFactory.getCurrentSession().get(className, pk);
	}

	
}
