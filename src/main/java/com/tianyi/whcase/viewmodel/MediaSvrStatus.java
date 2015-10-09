package com.tianyi.whcase.viewmodel;

public class MediaSvrStatus {
	private String svrNo;
	private String svrName;
	private String statusCode;
	private String cpu;
	private String memory;
	private String freeMemory;
	private String serverAddress;
	private int port;
	private int videoPort;
	private int nginPort ;
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
	public int getNginPort() {
		return nginPort;
	}
	public void setNginPort(int nginPort) {
		this.nginPort = nginPort;
	}
	
}
