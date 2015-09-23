package com.tianyi.whcase.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.dao.CaseMapper;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.CaseUnit;
import com.tianyi.whcase.service.CaseService;
import com.tianyi.whcase.viewmodel.CaseVM;
 
@Service
public class CaseServiceImpl implements CaseService {
	
	@Autowired CaseMapper caseMapper;

	/**
	 * 获取案件列表
	 * @return 
	 */
	public ListResult<CaseVM> getCasePushListByReceiveStatus(Integer receiveStatus) {
		int count = caseMapper.selectVMCountByReceiveStatus(receiveStatus);
		List<CaseVM> caseList =caseMapper.selectByReceiveStatus(receiveStatus); 
		ListResult<CaseVM> list = new ListResult<CaseVM>(count,caseList);
		return list;
	}

	public CaseVM getCaseMainInfo(String caseId) {
		return caseMapper.selectVMByPrimaryKey(caseId);
	}

	public String saveCaseInfo(CaseVM caseInfo) {
		Case c = caseInfo.getCase();
		int temp =caseMapper.updateByPrimaryKey(c);
		if(temp==0){
			return "未进行修改";
		}
		return "";
	}

	public List<CaseCategory> getCatogory() {
		return caseMapper.selectCaseCategory();
	}

	public List<CaseUnit> getCaseUnit() {
		return caseMapper.selectCaseUnit();
	}

	public String insert(Case c) {
		Case tempC = caseMapper.selectByPrimaryKey(c.getId());
		if(tempC!=null){
			return "获取的案件已经存在";
		}
		int temp = caseMapper.insert(c); 
		if(temp>0){
			return "";
		}else{
			return "案件插入失败";
		}
		
		
	}

	public ListResult<CaseVM> getCasePushListByPageAndRow(
			Map<String, Object> map) {
		int count=caseMapper.countByMap(map);
		List<CaseVM> ls=caseMapper.loaCaselistWithPage(map);
		ListResult<CaseVM> result=new ListResult<CaseVM>(count,ls);
		return result;
	}

}
