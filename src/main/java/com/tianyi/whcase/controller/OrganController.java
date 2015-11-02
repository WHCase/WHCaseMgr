package com.tianyi.whcase.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.whcase.core.Result;
import com.tianyi.whcase.dao.OrganMapper;
import com.tianyi.whcase.model.Organ;
@Controller
@RequestMapping("/Organ")
public class OrganController {
	@Autowired OrganMapper organMapper;
	
	@RequestMapping(value = "getOrganNameById.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getOrganNameById(
			@RequestParam(value="organId",required = false) int organId,
			HttpServletRequest request)throws Exception{
		Organ organ = organMapper.selectByPrimaryKey(organId);
		if(organ ==null){
			return new Result<Organ>(null,false,"未查询到对应机构").toJson();
		}
		Result<Organ> result = new Result<Organ>(organ,true,"查询成功");
		return result.toJson();
	}
}
