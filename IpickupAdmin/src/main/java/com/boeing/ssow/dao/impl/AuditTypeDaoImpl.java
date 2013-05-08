package com.boeing.ssow.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boeing.ssow.dao.AuditTypeDao;

@Repository
@Transactional
public class AuditTypeDaoImpl implements AuditTypeDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Object get(Class className, String pk) {

		return sessionFactory.getCurrentSession().get(className, pk);
	}

}
