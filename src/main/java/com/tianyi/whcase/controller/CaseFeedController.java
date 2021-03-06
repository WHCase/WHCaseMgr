package com.tianyi.whcase.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.whcase.core.Constants;
import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.core.Result;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.model.CaseFeed;
import com.tianyi.whcase.model.Organ;
import com.tianyi.whcase.service.CaseFeedService;
import com.tianyi.whcase.service.CaseService;
import com.tianyi.whcase.viewmodel.CaseFeedVM;
import com.tianyi.whcase.viewmodel.CaseVM;

/**
 * ��������������ҵ���߼�
 * @author seeLittleGirlAgain
 *
 */
@Controller
@RequestMapping("/caseFeed")
public class CaseFeedController {
	@Autowired CaseFeedService caseFeedService;
	@Autowired CaseService caseService;
	
	@RequestMapping(value = "getCaseBackAttchMents.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCaseMainInfo(
		@RequestParam(value="caseId",required = false) String id,
		HttpServletRequest request)throws Exception{
		try {
			List<CaseAttachItem> caseFeedAttachItemList = caseFeedService.getCaseBackAttchMents(id,Constants.RECEIVE_STATUS__FEEDBACK);
			
			ListResult<CaseAttachItem> result = new ListResult<CaseAttachItem>(caseFeedAttachItemList);
			return result.toJson();
		} catch (Exception ex) {
			Result<CaseAttachItem> result = new Result<CaseAttachItem>(null, false, false, false,
					"查询失败");
			return result.toJson();
		}
	}
	@RequestMapping(value = "getCaseBackMainInfo.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCaseBackMainInfo(
		@RequestParam(value="caseId",required = false) String id,
		@RequestParam(value="organId",required = false) Integer organId,
		HttpServletRequest request)throws Exception{
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();   
			map.put("caseId", id); 
			map.put("organId", organId);
			CaseFeedVM caseFeedAttachItemList = caseFeedService.getCaseBackMainInfo(map);
			Result<CaseFeedVM> result = new Result<CaseFeedVM>(caseFeedAttachItemList, true, false,
					false, "查询数据成功");
			return result.toJson();
		} catch (Exception ex) {
			Result<CaseFeedVM> result = new Result<CaseFeedVM>(null, false, false, false,
					"查询失败");
			return result.toJson();
		}
	}
	// 反馈信息保存和更新
	@RequestMapping(value = "saveFeedBacInfo.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveFeedBacInfo(
		@RequestParam(value="feedBackInfo",required = false) String feedBackInfo,
		HttpServletRequest request)throws Exception{
		try {
			JSONObject jObj = JSONObject.fromObject(feedBackInfo);
			CaseFeed feedBack = (CaseFeed)JSONObject.toBean(jObj,CaseFeed.class);
			
			UUID uuid = UUID.randomUUID();
			feedBack.setId(uuid.toString());
			
			String temp = caseFeedService.insertCaseFeed(feedBack);
			// 反馈信息只能反馈一次，反馈过了就不能反馈了   修改人：xie
			
			temp = caseService.updateCaseReceiveStatus(Constants.RECEIVE_STATUS__FEEDBACK, feedBack.getCaseId());
          
			if(!temp.isEmpty()){
				Result<CaseVM> result = new Result<CaseVM>(null, false, false, false,
						temp);
				return result.toJson();
			}
			Result<CaseFeed> result = new Result<CaseFeed>(null, true, false,
					false, "添加案件反馈信息成功");
			return result.toJson();
		} catch (Exception ex) {
			Result<CaseFeed> result = new Result<CaseFeed>(null, false, false, false,
					"案件反馈信息编辑失败");
			return result.toJson();
		}
	}
	@RequestMapping(value = "getFeedBackOrganById.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getFeedBackOrganById(
		@RequestParam(value="caseId",required = false) String id,
		HttpServletRequest request)throws Exception{
		
		try {
			List<Organ> list = caseFeedService.getFeedBackOrganById(id);
			JSONArray result = JSONArray.fromObject(list);
			return result.toString();
		} catch (Exception ex) {
			return "";
		}
	}

	
}
