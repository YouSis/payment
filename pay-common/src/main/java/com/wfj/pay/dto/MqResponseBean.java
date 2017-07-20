package com.wfj.pay.dto;

/**
 * Created by wjg on 2017/7/19.
 */
public class MqResponseBean {
    private String messageID;
    private String serviceID;
    private Integer respStatus;
    private String bizCode;
    private String bizDesc;

    public MqResponseBean() {
    }

    public MqResponseBean(String messageID, String serviceID, Integer respStatus, String bizCode, String bizDesc) {
        this.messageID = messageID;
        this.serviceID = serviceID;
        this.respStatus = respStatus;
        this.bizCode = bizCode;
        this.bizDesc = bizDesc;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public Integer getRespStatus() {
        return respStatus;
    }

    public void setRespStatus(Integer respStatus) {
        this.respStatus = respStatus;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizDesc() {
        return bizDesc;
    }

    public void setBizDesc(String bizDesc) {
        this.bizDesc = bizDesc;
    }
}
