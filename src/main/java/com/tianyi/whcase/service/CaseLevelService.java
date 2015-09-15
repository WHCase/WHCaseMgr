package com.tianyi.whcase.service;

import org.springframework.stereotype.Service;

@Service
public interface CaseLevelService {

	String insertCaseLevel(String caseId, int levelId);

}
