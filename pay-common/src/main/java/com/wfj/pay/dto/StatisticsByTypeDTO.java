package com.wfj.pay.dto;

/**
 * 渠道查询DTO.
 * 
 * @author haowenchao
 */
public class StatisticsByTypeDTO implements java.io.Serializable{

	private static final long serialVersionUID = -6167386697498388868L;
	/**
	 * 名称.
	 */
	private String name;
	/**
	 * 充值方式.
	 */
	private String payBank;
    /**
     * 订单支付终端标识名称.
     */
    private String finalPayTerminalName;
	/**
	 * 渠道.
	 */
	private String payType;
    /**
     * 支付服务.
     */
    private String payService ;
	/**
	 * 人民币.
	 */
	private String price;
	/**
	 * 手续费率.
	 */
	private String rate;
	/**
	 * 手续费支出
	 */
	private Double channelCost ;
    /**
     * 应付金额
     */
    private Double needPayPrice ;
    /**
     * 议价收入
     */
    private Double bargainIncome ;
    /**
     * 实际收入
     */
    private Double realIncome ;
    
    /**
     * 业务平台名称
     */
    private String bpName;
    /**
     * 创建时间
     * @return
     */
    private Long createDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPayBank() {
		return payBank;
	}
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

    public String getFinalPayTerminalName() {
        return finalPayTerminalName;
    }

    public void setFinalPayTerminalName(String finalPayTerminalName) {
        this.finalPayTerminalName = finalPayTerminalName;
    }

    public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}

    public String getPayService() {
        return payService;
    }

    public void setPayService(String payService) {
        this.payService = payService;
    }

    public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}

    public Double getChannelCost() {
        return channelCost;
    }

    public void setChannelCost(Double channelCost) {
        this.channelCost = channelCost;
    }

    public Double getNeedPayPrice() {
        return needPayPrice;
    }

    public void setNeedPayPrice(Double needPayPrice) {
        this.needPayPrice = needPayPrice;
    }

    public Double getBargainIncome() {
        return bargainIncome;
    }

    public void setBargainIncome(Double bargainIncome) {
        this.bargainIncome = bargainIncome;
    }

    public Double getRealIncome() {
        return realIncome;
    }

    public void setRealIncome(Double realIncome) {
        this.realIncome = realIncome;
    }
	public String getBpName() {
		return bpName;
	}
	public void setBpName(String bpName) {
		this.bpName = bpName;
	}
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
}
