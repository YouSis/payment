package com.wfj.pay.controller.ops;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.wfj.pay.dto.MerchantRequestDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.ResponseDTO;
import com.wfj.pay.dubbo.IOPSOperationDubbo;
import com.wfj.pay.po.PayMerchantPO;
import com.wfj.pay.utils.ObjectUtil;

/**
 * ops 签约商户管理接口controller
 * @author jh
 *
 */
@Controller
@RequestMapping("/admin/merchant")
public class MerchantController {

	private Logger LOGGER = LoggerFactory.getLogger(MerchantController.class);
	
	@Reference(version="1.0.0", cluster = "failfast")
	private IOPSOperationDubbo opsOperation;
	
	/**
	 * 分页查询商户信息
	 * @param requestDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public ResponseDTO<PaginationDTO<PayMerchantPO>> searchList(MerchantRequestDTO requestDTO) {
		LOGGER.info("ops支付页签约商户管理页面分页查询参数:" + JSON.toJSONString(requestDTO));
		if (ObjectUtil.isEmpty(requestDTO.getName())) {
			requestDTO.setName(null);
		}
		if (ObjectUtil.isEmpty(requestDTO.getId()) || (requestDTO.getId() == 0)) {
			requestDTO.setId(null);
		}
		
		try {
			ResponseDTO<PaginationDTO<PayMerchantPO>> response = new ResponseDTO<PaginationDTO<PayMerchantPO>>();
			PaginationDTO<PayMerchantPO> pagination = opsOperation.findMerchant(requestDTO);
			
			response.setData(pagination);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据id查询单挑数据
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get")
	public ResponseDTO<PayMerchantPO> searchOne(Long id) {
		LOGGER.info("ops支付页签约商户管理页面单条查询参数:id=" + id);
		try {
			ResponseDTO<PayMerchantPO> response = new ResponseDTO<PayMerchantPO>();
			PayMerchantPO merchantPO = opsOperation.findById(id);
			
			response.setData(merchantPO);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 新增新商户信息
	 * @param MerchantDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public ResponseDTO<?> add(MerchantRequestDTO MerchantDTO) {
		LOGGER.info("ops支付页签约商户管理页面新增商户参数:" + JSON.toJSONString(MerchantDTO));
		try {
			ResponseDTO<?> response = new ResponseDTO<>();
			opsOperation.add(MerchantDTO);
			
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	@ResponseBody
	@RequestMapping("/edt")
	public ResponseDTO<?> update(MerchantRequestDTO merchantDTO) {
		LOGGER.info("ops支付页签约商户管理页面修改商户参数:" + JSON.toJSONString(merchantDTO));
		
		try {
			ResponseDTO<?> response = new ResponseDTO<>();
			opsOperation.update(merchantDTO);

			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询门店
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectMerCode")
	public ResponseDTO<List<PayMerchantPO>> selectMerCode() {
		LOGGER.info("ops支付页查询门店");
		
		try {
			ResponseDTO<List<PayMerchantPO>> response = new ResponseDTO<List<PayMerchantPO>>();
			List<PayMerchantPO> merchantList = opsOperation.merchantSelectMerCode();
			
			response.setData(merchantList);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
}
