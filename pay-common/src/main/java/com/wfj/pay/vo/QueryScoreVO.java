package com.wfj.pay.vo;

/**
 * Created by wjg on 2017/5/16.
 */
public class QueryScoreVO {
    private String storeNo;
    private String cid;

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

    @Override
    public String toString() {
        return "QueryScoreVO{" +
                "storeNo='" + storeNo + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}
