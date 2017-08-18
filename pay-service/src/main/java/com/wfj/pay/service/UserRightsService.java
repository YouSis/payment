package com.wfj.pay.service;

import java.util.List;

/**
 * 用户权限service
 * @author jh
 *
 */
public interface UserRightsService {

	/**
	 * 根据用户id查询业务平台id
	 * @param userId
	 * @return
	 */
	List<Long> selectUserRightsByUserId(String userId);

}
