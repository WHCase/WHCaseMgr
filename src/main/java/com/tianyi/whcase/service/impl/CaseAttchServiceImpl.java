package com.tianyi.whcase.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.controller.JieShangInterfaceController;
import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.dao.CaseAttachItemMapper;
import com.tianyi.whcase.dao.CaseAttachMapper;
import com.tianyi.whcase.model.CaseAttach;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.service.CaseAttchService;
import com.tianyi.whcase.service.JieShangService;
import com.tianyi.whcase.viewmodel.CaseAttachVM;

@Service
public class CaseAttchServiceImpl implements CaseAttchService {
	@Autowired CaseAttachMapper caseAttachMapper;
	@Autowired CaseAttachItemMapper caseAttachItemMapper;
	@Autowired JieShangService jieShangService; 
	
	public ListResult<CaseAttachItem> getCaseRelativeByCaseId(String id,int resourceType ) {
		CaseAttach attach = caseAttachMapper.selectByCaseId(id,resourceType);
		if(attach==null){
			return null;
		}
		String caseAttachId = attach.getId();
		List<CaseAttachItem> attachItemList = caseAttachItemMapper.selectByCaseAttachId(caseAttachId);
		return new ListResult<CaseAttachItem>(attachItemList);
		
	}
	public ListResult<CaseAttachItem> getCaseRelativeByCaseId(String id,String resourceType ) {
		List<CaseAttach> attachList = caseAttachMapper.selectByCaseId(id,resourceType);
		if(attachList==null||attachList.size()==0){
			// 应该要提示一个信息，不应该为空
			return null;
		}
		List<CaseAttachItem> attachItemList = new ArrayList<CaseAttachItem>();
		for(int i = 0;i<attachList.size();i++){
			CaseAttach attach = attachList.get(i);
			String caseAttachId = attach.getId();
			List<CaseAttachItem> list =caseAttachItemMapper.selectByCaseAttachId(caseAttachId);
			for(int j=0;j<list.size();j++){
				attachItemList.add(list.get(j));
			}
		}
		
		return new ListResult<CaseAttachItem>(attachItemList);
		
	}

	public int AddAttachVM(CaseAttachVM caseAttachVM,HttpServletRequest request,HttpServletResponse response) {
		int temp =0;
		CaseAttach caseAttach = caseAttachVM.getCaseAttach();
		caseAttach.setResourceType("1");
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
			/*调用捷尚接口下载相应的附件*/
			try {
				jieShangService.downloadAttachFiles(attachItemList.get(i).getUri(),request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return temp;
	}
	public String deleteCaseAttach(String caseId,String caseattachId) {
		CaseAttachItem cAttachItem = caseAttachItemMapper.selectByPrimaryKey(caseattachId);
		if(cAttachItem==null){
			return "查询附件文件对应的附件信息不存在";
		}
		/*本地数据库删除，调用捷尚接口删除远程文件*/
		if(caseAttachItemMapper.deleteByPrimaryKey(caseattachId)<0){
			return "删除失败";
		}

		if(jieShangService.deleteCaseAttach(caseId,cAttachItem.getCaseAttchId())<0){
			return "调用捷尚删除接口删除失败";
		}
		return "";
	}

}
