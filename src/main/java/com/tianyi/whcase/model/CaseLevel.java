package com.tianyi.whcase.model;

/**
 * 案件等级
 * @author lq
 *
 */
public class CaseLevel {
	
	/**
	 * 主键id，采用varchar(36)格式
	 */
    private String id;

    /**
     * 关联案件id
     */
    private String caseId;

    /**
     * 案件推送等级
     */
    private Integer caseLevel;

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

    public Integer getCaseLevel() {
        return caseLevel;
    }

    public void setCaseLevel(Integer caseLevel) {
        this.caseLevel = caseLevel;
    }
}