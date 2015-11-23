package com.tianyi.whcase.dao;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.Organ;
import com.tianyi.whcase.model.detectUnit;

@MyBatisRepository
public interface OrganMapper {
	Organ selectByPrimaryKey(int id);
	int insert(Organ organ);
	int insertUnit(detectUnit unit);
	int insertCaseCategory(CaseCategory caseCategory);
	int selectUnitByUnitId(int detectedunitId);
	int selectCaseCategoryByCategoryId(String id);
}
