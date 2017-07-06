package com.wfj.pay.service.impl;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.cache.PayCacheHandle;
import com.wfj.pay.constant.*;
import com.wfj.pay.dto.OrderRequestDTO;
import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.dto.PayNotifyInfoDTO;
import com.wfj.pay.dto.PayTradeDTO;
import com.wfj.pay.mapper.PayChannelMapper;
import com.wfj.pay.mapper.PaySequencesMapper;
import com.wfj.pay.mapper.PayTradeMapper;
import com.wfj.pay.po.PayChannelPO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.service.IPayLogService;
import com.wfj.pay.service.IPayRefundTradeService;
import com.wfj.pay.service.IPayStrategyService;
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.utils.CalendarUtil;
import com.wfj.pay.utils.ObjectUtil;
import com.wfj.pay.utils.OrderEncryptUtils;
import com.wfj.pay.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by wjg on 2017/6/23.
 */
@Service
public class PayTradeServiceImpl implements IPayTradeService {
    private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("yyyyMM");
    @Autowired
    private PayTradeMapper payTradeMapper;
    @Autowired
    private PaySequencesMapper paySequencesMapper;
    @Autowired
    private PayChannelMapper payChannelMapper;
    @Autowired
    private IPayLogService payLogService;
    @Autowired
    private List<IPayStrategyService> strategyList;
    @Autowired
    private IPayRefundTradeService refundTradeService;

    @Override
    @DataSource("slave")
    public PayTradePO findByBpIdAndBpOrderId(Long bpId, String bpOrderId) {
        return payTradeMapper.selectByBpIdAndBpOrderId(bpId, bpOrderId);
    }

    @Override
    @DataSource("slave")
    public PayTradePO findByBpIdAndOrderTradeNo(Long bpId, String orderTradeNo) {
        return payTradeMapper.selectByBpIdAndOrderTradeNo(bpId, orderTradeNo);
    }

    @Override
    @DataSource("slave")
    public PayTradePO findByBpOrderIdOrOrderTradeNo(Long bpId, String bpOrderId, String orderTradeNo) {
        PayTradePO tradePO;
        if (StringUtils.isNotEmpty(orderTradeNo)) {
            tradePO = findByBpIdAndOrderTradeNo(bpId, orderTradeNo);
        } else {
            tradePO = findByBpIdAndBpOrderId(bpId, bpOrderId);
        }
        return tradePO;
    }

    @Override
    @DataSource("slave")
    public PayTradePO findByOrderTradeNo(String orderTradeNo) {
        return payTradeMapper.selectByOrderTradeNo(orderTradeNo);
    }

    @Override
    @DataSource("slave")
    public List<PayTradePO> findByTime(long beginTimeStamp, long endTimeStamp) {
        return payTradeMapper.selectByTimeStamp(beginTimeStamp, endTimeStamp);
    }


    @Override
    @DataSource("master")
    @Transactional
    public PayTradeDTO createOrder(OrderRequestDTO orderRequestDTO) {
        //1、创建订单
        PayTradePO payTradePO = savePayTrade(orderRequestDTO);
        //2、保存日志
        savePayLog(payTradePO);
        //3、组装DTO
        PayTradeDTO tradeDTO = new PayTradeDTO();
        BeanUtils.copyProperties(payTradePO, tradeDTO);
        tradeDTO.setAuthCode(orderRequestDTO.getAuthCode());
        return tradeDTO;
    }

