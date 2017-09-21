package com.wfj.pay.controller.ops;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.wfj.pay.dto.OPSOrderResponseDTO;
import com.wfj.pay.dto.OrderQueryReqDTO;
import com.wfj.pay.dto.OrderQueryResDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.PayOrderDetailDTO;
import com.wfj.pay.dto.ResponseDTO;
import com.wfj.pay.dubbo.IOPSOperationDubbo;
import com.wfj.pay.service.ESDataService;
import com.wfj.pay.service.PayTradeCountRepository;

/**
 * 订单管理controller
 * @author jh
 *
 */
@Controller
@RequestMapping("/admin/order")
public class PayOrderController {
	private final static Logger LOGGER = LoggerFactory.getLogger(PayOrderController.class);

	@Reference(version="1.0.0", cluster = "failfast")
	private IOPSOperationDubbo opsDubbo;
	@Autowired
	private ESDataService dataService;
	@Autowired
	private PayTradeCountRepository payTradeCount;
	
	/**
	 * 分页查询订单明细
	 * @param tradeDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/order")
	public ResponseDTO<PaginationDTO<OPSOrderResponseDTO>> order(OrderQueryReqDTO reqDTO){
		LOGGER.info("ops支付模块订单管理分页查询订单明细参数：" + JSON.toJSONString(reqDTO));
		ResponseDTO<PaginationDTO<OPSOrderResponseDTO>> response = new ResponseDTO<PaginationDTO<OPSOrderResponseDTO>>();
		
		List<Long> bpIds = opsDubbo.selectUserRightsByUserId(reqDTO.getUserId());
		reqDTO.setBpIds(bpIds);
		if(StringUtils.isNotBlank(reqDTO.getAccount())){
			// 用户名不正确,将查询不到结果.
			PaginationDTO<OPSOrderResponseDTO> paginationDTO = new PaginationDTO<OPSOrderResponseDTO>();
			response.setData(paginationDTO);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		}
		
		PaginationDTO<OPSOrderResponseDTO> paginationDTO = dataService.findAllPagePayOrder(reqDTO);
		response.setData(paginationDTO);
		response.setResult(ResponseDTO.RESULT_SUCCESS);
		return response;
	}
	
	/**
	 * 根据支付平台订单号查询单条数据
	 * @param orderTraeNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/orderDetail")
	public ResponseDTO<PayOrderDetailDTO> orderDetail(String orderTradeNo) {
		ResponseDTO<PayOrderDetailDTO> response = new ResponseDTO<PayOrderDetailDTO>();
		if (StringUtils.isEmpty(orderTradeNo)) {
			response.setResult(ResponseDTO.RESULT_INPUT);
			throw new RuntimeException("查看订单详细信息无orderTradeNo参数！");
		} else {
			PayOrderDetailDTO payOrderDetail = dataService.findOrderDetail(orderTradeNo);
			response.setData(payOrderDetail);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
		}
		return response;
	}
	
	/**
	 * 判断订单导出的记录数是否在范围之内
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkOrderExport")
	public ResponseDTO<Boolean> checkOrderExport(OrderQueryReqDTO reqDTO) {
		ResponseDTO<Boolean> response = new ResponseDTO<Boolean>();
		List<Long> bpIds = opsDubbo.selectUserRightsByUserId(reqDTO.getUserId());
		reqDTO.setBpIds(bpIds);
		if(StringUtils.isNotBlank(reqDTO.getAccount())){
			// 用户名不正确,将查询不到结果.
			response.setData(true);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		}
		Long Count = payTradeCount.getTradeCountByParams(reqDTO);
		if (Count > 10000L) {
			response.setMessage("导出数目超过10000条，将导出最新的10000条数据。");
		}
		response.setResult(ResponseDTO.RESULT_SUCCESS);
		return response;
	}
	
	/**
	 * 分页查询支付不成功订单
	 * @param orderDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findAllOrderCompensate")
	public ResponseDTO<PaginationDTO<OrderQueryResDTO>> findAllOrderCompensate(OrderQueryReqDTO orderDTO) {
		LOGGER.info("ops页面订单管理页面分页查询支付不成功的订单参数：" + JSON.toJSONString(orderDTO));
		ResponseDTO<PaginationDTO<OrderQueryResDTO>> response = new ResponseDTO<PaginationDTO<OrderQueryResDTO>>();
		orderDTO.setBpIds(opsDubbo.selectUserRightsByUserId(orderDTO.getUserId()));
		response.setData(opsDubbo.findAllOrderCompensate(orderDTO));
		response.setResult(ResponseDTO.RESULT_SUCCESS);
		return response;
	}
	
	/**
	 * 支付不成功的补偿单笔查询
	 * @param orderDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/singleOrderQuery")
	public ResponseDTO<?> singleOrderQuery(OrderQueryReqDTO orderDTO) {
		LOGGER.info("ops页面订单管理页面补偿订单状态参数：" + JSON.toJSONString(orderDTO));
		ResponseDTO<?> response = new ResponseDTO();
		orderDTO.setBpIds(opsDubbo.selectUserRightsByUserId(orderDTO.getUserId()));
		opsDubbo.singLeTradeQuery(orderDTO);
		response.setResult(ResponseDTO.RESULT_SUCCESS);
		return response;
	}
}
