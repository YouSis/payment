package com.wfj.pay.dto;

import java.io.Serializable;

/**
 * 下拉选择框DTO.
 * 
 * @author haowenchao
 */
public class SelectOptionDTO implements Serializable {
	private static final long serialVersionUID = 6721674510328567423L;
	/**
	 * 下拉的值.
	 */
	private String id;
	/**
	 * 下拉显示名称.
	 */
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SelectOptionDTO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SelectOptionDTO() {
		super();
	}

}
