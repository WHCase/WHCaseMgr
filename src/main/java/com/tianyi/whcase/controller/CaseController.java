package com.tianyi.whcase.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.whcase.core.DateJsonValueProcessor;
import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.core.Result;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.CaseUnit;
import com.tianyi.whcase.service.CaseService;
import com.tianyi.whcase.viewmodel.CaseVM;

/**
 * 
 * @author seeLittleGirlAgain
 *
 */
@Controller
@RequestMapping("/case")
public class CaseController {
	
	@Autowired CaseService caseService;
	/**
	 * 
	 * @param caseInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getCaseList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCasePushList(
		@RequestParam(value="caseInfo",required = false) String caseInfo,
		HttpServletRequest request)throws Exception{
		/**
		 * 根据案件接收类型、查询时间段来查询案件，返回一个案件列表
		 */
		JSONObject jObj = JSONObject.fromObject(caseInfo);
		Case caseinfo = (Case) JSONObject.toBean(jObj,Case.class);
		
		Integer receiveStatus = caseinfo.getReceiveStatus();
		ListResult<CaseVM> caseList =caseService.getCasePushListByReceiveStatus(receiveStatus); 
		if(caseList ==null){
			return null;
		}
		return caseList.toJson();
	}
	@RequestMapping(value = "getCaseMainInfo.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCaseMainInfo(
		@RequestParam(value="caseId",required = false) String id,
		HttpServletRequest request)throws Exception{
		/**
		 * 根据案件id来查询案件，返回一个案件列表
		 */	
		try {
			CaseVM caseInfo = caseService.getCaseMainInfo(id);
			
			Result<CaseVM> result = new Result<CaseVM>(caseInfo, true, false,
					false, "查询数据成功");
			return result.toJson();
		} catch (Exception ex) {
			Result<CaseVM> result = new Result<CaseVM>(null, false, false, false,
					"查询失败");
			return result.toJson();
		}
	}
	@RequestMapping(value = "SaveCaseInfo.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String SaveCaseInfo(
		@RequestParam(value="caseInfo",required = false) String caseInfo,
		HttpServletRequest request)throws Exception{
		try {
			JSONObject jObj = JSONObject.fromObject(caseInfo);
			CaseVM caseinfo = (CaseVM) JSONObject.toBean(jObj,CaseVM.class);
			
			String temp = caseService.saveCaseInfo(caseinfo);
			if(!temp.isEmpty()){
				Result<CaseVM> result = new Result<CaseVM>(null, false, false, false,
						temp);
				return result.toJson();
			}
			Result<CaseVM> result = new Result<CaseVM>(null, true, false,
					false, "查询数据成功");
			return result.toJson();
		} catch (Exception ex) {
			Result<CaseVM> result = new Result<CaseVM>(null, false, false, false,
					"查询失败");
			return result.toJson();
		}
	}
	@RequestMapping(value = "getCatogory.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCatogory(
		HttpServletRequest request)throws Exception{
		try {
			List<CaseCategory> caseCategoryList = caseService.getCatogory();
			JSONArray rs=JSONArray.fromObject(caseCategoryList);
			return rs.toString();
		} catch (Exception ex) {
			Result<CaseCategory> result = new Result<CaseCategory>(null, false, false, false,
					"获取案件类型失败");
			return result.toJson();
		}
	}
	@RequestMapping(value = "getCaseUnit.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCaseUnit(
		HttpServletRequest request)throws Exception{
		try {
			List<CaseUnit> caseUnitList = caseService.getCaseUnit();
			JSONArray rs=JSONArray.fromObject(caseUnitList);
			return rs.toString();
		} catch (Exception ex) {
			Result<CaseUnit> result = new Result<CaseUnit>(null, false, false, false,
					"获取侦查单位失败");
			return result.toJson();
		}
	}
	
}
