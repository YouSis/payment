package com.wfj.pay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.mapper.UserRightsMapper;
import com.wfj.pay.service.UserRightsService;

/**
 * 用户权限
 * @author jh
 *
 */
@Service
public class UserRightsServiceImpl implements UserRightsService{
	
	@Autowired
	private UserRightsMapper userRightsMapper;

	/**
	 * 根据用户id查询权限内业务平添id
	 */
	@Override
	@DataSource("slave")
	public List<Long> selectUserRightsByUserId(String userId) {
		return userRightsMapper.selectUserRightsByUserId(userId);
	}

	
}
