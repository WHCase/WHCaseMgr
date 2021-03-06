package com.tianyi.whcase.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.dao.CaseAttachMapper;
import com.tianyi.whcase.dao.CaseFeedMapper;
import com.tianyi.whcase.dao.CaseMapper;
import com.tianyi.whcase.dao.CaseOrganMapper;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.CaseFeed;
import com.tianyi.whcase.model.CaseUnit;
import com.tianyi.whcase.service.CaseService;
import com.tianyi.whcase.service.CommonService;
import com.tianyi.whcase.service.JieShangService;
import com.tianyi.whcase.viewmodel.CaseTJVM;
import com.tianyi.whcase.viewmodel.CaseVM;

@Service
public class CaseServiceImpl implements CaseService {

	@Autowired
	CaseMapper caseMapper;
	@Autowired
	CaseAttachMapper caseAttachMapper;
	@Autowired
	CaseOrganMapper caseOrganMapper;
	@Autowired
	CaseFeedMapper caseFeedMapper;
	
	@Autowired JieShangService jieShangService; 
	@Autowired CommonService commonService; 
	/**
	 * 获取案件列表
	 * 
	 * @return
	 */
	public ListResult<CaseVM> getCasePushListByReceiveStatus(
			Map<String, Object> map) {
		int count = caseMapper.selectUnFeedBackVMCount();
		List<CaseVM> caseList = caseMapper.selectUnFeedBackDistributedCase(map);
		for(int i = 0;i<caseList.size();i++){
			/*案件状态、Handling - 受理，
			 Detected - 已破
			 CloseCase - 销案*/
			if(caseList.get(i).getStatus().equals("Handling")){
				caseList.get(i).setCaseStatus("受理");
			}else if(caseList.get(i).getStatus().equals("Detected")){
				caseList.get(i).setCaseStatus("已破");
			}else if(caseList.get(i).getStatus().equals("CloseCase")){
				caseList.get(i).setCaseStatus("销案");
			}
		}
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
		for(int i = 0;i<ls.size();i++){
			/*案件状态、Handling - 受理，
			 Detected - 已破
			 CloseCase - 销案*/
			if(ls.get(i).getStatus().equals("Handling")){
				ls.get(i).setCaseStatus("受理");
			}else if(ls.get(i).getStatus().equals("Detected")){
				ls.get(i).setCaseStatus("已破");
			}else if(ls.get(i).getStatus().equals("CloseCase")){
				ls.get(i).setCaseStatus("销案");
			}
		}
		ListResult<CaseVM> result = new ListResult<CaseVM>(count, ls);
		return result;
	}

	public int deleteByCaseId(String caseId) {
		return caseMapper.deleteByPrimaryKey(caseId);
	}
	
	/**
	 * 删除本地案件及其附件信息
	 * @param CaseId
	 * @return
	 */
	public int deleteLocalCase(String CaseId){
		int temp = -1;
		try{
			if(!(caseMapper.deleteByPrimaryKey(CaseId) > 0))
				return 100;
			if(!(caseAttachMapper.deleteByCaseId(CaseId) > 0))
				return 200;
			temp = 0;
		}catch(Exception e){
			e.printStackTrace();
			temp = -2;
		}
		return temp;
	}

	public int updateCase(Case c) {
		return caseMapper.updateByPrimaryKey(c);
	}
	
	/**
	 * 更新案件及其相关信息
	 * @param c
	 * @return
	 */
	public int updateCCase(Case c) {
		CaseVM cc = new CaseVM();
		int temp = -1;
		try{
			cc = jieShangService.getCase(c.getId());
			temp = commonService.insertOrUpdateCaseVM(cc);
		}catch(Exception e){
			e.printStackTrace();
			temp = -2;
		}
		return temp;	
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
			return "";
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

	public ListResult<CaseVM> getCaseFeedBackListByReceiveStatus(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		int count = caseMapper.selectVMCountByReceiveStatus1(map);
		List<CaseVM> caseList = caseMapper.selectByReceiveStatus(map);
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
		caseTJ.setNotReceivedCaseCount(notReceive+distribute);
		
		caseTJ.setFeedBackCaseCount(feedBack);
		caseTJ.setNotFeedBackCaseCount(notFeedBack+receive);
		
		
				
		return caseTJ;
	}

	public ListResult<CaseVM> getDistributeCaseByOrganId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<CaseVM> caseVMList = new ArrayList<CaseVM>();
		int count =caseMapper.selectCountByorganId(map);
		caseVMList = caseMapper.selectByorganId(map);
		ListResult<CaseVM> l = new ListResult<CaseVM>(count, caseVMList);
		return l;
	}

	public ListResult<CaseVM> getFeedCaseByOrganId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<CaseVM> caseVMList = new ArrayList<CaseVM>();
		List<CaseVM> list = new ArrayList<CaseVM>();
		int count =caseMapper.selectCountFeedCaseByorganId(map);
		caseVMList = caseMapper.selectFeedCaseByorganId(map);
		list = dereplication(caseVMList);
		Integer organId = (Integer)map.get("organId");		
		for(CaseVM p:list){
          
			if(!"".equals(p.getReceiveTime()) && p.getReceiveTime() != null){				
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("caseId", p.getCase().getId());
				map1.put("organId",organId);
				CaseFeed cf = caseFeedMapper.selectByCondition(map1);
				if(cf != null){
					p.setFeedTime(cf.getCreateTime());
				}else{
					p.setFeedTime(null);
				}
			}	
		}
		ListResult<CaseVM> l = new ListResult<CaseVM>(count, list);
		return l;
	}
	
	/**
	 * List<CaseVM>去重,去空
	 * @param list
	 * @return
	 */
	private List<CaseVM> dereplication(List<CaseVM> list){
		List<CaseVM> newlist = new ArrayList<CaseVM>();
		if(list.size() == 0){
			return list;
		}else{
			for(CaseVM cv0:list){
				if("".equals(cv0.getId()) || cv0.getId() == null)
					continue;
				newlist.add(list.get(0));
				break;
			}			
		}
		for(CaseVM cv:list){
			int count = 0;
			if("".equals(cv.getId()) || cv.getId() == null)
				continue;
			for(CaseVM cv1:newlist){
				if(cv.getId().equals(cv1.getId()) && cv.getSummary().equals(cv1.getSummary())){
					count++;
				}
			}
			if(count == 0){
				newlist.add(cv);
			}
		}
		return newlist;		
	}

	
	/**
	 * 案件更新
	 * @param list
	 */
	public void updateCase(List<Case> list){
		try{
			Case t = new Case();
			for(Case c:list){
				t = caseMapper.selectByPrimaryKey(c.getId());
				if(t == null){
					caseMapper.insert(c);
				}else{
					caseMapper.updateByPrimaryKey(c);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
