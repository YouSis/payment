package com.wfj.pay.controller.ops;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.PayChannelFeeRateDTO;
import com.wfj.pay.dto.PayDictionaryDTO;
import com.wfj.pay.dto.PayFeeRateTypeDTO;
import com.wfj.pay.dto.PayPartnerAccountDTO;
import com.wfj.pay.dto.ResponseDTO;
import com.wfj.pay.dubbo.IOPSOperationDubbo;

@Controller
@RequestMapping("/admin/payPartnerAccount")
public class PartnerAccountController {
	private Logger LOGGER = LoggerFactory.getLogger(PartnerAccountController.class);
	
	@Reference(version="1.0.0", cluster = "failfast")
	private IOPSOperationDubbo opsOperationDubbo;
	
	/**
	 * 支付渠道分页查询
	 * @param partnerAccount
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public ResponseDTO<PaginationDTO<PayPartnerAccountDTO>> searchList(PayPartnerAccountDTO partnerAccount) {
		LOGGER.info("ops支付页面支付渠道分页查询参数:pageNo:" + partnerAccount.getPageNo() + "pageSize:" + partnerAccount.getPageSize() + "partner:" + partnerAccount.getPartner());
		ResponseDTO<PaginationDTO<PayPartnerAccountDTO>> response = new ResponseDTO<PaginationDTO<PayPartnerAccountDTO>>();
		if (StringUtils.isBlank(partnerAccount.getPayType())) {
			partnerAccount.setPayType(null);
		}
		if (partnerAccount.getId() == null || partnerAccount.getId() == 0) {
			partnerAccount.setId(null);
		}
		String partner = partnerAccount.getPartner();
		if (StringUtils.isBlank(partner)) {
			partnerAccount.setPartner(null);
		} else {
			partner = partner.replaceAll(" ", "");
			partnerAccount.setPartner(partner);
		}
		
		try {
			PaginationDTO<PayPartnerAccountDTO> rsDTO = opsOperationDubbo.findPartnerAccountAllByPage(partnerAccount);
			response.setData(rsDTO);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询单个支付渠道
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get")
	public ResponseDTO<PayPartnerAccountDTO> searchOne(Long id) {
		LOGGER.info("ops支付页面查询单个支付渠道参数id=" + id);
		ResponseDTO<PayPartnerAccountDTO> response = new ResponseDTO<PayPartnerAccountDTO>();
		
		try {
			PayPartnerAccountDTO rs = opsOperationDubbo.findOnePayPartnerAccountById(id);
			response.setData(rs);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 新增支付渠道
	 * @param payPartnerAccount
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public ResponseDTO<?> add(PayPartnerAccountDTO payPartnerAccount) {
		LOGGER.info("ops页面新增支付渠道参数：" + JSON.toJSONString(payPartnerAccount));
		ResponseDTO response = new ResponseDTO();
		
		try {
			Timestamp createDate = new Timestamp(System.currentTimeMillis());
			payPartnerAccount.setCreateDate(createDate);
			opsOperationDubbo.addPayPartnerAccount(payPartnerAccount);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改支付渠道内容
	 * @param partnerAccount
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/edt")
	public ResponseDTO<?> edit(PayPartnerAccountDTO partnerAccount) {
		LOGGER.info("ops页面修改支付渠道参数" + JSON.toJSONString(partnerAccount));
		ResponseDTO response = new ResponseDTO();
		
		try {
			Timestamp createDate = new Timestamp(System.currentTimeMillis());
			partnerAccount.setCreateDate(createDate);
			opsOperationDubbo.updatePayPartnerAccount(partnerAccount);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询支付渠道费率
	 * @param channelFeeRate
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectChannelFeeRateList")
	public ResponseDTO<List<PayChannelFeeRateDTO>> selectChannelFeeRateList(PayChannelFeeRateDTO channelFeeRate) {
		LOGGER.info("ops页面支付渠道管理查询支付渠道费率参数" + JSON.toJSONString(channelFeeRate));
		ResponseDTO<List<PayChannelFeeRateDTO>> response = new ResponseDTO<List<PayChannelFeeRateDTO>>();
		
		try {
			List<PayChannelFeeRateDTO> list = opsOperationDubbo.selectPayChannelFeeRateList(channelFeeRate);
			response.setData(list);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
		} catch (RuntimeException e) {
			LOGGER.error(e.toString(), e);
			response.setMessage(e.getMessage());
			response.setResult(ResponseDTO.RESULT_FAILURE);
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			response.setResult(ResponseDTO.RESULT_FAILURE);
		}
		
		return response;
	}
	
	/**
	 * 新增支付渠道费率
	 * @param channelFeeRate
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addChannelFeeRate")
	public ResponseDTO<?> addChannelFeeRate(PayChannelFeeRateDTO channelFeeRate) {
		LOGGER.info("ops页面支付渠道管理新增支付渠道费率参数：" + JSON.toJSONString(channelFeeRate));
		ResponseDTO response = new ResponseDTO();
		
		try {
			opsOperationDubbo.addChannelFeeRate(channelFeeRate);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
		} catch (RuntimeException e) {
			LOGGER.error(e.toString(), e);
			response.setMessage(e.getMessage());
			response.setResult(ResponseDTO.RESULT_FAILURE);
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			response.setResult(ResponseDTO.RESULT_FAILURE);
		}
		return response;
	}
	
	/**
	 * 修改支付渠道费率
	 * @param channelFeeRate
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateChannelFeeRate")
	public ResponseDTO<?> updateChannelFeeRate(PayChannelFeeRateDTO channelFeeRate) {
		LOGGER.info("ops页面支付渠道管理修改支付渠道费率参数：" + JSON.toJSONString(channelFeeRate));
		ResponseDTO response = new ResponseDTO();
		
		try {
			opsOperationDubbo.updateChannelFeeRate(channelFeeRate);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
		} catch (RuntimeException e) {
			LOGGER.error(e.toString(), e);
			response.setMessage(e.getMessage());
			response.setResult(ResponseDTO.RESULT_FAILURE);
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			response.setResult(ResponseDTO.RESULT_FAILURE);
		}
		return response;
	}
	
	/**
	 * 根据支付渠道类型查询支付渠道费率类型
	 * @param payType
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectChannelFeeRateType")
	public ResponseDTO<List<PayFeeRateTypeDTO>> selectChannelFeeRateType(String payType) {
		LOGGER.info("ops页面根据支付渠道查询支付渠道费率类型参数payType:" + payType);
		ResponseDTO<List<PayFeeRateTypeDTO>> response = new ResponseDTO<List<PayFeeRateTypeDTO>>();
		
		try {
			List<PayFeeRateTypeDTO> rs = opsOperationDubbo.selectFeeRateType(payType);
			response.setData(rs);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			response.setResult(ResponseDTO.RESULT_FAILURE);
		}
		return response;
	}
	
	/**
	 * 查询渠道支付类型
	 * @param payType
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectChannelType")
	public ResponseDTO<List<PayDictionaryDTO>> selectPayDictionary() {
		ResponseDTO<List<PayDictionaryDTO>> response = new ResponseDTO<List<PayDictionaryDTO>>();
		
		try {
			List<PayDictionaryDTO> list = opsOperationDubbo.selectPayDictionary();
			response.setData(list);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			response.setResult(ResponseDTO.RESULT_FAILURE);
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/selectPayChannel")
	public ResponseDTO<List<PayDictionaryDTO>> selectChannelPay() {
		ResponseDTO<List<PayDictionaryDTO>> response = new ResponseDTO<List<PayDictionaryDTO>>();
		try {
			List<PayDictionaryDTO> list = opsOperationDubbo.selectPayDIctionaryAll();
			response.setData(list);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			response.setResult(ResponseDTO.RESULT_FAILURE);
		}
		return response;
	}
}
