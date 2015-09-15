package com.tianyi.whcase.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.dao.CaseOrganMapper;
import com.tianyi.whcase.model.CaseOrgan;
import com.tianyi.whcase.service.CaseOrganService;

@Service
public class CaseOrganServiceImpl implements CaseOrganService {
	@Autowired CaseOrganMapper caseOrganMapper;
	public String insertCaseOrgan(String caseId, int indexOf) {
		
		if(caseOrganMapper.selectByCaseIdAndOrganId(caseId,indexOf)!=null){
			return "本案件已经向相关的派出所推送过了";
		}
		CaseOrgan record = new CaseOrgan();
		record.setCaseId(caseId);
		record.setOrganizationId(indexOf);
		UUID u = java.util.UUID.randomUUID();
		record.setId(u.toString());
		int temp =caseOrganMapper.insert(record);
		if(temp ==-1){
			return "推送失败";
		}
		return "";
	}

}
