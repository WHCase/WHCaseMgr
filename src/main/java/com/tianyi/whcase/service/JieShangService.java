package com.tianyi.whcase.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.viewmodel.CaseAttachVM;
import com.tianyi.whcase.viewmodel.CaseVM;
import com.tianyi.whcase.viewmodel.MediaSvrStatus;
import com.tianyi.whcase.viewmodel.WorkspaceInfo;

@Service
public class JieShangService {
	/**
	 * 更新案件
	 * @param caseInfo
	 * @return
	 */
	public String updateCCase(CaseVM caseInfo){		
		try {
			String urlStr = "http://223.223.183.242:40000/center/UpdateCCase";
			//String urlStr = "http://192.168.0.201:40000/center/UpdateCCase";
			
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true); 
			con.setRequestProperty("Content-Type", "application/xml");
			OutputStreamWriter out = new OutputStreamWriter(con  
                    .getOutputStream());      
            String xmlInfo = getXmlInfoForCase(caseInfo);
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
			System.out.println(ex.getMessage());
		}
		return"";
	}
	private String getXmlInfoForCase(CaseVM caseInfo) {
		 StringBuilder sb = new StringBuilder();
		 
	 	sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
	 	if(caseInfo.getId()!=null){
	 		sb.append("<CCase ID=\""+caseInfo.getId()+"\" ");  
	 	}
        if(caseInfo.getName()!=null){
        	sb.append("Name=\""+caseInfo.getName()+"\" ");
        }
        if(caseInfo.getCreator()!=null){
        	sb.append("Creator=\""+caseInfo.getCreator()+"\" ");
        }
        if(caseInfo.getCreateTime()!=null){
        	 java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
             String s = format1.format(caseInfo.getCreateTime());
        	sb.append("CreateTime=\""+s+"\" ");
        }
        if(caseInfo.getStartTime()!=null){
        	java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String t = format1.format(caseInfo.getStartTime());
        	sb.append("StartTime=\""+t+"\" ");
        }
        if(caseInfo.getCode()!=null){
        	sb.append("Code=\""+caseInfo.getCode()+"\" "); 
        }
        if(caseInfo.getCategoriesId()!=null){
        	sb.append("Categories=\""+caseInfo.getCategoriesId()+"\" ");
        }
        if(caseInfo.getSummary()!=null){
        	sb.append("Summary=\""+caseInfo.getSummary()+"\" ");
        }
        if(caseInfo.getStatus()!=null){
        	sb.append("Status=\""+caseInfo.getStatus()+"\" ");
        }
        
        if(caseInfo.getIsregister()!=null){
        	sb.append("IsRegister=\""+caseInfo.getIsregister().toString()+"\" ");
        }
        sb.append("UserGroupId=\"0\" ");
        
        if(caseInfo.getLevel()!=null){
        	sb.append("Level=\""+caseInfo.getLevel()+"\" ");
        }
        if(caseInfo.getLongitude()!=null){
        	sb.append("Longitude=\""+caseInfo.getLongitude()+"\" ");
        }
        if(caseInfo.getLatitude()!=null){
        	sb.append("Latitude=\""+caseInfo.getLatitude()+"\" ");
        }
        if(caseInfo.getOrganizationId()!=null){
        	sb.append("OrganizationID=\""+caseInfo.getOrganizationId()+"\" ");
        }
        if(caseInfo.getDetectedunitId()!=null){
        	sb.append("DetectedUnit=\""+caseInfo.getDetectedunitId()+"\"");
        }
          
        sb.append("></CCase>");    
	    return sb.toString();  
	}
	
	public String getCaseMessages(String caseID,Integer messageType){
		return"";
	}

	public MediaSvrStatus getAllMsSvrStatus(){
		MediaSvrStatus s = new MediaSvrStatus(); 
		try {
			String urlStr = "http://223.223.183.242:40000/center/GetAllMsSvrStatus";
			//String urlStr = "http://192.168.0.201:40000/center/GetAllMsSvrStatus";
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true); 
			con.setRequestProperty("Content-Type", "application/xml");

            BufferedReader br = new BufferedReader(new InputStreamReader(con  
                    .getInputStream())); 
            
            String line = "";  
            for (line = br.readLine(); line != null; line = br.readLine()) {
            	System.out.println(line);
                s = getMediaSvrStatusInfoFromxml(line);
            }
            
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
		return s;
	}

	private MediaSvrStatus getMediaSvrStatusInfoFromxml(String xml) {
		try {
			MediaSvrStatus mss = new MediaSvrStatus();
			Document document = DocumentHelper.parseText(xml);
			Element root =  document.getRootElement();
			Element mediaSvrStatusElement = root.element("MediaSvrStatus");
			mss.setSvrNo(mediaSvrStatusElement.attributeValue("SvrNo"));
			mss.setSvrName(mediaSvrStatusElement.attributeValue("SvrName"));
			mss.setStatusCode(mediaSvrStatusElement.attributeValue("StatusCode"));
			mss.setCpu(mediaSvrStatusElement.attributeValue("CPU"));
			mss.setMemory(mediaSvrStatusElement.attributeValue("Memory"));
			mss.setFreeMemory(mediaSvrStatusElement.attributeValue("FreeMemory"));
			
			Element configElement = mediaSvrStatusElement.element("Config");
			mss.setServerAddress(configElement.attributeValue("ServerAddress"));
			mss.setPort(Integer.parseInt(configElement.attributeValue("Port")));
			mss.setVideoPort(Integer.parseInt(configElement.attributeValue("VideoPort")));
			mss.setNginPort(Integer.parseInt(configElement.attributeValue("NginxPort")));
			
			return mss;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public WorkspaceInfo getWorkspaceInfo(){
		WorkspaceInfo wsInfo = new WorkspaceInfo();
		try {
			String urlStr = "http://223.223.183.242:40000/center/GetWorkspacesInfoList";
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
	
	private WorkspaceInfo getWorkspaceInfoFromxml(String xml) {
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
	public String downloadAttachFiles(String uri){
		uri = uri.replace("resource://", "");
		MediaSvrStatus mss = getAllMsSvrStatus();
		String ip = mss.getServerAddress();
		int port = mss.getPort();
		WorkspaceInfo ws = getWorkspaceInfo();
		try {
			File file = new File("/tempFile/download/"+uri);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			String urlStr = "http://"+ip+":"+port+"/"+ws.getNo()+"/"+uri;
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true); 
			con.setRequestProperty("Content-Type", "application/xml");
 
            BufferedReader br = new BufferedReader(new InputStreamReader(con  
                    .getInputStream()));  
            String line = "";  
            for (line = br.readLine(); line != null; line = br.readLine()) {
                bw.write(line);
            }
            bw.close();
            
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return ex.getMessage();
		}
		return"";
	}
	/*上传文件*/
	public String uploadFile(CommonsMultipartFile file,String relativePath){
		MediaSvrStatus mss = getAllMsSvrStatus();
		String ip = mss.getServerAddress();
		int port = mss.getPort();
		WorkspaceInfo ws = getWorkspaceInfo();
		try {
			String urlStr = "http://"+ip+":"+port+"/center/UploadFile?s="+ws.getNo()+"&p="+relativePath;
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true); 
			con.setRequestProperty("Content-Type", "application/octet-stream");
			OutputStreamWriter out = new OutputStreamWriter(con  
                    .getOutputStream());      

            out.write(new String(file.getBytes()));  
            out.flush();  
            out.close();  
            BufferedReader br = new BufferedReader(new InputStreamReader(con  
                    .getInputStream()));  
            String line = "";  
            for (line = br.readLine(); line != null; line = br.readLine()) {  
                System.out.println("\n\r 返回结果："+line);  
            }
            
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return ex.getMessage();
		}
		return"";
	}
	/*调用上传附件接口*/
	public String addCCaseMessage(String caseId,CaseAttachVM attach){
		
		try {
			String urlStr = "http://223.223.183.242:40000/center/AddCCaseMessage?caseId="+caseId;
			//String urlStr = "http://192.168.0.201:40000/center/AddCCaseMessage?caseId="+caseId;
			
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true); 
			con.setRequestProperty("Content-Type", "application/xml");
			OutputStreamWriter out = new OutputStreamWriter(con  
                    .getOutputStream());      
            String xmlInfo = getXmlInfoForAttach(attach);
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
			System.out.println(ex.getMessage());
			return ex.getMessage();
		}
		return"";
	}
	/*删除附件*/
	public int deleteCaseAttach(String caseId,String attachItemId){
		try {
			String urlStr = "http://223.223.183.242:40000/center/DeleteCCaseMessage?caseID="+caseId+"&itemId="+attachItemId;
			//String urlStr = "http://192.168.0.201:40000/center/DeleteCCaseMessage?caseID="+caseId+"&itemId="+attachItemId;
			
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true); 
			con.setRequestProperty("Content-Type", "application/xml");

            BufferedReader br = new BufferedReader(new InputStreamReader(con  
                    .getInputStream())); 
            
            String line = "";  
            int s = 0;
            for (line = br.readLine(); line != null; line = br.readLine()) {
            	System.out.println(line);
                s = getCodeFromLine(line);
            }
            return s;
            
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return -1;
		}

	}
	
	private int getCodeFromLine(String line){	
		try {
			Document document = DocumentHelper.parseText(line);
			Element root =  document.getRootElement();
			String temp = root.getText();
			return Integer.parseInt(temp);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
		//return 0;
	}
	private String getXmlInfoForAttach(CaseAttachVM attach) {
		StringBuilder sb = new StringBuilder();
		 
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<MessageItem ");
		if(attach.getId()!=null){
			sb.append(" ID=\""+attach.getId()+"\" ");
		}
		if(attach.getName()!=null){
			sb.append(" Name=\""+attach.getName()+"\" ");
		}
		if(attach.getDescription()!=null){
			sb.append(" Description=\""+attach.getDescription()+"\" ");
		}
		if(attach.getMessageType()!=null){
			sb.append(" MessageType=\""+attach.getMessageType()+"\" ");
		}
		sb.append("><Attachments><Item ");
		CaseAttachItem item = attach.getAttachItemList().get(0);
		if(item.getId()!=null){
			sb.append(" ID=\""+item.getId()+"\" ");
		}
		if(item.getName()!=null){
			sb.append(" Name=\""+item.getName()+"\" ");
		}
		if(item.getItemType()!=null){
			sb.append(" Type=\""+item.getItemType()+"\" ");
		}
		if(item.getUri()!=null){
			sb.append(" Uri=\""+item.getUri()+"\" ");
		}
		sb.append(" /></Attachments></MessageItem>");    
		return sb.toString();  
	}
}
