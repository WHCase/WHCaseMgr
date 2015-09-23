package com.tianyi.whcase.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.service.CaseService;



@Controller
@RequestMapping("/center")
public class XMLController {
	@Autowired CaseService caseService;
	
	@RequestMapping(value = "UpdateCCase.do")
	public @ResponseBody String receiveXML(
			@RequestBody String requestBody,
		HttpServletRequest request)throws Exception{
		Document document = DocumentHelper.parseText(requestBody);
		/*获取xml进行解析，获取相关的数据，保存到数据库*/
		Element root =  document.getRootElement();
		
		Case c = new Case();
		c.setId(root.attributeValue("id"));
		c.setName(root.attributeValue("Name"));
		c.setCreator(Integer.parseInt(root.attributeValue("Creator")));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    Date date = sdf.parse(root.attributeValue("CreateTime")); 
		c.setCreateTime(date);
		
		c.setCode(root.attributeValue("Code"));
		c.setCategoriesId(root.attributeValue("Categories"));
		
		Date date1 = sdf.parse(root.attributeValue("StartTime"));
		c.setStartTime(date1);
		c.setSummary(root.attributeValue("Summary"));
		c.setStatus(root.attributeValue("Status"));
		
		c.setIsregister(false);
		c.setLevel(root.attributeValue("Level"));
		c.setLongitude(root.attributeValue("Longitude"));
		c.setLatitude(root.attributeValue("Latitude"));
		
		c.setOrganizationId(Integer.parseInt(root.attributeValue("OrganizationID")));
		c.setDetectedunitId(Integer.parseInt(root.attributeValue("DetectedUnit")));
		
		String temp = caseService.insert(c);
		if(!temp.isEmpty()){
			return temp;
		}else{
			return "案件插入成功";
		}
	}
}
