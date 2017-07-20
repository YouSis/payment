package com.wfj.pay.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.wfj.pay.constant.PayOrderTerminalEnum;
import com.wfj.pay.constant.PayServiceEnum;
import com.wfj.pay.constant.PayTypeEnum;
import com.wfj.pay.dto.*;
import com.wfj.pay.dubbo.IOfflinePayDubbo;
import com.wfj.pay.utils.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by wjg on 2017/6/22.
 */
@Controller
public class OfflinePayController {
    private Logger logger = LoggerFactory.getLogger(OfflinePayController.class);

    @Reference(version = "1.0.0", cluster = "failfast")
    private IOfflinePayDubbo offlinePayDubbo;

    /**
     * 线下支付创建订单接口
     *
     * @param orderRequestDTO
     * @param result
     */
    @RequestMapping("front/order/shbOrderMsg")
    @ResponseBody
    public OrderResponseDTO createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO, BindingResult result, HttpServletRequest request) {
        logger.info("--->接到线下支付的请求:" + JSON.toJSONString(orderRequestDTO));
        OrderResponseDTO orderResponseDTO = validate(orderRequestDTO, result);
        //校验失败直接返回
        if (orderResponseDTO != null) {
            return orderResponseDTO;
        }
        orderRequestDTO.setPayIp(WebUtil.getIpAddress(request));
        try {
            orderResponseDTO = offlinePayDubbo.createOrder(orderRequestDTO);
        } catch (Exception e) {
            //调用dubbo失败，可能是超时，也可能是服务方出错了
            logger.error(e.toString(), e);
            orderResponseDTO = new OrderResponseDTO("1", "false", "系统错误,请重试");
        }
        logger.info("--->返回线下支付请求的响应:" + JSON.toJSONString(orderResponseDTO));
        return orderResponseDTO;
    }

    /**
     * 线下订单查询的接口
     *
     * @param orderQueryRequestDTO
     * @param result
     * @return
     */
    @RequestMapping("fron/order/shbSelectAnOrder")
    @ResponseBody
    public OrderResponseDTO queryOrder(@RequestBody @Valid OrderQueryRequestDTO orderQueryRequestDTO, BindingResult result) {
        logger.info("--->接到线下单笔查询的请求:" + JSON.toJSONString(orderQueryRequestDTO));
        OrderResponseDTO orderResponseDTO = validateQuery(orderQueryRequestDTO, result);
        //校验失败直接返回
        if (orderResponseDTO != null) {
            return orderResponseDTO;
        }
        try {
            orderResponseDTO = offlinePayDubbo.queryOrder(orderQueryRequestDTO);
        } catch (Exception e) {
            //调用dubbo失败，可能是超时，也可能是服务方出错了
            logger.error(e.toString(), e);
            orderResponseDTO = new OrderResponseDTO("1", "false", "系统错误,请重试");
        }
        logger.info("--->返回线下单笔查询的响应:" + JSON.toJSONString(orderResponseDTO));
        return orderResponseDTO;
    }


    /**
     * 线下关闭订单接口
     *
     * @param orderCloseRequestDTO
     * @param result
     * @return
     */
    @RequestMapping("front/order/closeOrder")
    @ResponseBody
    public OrderResponseDTO closeOrder(@RequestBody @Valid OrderCloseRequestDTO orderCloseRequestDTO, BindingResult result) {
        logger.info("--->接到线下关闭订单的请求:" + JSON.toJSONString(orderCloseRequestDTO));
        OrderResponseDTO orderResponseDTO = validateClose(orderCloseRequestDTO, result);
        //校验失败直接返回
        if (orderResponseDTO != null) {
            return orderResponseDTO;
        }
        try {
            orderResponseDTO = offlinePayDubbo.closeOrder(orderCloseRequestDTO);
        } catch (Exception e) {
            //调用dubbo失败，可能是超时，也可能是服务方出错了
            logger.error(e.toString(), e);
            orderResponseDTO = new OrderResponseDTO("1", "false", "系统错误,请重试");
        }
        logger.info("--->返回线下关闭订单的响应:" + JSON.toJSONString(orderResponseDTO));
        return orderResponseDTO;

    }

    /**
     * 线下退款接口
     *
     * @param refundOrderRequestDTO
     * @param result
     * @return
     */
    @RequestMapping("fron/order/createRefundOrder")
    @ResponseBody
    public RefundOrderResponseDTO createRefundOrder(@RequestBody @Valid RefundOrderRequestDTO refundOrderRequestDTO, BindingResult result) {
        logger.info("--->接到线下退款的请求:" + JSON.toJSONString(refundOrderRequestDTO));
        RefundOrderResponseDTO refundOrderResponseDTO = validateRefund(refundOrderRequestDTO, result);
        //校验失败直接返回
        if (refundOrderResponseDTO != null) {
            return refundOrderResponseDTO;
        }
        try {
            refundOrderResponseDTO = offlinePayDubbo.createRefundOrder(refundOrderRequestDTO);
        } catch (Exception e) {
            //调用dubbo失败，可能是超时，也可能是服务方出错了
            logger.error(e.toString(), e);
            refundOrderResponseDTO = new RefundOrderResponseDTO("1", "false", "系统错误,请重试");
        }
        logger.info("--->返回线下退款的响应:" + JSON.toJSONString(refundOrderResponseDTO));
        return refundOrderResponseDTO;
    }

    /**
     * 线下退款查询
     * @param refundOrderQueryRequestDTO
     * @param result
     * @return
     */
    @RequestMapping("fron/order/selectRefundOrderQuery")
    @ResponseBody
    public RefundOrderResponseDTO refundOrderQuery(@RequestBody @Valid RefundOrderQueryRequestDTO refundOrderQueryRequestDTO,BindingResult result){
        logger.info("--->接到线下退款查询的请求:" + JSON.toJSONString(refundOrderQueryRequestDTO));
        RefundOrderResponseDTO refundOrderResponseDTO = validateQueryRefund(refundOrderQueryRequestDTO, result);
        //校验失败直接返回
        if (refundOrderResponseDTO != null) {
            return refundOrderResponseDTO;
        }
        try {
            refundOrderResponseDTO = offlinePayDubbo.queryRefundOrder(refundOrderQueryRequestDTO);
        } catch (Exception e) {
            //调用dubbo失败，可能是超时，也可能是服务方出错了
            logger.error(e.toString(), e);
            refundOrderResponseDTO = new RefundOrderResponseDTO("1", "false", "系统错误,请重试");
        }
        logger.info("--->返回线下退款查询的响应:" + JSON.toJSONString(refundOrderResponseDTO));
        return refundOrderResponseDTO;
    }

    private RefundOrderResponseDTO validateQueryRefund(RefundOrderQueryRequestDTO refundOrderQueryRequestDTO, BindingResult result) {
        RefundOrderResponseDTO refundOrderResponseDTO = null;
        String valResult = processValidateResult(result);
        if (StringUtils.isNotEmpty(processValidateResult(result))) {
            refundOrderResponseDTO = new RefundOrderResponseDTO("1", "false", valResult);
            return refundOrderResponseDTO;
        }
        if (StringUtils.isEmpty(refundOrderQueryRequestDTO.getBpRefundOrderId()) && StringUtils.isEmpty(refundOrderQueryRequestDTO.getOrderTradeNo())) {
            refundOrderResponseDTO = new RefundOrderResponseDTO("1", "false", "bpRefundOrderId");
            return refundOrderResponseDTO;
        }
        //此处为了兼容PAD,如果不传payType就不进行校验
        if (StringUtils.isNotEmpty(refundOrderQueryRequestDTO.getPayType())) {
            if (!PayTypeEnum.isContain(refundOrderQueryRequestDTO.getPayType())) {
                refundOrderResponseDTO = new RefundOrderResponseDTO("1", "false", "payType只接收WECHATPAY_OFFLINE或者ALIPAY_OFFLINE");
                return refundOrderResponseDTO;
            }
        }
        return refundOrderResponseDTO;
    }

    /**
     * 创建退款的参数校验
     *
     * @param refundOrderRequestDTO
     * @param result
     * @return
     */
    private RefundOrderResponseDTO validateRefund(RefundOrderRequestDTO refundOrderRequestDTO, BindingResult result) {
        RefundOrderResponseDTO refundOrderResponseDTO = null;
        String valResult = processValidateResult(result);
        if (StringUtils.isNotEmpty(processValidateResult(result))) {
            refundOrderResponseDTO = new RefundOrderResponseDTO("1", "false", valResult);
            return refundOrderResponseDTO;
        }
        if (StringUtils.isEmpty(refundOrderRequestDTO.getBpOrderId()) && StringUtils.isEmpty(refundOrderRequestDTO.getOrderTradeNo())) {
            refundOrderResponseDTO = new RefundOrderResponseDTO("1", "false", "bpOrderId和orderTradeNo必须有一个有值");
            return refundOrderResponseDTO;
        }
        //此处为了兼容PAD,如果不传payType就不进行校验
        if (StringUtils.isNotEmpty(refundOrderRequestDTO.getPayType())) {
            if (!PayTypeEnum.isContain(refundOrderRequestDTO.getPayType())) {
                refundOrderResponseDTO = new RefundOrderResponseDTO("1", "false", "payType只接收WECHATPAY_OFFLINE或者ALIPAY_OFFLINE");
                return refundOrderResponseDTO;
            }
        }
        return refundOrderResponseDTO;
    }

    /**
     * 关闭订单请求参数的校验
     *
     * @param orderCloseRequestDTO
     * @param result
     * @return
     */
    private OrderResponseDTO validateClose(OrderCloseRequestDTO orderCloseRequestDTO, BindingResult result) {
        OrderResponseDTO orderResponseDTO = null;
        String valResult = processValidateResult(result);
        if (StringUtils.isNotEmpty(processValidateResult(result))) {
            orderResponseDTO = new OrderResponseDTO("1", "false", valResult);
            return orderResponseDTO;
        }
        if (StringUtils.isEmpty(orderCloseRequestDTO.getBpOrderId()) && StringUtils.isEmpty(orderCloseRequestDTO.getOrderTradeNo())) {
            orderResponseDTO = new OrderResponseDTO("1", "false", "bpOrderId和orderTradeNo必须有一个有值");
            return orderResponseDTO;
        }
        return orderResponseDTO;
    }

    /**
     * 单笔查询的参数校验
     *
     * @param orderQueryRequestDTO
     * @param result
     * @return
     */
    private OrderResponseDTO validateQuery(OrderQueryRequestDTO orderQueryRequestDTO, BindingResult result) {
        OrderResponseDTO orderResponseDTO = null;
        String valResult = processValidateResult(result);
        if (StringUtils.isNotEmpty(processValidateResult(result))) {
            orderResponseDTO = new OrderResponseDTO("1", "false", valResult);
            return orderResponseDTO;
        }
        if (StringUtils.isEmpty(orderQueryRequestDTO.getBpOrderId()) && StringUtils.isEmpty(orderQueryRequestDTO.getOrderTradeNo())) {
            orderResponseDTO = new OrderResponseDTO("1", "false", "bpOrderId和orderTradeNo必须有一个有值");
            return orderResponseDTO;
        }
        //此处为了兼容PAD,如果不传payType就不进行校验
        if (StringUtils.isNotEmpty(orderQueryRequestDTO.getPayType())) {
            if (!PayTypeEnum.isContain(orderQueryRequestDTO.getPayType())) {
                orderResponseDTO = new OrderResponseDTO("1", "false", "payType只接收WECHATPAY_OFFLINE或者ALIPAY_OFFLINE");
                return orderResponseDTO;
            }
        }
        return orderResponseDTO;
    }

    /**
     * 创建支付的校验参数
     *
     * @param result
     * @return
     */
    private OrderResponseDTO validate(OrderRequestDTO orderRequestDTO, BindingResult result) {
        OrderResponseDTO orderResponseDTO = null;
        String valResult = processValidateResult(result);
        if (StringUtils.isNotEmpty(valResult)) {
            orderResponseDTO = new OrderResponseDTO("1", "false", valResult);
            return orderResponseDTO;
        }
        if (!PayTypeEnum.isContain(orderRequestDTO.getPayType())) {
            orderResponseDTO = new OrderResponseDTO("1", "false", "payType只接收WECHATPAY_OFFLINE或者ALIPAY_OFFLINE");
            return orderResponseDTO;
        }
        if (!PayServiceEnum.isContain(orderRequestDTO.getPayService())) {
            orderResponseDTO = new OrderResponseDTO("1", "false", "payService只接收1或2或3");
            return orderResponseDTO;
        }
        if (!PayOrderTerminalEnum.isContain(orderRequestDTO.getInitOrderTerminal())) {
            orderResponseDTO = new OrderResponseDTO("1", "false", "initOrderTerminal只接收01或02或03或04");
            return orderResponseDTO;
        }
        return null;
    }

    /**
     * 如果实体类校验有错误，返回对应的错误信息
     *
     * @param result
     * @return
     */
    private String processValidateResult(BindingResult result) {
        if (result.hasErrors()) {
            StringBuffer sb = new StringBuffer("校验错误：");
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.stream().forEach(fieldError -> {
                logger.info("校验错误：" + fieldError.getField() + fieldError.getDefaultMessage());
                sb.append(fieldError.getField() + fieldError.getDefaultMessage() + ",");
            });
            return sb.substring(0, sb.length() - 1);
        }
        return null;
    }
}

