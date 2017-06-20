package com.wfj.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.wfj.pay.vo.OperateScoreVO;
import com.wfj.pay.vo.QueryScoreVO;


/**
 * Created by wjg on 2017/5/16.
 */
public interface IMemberScoreService {
    /**
     * 查询积分
     * @param scoreVO
     * @return
     */
    JSONObject queryMemberScore(QueryScoreVO scoreVO);

    /**
     * 加减积分
     * @param scoreVO
     * @return
     */
    JSONObject operateMemberScore(OperateScoreVO scoreVO);
}
