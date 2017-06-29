package com.wfj.pay.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.wfj.pay.constant.PayOrderStatus;
import com.wfj.pay.constant.PayTypeEnum;
import com.wfj.pay.constant.TradeStatusConstant;
import com.wfj.pay.dto.*;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.rule.ICreateTradeRule;
import com.wfj.pay.rule.IQueryTradeRule;
import com.wfj.pay.service.IPayStrategyService;
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.utils.ObjectUtil;
import com.wfj.pay.utils.OrderEncryptUtils;
import com.wfj.pay.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by wjg on 2017/6/22.
 */
@Service(version = "1.0.0", cluster = "failfast", timeout = 20000)
public class OfflinePayDubboImpl implements IOfflinePayDubbo {
    private Logger logger = LoggerFactory.getLogger(OfflinePayDubboImpl.class);
    @Autowired
    private ICreateTradeRule createTradeRule;
    @Autowired
    private IQueryTradeRule queryTradeRule;
    @Autowired
    private IPayTradeService payTradeService;
    @Autowired
    private List<IPayStrategyService> strategyList;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        logger.info("--->接到线下支付的报文:" + JSON.toJSONString(orderRequestDTO));
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
        PayTradeDTO payTradeDTO = payTradeService.createOrder(orderRequestDTO);
        //3、去支付
        responseDTO = pay(payTradeDTO);
        long createOrderEnd = System.currentTimeMillis();
        logger.info("--->返回线下支付的响应:" + JSON.toJSONString(responseDTO));
        logger.info("--->" + payTradeDTO.getOrderTradeNo() + "订单支付请求总耗时：" + (createOrderEnd - createOrderStart) + "ms");
        return responseDTO;
    }

    @Override
    public OrderResponseDTO queryOrder(OrderQueryRequestDTO orderQueryRequestDTO) {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        logger.info("--->接到线下单笔查询的报文:" + JSON.toJSONString(orderQueryRequestDTO));
        long queryOrderStart = System.currentTimeMillis();
        //1、校验
        RuleResultDTO validateResult = validateQuery(orderQueryRequestDTO);
        if (!validateResult.isSuccess()) {
            responseDTO.setResultCode("1");
            responseDTO.setResultMsg("false");
            responseDTO.setErrDetail(validateResult.getErrorMessage());
            return responseDTO;
        }
        //2、发起查询
        PayTradePO tradePO;
        if (StringUtils.isNotEmpty(orderQueryRequestDTO.getOrderTradeNo())) {
            tradePO = payTradeService.findByBpIdAndOrderTradeNo(Long.valueOf(orderQueryRequestDTO.getBpId()), orderQueryRequestDTO.getOrderTradeNo());
        } else {
            tradePO = payTradeService.findByBpIdAndBpOrderId(Long.valueOf(orderQueryRequestDTO.getBpId()), orderQueryRequestDTO.getBpOrderId());
        }
        responseDTO = query(tradePO);
        long queryOrderEnd = System.currentTimeMillis();
        logger.info("--->返回线下单笔查询的响应:" + JSON.toJSONString(responseDTO));
        logger.info("--->" + tradePO.getOrderTradeNo() + "订单支付请求总耗时：" + (queryOrderEnd - queryOrderStart) + "ms");
        return responseDTO;
    }

    /**
     * 去支付
     *
     * @param payTradeDTO
     * @return
     */
    private OrderResponseDTO pay(PayTradeDTO payTradeDTO) {
        PayTypeEnum typeEnum = PayTypeEnum.valueOf(payTradeDTO.getPayType());
        Optional<IPayStrategyService> payStrategy = strategyList.stream().filter(strategy -> strategy.match(typeEnum)).findFirst();
        return payStrategy.get().toPay(payTradeDTO);
    }

    /**
     * 发起查询
     *
     * @param payTradePO
     * @return
     */
    private OrderResponseDTO query(PayTradePO payTradePO) {
        //1、如果本地是支付状态，支付返回
        if (PayOrderStatus.PAYED.equals(payTradePO.getStatus())) {
            return payTradeService.transfer(payTradePO);
        }
        //2、如果是未支付状态则调用对应的策略类进行查询
        PayTypeEnum typeEnum = PayTypeEnum.valueOf(payTradePO.getPayType());
        Optional<IPayStrategyService> payStrategy = strategyList.stream().filter(strategy -> strategy.match(typeEnum)).findFirst();
        return payStrategy.get().queryOrder(payTradePO);
    }

    /**
     * 创建订单的校验
     *
     * @param orderRequestDTO
     * @return
     */
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
        return result;
    }

    /**
     * 订单查询的校验
     *
     * @param orderQueryRequestDTO
     * @return
     */
    private RuleResultDTO validateQuery(OrderQueryRequestDTO orderQueryRequestDTO) {
        RuleResultDTO result;
        result = queryTradeRule.bpIdValidate(orderQueryRequestDTO.getBpId());
        if (!result.isSuccess()) {
            return result;
        }
        result = queryTradeRule.orderExistValidate(orderQueryRequestDTO);
        if (!result.isSuccess()) {
            return result;
        }
        return result;
    }
}
