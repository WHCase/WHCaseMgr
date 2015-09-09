package com.tianyi.whcase.model;

import java.util.Date;

/**
 * 案件主体信息
 * @author lq
 *
 */
public class Case {
	
	/**
	 * 主键id，guid类型，
	 * mysql里面：varchar(36)
	 */
    private String id;

    /**
     * 案件名称
     */
    private String name;

    /**
     * 案件录入人员
     */
    private Integer creator;

    /**
     * 录入时间
     */
    private Date createTime;

    /**
     * 案件编号
     */
    private String code;

    /**
     * 案件类型id，对应类型数据字典
     */
    private String categoriesId;

    /**
     * 案发时间
     */
    private Date startTime;

    /**
     * 案情简述
     */
    private String summary;

    /**
     * 案件状态 
     * Handling - 受理，
     * Detected - 已破
     * CloseCase - 销案
     *  
     */
    private String status;

    /**
     * 关联串案并案id，Guid类型
     */
    private String caseGroupId;

    /**
     * 是否立案
     *  true - 是，
     *  false - 否
     */
    private Boolean isregister;

    /**
     * 案件等级： 
     * 捷尚那边好像没处理，默认都是0；
     */
    private String level;

    /**
     * 案发地点纬度
     */
    private String longitude;

    /**
     * 案发地点经度
     */
    private String latitude;

    /**
     * 案件所属辖区单位ID 
     * （即案件所属区域， 参考单位字典）
     */
    private Integer organizationId;

    /**
     * 侦破单位ID 
     * (辖区单位ID， 参考单位字典) 
     * -1表示无
     */
    private Integer detectedunitId;

    /**
     * 接收状态
     * 0：未接受，捷尚调用接口写入的时候默认为0，均未接受
     * 1：已接收，
     * 2：已反馈，
     * 9：拒收（不用管、预留在此）
     */
    private String receiveStatus;

    /**
     * 案件接收时间
     */
    private Date receiveTime;

    /**
     * 案件推送时间
     */
    private Date senderTime;

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

    public String getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getSenderTime() {
        return senderTime;
    }

    public void setSenderTime(Date senderTime) {
        this.senderTime = senderTime;
    }
}