package com.wfj.pay.dto;

import java.io.Serializable;

/**
 * Created by wjg on 2017/6/22.
 */
public class OrderResponseDTO implements Serializable{
    private static final long serialVersionUID = -1009017617586265037L;

    private String resultCode;
    private String resultMsg;
    private String errDetail;

    public OrderResponseDTO(String resultCode, String resultMsg, String errDetail) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.errDetail = errDetail;
    }

    public OrderResponseDTO() {
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getErrDetail() {
        return errDetail;
    }

    public void setErrDetail(String errDetail) {
        this.errDetail = errDetail;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OrderResponseDTO{");
        sb.append("resultCode='").append(resultCode).append('\'');
        sb.append(", resultMsg='").append(resultMsg).append('\'');
        sb.append(", errDetail='").append(errDetail).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
