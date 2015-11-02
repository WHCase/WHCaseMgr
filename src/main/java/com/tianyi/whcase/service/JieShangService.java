package com.tianyi.whcase.service;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

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
		//MediaSvrStatus mss = getAllMsSvrStatus();
		//String ip = mss.getServerAddress();
		//int port = mss.getPort();
		String ip = "223.223.183.242";
		int port = 40001;
		WorkspaceInfo ws = getWorkspaceInfo();
		try {
			String serverPath = getClass().getResource("/").getFile()
					.toString();
			serverPath = serverPath.substring(0, (serverPath.length() - 16));
			
			File file = new File(serverPath+"/"+uri);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			file.createNewFile();

//			FileWriter fw = new FileWriter(file.getAbsoluteFile());
//			BufferedWriter bw = new BufferedWriter(fw);
			
			String urlStr = "http://"+ip+":"+port+"/"+uri;
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true); 
			con.setRequestProperty("Content-Type", "application/octet-stream");
			
//			BufferedReader br = new BufferedReader(new InputStreamReader(con  
//                    .getInputStream()));
			InputStream br = con.getInputStream();
			
			FileOutputStream out = new FileOutputStream(file,true);
			int len = 0;
			byte[] buf = new byte[1024];
			while((len = br.read(buf))!=-1){
				out.write(buf,0,len);
			}
			out.close();
			
// 
//            
//            
//            String line = "";  
//            for (line = br.readLine(); line != null; line = br.readLine()) {
//                bw.write(line);
//            }
//            bw.flush();
//            bw.close();
//            fw.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return ex.getMessage();
		}
		return"";
	}
	@Test
	public void test() throws Exception{
		File sf = new File("F:/gongsi/problems/grid.jpg");
		File df = new File("F:/gongsi/problems/copy.jpg");
		if(!df.getParentFile().exists()){
			df.getParentFile().mkdirs();
		}
		df.createNewFile();
		FileInputStream in = new FileInputStream(sf);
		FileOutputStream out = new FileOutputStream(df,true);
		int len = 0;
		byte[] buf = new byte[1024];
		while((len = in.read(buf))!=-1){
			out.write(buf,0,len);
		}
		out.close();
	} 
	/*上传文件*/
	public String uploadFile(CommonsMultipartFile file,String relativePath){
//		MediaSvrStatus mss = getAllMsSvrStatus();
//		String ip = mss.getServerAddress();
//		int port = mss.getPort();
		String ip = "223.223.183.242";
		int port = 40000;
		WorkspaceInfo ws = getWorkspaceInfo();
		try {
			String urlStr = "http://"+ip+":"+port+"/center/UploadFile?s="+ws.getNo()+"&p="+relativePath;
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true); 
			con.setRequestProperty("Content-Type", "application/octet-stream");
			OutputStream out = con.getOutputStream();      
			out.write(file.getBytes());
            
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
			String urlStr = "http://223.223.183.242:40000/center/AddCCaseMessage";
			//String urlStr = "http://192.168.0.201:40000/center/AddCCaseMessage?caseId="+caseId;
			
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true); 
			
			con.setRequestProperty("Content-Type", "application/xml");	
			OutputStreamWriter out = new OutputStreamWriter(con  
                    .getOutputStream());

            String xmlInfo = getXmlInfoForAttach(caseId,attach);
            System.out.println("urlStr=" + urlStr);  
            System.out.println("xmlInfo=" + xmlInfo);  
            //out.write(xmlInfo); 
            
            out.write(xmlInfo); 
            out.flush();  
            out.close();  
            InputStreamReader s = new InputStreamReader(con  
                    .getInputStream());
            //
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
	private String getXmlInfoForAttach(String caseId,CaseAttachVM attach) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		java.util.Date date = new java.util.Date();
		String time = sdf.format(date);
		
		CaseAttachItem item = attach.getAttachItemList().get(0);
 
		String temp = "<?xml version=\"1.0\" encoding=\"utf-8\"?><CCaseMessageAdd CasdID=\""+caseId+"\">"+
		"<MessageItem ID=\""+attach.getId()+"\" MessageType=\""+(attach.getMessageType()==null?"4":attach.getMessageType())+"\" IsTopMost=\"false\">"+
				"<Attachments><Item ID=\""+item.getId()+"\" Name=\""+item.getName()+"\" Creator=\"0\" CreateTime=\""+time+"\" Type=\"Unknown\" />" +
				"</Attachments></MessageItem></CCaseMessageAdd>";
		//return sb.toString();
		return temp;
	}
	/*
	 * 获取案件类型
	 */
	public String GetDictionary(){
		String urlStr = "http://223.223.183.242:40000/center/GetDictionary";
		//String urlStr = "http://192.168.0.201:40000/center/GetDictionary";
		URL url;
		try {
			url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true); 
			con.setRequestProperty("Content-Type", "application/xml");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con  
                    .getInputStream())); 
            
            String line = "";  
            for (line = br.readLine(); line != null; line = br.readLine()) {
            	System.out.println(line);
            }
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return"";
	}
}
