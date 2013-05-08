package com.boeing.ssow.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boeing.ssow.dao.CheckListDao;
import com.boeing.ssow.model.CheckList;
import com.boeing.ssow.model.Section;

@Repository
@Transactional
public class CheckListDaoImpl implements CheckListDao {

	@Autowired
	private SessionFactory sessionFactory;

	public List<CheckList> getAllCheckList () {
		
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery("From CheckList cl where cl.active = '1' order by cl.itemNumber ");

		List<CheckList> list = (List<CheckList>) query.list();

 		return query.list();
// 		return list;

	}

	public Object get(Class className, String pk) {

		return sessionFactory.getCurrentSession().get(className, pk);
	}

	public void save ( Object object ) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}

	// TODO: needs to be correctly set up for next ItemNumber in CheckList (RJM)
	public String getNextItemNumber() {

		Criteria criteria = 
				sessionFactory.getCurrentSession()
				.createCriteria(CheckList.class)
				.setProjection(Projections.max("itemNumber"));
		String maxItemNumber = (String) criteria.uniqueResult();
		String itemNumberSuffix = maxItemNumber.substring(2);
		int maxItemNoAsInt = Integer.parseInt(itemNumberSuffix) + 1;
		String maxItemNumberValue = "CL" + maxItemNoAsInt + "";
		return maxItemNumberValue;
		
	}


}
