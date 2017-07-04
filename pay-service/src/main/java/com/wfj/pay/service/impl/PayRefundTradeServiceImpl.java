package com.wfj.pay.service.impl;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.cache.PayCacheHandle;
import com.wfj.pay.constant.PayLogConstant;
import com.wfj.pay.constant.PayRefundTradeStatus;
import com.wfj.pay.constant.PayTypeEnum;
import com.wfj.pay.dto.RefundNotifyInfoDTO;
import com.wfj.pay.dto.RefundOrderQueryRequestDTO;
import com.wfj.pay.dto.RefundOrderRequestDTO;
import com.wfj.pay.dto.RefundOrderResponseDTO;
import com.wfj.pay.mapper.PayRefundTradeMapper;
import com.wfj.pay.po.PayRefundTradePO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.service.IPayLogService;
import com.wfj.pay.service.IPayRefundTradeService;
import com.wfj.pay.service.IPayStrategyService;
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.utils.ObjectUtil;
import com.wfj.pay.utils.OrderEncryptUtils;
import com.wfj.pay.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by wjg on 2017/6/30.
 */
@Service
public class PayRefundTradeServiceImpl implements IPayRefundTradeService {
    @Autowired
    private PayRefundTradeMapper payRefundTradeMapper;
    @Autowired
    private IPayTradeService payTradeService;
    @Autowired
    private IPayLogService payLogService;
    @Autowired
    private List<IPayStrategyService> strategyList;

    @Override
    @DataSource("slave")
    public PayRefundTradePO findPayRefundTradePo(Long bpId, String bpRefundOrderId) {
        return payRefundTradeMapper.selectByBpIdAndBpRefundOrderId(bpId, bpRefundOrderId);
    }

    @Override
    @DataSource("slave")
    public PayRefundTradePO findPayRefundTradePO(String refundTradeNo) {
        return payRefundTradeMapper.selectByRefundTradeNo(refundTradeNo);
    }

    @Override
    @DataSource("slave")
    public PayRefundTradePO findLastSuccessRefundTrade(String orderTradeNo) {
        return payRefundTradeMapper.selectLastSuccessRefundTrade(orderTradeNo);
    }

    @Override
    @DataSource("slave")
    public List<PayRefundTradePO> findByOrderTradeNo(String orderTradeNo) {
        return payRefundTradeMapper.selectRefundTradeByOrderTradeNo(orderTradeNo);
    }

    @Override
    @DataSource("master")
    @Transactional
    public PayRefundTradePO createRefundTrade(RefundOrderRequestDTO refundOrderRequestDTO) {
        //1、保存退款单
        PayTradePO tradePO = payTradeService.findByBpOrderIdOrOrderTradeNo(Long.valueOf(refundOrderRequestDTO.getBpId()), refundOrderRequestDTO.getBpOrderId(), refundOrderRequestDTO.getOrderTradeNo());
        String orderTradeNo = tradePO.getOrderTradeNo();
        PayRefundTradePO refundTradePO = new PayRefundTradePO();
        refundTradePO.setBpId(Long.valueOf(refundOrderRequestDTO.getBpId()));
        refundTradePO.setBpRefundOrderId(refundOrderRequestDTO.getBpRefundOrderId());
        refundTradePO.setCreateDate(new Timestamp(System.currentTimeMillis()));
        refundTradePO.setStatus(PayRefundTradeStatus.WAIT_REFUND);
        refundTradePO.setRefundTradeNo(orderTradeNo + "R" + payRefundTradeMapper.selectRefundTradeCount(orderTradeNo));
        refundTradePO.setRefundFee(Double.valueOf(refundOrderRequestDTO.getRefundFee()));
        refundTradePO.setOrderTradeNo(orderTradeNo);
        payRefundTradeMapper.insert(refundTradePO);
        //2、保存日志
        Map<String, Object> logMap = ObjectUtil.beanToMap(refundTradePO);
        logMap.put("bpName", PayCacheHandle.getBusinessPOByBpId(Long.valueOf(refundOrderRequestDTO.getBpId())).getBpName());
        logMap.put("status",PayRefundTradeStatus.WAIT_REFUND_NAME);
        payLogService.saveRefundLog(logMap, PayLogConstant.REFUND_SETP_CREATE,PayLogConstant.SUCCESS_NAME);
        return refundTradePO;
    }

