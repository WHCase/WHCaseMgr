package com.tianyi.whcase.viewmodel;

import java.util.List;

public class caseOrganVM {
	private String caseId;
	private List<Integer> organList;
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public List<Integer> getOrganList() {
		return organList;
	}
	public void setOrganList(List<Integer> organList) {
		this.organList = organList;
	}
}
