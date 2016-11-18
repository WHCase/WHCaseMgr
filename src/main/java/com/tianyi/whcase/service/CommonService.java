package com.tianyi.whcase.service;

import org.springframework.stereotype.Service;

import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.detectUnit;
import com.tianyi.whcase.viewmodel.CaseVM;

@Service
public interface CommonService {
	int InsertCaseCategory(CaseCategory category);
	int InsertUnit(detectUnit unit);
	int insertOrUpdateCaseVM(CaseVM ccase);
}
