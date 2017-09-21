package com.wfj.pay.dto;

import java.util.List;

import com.wfj.pay.po.PayLogEsPO;

/**
 * @description 订单详情的DTO
 * @author admin
 */
public class PayOrderDetailDTO implements java.io.Serializable {

	private static final long serialVersionUID = 878826820894715161L;
	/**
	 * 订单信息.
	 */
	private OrderQueryResDTO payorder;
	/**
	 * 订单log记录.
	 */
	private List<PayLogDTO> paylogs;

	public OrderQueryResDTO getPayorder() {
		return payorder;
	}

	public void setPayorder(OrderQueryResDTO payorder) {
		this.payorder = payorder;
	}

	public List<PayLogDTO> getPaylog() {
		return paylogs;
	}

	public void setPaylog(List<PayLogDTO> paylogs) {
		this.paylogs = paylogs;
	}

}
