package com.tianyi.whcase.model;

/**
 * �������Ͷ�Ӧ��֯������Ϣ
 * һ�Զ��ϵ��
 * һ��������ͬʱ���͵�������������ͬʱ����
 * @author lq
 *
 */
public class CaseOrgan {
	
	/**
	 * ����id
	 */
    private String id;

    /**
     * ��������id
     */
    private String caseId;

    /**
     * ���յ�λid
     */
    private Integer organizationId;

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

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }
}