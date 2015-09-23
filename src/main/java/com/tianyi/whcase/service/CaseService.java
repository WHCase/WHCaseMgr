package com.tianyi.whcase.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.CaseUnit;
import com.tianyi.whcase.viewmodel.CaseVM;

@Service
public interface CaseService {

	/**
	 * 通过case的某个字段来获取刚推送过来的列表
	 * @param paramValue 参数值
	 * @param paramName 参数字段名
	 */
	ListResult<CaseVM> getCasePushListByReceiveStatus(Integer receiveStatus);

	CaseVM getCaseMainInfo(String caseId);

	String saveCaseInfo(CaseVM caseInfo);

	List<CaseCategory> getCatogory();

	List<CaseUnit> getCaseUnit();

	String insert(Case c);

	ListResult<CaseVM> getCasePushListByPageAndRow(Map<String, Object> map);

}
