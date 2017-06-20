package com.wfj.pay.po;

import java.sql.Timestamp;

/**
 * Created by wjg on 2017/5/15.
 */
public class MemberScore {
    private long sid;
    private String storeNo;
    private String cid;
    private double score;
    private Timestamp updateTime;
    private String updateChannel;
    private String remark;

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateChannel() {
        return updateChannel;
    }

    public void setUpdateChannel(String updateChannel) {
        this.updateChannel = updateChannel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MemberScore{" +
                "sid=" + sid +
                ", storeNo='" + storeNo + '\'' +
                ", cid='" + cid + '\'' +
                ", score=" + score +
                ", updateTime=" + updateTime +
                ", updateChannel='" + updateChannel + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
