package com.wfj.pay.controller.ops;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.wfj.pay.dto.BankChannelDTO;
import com.wfj.pay.dto.BusinessDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.PartnerAccountDTO;
import com.wfj.pay.dto.ResponseDTO;
import com.wfj.pay.dto.SelectBankDTO;
import com.wfj.pay.dto.SelectOptionDTO;
import com.wfj.pay.dubbo.IOPSOperationDubbo;
import com.wfj.pay.utils.ObjectUtil;

/**
 * 业务平台管理
 * @author jh
 *
 */
@Controller
@RequestMapping("/admin/business_station")
public class BusinessController {
	private Logger LOGGER = LoggerFactory.getLogger(BusinessController.class);

	@Reference(version="1.0.0", cluster = "failfast")
	private IOPSOperationDubbo opsOperationDubbo;
	
	/**
	 * 分页查询业务平台
	 * @param businessDTO
	 * @param platformId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public ResponseDTO<PaginationDTO<BusinessDTO>> searchList(BusinessDTO businessDTO, String platformId, String name) {
		LOGGER.info("ops支付页业务平台管理页面分页插叙参数：" + JSON.toJSONString(businessDTO) + "-----platformId:" + platformId);
		
		ResponseDTO<PaginationDTO<BusinessDTO>> response = new ResponseDTO<PaginationDTO<BusinessDTO>>();
		if (ObjectUtil.isEmpty(name)) {
			businessDTO.setBpName(null);
		} else {
			businessDTO.setBpName(name);
		}
		if (ObjectUtil.isEmpty(platformId)) {
			platformId = null;
		}else{
			platformId = platformId.replaceAll(" ","");
			businessDTO.setId(Long.valueOf(platformId));
		}
		
		try {
			PaginationDTO<BusinessDTO> paginationDTO = opsOperationDubbo.selectBusinessByPage(businessDTO);
			
			response.setData(paginationDTO);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 业务平台下拉框
	 * @param userId
	 * @param flag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/query")
	public ResponseDTO<List<SelectOptionDTO>> searchQuery(String userId, String flag) {
		LOGGER.info("ops支付页业务平台下拉框参数userId：" + userId + "--flag:" + flag);
		ResponseDTO<List<SelectOptionDTO>> response = new ResponseDTO<List<SelectOptionDTO>>();
		try {
			List<SelectOptionDTO> optionList = new ArrayList<SelectOptionDTO>();
			if ("0".equals(flag)) {
				optionList = opsOperationDubbo.findQueryBusiness(opsOperationDubbo.selectUserRightsByUserId(userId));
			} else {
				optionList = opsOperationDubbo.findQueryBusiness(null);
			}
			response.setData(optionList);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 新增业务平台
	 * @param businessDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public ResponseDTO<?> add(BusinessDTO businessDTO) {
		LOGGER.info("ops支付页业务平台管理新增业务平台参数：" + JSON.toJSONString(businessDTO));
		ResponseDTO response = new ResponseDTO();
		try {
			opsOperationDubbo.addBusiness(businessDTO);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改业务平台
	 * @param businessDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/edt")
	public ResponseDTO<?> edit(BusinessDTO businessDTO) {
		LOGGER.info("ops支付页面修改业务平台操作参数：" + JSON.toJSONString(businessDTO));
		ResponseDTO response = new ResponseDTO();
		
		Timestamp lastDate = new Timestamp(System.currentTimeMillis());
		businessDTO.setLastDate(lastDate);
		
		try {
			opsOperationDubbo.editBusiness(businessDTO);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 校验业务平台是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/check")
	public ResponseDTO<?> check(Long id, String name) {
		LOGGER.info("ops支付页面校验业务平台参数id:" + id + "---name:" + name);
		ResponseDTO response = new ResponseDTO();
		try {
			if (opsOperationDubbo.checkBusiness(id, name)) {
				response.setResult(ResponseDTO.RESULT_SUCCESS);
			} else {
				response.setResult(ResponseDTO.RESULT_FAILURE);
			}
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加支付渠道
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addPayChannel")
	public ResponseDTO<?> addPayChannel(BankChannelDTO bankChannelDTO) {
		LOGGER.info("ops页面业务平台添加支付渠道参数：" + JSON.toJSONString(bankChannelDTO));
		ResponseDTO response = new ResponseDTO();
		try {
			opsOperationDubbo.addPayChannel(bankChannelDTO);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除支付渠道
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deletePayChannel")
	public ResponseDTO<?> deletePayChannel(Long id) {
		ResponseDTO response = new ResponseDTO();
		
		try {
			opsOperationDubbo.deletePayChannel(id);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询银行字典
	 * @param bankDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectBankList")
	public ResponseDTO<List<SelectBankDTO>> selectBankList(SelectBankDTO bankDTO) {
		LOGGER.info("ops页面业务平台查询银行字典参数：" + JSON.toJSONString(bankDTO));
		ResponseDTO<List<SelectBankDTO>> response = new ResponseDTO<List<SelectBankDTO>>();
		List<SelectBankDTO> rs = new ArrayList<SelectBankDTO>();
		
		try {
			rs = opsOperationDubbo.selectBankByFlag(bankDTO);
			response.setData(rs);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据bpid和payservice查询支付渠道列表
	 * @param bpId
	 * @param payService
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectPayChannel")
	public ResponseDTO<List<BankChannelDTO>> selectPayChannel(Integer bpId, Integer payService) {
		LOGGER.info("ops支付页面业务平台根据bpid和payservice查询支付渠道列表参数：bpid=" + bpId + ", payservice=" + payService);
		ResponseDTO<List<BankChannelDTO>> response = new ResponseDTO<List<BankChannelDTO>>();
		
		try {
			List<BankChannelDTO> rs = opsOperationDubbo.selectPayChannel(bpId, payService);
			response.setData(rs);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询渠道签约帐号
	 * @param payType
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectPartnerAccount")
	public ResponseDTO<List<PartnerAccountDTO>> findChannelAccount(String payType) {
		LOGGER.info("ops支付页面查询支付渠道参数： payType=" + payType);
		ResponseDTO<List<PartnerAccountDTO>> response = new ResponseDTO<List<PartnerAccountDTO>>();
		
		try {
			List<PartnerAccountDTO> rs = opsOperationDubbo.selectChannelAccount(payType);
			response.setData(rs);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改签约渠道帐号
	 * @param bankChannel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updatePayChannel")
	public ResponseDTO<?> updatePayChannel(BankChannelDTO bankChannel) {
		ResponseDTO response = new ResponseDTO();
		
		try {
			opsOperationDubbo.updatePayChannel(bankChannel);
			response.setResult(ResponseDTO.RESULT_SUCCESS);
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 校验支付渠道是否存在
	 * @param id
	 * @param bpId
	 * @param payService
	 * @param dicCode
	 * @param clientType
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkPayChannel")
	public ResponseDTO<?> checkPayChannel(Integer id, Long bpId, Integer payService, String dicCode, String clientType) {
		ResponseDTO response = new ResponseDTO();
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("id", id);
		para.put("bpId", bpId);
		para.put("payService", payService);
		para.put("dicCode", dicCode);
		para.put("clientType", clientType);
		try {
			if (opsOperationDubbo.checkPayChannel(para)) {
				response.setResult(ResponseDTO.RESULT_SUCCESS);
			} else {
				response.setResult(ResponseDTO.RESULT_FAILURE);
			}
			return response;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
		
	}
}
