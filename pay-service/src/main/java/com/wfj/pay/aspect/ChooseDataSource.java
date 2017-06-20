package com.wfj.pay.aspect;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态路由类
 * @author ghost
 *
 */
public class ChooseDataSource extends AbstractRoutingDataSource {

	// 获取数据源名称
	protected Object determineCurrentLookupKey() {
		return HandleDataSource.getDataSource();
	}

}
