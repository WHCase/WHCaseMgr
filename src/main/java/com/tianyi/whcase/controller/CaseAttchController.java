package com.tianyi.whcase.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
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
	@Autowired JieShangService jieShangService;
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
	@RequestMapping(value = "downloadCaseAttch.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String downloadCaseAttch(
			@RequestParam(value = "url", required = false) String url, 
			HttpServletRequest request,HttpServletResponse response ){
		String result = null;
		try {
			/*调取洁尚接口*/
			result = jieShangService.downloadAttachFiles(url, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "deleteCaseAttach.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String deleteCaseAttach(
			@RequestParam(value="caseId",required = false) String caseId,
		@RequestParam(value="caseAttachItemId",required = false) String caseAttachItemId,
		HttpServletRequest request)throws Exception{
		String temp = caseAttchService.deleteCaseAttach(caseId,caseAttachItemId);
		if("0".equals(temp)){
			Result<CaseAttachItem> result = new Result<CaseAttachItem>(null, true, "删除成功");
			return result.toJson();
		}else{
			Result<CaseAttachItem> result = new Result<CaseAttachItem>(null, false, temp);
			return result.toJson();
		}
		
	}
	public  void main(String[] args) {
		String temp = caseAttchService.deleteCaseAttach("bb2a2458-92f1-0984-9021-40d005050505","ca624d2c-abf1-480b-9e82-3698fdaedd00");
	    System.out.println("------------"+temp);
	}
	
	/**
	 * (优创接口)当用户删除附件，捷尚通过调用这个接口来通知优创
	 * @param caseId
	 * @param caseattachId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "DeleteCaseAttachItem.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String DeleteCaseAttach(
			@RequestParam(value="caseId",required = false) String caseId,
		@RequestParam(value="caseAttachItemId",required = false) String caseAttachItemId,
		HttpServletRequest request)throws Exception{
		int temp = -1;
		try {
			temp = caseAttchService.deleteLocalAttach(caseId,caseAttachItemId);
		} catch (Exception e) {			
			e.printStackTrace();
			System.out.println("捷尚删除附件错误");
		}
		return getReturnXml(temp);
		
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
			HttpServletRequest request,
			HttpServletResponse response
			)throws Exception{
		Document document = DocumentHelper.parseText(requestBody);
		
		CaseAttachVM caseAttachVM =getAttachVMByDocument(document,id);
		int temp = -1;
		temp = caseAttchService.AddAttachVM(caseAttachVM,request,response);

		return getReturnXml(temp);
	}
	private CaseAttachVM getAttachVMByDocument(Document document,String id) {
		Element root =  document.getRootElement();
		
		if(root==null){
			return null;
		}
			CaseAttachVM caseAttachVM = new CaseAttachVM();
			caseAttachVM.setId(root.attributeValue("ID"));
			caseAttachVM.setCaseId(id);
			caseAttachVM.setCreator(Integer.parseInt(root.attributeValue("Creator")));
			caseAttachVM.setDescription(root.attributeValue("Description"));
			caseAttachVM.setMessageType(root.attributeValue("MessageType"));
			caseAttachVM.setName(root.attributeValue("Name"));
			/*'1'未自身附件，'2'为添加附件*/
			caseAttachVM.setResourceType("1");
			@SuppressWarnings("unchecked")
			List<Element> itemList = root.element("Attachments").elements();
			
			if(itemList==null||itemList.size()==0){
				caseAttachVM.setAttachItemList(null);
				return caseAttachVM;
			}
			List<CaseAttachItem> caseItemList = new ArrayList<CaseAttachItem>();
			for(int j = 0;j<itemList.size();j++){
				CaseAttachItem caseAttachItem = new CaseAttachItem();
				caseAttachItem.setId(itemList.get(j).attributeValue("ID"));				
				caseAttachItem.setName(itemList.get(j).attributeValue("Name"));
				caseAttachItem.setUri(itemList.get(j).attributeValue("Uri"));
				//caseAttachItem.setItemType(generateClassFromFileType(caseAttachItem.getUri()));
				caseAttachItem.setCaseAttchId(root.attributeValue("ID"));
				caseItemList.add(caseAttachItem);
			}
			caseAttachVM.setAttachItemList(caseItemList);

		return caseAttachVM;
	}
	private String getReturnXml(int returnNum){
		StringBuilder sb = new StringBuilder();  
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");  
        sb.append("<int>"+returnNum+"</int>");  
          
        return sb.toString();  
	}
	
	/**
	 * 文件类型
	 * @param uri
	 * @return
	 */
	private String generateClassFromFileType(String uri){
		if(uri == null || "".equals(uri))
			return "";
		String[] names = uri.split(".");
		String fileType = names[names.length - 1];
 		if(fileType.toLowerCase().equals("png")||fileType.equals("jpg")||fileType.equals("jpeg")||fileType.equals("gif")||fileType.equals("ico")||fileType.equals("bmp")){
			return "Image"; 
		}else if(fileType.equals("doc")||fileType.equals("docx")){
			return "document"; 
		}else if(fileType.equals("ppt")||fileType.equals("pptx")){
			return "document"; 
		}else if(fileType.equals("xls")||fileType.equals("xlsx")){
			return "document"; 
		}else if(fileType.equals("txt")){
			return "document"; 
		}else if(fileType.toLowerCase().equals("3gp")||fileType.equals("avi")||fileType.equals("wma")||fileType.equals("rmvb")||fileType.equals("rm")||fileType.equals("flash")||fileType.equals("mp4")||fileType.equals("mid")){
			return "Video"; 
		}else if(fileType.toUpperCase().equals("MP3")||fileType.equals("WMA")||fileType.equals("WAV")||fileType.equals("ASF")||fileType.equals("AAC")||fileType.equals("Mp3Pro")||fileType.equals("VQF")||fileType.equals("FLAC")||fileType.equals("APE")||fileType.equals("MID")||fileType.equals("OGG")){
			return "Audio"; 
		}else{
			return "Unknown"; 
		}
	}
	
	@Test
	public void TestAddCaseAttach(){
//		String caseId = "bf5d3258-92f1-0884-9455-b9b905050505";
//		String caseAttachId = "618b3258-cfa6-a0e4-0ce9-037505050505";
//		try{
//			int temp = caseAttchService.deleteLocalAttach(caseId,caseAttachId);
//			System.out.print(getReturnXml(temp));
//		}catch(Exception e){
//			e.printStackTrace();
//		}		
	}
	
	@Test
	public void TestDeleteLocalAttach(){
//		String caseId = "bf5d3258-92f1-0884-9455-b9b905050505";
//		String caseAttachId = "618b3258-cfa6-a0e4-0ce9-037505050505";
//		try{
//			int temp = 0;
//			temp = caseAttchService.deleteLocalAttach(caseId,caseAttachId);
//			System.out.print(getReturnXml(temp));
//		}catch(Exception e){
//			e.printStackTrace();
//		}		
	}
}
