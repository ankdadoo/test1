package com.boeing.ssow.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boeing.ssow.dao.CheckListGroupDao;
import com.boeing.ssow.model.CheckListGroup;

@Repository
@Transactional
public class CheckListGroupDaoImpl implements CheckListGroupDao {

	@Autowired 
	private SessionFactory sessionFactory;

	public List<CheckListGroup> getAllCheckListGroups () {
		
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery("From CheckListGroup clg");

		List<CheckListGroup> list = (List<CheckListGroup>) query.list();

 		return query.list();
// 		return list;

	}

	public Object get(Class className, String pk) {

		return sessionFactory.getCurrentSession().get(className, pk);
	}

	public void save ( Object object ) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}


}
