package com.tianyi.whcase.viewmodel;

import java.util.List;

public class caseOrganVM {
	private String caseId;
	private List<Integer> organList;
	private int organId;
	public int getOrganId() {
		return organId;
	}
	public void setOrganId(int organId) {
		this.organId = organId;
	}
	private String organName;
	private String disTime;
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getDisTime() {
		return disTime;
	}
	public void setDisTime(String disTime) {
		this.disTime = disTime;
	}
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
