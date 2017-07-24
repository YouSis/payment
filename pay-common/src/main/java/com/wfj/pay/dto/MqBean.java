package com.wfj.pay.dto;

/**
 * Created by wjg on 2017/7/19.
 */
public class MqBean<T> {
    private MqHeaderBean header;
    private T data;


    public MqHeaderBean getHeader() {
        return header;
    }

    public void setHeader(MqHeaderBean header) {
        this.header = header;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
