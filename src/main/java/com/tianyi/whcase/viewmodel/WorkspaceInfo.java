package com.tianyi.whcase.viewmodel;

public class WorkspaceInfo {
	/*工作目录名称(用于显示)*/
	private String name;
	/*工作目录的NO*/
	private String no;
	/*可用空间*/
	private Integer value;
	/*总容量空间*/
	private Integer total;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
