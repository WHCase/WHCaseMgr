package com.tianyi.whcase.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.tianyi.whcase.viewmodel.CaseVM;
import com.tianyi.whcase.viewmodel.MediaSvrStatus;

public class XmlAnalysis {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	/**
	 * 获取的流媒体参数解析成对象
	 * @param xml
	 * @return
	 */
	public static MediaSvrStatus getMSSFromxml(String xml) {
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
			mss.setServerNo(configElement.attributeValue("ServerNo"));
			mss.setServerAddress(configElement.attributeValue("ServerAddress"));
			mss.setPort(Integer.parseInt(configElement.attributeValue("Port")));
			mss.setVideoPort(Integer.parseInt(configElement
					.attributeValue("VideoPort")));
			mss.setNginxPort(Integer.parseInt(configElement
					.attributeValue("NginxPort")));

			return mss;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 获取案件信息解析成对象
	 * @param xml
	 * @return
	 */
	public static CaseVM getCaseFromxml(String xml) {
		CaseVM cv = new CaseVM();
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Element cCase = root.element("CCase");
			cv.setId(cCase.attributeValue("ID"));
			cv.setName(cCase.attributeValue("Name"));
			cv.setCreator(Integer.valueOf(cCase.attributeValue("Creator")));
			Date ctime = sdf.parse(root.attributeValue("CreateTime"));
			cv.setCreateTime(ctime);
			cv.setCode(root.attributeValue("Code"));
			cv.setCategoriesId(root.attributeValue("Categories"));
			Date stime = sdf.parse(root.attributeValue("StartTime"));
			cv.setStartTime(stime);
			cv.setSummary(root.attributeValue("Summary"));
			cv.setStatus(root.attributeValue("Status"));
			if("true".equals(root.attributeValue("Isregister"))){
				 cv.setIsregister(true);
			}else{
				 cv.setIsregister(false);
			};
           
			cv.setCaseGroupId("UserGroupId");  // 用户所在组的id
			cv.setLevel(root.attributeValue("Level"));
			cv.setLongitude(root.attributeValue("Longitude"));
			cv.setLatitude(root.attributeValue("Latitude"));

			cv.setOrganizationId(Integer.parseInt(root
					.attributeValue("OrganizationID")));
			cv.setDetectedunitId(Integer.parseInt(root
					.attributeValue("DetectedUnit")));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		return cv;
	}
	
	
	/**
	 * 接口返回值
	 * @param returnNum
	 * @return
	 */
	public static String getReturnCodeXml(int returnNum) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<int>" + returnNum + "</int>");

		return sb.toString();
	}
}
