package com.wfj.pay.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRightsMapper {

	/**
	 * 
	 * @param userId
	 */
	List<Long> selectUserRightsByUserId(String userId);

}
