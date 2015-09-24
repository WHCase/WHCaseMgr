package com.tianyi.whcase.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface CaseOrganService {

	String insertCaseOrgan(String caseId, int indexOf);

	List<String> selectCaseLiseByOrganId(int organId);
	
}
