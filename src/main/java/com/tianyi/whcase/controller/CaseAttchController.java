package com.tianyi.whcase.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
	

	
	@ResponseBody
	@RequestMapping(value = "downloadFile.do", produces = "application/json;charset=UTF-8")
	public void downloadFile(
			@RequestParam(value="url",required=true)String url,
			HttpServletRequest req,HttpServletResponse response
			){
        try {
        	String path = getClass().getResource("/").getFile().toString();
        	path = path.substring(0, (path.length() - 16));
        	String[] basePath = url.split("/Files");
        	int ment = basePath.length;
        	path = path + "Files" + basePath[ment-1];
        	response.reset();
        	String[] fileName = basePath[1].split("/");
        	String filename = fileName[fileName.length-1];
        	response.setContentType("APPLICATION/OCTET-STREAM; charset=UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=\""
			       + new String(filename.getBytes("GB2312"), "ISO-8859-1")
			       + "\"");
			FileInputStream inStream = new FileInputStream(path);
			byte[] b = new byte[100];
			int len;
			while ((len = inStream.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
			inStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 删除附件下指定文件
	 * @param caseId
	 * @param caseAttachItemId
	 * @param request
	 * @return
	 * @throws Exception
	 */
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
				String Type = generateClassFromFileType(caseAttachItem.getUri());
				caseAttachItem.setItemType(Type);
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
		String fileType=uri.substring(uri.lastIndexOf(".")+1);
 		if(fileType.toLowerCase().equals("png")||fileType.equals("jpg")||fileType.equals("jpeg")||fileType.equals("gif")||fileType.equals("ico")||fileType.equals("bmp")){
			return "Image"; 
		}else if(fileType.equals("doc")||fileType.equals("docx")){
			return "document"; 
		}else if(fileType.equals("ppt")||fileType.equals("pptx")){
			return "document"; 
		}else if(fileType.equals("xls")||fileType.equals("xlsx")){
			return "document"; 
		}else if(fileType.equals("txt")||fileType.equals("log")){
			return "document"; 
		}else if(fileType.toLowerCase().equals("3gp")||fileType.equals("avi")||fileType.equals("wma")||fileType.equals("rmvb")||fileType.equals("rm")||fileType.equals("flash")||fileType.equals("mp4")||fileType.equals("mid")){
			return "Video"; 
		}else if(fileType.toUpperCase().equals("MP3")||fileType.equals("WMA")||fileType.equals("WAV")||fileType.equals("ASF")||fileType.equals("AAC")||fileType.equals("Mp3Pro")||fileType.equals("VQF")||fileType.equals("FLAC")||fileType.equals("APE")||fileType.equals("MID")||fileType.equals("OGG")){
			return "Audio"; 
		}else{
			return "Unknown"; 
		}
	}
	

}
