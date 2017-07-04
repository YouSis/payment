package com.wfj.pay.service.impl;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.constant.PayLogConstant;
import com.wfj.pay.mapper.PayLogMapper;
import com.wfj.pay.mapper.PayRefundLogMapper;
import com.wfj.pay.po.PayLogPO;
import com.wfj.pay.po.PayRefundLogPO;
import com.wfj.pay.service.IPayLogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by wjg on 2017/6/26.
 */
@Service
public class PayLogServiceImpl implements IPayLogService {
    private Logger logger = LoggerFactory.getLogger(PayLogServiceImpl.class);
    private final static String openToken = "#{";
    private final static String closeToken = "}";
    @Autowired
    private PayLogMapper payLogMapper;
    @Autowired
    private PayRefundLogMapper refundLogMapper;

    @Override
    @DataSource("master")
    @Transactional
    public void saveLog(Map<String, Object> logMap, String logModel, String status) {
        String logContent = process(logMap,logModel,status);
        PayLogPO payLogPO = new PayLogPO();
        payLogPO.setOrderTradeNo(logMap.get("orderTradeNo").toString());
        payLogPO.setOwnPlatform("wfjpay");
        payLogPO.setContent(logContent);
        payLogPO.setCreateDate(new Timestamp(System.currentTimeMillis()));
        payLogPO.setStatus(status);

        payLogMapper.insert(payLogPO);
    }

    @Override
    @DataSource("master")
    @Transactional
    public void saveRefundLog(Map<String, Object> logMap, String modelKey, String status) {
        String logContent = process(logMap,modelKey,status);
        PayRefundLogPO refundLogPO = new PayRefundLogPO();
        refundLogPO.setOrderTradeNo(logMap.get("orderTradeNo").toString());
        refundLogPO.setRefundTradeNo(logMap.get("refundTradeNo").toString());
        refundLogPO.setContent(logContent);
        refundLogPO.setCreateDate(new Timestamp(System.currentTimeMillis()));
        refundLogPO.setStatus(Long.valueOf(status));
        refundLogMapper.insert(refundLogPO);
    }

    /**
     * 对日志模板内容进行替换
     *
     * @param text
     * @param logMap
     * @return
     */
    private String parse(String text, MapWraper<String, Object> logMap) {
        StringBuilder builder = new StringBuilder();
        if (text != null && text.length() > 0) {
            char[] src = text.toCharArray();
            int offset = 0;
            int start = text.indexOf(openToken, offset);
            while (start > -1) {
                int end = text.indexOf(closeToken, start);
                if (end == -1) {
                    builder.append(src, offset, src.length - offset);
                    offset = src.length;
                } else {
                    builder.append(src, offset, start - offset);
                    offset = start + openToken.length();
                    String content = new String(src, offset, end - offset);
                    builder.append(logMap.get(content));
                    offset = end + closeToken.length();
                }

                start = text.indexOf(openToken, offset);
            }
            if (offset < src.length) {
                builder.append(src, offset, src.length - offset);
            }
        }
        return builder.toString();
    }

    /**
     * 对空值进行处理
     *
     * @param <K>
     * @param <V>
     */
    private static class MapWraper<K, V> {
        private final Map<String, Object> map;
        private String DEFAULT_BLANK_VALUE = "无";

        public MapWraper(Map<String, Object> map) {
            this.map = map;
        }

        public MapWraper(Map<String, Object> map, String default_balnk_value) {
            this.map = map;
            this.DEFAULT_BLANK_VALUE = default_balnk_value;
        }

        public String get(String key) {
            Object value = map.get(key);
            if (ObjectUtils.isEmpty(value)) {
                return DEFAULT_BLANK_VALUE;
            }
            return value.toString();
        }
    }

    private String process(Map<String, Object> logMap, String logModel, String status) {
        if (StringUtils.isEmpty(logModel)) {
            throw new RuntimeException("日志模板key不能为空");
        }
        if (logMap == null || logMap.get("orderTradeNo") == null) {
            throw new RuntimeException("orderTradeNo必须存在，否则日志无效");
        }
        String logContent = parse(logModel, new MapWraper<>(logMap));

        if (status.equals(PayLogConstant.FAIL_NAME)
                && !ObjectUtils.isEmpty(logMap.get("errorMsg"))) {// 如果操作失败，将错误提示信息添加到logStatement前.
            logContent = "错误提示：" + logMap.get("errorMsg").toString()
                    + "\n\n处理信息：" + logContent;
        }
        if (logContent.length() > 900) {
            logContent = logContent.substring(0, 900) + "...";
        }
        return logContent;
    }
}
