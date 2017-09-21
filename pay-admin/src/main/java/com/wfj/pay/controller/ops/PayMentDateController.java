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

import com.wfj.pay.dto.PayMentDateDTO;
import com.wfj.pay.dto.ResponseDTO;
import com.wfj.pay.service.PayTradeCountRepository;

@Controller
@RequestMapping("/admin/statisticalQuery")
public class PayMentDateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PayMentDateController.class);
	
	@Autowired
	private PayTradeCountRepository tradeCountRepository;
	
	@ResponseBody
	@RequestMapping("/get")
	public ResponseDTO<List<PayMentDateDTO>> getStatisticalQuery(String startTime, String endTime, String merCodes, String payTypes) {
		ResponseDTO<List<PayMentDateDTO>> response = new ResponseDTO<List<PayMentDateDTO>>();
		List<String> merCodeList = new ArrayList<String>();
		List<String> payTypeList = new ArrayList<String>();
		if (!StringUtils.isEmpty(merCodes)) {
			String merCode[] = merCodes.split(",");
			for (String Store : merCode) {
				merCodeList.add(Store);
			}
		}
		if (!StringUtils.isEmpty(payTypes)) {
			String payType[] = payTypes.split(",");
			for (String type : payType) {
				payTypeList.add(type);
			}
		}
		List<PayMentDateDTO> payMentDate = tradeCountRepository.selectPayTradeCount(payTypeList, merCodeList, Long.valueOf(startTime), Long.valueOf(endTime));
		response.setData(payMentDate);
		response.setResult(ResponseDTO.RESULT_SUCCESS);
		return response;
	}
	
}
