package com.tianyi.whcase.controller;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.model.CaseGroup;
import com.tianyi.whcase.service.CaseGroupService;
 
/**
 * 关联串并案控制器业务逻辑
 * @author seeLittleGirlAgain
 *
 */
@Controller
@RequestMapping("/caseGroup")
public class CaseGroupController {
	
	@Autowired
	private CaseGroupService caseGroupService;

	@RequestMapping(value = "getCaseRelative.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCasePushList(
		@RequestParam(value="caseId",required = false) String id,
		HttpServletRequest request)throws Exception{
		
		ListResult<CaseGroup> caseGroupList= caseGroupService.getCaseRelativeByCaseId(id);
		
		if(caseGroupList ==null){
			return null;
		}
		return caseGroupList.toJson();
	}
}
