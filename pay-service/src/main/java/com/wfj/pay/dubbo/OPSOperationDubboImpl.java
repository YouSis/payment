package com.wfj.pay.dubbo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.wfj.pay.cache.PayCacheHandle;
import com.wfj.pay.dto.BankChannelDTO;
import com.wfj.pay.dto.BusinessDTO;
import com.wfj.pay.dto.MerchantRequestDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.PartnerAccountDTO;
import com.wfj.pay.dto.PayChannelFeeRateDTO;
import com.wfj.pay.dto.PayDictionaryDTO;
import com.wfj.pay.dto.PayFeeRateTypeDTO;
import com.wfj.pay.dto.PayPartnerAccountDTO;
import com.wfj.pay.dto.SelectBankDTO;
import com.wfj.pay.dto.SelectOptionDTO;
import com.wfj.pay.po.PayBusinessPO;
import com.wfj.pay.po.PayChannelFeeRatePO;
import com.wfj.pay.po.PayDictionaryPO;
import com.wfj.pay.po.PayMerchantPO;
import com.wfj.pay.po.PayPartnerAccountPO;
import com.wfj.pay.service.IPayBusinessService;
import com.wfj.pay.service.IPayDictionaryService;
import com.wfj.pay.service.IPayMerchantService;
import com.wfj.pay.service.IPayPartnerAccountServive;
import com.wfj.pay.service.UserRightsService;
import com.wfj.pay.utils.ObjectUtil;

/**
 * ops操作dubbo接口实现类
 * @author jh
 *
 */
@Service(version="1.0.0", cluster="failfast", timeout=30000)
public class OPSOperationDubboImpl implements IOPSOperationDubbo {

	private Logger LOGGER = LoggerFactory.getLogger(OPSOperationDubboImpl.class);
	
	@Autowired
	private IPayMerchantService merchantService;
	@Autowired
	private IPayBusinessService businessService;
	@Autowired
	private UserRightsService userRightService;
	@Autowired
	private IPayPartnerAccountServive partnerAccountService;
	@Autowired
	private IPayDictionaryService dictionaryService;

	/**
	 * 分页查询商户信息
	 */
	@Override
	public PaginationDTO<PayMerchantPO> findMerchant(MerchantRequestDTO requestDTO) throws RuntimeException{
		LOGGER.info("ops支付页签约商户管理页面分页查询参数:" + JSON.toJSONString(requestDTO));
		if (requestDTO.getPageNo() <= 0 || requestDTO.getPageSize() <= 0) {
			LOGGER.error("ops支付页签约商户管理页面分页查询参数：");
			LOGGER.error("pageNo:" + requestDTO.getPageNo());
			LOGGER.error("pageSize:" + requestDTO.getPageSize());
			throw new RuntimeException("ops支付页签约商户管理页面分页查询参数异常");
		}
		
		PaginationDTO<PayMerchantPO> pagination = merchantService.findMerchant(requestDTO);
		return pagination;
	}

	/**
	 * 根据id查询商户信息
	 */
	@Override
	public PayMerchantPO findById(Long id) throws RuntimeException {
		LOGGER.info("ops支付页签约商户管理页面单条查询参数:id=" + id);
		if (ObjectUtil.isEmpty(id)) {
			LOGGER.error("ops支付页签约商户管理页面单条查询id为空");
			throw new RuntimeException("ops支付页签约商户管理页面单条查询id为空");
		}
		
		PayMerchantPO merchantPO = merchantService.findById(id);
		return merchantPO;
	}

	/**
	 * 新增商户
	 */
	@Override
	public void add(MerchantRequestDTO merchantDTO) throws RuntimeException {
		LOGGER.info("ops支付页签约商户管理页面新增商户参数:" + JSON.toJSONString(merchantDTO));
		if (ObjectUtil.isEmpty(merchantDTO.getName())) {
			LOGGER.error("ops支付页签约商户管理页面新增商户名称为空");
			throw new RuntimeException("ops支付页签约商户管理页面新增商户名称为空");
		}
		if (ObjectUtil.isEmpty(merchantDTO.getIsOpenYZShop())) {
			LOGGER.error("ops支付页签约商户管理页面新增商户是否为有赞商城不能为空");
			throw new RuntimeException("ops支付页签约商户管理页面新增商户是否为有赞商城不能为空");
		}
		merchantService.add(merchantDTO);
	}

