package com.wfj.pay.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wfj.pay.Dao.PayRefundLogReposigory;
import com.wfj.pay.Dao.PayRefundTradeRepository;
import com.wfj.pay.Dao.PayTradeRepository;
import com.wfj.pay.Dao.payLogRepository;
import com.wfj.pay.constant.PayLogConstant;
import com.wfj.pay.constant.PayOrderPayType;
import com.wfj.pay.constant.PayOrderTerminalEnum;
import com.wfj.pay.constant.PayServiceConstant;
import com.wfj.pay.constant.PayTradeStatus;
import com.wfj.pay.dto.OPSOrderResponseDTO;
import com.wfj.pay.dto.OrderQueryReqDTO;
import com.wfj.pay.dto.OrderQueryResDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.PayLogDTO;
import com.wfj.pay.dto.PayOrderDetailDTO;
import com.wfj.pay.dto.StatisticsByTypeDTO;
import com.wfj.pay.dubbo.IOPSOperationDubbo;
import com.wfj.pay.po.PayBusinessPO;
import com.wfj.pay.po.PayDictionaryPO;
import com.wfj.pay.po.PayLogEsPO;
import com.wfj.pay.po.PayLogPO;
import com.wfj.pay.po.PayRefundLogEsPO;
import com.wfj.pay.po.PayRefundLogPO;
import com.wfj.pay.po.PayRefundTradeEsPO;
import com.wfj.pay.po.PayRefundTradePO;
import com.wfj.pay.po.PayTradeEsPO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.service.ESDataService;

