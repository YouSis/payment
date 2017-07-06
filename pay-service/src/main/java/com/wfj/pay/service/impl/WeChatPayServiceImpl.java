package com.wfj.pay.service.impl;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.constant.PayRefundTradeStatus;
import com.wfj.pay.constant.PayTradeStatus;
import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.dto.PayNotifyInfoDTO;
import com.wfj.pay.dto.RefundNotifyInfoDTO;
import com.wfj.pay.dto.RefundOrderResponseDTO;
import com.wfj.pay.po.PayRefundWeChatOfNotifyInfoPO;
import com.wfj.pay.po.PayWeChatNotifyInfoPO;
import com.wfj.pay.service.*;
import com.wfj.pay.utils.CalendarUtil;
import com.wfj.pay.utils.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Map;

/**
 * Created by wjg on 2017/7/5.
 */
@Service
public class WeChatPayServiceImpl implements IWeChatPayService {
    private Logger logger = LoggerFactory.getLogger(WeChatPayServiceImpl.class);
    @Autowired
    private IPayWeChatPayNotifyService weChatPayNotifyService;
    @Autowired
    private IPayRefundWeChatOfNotifyService refundWeChatOfNotifyService;
    @Autowired
    private IPayTradeService payTradeService;
    @Autowired
    private IPayRefundTradeService refundTradeService;
    @Override
    @DataSource("master")
    @Transactional
    public OrderResponseDTO doAfterWeChatSuccess(Map<String, String> resultMap) {
        //1、保存微信交易的通知日志
        PayWeChatNotifyInfoPO weChatNotifyInfoPO = (PayWeChatNotifyInfoPO) ObjectUtil.mapToBean(PayWeChatNotifyInfoPO.class, resultMap);
        weChatPayNotifyService.saveWeChatPayNotify(weChatNotifyInfoPO);
        //2、更改订单状态
        PayNotifyInfoDTO payNotifyInfoDTO = new PayNotifyInfoDTO();
        payNotifyInfoDTO.setOrderTradeNo(weChatNotifyInfoPO.getOut_trade_no());
        payNotifyInfoDTO.setPaySerialNumber(weChatNotifyInfoPO.getTransaction_id());
        payNotifyInfoDTO.setStatus(PayTradeStatus.PAYED);
        try {
            Timestamp platformPayDate = CalendarUtil.getTimestampFromString(weChatNotifyInfoPO.getTime_end());
            payNotifyInfoDTO.setPlatformPayDate(CalendarUtil.getDateLong(platformPayDate));
            payNotifyInfoDTO.setPlatformPayFormat(platformPayDate);
        } catch (ParseException e) {
            logger.error(e.toString(), e);
        }
        if (weChatNotifyInfoPO.getCoupon_fee() != null) {
            payNotifyInfoDTO.setCouponFee(new BigDecimal(weChatNotifyInfoPO.getCoupon_fee())
                    .divide(new BigDecimal("100")).doubleValue());
        } else {
            payNotifyInfoDTO.setCouponFee(0d);
        }
        payTradeService.updateOrderAfterPaySuccess(payNotifyInfoDTO);
        //3、转化为DTO
        return  payTradeService.transfer(resultMap.get("out_trade_no"));
    }

    @Override
    @DataSource("master")
    @Transactional
    public RefundOrderResponseDTO doAfterRefundSuccess(Map<String, String> resultMap) {
        //1、修改退款单的状态
        RefundNotifyInfoDTO notifyInfoDTO = new RefundNotifyInfoDTO();
        notifyInfoDTO.setRefundTradeNo(resultMap.get("out_refund_no"));
        notifyInfoDTO.setRefundSerialNumber(resultMap.get("refund_id"));
        notifyInfoDTO.setRefundDate(new Timestamp(System.currentTimeMillis()));
        notifyInfoDTO.setStatus(PayRefundTradeStatus.REFUND);
        if(StringUtils.isNotEmpty(resultMap.get("coupon_refund_fee"))){
            notifyInfoDTO.setCouponFee(new BigDecimal(resultMap.get("coupon_refund_fee")).divide(new BigDecimal("100")).doubleValue());
        }
        refundTradeService.updateAfterRefundSuccess(notifyInfoDTO);
        //2、保存退款通知信息
        PayRefundWeChatOfNotifyInfoPO refundNotifyPO = (PayRefundWeChatOfNotifyInfoPO)ObjectUtil.mapToBean(PayRefundWeChatOfNotifyInfoPO.class, resultMap);
        refundNotifyPO.setRefundDate(new Timestamp(System.currentTimeMillis()));
        refundNotifyPO.setOut_refund_no(notifyInfoDTO.getRefundTradeNo());
        refundNotifyPO.setRefund_id(notifyInfoDTO.getRefundSerialNumber());
        refundNotifyPO.setRefundFee(new BigDecimal(refundNotifyPO.getRefund_fee()).divide(new BigDecimal("100")).doubleValue());
        refundWeChatOfNotifyService.savePayRefundWeChatOfNotifyInfo(refundNotifyPO);
        //3、转换成dto返回
        return refundTradeService.transfer(resultMap.get("out_refund_no"));
    }
}
