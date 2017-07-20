package com.wfj.pay.dao;

import com.wfj.pay.po.PayTradeEsPO;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by wjg on 2017/7/18.
 */
public interface PayTradeRepository extends CrudRepository<PayTradeEsPO,Long> {

}
