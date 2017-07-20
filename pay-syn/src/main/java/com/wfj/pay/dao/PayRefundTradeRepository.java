package com.wfj.pay.dao;

import com.wfj.pay.po.PayRefundTradeEsPO;
import com.wfj.pay.po.PayTradeEsPO;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by wjg on 2017/7/18.
 */
public interface PayRefundTradeRepository extends CrudRepository<PayRefundTradeEsPO,Long> {

}
