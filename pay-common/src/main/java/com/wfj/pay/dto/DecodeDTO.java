/**
 * 
 */
package com.wfj.pay.dto;

import java.io.Serializable;

/**
 * @author ghost
 * @version 创建时间：2016年7月27日 下午6:51:35 类说明 解密DTO类
 */
public class DecodeDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4892610310694191461L;

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DecodeDTO [key=");
		builder.append(key);
		builder.append("]");
		return builder.toString();
	}
}
