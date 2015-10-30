package com.tianyi.whcase.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String result = "";
			
			String realPath = getClass().getResource("/").getFile().toString();
			realPath = realPath.substring(0, (realPath.length() - 16));
			
			realPath = realPath+"tempFile/upload/"+id;
			
			if (!cmFile.isEmpty()) {
				String name = cmFile.getFileItem().getName();
				
				String dirUrl = realPath + id+"/"+ name;
				File filedir = new File(dirUrl);

				if(!filedir.getParentFile().exists()){
					filedir.getParentFile().mkdirs();
				}
				filedir.createNewFile();
				
				cmFile.getFileItem().write(filedir);
				
				String attchType = "";
				String[] types = name.split("\\.");
				
				String filePath = "tempFile/upload/"+id+"/"+name;
				
				if(types[1].equals("png")||types[1].equals("jpg")||types[1].equals("jpeg")||types[1].equals("gif")||types[1].equals("ico")||types[1].equals("bmp")){
					attchType = "图片文件"; 
				}else if(types[1].equals("doc")||types[1].equals("docx")){
					attchType = "文档文件"; 
				}else if(types[1].equals("ppt")||types[1].equals("pptx")){
					attchType = "演示文件"; 
				}else if(types[1].equals("xls")||types[1].equals("xlsx")){
					attchType = "Excel文件"; 
				}else if(types[1].equals("txt")){
					attchType = "文本文件"; 
				}else{
					attchType = "一般文件"; 
				}
				CaseAttach attach = new CaseAttach();
				UUID u = java.util.UUID.randomUUID();
				attach.setId(u.toString());
				attach.setCaseId(id);
				attach.setResourceType("2");
				//设置附件相关信息
				attach.setName("派出所上传附件");
				String caseAttachId = "";
				CaseAttach c = caseAttachMapper.selectByCaseId(id, "2");
				if( c==null){
					caseAttachMapper.insert(attach);
					caseAttachId = u.toString();
				}else{
					caseAttachId =c.getId(); 
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
				SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
				jieShangService.uploadFile(cmFile, "File\\"+f.format(new Date())+"\\"+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+"\\"+id+"\\"+name);
				CaseAttachVM temp = new CaseAttachVM();
				temp.SetCaseAttach(attach);
				List<CaseAttachItem> li = new ArrayList<CaseAttachItem>();
				li.add(attch);
				temp.setAttachItemList(li);
				jieShangService.addCCaseMessage(id, temp);
			}
			return result;
		} catch (Exception ex) {
			return "File Upload Failed";
		}
	}
}
