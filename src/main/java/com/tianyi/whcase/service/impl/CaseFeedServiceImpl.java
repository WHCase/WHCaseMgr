package com.tianyi.whcase.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.dao.CaseAttachItemMapper;
import com.tianyi.whcase.dao.CaseAttachMapper;
import com.tianyi.whcase.dao.CaseFeedMapper;
import com.tianyi.whcase.dao.CaseMapper;
import com.tianyi.whcase.model.CaseAttach;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.model.CaseFeed;
import com.tianyi.whcase.model.Organ;
import com.tianyi.whcase.service.CaseFeedService;
import com.tianyi.whcase.viewmodel.CaseFeedVM;
import com.tianyi.whcase.viewmodel.CaseVM;

@Service
public class CaseFeedServiceImpl implements CaseFeedService {
	@Autowired
	CaseAttachMapper caseAttachMapper;
	@Autowired
	CaseAttachItemMapper caseAttachItemMapper;
	@Autowired
	CaseFeedMapper caseFeedMapper;
	@Autowired
	CaseMapper caseMapper;

	public List<CaseAttachItem> getCaseBackAttchMents(String id,
			int resourceType) {

		/**
		 * 获取反馈案件的附件
		 */
		CaseAttach attach = caseAttachMapper.selectByCaseId(id, resourceType);
		if (attach == null) {
			return null;
		}
		String caseAttachId = attach.getId();
		List<CaseAttachItem> attachItemList = caseAttachItemMapper
				.selectByCaseAttachId(caseAttachId);
		return attachItemList;
	}

	public CaseFeedVM getCaseBackMainInfo(String id) {
		 
		return null;
	}
    
	// 插入反馈的数据
	public String insertCaseFeed(CaseFeed feedBack) {
		Map<String, Object> map = new HashMap<String, Object>();   
		map.put("caseId", feedBack.getCaseId());
		map.put("organId", feedBack.getOrganizationId());
		CaseFeed fb = caseFeedMapper.selectByCondition(map);
		if (fb != null) {
			/*fb.setContent(feedBack.getContent());
			fb.setCaseResult(feedBack.getCaseResult());
			caseFeedMapper.updateByPrimaryKey(fb);*/
			return "-1";
		} else {
			caseFeedMapper.insert(feedBack);
		}
		return "";

	}

	public List<Organ> getFeedBackOrganById(String id) {
		// TODO Auto-generated method stub
		return caseFeedMapper.getFeedBackOrganById(id);
	}

	public CaseFeedVM getCaseBackMainInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<CaseFeedVM> list = caseFeedMapper.getCaseBackMainInfo(map);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		} 
	}

}
