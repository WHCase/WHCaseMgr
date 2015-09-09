package com.tianyi.whcase.model;

import java.util.Date;

/**
 * 案件推送记录
 * @author lq
 *
 */
public class CaseSender {
	
	/**
	 * 主键id
	 */
    private String id;

    /**
     * 关联案件id
     */
    private String caseId;

    /**
     * 推送时间
     */
    private Date sendTime;

    /**
     * 推送人
     */
    private Integer sender;

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

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }
}