    @DataSource("master")
    @Transactional
    private PayTradePO savePayTrade(OrderRequestDTO orderRequestDTO) {
        PayTradePO payTradePO = new PayTradePO();
        BeanUtils.copyProperties(orderRequestDTO, payTradePO);
        Long currentTime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTime);
        payTradePO.setBpId(Long.valueOf(orderRequestDTO.getBpId()));
        payTradePO.setPayService(Integer.valueOf(orderRequestDTO.getPayService()));
        payTradePO.setOrderTradeNo(paySequencesMapper.selectOrderTradeNoNextVal());
        payTradePO.setCreateDate(currentTime);
        payTradePO.setCreateDateFormat(timestamp);
        payTradePO.setCreateDateDay(Long.valueOf(DAY_FORMAT.format(timestamp)));
        payTradePO.setCreateDateMonth(Long.valueOf(MONTH_FORMAT.format(timestamp)));
        payTradePO.setCreateDateQuarter(Long.valueOf(CalendarUtil.getCurrentQuarter()));
        payTradePO.setPayBank(orderRequestDTO.getPayType());
        payTradePO.setNeedPayPrice(orderRequestDTO.getTotalFee());
        payTradePO.setStatus(1L);
        payTradePO.setNotifyNum("0");
        payTradePO.setNotifyStatus("1");
        payTradePO.setFinalPayTerminal(orderRequestDTO.getInitOrderTerminal());
        payTradePO.setPayCurrency("CNY");
        PayChannelPO payChannelPO = payChannelMapper.getPayChannelByBPDicCodePayService(Long.valueOf(orderRequestDTO.getBpId()), orderRequestDTO.getPayType(), orderRequestDTO.getInitOrderTerminal(), Integer.valueOf(orderRequestDTO.getPayService()));
        payTradePO.setPayPartner(payChannelPO.getPayPartner());
        payTradePO.setPayMediumCode(PayTypeEnum.getMedium(orderRequestDTO.getPayType()));
        payTradeMapper.insert(payTradePO);
        return payTradePO;
    }

    @DataSource("master")
    @Transactional
    private void savePayLog(PayTradePO payTradePO) {
        Map<String, Object> map = ObjectUtil.beanToMap(payTradePO);
        map.put("status", PayTradeStatus.WAIT_PAY_NAME);
        map.put("bpName", PayCacheHandle.getBusinessPOByBpId(payTradePO.getBpId()).getBpName());
        payLogService.saveLog(map, PayLogConstant.PAY_STEP_CREATE, PayLogConstant.SUCCESS_NAME);
    }

    @Override
    @DataSource("master")
    @Transactional
    public void updateOrderAfterPaySuccess(PayNotifyInfoDTO payNotifyInfoDTO) {
        //1、更新订单状态
        Long currentTime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTime);
        PayTradePO payTradePO = new PayTradePO();
        BeanUtils.copyProperties(payNotifyInfoDTO, payTradePO);
        payTradePO.setPayDate(currentTime);
        payTradePO.setPayDateFormat(timestamp);
        payTradePO.setPayDateDay(Long.valueOf(DAY_FORMAT.format(timestamp)));
        payTradePO.setPayDateMonth(Long.valueOf(MONTH_FORMAT.format(timestamp)));
        payTradePO.setPayDateQuarter(Long.valueOf(CalendarUtil.getCurrentQuarter()));
        payTradeMapper.updateOrderAfterPaySuccess(payTradePO);

        //2、保存支付成功的日志
        PayTradePO tradePO = findByOrderTradeNo(payNotifyInfoDTO.getOrderTradeNo());
        Map<String, Object> logMap = new HashMap<String, Object>();
        logMap.put("paySerialNumber", payNotifyInfoDTO.getPaySerialNumber());
        logMap.put("payBank", tradePO.getPayType());
        logMap.put("orderTradeNo", tradePO.getOrderTradeNo());
        logMap.put("totalFee", tradePO.getTotalFee());
        logMap.put("status", PayTradeStatus.PAYED_NAME);
        payLogService.saveLog(logMap, PayLogConstant.PAY_STEP_NOTIFY, PayLogConstant.SUCCESS_NAME);
    }

    @Override
    @DataSource("slave")
    public OrderResponseDTO transfer(String orderTradeNo) {
        PayTradePO payTradePO = findByOrderTradeNo(orderTradeNo);
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO("0", "true", "支付成功");
        BeanUtils.copyProperties(payTradePO, orderResponseDTO);
        orderResponseDTO.setTradeStatus(TradeStatusConstant.SUCCESS);
        orderResponseDTO.setTotalFee(StringUtil.getFormatDouble(payTradePO.getTotalFee()));
        orderResponseDTO.setCouponFee(StringUtil.getFormatDouble(payTradePO.getCouponFee()));
        orderResponseDTO.setRefundDetail(refundTradeService.getRefundDetail(orderTradeNo));
        Map<String, String> map = ObjectUtil.beanToMap2(orderResponseDTO);
        String sign = OrderEncryptUtils.getSign(map, payTradePO.getBpId());
        orderResponseDTO.setSign(sign);
        return orderResponseDTO;
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
    public OrderResponseDTO pay(PayTradeDTO payTradeDTO) {
        //1、保存去支付的日志
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("orderTradeNo", payTradeDTO.getOrderTradeNo());
        logMap.put("payType", payTradeDTO.getPayType());
        logMap.put("payBank", payTradeDTO.getPayType());
        logMap.put("totalFee", payTradeDTO.getTotalFee());
        logMap.put("status", PayTradeStatus.WAIT_PAY_NAME);
        payLogService.saveLog(logMap, PayLogConstant.PAY_STEP_TOPAY, PayLogConstant.SUCCESS_NAME);
        //2、调用策略类发起支付
        PayTypeEnum typeEnum = PayTypeEnum.valueOf(payTradeDTO.getPayType());
        Optional<IPayStrategyService> payStrategy = strategyList.stream().filter(strategy -> strategy.match(typeEnum)).findFirst();
        return payStrategy.get().toPay(payTradeDTO);
    }

    @Override
    public OrderResponseDTO query(PayTradePO tradePO) {
        //1、如果本地是支付状态，支付返回
        if (PayTradeStatus.PAYED.equals(tradePO.getStatus())) {
            return transfer(tradePO.getOrderTradeNo());
        }
        //2、如果是未支付状态则调用对应的策略类进行查询
        PayTypeEnum typeEnum = PayTypeEnum.valueOf(tradePO.getPayType());
        Optional<IPayStrategyService> payStrategy = strategyList.stream().filter(strategy -> strategy.match(typeEnum)).findFirst();
        return payStrategy.get().queryOrder(tradePO);
    }

    @Override
    public OrderResponseDTO close(PayTradePO tradePO, String source) {
        OrderResponseDTO orderResponseDTO;
        //1、如果本地已经是支付状态，则不能关闭
        if (PayTradeStatus.PAYED.equals(tradePO.getStatus())) {
            orderResponseDTO = new OrderResponseDTO(CloseOrderErrorEnum.CLOSE_ORDER_ORDER_PAYMENT.getCode(), "false", CloseOrderErrorEnum.CLOSE_ORDER_ORDER_PAYMENT.getMsg());
            return orderResponseDTO;
        }
        //2、如果本地是未支付状态，则调用策略类先查询，如果确实未支付则调用关闭接口
        PayTypeEnum typeEnum = PayTypeEnum.valueOf(tradePO.getPayType());
        Optional<IPayStrategyService> payStrategy = strategyList.stream().filter(strategy -> strategy.match(typeEnum)).findFirst();
        return payStrategy.get().closeOrder(tradePO, source);
    }

}
