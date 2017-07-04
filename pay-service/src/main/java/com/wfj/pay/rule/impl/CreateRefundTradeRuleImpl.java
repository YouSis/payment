package com.wfj.pay.rule.impl;

import com.wfj.pay.constant.PayRefundTradeStatus;
import com.wfj.pay.constant.PayTradeStatus;
import com.wfj.pay.dto.RefundOrderRequestDTO;
import com.wfj.pay.dto.RuleResultDTO;
import com.wfj.pay.po.PayRefundTradePO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.rule.ICreateRefundTradeRule;
import com.wfj.pay.service.IPayRefundTradeService;
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.utils.OrderEncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by wjg on 2017/6/30.
 */
@Component
public class CreateRefundTradeRuleImpl implements ICreateRefundTradeRule {
    @Autowired
    private IPayRefundTradeService refundTradeService;
    @Autowired
    private IPayTradeService tradeService;

    @Override
    public RuleResultDTO orderExistValidate(RefundOrderRequestDTO requestDTO) {
        return orderExistsValidate(requestDTO.getBpId(), requestDTO.getBpOrderId(), requestDTO.getOrderTradeNo());
    }

    @Override
    public RuleResultDTO refundOrderExistValidate(RefundOrderRequestDTO requestDTO) {
        RuleResultDTO resultDTO = new RuleResultDTO();
        PayRefundTradePO payRefundTradePO = refundTradeService.findPayRefundTradePo(Long.valueOf(requestDTO.getBpId()), requestDTO.getBpRefundOrderId());
        if (payRefundTradePO != null) {
            resultDTO.setErrorMessage("退款单号为" + requestDTO.getBpRefundOrderId() + "的退款单已经存在,请检查");
            resultDTO.setSuccess(false);
        }
        return resultDTO;
    }

    @Override
    public RuleResultDTO feeValidate(RefundOrderRequestDTO requestDTO) {
        RuleResultDTO resultDTO = new RuleResultDTO();
        PayTradePO tradePO = tradeService.findByBpOrderIdOrOrderTradeNo(Long.valueOf(requestDTO.getBpId()), requestDTO.getBpOrderId(), requestDTO.getOrderTradeNo());
        if (!PayTradeStatus.PAYED.equals(tradePO.getStatus())) {
            resultDTO.setErrorMessage("订单是未支付状态,不能发起退款");
            resultDTO.setSuccess(false);
            return resultDTO;
        }
        if (!requestDTO.getBpId().equals(String.valueOf(tradePO.getBpId()))) {
            resultDTO.setErrorMessage("退款门店与支付门店不符");
            resultDTO.setSuccess(false);
            return resultDTO;
        }
        BigDecimal refundFee = new BigDecimal(requestDTO.getRefundFee());
        BigDecimal totalFee = new BigDecimal(String.valueOf(tradePO.getTotalFee()));
        BigDecimal totalRefundFee = new BigDecimal(String.valueOf(refundTradeService.findByOrderTradeNo(tradePO.getOrderTradeNo())
                .stream()
                .filter(refundTradePO -> PayRefundTradeStatus.REFUND.equals(refundTradePO.getStatus()))
                .mapToDouble(PayRefundTradePO::getRefundFee)
                .sum()));
        if (refundFee.compareTo(totalFee) > 0) {
            resultDTO.setErrorMessage("退款金额不能大于实际支付金额");
            resultDTO.setSuccess(false);
            return resultDTO;
        }
        if (refundFee.add(totalRefundFee).compareTo(totalFee) > 0) {
            resultDTO.setErrorMessage("退款总金额不能大于实际支付金额");
            resultDTO.setSuccess(false);
            return resultDTO;
        }
        if (StringUtils.isNotEmpty(requestDTO.getPayType())) {
            if (!tradePO.getPayType().equals(requestDTO.getPayType())) {
                resultDTO.setErrorMessage("支付方式与退款方式不一致");
                resultDTO.setSuccess(false);
                return resultDTO;
            }
        }
        return resultDTO;
    }

    @Override
    public RuleResultDTO md5Validate(RefundOrderRequestDTO requestDTO) {
        RuleResultDTO resultDTO = new RuleResultDTO();
        if (!OrderEncryptUtils.checkCreateRefundOrderSign(requestDTO)) {
            resultDTO.setErrorMessage("签名错误,请检查参数");
            resultDTO.setSuccess(false);
        }
        return resultDTO;
    }
}
