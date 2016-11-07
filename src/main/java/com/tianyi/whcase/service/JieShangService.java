package com.tianyi.whcase.service;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sound.sampled.AudioFormat.Encoding;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tianyi.whcase.core.FileUtil;
import com.tianyi.whcase.model.CaseAttach;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.Organ;
import com.tianyi.whcase.model.detectUnit;
import com.tianyi.whcase.viewmodel.CaseAttachVM;
import com.tianyi.whcase.viewmodel.CaseVM;
import com.tianyi.whcase.viewmodel.MediaSvrStatus;
import com.tianyi.whcase.viewmodel.WorkspaceInfo;

@Service
public class JieShangService {

	@Autowired
	CommonService commonService;
	@Autowired
	OrganService organService;
	
	@Autowired 
	CaseAttchService caseAttchService;
	/**
	 * 更新案件
	 * 
	 * @param caseInfo
	 * @return
	 */
	public String updateCCase(CaseVM caseInfo) {
		try {
			//String urlStr = "http://223.223.183.242:40000/center/UpdateCCase";
			String urlStr = "http://192.168.0.201:40000/center/UpdateCCase";  //

			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/xml");
			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());
			String xmlInfo = getXmlInfoForCase(caseInfo);
			System.out.println("urlStr=" + urlStr);
			System.out.println("xmlInfo=" + xmlInfo);
			out.write(new String(xmlInfo.getBytes("ISO-8859-1")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println("\n\r 返回结果：" + line);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "0";
	}

	private String getXmlInfoForCase(CaseVM caseInfo) {
		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		if (caseInfo.getId() != null) {
			sb.append("<CCase ID=\"" + caseInfo.getId() + "\" ");
		}
		if (caseInfo.getName() != null) {
			sb.append("Name=\"" + caseInfo.getName() + "\" ");
		}
		if (caseInfo.getCreator() != null) {
			sb.append("Creator=\"" + caseInfo.getCreator() + "\" ");
		}
		if (caseInfo.getCreateTime() != null) {
			java.text.DateFormat format1 = new java.text.SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			String s = format1.format(caseInfo.getCreateTime());
			sb.append("CreateTime=\"" + s + "\" ");
		}
		if (caseInfo.getStartTime() != null) {
			java.text.DateFormat format1 = new java.text.SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			String t = format1.format(caseInfo.getStartTime());
			sb.append("StartTime=\"" + t + "\" ");
		}
		if (caseInfo.getCode() != null) {
			sb.append("Code=\"" + caseInfo.getCode() + "\" ");
		}
		if (caseInfo.getCategoriesId() != null) {
			sb.append("Categories=\"" + caseInfo.getCategoriesId() + "\" ");
		}
		if (caseInfo.getSummary() != null) {
			sb.append("Summary=\"" + caseInfo.getSummary() + "\" ");
		}
		if (caseInfo.getStatus() != null) {
			sb.append("Status=\"" + caseInfo.getStatus() + "\" ");
		}

		if (caseInfo.getIsregister() != null) {
			sb.append("IsRegister=\"" + caseInfo.getIsregister().toString()
					+ "\" ");
		}
		sb.append("UserGroupId=\"0\" ");

		if (caseInfo.getLevel() != null) {
			sb.append("Level=\"" + caseInfo.getLevel() + "\" ");
		}
		if (caseInfo.getLongitude() != null) {
			sb.append("Longitude=\"" + caseInfo.getLongitude() + "\" ");
		}
		if (caseInfo.getLatitude() != null) {
			sb.append("Latitude=\"" + caseInfo.getLatitude() + "\" ");
		}
		if (caseInfo.getOrganizationId() != null) {
			sb.append("OrganizationID=\"" + caseInfo.getOrganizationId()
					+ "\" ");
		}
		if (caseInfo.getDetectedunitId() != null) {
			sb.append("DetectedUnit=\"" + caseInfo.getDetectedunitId() + "\"");
		}

		sb.append("></CCase>");
		return sb.toString();
	}

	public String getCase(String caseID){
		try {
			//String urlStr = "http://223.223.183.242:40000/center/UpdateCCase";
			String urlStr = "http://192.168.0.201:40000/center/UpdateCCase";  //

			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/xml");
			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());
			String xmlInfo = getXmlCaseInfoForCase(caseID);
			System.out.println("urlStr=" + urlStr);
			System.out.println("xmlInfo=" + xmlInfo);
			out.write(new String(xmlInfo.getBytes("ISO-8859-1")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println("\n\r 返回结果：" + line);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "0";
		
	}
	private String getXmlCaseInfoForCase(String caseID) {
		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		if (caseID != null) {
			sb.append("<CCase ID=\"" + caseID + "\" ");
		}
		/*if (caseInfo.getName() != null) {
			sb.append("Name=\"" + caseInfo.getName() + "\" ");
		}
		if (caseInfo.getCreator() != null) {
			sb.append("Creator=\"" + caseInfo.getCreator() + "\" ");
		}
		if (caseInfo.getCreateTime() != null) {
			java.text.DateFormat format1 = new java.text.SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			String s = format1.format(caseInfo.getCreateTime());
			sb.append("CreateTime=\"" + s + "\" ");
		}
		if (caseInfo.getStartTime() != null) {
			java.text.DateFormat format1 = new java.text.SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			String t = format1.format(caseInfo.getStartTime());
			sb.append("StartTime=\"" + t + "\" ");
		}
		if (caseInfo.getCode() != null) {
			sb.append("Code=\"" + caseInfo.getCode() + "\" ");
		}
		if (caseInfo.getCategoriesId() != null) {
			sb.append("Categories=\"" + caseInfo.getCategoriesId() + "\" ");
		}
		if (caseInfo.getSummary() != null) {
			sb.append("Summary=\"" + caseInfo.getSummary() + "\" ");
		}
		if (caseInfo.getStatus() != null) {
			sb.append("Status=\"" + caseInfo.getStatus() + "\" ");
		}

		if (caseInfo.getIsregister() != null) {
			sb.append("IsRegister=\"" + caseInfo.getIsregister().toString()
					+ "\" ");
		}
		sb.append("UserGroupId=\"0\" ");

		if (caseInfo.getLevel() != null) {
			sb.append("Level=\"" + caseInfo.getLevel() + "\" ");
		}
		if (caseInfo.getLongitude() != null) {
			sb.append("Longitude=\"" + caseInfo.getLongitude() + "\" ");
		}
		if (caseInfo.getLatitude() != null) {
			sb.append("Latitude=\"" + caseInfo.getLatitude() + "\" ");
		}
		if (caseInfo.getOrganizationId() != null) {
			sb.append("OrganizationID=\"" + caseInfo.getOrganizationId()
					+ "\" ");
		}
		if (caseInfo.getDetectedunitId() != null) {
			sb.append("DetectedUnit=\"" + caseInfo.getDetectedunitId() + "\"");
		}
*/
		sb.append("></CCase>");
		return sb.toString();
	}

	public String getCaseMessages(String caseID, Integer messageType) {
		try {
			//String urlStr = "http://223.223.183.242:40000/center/UpdateCCase";
			String urlStr = "http://192.168.0.201:40000/center/UpdateCCase";  //

			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/xml");
			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());
			
			String xmlInfo = getXmlInfoForCaseMessages(caseID, messageType);
			int len = xmlInfo.length();
			out.write(xmlInfo, 0, len);
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println("\n\r 返回结果：" + line);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "";
	}

	private String getXmlInfoForCaseMessages(String caseID, Integer messageType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		java.util.Date date = new java.util.Date();
		String time = sdf.format(date);
		CaseAttachItem item = new CaseAttachItem();
		CaseAttach attach = caseAttchService.getCaseAttachBycaseID(caseID,messageType);
		String temp = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<ArrayOfMessageItem>"
				+ "<MessageItem ID=\""
				+ caseID
				+ "\" Name=\""
				+ attach.getName()
				+ "\" MessageType=\""
				+ messageType
				+ "\" IsTopMost=\"false\">"
				+ "<Attachments><Item ID=\""
				+ item.getId()
				+ "\" Name=\""
				+ item.getName()
				+ "\" Creator=\"0\" "
				+ "CreateTime=\""
				+ time
				+ "\" Uri=\""
				+ item.getUri()
				+ "\" Type=\""
				+ item.getItemType()
				+ "\"/>"
				+ "</Attachments></MessageItem></ArrayOfMessageItem>";
				
		return temp;
		
	}

	public MediaSvrStatus getAllMsSvrStatus() {
		MediaSvrStatus s = new MediaSvrStatus();
		try {
			//String urlStr = "http://223.223.183.242:40000/center/GetAllMsSvrStatus";
			 String urlStr =
			 "http://192.168.0.201:40000/center/GetAllMsSvrStatus";
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/xml");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

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
			Element root = document.getRootElement();
			Element mediaSvrStatusElement = root.element("MediaSvrStatus");
			mss.setSvrNo(mediaSvrStatusElement.attributeValue("SvrNo"));
			mss.setSvrName(mediaSvrStatusElement.attributeValue("SvrName"));
			mss.setStatusCode(mediaSvrStatusElement
					.attributeValue("StatusCode"));
			mss.setCpu(mediaSvrStatusElement.attributeValue("CPU"));
			mss.setMemory(mediaSvrStatusElement.attributeValue("Memory"));
			mss.setFreeMemory(mediaSvrStatusElement
					.attributeValue("FreeMemory"));

			Element configElement = mediaSvrStatusElement.element("Config");
			mss.setServerAddress(configElement.attributeValue("ServerAddress"));
			mss.setPort(Integer.parseInt(configElement.attributeValue("Port")));
			mss.setVideoPort(Integer.parseInt(configElement
					.attributeValue("VideoPort")));
			mss.setNginPort(Integer.parseInt(configElement
					.attributeValue("NginxPort")));

			return mss;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public WorkspaceInfo getWorkspaceInfo() {
		WorkspaceInfo wsInfo = new WorkspaceInfo();
		try {
			//String urlStr = "http://223.223.183.242:40000/center/GetWorkspacesInfoList";
			 String urlStr =
			 "http://192.168.0.201:40000/center/GetWorkspacesInfoList";

			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/xml");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
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
			Element root = document.getRootElement();
			Element performanceElement = root.element("PerformanceItem");
			ws.setName(performanceElement.attributeValue("Name"));
			ws.setNo(performanceElement.attributeValue("No"));

			ws.setValue(Double.parseDouble(performanceElement
					.attributeValue("Value")));
			ws.setTotal(Double.parseDouble(performanceElement
					.attributeValue("Total")));

			return ws;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Test
	public void download() {

		String uri = "/resource/icon/login/login-btn.gif";
		String ip = "192.168.0.201";
		int port = 8080;
		try {
			String serverPath = getClass().getResource("/").getFile()
					.toString();
			serverPath = serverPath.substring(0, (serverPath.length() - 16));

			File file = new File(serverPath + "/" + uri);
			System.out.println(serverPath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			// file.createNewFile();
			System.out.println(serverPath + "/" + uri);
			String urlStr = "http://" + ip + ":" + port + "/" + uri;
			System.out.println(urlStr);
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();

			BufferedInputStream bis = new BufferedInputStream(
					con.getInputStream());
			FileOutputStream out = new FileOutputStream(serverPath + "/" + uri);

			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = bis.read(buf)) != -1) {
				out.write(buf, 0, len);
			}

			out.close();
			bis.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());

		}

	}

	public String downloadAttachFiles(String uri, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		uri = uri.replace("resource://", "");
		//String ip = "223.223.183.242";
		 String ip = "192.168.0.201";
		int port = 40001;
        String result = "1";
		try {
			String serverPath = getClass().getResource("/").getFile()
					.toString();
			serverPath = serverPath.substring(0, (serverPath.length() - 16));

			File file = new File(serverPath + "/" + uri);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			System.out.println(serverPath + "/" + uri);
			String urlStr = "http://" + ip + ":" + port + "/" + uri;
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/octet-stream");
			con.setRequestMethod("GET");

			BufferedInputStream bis = new BufferedInputStream(
					con.getInputStream());
			FileOutputStream out = new FileOutputStream(serverPath + "/" + uri);

			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = bis.read(buf)) != -1) {
				out.write(buf, 0, len);
				result = "0";
			}           
			out.close();
			bis.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			result = "2";
		}
		return result;
	}

	@Test
	public void code() throws UnsupportedEncodingException {
		System.out.println(URLEncoder.encode("This string has 你好", "UTF-8"));
		System.out.println(URLEncoder.encode("This string has spaces", "GBK"));
		System.out.println(URLEncoder.encode("This string has spaces",
				"ISO-8859-1"));
	}

	@Test
	public void test() throws Exception {
		File sf = new File("F:/gongsi/problems/grid.jpg");
		File df = new File("F:/gongsi/problems/copy.jpg");
		if (!df.getParentFile().exists()) {
			df.getParentFile().mkdirs();
		}
		df.createNewFile();
		FileInputStream in = new FileInputStream(sf);
		FileOutputStream out = new FileOutputStream(df, true);
		int len = 0;
		byte[] buf = new byte[1024];
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
		out.close();
	}

	/* 上传文件 */
	public String uploadFile(CommonsMultipartFile file, String relativePath) {
		// MediaSvrStatus mss = getAllMsSvrStatus();
		// String ip = mss.getServerAddress();
		// int port = mss.getPort();
		//String ip = "223.223.183.242";
		String result = "1";
		 String ip = "192.168.0.201";
		int port = 40000;
		WorkspaceInfo ws = getWorkspaceInfo();
		try {
			// URLEncoder.encode(relativePath, "UTF-8");
			// URLEncoder.encode(relativePath,"GBK");
			String urlStr = "http://" + ip + ":" + port
					+ "/center/UploadFile?s=" + ws.getNo() + "&p="
					+ URLEncoder.encode(relativePath, "UTF-8");
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/octet-stream");
			con.setRequestMethod("POST");
			con.connect();
			OutputStream out = con.getOutputStream();
			out.write(file.getBytes());

			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println("\n\r 返回结果：" + line);
				int s = getCodeFromLine(line);
				result = String.valueOf(s);
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "2";
		}
		return result;
	}

	/* 调用上传附件接口 */
	public  String addCCaseMessage(String caseId, CaseAttachVM attach)
			throws Exception {
		String result = "1";
		try {
//			String urlStr = "http://223.223.183.242:40000/center/AddCCaseMessage";
			 String urlStr ="http://192.168.0.103:40000/center/AddCCaseMessage";

			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type",
					"application/xml; charset=utf-8");
			con.setRequestMethod("POST");
			con.connect();

			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");

			String xmlInfo = getXmlInfoForAttach(caseId, attach);
			int len = xmlInfo.length();
			out.write(xmlInfo, 0, len);
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println("\n\r 返回结果：" + line);
				int s = getCodeFromLine(line);
				result = String.valueOf(s);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "2";
		}
		return result;
	}

	/* 删除附件 */
	public int deleteCaseAttach(String caseId, String attachItemId) {
		try {
//			String urlStr = "http://223.223.183.242:40000/center/DeleteCCaseMessage?caseID="
//					+ caseId + "&itemId=" + attachItemId;
			 String urlStr =
			 "http://192.168.0.201:40000/center/DeleteCCaseMessage?caseID="+caseId+"&itemId="+attachItemId;

			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/xml");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String line = "";
			int s = -2;
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

	private int getCodeFromLine(String line) {
		try {
			Document document = DocumentHelper.parseText(line);
			Element root = document.getRootElement();
			String temp = root.getText();
			return Integer.parseInt(temp);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

		// return 0;
	}

	private String getXmlInfoForAttach(String caseId, CaseAttachVM attach)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		java.util.Date date = new java.util.Date();
		String time = sdf.format(date);

		CaseAttachItem item = attach.getAttachItemList().get(0);

		String temp = "<?xml version=\"1.0\" encoding=\"utf-8\"?><CCaseMessageAdd CasdID=\""
				+ caseId
				+ "\">"
				+ "<MessageItem ID=\""
				+ attach.getId()
				+ "\" Name=\""
				+ attach.getName()
				+ "\" MessageType=\""
				+ (attach.getMessageType() == null ? "4" : attach.getMessageType())
				+ "\" IsTopMost=\"false\">"
				+ "<Attachments><Item ID=\""
				+ item.getId()
				+ "\" Name=\""
				+ item.getName()
				+ "\" Creator=\"0\" CreateTime=\""
				+ time
				+ "\" Uri=\""
				+ item.getUri()
				+ "\" Type=\""
				+ item.getItemType()
				+ "\"/>"
				+ "</Attachments></MessageItem></CCaseMessageAdd>";
		// return sb.toString();
		return temp;
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		// Date datetime = sdf.parse((new Date()).toString());
		// StringBuilder sb = new StringBuilder();
		//
		// sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		// sb.append("<CCaseMessageAdd CasdID=\""+caseId+"\">");
		// sb.append("<MessageItem");
		// if(attach.getId()!=null){
		// sb.append(" ID=\""+attach.getId()+"\"");
		// }
		// if(attach.getName()!=null){
		// sb.append(" Name=\""+attach.getName()+"\"");
		// }
		// if(attach.getDescription()!=null){
		// sb.append(" Description=\"\"");
		// }
		// if(attach.getMessageType()!=null){
		// sb.append(" MessageType=\""+attach.getMessageType()+"\"");
		// //sb.append(" Creator=\"0\" CreateTime=\"2015-10-10 00:00:00\" IsTopMost=\"false\" ");
		// }
		// sb.append("><Attachments><Item");
		// CaseAttachItem item = attach.getAttachItemList().get(0);
		// if(item.getId()!=null){
		// sb.append(" ID=\""+item.getId()+"\"");
		// }
		// if(item.getName()!=null){
		// sb.append(" Name=\""+item.getName()+"\"");
		// sb.append(" Creator=\"0\" CreateTime=\"2015-10-10T00:00:00\"");
		// }
		// if(item.getItemType()!=null){
		// sb.append(" Type=\""+item.getItemType()+"\"");
		// }
		// if(item.getUri()!=null){
		// sb.append(" Uri=\""+item.getUri()+"\"");
		// }
		// sb.append("/></Attachments></MessageItem></CCaseMessageAdd>");
		// return sb.toString();
	}

	/*
	 * 获取案件类型
	 */

	public String GetDictionary() {
		//String urlStr = "http://223.223.183.242:40000/center/GetDictionary";
		 String urlStr = "http://192.168.0.201:40000/center/GetDictionary";
		
		try {
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type",
					"application/xml; charset=utf-8");
			con.setRequestMethod("POST");
			con.connect();

			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());
			
			String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?><int>3</int>";
			int len = content.length();
			out.write(content, 0, len);
			out.flush();
			out.close();
			String line = "";
			InputStreamReader br = new InputStreamReader(
					con.getInputStream(),"UTF-8");
			BufferedReader br1 = new BufferedReader(br);
			for (line = br1.readLine(); line != null; line = br1.readLine()) {
				System.out.println(line);
				List<CaseCategory> categoryList = GetCaseCategoryFromXml(line);
				for (int i = 0; i < categoryList.size(); i++) {
					commonService.InsertCaseCategory(categoryList.get(i));
				}
			}

			br.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

		return "";
	}

	private List<CaseCategory> GetCaseCategoryFromXml(String xml)
			throws DocumentException {
		// String xml =
		// "<ArrayOfDictionaryItem xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><DictionaryItem DictType=\"3\" No=\"dc440454-34dc-10c6-78fc-aa4d05050505\" Value=\"危害国家安全案\" Flag=\"0\"/><DictionaryItem DictType=\"3\" No=\"dc440454-34dc-10c6-78fc-aa4f05050505\" Value=\"背叛国家案\" Flag=\"0\"/></ArrayOfDictionaryItem>";
		// xml = xml.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>",
		// "");
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		@SuppressWarnings("rawtypes")
		Iterator item = root.elementIterator();
		List<CaseCategory> list = new ArrayList<CaseCategory>();
		while (item.hasNext()) {
			Element element = (Element) item.next();
			CaseCategory category = new CaseCategory();
			category.setId(element.attributeValue("No"));
			category.setText(element.attributeValue("Value"));
			list.add(category);
		}
		return list;
	}

	private List<Organ> GetOrganListFromXml(String xml)
			throws DocumentException {
		// xml = xml.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>",
		// "");
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		@SuppressWarnings("rawtypes")
		Iterator item = root.elementIterator();
		List<Organ> list = new ArrayList<Organ>();
		while (item.hasNext()) {
			Element element = (Element) item.next();
			Organ organ = new Organ();
			organ.setId(Integer.parseInt(element.attributeValue("ID")));
			organ.setName(element.attributeValue("Name"));
			organ.setParentId(Integer.parseInt(element
					.attributeValue("ParentId")));
			list.add(organ);
		}
		return list;
	}

	
	public String GetAllOrganizations() {
		//String urlStr = "http://223.223.183.242:40000/center/GetAllOrganizations";
		 String urlStr =
		 "http://192.168.0.201:40000/center/GetAllOrganizations";
		URL url;
		try {
			url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/xml");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "utf8"));

			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println(line);
				List<Organ> organList = GetOrganListFromXml(line);
				System.out.println(organList.size());
				for (int i = 0; i < organList.size(); i++) {
					System.out.println(organList.get(i).getName());
					organService.insert(organList.get(i));
				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			//return e.getMessage();
		}
		return"";
	}
}
