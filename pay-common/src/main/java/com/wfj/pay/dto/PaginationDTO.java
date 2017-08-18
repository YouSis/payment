package com.wfj.pay.dto;

import java.util.Collections;
import java.util.List;

/**
 * 分页DTO.
 * @author haowenchao
 */
public class PaginationDTO<E> extends PageDTO implements java.io.Serializable {



	private static final long serialVersionUID = -1981409199511669701L;

	/**
	 * 数据列表.
	 */
	private List<E> listData = Collections.emptyList();

	// TODO 删掉(v3.0.1)
	/**
	 * 查询数据总数.
	 */
	private String totalData;

	public PaginationDTO(int rows, int pageSize) {
		super(rows, pageSize);
	}

	public PaginationDTO() {
		super();
	}

	public List<E> getListData() {
		return listData;
	}

	public void setListData(List<E> datas) {
		this.listData = datas;
	}

	public String getTotalData() {
		return totalData;
	}

	public void setTotalData(String totalData) {
		this.totalData = totalData;
	}

	@Override
	public String toString() {
		return "PaginationDTO [listData=" + listData + ", totalData="
				+ totalData + "]";
	}

}
