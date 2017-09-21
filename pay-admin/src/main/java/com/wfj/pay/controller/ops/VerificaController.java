package com.wfj.pay.controller.ops;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.wfj.pay.dto.OrderQueryReqDTO;
import com.wfj.pay.dto.OrderQueryResDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.ResponseDTO;
import com.wfj.pay.dubbo.IOPSOperationDubbo;
import com.wfj.pay.service.ESDataService;
import com.wfj.pay.service.PayTradeCountRepository;

/**
 * 第三方对账明细
 * @author jh
 *
 */
@Controller
@RequestMapping("/admin/verifica")
public class VerificaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(VerificaController.class);
	
	@Reference(version="1.0.0", cluster = "failfast")
	private IOPSOperationDubbo opsDubbo;
	@Autowired
	private ESDataService dataService;
	@Autowired
	private PayTradeCountRepository tradeCountRepository;
	
	/**
	 * 对账明细分页
	 * @param reqDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public ResponseDTO<PaginationDTO<OrderQueryResDTO>> order(OrderQueryReqDTO reqDTO) {
		LOGGER.info("第三方对账明细分页查询参数：" + JSON.toJSONString(reqDTO));
		ResponseDTO<PaginationDTO<OrderQueryResDTO>> response = new ResponseDTO<PaginationDTO<OrderQueryResDTO>>();
		reqDTO.setBpIds(opsDubbo.selectUserRightsByUserId(reqDTO.getUserId()));
		
		format(reqDTO);
		PaginationDTO<OrderQueryResDTO> pagination = dataService.findAllPagePayOrderAdmin(reqDTO);
		response.setData(pagination);
		response.setResult(ResponseDTO.RESULT_SUCCESS);
		return response;
	}
	
	/**
	 * 总金额和优惠总金额
	 * @param reqDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/count")
	public ResponseDTO<Map<String, Object>> getVerificaCount(OrderQueryReqDTO reqDTO) {
		LOGGER.info("第三方对账明细统计总金额参数参数：" + JSON.toJSONString(reqDTO));
		ResponseDTO<Map<String, Object>> response = new ResponseDTO<Map<String, Object>>();
		//reqDTO.setBpIds(opsDubbo.selectUserRightsByUserId(reqDTO.getUserId()));
		format(reqDTO);
		Map<String, Object> request = tradeCountRepository.verificaMoneyCount(reqDTO);
		response.setData(request);
		response.setResult(ResponseDTO.RESULT_SUCCESS);
		return response;
	}
	
	/**
	 * 对账数据导出记录数是否在返回内
	 * @param reqDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkExport")
	public ResponseDTO<Boolean> checkOrderExport(OrderQueryReqDTO reqDTO) {
		ResponseDTO<Boolean> response = new ResponseDTO<Boolean>();
		
		reqDTO.setBpIds(opsDubbo.selectUserRightsByUserId(reqDTO.getUserId()));
		Long Count = tradeCountRepository.getTradeCountByParams(reqDTO);
		if (Count > 10000L) {
			response.setMessage("导出数目超过10000条，将导出最新的10000条数据。");
		}
		response.setResult(ResponseDTO.RESULT_SUCCESS);
		return response;
	}
	
	public void format(OrderQueryReqDTO reqDTO) {
		if (reqDTO.getBpId() == null || reqDTO.getBpId() == 0) {
			reqDTO.setBpId(null);
		}
		if (StringUtils.isBlank(reqDTO.getFinalPayTerminal())) {
			reqDTO.setFinalPayTerminal(null);
		}
		if (StringUtils.isBlank(reqDTO.getPayBank())) {
			reqDTO.setPayBank(null);
		}
		if (StringUtils.isBlank(reqDTO.getPayService())) {
			reqDTO.setPayService(null);
		}
		if (StringUtils.isBlank(reqDTO.getPayType())) {
			reqDTO.setPayType(null);
		}
		if (StringUtils.isBlank(reqDTO.getUserName())) {
			reqDTO.setUserName(null);
		}
	}
}
