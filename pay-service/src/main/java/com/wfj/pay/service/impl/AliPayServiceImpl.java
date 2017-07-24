package com.wfj.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.constant.PayLogConstant;
import com.wfj.pay.constant.PayRefundTradeStatus;
import com.wfj.pay.constant.PayTradeStatus;
import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.dto.PayNotifyInfoDTO;
import com.wfj.pay.dto.RefundNotifyInfoDTO;
import com.wfj.pay.dto.RefundOrderResponseDTO;
import com.wfj.pay.mapper.PayTradeMapper;
import com.wfj.pay.po.PayAliPayOffLineNotifyInfoPO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.po.RefundAliOffLineNotifyInfoPO;
import com.wfj.pay.sdk.alipay.AliPayInterface;
import com.wfj.pay.service.*;
import com.wfj.pay.utils.CalendarUtil;
import com.wfj.pay.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by kongqf on 2017/7/11.
 */
@Service
public class AliPayServiceImpl implements IAliPayService {
    private Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);
    @Autowired
    private IPayAliPayNotifyService payAliPayNotifyService;
    @Autowired
    private IPayRefundAliPayOfNotifyService refundAliPayOfNotifyService;
    @Autowired
    private IPayTradeService payTradeService;
    @Autowired
    private IPayRefundTradeService refundTradeService;
    @Autowired
    private PayTradeMapper payTradeMapper;
    @Autowired
    private IPayLogService payLogService;

    @Override
    @DataSource("master")
    @Transactional
    public OrderResponseDTO doAfterAliSuccess(String result, AliPayInterface aliPayInterface) {
        //1、保存支付宝交易的通知日志
        Map<String, String> resultMap = JsonUtil.string2Map(result);
        String response = resultMap.get(aliPayInterface.getMethod());
        PayAliPayOffLineNotifyInfoPO alipayNotifyInfoPO = JSONObject.parseObject(response, PayAliPayOffLineNotifyInfoPO.class);
        if (StringUtils.isEmpty(alipayNotifyInfoPO.getGmt_payment())) {
            alipayNotifyInfoPO.setGmt_payment(StringUtils.isNotEmpty(alipayNotifyInfoPO.getSend_pay_date()) ?
                    alipayNotifyInfoPO.getSend_pay_date() :
                    CalendarUtil.getsimpleDateFormat(new Date(), CalendarUtil.FORMAT_LONG));
        }
        payAliPayNotifyService.saveAliPayNotify(alipayNotifyInfoPO);

        //2、更改订单状态
        PayNotifyInfoDTO payNotifyInfoDTO = new PayNotifyInfoDTO();
        payNotifyInfoDTO.setOrderTradeNo(alipayNotifyInfoPO.getOut_trade_no());
        payNotifyInfoDTO.setPaySerialNumber(alipayNotifyInfoPO.getTrade_no());
        payNotifyInfoDTO.setStatus(PayTradeStatus.PAYED);
        try {
            Timestamp platformPayDate = CalendarUtil.getTimestampFromString(alipayNotifyInfoPO.getGmt_payment(), CalendarUtil.FORMAT_LONG);
            payNotifyInfoDTO.setPlatformPayDate(CalendarUtil.getDateLong(platformPayDate));
            payNotifyInfoDTO.setPlatformPayFormat(platformPayDate);
        } catch (ParseException e) {
            logger.error(e.toString(), e);
        }
        payTradeService.updateOrderAfterPaySuccess(payNotifyInfoDTO);
        //3、转化为DTO
        return payTradeService.transfer(alipayNotifyInfoPO.getOut_trade_no());
    }

    @Override
    @DataSource("master")
    @Transactional
    public RefundOrderResponseDTO doAfterRefundSuccess(Map<String, String> resultMap) {
        //1、保存退款通知信息
        RefundAliOffLineNotifyInfoPO refundNotifyPO = JSONObject.parseObject(JSON.toJSONString(resultMap), RefundAliOffLineNotifyInfoPO.class);
        refundAliPayOfNotifyService.savePayRefundAliPayOfNotifyInfo(refundNotifyPO);

        //2、修改退款单的状态
        RefundNotifyInfoDTO notifyInfoDTO = new RefundNotifyInfoDTO();
        notifyInfoDTO.setRefundTradeNo(resultMap.get("out_refund_no"));
        //notifyInfoDTO.setRefundSerialNumber(resultMap.get("refund_id"));
        try {
            notifyInfoDTO.setRefundDate(CalendarUtil.getTimestampFromString(refundNotifyPO.getGmt_refund_pay(), CalendarUtil.FORMAT_LONG));
        } catch (ParseException e) {
            notifyInfoDTO.setRefundDate(new Timestamp(System.currentTimeMillis()));
        }
        notifyInfoDTO.setStatus(PayRefundTradeStatus.REFUND);
       /* if (StringUtils.isNotEmpty(resultMap.get("coupon_refund_fee"))) {
            notifyInfoDTO.setCouponFee(new BigDecimal(resultMap.get("coupon_refund_fee")).divide(new BigDecimal("100")).doubleValue());
        }*/
        refundTradeService.updateAfterRefundSuccess(notifyInfoDTO);


        //3、转换成dto返回
        return refundTradeService.transfer(resultMap.get("out_refund_no"));
    }


    @Override
    @DataSource("master")
    @Transactional
    public void doAfterCloseSuccess(String orderTradeNo, String operateSource) {
        PayTradePO payTradePO = findByOrderTradeNo(orderTradeNo);
        //1、更新订单状态
        payTradeMapper.updateOrderStatus(orderTradeNo, PayTradeStatus.CANCELED);
        //2、保存日志
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("bpId", payTradePO.getBpId());
        logMap.put("bpOrderId", payTradePO.getBpOrderId());
        logMap.put("orderTradeNo", payTradePO.getOrderTradeNo());
        logMap.put("operateSource", PayLogConstant.OPERATE_SOURCE_PERSON.equals(operateSource) ? PayLogConstant.OPERATE_SOURCE_PERSON_NAME : PayLogConstant.OPERATE_SOURCE_SCHEDULE_NAME);
        payLogService.saveLog(logMap, PayLogConstant.PAY_STEP_CLOSE, PayLogConstant.SUCCESS_NAME);
    }


    @Override
    @DataSource("slave")
    public PayTradePO findByOrderTradeNo(String orderTradeNo) {
        return payTradeMapper.selectByOrderTradeNo(orderTradeNo);
    }
}
