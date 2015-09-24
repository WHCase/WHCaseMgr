package com.tianyi.whcase.viewmodel;

import java.util.List;

public class DistributeCase {
	private List<String> caseIdList;
	private int receiveStatus;
	public List<String> getCaseIdList() {
		return caseIdList;
	}
	public void setCaseIdList(List<String> caseIdList) {
		this.caseIdList = caseIdList;
	}
	public int getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(int receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
	
}
