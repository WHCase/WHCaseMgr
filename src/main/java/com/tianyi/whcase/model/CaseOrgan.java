package com.tianyi.whcase.model;

/**
 * 案件推送对应组织机构信息
 * 一对多关系，
 * 一个案件可同时推送到其他几个机构同时侦破
 * @author lq
 *
 */
public class CaseOrgan {
	
	/**
	 * 主键id
	 */
    private String id;

    /**
     * 关联案件id
     */
    private String caseId;

    /**
     * 接收单位id
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