package com.tianyi.whcase.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.service.CaseAttchService;
import com.tianyi.whcase.viewmodel.CaseAttachVM;


/**
 * �����������������
 * @author seeLittleGirlAgain
 *
 */

@Controller
@RequestMapping("/caseAttch")
public class CaseAttchController {
	@Autowired CaseAttchService caseAttchService;
	
	@RequestMapping(value = "getCaseAttchMents.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCaseAttchMents(
		@RequestParam(value="caseId",required = false) String id,
		HttpServletRequest request)throws Exception{
		
		ListResult<CaseAttachItem> caseGroupList= caseAttchService.getCaseRelativeByCaseId(id,1);
		
		if(caseGroupList ==null){
			return null;
		}
		return caseGroupList.toJson();
	}
	@RequestMapping(value = "AddCaseAttach.do", produces = "application/xml;charset=UTF-8")
	public @ResponseBody String AddCaseAttach(
			@RequestParam(value="caseId",required = false) String id,
			@RequestBody String requestBody,			
			HttpServletRequest request)throws Exception{
		Document document = DocumentHelper.parseText(requestBody);
		
		List<CaseAttachVM> caseAttachVMList =getAttachVMByDocument(document,id);
		int temp = 0;
		for(int i = 0;i<caseAttachVMList.size();i++){
			temp = caseAttchService.AddAttachVM(caseAttachVMList.get(i));
		}
		return getReturnXml(temp);
	}
	private List<CaseAttachVM> getAttachVMByDocument(Document document,String id) {
		List<CaseAttachVM> caseAttachVMList =new ArrayList<CaseAttachVM>();
		Element root =  document.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> messageItemList = root.elements();
		
		if(messageItemList==null||messageItemList.size()==0){
			return null;
		}
		for(int i = 0;i<messageItemList.size();i++){
			CaseAttachVM caseAttachVM = new CaseAttachVM();
			caseAttachVM.setId(messageItemList.get(i).attributeValue("ID"));
			caseAttachVM.setCaseId(id);
			caseAttachVM.setCreator(Integer.parseInt(messageItemList.get(i).attributeValue("Creator")));
			caseAttachVM.setDescription(messageItemList.get(i).attributeValue("Description"));
			caseAttachVM.setMessageType(messageItemList.get(i).attributeValue("MessageType"));
			caseAttachVM.setName(messageItemList.get(i).attributeValue("Name"));
			@SuppressWarnings("unchecked")
			List<Element> itemList = messageItemList.get(i).element("Attachments").elements();
			
			if(itemList==null||itemList.size()==0){
				return null;
			}
			List<CaseAttachItem> caseItemList = new ArrayList<CaseAttachItem>();
			for(int j = 0;j<itemList.size();j++){
				CaseAttachItem caseAttachItem = new CaseAttachItem();
				caseAttachItem.setId(itemList.get(j).attributeValue("ID"));
				caseAttachItem.setItemType(itemList.get(j).attributeValue("Type"));
				caseAttachItem.setName(itemList.get(j).attributeValue("Name"));
				caseAttachItem.setUri(itemList.get(j).attributeValue("Uri"));
				caseAttachItem.setCaseAttchId(messageItemList.get(i).attributeValue("ID"));
				caseItemList.add(caseAttachItem);
			}
			caseAttachVM.setAttachItemList(caseItemList);
			caseAttachVMList.add(caseAttachVM);
		}
		return caseAttachVMList;
	}
	private String getReturnXml(int returnNum){
		StringBuilder sb = new StringBuilder();  
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");  
        sb.append("<int>"+returnNum+"</int>");  
          
        return sb.toString();  
	}
	
}
