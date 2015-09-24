package com.tianyi.whcase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.dao.CaseAttachItemMapper;
import com.tianyi.whcase.dao.CaseAttachMapper;
import com.tianyi.whcase.model.CaseAttach;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.service.CaseAttchService;
import com.tianyi.whcase.viewmodel.CaseAttachVM;

@Service
public class CaseAttchServiceImpl implements CaseAttchService {
	@Autowired CaseAttachMapper caseAttachMapper;
	@Autowired CaseAttachItemMapper caseAttachItemMapper;
	
	public ListResult<CaseAttachItem> getCaseRelativeByCaseId(String id,int resourceType ) {
		CaseAttach attach = caseAttachMapper.selectByCaseId(id,resourceType);
		if(attach==null){
			return null;
		}
		String caseAttachId = attach.getId();
		List<CaseAttachItem> attachItemList = caseAttachItemMapper.selectByCaseAttachId(caseAttachId);
		return new ListResult<CaseAttachItem>(attachItemList);
		
	}

	public int AddAttachVM(CaseAttachVM caseAttachVM) {
		int temp =0;
		CaseAttach caseAttach = caseAttachVM.getCaseAttach();
		if(caseAttachMapper.selectByPrimaryKey(caseAttach.getId())!=null){
			return -2;
		}
		
		temp = caseAttachMapper.insert(caseAttach);
		
		/*
		 * 获取附件文件列表，单独保存
		 */
		List<CaseAttachItem> attachItemList = caseAttachVM.getAttachItemList();
		if(attachItemList==null ||attachItemList.size() ==0){
			return temp;
		}
		for(int i = 0;i<attachItemList.size();i++){
			if(caseAttachItemMapper.selectByPrimaryKey(attachItemList.get(i).getId())!=null){
				return -3;
			}
			temp = caseAttachItemMapper.insert(attachItemList.get(i));
		}
		return temp;
	}

}
