package com.boeing.ssow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boeing.ssow.dao.AuditDao;
import com.boeing.ssow.service.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

	@Autowired
	private AuditDao auditDao;

	public Object get(Class className, String pk) {

		return auditDao.get(className, pk);
	}

	public void save(Object object) {

		auditDao.save(object);
	}

}
