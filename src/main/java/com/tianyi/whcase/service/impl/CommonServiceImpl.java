package com.tianyi.whcase.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.dao.CaseAttachItemMapper;
import com.tianyi.whcase.dao.CaseAttachMapper;
import com.tianyi.whcase.dao.CaseMapper;
import com.tianyi.whcase.dao.OrganMapper;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.model.CaseAttach;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.detectUnit;
import com.tianyi.whcase.service.CommonService;
import com.tianyi.whcase.viewmodel.CaseAttachVM;
import com.tianyi.whcase.viewmodel.CaseVM;

@Service
public class CommonServiceImpl implements CommonService{
	@Autowired OrganMapper organMapper;
	@Autowired CaseMapper caseMapper;
	@Autowired CaseAttachMapper caseAttachMapper;
	@Autowired CaseAttachItemMapper caseAttachItemMapper;
	public int InsertCaseCategory(CaseCategory category) {
		if(organMapper.selectCaseCategoryByCategoryId(category.getId())==0){
			return organMapper.insertCaseCategory(category);
		}else{
			return 0;
		}
	}

	public int InsertUnit(detectUnit unit) {
		if(organMapper.selectUnitByUnitId(unit.getDetectedunitId())==0){
			return organMapper.insertUnit(unit);
		}else{
			return 0;
		}
		
	}
	

	public int insertOrUpdateCaseVM(CaseVM ccase) {
		// TODO Auto-generated method stub
		try{
			//案件主体信息更新
			Case ca = (Case)ccase;
			insertCase(ca);
			List<CaseAttachVM> alist = ccase.getCaseAttachVMlist();
			for(CaseAttachVM cavm:alist){
				//附件信息更新
				CaseAttach attach = (CaseAttach)cavm;
				insertCaseAttach(attach);
				//附件所带文件信息更新
				List<CaseAttachItem> ilist = cavm.getAttachItemList();
				for(CaseAttachItem item:ilist){
					insertCaseAttachItem(item);
				}
			}
			return 0;//成功编号
		}catch(Exception e){
			e.printStackTrace();
			return 400;//失败编号
		}		
	}
	
	/**
	 * 更新案件信息
	 * @param c
	 */
	private void insertCase(Case c){
		if(c != null){
			Case cs = new Case();
			cs = caseMapper.selectByPrimaryKey(c.getId());
			if(cs != null){
				caseMapper.updateByPrimaryKey(c);
				System.out.println("更新案件信息:"+c.getId());
			}else{
				c.setReceiveStatus(1);
				caseMapper.insert(c);
			}
		}
	}
	
	
	/**
	 * 附件更新
	 * @param ca
	 */
	private void insertCaseAttach(CaseAttach ca){
		if(ca != null){
			CaseAttach cs = new CaseAttach();
			cs = caseAttachMapper.selectByPrimaryKey(ca.getId());
			if(cs != null){
				caseAttachMapper.updateByPrimaryKey(ca);
				System.out.println("更新附件信息:"+ca.getId());
			}else{
				ca.setResourceType("1");
				caseAttachMapper.insert(ca);
			}
		}
	}
	
	/**
	 * 文件信息更新
	 * @param cai
	 */
	private void insertCaseAttachItem(CaseAttachItem cai){
		if(cai != null){
			CaseAttachItem cs = new CaseAttachItem();
			cs = caseAttachItemMapper.selectByPrimaryKey(cai.getId());
			if(cs != null){
				caseAttachItemMapper.updateByPrimaryKey(cai);
				System.out.println("更新文件信息:"+cai.getId());
			}else{
				caseAttachItemMapper.insert(cai);				
			}
		}
	}
}
