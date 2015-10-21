package com.tianyi.whcase.viewmodel;

import java.util.Date;
import java.util.List;

public class caseOrganVM {
	private String caseId;
	private List<Integer> organList;
	private int organId;
	private Date receiveTime;
	private Date senderTime;
	public Date getSenderTime() {
		return senderTime;
	}
	public void setSenderTime(Date senderTime) {
		this.senderTime = senderTime;
	}
	public int getOrganId() {
		return organId;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
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
