package com.tianyi.whcase.model;

/**
 * �������ļ��б�
 * @author lq
 *
 */
public class CaseAttachItem {
	
	/**
	 * �ļ�id�������һ�£�����varchar(36)��ʽ 
	 */
    private String id;

    /**
     * ��������id
     */
    private String caseAttchId;

    /**
     * �ļ�����
     */
    private String name;

    /**
     * �ļ�Uri 
     */
    private String uri;

    /**
     * �ļ�����
     * Image��Video��Audio��Document��Unknown
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