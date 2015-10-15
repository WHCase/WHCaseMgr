package com.tianyi.whcase.controller;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			String realPath = getClass().getResource("/").getFile().toString();
			String result = "";
			realPath = realPath.substring(0, (realPath.length() - 16));
			realPath = realPath + "uploadFile";
			File sF = new File(realPath);
			if (!sF.exists()) {
				sF.mkdir();
			}
			realPath = realPath + "/" + "Attchfile";
			File iconF = new File(realPath);
			if (!iconF.exists()) {
				iconF.mkdir();
			}
			realPath = realPath + "/";

			if (!cmFile.isEmpty()) {
				String name = cmFile.getFileItem().getName();
				
				String dirUrl = realPath + id;
				File filedir = new File(dirUrl);

				if (!filedir.exists()) {
					filedir.mkdir();
				}
				dirUrl = dirUrl + "/";
				String iconUrl = dirUrl + name;
				File existFile = new File(iconUrl);
				 
				String attchType = "";
				String[] types = name.split("\\.");
				
				String temp = "E:/data/uploadFile/"+name;
				File tempFile = new File(temp);
				//cmFile.getFileItem().write(existFile);
				cmFile.getFileItem().write(tempFile);

				String filePath = "uploadFile/Attchfile/"+id+"/"+name;
				
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
				jieShangService.uploadFile(cmFile, "Attchfile\\"+id+"\\");
				
			}
			return result;
		} catch (Exception ex) {
			return "File Upload Failed";
		}
	}
}
