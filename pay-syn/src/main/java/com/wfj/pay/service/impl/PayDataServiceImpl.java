package com.wfj.pay.service.impl;

import com.wfj.pay.dao.PayLogRepository;
import com.wfj.pay.dao.PayRefundLogRepository;
import com.wfj.pay.dao.PayRefundTradeRepository;
import com.wfj.pay.dao.PayTradeRepository;
import com.wfj.pay.dto.MqBean;
import com.wfj.pay.dto.PayDataDTO;
import com.wfj.pay.dto.PayRefundDataDTO;
import com.wfj.pay.service.IPayDataService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

/**
 * Created by wjg on 2017/7/19.
 */
@Service
public class PayDataServiceImpl implements IPayDataService{
    @Autowired
    private PayTradeRepository payTradeRepository;
    @Autowired
    private PayLogRepository  payLogRepository;
    @Autowired
    private PayRefundTradeRepository refundTradeRepository;
    @Autowired
    private PayRefundLogRepository refundLogRepository;
    @Override
    public void savePayTrade(MqBean<PayDataDTO> mqBean) {
        payTradeRepository.save(mqBean.getData().getPayTradePO());
        payLogRepository.save(mqBean.getData().getPayLogPOList());
    }

    @Override
    public void saveRefundTrade(MqBean<PayRefundDataDTO> mqBean) {
        refundTradeRepository.save(mqBean.getData().getRefundTradePO());
        refundLogRepository.save(mqBean.getData().getRefundLogs());
    }
}
