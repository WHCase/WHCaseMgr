package com.tianyi.whcase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.dao.CaseGroupMapper;
import com.tianyi.whcase.model.CaseGroup;
import com.tianyi.whcase.service.CaseGroupService;

@Service
public class CaseGroupServiceImpl implements CaseGroupService {
	@Autowired CaseGroupMapper caseGroupMapper;

	public ListResult<CaseGroup> getCaseRelativeByCaseId(String id) {
		List<CaseGroup> caseList =caseGroupMapper.selectByCaseId(id); 
		return new ListResult<CaseGroup>(caseList);
	}

}
