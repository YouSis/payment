package com.wfj.pay.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.wfj.pay.dto.OrderRequestDTO;
import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.dto.RuleResultDTO;
import com.wfj.pay.rule.ICreateTradeRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wjg on 2017/6/22.
 */
@Service(version = "1.0.0", cluster = "failfast", timeout = 20000)
public class OfflinePayDubboImpl implements IOfflinePayDubbo {
    private Logger logger = LoggerFactory.getLogger(OfflinePayDubboImpl.class);
    @Autowired
    private ICreateTradeRule createTradeRule;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        logger.info("接到线下支付的报文:" + JSON.toJSONString(orderRequestDTO));
        long createOrderStart = System.currentTimeMillis();
        //1、校验
        RuleResultDTO validateResult = validate(orderRequestDTO);
        if (!validateResult.isSuccess()) {
            responseDTO.setResultCode("1");
            responseDTO.setResultMsg("false");
            responseDTO.setErrDetail(validateResult.getErrorMessage());
            return responseDTO;
        }
        //2、创建订单

        //3、去支付

        long createOrderEnd = System.currentTimeMillis();
        return null;
    }


    private RuleResultDTO validate(OrderRequestDTO orderRequestDTO) {
        RuleResultDTO result;
        result = createTradeRule.antiPhishingValidate(orderRequestDTO.getAntiPhishingKey());
        if (!result.isSuccess()) {
            return result;
        }
        result = createTradeRule.bpIdValidate(orderRequestDTO.getBpId());
        if (!result.isSuccess()) {
            return result;
        }
        result = createTradeRule.repeatSubmitValidate(orderRequestDTO);
        if (!result.isSuccess()) {
            return result;
        }
        result = createTradeRule.repeatCreateValidate(orderRequestDTO);
        if (!result.isSuccess()) {
            return result;
        }
        result = createTradeRule.md5Validate(orderRequestDTO);
        if (!result.isSuccess()) {
            return result;
        }
        result = createTradeRule.payChannelValidate(orderRequestDTO);
        if (!result.isSuccess()) {
            return result;
        }
        return new RuleResultDTO();
    }
}
