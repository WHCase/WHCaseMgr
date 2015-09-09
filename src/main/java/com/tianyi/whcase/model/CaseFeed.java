package com.tianyi.whcase.model;

import java.util.Date;

/**
 * ����������Ϣ
 * @author lq
 *
 */
public class CaseFeed {
	
	/**
	 * ������Ϣid������Guid����
	 * varchar(36)
	 */
    private String id;

    /**
     * ��������id
     */
    private String caseId;

    /**
     * ��������
     */
    private String content;

    /**
     * ������
     */
    private String caseResult;

    /**
     * ����ʱ��
     */
    private Date createTime;

    /**
     * ¼�����
     */
    private Integer organizationId;

    /**
     * ¼����Ա��Ϣ
     */
    private Integer creator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCaseResult() {
        return caseResult;
    }

    public void setCaseResult(String caseResult) {
        this.caseResult = caseResult;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }
}