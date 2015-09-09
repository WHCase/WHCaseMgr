package com.tianyi.whcase.model;

import java.util.Date;

/**
 * ����������Ϣ
 * @author lq
 *
 */
public class Case {
	
	/**
	 * ����id��guid���ͣ�
	 * mysql���棺varchar(36)
	 */
    private String id;

    /**
     * �������
     */
    private String name;

    /**
     * ����¼����Ա
     */
    private Integer creator;

    /**
     * ¼��ʱ��
     */
    private Date createTime;

    /**
     * �������
     */
    private String code;

    /**
     * ��������id����Ӧ��������ֵ�
     */
    private String categoriesId;

    /**
     * ����ʱ��
     */
    private Date startTime;

    /**
     * �������
     */
    private String summary;

    /**
     * ����״̬ 
     * Handling - ���?
     * Detected - ����
     * CloseCase - ��
     *  
     */
    private String status;

    /**
     * ������������id��Guid����
     */
    private String caseGroupId;

    /**
     * �Ƿ�����
     *  true - �ǣ�
     *  false - ��
     */
    private Boolean isregister;

    /**
     * �����ȼ��� 
     * �����Ǳߺ���û���?Ĭ�϶���0��
     */
    private String level;

    /**
     * �����ص�γ��
     */
    private String longitude;

    /**
     * �����ص㾭��
     */
    private String latitude;

    /**
     * ��������Ͻ��λID 
     * ���������������� �ο���λ�ֵ䣩
     */
    private Integer organizationId;

    /**
     * ���Ƶ�λID 
     * (Ͻ��λID�� �ο���λ�ֵ�) 
     * -1��ʾ��
     */
    private Integer detectedunitId;

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

    public String getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(String categoriesId) {
        this.categoriesId = categoriesId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCaseGroupId() {
        return caseGroupId;
    }

    public void setCaseGroupId(String caseGroupId) {
        this.caseGroupId = caseGroupId;
    }

    public Boolean getIsregister() {
        return isregister;
    }

    public void setIsregister(Boolean isregister) {
        this.isregister = isregister;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getDetectedunitId() {
        return detectedunitId;
    }

    public void setDetectedunitId(Integer detectedunitId) {
        this.detectedunitId = detectedunitId;
    }
}