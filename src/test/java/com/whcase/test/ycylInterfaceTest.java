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

}
