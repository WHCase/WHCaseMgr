package com.tianyi.whcase.model;

/**
 * ��������������Ϣ
 * @author lq
 *
 */
public class CaseAttach {
	
	/**
	 * ����id�������һ�£�����Guid����
	 * mysql����varchar(36)
	 */
    private String id;

    /**
     * �������
     */
    private String name;

    /**
     * ����������id Guid����
     */
    private String caseId;

    /**
     * ��������
     */
    private String description;

    /**
     * ����������
     */
    private Integer creator;

    /**
     * ��������
     */
    private String messageType;
    
    private String resourceType;
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}