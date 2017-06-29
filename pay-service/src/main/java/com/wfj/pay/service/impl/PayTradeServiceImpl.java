package com.wfj.pay.service.impl;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.constant.PayLogConstant;
import com.wfj.pay.constant.PayTypeEnum;
import com.wfj.pay.constant.TradeStatusConstant;
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
import com.wfj.pay.service.IPayTradeService;
import com.wfj.pay.utils.CalendarUtil;
import com.wfj.pay.utils.ObjectUtil;
import com.wfj.pay.utils.OrderEncryptUtils;
import com.wfj.pay.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

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

    @Override
    @DataSource("slave")
    public PayTradePO findByBpIdAndBpOrderId(Long bpId, String bpOrderId) {
        return payTradeMapper.selectByBpIdAndBpOrderId(bpId, bpOrderId);
    }

    @Override
    @DataSource("slave")
    public PayTradePO findByBpIdAndOrderTradeNo(Long bpId, String orderTradeNo) {
        return payTradeMapper.selectByBpIdAndOrderTradeNo(bpId,orderTradeNo);
    }

    @Override
    @DataSource("slave")
    public PayTradePO findByOrderTradeNo(String orderTradeNo) {
        return payTradeMapper.selectByOrderTradeNo(orderTradeNo);
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
        BeanUtils.copyProperties(payTradePO,tradeDTO);
        tradeDTO.setAuthCode(orderRequestDTO.getAuthCode());
        return tradeDTO;
    }


    private PayTradePO savePayTrade(OrderRequestDTO orderRequestDTO) {
        PayTradePO payTradePO = new PayTradePO();
        BeanUtils.copyProperties(orderRequestDTO, payTradePO);
        Long currentTime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTime);
        payTradePO.setOrderTradeNo(paySequencesMapper.selectOrderTradeNoNextVal());
        payTradePO.setCreateDate(currentTime);
        payTradePO.setCreateDateFormat(timestamp);
        payTradePO.setCreateDateDay(Long.valueOf(DAY_FORMAT.format(timestamp)));
        payTradePO.setCreateDateMonth(Long.valueOf(MONTH_FORMAT.format(timestamp)));
        payTradePO.setCreateDateQuarter(Long.valueOf(CalendarUtil.getCurrentQuarter()));
        payTradePO.setPayBank(orderRequestDTO.getPayService());
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

    private void savePayLog(PayTradePO payTradePO) {
        Map<String, Object> map = ObjectUtil.beanToMap(payTradePO);
        payLogService.saveLog(map, PayLogConstant.PAY_STEP_CREATE, "1");
    }

    @Override
    @DataSource("master")
    @Transactional
    public void updateOrderAfterPaySuccess(PayNotifyInfoDTO payNotifyInfoDTO) {
        Long currentTime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTime);
        PayTradePO payTradePO = new PayTradePO();
        BeanUtils.copyProperties(payNotifyInfoDTO,payTradePO);
        payTradePO.setPayDate(currentTime);
        payTradePO.setPayDateFormat(timestamp);
        payTradePO.setPayDateDay(Long.valueOf(DAY_FORMAT.format(timestamp)));
        payTradePO.setPayDateMonth(Long.valueOf(MONTH_FORMAT.format(timestamp)));
        payTradePO.setPayDateQuarter(Long.valueOf(CalendarUtil.getCurrentQuarter()));
        payTradeMapper.updateOrderAfterPaySuccess(payTradePO);
    }

    @Override
    @DataSource("slave")
    public OrderResponseDTO transfer(PayTradePO payTradePO) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO("0","true","支付成功");
        BeanUtils.copyProperties(payTradePO,orderResponseDTO);
        orderResponseDTO.setTradeStatus(TradeStatusConstant.SUCCESS);
        orderResponseDTO.setTotalFee(StringUtil.getFormatDouble(payTradePO.getTotalFee()));
        orderResponseDTO.setCouponFee(StringUtil.getFormatDouble(payTradePO.getCouponFee()));
        //待实现。。。
        orderResponseDTO.setRefundDetail(null);
        Map<String, String> map = ObjectUtil.beanToMap2(payTradePO);
        String sign = OrderEncryptUtils.getSign(map, payTradePO.getBpId());
        orderResponseDTO.setSign(sign);
        return  orderResponseDTO;
    }
}
