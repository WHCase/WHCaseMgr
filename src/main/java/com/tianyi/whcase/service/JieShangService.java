package com.tianyi.whcase.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tianyi.whcase.model.CaseAttach;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.Organ;
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
	
	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	/**
	 * 更新案件接口  通过
	 * 
	 * @param caseInfo
	 * @return
	 */
	public String updateCCase(CaseVM caseInfo) {
		String result = "-1";
		try {
			/*测试写的死数据
			CaseVM caseInfo = new CaseVM();
			caseInfo.setId("392d2958-92f1-2384-58fc-14d405050505");
			caseInfo.setName("WH-风劲角优创");
			caseInfo.setCreator(3);
			String cteateTime = "2016-11-14T03:19:21.961Z";
			caseInfo.setCreateTime(sdf.parse(cteateTime));
			caseInfo.setCode("111111111412");
			caseInfo.setCategoriesId("dc440454-34dc-10c6-78fc-aa5e05050505");
			String startTime = "2016-11-13T16:00:00Z";
			caseInfo.setStartTime(sdf.parse(startTime));
			caseInfo.setSummary("圣达菲优创");
			caseInfo.setStatus("Detected");
			caseInfo.setIsregister(false);
			caseInfo.setLevel("0");
			caseInfo.setCaseGroupId("0");
			caseInfo.setLongitude("9999");
			caseInfo.setLatitude("9999"); 
			caseInfo.setOrganizationId(2);
			caseInfo.setDetectedunitId(-1);*/
			
			String urlStr = "http://101.69.255.110:40000/center/UpdateCCase";  

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
			out.write(new String(xmlInfo.getBytes("UTF-8")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println("\n\r 返回结果：" + line);
				result = "" + getCodeFromLine(line);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return result;
	}

	/**
	 * 大量案件获取更新
	 * @param start
	 * @param end
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<CaseVM> QueryCases4WuHou(String start,String end,Integer pageIndex,Integer pageSize){
		List<CaseVM> list = new ArrayList<CaseVM>();
		try {
			
			String urlStr = "http://101.69.255.110:40000/center/QueryCases4WuHou?start="+start+"&end="+end+"&pageIndex="+0+"&pageSize="+100;


			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/xml");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String response = ""; 
			String readLine = null; 
			while((readLine =br.readLine()) != null){ 		 
			    response = response + readLine; 
			    list = getCCaseFromXml(response);
			}
			br.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return list;
		
	}

	/**
	 * 获取案件信息接口
	 * @param caseID
	 * @return
	 */
	public String getCase(String caseID){
		try {
			String urlStr = "http://http://101.69.255.110/:40000/center/getCase?caseID="+caseID;

			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/xml");
		/*	OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());
			
			String xmlInfo = getXmlCaseInfoForCase(caseID);
			int len = xmlInfo.length();
			out.write(xmlInfo, 0, len);
			out.flush();
			out.close();*/
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			/*String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println("\n\r 返回结果：" + line);
			}*/
			String response = ""; 
			String readLine = null; 
			while((readLine =br.readLine()) != null){ 
			 
			    response = response + readLine; 
			}
			br.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "";
		
	}

	/**
	 * 获取案件附件  通过
	 * @param caseID
	 * @param messageType
	 * @return
	 */
	public String getCaseMessages(String caseID, Integer messageType) {
		try {
			
			String urlStr = "http://101.69.255.110:40000/center/GetCaseMessages?caseID="+caseID+"&messageType="+66; 

			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/xml");
			
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

	/**
	 * 获取流媒体信息接口
	 * @return
	 */
	public MediaSvrStatus getAllMsSvrStatus() {
		MediaSvrStatus s = new MediaSvrStatus();
		try {
			
			String urlStr = "http://101.69.255.110:40000/center/GetAllMsSvrStatus";
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

    /**
     * 获取工作目录   通过
     * @return
     */
	public WorkspaceInfo getWorkspaceInfo() {
		WorkspaceInfo wsInfo = new WorkspaceInfo();
		try {
			//String urlStr = "http://223.223.183.242:40000/center/GetWorkspacesInfoList";
			 String urlStr =
//			 "http://101.69.255.110:40000/center/GetWorkspacesInfoList";
			 "http://101.69.255.110:40000/center/GetAllWorkspaceInfo";

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

	/**
	 * 下载文件接口
	 * @param uri
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String downloadAttachFiles(String uri, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		uri = uri.replace("resource://", "");
		//String ip = "223.223.183.242";

		String ip = "101.69.255.110";
		int port = 21001;
        String result = "1";
		try {
			String serverPath = getClass().getResource("/").getFile()
					.toString();
			serverPath = serverPath.substring(0, (serverPath.length() - 16));
            String[] path = uri.split("/Files");
            int ment = path.length;
			File file = new File(serverPath + "/Files" + path[ment-1]);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			System.out.println(serverPath + "/Files" + path[ment-1]);
			String urlStr = "http://" + ip + ":" + port + "/" + uri;
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/octet-stream");
			con.setRequestMethod("GET");

			BufferedInputStream bis = new BufferedInputStream(
					con.getInputStream());
			FileOutputStream out = new FileOutputStream(serverPath + "/Files" + path[ment-1]);

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

	/**
	 * 上传文件接口
	 * @param file
	 * @param relativePath
	 * @return
	 */
	public String uploadFile(CommonsMultipartFile file, String relativePath) {
		// MediaSvrStatus mss = getAllMsSvrStatus();
		// String ip = mss.getServerAddress();
		// int port = mss.getPort();
		String result = "0";
		String ip = "101.69.255.110";
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
		/*	BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println("\n\r 返回结果：" + line);
				int s = getCodeFromLine(line);
				result = String.valueOf(s);
			}*/

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "2";
		}
		return result;
	}

	/**
	 * 上传附件信息接口
	 * @param caseId
	 * @param attach
	 * @return
	 * @throws Exception
	 */
	public  String addCCaseMessage(String caseId, CaseAttachVM attach)
			throws Exception {
		String result = "0";
		try {
//			String urlStr = "http://223.223.183.242:40000/center/AddCCaseMessage";
			 String urlStr ="http://101.69.255.110:40000/center/AddCCaseMessage";

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
			/*BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println("\n\r 返回结果：" + line);
				int s = getCodeFromLine(line);
				result = String.valueOf(s);
			}*/
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "2";
		}
		return result;
	}

	/**
	 * 删除附件
	 * @param caseId
	 * @param attachItemId
	 * @return
	 */
	public int deleteCaseAttach(String caseId, String attachItemId) {
		try {
//			String urlStr = "http://223.223.183.242:40000/center/DeleteCCaseMessage?caseID="
//					+ caseId + "&itemId=" + attachItemId;
			 String urlStr =
			 "http://101.69.255.110:40000/center/DeleteCCaseMessage?caseID="+caseId+"&itemId="+attachItemId;

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

	/**
	 * 返回值(成功0)
	 * @param line
	 * @return
	 */
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
 
	/**
	 * 获取所有组织机构接口
	 * @return
	 */
	public String getAllOrganizations() {
		//String urlStr = "http://223.223.183.242:40000/center/GetAllOrganizations";
		 String urlStr =
		 "http://101.69.255.110:40000/center/GetAllOrganizations";
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
				List<Organ> organList = getOrganListFromXml(line);
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

	/*
	 * 获取案件类型  通过
	 * @return
	 */
	public String getDictionary() {
		//String urlStr = "http://223.223.183.242:40000/center/GetDictionary";
		 String urlStr = "http://101.69.255.110:40000/center/GetDictionary";
		
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
				List<CaseCategory> categoryList = getCaseCategoryFromXml(line);
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

	/**
	 * xml-->案件类型对象集合
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	private List<CaseCategory> getCaseCategoryFromXml(String xml)
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

	/**
	 * xml-->组织机构对象集合
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	private List<Organ> getOrganListFromXml(String xml)
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
	
	/**
	 * xml-->案件list
	 * @param xml
	 * @return
	 * @throws DocumentException
	 * @throws ParseException 
	 */
	private List<CaseVM> getCCaseFromXml(String xml)throws DocumentException, ParseException {
		// xml = xml.replaceAll("<?xml version=\"1.0\" encoding=\"utf-8\"?>",
		// "");
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		@SuppressWarnings("rawtypes")
		Iterator item = root.elementIterator();
		List<CaseVM> list = new ArrayList<CaseVM>();
		while (item.hasNext()) {
			Element element = (Element) item.next();
			CaseVM ccase = new CaseVM();
			ccase.setId(element.attributeValue("ID"));
			ccase.setName(element.attributeValue("Name"));
			ccase.setCreator(Integer.parseInt(element.attributeValue("Creator")));
			ccase.setCreateTime(sdf.parse(element.attributeValue("CreateTime")));
			ccase.setCode(element.attributeValue("Code"));
			ccase.setCategoriesId(element.attributeValue("Categories"));
			ccase.setStartTime(sdf.parse(element.attributeValue("StartTime")));
			ccase.setSectionTime(sdf.parse(element.attributeValue("SectionTime")));
			ccase.setAddress(element.attributeValue("Address"));
			ccase.setSummary(element.attributeValue("Summary"));
			ccase.setStatus(element.attributeValue("Status"));
			ccase.setCaseGroupId(element.attributeValue("CaseGroupId"));
			ccase.setIsregister(Boolean.valueOf(element.attributeValue("IsRegister")));			
			ccase.setUserGroupId(element.attributeValue("UserGroupId"));
			ccase.setLevel(element.attributeValue("Level"));
			ccase.setLongitude(element.attributeValue("Longitude"));
			ccase.setLatitude(element.attributeValue("Latitude"));
			ccase.setOrganizationId(Integer.parseInt(element.attributeValue("OrganizationID")));
			ccase.setDetectedunitId(Integer.parseInt(element.attributeValue("DetectedUnit")));
			ccase.setOnlyWithResource(Boolean.valueOf(element.attributeValue("OnlyWithResource")));
			list.add(ccase);
		}
		return list;
	}
	
	/**
	 * xml-->CaseAttach案件附件信息
	 * @param xml
	 * @return
	 */
	private CaseAttach getCaseMessageFromxml(String xml) {
		try {
			CaseAttach ca = new CaseAttach();
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Element attach = root.element("MessageItem");
			ca.setId(attach.attributeValue("ID"));
			ca.setName(attach.attributeValue("Name"));
			ca.setDescription(attach.attributeValue("Description"));
			ca.setCreator(Integer.valueOf(attach.attributeValue("Creator")));
			ca.setMessageType(attach.attributeValue("MessageType"));
			try {
				ca.setCreateTime(sdf.parse(attach.attributeValue("CreateTime")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
			return ca;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * xml-->工作空间对象
	 * @param xml
	 * @return
	 */
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

	/**
	 * xml==>流媒体信息
	 * @param xml
	 * @return MediaSvrStatus
	 */
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
			mss.setNginxPort(Integer.parseInt(configElement
					.attributeValue("NginxPort")));

			return mss;
		} catch (DocumentException e) {

			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * CaseAttachItem-->xml
	 * @param caseID
	 * @param messageType
	 * @return
	 */
	private String getXmlInfoForCaseMessages(String caseID, Integer messageType) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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
	
	/**
	 * 案件信息-->xml
	 * @param caseID
	 * @return
	 */
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

	/**
	 * 案件更新信息-->xml
	 * @param caseInfo
	 * @return
	 */
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
  
	/**
	 * 案件附件信息-->xml
	 * @param caseId
	 * @param attach
	 * @return
	 * @throws ParseException
	 */
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

	@Test
	public void download() {

		String uri = "CaseCenter_ws1/Files/20161110/14/9a0f2458-cfa6-26e4-e4d7-a13d05050505.jpg";
		String ip = "101.69.255.110";
		int port = 21001;
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
	@Test
	public void testConnect() throws DocumentException, ParseException {
		
		String endTime = sdf.format(new Date());
		String startTime = "2001-01-01T00:00:01";
		List<CaseVM> list = new ArrayList<CaseVM>();
		list = QueryCases4WuHou(startTime,endTime,0,4);
		System.out.println(list.size());
	//	MediaSvrStatus src = getAllMsSvrStatus();

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
	@Test
	public void syslist(){
		getDictionary();
	}
}
