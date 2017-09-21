package com.wfj.pay.controller.ops;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wfj.pay.dto.OrderQueryReqDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.ResponseDTO;
import com.wfj.pay.dto.StatisticsByTypeDTO;
import com.wfj.pay.dubbo.IOPSOperationDubbo;
import com.wfj.pay.service.ESDataService;

/**
 * 渠道统计
 * @author jh
 *
 */
@Controller
@RequestMapping("/admin/statistics_type")
public class StatisticsTypeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsTypeController.class);

	@Reference(version="1.0.0", cluster = "failfast")
	private IOPSOperationDubbo opsDubbo;
	@Autowired
	private ESDataService esDataService;
	
	/**
	 * 统计管理按渠道查询分页
	 * @param reqDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public ResponseDTO<PaginationDTO<StatisticsByTypeDTO>> serchList(OrderQueryReqDTO reqDTO) {
		ResponseDTO<PaginationDTO<StatisticsByTypeDTO>> response = new ResponseDTO<PaginationDTO<StatisticsByTypeDTO>>();
		
		reqDTO.setBpIds(opsDubbo.selectUserRightsByUserId(reqDTO.getUserId()));
		format(reqDTO);
		response.setResult(ResponseDTO.RESULT_SUCCESS);
		PaginationDTO<StatisticsByTypeDTO> paginationDTO = esDataService.findAllPriceByType(reqDTO);
		response.setData(paginationDTO);
		return response;
	}
	
	public void format(OrderQueryReqDTO reqDTO) {
		if (reqDTO.getBpId() == null || reqDTO.getBpId() ==0) {
			reqDTO.setBpId(null);
		}
		if (StringUtils.isEmpty(reqDTO.getFinalPayTerminal())) {
			reqDTO.setFinalPayTerminal(null);
		}
		if (StringUtils.isEmpty(reqDTO.getPayBank())) {
			reqDTO.setPayBank(null);
		}
		if (StringUtils.isEmpty(reqDTO.getPayService())) {
			reqDTO.setPayService(null);
		}
		if (StringUtils.isEmpty(reqDTO.getPayType())) {
			reqDTO.setPayType(null);
		}
	}
}
