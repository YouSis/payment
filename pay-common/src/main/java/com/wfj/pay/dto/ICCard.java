/**
 * 
 */
package com.wfj.pay.dto;

import java.io.Serializable;

/**
 * @author ghost
 * @version 创建时间：2016年7月26日 下午9:58:34 类说明 解密DTO类
 */
public class ICCard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2618989477150284936L;

	private String cardNo;
	private String mdh;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMdh() {
		return mdh;
	}

	public void setMdh(String mdh) {
		this.mdh = mdh;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ICCard [cardNo=");
		builder.append(cardNo);
		builder.append(", mdh=");
		builder.append(mdh);
		builder.append("]");
		return builder.toString();
	}

}
