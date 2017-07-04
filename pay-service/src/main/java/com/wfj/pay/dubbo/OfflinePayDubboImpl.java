package com.wfj.pay.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.wfj.pay.constant.CloseOrderErrorEnum;
import com.wfj.pay.constant.PayTradeStatus;
import com.wfj.pay.constant.PayTypeEnum;
import com.wfj.pay.dto.*;
import com.wfj.pay.po.PayRefundTradePO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.rule.*;
import com.wfj.pay.service.IPayRefundTradeService;
import com.wfj.pay.service.IPayStrategyService;
import com.wfj.pay.service.IPayTradeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
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
    private ICloseTradeRule closeTradeRule;
    @Autowired
    private ICreateRefundTradeRule createRefundTradeRule;
    @Autowired
    private IQueryRefundTradeRule queryRefundTradeRule;
    @Autowired
    private IPayTradeService payTradeService;
    @Autowired
    private IPayRefundTradeService refundTradeService;

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
        responseDTO = payTradeService.pay(payTradeDTO);
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
        PayTradePO tradePO = payTradeService.findByBpOrderIdOrOrderTradeNo(Long.valueOf(orderQueryRequestDTO.getBpId()), orderQueryRequestDTO.getBpOrderId(), orderQueryRequestDTO.getOrderTradeNo());
        responseDTO = payTradeService.query(tradePO);
        long queryOrderEnd = System.currentTimeMillis();
        logger.info("--->返回线下单笔查询的响应:" + JSON.toJSONString(responseDTO));
        logger.info("--->" + tradePO.getOrderTradeNo() + "订单查询请求总耗时：" + (queryOrderEnd - queryOrderStart) + "ms");
        return responseDTO;
    }

    @Override
    public OrderResponseDTO closeOrder(OrderCloseRequestDTO orderCloseRequestDTO) {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        logger.info("--->接到线下关闭订单的报文:" + JSON.toJSONString(orderCloseRequestDTO));
        long closeOrderStart = System.currentTimeMillis();
        //1、校验
        RuleResultDTO validateResult = validateClose(orderCloseRequestDTO);
        if (!validateResult.isSuccess()) {
            responseDTO.setResultCode("1");
            responseDTO.setResultMsg("false");
            responseDTO.setErrDetail(validateResult.getErrorMessage());
            return responseDTO;
        }
        //2、发起关闭
        PayTradePO tradePO = payTradeService.findByBpOrderIdOrOrderTradeNo(Long.valueOf(orderCloseRequestDTO.getBpId()),orderCloseRequestDTO.getBpOrderId(),orderCloseRequestDTO.getOrderTradeNo());
        responseDTO = payTradeService.close(tradePO);
        long closeOrderEnd = System.currentTimeMillis();
        logger.info("--->返回线下关闭订单的响应:" + JSON.toJSONString(responseDTO));
        logger.info("--->" + tradePO.getOrderTradeNo() + "订单关闭请求总耗时：" + (closeOrderEnd - closeOrderStart) + "ms");
        return responseDTO;
    }

    @Override
    public RefundOrderResponseDTO createRefundOrder(RefundOrderRequestDTO refundOrderRequestDTO) {
        RefundOrderResponseDTO responseDTO = new RefundOrderResponseDTO();
        logger.info("--->接到线下退款的报文:" + JSON.toJSONString(refundOrderRequestDTO));
        long createRefundStart = System.currentTimeMillis();
        //1、校验
        RuleResultDTO validateResult = validateCreateRefund(refundOrderRequestDTO);
        if (!validateResult.isSuccess()) {
            responseDTO.setResultCode("1");
            responseDTO.setResultMsg("false");
            responseDTO.setErrDetail(validateResult.getErrorMessage());
            return responseDTO;
        }
        //2、创建退款单
        PayRefundTradePO refundTradePO = refundTradeService.createRefundTrade(refundOrderRequestDTO);
        //3、发起退款
        responseDTO = refundTradeService.refund(refundTradePO);
        long createRefundEnd = System.currentTimeMillis();
        logger.info("--->返回线下关闭订单的响应:" + JSON.toJSONString(responseDTO));
        logger.info("--->" + (StringUtils.isNotEmpty(refundOrderRequestDTO.getOrderTradeNo())?refundOrderRequestDTO.getOrderTradeNo():refundOrderRequestDTO.getBpOrderId()) + "订单退款请求总耗时：" + (createRefundEnd - createRefundStart) + "ms");
        return responseDTO;
    }

    @Override
    public RefundOrderResponseDTO queryRefundOrder(RefundOrderQueryRequestDTO refundQueryDTO) {
        RefundOrderResponseDTO responseDTO = new RefundOrderResponseDTO();
        logger.info("--->接到线下退款查询的报文:" + JSON.toJSONString(refundQueryDTO));
        long beginTime = System.currentTimeMillis();
        //1、校验
        RuleResultDTO validateResult = validateQueryRefund(refundQueryDTO);
        if (!validateResult.isSuccess()) {
            responseDTO.setResultCode("1");
            responseDTO.setResultMsg("false");
            responseDTO.setErrDetail(validateResult.getErrorMessage());
            return responseDTO;
        }
        //2、发起查询
        responseDTO = refundTradeService.refundQuery(refundQueryDTO);
        long endTime = System.currentTimeMillis();
        logger.info("--->返回线下退款查询的响应:" + JSON.toJSONString(responseDTO));
        logger.info("--->" + (StringUtils.isNotEmpty(refundQueryDTO.getOrderTradeNo())?refundQueryDTO.getOrderTradeNo():refundQueryDTO.getBpRefundOrderId()) + "订单退款查询请求总耗时：" + (endTime - beginTime) + "ms");
        return responseDTO;
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
        result = createTradeRule.md5Validate(orderRequestDTO);
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
        result = queryTradeRule.payTypeValidate(orderQueryRequestDTO);
        if (!result.isSuccess()) {
            return result;
        }
        return result;
    }

    /**
     * 关闭订单的校验
     *
     * @param orderCloseRequestDTO
     * @return
     */
    private RuleResultDTO validateClose(OrderCloseRequestDTO orderCloseRequestDTO) {
        RuleResultDTO result;
        result = closeTradeRule.antiPhishingValidate(orderCloseRequestDTO.getAntiPhishingKey());
        if (!result.isSuccess()) {
            return result;
        }
        result = closeTradeRule.md5Validate(orderCloseRequestDTO);
        if (!result.isSuccess()) {
            return result;
        }
        result = closeTradeRule.bpIdValidate(orderCloseRequestDTO.getBpId());
        if (!result.isSuccess()) {
            return result;
        }
        result = closeTradeRule.orderExistValidate(orderCloseRequestDTO);
        if (!result.isSuccess()) {
            return result;
        }
        return result;
    }

    /**
     * 退款的校验
     * @param refundOrderRequestDTO
     * @return
     */
    private RuleResultDTO validateCreateRefund(RefundOrderRequestDTO refundOrderRequestDTO) {
        RuleResultDTO result;
        result = createRefundTradeRule.antiPhishingValidate(refundOrderRequestDTO.getAntiPhishingKey());
        if (!result.isSuccess()) {
            return result;
        }
        result = createRefundTradeRule.md5Validate(refundOrderRequestDTO);
        if (!result.isSuccess()) {
            return result;
        }
        result = createRefundTradeRule.bpIdValidate(refundOrderRequestDTO.getBpId());
        if (!result.isSuccess()) {
            return result;
        }
        result = createRefundTradeRule.orderExistValidate(refundOrderRequestDTO);
        if (!result.isSuccess()) {
            return result;
        }
        result = createRefundTradeRule.refundOrderExistValidate(refundOrderRequestDTO);
        if (!result.isSuccess()) {
            return result;
        }
        result = createRefundTradeRule.feeValidate(refundOrderRequestDTO);
        if (!result.isSuccess()) {
            return result;
        }
        return result;
    }

    /**
     * 退款查询的校验
     * @param refundQueryDTO
     * @return
     */
    private RuleResultDTO validateQueryRefund(RefundOrderQueryRequestDTO refundQueryDTO) {
        RuleResultDTO result;
        result = queryRefundTradeRule.bpIdValidate(refundQueryDTO.getBpId());
        if (!result.isSuccess()) {
            return result;
        }
        result = queryRefundTradeRule.refundExistValidate(refundQueryDTO);
        if (!result.isSuccess()) {
            return result;
        }
        return result;
    }

}
