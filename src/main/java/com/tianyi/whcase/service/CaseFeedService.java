package com.tianyi.whcase.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.model.CaseFeed;
import com.tianyi.whcase.model.Organ;
import com.tianyi.whcase.viewmodel.CaseFeedVM;

@Service
public interface CaseFeedService {

	List<CaseAttachItem> getCaseBackAttchMents(String id,int resourceType);

	CaseFeedVM getCaseBackMainInfo(String id);

	String insertCaseFeed(CaseFeed feedBack);

	List<Organ> getFeedBackOrganById(String id);

	CaseFeedVM getCaseBackMainInfo(Map<String, Object> map);

}
