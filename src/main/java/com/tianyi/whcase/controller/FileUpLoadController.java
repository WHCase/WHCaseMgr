package com.tianyi.whcase.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tianyi.whcase.dao.CaseAttachItemMapper;
import com.tianyi.whcase.dao.CaseAttachMapper;
import com.tianyi.whcase.model.CaseAttach;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.service.JieShangService;
import com.tianyi.whcase.viewmodel.CaseAttachVM;
import com.tianyi.whcase.viewmodel.WorkspaceInfo;

@Scope("prototype")
@Controller
@RequestMapping("/fileUpload")
public class FileUpLoadController {
	@Autowired CaseAttachMapper caseAttachMapper;
	@Autowired CaseAttachItemMapper caseAttachItempper;
	@Autowired JieShangService jieShangService;
	
	@RequestMapping(value = "uploadFile.do", produces = "text/plain;charset=UTF-8")
	public @ResponseBody
	String uploadFile(
			@RequestParam("file") CommonsMultipartFile cmFile, // 请求参数一定要与form中的参数名对应
																// 
			@RequestParam(value="id",required = false) String id,
//			@RequestParam(value="organizationId",required = false) String organId,
			HttpServletRequest request, HttpServletResponse response) {
//		int organizationId = Integer.parseInt(organId);
		try {
			String result = "";
			
			String realPath = getClass().getResource("/").getFile().toString();
			realPath = realPath.substring(0, (realPath.length() - 16));
			
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			String relativePath = "Files/"+f.format(new Date())+"/"+Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			
			realPath = realPath+relativePath;
			
			if (!cmFile.isEmpty()) {
				String url = cmFile.getFileItem().getName();
				String name = url.substring(url.lastIndexOf("\\")+1, url.length());
				String attchType = "";
				String fileType = getFileType(name);
				
				String uriName = Calendar.getInstance().get(Calendar.MINUTE)+""+Calendar.getInstance().get(Calendar.SECOND)+"."+fileType;
				String dirUrl = realPath+"/"+ uriName;
				File filedir = new File(dirUrl);

				if(!filedir.getParentFile().exists()){
					filedir.getParentFile().mkdirs();
				}
				filedir.createNewFile();
				
				cmFile.getFileItem().write(filedir);
				
				WorkspaceInfo ws = getWorkspaceInfo();
				
				String filePath = "resource://"+ws.getNo()+"/"+relativePath+"/"+uriName;

				attchType = generateClassFromFileType(fileType);
				
				CaseAttach attach = new CaseAttach();
				UUID u = java.util.UUID.randomUUID();
				attach.setId(u.toString());
				attach.setCaseId(id);
				attach.setCreator(0);
//				attach.setOrganizationId(organizationId);
				attach.setResourceType("2");
				//设置附件相关信息
				attach.setName("派出所上传附件");
				String caseAttachId = "";
				List<CaseAttach> c = caseAttachMapper.selectByCaseId(id, "2");
				if( c==null||c.size()==0){
					caseAttachMapper.insert(attach);
					caseAttachId = u.toString();
				}else{
					caseAttachId =c.get(0).getId(); 
				}
				
				CaseAttachItem attch = new CaseAttachItem();
				UUID d = java.util.UUID.randomUUID();
				attch.setId(d.toString());
				attch.setCaseAttchId(caseAttachId);
				attch.setUri(filePath);
				attch.setItemType(attchType);
				attch.setName(name);
				
				caseAttachItempper.insert(attch);
				/*调用捷尚接口，上传附件*/
				String s = jieShangService.uploadFile(cmFile, relativePath+"/"+uriName);
				if(!"0".equals(s))
					return "File Upload Failed";
				CaseAttachVM temp = new CaseAttachVM();
				temp.SetCaseAttach(attach);
				List<CaseAttachItem> li = new ArrayList<CaseAttachItem>();
				li.add(attch);
				temp.setAttachItemList(li);
				// 调用上传附件接口**捷尚
				result = jieShangService.addCCaseMessage(id, temp);
			}
			return result;
		} catch (Exception ex) {
			return "File Upload Failed";
		}
	}
	private String getFileType(String fileName){
		String[] types = fileName.split("\\.");
		int len = types.length;
		
		return types[len-1];
	}
	
	private String generateClassFromFileType(String fileType){
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
	public  WorkspaceInfo getWorkspaceInfo(){
		WorkspaceInfo wsInfo = new WorkspaceInfo();
		try {
			String urlStr = "http://172.16.4.238:40000:40000/center/GetWorkspacesInfoList";
			//String urlStr = "http://192.168.0.201:40000/center/GetWorkspacesInfoList";
			
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true); 
			con.setRequestProperty("Content-Type", "application/xml");

            BufferedReader br = new BufferedReader(new InputStreamReader(con  
                    .getInputStream()));  
            String line = "";  
            for (line = br.readLine(); line != null; line = br.readLine()) {
                wsInfo = getWorkspaceInfoFromxml(line);
            }
            
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			
			return null;
		}
		return wsInfo;
	}
	
	private  WorkspaceInfo getWorkspaceInfoFromxml(String xml) {
		try {
			WorkspaceInfo ws = new WorkspaceInfo();
			Document document = DocumentHelper.parseText(xml);
			Element root =  document.getRootElement();
			Element performanceElement = root.element("PerformanceItem");
			ws.setName(performanceElement.attributeValue("Name"));
			ws.setNo(performanceElement.attributeValue("No"));
			
			ws.setValue(Double.parseDouble(performanceElement.attributeValue("Total")));
			ws.setTotal(Double.parseDouble(performanceElement.attributeValue("Total")));
			
			return ws;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	@Test
	public void test(){
//		System.out.println(Calendar.getInstance().get(Calendar.MINUTE));
//		System.out.println(Calendar.getInstance().get(Calendar.MINUTE)+""+Calendar.getInstance().get(Calendar.SECOND));
		
		System.out.println(this.getFileType("192.168.0.105.xlsx"));
	}
}
