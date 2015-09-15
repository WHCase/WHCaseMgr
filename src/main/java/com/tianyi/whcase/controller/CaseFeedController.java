package com.tianyi.whcase.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.core.Result;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.service.CaseFeedService;
import com.tianyi.whcase.viewmodel.CaseFeedVM;

/**
 * ��������������ҵ���߼�
 * @author seeLittleGirlAgain
 *
 */
@Controller
@RequestMapping("/caseFeed")
public class CaseFeedController {
	@Autowired CaseFeedService caseFeedService;
	
	
	@RequestMapping(value = "getCaseBackAttchMents.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCaseMainInfo(
		@RequestParam(value="caseId",required = false) String id,
		HttpServletRequest request)throws Exception{
		/**
		 * 根据案件id来查询案件，返回一个案件列表
		 */	
		try {
			List<CaseAttachItem> caseFeedAttachItemList = caseFeedService.getCaseBackAttchMents(id,2);
			
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
		HttpServletRequest request)throws Exception{
		
		try {
			CaseFeedVM caseFeedAttachItemList = caseFeedService.getCaseBackMainInfo(id);
			
			Result<CaseFeedVM> result = new Result<CaseFeedVM>(caseFeedAttachItemList, true, false,
					false, "查询数据成功");
			return result.toJson();
		} catch (Exception ex) {
			Result<CaseFeedVM> result = new Result<CaseFeedVM>(null, false, false, false,
					"查询失败");
			return result.toJson();
		}
	}
	
}
