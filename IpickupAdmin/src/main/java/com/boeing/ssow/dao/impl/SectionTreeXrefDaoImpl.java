package com.boeing.ssow.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boeing.ssow.dao.SectionTreeXrefDao;

@Repository
@Transactional
public class SectionTreeXrefDaoImpl implements SectionTreeXrefDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Object get(Class className, String pk) {

		return sessionFactory.getCurrentSession().get(className, pk);
	}

	public void save ( Object object ) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}
	
}
