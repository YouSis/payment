package com.wfj.pay.controller.ops;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wfj.pay.dto.OrderQueryReqDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.ResponseDTO;
import com.wfj.pay.dto.StatisticsDTO;
import com.wfj.pay.dto.StatisticsRequestDTO;
import com.wfj.pay.dubbo.IOPSOperationDubbo;
import com.wfj.pay.service.ESDataService;
import com.wfj.pay.service.PayTradeCountRepository;

@Controller
@RequestMapping("/admin/statistics")
public class StatisController {

	@Reference(version="1.0.0", cluster = "failfast")
	private IOPSOperationDubbo opsDubbo;
	@Autowired
	private ESDataService esDataService;
	@Autowired
	private PayTradeCountRepository tradeCountRepository;
	
	/**
	 * 统计管理统计查询
	 * @param reqDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public ResponseDTO<PaginationDTO<StatisticsDTO>> searchList(StatisticsRequestDTO reqDTO) {
		ResponseDTO<PaginationDTO<StatisticsDTO>> response = new ResponseDTO<PaginationDTO<StatisticsDTO>>();
		if (StringUtils.isEmpty(reqDTO.getPageNo().toString()) || StringUtils.isEmpty(reqDTO.getPageSize().toString())) {
			reqDTO.setPageNo(1);
			reqDTO.setPageSize(400);
		}
		reqDTO.setAllowedBpIds(opsDubbo.selectUserRightsByUserId(reqDTO.getUserId()));
		PaginationDTO<StatisticsDTO> pagination = tradeCountRepository.searchlist(reqDTO);
		response.setData(pagination);
		response.setResult(ResponseDTO.RESULT_SUCCESS);
		return response;
	}
}
