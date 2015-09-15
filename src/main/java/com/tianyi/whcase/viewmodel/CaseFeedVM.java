package com.tianyi.whcase.viewmodel;

import java.util.List;

import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.model.CaseFeed;

public class CaseFeedVM extends CaseFeed{
	private String caseName;
	private List<CaseAttachItem> caseAttachItemList;
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public List<CaseAttachItem> getCaseAttachItemList() {
		return caseAttachItemList;
	}
	public void setCaseAttachItemList(List<CaseAttachItem> caseAttachItemList) {
		this.caseAttachItemList = caseAttachItemList;
	}
	public CaseFeedVM(CaseFeed caseFeed){
		this.setCaseId(caseFeed.getCaseId());
		this.setCaseResult(caseFeed.getCaseResult());
		this.setContent(caseFeed.getContent());
		this.setCreateTime(caseFeed.getCreateTime());
		this.setCreator(caseFeed.getCreator());
		this.setId(caseFeed.getId());
		this.setOrganizationId(caseFeed.getOrganizationId());
	}
}
