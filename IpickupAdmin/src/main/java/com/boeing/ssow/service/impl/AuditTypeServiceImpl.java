package com.boeing.ssow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boeing.ssow.dao.AuditTypeDao;
import com.boeing.ssow.service.AuditTypeService;

@Service
public class AuditTypeServiceImpl implements AuditTypeService {

	@Autowired
	private AuditTypeDao auditTypeDao;

	public Object get(Class className, String pk) {

		return auditTypeDao.get(className, pk);
	}

}
