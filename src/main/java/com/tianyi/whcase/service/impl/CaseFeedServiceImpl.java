package com.tianyi.whcase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.dao.CaseAttachItemMapper;
import com.tianyi.whcase.dao.CaseAttachMapper;
import com.tianyi.whcase.dao.CaseFeedMapper;
import com.tianyi.whcase.dao.CaseMapper;
import com.tianyi.whcase.model.CaseAttach;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.model.CaseFeed;
import com.tianyi.whcase.service.CaseFeedService;
import com.tianyi.whcase.viewmodel.CaseFeedVM;
import com.tianyi.whcase.viewmodel.CaseVM;

@Service
public class CaseFeedServiceImpl implements CaseFeedService{
	@Autowired CaseAttachMapper caseAttachMapper;
	@Autowired CaseAttachItemMapper caseAttachItemMapper;
	@Autowired CaseFeedMapper caseFeedMapper;
	@Autowired CaseMapper caseMapper;
	
	public List<CaseAttachItem> getCaseBackAttchMents(String id,int resourceType) {
		
		/**
		 * 获取反馈案件的附件
		 */
		CaseAttach attach = caseAttachMapper.selectByCaseId(id,resourceType);
		if(attach==null){
			return null;
		}
		String caseAttachId = attach.getId();
		List<CaseAttachItem> attachItemList = caseAttachItemMapper.selectByCaseAttachId(caseAttachId);
		return attachItemList;
	}

	public CaseFeedVM getCaseBackMainInfo(String id) {
		/**
		 * 获取反馈信息
		 */
		CaseFeed caseFeed = caseFeedMapper.selectByCaseId(id);
		if(caseFeed==null){
			return null;
		}
		CaseFeedVM caseFeedVM = new CaseFeedVM(caseFeed);
		/**
		 * 获取案件名称
		 */
		CaseVM caseInfo= caseMapper.selectVMByPrimaryKey(id);
		caseFeedVM.setCaseName(caseInfo.getName());
		caseFeedVM.setCaseId(id);
		
		return caseFeedVM;
	}

	public String insertCaseFeed(CaseFeed feedBack) {
		if(caseFeedMapper.insert(feedBack)>0){
			return "";
		}else{
			return "添加案件反馈信息失败";
		}
	}

}
