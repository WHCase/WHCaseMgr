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

import com.tianyi.whcase.core.Constants;
import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.core.Result;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.service.CaseAttchService;
import com.tianyi.whcase.service.JieShangService;
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
		
		ListResult<CaseAttachItem> caseGroupList= caseAttchService.getCaseRelativeByCaseId(id,"1");
		
		if(caseGroupList ==null){
			return null;
		}
		return caseGroupList.toJson();
	}
	@RequestMapping(value = "getUpCaseAttchMents.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getUpCaseAttchMents(
		@RequestParam(value="caseId",required = false) String id,
		HttpServletRequest request)throws Exception{
		
		ListResult<CaseAttachItem> caseGroupList= caseAttchService.getCaseRelativeByCaseId(id,"2");
		
		if(caseGroupList ==null){
			return null;
		}
		return caseGroupList.toJson();
	}
	@RequestMapping(value = "deleteCaseAttach.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String deleteCaseAttach(
			@RequestParam(value="caseId",required = false) String caseId,
		@RequestParam(value="caseattachId",required = false) String caseattachId,
		HttpServletRequest request)throws Exception{
		String temp = caseAttchService.deleteCaseAttach(caseId,caseattachId);
		if(temp ==""){
			Result<CaseAttachItem> result = new Result<CaseAttachItem>(null, true, "删除成功");
			return result.toJson();
		}else{
			Result<CaseAttachItem> result = new Result<CaseAttachItem>(null, false, temp);
			return result.toJson();
		}
		
	}
	/**
	 * (优创接口)当用户添加附件，捷尚通过调用这个接口来通知优创
	 * @param id
	 * @param requestBody
	 * @param request
	 * @return
	 * @throws Exception
	 */
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
			/*'1'未自身附件，'2'为添加附件*/
			caseAttachVM.setResourceType("1");
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
