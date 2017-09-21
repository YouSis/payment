package com.wfj.pay.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.cache.PayCacheHandle;
import com.wfj.pay.constant.PayOrderPayType;
import com.wfj.pay.constant.PayOrderTerminalEnum;
import com.wfj.pay.constant.PayTradeStatus;
import com.wfj.pay.dto.OrderQueryReqDTO;
import com.wfj.pay.dto.OrderQueryResDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.mapper.PayTradeMapper;
import com.wfj.pay.po.PayBusinessPO;
import com.wfj.pay.po.PayPartnerAccountPO;
import com.wfj.pay.po.PayTradePO;
import com.wfj.pay.service.IPayOrderService;

@Service
public class PayOrderServiceImpl implements IPayOrderService{

	@Autowired
	private PayTradeMapper tradeMapper;
	
	@Override
    @DataSource("slave")
	public PaginationDTO<OrderQueryResDTO> findAllOrderCompensate(OrderQueryReqDTO orderDTO) {
		PaginationDTO<OrderQueryResDTO> paginationDTO = new PaginationDTO<OrderQueryResDTO>();
		
		PageHelper.startPage(orderDTO.getPageNo(), orderDTO.getPageSize());
		List<PayTradePO> payTradeList = tradeMapper.selectAllOrderCompensate(orderDTO);
		
		paginationDTO.setPageNo(orderDTO.getPageNo());
		paginationDTO.setPageSize(orderDTO.getPageSize());
		paginationDTO.setTotalPages(((Page<PayTradePO>) payTradeList).getPages());
		paginationDTO.setTotalHit((int) ((Page<PayTradePO>) payTradeList).getTotal());
		
		List<OrderQueryResDTO> orderQueryResDTO = new ArrayList<OrderQueryResDTO>();
		payTradeList.forEach(po -> {
			OrderQueryResDTO dto = new OrderQueryResDTO();
			BeanUtils.copyProperties(po, dto);
			
			PayBusinessPO businessPO = PayCacheHandle.getBusinessPOByBpId(po.getBpId());
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
			}else if ("02".equals(dto.getInitOrderTerminal())) {
				dto.setInitOrderTerminalName("移动端");
			}else if("03".equals(dto.getInitOrderTerminal())){
				dto.setInitOrderTerminalName("PAD端");
			}else if("04".equals(dto.getInitOrderTerminal())){
				dto.setInitOrderTerminalName("POS端");
			}
			orderQueryResDTO.add(dto);
		});
		paginationDTO.calculatePage();
		paginationDTO.setListData(orderQueryResDTO);
		return paginationDTO;
	}

	
	@Override
    @DataSource("slave")
	public List<PayTradePO> findAllOrderByStatus(OrderQueryReqDTO orderDTO) {
		List<PayTradePO> payTradePOList = tradeMapper.selectAllOrderByStatus(orderDTO);
		return payTradePOList;
	}


	@Override
	public List<PayTradePO> tradeToES() {
		PageHelper.startPage(1, 100);
		List<PayTradePO> payTradeList = tradeMapper.tradeToES();
		return payTradeList;
	}

}
