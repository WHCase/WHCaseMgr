package com.tianyi.whcase.service;

import org.springframework.stereotype.Service;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.model.CaseGroup;

@Service
public interface CaseGroupService {

	ListResult<CaseGroup> getCaseRelativeByCaseId(String id);
	
}
