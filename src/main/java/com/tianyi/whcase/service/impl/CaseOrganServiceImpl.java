package com.tianyi.whcase.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.dao.CaseOrganMapper;
import com.tianyi.whcase.model.CaseOrgan;
import com.tianyi.whcase.service.CaseOrganService;
import com.tianyi.whcase.viewmodel.CaseTJVM;
import com.tianyi.whcase.viewmodel.caseOrganVM;

@Service
public class CaseOrganServiceImpl implements CaseOrganService {
	@Autowired CaseOrganMapper caseOrganMapper;
	public String insertCaseOrgan(String caseId, int indexOf) {
		
		List<CaseOrgan> tempList = caseOrganMapper.selectByCaseIdAndOrganId(caseId,indexOf);
		if(tempList.size()!=0){
			return "本案件已经向相关的派出所推送过了";
		}
		CaseOrgan record = new CaseOrgan();
		record.setCaseId(caseId);
		record.setOrganizationId(indexOf);
		UUID u = java.util.UUID.randomUUID();
		record.setId(u.toString());
		record.setSenderTime(new Date());
		
		int temp =caseOrganMapper.insert(record);
		if(temp ==-1){
			return "推送失败";
		}
		return "";
	}
	public List<String> selectCaseLiseByOrganId(int organId) {
		List<CaseOrgan> temp = caseOrganMapper.selectByOrganId(organId);
		if(temp ==null||temp.size()==0){
			return null;
		}
		List<String> caseIdList = new ArrayList<String>();
		for(int i = 0;i<temp.size();i++){
			caseIdList.add(temp.get(i).getCaseId());
		}
		return caseIdList;
	}
	public List<caseOrganVM> selectRecordLiseByCaseId(String caseId) {
		// TODO Auto-generated method stub 
		List<caseOrganVM> list = caseOrganMapper.selectRecordLiseByCaseId(caseId); 
		return list;
	}
	public CaseTJVM getCaseTJInfo(int organId) {
		// TODO Auto-generated method stub
		
		CaseTJVM caseTJ = new CaseTJVM();
		int feedBack = caseOrganMapper.selectCountCaseByReceiveStatusAndOrganId(organId,6);
		int notFeedBack = caseOrganMapper.selectCountCaseByReceiveStatusAndOrganId(organId,5);
		caseTJ.setFeedBackCaseCount(feedBack);
		caseTJ.setNotFeedBackCaseCount(notFeedBack);
		
		caseTJ.setReceivedCaseCount(caseOrganMapper.selectCountCaseByReceiveStatusAndOrganId(organId,4)+feedBack+notFeedBack);
		caseTJ.setNotReceivedCaseCount(caseOrganMapper.selectCountCaseByReceiveStatusAndOrganId(organId,3));
		
		return caseTJ;
	}
	public String updateReiceive(String caseId, int organId) {
		List<CaseOrgan> caseOrganList = caseOrganMapper.selectByCaseIdAndOrganId(caseId, organId); 
		if(caseOrganList.size()==0){
			return "该推送案件不存在";
		}
		caseOrganList.get(0).setReceiveTime(new Date());
		int temp =caseOrganMapper.updateByPrimaryKey(caseOrganList.get(0));
		if(temp >0){
			return "";
		}else{
			return "更新失败";
		}
	}

}
