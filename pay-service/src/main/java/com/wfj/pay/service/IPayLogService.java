package com.wfj.pay.service;

import java.util.Map;

/**
 * Created by wjg on 2017/6/26.
 */
public interface IPayLogService {
    /**
     * 保存日志
     * @param logMap
     * @param modelKey
     * @param status
     */
    void saveLog(Map<String,Object> logMap, String modelKey, String status);
}
