package com.tianyi.whcase.viewmodel;

import java.util.List;

import com.tianyi.whcase.model.CaseAttach;
import com.tianyi.whcase.model.CaseAttachItem;

public class CaseAttachVM extends CaseAttach{
	private List<CaseAttachItem> attachItemList;

	public List<CaseAttachItem> getAttachItemList() {
		return attachItemList;
	}

	public void setAttachItemList(List<CaseAttachItem> attachItemList) {
		this.attachItemList = attachItemList;
	}
	public CaseAttach getCaseAttach(){
		CaseAttach caseAttach = new CaseAttach();
		caseAttach.setId(this.getId());
		caseAttach.setCaseId(this.getCaseId());
		caseAttach.setCreator(this.getCreator());
		caseAttach.setDescription(this.getDescription());
		caseAttach.setMessageType(this.getMessageType());
		caseAttach.setName(this.getName());
		caseAttach.setResourceType(this.getResourceType());
		
		return caseAttach;
	}
}
