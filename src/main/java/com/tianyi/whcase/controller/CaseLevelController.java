package com.tianyi.whcase.controller;

import javax.servlet.http.HttpServletRequest;


import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.whcase.core.Result;
import com.tianyi.whcase.model.CaseLevel;
import com.tianyi.whcase.service.CaseLevelService;
import com.tianyi.whcase.viewmodel.caseOrganVM;

@Controller
@RequestMapping("/CaseLevel")
public class CaseLevelController {
	@Autowired CaseLevelService caseLevelService;
	
	@RequestMapping(value = "setCaseLevel.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String pushCaseToOrgans(
			@RequestParam(value="caseLevel",required = false) String caseLevel,
			HttpServletRequest request)throws Exception{
		
		JSONObject jObj = JSONObject.fromObject(caseLevel);
		CaseLevel caselevel = (CaseLevel) JSONObject.toBean(jObj,CaseLevel.class);
		try{
			String temp = caseLevelService.insertCaseLevel(caselevel.getCaseId(),caselevel.getCaseLevel());
			if(!temp.isEmpty()){
				return new Result<caseOrganVM>(null, false, false, false,
						temp).toJson();
			}
			
			return new Result<caseOrganVM>(null, true, true, true,
					"推送成功").toJson();
		}catch(Exception ex){
			Result<caseOrganVM> result = new Result<caseOrganVM>(null, false, false, false,
					"推送失败");
			return result.toJson();
		}
		
	}
}
