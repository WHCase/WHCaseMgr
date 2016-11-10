package com.tianyi.whcase.viewmodel;

public class MediaSvrStatus {
	private String svrNo;
	private String svrName;
	private String statusCode;
	private String cpu;
	private String memory;
	private String freeMemory;
	private String serverNo;
	private String serverAddress;
	private int port;
	private int videoPort;
	private int nginxPort ;
	public String getSvrNo() {
		return svrNo;
	}
	public void setSvrNo(String svrNo) {
		this.svrNo = svrNo;
	}
	public String getSvrName() {
		return svrName;
	}
	public void setSvrName(String svrName) {
		this.svrName = svrName;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getFreeMemory() {
		return freeMemory;
	}
	public void setFreeMemory(String freeMemory) {
		this.freeMemory = freeMemory;
	}
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getVideoPort() {
		return videoPort;
	}
	public void setVideoPort(int videoPort) {
		this.videoPort = videoPort;
	}
	public String getServerNo() {
		return serverNo;
	}
	public void setServerNo(String serverNo) {
		this.serverNo = serverNo;
	}
	public int getNginxPort() {
		return nginxPort;
	}
	public void setNginxPort(int nginxPort) {
		this.nginxPort = nginxPort;
	}
	
}
