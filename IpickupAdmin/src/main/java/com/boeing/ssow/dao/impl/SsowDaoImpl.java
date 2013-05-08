package com.boeing.ssow.dao.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.boeing.ssow.dao.AcronymsDao;
import com.boeing.ssow.dao.SsowDao;
import com.boeing.ssow.model.Acronyms;
import com.boeing.ssow.model.Dictionary;
import com.boeing.ssow.model.ProgramFile;
import com.boeing.ssow.model.Rules;
import com.boeing.ssow.model.Sdrl;
import com.boeing.ssow.model.Section;
import com.boeing.ssow.model.Ssow;
import com.boeing.ssow.model.SsowSearchCriteria;
import com.boeing.ssow.model.User;



@Repository
@Transactional
public class SsowDaoImpl implements SsowDao {
  
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public List<Ssow> getSsowList (User user) {
		
		if ( user != null ) {
	//	logger.info("*** getting the list only for " + user.getUsername());
		Query query = sessionFactory.getCurrentSession().createQuery("From Ssow s where s.active = '1' and s.createdBy = :createdBy and s.status in ('In-Review' , 'In-Work') order by s.createdDate desc").setString("createdBy" ,  user.getFullName());
		
		return query.list();
		}else {
			return new ArrayList();
		}
		
	}
	
	public List<Ssow> getSsowList () {
		
		
		Query query = sessionFactory.getCurrentSession().createQuery("From Ssow s where s.active = '1' and s.status in ('In-Review' , 'In-Work') order by s.createdDate desc");
		
		return query.list();
		
		
	}
	
	
	
	public void delete ( Object object) {
		
		sessionFactory.getCurrentSession().delete(object);
	}
	
public List<Ssow> getSsowListBySsowNumer ( String ssowNumber) {
		
		
		Query query = sessionFactory.getCurrentSession().createQuery("From Ssow s where s.active = '1' and s.ssowNumber = :ssowNumber ").setString("ssowNumber" , ssowNumber);
		
		return query.list();
		
		
	}
	
