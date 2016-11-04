package com.tianyi.whcase.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.whcase.core.Result;
import com.tianyi.whcase.service.JieShangService;
import com.tianyi.whcase.util.DbConfig;

@Controller
@RequestMapping("/JieShang")
public class JieShangInterfaceController {
	@Autowired JieShangService jieShangService;
	/**
	 * 更新案件
	 * @param caseInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateCCase.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String updateCCase(
		@RequestParam(value="caseInfo",required = false) String caseInfo,
		HttpServletRequest request)throws Exception{
		
		try {
			//String s = DbConfig.getInstance().getIpUrl();
			//String urlStr = "http://223.223.183.242:40000/center/UpdateCCase";
			String urlStr = "http://192.168.16.74:40000/center/UpdateCCase";
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true); 
			con.setRequestProperty("Content-Type", "application/xml");
			OutputStreamWriter out = new OutputStreamWriter(con  
                    .getOutputStream()); 
			// 测试写的死的数据，应该从数据库获取
            String xmlInfo = getXmlInfo();
            System.out.println("urlStr=" + urlStr);  
            System.out.println("xmlInfo=" + xmlInfo);  
            out.write(new String(xmlInfo.getBytes("ISO-8859-1")));  
            out.flush();  
            out.close();  
            BufferedReader br = new BufferedReader(new InputStreamReader(con  
                    .getInputStream()));  
            String line = "";  
            for (line = br.readLine(); line != null; line = br.readLine()) {  
                System.out.println("\n\r 返回结果："+line);  
            }  
		} catch (Exception ex) {

		}
		return"";
	}
	private String getXmlInfo() {
		// TODO Auto-generated method stub
		 StringBuilder sb = new StringBuilder();  
	        sb.append("<CCase ID=\"df40d455-f1a8-0d7d-b886-bd6305050505\"");  
	        sb.append(" Name=\"有视频图片的案件\" ");  
	        sb.append(" Creator=\"1\" ");  
	        sb.append(" CreateTime=\"2015-08-19T08:39:59.763Z\"  ");  
	        sb.append(" Code=\"xxxxxx\"  ");  
	        sb.append(" Categories=\"dc440454-34dc-10c6-78fc-aa4e05050505\" StartTime=\"2015-08-19T16:31:34\" ");  
	        sb.append(" Summary=\"xxxx\" ");  
	        sb.append(" Status=\"Handling\" IsRegister=\"false\" UserGroupId=\"0\" Level=\"0\" ");  
	        sb.append(" Longitude=\"9999\" Latitude=\"9999\"  OrganizationID=\"8\" ");  
	        sb.append(" DetectedUnit=\"-1\"> ");  
	        sb.append(" </CCase> ");    
	        return sb.toString();  
	}
	/**
	 * 获取指定案件信息
	 * @param caseID
	 * @param messageType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getCase.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCase(
		@RequestParam(value="caseID",required = false) String caseID,
		@RequestParam(value="messageType",required = false) Integer messageType,
		HttpServletRequest request)throws Exception{
		return"";
	}
	
	/**
	 * 获取案件附件
	 * @param caseID
	 * @param messageType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getCaseMessages.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getCaseMessages(
		@RequestParam(value="caseID",required = false) String caseID,
		@RequestParam(value="messageType",required = false) Integer messageType,
		HttpServletRequest request)throws Exception{
		return"";
	}
	/**
	 * 	获取流媒体服务列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getAllMsSvrStatus.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getAllMsSvrStatus(
		HttpServletRequest request)throws Exception{
		return"";
	}
	/**
	 * 	获取系统可用的工作目录列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getWorkspaceInfo.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getWorkspaceInfo(
		HttpServletRequest request)throws Exception{
		return"";
	}
	/**
	 * 案件附件中文件下载
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "DownloadAttachFiles.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String DownloadAttachFiles(
		HttpServletRequest request)throws Exception{
		return"";
	}
	/**
	 * 上传附件调用捷尚接口获取案件类型，更新最新的案件类型
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getGetDictionary.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getGetDictionary(
		HttpServletRequest request)throws Exception{
		String temp = jieShangService.GetDictionary();
		if(temp.equals("")){
			return new Result<String>(null,true,"获取案件类型成功").toJson();
		}else{
			return new Result<String>(null,false,temp).toJson();
		}
		
	}
	/**
	 * 获取所有组织机构
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "GetAllOrganizations.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String GetAllOrganizations(
		HttpServletRequest request)throws Exception{
		String temp = jieShangService.GetAllOrganizations();
		if(temp.equals("")){
			return new Result<String>(null,true,"获取案件类型成功").toJson();
		}else{
			return new Result<String>(null,false,temp).toJson();
		}
	}
	/**
	 * 上传附件
	 * @param caseInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateCaseAttach.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String updateCaseAttach(
		@RequestParam(value="caseInfo",required = false) String caseInfo,
		@RequestParam(value="file",required = false) File file,
		HttpServletRequest request)throws Exception{
			
		return "";	
	}
	/**
	 * 删除附件
	 * @param caseId
	 * @param itemId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteCCaseMessage.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String DeleteCCaseMessage(
		@RequestParam(value="caseId",required = false) String caseId,
		@RequestParam(value="itemId",required = false) String itemId,
		HttpServletRequest request)throws Exception{						
		try {
			//String s = DbConfig.getInstance().getIpUrl();
			//String urlStr = "http://223.223.183.242:40000/center/UpdateCCase";
			String urlStr = "http://192.168.16.74:40000/center/DeleteCCaseMessage?caseID="+caseId+"&itemId="+itemId;
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true); 
			con.setRequestProperty("Content-Type", "application/xml");
			OutputStreamWriter out = new OutputStreamWriter(con  
	                .getOutputStream()); 
			// 测试写的死的数据，应该从数据库获取
//            String xmlInfo = getXmlInfo();
//            System.out.println("urlStr=" + urlStr);  
//            System.out.println("xmlInfo=" + xmlInfo);  
//            out.write(new String(xmlInfo.getBytes("ISO-8859-1")));  
            out.flush();  
            out.close();  
            BufferedReader br = new BufferedReader(new InputStreamReader(con  
                    .getInputStream()));  
            String line = "";  
            for (line = br.readLine(); line != null; line = br.readLine()) {  
                System.out.println("\n\r 返回结果："+line);  
            }  
		} catch (Exception ex) {

		}
		return "";
	}
}
