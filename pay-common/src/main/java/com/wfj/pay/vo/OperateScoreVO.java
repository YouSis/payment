package com.wfj.pay.vo;

/**
 * Created by wjg on 2017/5/16.
 */
public class OperateScoreVO {
    private String storeNo;
    private String cid;
    private Double scoreFee;
    private String channel;

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Double getScoreFee() {
        return scoreFee;
    }

    public void setScoreFee(Double scoreFee) {
        this.scoreFee = scoreFee;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "OperateScoreVO{" +
                "storeNo='" + storeNo + '\'' +
                ", cid='" + cid + '\'' +
                ", scoreFee=" + scoreFee +
                ", channel='" + channel + '\'' +
                '}';
    }
}
