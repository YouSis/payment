package com.wfj.pay.dao;

import com.wfj.pay.po.PayRefundLogEsPO;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by wjg on 2017/7/19.
 */
public interface PayRefundLogRepository extends CrudRepository<PayRefundLogEsPO,Long> {
}
