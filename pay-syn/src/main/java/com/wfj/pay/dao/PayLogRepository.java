package com.wfj.pay.dao;

import com.wfj.pay.po.PayLogEsPO;
import com.wfj.pay.po.PayLogPO;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by wjg on 2017/7/19.
 */
public interface PayLogRepository extends CrudRepository<PayLogEsPO,Long> {
}