	/**
	 * 修改商户信息
	 */
	@Override
	public void update(MerchantRequestDTO merchantDTO) {
		LOGGER.info("ops支付页签约商户管理页面修改商户参数:" + JSON.toJSONString(merchantDTO));
		
		if (ObjectUtil.isEmpty(merchantDTO.getId())) {
			LOGGER.error("签约商户ID为空(编辑)");
			throw new RuntimeException("签约商户ID为空(编辑)");
		}
		if (ObjectUtil.isEmpty(merchantDTO.getName())) {
			LOGGER.error("签约商户名称为空(编辑)");
			throw new RuntimeException("签约商户名称为空(编辑)");
		}
		if (ObjectUtil.isEmpty(merchantDTO.getIsOpenYZShop())) {
			LOGGER.error("签约商户是否为有赞商店为空(编辑)");
			throw new RuntimeException("签约商户是否为有赞商店为空(编辑)");
		}
		
		merchantService.update(merchantDTO);
	}

	/**
	 * 查询门店
	 */
	@Override
	public List<PayMerchantPO> merchantSelectMerCode() {
		return merchantService.selectMerCode();
		
	}

	/**
	 * 分页查询业务平台
	 */
	@Override
	public PaginationDTO<BusinessDTO> selectBusinessByPage(BusinessDTO businessDTO) {
		LOGGER.info("ops支付页业务平台管理页面分页插叙参数：" + JSON.toJSONString(businessDTO));
		if (businessDTO.getPageNo() <= 0 || businessDTO.getPageSize() <= 0) {
			LOGGER.error("ops支付页业务平台页面分页查询参数错误");
			LOGGER.error("pageNo:" + businessDTO.getPageNo());
			LOGGER.error("pageSize:" + businessDTO.getPageSize());
			throw new RuntimeException("ops支付页业务平台页面分页查询参数异常");
		}
		PaginationDTO<PayBusinessPO> pagination = businessService.selectBusinessByPage(businessDTO);
		PaginationDTO<BusinessDTO> paginationDTO = new PaginationDTO<BusinessDTO>();
		BeanUtils.copyProperties(pagination, paginationDTO);
		return paginationDTO;
	}

	/**
	 * 根据用户id查询权限内业务平台id
	 */
	@Override
	public List<Long> selectUserRightsByUserId(String userId) {
		LOGGER.info("ops页面根据用户名id查询对应业务平台bpid结合参数userId：" + userId);
		if (StringUtils.isBlank(userId)) {
			LOGGER.error("查找用户权限：用户名不可以为空！");
			throw new RuntimeException("查找用户权限：用户名不可以为空！");
		}
		return userRightService.selectUserRightsByUserId(userId);
	}

	/**
	 * 根据id查询平台名
	 */
	@Override
	public List<SelectOptionDTO> findQueryBusiness(List<Long> bpIds) {
		LOGGER.info("ops页面根据业务平台id结合查询业务平台名称参数：" + bpIds);
		List<SelectOptionDTO> rs = businessService.findOptionByBpId(bpIds);
		return rs;
	}

	/**
	 * 新增业务平台
	 */
	@Override
	public void addBusiness(BusinessDTO businessDTO) {
		LOGGER.info("ops支付页业务平台管理新增业务平台参数："+ JSON.toJSONString(businessDTO));
		if (StringUtils.isBlank(businessDTO.getBpName())) {
			LOGGER.error("ops支付页面新增业务平台名称为空");
			throw new RuntimeException("ops支付页面新增业务平台名称为空");
		}
		
		businessService.add(businessDTO);
	}

