package com.tianyi.whcase.viewmodel;

import java.util.List;

public class DistributeCase {
	private List<String> caseIdList;
	private int receiveStatus;
	private int organId;
	private int caseType;
	public int getCaseType() {
		return caseType;
	}
	public void setCaseType(int caseType) {
		this.caseType = caseType;
	}
	public int getOrganId() {
		return organId;
	}
	public void setOrganId(int organId) {
		this.organId = organId;
	}
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
