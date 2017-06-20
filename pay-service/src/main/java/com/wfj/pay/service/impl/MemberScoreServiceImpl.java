package com.wfj.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wfj.pay.annotation.DataSource;
import com.wfj.pay.mapper.MemberScoreMapper;
import com.wfj.pay.po.MemberScore;
import com.wfj.pay.service.IMemberScoreService;
import com.wfj.pay.vo.OperateScoreVO;
import com.wfj.pay.vo.QueryScoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * Created by wjg on 2017/5/16.
 */
@Service
public class MemberScoreServiceImpl implements IMemberScoreService {
    @Autowired
    private MemberScoreMapper memberScoreMapper;

    @DataSource("slave")
    @Override
    public JSONObject queryMemberScore(QueryScoreVO scoreVO) {
        JSONObject result = new JSONObject();
        MemberScore memberScore = memberScoreMapper.select(scoreVO.getStoreNo(), scoreVO.getCid());
        if (null == memberScore) {
            result.put("code", "102");
            result.put("msg", "无此记录");
            return result;
        }
        result.put("code", "0");
        result.put("msg", "OK");
        result.put("content", memberScore);
        return result;
    }

    @DataSource("master")
    @Transactional
    @Override
    public JSONObject operateMemberScore(OperateScoreVO scoreVO) {
        JSONObject result = new JSONObject();
        MemberScore memberScore = memberScoreMapper.select(scoreVO.getStoreNo(), scoreVO.getCid());
        if (null == memberScore) {
            result.put("code", "101");
            result.put("msg", "无此记录");
            return result;
        }
        //更新积分表
        memberScore.setScore(memberScore.getScore()+scoreVO.getScoreFee());
        memberScore.setUpdateChannel(scoreVO.getChannel());
        memberScore.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        memberScoreMapper.update(memberScore);


        result.put("code", "0");
        result.put("msg", "OK");
        return result;
    }
}
