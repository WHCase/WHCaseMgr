package com.tianyi.whcase.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		return caseList.toJson();
	}
	@RequestMapping(value = "loaCaselistWithPage.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String loaCaselistWithPage(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value="caseInfo",required = false) String caseInfo,
		HttpServletRequest request)throws Exception{
		/**
		 * 根据案件接收类型、查询时间段来查询案件，返回一个案件列表
		 */
		JSONObject jObj = JSONObject.fromObject(caseInfo);
		Case caseinfo = (Case) JSONObject.toBean(jObj,Case.class);
		
		Map<String, Object> map = new HashMap<String, Object>();   
		page = page == 0 ? 1 : page;
		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		map.put("receiveStatus",caseinfo.getReceiveStatus());

		ListResult<CaseVM> caseList =caseService.getCasePushListByPageAndRow(map); 
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
	/**
	 * （优创提供外调接口）新增案件
	 * @param caseId 新增案件Id
	 * @param request
	 * @return 大于0  案件接收成功；-1 接收失败；
	 * @throws Exception
	 */
	@RequestMapping(value = "AddCase.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody int AddCase(
			@RequestParam(value="caseId",required = false) String caseId,
			HttpServletRequest request)throws Exception{
		
		return 0;
	}
	/**
	 * （优创提供外调接口） 删除指定案件
	 * @param caseId 待删除的案件ID
	 * @param request
	 * @return -1删除失败 ； 大于0 删除成功；
	 * @throws Exception
	 */
	@RequestMapping(value = "DeleteCase.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody int DeleteCase(
			@RequestParam(value="caseId",required = false) String caseId,
			HttpServletRequest request)throws Exception{
		
		return 0;
	}
	/**
	 * (优创提供外调接口)更新相关案件
	 * @param caseInfo 案件
	 * @param request
	 * @return	-1更新失败 ； 大于0 更新成功；
	 * @throws Exception
	 */
	@RequestMapping(value = "UpdateCase.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody int UpdateCase(
			@RequestParam(value="caseInfo",required = false) String caseInfo,
			HttpServletRequest request)throws Exception{
		
		return 0;
	}
	
}