	public String getNextRuleNumber() {

		Criteria criteria = 
				sessionFactory.getCurrentSession()
				.createCriteria(Rules.class)
				.setProjection(Projections.max("name"));
				//.add(Restrictions.eq("sectionType", "H"));  // TODO: static value
		
//		Integer maxHeaderNumberValue = (Integer) criteria.uniqueResult() + 1;
		String maxHeaderAsString = (String) criteria.uniqueResult(); //  + 1;
		//int maxHeaderAsInt = Integer.parseInt(maxHeaderAsString) + 1;
	///	String maxHeaderNumber = maxHeaderAsInt + ""; // maxHeaderNumberValue.toString(); //  + "";

		return maxHeaderAsString;
		
	}
	
	
	public void save ( Object object ) {
		
		
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}
	
	
	public Object get ( Class className , String pk ) {
		
		
	return	sessionFactory.getCurrentSession().get(className, pk);
	}
	
	
	
public List getRules () {
		
		
		Query query =  sessionFactory.getCurrentSession().createQuery("From Rules r where r.active = '1'  order by r.name asc");
		return query.list();
	}
	

public List getRulesCriteriaXrefById ( String criteriaId) {
	
	
	Query query =  sessionFactory.getCurrentSession().createQuery("From RulesCriteriaXref r where r.criteriaId = :criteriaId ").setString("criteriaId",  criteriaId);
	return query.list();
}


public List getCheckListProgramXrefById ( String checkListId) {
	
	
	Query query =  sessionFactory.getCurrentSession().createQuery("From CheckListProgramXref r where r.checklistid = :checklistid ").setString("checklistid",  checkListId);
	return query.list();
}
	public List getStatuses () {
		
		
		Query query =  sessionFactory.getCurrentSession().createQuery("From SsowStatus");
		return query.list();
	}
	
	
public List getCheckList () {
		
		
		Query query =  sessionFactory.getCurrentSession().createQuery("From CheckList where  active = '1' order by itemNumber asc");
		return query.list();
	}
	
public List getBasicQList () {
		
		
		Query query =  sessionFactory.getCurrentSession().createQuery("From CheckList where basicQ = '1' and active = '1' order by itemNumber asc ");
		return query.list();
	}
	
public List getRulesCriteriaXrefList ( String dependencyId) {
	
	Query query =  sessionFactory.getCurrentSession().createQuery("From RulesCriteriaXref where dependencyID = :dependencyId ").setString("dependencyId" , dependencyId);
	return query.list();
	
	
	
}


public List getSsowCheckListXrefList ( String checkListId) {
	
	Query query =  sessionFactory.getCurrentSession().createQuery("From SsowCheckListXref  where checkList.id  = :checkListId ").setString("checkListId" , checkListId);
	return query.list();
	
	
	
}
public List getSdrlList () {
	
	
	Query query =  sessionFactory.getCurrentSession().createQuery("From Sdrl where active = '1' ");
	return query.list();
}

public List getSdrlEditableList () {
	
	
	Query query =  sessionFactory.getCurrentSession().createQuery("From Sdrl where active = '1' and editable ='1'  order by name asc");
	return query.list();
}


public List getAcronymsList () {
	
	
	Query query =  sessionFactory.getCurrentSession().createQuery("From Acronyms where  active = '1'  order by name asc");
	return query.list();
}



public List getDictionaryList () {
	
	
	Query query =  sessionFactory.getCurrentSession().createQuery("From Dictionary where  active = '1' order by name asc ");
	return query.list();
}


public List getAdvancedQList () {
	
	
	Query query =  sessionFactory.getCurrentSession().createQuery("From CheckList where basicQ = '0' and active = '1' order by itemNumber asc");
	return query.list();
}
public List getDocumentTypes () {
		
		
		Query query =  sessionFactory.getCurrentSession().createQuery("From SsowDocumentTypes");
		return query.list();
	}


public List<Sdrl> getSdrlByRuleId( String ruleId) {

	
	Query query = sessionFactory
	.getCurrentSession()


	.createQuery("select s From Sdrl  as s join s.rulesSet as r  where r.id = :ruleId").setString("ruleId", ruleId);
	List<Sdrl> list = (List<Sdrl>) query.list();

		return list;

}



public List<Acronyms> getAcronymsByRuleId( String ruleId) {

	
	Query query = sessionFactory
	.getCurrentSession()


	.createQuery("select s From Acronyms  as s join s.rulesSet as r  where r.id = :ruleId").setString("ruleId", ruleId);
	List<Acronyms> list = (List<Acronyms>) query.list();

		return list;

}

public List<Section> getSectionByRuleId( String ruleId) {

	
	Query query = sessionFactory
	.getCurrentSession()


	.createQuery("select s From Section  as s join s.rulesSet as r  where r.id = :ruleId").setString("ruleId", ruleId);
	List<Section> list = (List<Section>) query.list();

		return list;

}

public List<ProgramFile> getProgramFileList( String programName) {

	
	Query query = sessionFactory
	.getCurrentSession()


	.createQuery(" From ProgramFile  where programName = :programName").setString("programName", programName);
	List<ProgramFile> list = (List<ProgramFile>) query.list();

		return list;

}
public List<Dictionary> getDictionaryByRuleId( String ruleId) {

	
	Query query = sessionFactory
	.getCurrentSession()


	.createQuery("select s From Dictionary  as s join s.rulesSet as r  where r.id = :ruleId").setString("ruleId", ruleId);
	List<Dictionary> list = (List<Dictionary>) query.list();

		return list;

}
	public List<Ssow> getSearchResults ( SsowSearchCriteria criteria) {
		
		
		//logger.info("*** Getting to the search results ");
		Criteria hbCriteria  = sessionFactory.getCurrentSession().createCriteria(Ssow.class);
		
	//	logger.info("*** ssw number " + criteria.getSsowNumber());
		//logger.info("*** ssw number " + criteria.getSsowDate());
		//logger.info("*** ssw number " + new Date());
		
		if ( StringUtils.hasLength(criteria.getSsowNumber())) {
			hbCriteria.add( Restrictions.like("ssowNumber", "%" + criteria.getSsowNumber() + "%"));
		}
		
		if ( StringUtils.hasLength(criteria.getProcurementAgent())) {
		 
				hbCriteria.add( Restrictions.like("procurementAgent", "%" + criteria.getProcurementAgent() + "%"));
		}
		
		//if ( StringUtils.hasLength(criteria.getProgram())) { 
		//		hbCriteria.add( Restrictions.like("program", "%" + criteria.getProgram() + "%"));
		//}
		
		if ( StringUtils.hasLength(criteria.getSupplier())) {
				hbCriteria.add( Restrictions.like("supplier", "%" + criteria.getSupplier() + "%"));
		}
		
		if ( StringUtils.hasLength(criteria.getSre())) {
			hbCriteria.add( Restrictions.like("sre", "%" + criteria.getSre() + "%"));
		}
		
		if ( StringUtils.hasLength(criteria.getProcNumber())) {
				hbCriteria.add( Restrictions.like("procSpecNumber", "%" + criteria.getProcNumber() + "%"));
		}
		
		if ( StringUtils.hasLength(criteria.getLeadFocal())) {
				hbCriteria.add( Restrictions.like("leadEngFocal", "%" + criteria.getLeadFocal() + "%"));
		}
		
		if ( StringUtils.hasLength(criteria.getIptTeamLead())) {
			
			hbCriteria.add( Restrictions.like("iptTeamLead", "%" + criteria.getIptTeamLead() + "%"));
		}
		
		if ( StringUtils.hasLength(criteria.getIptSupplierMgr())) {
		
				hbCriteria.add( Restrictions.like("iptSupplierMgr", "%" + criteria.getIptSupplierMgr() + "%"));
		}
		
		if ( StringUtils.hasLength(criteria.getSupplierMgmtMgr())) {
				hbCriteria.add( Restrictions.like("supplierMgmtMgr", "%" + criteria.getSupplierMgmtMgr() + "%"));
		}
		
		
		if ( StringUtils.hasLength(criteria.getStatus())) {
			hbCriteria.add( Restrictions.like("status", "%" + criteria.getStatus() + "%"));
	     }
		
		
		//if ( StringUtils.hasLength(criteria.getDocumentType())) {
		//	hbCriteria.add( Restrictions.like("documentType", "%" + criteria.getDocumentType() + "%"));
	    // }
		
		if ( StringUtils.hasLength(criteria.getProgramManager())) {
				hbCriteria.add( Restrictions.like("programMgr", "%" + criteria.getProgramManager() + "%"));
		}
				hbCriteria.add( Restrictions.eq("active", new Integer (1)));
				
		 
		 if ( criteria.getSsowDate() != null ) {
			 
			 hbCriteria.add( Restrictions.between("createdDate", criteria.getSsowDate() , new Date()));
		 }
		 
		 hbCriteria.addOrder( Order.desc("createdDate"));
				//.add(Restrictions)
				List list = hbCriteria.list();
		
				return list ;
		//return this.getSsowList();
		//return new ArrayList();
	}
	
}
