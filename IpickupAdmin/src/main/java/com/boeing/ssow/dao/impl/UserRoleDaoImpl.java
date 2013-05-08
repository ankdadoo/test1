package com.boeing.ssow.dao.impl;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boeing.ssow.dao.UserRoleDao;
import com.boeing.ssow.model.User;
import com.boeing.ssow.model.UserRole;



@Repository
@Transactional
public class UserRoleDaoImpl implements UserRoleDao {
  
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public List<UserRole> getUserRoleList () {
		
		Query query = sessionFactory.getCurrentSession().createQuery("From UserRole");
		
		return query.list();
		
	}
	
	public UserRole getUserRole (String id) {

		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from UserRole ur where ur.id = :id ")
				.setString("id", id);

		List list = query.list();

		if (list == null || list.size() == 0 || list.size() > 1) {
			return null;

		} else {
			return (UserRole) list.get(0);
		}
		
	}
	
}
