package com.tianyi.whcase.dao;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.CaseCategory;

@MyBatisRepository
public interface CaseCategoryMapper {
	int insert(CaseCategory category);
}