	/**
	 * 修改业务平台
	 */
	@Override
	public void editBusiness(BusinessDTO businessDTO) {
		if (StringUtils.isBlank(String.valueOf(businessDTO.getId()))) {
			LOGGER.error("ops支付页面修改业务平台操作平台id为空");
			throw new RuntimeException("ops支付页面修改业务平台操作平台id为空");
		}
		if (StringUtils.isBlank(businessDTO.getBpName())) {
			LOGGER.error("ops支付页面修改业务平台操作平台名称为空");
			throw new RuntimeException("ops支付页面修改业务平台操作平台名称为空");
		}
		
		businessService.edit(businessDTO);
	}

	/**
	 * 校验业务平台
	 */
	@Override
	public boolean checkBusiness(Long id, String name) {
		return businessService.check(id, name);
	}

	/**
	 * 业务平台增加支付渠道
	 */
	@Override
	public void addPayChannel(BankChannelDTO bankChannelDTO) {
		if (StringUtils.isBlank(bankChannelDTO.getBp_id().toString())) {
			LOGGER.error("ops支付页面业务平台新增支付渠道bpid为空");
			throw new RuntimeException("ops支付页面业务平台新增支付渠道bpid为空");
		}
		if (StringUtils.isBlank(bankChannelDTO.getDic_code().toString())) {
			LOGGER.error("ops支付页面业务平台新增支付渠道dic_code为空");
			throw new RuntimeException("ops支付页面业务平台新增支付渠道dic_code为空");
		}
		if (StringUtils.isBlank(bankChannelDTO.getClient_type().toString())) {
			LOGGER.error("ops支付页面业务平台新增支付渠道client_type为空");
			throw new RuntimeException("ops支付页面业务平台新增支付渠道client_type为空");
		}
		if (StringUtils.isBlank(bankChannelDTO.getPay_service().toString())) {
			LOGGER.error("ops支付页面业务平台新增支付渠道pay_service为空");
			throw new RuntimeException("ops支付页面业务平台新增支付渠道pay_service为空");
		}
		
		try {
			businessService.addPayChannel(bankChannelDTO);
			
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
		
		
	}

	/**
	 * 删除业务平台支付渠道
	 */
	@Override
	public void deletePayChannel(Long id) {
		businessService.deletePayChannel(id);
	}

	/**
	 * 业务平台查询银行字典
	 */
	@Override
	public List<SelectBankDTO> selectBankByFlag(SelectBankDTO bankDTO) {
		try {
			List<SelectBankDTO> rs = new ArrayList<SelectBankDTO>();
			rs = businessService.selectBankByFlag(bankDTO);
			return rs;
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * 根据bpid和payservice查询支付渠道
	 */
	@Override
	public List<BankChannelDTO> selectPayChannel(Integer bpId, Integer payService) {
		return businessService.selectPayChannel(bpId, payService);
	}

	/**
	 * 查询支付渠道帐号
	 */
	@Override
	public List<PartnerAccountDTO> selectChannelAccount(String payType) {
		return businessService.selectPartnerAccount(payType);
	}

	/**
	 * 修改支付渠道
	 */
	@Override
	public void updatePayChannel(BankChannelDTO bankChannel) {
		if (StringUtils.isBlank(bankChannel.getId().toString())) {
			LOGGER.error("ops支付页面业务平台修改支付渠道id为空");
			throw new RuntimeException("ops支付页面业务平台修改支付渠道id为空");
		}
		if (StringUtils.isBlank(bankChannel.getBp_id().toString())) {
			LOGGER.error("ops支付页面业务平台修改支付渠道bpid为空");
			throw new RuntimeException("ops支付页面业务平台修改支付渠道bpid为空");
		}
		if (StringUtils.isBlank(bankChannel.getDic_code().toString())) {
			LOGGER.error("ops支付页面业务平台修改支付渠道dic_code为空");
			throw new RuntimeException("ops支付页面业务平台修改支付渠道dic_code为空");
		}
		if (StringUtils.isBlank(bankChannel.getClient_type().toString())) {
			LOGGER.error("ops支付页面业务平台修改支付渠道client_type为空");
			throw new RuntimeException("ops支付页面业务平台修改支付渠道client_type为空");
		}
		if (StringUtils.isBlank(bankChannel.getPay_service().toString())) {
			LOGGER.error("ops支付页面业务平台修改支付渠道pay_service为空");
			throw new RuntimeException("ops支付页面业务平台修改支付渠道pay_service为空");
		}
		businessService.updatePayChannel(bankChannel);
	}

	/**
	 * 校验支付渠道是否存在
	 */
	@Override
	public boolean checkPayChannel(Map<String, Object> para) {
		return businessService.checkPayChannel(para);
	}

	/**
	 * 支付渠道分页查询
	 */
	@Override
	public PaginationDTO<PayPartnerAccountDTO> findPartnerAccountAllByPage(PayPartnerAccountDTO partnerAccount) {
		LOGGER.info("ops支付页面支付渠道分页查询参数:pageNo:" + partnerAccount.getPageNo() + "pageSize:" + partnerAccount.getPageSize() + "partner:" + partnerAccount.getPartner());
		PaginationDTO<PayPartnerAccountDTO> pageDTO = new PaginationDTO<PayPartnerAccountDTO>();
		if (partnerAccount.getPageNo() <= 0 || partnerAccount.getPageSize() <= 0) {
			LOGGER.error("分页查询参数：");
			LOGGER.error("pageNo:" + partnerAccount.getPageNo());
			LOGGER.error("pageSize:" + partnerAccount.getPageSize());
			LOGGER.error("partner:" + partnerAccount.getPartner());
			throw new RuntimeException("分页参数异常");
		}
		
		PaginationDTO<PayPartnerAccountPO> pagePO = partnerAccountService.findAllByPage(partnerAccount);
		BeanUtils.copyProperties(pagePO, pageDTO);
		
		return pageDTO;
	}

	/**
	 * 根据id查询单个支付渠道
	 * @param id
	 * @return
	 */
	@Override
	public PayPartnerAccountDTO findOnePayPartnerAccountById(Long id) {
		LOGGER.info("ops支付页面根据id查询单个支付渠道参数:" + id);
		PayPartnerAccountPO po = PayCacheHandle.getPayPartnerAccout(id);
		
		PayPartnerAccountDTO dto = new PayPartnerAccountDTO();
		BeanUtils.copyProperties(po, dto);
		return dto;
	}

	/**
	 * 支付渠道管理页面新增支付渠道
	 */
	@Override
	public void addPayPartnerAccount(PayPartnerAccountDTO payPartnerAccount) {
		LOGGER.info("ops页面新增支付渠道参数：" + JSON.toJSONString(payPartnerAccount));
		if (ObjectUtil.isEmpty(payPartnerAccount.getPayType())) {
			LOGGER.error("渠道号类型为空(新增)");
			throw new RuntimeException("渠道号类型为空(新增)");
		}
		if(ObjectUtil.isEmpty(payPartnerAccount.getPartner())){
			LOGGER.error("渠道号partner为空(新增)");
			throw new RuntimeException("渠道号partner为空(新增)");
		}
		
		partnerAccountService.add(payPartnerAccount);
	}

	/**
	 * 修改支付渠道
	 */
	@Override
	public void updatePayPartnerAccount(PayPartnerAccountDTO partnerAccount) {
		LOGGER.info("ops页面修改支付渠道参数" + JSON.toJSONString(partnerAccount));
		if (ObjectUtil.isEmpty(partnerAccount.getPayType())) {
			LOGGER.error("渠道号类型为空(新增)");
			throw new RuntimeException("渠道号类型为空(新增)");
		}
		if(ObjectUtil.isEmpty(partnerAccount.getId())){
			LOGGER.error("渠道号id为空(新增)");
			throw new RuntimeException("渠道号id为空(新增)");
		}
		
		partnerAccountService.update(partnerAccount);
	}

	/**
	 * 查询支付渠道费率
	 */
	@Override
	public List<PayChannelFeeRateDTO> selectPayChannelFeeRateList(PayChannelFeeRateDTO channelFeeRate) {
		LOGGER.info("ops页面支付渠道管理查询支付渠道费率参数" + JSON.toJSONString(channelFeeRate));
		if(channelFeeRate==null){
			LOGGER.error("查询参数不存在(查询)");
			throw new RuntimeException("查询参数不存在！");
		}
		if(channelFeeRate.getPayPartner()==null || channelFeeRate.getPayPartner() == 0){
			LOGGER.error("渠道账户Partner不能为空(查询)");
			throw new RuntimeException("渠道账户Partner不能为空！");
		}
		if(StringUtils.isBlank(channelFeeRate.getRateType())){
			LOGGER.error("渠道类型不能为空(查询)");
			throw new RuntimeException("渠道类型不能为空！");
		}
		List<PayChannelFeeRatePO> poList = partnerAccountService.selectPayChannelFeeRateList(channelFeeRate);;
		List<PayChannelFeeRateDTO> dtoList = new ArrayList<PayChannelFeeRateDTO>();
		poList.forEach(po -> {
			PayChannelFeeRateDTO dto = new PayChannelFeeRateDTO();
			BeanUtils.copyProperties(po, dto);
			dtoList.add(dto);
		});
		return dtoList;
	}

	/**
	 * 新增支付渠道费率
	 */
	@Override
	public void addChannelFeeRate(PayChannelFeeRateDTO channelFeeRate) {
		LOGGER.info("ops页面支付渠道管理新增支付渠道费率参数：" + JSON.toJSONString(channelFeeRate));
		if(channelFeeRate.getPayPartner() == null || channelFeeRate.getPayPartner() == 0){
			LOGGER.error("渠道账户为空(添加)");
			throw new RuntimeException("渠道账户为空！");
		}
		if(channelFeeRate.getFeeCostRate() == null){
			LOGGER.error("渠道费率为空(添加)");
			throw new RuntimeException("渠道费率为空！");
		}
		if(StringUtils.isBlank(channelFeeRate.getRateType())){
			LOGGER.error("渠道费率类型为空(添加)");
			throw new RuntimeException("渠道费率类型为空！");
		}
		
		partnerAccountService.addChannelFeeRate(channelFeeRate);
	}

	/**
	 * 修改支付渠道费率
	 */
	@Override
	public void updateChannelFeeRate(PayChannelFeeRateDTO channelFeeRate) {
		LOGGER.info("ops页面支付渠道管理修改支付渠道费率参数：" + JSON.toJSONString(channelFeeRate));
		if(channelFeeRate.getId()==null || channelFeeRate.getId() == 0){
			LOGGER.error("渠道费率id为空(修改)");
			throw new RuntimeException("渠道费率id为空(修改)");
		}
		if(channelFeeRate.getFeeCostRate()==null){
			LOGGER.error("渠道费率为空(修改)");
			throw new RuntimeException("渠道费率为空(修改)");
		}
		if(StringUtils.isBlank(channelFeeRate.getRateType())){
			LOGGER.error("渠道费率类型为空(修改)");
			throw new RuntimeException("渠道费率类型为空(修改)");
		}
		
		partnerAccountService.updateChannelFeeRate(channelFeeRate);
	}

	/**
	 * 根据支付渠道类型查询支付渠道费率类型
	 */
	@Override
	public List<PayFeeRateTypeDTO> selectFeeRateType(String payType) {
		return partnerAccountService.selectFeeRateType(payType);
	}

	/**
	 * 查询渠道支付类型
	 */
	@Override
	public List<PayDictionaryDTO> selectPayDictionary() {
		List<PayDictionaryPO> poList = dictionaryService.selectPayDictionary();
		List<PayDictionaryDTO> dtoList = new ArrayList<PayDictionaryDTO>();
		poList.forEach(po -> {
			PayDictionaryDTO dto = new PayDictionaryDTO();
			BeanUtils.copyProperties(po, dto);
			dtoList.add(dto);
		});
		return dtoList;
	}
}
