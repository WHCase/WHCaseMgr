package com.tianyi.whcase.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.whcase.core.Constants;
import com.tianyi.whcase.core.DateJsonValueProcessor;
import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.core.Result;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.CaseUnit;
import com.tianyi.whcase.service.CaseService;
import com.tianyi.whcase.viewmodel.CaseVM;
import com.tianyi.whcase.viewmodel.DistributeCase;

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
	@RequestMapping(value = "getDistributeCaseList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getDistributeCaseList(
		@RequestParam(value="caseInfo",required = false) String caseInfo,
		HttpServletRequest request)throws Exception{
		/**
		 * 根据案件接收类型、查询时间段来查询案件，返回一个案件列表
		 */
		JSONObject jObj = JSONObject.fromObject(caseInfo);
		DistributeCase distributeCase = (DistributeCase)JSONObject.toBean(jObj,DistributeCase.class);
		if(distributeCase.getCaseIdList() ==null){
			return new ListResult<CaseVM>(null).toJson();
		}
		List<CaseVM> caseList =caseService.getDistributeCase(distributeCase.getReceiveStatus(),distributeCase.getCaseIdList()); 
		ListResult<CaseVM> result = new ListResult<CaseVM>(caseList);

		return result.toJson();
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
	 * 
	 * @param caseId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "changeCaseReceiveStatusAndLevel.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String changeCaseReceiveStatusAndLevel(
			@RequestParam(value="caseId",required = false) String caseId,
			@RequestParam(value="caseLevel",required = false) String caseLevel,
			HttpServletRequest request)throws Exception{
		String temp = caseService.updateCaseReceiveStatus(Constants.RECEIVE_STATUS__DISTRIBUTED,caseLevel,caseId);
		Result<Case> result = new Result<Case>(null,true,false,false,temp);
		return result.toJson();
	}
	/**
	 * （优创提供外调接口）新增案件
	 * @param requestBody
	 * @param request
	 * @return 0：新添案件成功
	 * @throws Exception
	 */
	@RequestMapping(value = "AddCase", produces = "application/xml;charset=UTF-8")
	public @ResponseBody String AddCase(
			@RequestBody String requestBody,
			HttpServletRequest request)throws Exception{
		Document document = DocumentHelper.parseText(requestBody);
		
		Case c = getCaseInfoFromDocument(document);
		
		String temp = caseService.insert(c);
		
		if(!temp.isEmpty()){
			return getReturnXml(-1);
		}else{
			
			return getReturnXml(0);
		}
		
	}
	private Case getCaseInfoFromDocument(Document document) {
		Element root =  document.getRootElement();
		Case c = new Case();
		c.setId(root.attributeValue("id"));
		c.setName(root.attributeValue("Name"));
		c.setCreator(Integer.parseInt(root.attributeValue("Creator")));
		c.setReceiveStatus(Constants.RECEIVE_STATUS_NOT_DISTRIBUTE);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    
		try {
			Date date;
			date = sdf.parse(root.attributeValue("CreateTime"));
			c.setCreateTime(date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		
		c.setCode(root.attributeValue("Code"));
		c.setCategoriesId(root.attributeValue("Categories"));
		
		
		try {
			Date date1;
			date1 = sdf.parse(root.attributeValue("StartTime"));
			c.setStartTime(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.setSummary(root.attributeValue("Summary"));
		c.setStatus(root.attributeValue("Status"));
		
		c.setIsregister(false);
		c.setLevel(root.attributeValue("Level"));
		c.setLongitude(root.attributeValue("Longitude"));
		c.setLatitude(root.attributeValue("Latitude"));
		
		c.setOrganizationId(Integer.parseInt(root.attributeValue("OrganizationID")));
		c.setDetectedunitId(Integer.parseInt(root.attributeValue("DetectedUnit")));
		return c;
	}
	/**
	 * （优创提供外调接口） 删除指定案件
	 * @param caseId 待删除的案件ID
	 * @param request
	 * @return -1删除失败 ； 大于0 删除成功；
	 * @throws Exception
	 */
	@RequestMapping(value = "DeleteCase", produces = "application/xml;charset=UTF-8")
	public @ResponseBody String DeleteCase(
			@RequestParam(value="caseId",required = false) String caseId,
			HttpServletRequest request)throws Exception{
		/*
		 * 是否要删除案件相关的串并案、附件等相关信息？
		 * */
		caseService.deleteByCaseId(caseId);
		return getReturnXml(0);
	}
	/**
	 * (优创提供外调接口)更新相关案件
	 * @param caseInfo 案件
	 * @param request
	 * @return	-1更新失败 ； 大于0 更新成功；
	 * @throws Exception
	 */
	@RequestMapping(value = "UpdateCase.do", produces = "application/xml;charset=UTF-8")
	public @ResponseBody String UpdateCase(
			@RequestBody String requestBody,			
			HttpServletRequest request)throws Exception{
		Document document = DocumentHelper.parseText(requestBody);
		/**
		 * 是否需要重新获取案件附件？
		 */
		Case c = getCaseInfoFromDocument(document);
		caseService.updateCase(c);
		return getReturnXml(0);
	}
	
	private String getReturnXml(int returnNum){
		StringBuilder sb = new StringBuilder();  
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");  
        sb.append("<int>"+returnNum+"</int>");  
          
        return sb.toString();  
	}
}
