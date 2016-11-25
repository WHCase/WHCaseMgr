package com.whcase.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.service.CaseAttchService;
import com.tianyi.whcase.viewmodel.CaseAttachVM;

public class ycylInterfaceTest extends BaseTest {

	public static String IP = "121.199.8.150";
	public static int port = 8080;
	public static String scope1 = "case";
	public static String scope2 = "caseAttch";
	
	@Autowired CaseAttchService caseAttchService;	
	
	@Test
	public void AddAttach(){
		CaseAttachVM  attach = new CaseAttachVM();
		attach.setId("ac02dd55-acc6-2410-40bb-540705050505");
		attach.setName("test");
		attach.setCreator(1);
		attach.setDescription("测试新增");
		attach.setCreateTime(new Date());
		attach.setMessageType("4");
		attach.setCaseId("b13e3558-92f1-1d84-241a-e50d05050505");
		CaseAttachItem item = new CaseAttachItem();
		List<CaseAttachItem> list = new ArrayList<CaseAttachItem>();
		item.setId("b70adc55-acd6-2410-40bb-540905050505");
		item.setCaseAttchId(attach.getId());
		item.setName("360壁纸 8013.jpg");
		item.setItemType("Image");
		item.setUri("resource://CaseCenter_ws1/Files/20150825/14/1.jpg");
		list.add(item);
		attach.setAttachItemList(list);
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		caseAttchService.AddAttachVM(attach,request,response);
	}
	
	@Test
	public void DeleteAttach(){
		
	}
	
	@Test
	public void DeleteCase(){
		
	}
	
	@Test
	public void UpdateCase(){
		
	}
	
	@Test
	public void AddCase(){
		
	}
	
	
	/**
	 * 文件类型
	 * @param uri
	 * @return
	 */
	private String generateClassFromFileType(String uri){
		if(uri == null || "".equals(uri))
			return "";
//		String[] names = uri.split(".");
//		String fileType = names[names.length - 1];
		String fileType=uri.substring(uri.lastIndexOf(".")+1);
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
	public void TestType(){
		String uri1 = "resource://CaseCenter_ws1/Files/20161124/16/c0a73658-cfa6-78e4-40ae-dd8005050505.mp4";
		String uri2 = "resource://CaseCenter_ws1/Files/20161121/13/788b3258-cfa6-a0e4-0ce9-037705050505.jpg";
		
		String type1 = generateClassFromFileType(uri1);
		String type2 = generateClassFromFileType(uri2);
		
		System.out.println("文件1:"+uri1);
		System.out.println("类型:"+type1);
		System.out.println("文件2:"+uri2);
		System.out.println("类型:"+type2);		
	}
	

}