@Service
public class ESDataServiceImpl implements ESDataService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ESDataServiceImpl.class);

	@Autowired
	PayTradeRepository tradeRepository;
	@Autowired
	payLogRepository payLogRepository;
	@Autowired
	PayRefundTradeRepository refundTradeRepository;
	@Autowired
	PayRefundLogReposigory refundLogReposigory;
	@Reference(version="1.0.0", cluster = "failfast")
	private IOPSOperationDubbo opsDubbo;

	/**
	 * 分页查询支付订单
	 */
	@Override
	public PaginationDTO<OPSOrderResponseDTO> findAllPagePayOrder(OrderQueryReqDTO tradeDTO) {
		PaginationDTO<OPSOrderResponseDTO> paginationDTO = new PaginationDTO<OPSOrderResponseDTO>();
		formatParams(tradeDTO);
		//es分页类pageable分开起始页从0开始
		Pageable pageable = new PageRequest(tradeDTO.getPageNo() - 1, tradeDTO.getPageSize());
		Page<PayTradeEsPO> payTradeEsPOS = null;
		if (tradeDTO.getBpIds().size() > 0) {
			//有bpids参数
			payTradeEsPOS = tradeRepository.findByPayTypeAndBpOrderIdAndBpIdAndStatusAndOrderTradeNoAndInitOrderTerminalAndBpIdInAndCreateDateBetweenOrderByCreateDateDesc(tradeDTO.getPayType(), tradeDTO.getBpOrderId(), tradeDTO.getBpId(), tradeDTO.getStatus(), tradeDTO.getOrderTradeNo(), tradeDTO.getInitOrderTerminal(), tradeDTO.getBpIds(), tradeDTO.getStartTime(), tradeDTO.getEndTime(), pageable);
		} else {
			//无bpids参数
			payTradeEsPOS = tradeRepository.findByPayTypeAndBpOrderIdAndBpIdAndStatusAndOrderTradeNoAndInitOrderTerminalAndCreateDateBetweenOrderByCreateDateDesc(tradeDTO.getPayType(), tradeDTO.getBpOrderId(), tradeDTO.getBpId(), tradeDTO.getStatus(), tradeDTO.getOrderTradeNo(), tradeDTO.getInitOrderTerminal(), tradeDTO.getStartTime(), tradeDTO.getEndTime(), pageable);
		}
		paginationDTO.setPageNo(tradeDTO.getPageNo());
		paginationDTO.setPageSize(tradeDTO.getPageSize());
		paginationDTO.setTotalPages(payTradeEsPOS.getTotalPages());
		paginationDTO.setTotalHit(Integer.valueOf(String.valueOf(payTradeEsPOS.getTotalElements())));
		paginationDTO.calculatePage();
		
		List<OPSOrderResponseDTO> resDTOList = new ArrayList<OPSOrderResponseDTO>();
		List<PayTradeEsPO> tradeEsPOList = payTradeEsPOS.getContent();
		tradeEsPOList.forEach(po -> {
			OPSOrderResponseDTO dto = new OPSOrderResponseDTO();
			BeanUtils.copyProperties(po, dto);
			
			PayBusinessPO businessPO = opsDubbo.findBusinessByBpid(po.getBpId());
			if (businessPO != null) {
				dto.setBpName(businessPO.getBpName());
			}
			dto.setStatusName(PayTradeStatus
					.getNameByCode(po.getStatus()));
			dto.setPayTypeName(PayOrderPayType
					.getNameByCode(po.getPayType()));
			
			// 设置订单支付终端标识名称
			if ("01".equals(po.getInitOrderTerminal())) {
				dto.setInitOrderTerminalName("PC端");
			}else if ("02".equals(po.getInitOrderTerminal())) {
				dto.setInitOrderTerminalName("移动端");
			}else if("03".equals(po.getInitOrderTerminal())){
				dto.setInitOrderTerminalName("PAD端");
			}else if("04".equals(po.getInitOrderTerminal())){
				dto.setInitOrderTerminalName("POS端");
			}
			dto.setCreateDateFormat(null);
			resDTOList.add(dto);
		});
		paginationDTO.setListData(resDTOList);
		return paginationDTO;
	}

	/**
	 * 校验参数  空字符串  0值都设置成null   以免影响查询结果
	 * @param tradeDTO
	 */
	private void formatParams(OrderQueryReqDTO tradeDTO) {
		if (StringUtils.isEmpty(tradeDTO.getPayType())) {
			tradeDTO.setPayType(null);
		}
		if (StringUtils.isEmpty(tradeDTO.getBpOrderId())) {
			tradeDTO.setBpOrderId(null);
		}
		if (tradeDTO.getBpId() == null || tradeDTO.getBpId() == 0L) {
			tradeDTO.setBpId(null);
		}
		if (tradeDTO.getStatus() == null || tradeDTO.getStatus() == 0L) {
			tradeDTO.setStatus(null);
		}
		if (StringUtils.isEmpty(tradeDTO.getOrderTradeNo())) {
			tradeDTO.setOrderTradeNo(null);
		}
		if (StringUtils.isEmpty(tradeDTO.getInitOrderTerminal())) {
			tradeDTO.setInitOrderTerminal(null);
		}
	}

	/**
	 * 查询支付详细信息
	 */
	@Override
	public PayOrderDetailDTO findOrderDetail(String orderTradeNo) {
		PayOrderDetailDTO orderDetailDTO = new PayOrderDetailDTO();
		OrderQueryResDTO orderQueryResDTO = new OrderQueryResDTO();
		PayTradeEsPO payTrade = tradeRepository.findByOrderTradeNo(orderTradeNo);
		BeanUtils.copyProperties(payTrade, orderQueryResDTO);
		PayBusinessPO businessPO = opsDubbo.findBusinessByBpid(payTrade.getBpId());
		if (businessPO != null) {
			orderQueryResDTO.setBpName(businessPO.getBpName());
		}
		PayDictionaryPO dictionaryPO = opsDubbo.selectPayDictionaryByPayBank(payTrade.getPayBank());
		if (dictionaryPO != null) {
			orderQueryResDTO.setPayBankName(dictionaryPO.getName());
			orderQueryResDTO.setDicId(dictionaryPO.getId());
		}
		orderQueryResDTO.setStatusName(PayTradeStatus
				.getNameByCode(payTrade.getStatus()));
		orderQueryResDTO.setPayTypeName(PayOrderPayType
				.getNameByCode(payTrade.getPayType()));
		orderDetailDTO.setPayorder(orderQueryResDTO);
		
		List<PayLogEsPO> payLogList = payLogRepository.findByOrderTradeNoOrderByIdDesc(orderTradeNo);
		List<PayLogDTO> payLogDTOList = new ArrayList<PayLogDTO>();
		payLogList.forEach(payLog -> {
			PayLogDTO dto = new PayLogDTO();
			BeanUtils.copyProperties(payLog, dto);
			dto.setStep(payLogList.indexOf(payLog) + 1);
			dto.setStatusName(PayLogConstant.statusMap.get(dto
					.getStatus()));
			payLogDTOList.add(dto);
		});
		orderDetailDTO.setPaylog(payLogDTOList);
		return orderDetailDTO;
	}

	@Override
	public List<OrderQueryResDTO> selectOrderExport(OrderQueryReqDTO reqDTO) {
		formatParams(reqDTO);
		//es分页类pageable分开起始页从0开始
		Pageable pageable = new PageRequest(reqDTO.getPageNo() - 1, reqDTO.getPageSize());
		Page<PayTradeEsPO> payTradeEsPOS = null;
		if (reqDTO.getBpIds().size() > 0) {
			//有bpids参数
			payTradeEsPOS = tradeRepository.findByPayTypeAndBpOrderIdAndBpIdAndStatusAndOrderTradeNoAndInitOrderTerminalAndBpIdInAndCreateDateBetweenOrderByCreateDateDesc(reqDTO.getPayType(), reqDTO.getBpOrderId(), reqDTO.getBpId(), reqDTO.getStatus(), reqDTO.getOrderTradeNo(), reqDTO.getInitOrderTerminal(), reqDTO.getBpIds(), reqDTO.getStartTime(), reqDTO.getEndTime(), pageable);
		} else {
			//无bpids参数
			payTradeEsPOS = tradeRepository.findByPayTypeAndBpOrderIdAndBpIdAndStatusAndOrderTradeNoAndInitOrderTerminalAndCreateDateBetweenOrderByCreateDateDesc(reqDTO.getPayType(), reqDTO.getBpOrderId(), reqDTO.getBpId(), reqDTO.getStatus(), reqDTO.getOrderTradeNo(), reqDTO.getInitOrderTerminal(), reqDTO.getStartTime(), reqDTO.getEndTime(), pageable);
		}
		
		List<OrderQueryResDTO> resDTOList = new ArrayList<OrderQueryResDTO>();
		List<PayTradeEsPO> tradeEsPOList = payTradeEsPOS.getContent();
		tradeEsPOList.forEach(po -> {
			OrderQueryResDTO dto = new OrderQueryResDTO();
			BeanUtils.copyProperties(po, dto);
			
			PayBusinessPO businessPO = opsDubbo.findBusinessByBpid(po.getBpId());
			if (businessPO != null) {
				dto.setBpName(businessPO.getBpName());
			}
			dto.setStatusName(PayTradeStatus
					.getNameByCode(po.getStatus()));
			dto.setPayTypeName(PayOrderPayType
					.getNameByCode(po.getPayType()));
			
			// 设置订单支付终端标识名称
			if ("01".equals(po.getInitOrderTerminal())) {
				dto.setInitOrderTerminalName("PC端");
			}else if ("02".equals(po.getInitOrderTerminal())) {
				dto.setInitOrderTerminalName("移动端");
			}else if("03".equals(po.getInitOrderTerminal())){
				dto.setInitOrderTerminalName("PAD端");
			}else if("04".equals(po.getInitOrderTerminal())){
				dto.setInitOrderTerminalName("POS端");
			}
			resDTOList.add(dto);
		});
		return resDTOList;
	}

	@Override
	public PaginationDTO<StatisticsByTypeDTO> findAllPriceByType(OrderQueryReqDTO reqDTO) {
		PaginationDTO<StatisticsByTypeDTO> paginationDTO = new PaginationDTO<StatisticsByTypeDTO>();
		if (reqDTO.getPageNo() <= 0 || reqDTO.getPageSize() <= 0) {
			LOGGER.error("分页查询参数：");
			LOGGER.error("pageNo:" + reqDTO.getPageNo());
			LOGGER.error("pageSize:" + reqDTO.getPageSize());
			throw new RuntimeException("分页参数异常");
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String start = format.format(reqDTO.getStartTime());
			Date startDay = format.parse(start);
			Long startTimeC = DateUtils.addYears(startDay, 1).getTime();
			if (reqDTO.getEndTime() > startTimeC) {
				LOGGER.error("时间差：" + (reqDTO.getEndTime() - reqDTO.getStartTime()));
				throw new RuntimeException("所选时间跨度超过1年");
			}
		} catch (Exception e) {
			LOGGER.error(e.toString(),e);
			throw new RuntimeException(e);
		}
		
		//es分页类pageable分开起始页从0开始
		Pageable pageable = new PageRequest(reqDTO.getPageNo() - 1, reqDTO.getPageSize());
		Page<PayTradeEsPO> payTradeEsPOS = null;
		if (reqDTO.getBpIds().size() > 0) {
			//有bpids参数
			payTradeEsPOS = tradeRepository.findByPayTypeAndFinalPayTerminalAndBpIdAndPayBankAndPayServiceAndBpIdInAndCreateDateBetweenOrderByCreateDateDesc(reqDTO.getPayType(), reqDTO.getFinalPayTerminal(), reqDTO.getBpId(), reqDTO.getPayBank(), reqDTO.getPayService(), reqDTO.getBpIds(),reqDTO.getStartTime(), reqDTO.getEndTime(), pageable);
		} else {
			//无bpids参数
			payTradeEsPOS = tradeRepository.findByPayTypeAndFinalPayTerminalAndBpIdAndPayBankAndPayServiceAndCreateDateBetweenOrderByCreateDateDesc(reqDTO.getPayType(), reqDTO.getFinalPayTerminal(), reqDTO.getBpId(), reqDTO.getPayBank(), reqDTO.getPayService(), reqDTO.getStartTime(), reqDTO.getEndTime(), pageable);
		}
		paginationDTO.setPageNo(reqDTO.getPageNo());
		paginationDTO.setPageSize(reqDTO.getPageSize());
		paginationDTO.setTotalPages(payTradeEsPOS.getTotalPages());
		paginationDTO.setTotalHit(Integer.valueOf(String.valueOf(payTradeEsPOS.getTotalElements())));
		paginationDTO.calculatePage();
		
		List<StatisticsByTypeDTO> dtoList = new ArrayList<StatisticsByTypeDTO>();
		List<PayTradeEsPO> tradeEsPOList = payTradeEsPOS.getContent();
		tradeEsPOList.forEach(po -> {
			StatisticsByTypeDTO dto = new StatisticsByTypeDTO();
			// 设置订单支付终端标识名称
			if ("01".equals(po.getInitOrderTerminal())) {
				dto.setFinalPayTerminalName("PC端");
			}else if ("02".equals(po.getInitOrderTerminal())) {
				dto.setFinalPayTerminalName("移动端");
			}else if("03".equals(po.getInitOrderTerminal())){
				dto.setFinalPayTerminalName("PAD端");
			}else if("04".equals(po.getInitOrderTerminal())){
				dto.setFinalPayTerminalName("POS端");
			}
			
			PayBusinessPO businessPO = opsDubbo.findBusinessByBpid(po.getBpId());
			if (businessPO != null) {
				dto.setBpName(businessPO.getBpName());
			}
			dto.setPayType(PayOrderPayType
					.getNameByCode(po.getPayType()));
			dto.setPayBank(PayOrderPayType
					.getNameByCode(po.getPayType()));
			dto.setName(PayOrderPayType
					.getNameByCode(po.getPayType()));
			if (po.getPayService() != 0 && po.getPayService() != null) {
				dto.setPayService(PayServiceConstant.getPayServiceName(po.getPayService()));
			}
			dto.setPrice(String.valueOf(po.getTotalFee()));
			dto.setRate("0.0%");
			dto.setChannelCost(po.getChannelFeeCost());
			dto.setNeedPayPrice(po.getNeedPayPrice());
			if (po.getBargainIncome() != null) {
				dto.setBargainIncome(po.getBargainIncome());
			} else {
				dto.setBargainIncome(0.0);
			}
			if (po.getRealIncome() != null) {
				dto.setRealIncome(po.getRealIncome());
			} else {
				dto.setRealIncome(0.0);
			}
			dto.setCreateDate(po.getCreateDate());
			dtoList.add(dto);
		});
		paginationDTO.setListData(dtoList);
		return paginationDTO;
	}

	@Override
	public PaginationDTO<OrderQueryResDTO> findAllPagePayOrderAdmin(OrderQueryReqDTO reqDTO) {
		PaginationDTO<OrderQueryResDTO> pagination = new PaginationDTO<OrderQueryResDTO>();
		
		if (reqDTO.getPageNo() <= 0 || reqDTO.getPageSize() <= 0) {
			LOGGER.error("分页查询参数：");
			LOGGER.error("pageNo:" + reqDTO.getPageNo());
			LOGGER.error("pageSize:" + reqDTO.getPageSize());
			throw new RuntimeException("分页参数异常");
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String start = format.format(reqDTO.getStartTime());
			Date startDay = format.parse(start);
			Long startTimeC = DateUtils.addYears(startDay, 1).getTime();
			if (reqDTO.getEndTime() > startTimeC) {
				LOGGER.error("时间差：" + (reqDTO.getEndTime() - reqDTO.getStartTime()));
				throw new RuntimeException("所选时间跨度超过1年");
			}
		} catch (Exception e) {
			LOGGER.error(e.toString(),e);
			throw new RuntimeException(e);
		}
		
		//es分页类pageable分开起始页从0开始
		Pageable pageable = new PageRequest(reqDTO.getPageNo() - 1, reqDTO.getPageSize());
		Page<PayTradeEsPO> payTradeEsPOS = null;
		if (reqDTO.getBpIds().size() > 0) {
			//有bpids参数
			payTradeEsPOS = tradeRepository.findByPayTypeAndFinalPayTerminalAndBpIdAndPayBankAndPayServiceAndUsernameAndBpIdInAndCreateDateBetweenOrderByCreateDateDesc(reqDTO.getPayType(), reqDTO.getFinalPayTerminal(), reqDTO.getBpId(), reqDTO.getPayBank(), reqDTO.getPayService(), reqDTO.getUserName(), reqDTO.getBpIds(), reqDTO.getStartTime(), reqDTO.getEndTime(), pageable);
		} else {
			//无bpids参数
			payTradeEsPOS = tradeRepository.findByPayTypeAndFinalPayTerminalAndBpIdAndPayBankAndPayServiceAndUsernameAndCreateDateBetweenOrderByCreateDateDesc(reqDTO.getPayType(), reqDTO.getFinalPayTerminal(), reqDTO.getBpId(), reqDTO.getPayBank(), reqDTO.getPayService(), reqDTO.getUserName(), reqDTO.getStartTime(), reqDTO.getEndTime(), pageable);
		}
		pagination.setPageNo(reqDTO.getPageNo());
		pagination.setPageSize(reqDTO.getPageSize());
		pagination.setTotalPages(payTradeEsPOS.getTotalPages());
		pagination.setTotalHit(Integer.valueOf(String.valueOf(payTradeEsPOS.getTotalElements())));
		pagination.calculatePage();
		
		List<OrderQueryResDTO> dtoList = new ArrayList<OrderQueryResDTO>();
		List<PayTradeEsPO> tradeEsPOList = payTradeEsPOS.getContent();
		tradeEsPOList.forEach(po -> {
			OrderQueryResDTO dto = new OrderQueryResDTO();
			BeanUtils.copyProperties(po, dto);
			// 设置订单支付终端标识名称
			if ("01".equals(po.getInitOrderTerminal())) {
				dto.setFinalPayTerminalName("PC端");
			}else if ("02".equals(po.getInitOrderTerminal())) {
				dto.setFinalPayTerminalName("移动端");
			}else if("03".equals(po.getInitOrderTerminal())){
				dto.setFinalPayTerminalName("PAD端");
			}else if("04".equals(po.getInitOrderTerminal())){
				dto.setFinalPayTerminalName("POS端");
			}
			
			PayBusinessPO businessPO = opsDubbo.findBusinessByBpid(po.getBpId());
			if (businessPO != null) {
				dto.setBpName(businessPO.getBpName());
			}
			dto.setPayTypeName(PayOrderPayType
					.getNameByCode(po.getPayType()));
			dto.setPayBankName(PayOrderPayType
					.getNameByCode(po.getPayType()));
			if (po.getPayService() != 0 && po.getPayService() != null) {
				dto.setPayServiceName(PayServiceConstant.getPayServiceName(po.getPayService()));
			}
			dto.setRate("0.0%");
			dtoList.add(dto);
		});
		pagination.setListData(dtoList);
		return pagination;
	}

	
	public void tradeToES() {
		List<PayTradePO> tradeEsPOList = opsDubbo.tradeToES();
		tradeEsPOList.forEach(trade -> {
			List<PayLogPO> longList = opsDubbo.getLongByOrderTradeNo(trade.getOrderTradeNo());
			PayTradeEsPO po = new PayTradeEsPO();
			BeanUtils.copyProperties(trade, po);
			tradeRepository.save(po);
			longList.forEach(logPO -> {
				PayLogEsPO logEsPO = new PayLogEsPO();
				BeanUtils.copyProperties(logPO, logEsPO);
				payLogRepository.save(logEsPO);
			});
			
			List<PayRefundTradePO> refundTradeList = opsDubbo.getRefundTradeByOrderTradeNo(trade.getOrderTradeNo());
			refundTradeList.forEach(refundTrade ->{
				PayRefundTradeEsPO esPO = new PayRefundTradeEsPO();
				BeanUtils.copyProperties(refundTrade, esPO);
				refundTradeRepository.save(esPO);
				List<PayRefundLogPO> refundLogList = opsDubbo.getRefundLogByRefundOrderTradeNo(refundTrade.getRefundTradeNo());
				refundLogList.forEach(refundLog -> {
					PayRefundLogEsPO logEsPO = new PayRefundLogEsPO();
					BeanUtils.copyProperties(refundLog, logEsPO);
					refundLogReposigory.save(logEsPO);
				});
			});
			
		});
	}
}
