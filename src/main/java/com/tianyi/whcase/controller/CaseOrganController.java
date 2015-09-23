package com.tianyi.whcase.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.whcase.core.Result;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.service.CaseOrganService;
import com.tianyi.whcase.viewmodel.CaseVM;
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
	
}
