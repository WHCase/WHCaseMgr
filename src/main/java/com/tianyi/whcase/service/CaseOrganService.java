package com.tianyi.whcase.service;

import org.springframework.stereotype.Service;

@Service
public interface CaseOrganService {

	String insertCaseOrgan(String caseId, int indexOf);
	
}
