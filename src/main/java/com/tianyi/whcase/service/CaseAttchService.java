package com.tianyi.whcase.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.model.CaseAttach;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.viewmodel.CaseAttachVM;

@Service
public interface CaseAttchService {

	ListResult<CaseAttachItem> getCaseRelativeByCaseId(String id,int resourceType);
	ListResult<CaseAttachItem> getCaseRelativeByCaseId(String id,String resourceType);

	int AddAttachVM(CaseAttachVM caseAttachVM,HttpServletRequest request,HttpServletResponse response);
	String deleteCaseAttach(String caseId,String caseAttachItemId);
	// 修改人xie
	CaseAttach getCaseAttachBycaseID(String caseID,Integer messageType);
	int deleteLocalAttach(String caseId, String caseAttachItemId);

}
