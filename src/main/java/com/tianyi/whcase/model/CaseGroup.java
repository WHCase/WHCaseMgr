package com.tianyi.whcase.model;

import java.util.Date;

/**
 * 案件串并案件信息
 * @author lq
 *
 */
public class CaseGroup {	
	
	/**
	 * 串并案id，与捷尚一致，采用varchar(36)格式 
	 */
    private String id;

    /**
     * 关联案件id
     */
    private String caseId;

    /**
     * 串并案件名称
     */
    private String name;

    /**
     * 创建者
     */
    private Integer creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 串并案编号
     */
    private String code;

    /**
     * 案件联系人
     */
    private String contactPerson;

    /**
     * 串案审核状态 
     * Audit-待审核, 
     * Approved-已通过 
     * Unapproved- 未通过
     */
    private String auditstate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getAuditstate() {
        return auditstate;
    }

    public void setAuditstate(String auditstate) {
        this.auditstate = auditstate;
    }
}