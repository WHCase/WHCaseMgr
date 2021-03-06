package com.tianyi.whcase.viewmodel;

import java.util.Date;
import java.util.List;

import com.tianyi.whcase.model.Case;

public class CaseVM extends Case {
	private String categoryName;
	private String detectedunitNname;
	private String organizationame;
	private int distributeStatus;
	private Date receiveTime;
	private Date feedTime;
	private String caseStatus;
	private String feedInfo;
	
	private Date SectionTime;
	private String address;
	private String userGroupId;
	private boolean OnlyWithResource;
	
	private List<CaseAttachVM> caseAttachVMlist;
	public String getFeedInfo() {
		return feedInfo;
	}
	public void setFeedInfo(String feedInfo) {
		this.feedInfo = feedInfo;
	}
	public String getFeedResult() {
		return feedResult;
	}
	public void setFeedResult(String feedResult) {
		this.feedResult = feedResult;
	}
	private String feedResult;
	
	public String getCaseStatus() {
		return caseStatus;
	}
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
	public Date getFeedTime() {
		return feedTime;
	}
	public void setFeedTime(Date feedTime) {
		this.feedTime = feedTime;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDetectedunitNname() {
		return detectedunitNname;
	}
	public void setDetectedunitNname(String detectedunitNname) {
		this.detectedunitNname = detectedunitNname;
	}
	public String getOrganizationame() {
		return organizationame;
	}
	public void setOrganizationame(String organizationame) {
		this.organizationame = organizationame;
	}
	
	public int getDistributeStatus() {
		return distributeStatus;
	}
	public void setDistributeStatus(int distributeStatus) {
		this.distributeStatus = distributeStatus;
	}
	public Case getCase(){
		Case c = new Case();
		c.setId(this.getId());
		c.setName(this.getName());
		c.setCategoriesId(this.getCategoriesId());
		c.setStartTime(this.getStartTime());
		c.setDetectedunitId(this.getDetectedunitId());
		c.setSummary(this.getSummary());
		c.setStatus(this.getStatus());
		c.setCreator(this.getCreator());
		c.setOrganizationId(this.getOrganizationId());
		return c;
	}
	public Date getSectionTime() {
		return SectionTime;
	}
	public void setSectionTime(Date sectionTime) {
		SectionTime = sectionTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
	public boolean isOnlyWithResource() {
		return OnlyWithResource;
	}
	public void setOnlyWithResource(boolean onlyWithResource) {
		OnlyWithResource = onlyWithResource;
	}
	public List<CaseAttachVM> getCaseAttachVMlist() {
		return caseAttachVMlist;
	}
	public void setCaseAttachVMlist(List<CaseAttachVM> caseAttachVMlist) {
		this.caseAttachVMlist = caseAttachVMlist;
	}


}
