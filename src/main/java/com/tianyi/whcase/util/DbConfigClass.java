package com.tianyi.whcase.util;

public class DbConfigClass {
	private static String url = null; // 地址
	private static String port = null; // 端口
	
	public DbConfigClass() throws ClassNotFoundException {

	} 
	
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		DbConfigClass.url = url;
	}
	public static String getPort() {
		return port;
	}
	public static void setPort(String port) {
		DbConfigClass.port = port;
	}
	

	private static void initParams() {
		url = DbConfig.getInstance().getIpUrl();
		port = DbConfig.getInstance().getPort(); 
	}
	
}
