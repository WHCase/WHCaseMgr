package com.tianyi.whcase.model;

/**
 * �����ȼ�
 * @author lq
 *
 */
public class CaseLevel {
	
	/**
	 * ����id������varchar(36)��ʽ
	 */
    private String id;

    /**
     * ��������id
     */
    private String caseId;

    /**
     * �������͵ȼ�
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