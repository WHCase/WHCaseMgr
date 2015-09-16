package com.tianyi.whcase.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/xml")
public class XMLController {
	
	@RequestMapping(value = "receiveXML.do", produces = "application/x-www-form-urlencoded")
	public @ResponseBody String receiveXML(
			@RequestParam(value = "doc", required = true) String doc,
		HttpServletRequest request)throws Exception{
	
		return "";
	}
}
