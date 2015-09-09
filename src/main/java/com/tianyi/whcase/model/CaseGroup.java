package com.tianyi.whcase.model;

import java.util.Date;

/**
 * ��������������Ϣ
 * @author lq
 *
 */
public class CaseGroup {	
	
	/**
	 * ������id�������һ�£�����varchar(36)��ʽ 
	 */
    private String id;

    /**
     * ��������id
     */
    private String caseId;

    /**
     * ������������
     */
    private String name;

    /**
     * ������
     */
    private Integer creator;

    /**
     * ����ʱ��
     */
    private Date createTime;

    /**
     * ���������
     */
    private String code;

    /**
     * ������ϵ��
     */
    private String contactPerson;

    /**
     * �������״̬ 
     * Audit-�����, 
     * Approved-��ͨ�� 
     * Unapproved- δͨ��
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