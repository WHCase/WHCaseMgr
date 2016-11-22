package com.tianyi.whcase.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.CaseUnit;
import com.tianyi.whcase.viewmodel.CaseTJVM;
import com.tianyi.whcase.viewmodel.CaseVM;

@Service
public interface CaseService {

	/**
	 * 通过case的某个字段来获取刚推送过来的列表
	 * @param paramValue 参数值
	 * @param paramName 参数字段名
	 */
	ListResult<CaseVM> getCasePushListByReceiveStatus(Map<String, Object> map);

	CaseVM getCaseMainInfo(String caseId);

	String saveCaseInfo(CaseVM caseInfo);

	List<CaseCategory> getCatogory();

	List<CaseUnit> getCaseUnit();

	String insert(Case c);

	ListResult<CaseVM> getCasePushListByPageAndRow(Map<String, Object> map);

	int deleteByCaseId(String caseId);

	int updateCase(Case c);

	String updateCaseReceiveStatusAndLevel(int receiveStatus,String level, String caseId);
	String updateCaseReceiveStatus(int receiveStatus, String caseId);

	List<CaseVM> getDistributeCase(int receiveStatus, List<String> caseIdList);

	ListResult<CaseVM> getCaseFeedBackListByReceiveStatus(Map<String, Object> map);

	CaseTJVM getCaseTJInfo();

	CaseTJVM getCaseTJInfo(Map<String, Object> map);

	ListResult<CaseVM> getDistributeCaseByOrganId(Map<String, Object> map);

	ListResult<CaseVM> getFeedCaseByOrganId(Map<String, Object> map);

	int updateCCase(Case c);

	int deleteLocalCase(String caseId);

}
