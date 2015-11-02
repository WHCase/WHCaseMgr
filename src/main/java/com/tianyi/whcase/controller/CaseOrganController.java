package com.tianyi.whcase.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.core.Result;
import com.tianyi.whcase.service.CaseOrganService;
import com.tianyi.whcase.viewmodel.CaseTJVM;
import com.tianyi.whcase.viewmodel.caseOrganVM;

/**
 * 
 * @author seeLittleGirlAgain
 *
 */
@Controller
@RequestMapping("/CaseOrgan")
public class CaseOrganController {
	@Autowired CaseOrganService caseOrganService;
	@RequestMapping(value = "getCaseListByOrganId.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCaseListByOrganId(
			@RequestParam(value="organId",required = false) int organId,
			HttpServletRequest request)throws Exception{
		List<String> caseIdList = caseOrganService.selectCaseLiseByOrganId(organId);
		String temp = "";
		if(caseIdList ==null){
			temp = "该机构下暂无推送案件";
		}else{
			temp = "案件获取成功";
		}
		ListResult<String> result = new ListResult<String>(caseIdList,true,temp);
		return result.toJson();
	}
	@RequestMapping(value = "pushCaseToOrgans.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String pushCaseToOrgans(
			@RequestParam(value="caseOrgan",required = false) String caseOrgan,
			HttpServletRequest request)throws Exception{
		
		JSONObject jObj = JSONObject.fromObject(caseOrgan);
		caseOrganVM caseorgan = (caseOrganVM) JSONObject.toBean(jObj,caseOrganVM.class);
		try{
			for(int i =0;i<caseorgan.getOrganList().size();i++){
				String temp = caseOrganService.insertCaseOrgan(caseorgan.getCaseId(),caseorgan.getOrganList().get(i));
				if(!temp.isEmpty()){
					Result<caseOrganVM> result = new Result<caseOrganVM>(null, false, false, false,
							temp);
					return result.toJson();
				}
			}
			return new Result<caseOrganVM>(null, true, true, true,
					"分配成功").toJson();
		}catch(Exception ex){
			Result<caseOrganVM> result = new Result<caseOrganVM>(null, false, false, false,
					"分配异常");
			return result.toJson();
		}
		
	}
	@RequestMapping(value = "getCaseDistributeRecordList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCaseDistributeRecordList(
			@RequestParam(value="caseId",required = false) String caseId,
			HttpServletRequest request)throws Exception{
		List<caseOrganVM> recordList = caseOrganService.selectRecordLiseByCaseId(caseId);
		 
		ListResult<caseOrganVM> result = new ListResult<caseOrganVM>(recordList,true,"");
		return result.toJson();
	}
	@RequestMapping(value = "getCaseTJInfo.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCaseDistributeRecordList(
			@RequestParam(value="organId",required = false) int organId,
			@RequestParam(value="startTime",required = false) String startTime,
			@RequestParam(value="endTime",required = false) String endTime,
			HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();   
		map.put("organId", organId);  
		if (!"".equals(startTime)&&startTime!=null) {
			map.put("startTime", startTime);
		}
		if (!"".equals(endTime)&&endTime!=null) {
			endTime = endTime+" 23:59:59";
			map.put("endTime", endTime);
		}
		CaseTJVM caseTJ = caseOrganService.getCaseTJInfo(map);
		List<CaseTJVM> list = new ArrayList<CaseTJVM>();
		list.add(caseTJ);
		ListResult<CaseTJVM> caseTJResult = new ListResult<CaseTJVM>(list);
		 
		return caseTJResult.toJson();
	}

}
