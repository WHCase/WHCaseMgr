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
import java.util.UUID;

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

import com.tianyi.whcase.core.Constants;
import com.tianyi.whcase.model.Case;
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
	
	public static String IP="101.69.255.110";
	
	public static String port="40000";
	
	/**
	 * 更新案件接口  通过
	 * 
	 * @param caseInfo
	 * @return
	 */
	public String updateCCase(CaseVM caseInfo) {
		String result = "-1";
		try {
						
			String urlStr = "http://"+IP+":"+port+"/center/UpdateCCase";  

			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/xml");
			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());
			String xmlInfo = getXmlInfoForCase(caseInfo);
			out.write(new String(xmlInfo.getBytes("UTF-8")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				result = "" + getCodeFromLine(line);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return result;
	}

	/**
	 * 大量案件获取更新  通过
	 * @param start
	 * @param end
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<CaseVM> QueryCases4WuHou(String start,String end,Integer pageIndex,Integer pageSize){
		List<CaseVM> list = new ArrayList<CaseVM>();
		try {
			
			String urlStr = "http://"+IP+":"+port+"/center/QueryCases4WuHou?start="+start+"&end="+end+"&pageIndex="+0+"&pageSize="+pageSize;


			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/xml;charset=utf-8");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(),"UTF-8"));
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
	 * 获取案件信息接口  通过
	 * @param caseID
	 * @return
	 */
	public  CaseVM getCase(String caseID){
		CaseVM ccase = new CaseVM();
		try {
			String urlStr = "http://"+IP+":"+port+"/center/getCase?caseID="+caseID;

			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/xml;charset=utf-8");
		
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(),"UTF-8"));
			String response = ""; 
			String readLine = null; 
			while((readLine =br.readLine()) != null){ 			 
			    response = response + readLine; 		  
			    ccase = getCaseMessagesFromXml(response);
			}
			br.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return ccase;
		
	}

	/**
	 * 获取案件附件  通过
	 * @param caseID
	 * @param messageType
	 * @return
	 */
	public List<CaseAttachVM> getCaseMessages(String caseID, Integer messageType) {
		List<CaseAttachVM> list = new ArrayList<CaseAttachVM>();
		try {
			
			String urlStr = "http://"+IP+":"+port+"/center/GetCaseMessages?caseID="+caseID+"&messageType="+66; 

			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/xml;charset=utf-8");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(),"UTF-8"));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				list = getMessagesFromXml(line,caseID);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}

	/**
	 * 获取流媒体信息接口
	 * @return
	 */
	public MediaSvrStatus getAllMsSvrStatus() {
		MediaSvrStatus s = new MediaSvrStatus();
		try {
			
			String urlStr = "http://"+IP+":"+port+"/center/GetAllMsSvrStatus";
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/xml;charset=utf-8");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(),"UTF-8"));

			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
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
			 String urlStr = "http://"+IP+":"+port+"/center/GetAllWorkspaceInfo";

			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/xml;charset=utf-8");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(),"UTF-8"));
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
//			String serverPath = Constants.serverPath;
//			 String serverPath = request.getSession().getServletContext().getRealPath("targ ");
	            
            String[] path = uri.split("/Files");
            int ment = path.length;
			File file = new File(serverPath + "/Files" + path[ment-1]);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
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
		String result = "-1";
		String ip = "101.69.255.110";
		int port = 21000;
		//WorkspaceInfo ws = getWorkspaceInfo();
		try {
			
			String urlStr = "http://" + ip + ":" + port
					+ "/media/UploadFile?s=CaseCenter_ws1&p="
					+ URLEncoder.encode(relativePath, "UTF-8");
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true); 
			con.setRequestProperty("Content-Type", "application/octet-stream");
			con.setRequestMethod("POST"); 
			
			//获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
            byte[] requestStringBytes = file.getBytes();

            //设置请求属性
            con.setRequestProperty("Content-length", "" + requestStringBytes.length);
          //建立输出流，并写入数据
            OutputStream outputStream = con.getOutputStream();
            outputStream.write(requestStringBytes);
            outputStream.close();
//			con.connect();
//			OutputStream out = con.getOutputStream();
//			out.write(file.getBytes());

            outputStream.flush();
            outputStream.close();
            
          //获得响应状态
           // int responseCode = con.getResponseCode();
            
            
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {				
				int s = getCodeFromLine(line);
				result = String.valueOf(s);
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "-2";
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
		String result = "-1";
		try {
			//String urlStr = "http://121.199.8.150:80/case/caseAttch/AddCaseAttach.do?caseId="+caseId;
		    String urlStr ="http://"+IP+":"+port+"/center/AddCCaseMessage";
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
				int s = getCodeFromLine(line);
				result = String.valueOf(s);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "-2";
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
			 
			String urlStr = "http://"+IP+":"+port+"/center/DeleteCCaseMessage?caseID="+caseId+"&itemId="+attachItemId;

			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/xml;charset=utf-8");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(),"UTF-8"));

			String line = "";
			int s = -1;
			for (line = br.readLine(); line != null; line = br.readLine()) {
				s = getCodeFromLine(line);
			}
			return s;

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return -2;
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
		 "http://"+IP+":"+port+"/center/GetAllOrganizations";
		URL url;
		String result = "组织机构类型更新失败!";
		try {
			url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/xml;charset=utf-8");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "utf8"));

			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				List<Organ> organList = getOrganListFromXml(line);
				for (int i = 0; i < organList.size(); i++) {
					organService.insert(organList.get(i));
				}
				result = "组织机构类型更新成功!";
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			//return e.getMessage();
			result = "数据获取出错!";
		}
		return result;
	}

	/**
	 * 获取案件类型  通过
	 * @return
	 */
	public String getDictionary() {
		//String urlStr = "http://223.223.183.242:40000/center/GetDictionary";
		 String urlStr = "http://"+IP+":"+port+"/center/GetDictionary";
		String temp = "案件类型更新失败!";
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
				List<CaseCategory> categoryList = getCaseCategoryFromXml(line);
				for (int i = 0; i < categoryList.size(); i++) {
					commonService.InsertCaseCategory(categoryList.get(i));
				}
				temp = "案件类型更新成功!";
			}

			br.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			temp = "获取数据失败!";
		}

		return temp;
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
	 * xml-->案件附件list
	 * @param xml
	 * @param caseID
	 * @return List<CaseAttachVM>
	 * @throws ParseException
	 */
	private List<CaseAttachVM> getMessagesFromXml(String xml,String caseID) throws ParseException {		
		List<CaseAttachVM> attachlist = new ArrayList<CaseAttachVM>();
		try {					
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();			
			//案件附件信息
			@SuppressWarnings("rawtypes")
			Iterator item = root.elementIterator();
			while (item.hasNext()) {
				CaseAttachVM ca = new CaseAttachVM();
				List<CaseAttachItem> list = new ArrayList<CaseAttachItem>();
				Element message = (Element) item.next();				
				ca.setId(message.attributeValue("ID"));
				ca.setName(message.attributeValue("Name"));
				ca.setDescription(message.attributeValue("Description"));
				ca.setCreator(Integer.valueOf(message.attributeValue("Creator")));
				ca.setCreateTime(sdf.parse(message.attributeValue("CreateTime")));
				ca.setMessageType(message.attributeValue("MessageType"));
				ca.setCaseId(caseID);
				ca.setResourceType("1");
				//附件所带文件
				Element Item = message.element("Attachments");
				@SuppressWarnings("rawtypes")
				Iterator items = Item.elementIterator();
				while (items.hasNext()) {
					CaseAttachItem cai = new CaseAttachItem();
					Element elements = (Element) items.next();
					cai.setId(elements.attributeValue("ID"));
					cai.setName(elements.attributeValue("Name"));				
					cai.setUri(elements.attributeValue("Uri"));
					String Type = generateClassFromFileType(cai.getUri());
					cai.setItemType(Type);
					cai.setCaseAttchId(ca.getId());
					list.add(cai);
				}
				ca.setAttachItemList(list);
				attachlist.add(ca);
			}
 		} catch (DocumentException e) {
			e.printStackTrace();
			System.out.println("解析案件信息出错！");
		}
		return attachlist;
	}
	
	/**
	 * xml-->案件主体信息及其相关附件、文件信息
	 * @param xml
	 * @return
	 * @throws ParseException 
	 */
	private CaseVM getCaseMessagesFromXml(String xml) throws ParseException {		
		List<CaseAttachVM> calist = new ArrayList<CaseAttachVM>();
		CaseVM ccase = new CaseVM();
		try {					
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			//案件主体信息
			ccase.setId(root.attributeValue("ID"));
			ccase.setName(root.attributeValue("Name"));
			ccase.setCreator(Integer.parseInt(root.attributeValue("Creator")));
			ccase.setCreateTime(sdf.parse(root.attributeValue("CreateTime")));
			ccase.setCode(root.attributeValue("Code"));
			ccase.setCategoriesId(root.attributeValue("Categories"));
			ccase.setStartTime(sdf.parse(root.attributeValue("StartTime")));
			ccase.setSectionTime(sdf.parse(root.attributeValue("SectionTime")));
			ccase.setAddress(root.attributeValue("Address"));
			ccase.setSummary(root.attributeValue("Summary"));
			ccase.setStatus(root.attributeValue("Status"));
			ccase.setCaseGroupId(root.attributeValue("CaseGroupId"));
			ccase.setIsregister(Boolean.valueOf(root.attributeValue("IsRegister")));			
			ccase.setUserGroupId(root.attributeValue("UserGroupId"));
			ccase.setLevel(root.attributeValue("Level"));
			ccase.setLongitude(root.attributeValue("Longitude"));
			ccase.setLatitude(root.attributeValue("Latitude"));
			ccase.setOrganizationId(Integer.parseInt(root.attributeValue("OrganizationID")));
			ccase.setDetectedunitId(Integer.parseInt(root.attributeValue("DetectedUnit")));
			ccase.setOnlyWithResource(Boolean.valueOf(root.attributeValue("OnlyWithResource")));
			//案件附件信息
			Element messageList = root.element("MessageList");
			@SuppressWarnings("rawtypes")
			Iterator item = messageList.elementIterator();
			while (item.hasNext()) {
				CaseAttachVM ca = new CaseAttachVM();
				List<CaseAttachItem> list = new ArrayList<CaseAttachItem>();									
				Element message = (Element) item.next();
					
				ca.setId(message.attributeValue("ID"));
				ca.setName(message.attributeValue("Name"));
				ca.setDescription(message.attributeValue("Description"));
				ca.setCreator(Integer.valueOf(message.attributeValue("Creator")));
				ca.setCreateTime(sdf.parse(message.attributeValue("CreateTime")));
				ca.setMessageType(message.attributeValue("MessageType"));
				ca.setCaseId(ccase.getId());
				ca.setResourceType("1");
				//附件所带文件
				Element Item = message.element("Attachments");
				@SuppressWarnings("rawtypes")
				Iterator items = Item.elementIterator();
				while (items.hasNext()) {
					CaseAttachItem cai = new CaseAttachItem();
					Element elements = (Element) items.next();
					cai.setId(elements.attributeValue("ID"));
					cai.setName(elements.attributeValue("Name"));
					cai.setUri(elements.attributeValue("Uri"));
					String Type = generateClassFromFileType(cai.getUri());
					cai.setItemType(Type);
					cai.setCaseAttchId(ca.getId());
					list.add(cai);
				}
				ca.setAttachItemList(list);
				calist.add(ca);
			}
            ccase.setCaseAttachVMlist(calist);

 		} catch (DocumentException e) {
			e.printStackTrace();
			System.out.println("解析案件信息出错！");
		}
		return ccase;
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
        Case caseInfo = new Case();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		if (caseID != null) {
			sb.append("<CCase ID=\"" + caseID + "\" ");
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

	//	CaseAttachItem item = new  CaseAttachItem();

		String temp = "<?xml version=\"1.0\" encoding=\"utf-8\"?><CCaseMessageAdd CasdID=\""
				+ caseId
				+ "\">"
				+ "<MessageItem ID=\""
				+ attach.getId()
				+ "\" Name=\""
				+ attach.getName()
				+ "\" Description=\""
				+ attach.getDescription()
				+ "\" MessageType=\""
				+ (attach.getMessageType() == null ? "4" : attach.getMessageType())
				+ "\" IsTopMost=\"false\">"
				+ "<Attachments>";
				
			
		for(CaseAttachItem item:attach.getAttachItemList()){
			temp += "<Item ID=\""
					+ item.getId()
					+ "\" Name=\""
					+ item.getName()		
					+ "\" Creator=\"0\" CreateTime=\""
					+ time
					+ "\" Uri=\""
					+ item.getUri()
					+ "\" Type=\""
					+ item.getItemType()
					+ "\"/>";
		}
		temp +=  "</Attachments></MessageItem></CCaseMessageAdd>";
		// return sb.toString();
		return temp;
		
	}


	
	 /** 文件类型
	 * @param uri
	 * @return
	 */
	private String generateClassFromFileType(String uri){
		if(uri == null || "".equals(uri))
			return "";
		String fileType=uri.substring(uri.lastIndexOf(".")+1);
 		if(fileType.toLowerCase().equals("png")||fileType.equals("jpg")||fileType.equals("jpeg")||fileType.equals("gif")||fileType.equals("ico")||fileType.equals("bmp")){
			return "Image"; 
		}else if(fileType.equals("doc")||fileType.equals("docx")){
			return "document"; 
		}else if(fileType.equals("ppt")||fileType.equals("pptx")){
			return "document"; 
		}else if(fileType.equals("xls")||fileType.equals("xlsx")){
			return "document"; 
		}else if(fileType.equals("txt")||fileType.equals("log")){
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
	public void testConnect() throws DocumentException, ParseException {
		int i = deleteCaseAttach("8d963758-92f1-1d84-241a-e51e05050505","ecc83e0f-1522-4b89-ac42-a735daf045f8");
		System.out.println("dddddd:"+i);
	}
}
