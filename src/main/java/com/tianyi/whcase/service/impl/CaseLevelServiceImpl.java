package com.tianyi.whcase.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.dao.CaseLevelMapper;
import com.tianyi.whcase.model.CaseLevel;
import com.tianyi.whcase.service.CaseLevelService;

@Service
public class CaseLevelServiceImpl implements CaseLevelService {

	@Autowired CaseLevelMapper caseLevelMapper;
	public String insertCaseLevel(String caseId, int levelId) {
		if(caseLevelMapper.selectByCaseId(caseId)!=null){
			return "本案件已经作为等级"+levelId+"推送过了";
		}
		CaseLevel caseLevel = new CaseLevel();
		caseLevel.setCaseId(caseId);
		caseLevel.setCaseLevel(levelId);
		UUID u = java.util.UUID.randomUUID();
		caseLevel.setId(u.toString());
		int temp = caseLevelMapper.insert(caseLevel);
		if(temp ==-1){
			return "推送失败";
		}
		return "";
		
	}

}
