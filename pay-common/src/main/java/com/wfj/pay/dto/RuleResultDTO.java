package com.wfj.pay.dto;

/**
 * Created by wjg on 2017/6/23.
 */
public class RuleResultDTO {
    private String errorMessage;
    private boolean isSuccess = true;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RuleResultDTO{");
        sb.append("errorMessage='").append(errorMessage).append('\'');
        sb.append(", isSuccess=").append(isSuccess);
        sb.append('}');
        return sb.toString();
    }
}
