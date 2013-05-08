package com.boeing.ssow.dao.impl;



import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boeing.ssow.dao.AcronymsDao;



@Repository
@Transactional
public class AcronymsDaoImpl implements AcronymsDao {
  
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	
}
