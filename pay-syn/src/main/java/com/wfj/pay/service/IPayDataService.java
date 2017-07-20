package com.wfj.pay.service;

import com.wfj.pay.dto.MqBean;
import com.wfj.pay.dto.PayDataDTO;
import com.wfj.pay.dto.PayRefundDataDTO;

/**
 * Created by wjg on 2017/7/19.
 */
public interface IPayDataService {

    void savePayTrade(MqBean<PayDataDTO> mqBean);

    void saveRefundTrade(MqBean<PayRefundDataDTO> mqBean);
}
