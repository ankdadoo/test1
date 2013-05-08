package com.boeing.ssow.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boeing.ssow.dao.RuleDao;
import com.boeing.ssow.model.Rules;

@Repository
@Transactional
public class RuleDaoImpl implements RuleDao {

	@Autowired
	private SessionFactory sessionFactory;

	public List<Rules> getAllRules() {
		Query query = sessionFactory
				.getCurrentSession()
//				.createQuery("From Rules r where r.active = '1' ");
				.createQuery("From Rules r where r.active = '1' order by r.name");

		List<Rules> list = (List<Rules>) query.list();
 		return list;

	}

	public Object get(Class className, String pk) {

		return sessionFactory.getCurrentSession().get(className, pk);
	}

	public void save ( Object object ) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}
	
}
