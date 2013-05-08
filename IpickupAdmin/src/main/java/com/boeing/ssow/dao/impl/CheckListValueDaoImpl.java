package com.boeing.ssow.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boeing.ssow.dao.CheckListValueDao;
import com.boeing.ssow.model.CheckListValue;

@Repository
@Transactional
public class CheckListValueDaoImpl implements CheckListValueDao {

	@Autowired
	private SessionFactory sessionFactory;

	public List<CheckListValue> getAllCheckListValues() {

		Query query = sessionFactory.getCurrentSession().createQuery("From CheckListValue");

		return query.list();

	}
	
}
