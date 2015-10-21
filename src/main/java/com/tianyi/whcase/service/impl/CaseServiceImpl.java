package com.tianyi.whcase.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.dao.CaseMapper;
import com.tianyi.whcase.dao.CaseOrganMapper;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.CaseUnit;
import com.tianyi.whcase.service.CaseService;
import com.tianyi.whcase.viewmodel.CaseTJVM;
import com.tianyi.whcase.viewmodel.CaseVM;

@Service
public class CaseServiceImpl implements CaseService {

	@Autowired
	CaseMapper caseMapper;
	@Autowired
	CaseOrganMapper caseOrganMapper;
	/**
	 * 获取案件列表
	 * 
	 * @return
	 */
	public ListResult<CaseVM> getCasePushListByReceiveStatus(
			Integer receiveStatus) {
		int count = caseMapper.selectUnFeedBackVMCount();
		List<CaseVM> caseList = caseMapper.selectUnFeedBackDistributedCase();
		ListResult<CaseVM> list = new ListResult<CaseVM>(count, caseList);
		return list;
	}

	public CaseVM getCaseMainInfo(String caseId) {
		return caseMapper.selectVMByPrimaryKey(caseId);
	}

	public String saveCaseInfo(CaseVM caseInfo) {
		Case c = caseInfo.getCase();
		int temp = caseMapper.updateByPrimaryKey(c);
		if (temp == 0) {
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
		if (tempC != null) {
			return "获取的案件已经存在";
		}
		int temp = caseMapper.insert(c);
		if (temp > 0) {
			return "";
		} else {
			return "案件插入失败";
		}

	}

	public ListResult<CaseVM> getCasePushListByPageAndRow(
			Map<String, Object> map) {
		int count = caseMapper.countByMap(map);
		List<CaseVM> ls = caseMapper.loaCaselistWithPage(map);
		ListResult<CaseVM> result = new ListResult<CaseVM>(count, ls);
		return result;
	}

	public int deleteByCaseId(String caseId) {
		return caseMapper.deleteByPrimaryKey(caseId);
	}

	public int updateCase(Case c) {
		return caseMapper.updateByPrimaryKey(c);
	}

	public String updateCaseReceiveStatusAndLevel(int receiveStatus, String level,
			String caseId) {
		int temp = caseMapper.updateCaseReceiveStatusAndLevel(receiveStatus, level,
				caseId);
		if (temp > 0) {
			return "修改状态成功";
		} else {
			return "修改失败";
		}
	}

	public String updateCaseReceiveStatus(int receiveStatus, String caseId) {
		int temp = caseMapper.updateCaseReceiveStatus(receiveStatus,caseId);
		if (temp > 0) {
			return "修改状态成功";
		} else {
			return "修改失败";
		}
	}
	public List<CaseVM> getDistributeCase(int receiveStatus,
			List<String> caseIdList) {
		List<CaseVM> caseVMList = new ArrayList<CaseVM>();
		for (int i = 0; i < caseIdList.size(); i++) {
			CaseVM temp = caseMapper.selectByCaseIdAndReceiveStatus(
					receiveStatus, caseIdList.get(i));
			if (temp != null) {
				caseVMList.add(temp);
			}
		}
		return caseVMList;
	}

	public ListResult<CaseVM> getCaseFeedBackListByReceiveStatus() {
		// TODO Auto-generated method stub
		int count = caseMapper.selectVMCountByReceiveStatus(6);
		List<CaseVM> caseList = caseMapper.selectByReceiveStatus(6);
		ListResult<CaseVM> list = new ListResult<CaseVM>(count, caseList);
		return list;

	}

	public CaseTJVM getCaseTJInfo() {
		// TODO Auto-generated method stub
		CaseTJVM caseTJ = new CaseTJVM();
		
		caseTJ.setCaseTotalCount(caseMapper.selectCountCase());
		
		int distribute = caseMapper.selectCountCaseByReceiveStatus(2);
		
		int notReceive = caseMapper.selectCountCaseByReceiveStatus(3);
		int receive = caseMapper.selectCountCaseByReceiveStatus(4);
		int notFeedBack = caseMapper.selectCountCaseByReceiveStatus(5);
		int feedBack = caseMapper.selectCountCaseByReceiveStatus(6);
		
		caseTJ.setDistributedCaseCount(distribute+receive+notReceive+feedBack+notFeedBack);
		caseTJ.setNotDistributeCaseCount(caseMapper.selectCountCaseByReceiveStatus(1));
		
		caseTJ.setReceivedCaseCount(receive+feedBack+notFeedBack);
		caseTJ.setNotReceivedCaseCount(notReceive);
		
		caseTJ.setFeedBackCaseCount(feedBack);
		caseTJ.setNotFeedBackCaseCount(notFeedBack);
		
		
				
		return caseTJ;
	}

	public CaseTJVM getCaseTJInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		CaseTJVM caseTJ = new CaseTJVM();
		
		caseTJ.setCaseTotalCount(caseMapper.selectTotalCountByCondition(map));
		map.put("casestatus", 2);
		int distribute = caseMapper.selectCountCaseByCondition(map);

		map.put("casestatus", 3);
		int notReceive = caseMapper.selectCountCaseByCondition(map);
		map.put("casestatus", 4);
		int receive = caseMapper.selectCountCaseByCondition(map);
		map.put("casestatus", 5);
		int notFeedBack = caseMapper.selectCountCaseByCondition(map);
		map.put("casestatus", 6);
		int feedBack = caseMapper.selectCountCaseByCondition(map);
		
		caseTJ.setDistributedCaseCount(distribute+receive+notReceive+feedBack+notFeedBack);
		map.put("casestatus", 1);
		caseTJ.setNotDistributeCaseCount(caseMapper.selectCountCaseByCondition(map));
		
		caseTJ.setReceivedCaseCount(receive+feedBack+notFeedBack);
		caseTJ.setNotReceivedCaseCount(notReceive);
		
		caseTJ.setFeedBackCaseCount(feedBack);
		caseTJ.setNotFeedBackCaseCount(notFeedBack);
		
		
				
		return caseTJ;
	}



}
