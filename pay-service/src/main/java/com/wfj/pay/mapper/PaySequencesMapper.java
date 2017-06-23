package com.wfj.pay.mapper;

import org.springframework.stereotype.Repository;

/**
 * Created by wjg on 2017/6/22.
 */
@Repository
public interface PaySequencesMapper {

    String selectOrderTradeNoNextVal();
}
