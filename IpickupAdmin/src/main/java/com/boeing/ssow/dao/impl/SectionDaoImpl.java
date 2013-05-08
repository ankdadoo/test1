package com.boeing.ssow.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boeing.ssow.dao.SectionDao;
import com.boeing.ssow.model.Section;
import com.boeing.ssow.model.SectionTreeXref;
import com.boeing.ssow.model.SectionType;

@Repository
@Transactional
public class SectionDaoImpl implements SectionDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Object get(Class className, String pk) {

		return sessionFactory.getCurrentSession().get(className, pk);
	}

	public void save ( Object object ) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}
	
	public int getNextOrderNumber() {

		Criteria criteria = 
				sessionFactory.getCurrentSession()
				.createCriteria(Section.class)
				.setProjection(Projections.max("orderNumber"));
		Integer maxOrderNumberValue = (Integer) criteria.uniqueResult() + 1;
		return maxOrderNumberValue;
		
	}
	
	
	public String getNextHeaderNumber() {

		Criteria criteria = 
				sessionFactory.getCurrentSession()
				.createCriteria(Section.class)
				.setProjection(Projections.max("sectionNumber"))
				.add(Restrictions.eq("sectionType", "H"));  // TODO: static value
		
		String maxHeaderAsString = (String) criteria.uniqueResult(); //  + 1;
		int maxHeaderAsInt = Integer.parseInt(maxHeaderAsString) + 1;
		String maxHeaderNumber = maxHeaderAsInt + ""; // maxHeaderNumberValue.toString(); //  + "";

		return maxHeaderNumber;
		
	}
	
	public List<Section> getChildrenSections(String id) {
		
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery("from Section s where s.active = '1' and s.id = :id ")
				.setString("id", id);
		
		List<Section> list = (List<Section>) query.list();
		
		if (list == null || list.size() == 0) {
			return null;

		} else {
			Section section = (Section) list.get(0);
			List<Section> list2 = new ArrayList<Section>(section.getChildrenSet());
			return list2;
		}

	}

	
	public List<SectionTreeXref> getParentXrefs(String id) {

		Query query = sessionFactory
				.getCurrentSession()
				.createQuery("from SectionTreeXref stx where stx.childId = :childId ")
				.setString("childId", id);
		
		List<SectionTreeXref> list = (List<SectionTreeXref>) query.list();
		return list; 
		
	}
	
	public List getMainSections() {
	
		Query query = sessionFactory
		.getCurrentSession()
		.createQuery("From Section s where s.sectionType = 'H' and s.sectionNumber <> '0' " +   // NOTE: remove active check - allows Header Sections that were deleted to be reachable
				" group by s.sectionNumber order by s.sectionNumber");
		List list = query.list();
 		return list;

	}

	public List<Section> getSectionsByRuleId( String ruleId) {

			Query query = sessionFactory
			.getCurrentSession()
			.createQuery("From Section  as s join s.rulesSet as r  where r.id = :ruleId").setString("ruleId", ruleId);
			List<Section> list = (List<Section>) query.list();

	 		return list;

	}
	
	public List<Section> getMainSubSections(String sectionNumber) { // TODO: add sectionType parameter

		// TODO: will need to know why value is empty
		if (sectionNumber.equalsIgnoreCase("")) {	
			sectionNumber = "1";
		}
			
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery("from Section s where s.sectionType = 'H' and  s.sectionNumber = :sectionNumber order by s.orderNumber")
				.setString("sectionNumber", sectionNumber);

		
		List<Section> list = (List<Section>) query.list();
 		return list;
	}
	
}
