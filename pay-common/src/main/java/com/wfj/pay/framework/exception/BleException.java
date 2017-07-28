package com.wfj.pay.framework.exception;

/**
 * Created by kongqf on 2017/7/25.
 */
public class BleException extends RuntimeException {
    private static final long serialVersionUID = 8710396445793589764L;
    private String code = null;
    private String errLever;

    public BleException() {

    }

    public BleException(String message) {
        super(message);
    }

    public BleException(String message, Throwable cause) {
        super(message, cause);
    }

    public BleException(String code, String message, String errLever) {
        super(message);
        this.code = code;
        this.errLever = errLever;
    }

    public BleException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getErrLever() {
        return errLever;
    }

    public void setErrLever(String errLever) {
        this.errLever = errLever;
    }
}
