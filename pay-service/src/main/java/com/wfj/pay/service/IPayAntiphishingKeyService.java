package com.wfj.pay.service;

/**
 * Created by wjg on 2017/6/30.
 */
public interface IPayAntiphishingKeyService {
    /**
     * 获取防钓鱼时间戳
     * @return
     */
    String getAntiPhishingKey();
}
