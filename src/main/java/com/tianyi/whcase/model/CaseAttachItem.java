package com.tianyi.whcase.model;

/**
 * 附件子文件列表
 * @author lq
 *
 */
public class CaseAttachItem {
	
	/**
	 * 文件id，与捷尚一致，采用varchar(36)格式 
	 */
    private String id;

    /**
     * 关联附件id
     */
    private String caseAttchId;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件Uri 
     */
    private String uri;

    /**
     * 文件类型
     * Image，Video，Audio，Document，Unknown
     */
    private String itemType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseAttchId() {
        return caseAttchId;
    }

    public void setCaseAttchId(String caseAttchId) {
        this.caseAttchId = caseAttchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}