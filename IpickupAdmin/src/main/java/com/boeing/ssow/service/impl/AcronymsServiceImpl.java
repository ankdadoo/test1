package com.boeing.ssow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boeing.ssow.dao.AcronymsDao;
import com.boeing.ssow.service.AcronymsService;

@Service
public class AcronymsServiceImpl implements  AcronymsService {
	
	@Autowired
	private  AcronymsDao  acronymsDao;


}
