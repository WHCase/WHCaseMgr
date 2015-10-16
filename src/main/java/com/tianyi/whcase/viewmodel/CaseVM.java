package com.tianyi.whcase.viewmodel;

import com.tianyi.whcase.model.Case;

public class CaseVM extends Case {
	private String categoryName;
	private String detectedunitNname;
	private String organizationame;
	private int distributeStatus;
	
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


}
