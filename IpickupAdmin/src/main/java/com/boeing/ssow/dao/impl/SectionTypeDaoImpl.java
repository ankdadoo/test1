package com.boeing.ssow.dao.impl;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boeing.ssow.dao.SectionTypeDao;
import com.boeing.ssow.model.SectionType;



@Repository
@Transactional
public class SectionTypeDaoImpl implements SectionTypeDao {
  
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public List<SectionType> getSectionTypeList () {
		
		Query query = sessionFactory.getCurrentSession().createQuery("From SectionType");
		
		return query.list();
		
	}
	
	public SectionType getSectionType (String id) {

		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from SectionType st where st.id = :id ")
				.setString("id", id);

		List list = query.list();

		if (list == null || list.size() == 0 || list.size() > 1) {
			return null;

		} else {
			return (SectionType) list.get(0);
		}
		
	}
	
}
