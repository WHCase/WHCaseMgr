package com.tianyi.whcase.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.dao.OrganMapper;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.detectUnit;
import com.tianyi.whcase.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService{
	@Autowired OrganMapper organMapper;
	public int InsertCaseCategory(CaseCategory category) {
		if(organMapper.selectCaseCategoryByCategoryId(category.getId())==0){
			return organMapper.insertCaseCategory(category);
		}else{
			return 0;
		}
	}

	public int InsertUnit(detectUnit unit) {
		if(organMapper.selectUnitByUnitId(unit.getDetectedunitId())==0){
			return organMapper.insertUnit(unit);
		}else{
			return 0;
		}
		
	}
	@Test
	public void test(){
		
	}
}
