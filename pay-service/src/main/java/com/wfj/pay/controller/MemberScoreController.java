package com.wfj.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.wfj.pay.service.IMemberScoreService;
import com.wfj.pay.vo.OperateScoreVO;
import com.wfj.pay.vo.QueryScoreVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wjg on 2017/5/16.
 */
@RestController
@RequestMapping("api")
public class MemberScoreController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IMemberScoreService memberScoreService;

    /**
     * 查询积分API
     *
     * @param scoreVO
     * @return
     */
    @RequestMapping(value = "queryScore", method = RequestMethod.POST, consumes = "application/json")
    public JSONObject queryScore(@RequestBody QueryScoreVO scoreVO) {
        logger.info("接受到查询积分的参数为" + scoreVO.toString());
        JSONObject result;
        try {
            result = memberScoreService.queryMemberScore(scoreVO);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            result = new JSONObject();
            result.put("code", "100");
            result.put("msg", "系统错误");
        }
        logger.info("返回查询积分的报文为" + result.toJSONString());
        return result;
    }

    /**
     * 积分加减API
     *
     * @param operateScoreVO
     * @return
     */
    @RequestMapping(value = "operateScore", method = RequestMethod.POST, consumes = "application/json")
    public JSONObject operateScore(@RequestBody OperateScoreVO operateScoreVO) {
        JSONObject result;
        logger.info("接受到加减积分的参数为" + operateScoreVO.toString());
        try {
            result = memberScoreService.operateMemberScore(operateScoreVO);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            result = new JSONObject();
            result.put("code", "100");
            result.put("msg", "系统错误");
        }
        logger.info("返回加减积分的报文为" + result.toJSONString());
        return result;
    }
}
