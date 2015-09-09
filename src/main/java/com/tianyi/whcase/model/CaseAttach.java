package com.tianyi.whcase.model;

/**
 * 案件关联附件信息
 * @author lq
 *
 */
public class CaseAttach {
	
	/**
	 * 附件id，与捷尚一致，采用Guid类型
	 * mysql里面varchar(36)
	 */
    private String id;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 关联案件的id Guid类型
     */
    private String caseId;

    /**
     * 附件描述
     */
    private String description;

    /**
     * 附件创建者
     */
    private Integer creator;

    /**
     * 附件类型
     */
    private String messageType;

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