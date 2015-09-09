package com.tianyi.whcase.model;

import java.util.Date;

/**
 * 案件反馈信息
 * @author lq
 *
 */
public class CaseFeed {
	
	/**
	 * 反馈信息id，采用Guid类型
	 * varchar(36)
	 */
    private String id;

    /**
     * 关联案件id
     */
    private String caseId;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 处理结果
     */
    private String caseResult;

    /**
     * 反馈时间
     */
    private Date createTime;

    /**
     * 录入机构
     */
    private Integer organizationId;

    /**
     * 录入人员信息
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