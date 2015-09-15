package com.tianyi.whcase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.dao.CaseMapper;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.CaseUnit;
import com.tianyi.whcase.service.CaseService;
import com.tianyi.whcase.viewmodel.CaseVM;
 
@Service
public class CaseServiceImpl implements CaseService {
	
	@Autowired CaseMapper caseMapper;

	/**
	 * 获取案件列表
	 * @return 
	 */
	public ListResult<CaseVM> getCasePushListByReceiveStatus(Integer receiveStatus) {
		List<CaseVM> caseList =caseMapper.selectByReceiveStatus(receiveStatus); 
		return new ListResult<CaseVM>(caseList);
	}

	public CaseVM getCaseMainInfo(String caseId) {
		return caseMapper.selectVMByPrimaryKey(caseId);
	}

	public String saveCaseInfo(CaseVM caseInfo) {
		Case c = caseInfo.getCase();
		int temp =caseMapper.updateByPrimaryKey(c);
		if(temp==0){
			return "未进行修改";
		}
		return "";
	}

	public List<CaseCategory> getCatogory() {
		return caseMapper.selectCaseCategory();
	}

	public List<CaseUnit> getCaseUnit() {
		return caseMapper.selectCaseUnit();
	}

}
