package com.tianyi.whcase.model;

import java.util.Date;

/**
 * �������ͼ�¼
 * @author lq
 *
 */
public class CaseSender {
	
	/**
	 * ����id
	 */
    private String id;

    /**
     * ��������id
     */
    private String caseId;

    /**
     * ����ʱ��
     */
    private Date sendTime;

    /**
     * ������
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