    @Override
    @DataSource("master")
    @Transactional
    public void updateAfterRefundSuccess(RefundNotifyInfoDTO notifyInfoDTO) {
        //1、更改状态
        PayRefundTradePO refundTradePO = payRefundTradeMapper.selectByRefundTradeNo(notifyInfoDTO.getRefundTradeNo());
        BeanUtils.copyProperties(notifyInfoDTO,refundTradePO);
        payRefundTradeMapper.update(refundTradePO);
        //2、保存日志
        Map<String, Object> map = ObjectUtil.beanToMap(refundTradePO);
        map.put("status",PayRefundTradeStatus.REFUND_NAME);
        payLogService.saveRefundLog(map,PayLogConstant.REFUND_STEP_SUCCESS,PayLogConstant.SUCCESS_NAME);
    }

    @Override
    @DataSource("slave")
    @Transactional
    public RefundOrderResponseDTO transfer(String refundTradeNo) {
        PayRefundTradePO refundTradePO = findPayRefundTradePO(refundTradeNo);
        PayTradePO tradePO = payTradeService.findByOrderTradeNo(refundTradePO.getOrderTradeNo());
        RefundOrderResponseDTO responseDTO = new RefundOrderResponseDTO("0","true","退款成功");
        BeanUtils.copyProperties(refundTradePO,responseDTO);
        responseDTO.setBpId(refundTradePO.getBpId().toString());
        responseDTO.setTotalFee(StringUtil.getFormatDouble(tradePO.getTotalFee()));
        responseDTO.setPayMediumCode(tradePO.getPayMediumCode());
        responseDTO.setRefundFee(StringUtil.getFormatDouble(refundTradePO.getRefundFee()));
        responseDTO.setCouponFee(StringUtil.getFormatDouble(refundTradePO.getCouponFee()));
        responseDTO.setTransactionId(refundTradePO.getRefundSerialNumber());
        Map<String, String> map = ObjectUtil.beanToMap2(responseDTO);
        String sign = OrderEncryptUtils.getSign(map, tradePO.getBpId());
        responseDTO.setSign(sign);
        return responseDTO;
    }

    @Override
    public RefundOrderResponseDTO refund(PayRefundTradePO refundTradePO) {
        PayTradePO tradePO = payTradeService.findByOrderTradeNo(refundTradePO.getOrderTradeNo());
        PayTypeEnum typeEnum = PayTypeEnum.valueOf(tradePO.getPayType());
        Optional<IPayStrategyService> payStrategy = strategyList.stream().filter(strategy -> strategy.match(typeEnum)).findFirst();
        return payStrategy.get().toRefund(refundTradePO,tradePO);
    }

    @Override
    @DataSource("slave")
    public String getRefundDetail(String orderTradeNo) {
        List<PayRefundTradePO> refundTrades = payRefundTradeMapper.selectRefundTradeByOrderTradeNo(orderTradeNo);
        if(refundTrades.isEmpty()){
            return null;
        }
        return refundTrades.stream().map(rt -> rt.getBpRefundOrderId()+"^"+rt.getStatus()+"^"+rt.getRefundFee()).collect(Collectors.joining("|"));
    }

    @Override
    public RefundOrderResponseDTO refundQuery(RefundOrderQueryRequestDTO refundQueryDTO) {
        RefundOrderResponseDTO responseDTO;
        //1、如果本地退款成功,直接返回
        PayRefundTradePO refundTradePO;
        if(StringUtils.isNotEmpty(refundQueryDTO.getOrderTradeNo())){
            refundTradePO = findLastSuccessRefundTrade(refundQueryDTO.getOrderTradeNo());
        }else{
            refundTradePO = findPayRefundTradePo(Long.valueOf(refundQueryDTO.getBpId()),refundQueryDTO.getBpRefundOrderId());
        }
        if(refundTradePO.getStatus().equals(PayRefundTradeStatus.REFUND)){
            responseDTO = transfer(refundTradePO.getRefundTradeNo());
            return responseDTO;
        }
        //2、发起查询
        PayTradePO tradePO = payTradeService.findByOrderTradeNo(refundTradePO.getOrderTradeNo());
        PayTypeEnum typeEnum = PayTypeEnum.valueOf(tradePO.getPayType());
        Optional<IPayStrategyService> payStrategy = strategyList.stream().filter(strategy -> strategy.match(typeEnum)).findFirst();
        return payStrategy.get().queryRefundOrder(refundTradePO,tradePO);
    }
}
