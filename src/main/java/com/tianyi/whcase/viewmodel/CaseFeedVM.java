package com.tianyi.whcase.viewmodel;

import java.util.List;

import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.model.CaseFeed;

public class CaseFeedVM extends CaseFeed{
	private String caseName;
	private String organName;
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
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
}
