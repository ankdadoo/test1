package com.boeing.ssow.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.boeing.ssow.dao.UserDao;
import com.boeing.ssow.model.Ssow;
import com.boeing.ssow.model.User;
import com.boeing.ssow.model.UserRole;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	public List<User> getAllUserList() {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"From User");

		return query.list();

	}
	
	public List<User> getUserList ( String username ,  String firstname ,  String lastname) {
		
		Criteria hbCriteria  = sessionFactory.getCurrentSession().createCriteria(User.class);
		
		if ( StringUtils.hasLength(username)) {
			hbCriteria.add( Restrictions.like("username", "%" + username + "%"));
		}

		if ( StringUtils.hasLength(firstname)) {
			hbCriteria.add( Restrictions.like("firstName", "%" + firstname + "%"));
		}
		
		if ( StringUtils.hasLength(lastname)) {
			hbCriteria.add( Restrictions.like("lastName", "%" + lastname + "%"));
		}
		
		hbCriteria.add(Restrictions.eq("active", 1));
		
		hbCriteria.addOrder(Order.asc("firstName"));
		hbCriteria.addOrder(Order.asc("lastName"));
		
		List list = hbCriteria.list();
		return list;
		
//		return hbCriteria.add(Restrictions.like("username", "%" + username + "%"))
//				.add(Restrictions.like("firstName", "%" + firstname + "%"))
//				.add(Restrictions.like("lastName", "%" + lastname + "%"))
//				.add(Restrictions.eq("active", 1))
//				.list();

	}


	public User validateUser(String username, String password) {

		// Query query =
		// sessionFactory.getCurrentSession().createQuery("from User u where u.username = :username and u.password = :password and u.active = '1' ").setString("username"
		// , username).setString("password" , password);

		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from User u where u.username = :username and u.active = '1' ")
				.setString("username", username);

		List list = query.list();

		if (list == null || list.size() == 0 || list.size() > 1) {
			return null;

		} else {
			return (User) list.get(0);
		}
	}

	public String addUser(User user) {

		Session session = sessionFactory.getCurrentSession();
		session.save(user);
		return "Success?";
	}
	
	public String updateUser(User user) {

		Session session = sessionFactory.getCurrentSession();
		session.update(user);
		return "Success?";
	}
	
	public boolean checkUserExistByUserName(String username) {

		Query query = sessionFactory.getCurrentSession()
				.createQuery("from User u where u.username = :username and u.active = '1' ")
				.setString("username", username);

		List list = query.list();

		if (list == null || list.size() == 0) {
			return false;

		} else {
			return true;
		}

	}
	
	public User getUser(String id) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from User u where u.id = :id ")
				.setString("id", id);

		List list = query.list();

		if (list == null || list.size() == 0 || list.size() > 1) {
			return null;

		} else {
			return (User) list.get(0);
		}
		
		
	}
	
	
	public User getUserByUsername(String username) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from User u where u.username = :username ")
				.setString("username", username);

		List list = query.list();

		if (list == null || list.size() == 0 || list.size() > 1) {
			return null;

		} else {
			return (User) list.get(0);
		}
		
		
	}


}
