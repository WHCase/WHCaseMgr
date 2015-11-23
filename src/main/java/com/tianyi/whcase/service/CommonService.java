package com.tianyi.whcase.service;

import org.springframework.stereotype.Service;

import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.detectUnit;

@Service
public interface CommonService {
	int InsertCaseCategory(CaseCategory category);
	int InsertUnit(detectUnit unit);
}
