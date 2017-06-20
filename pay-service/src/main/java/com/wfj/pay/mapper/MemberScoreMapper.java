package com.wfj.pay.mapper;

import com.wfj.pay.po.MemberScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by wjg on 2017/5/15.
 */
@Repository
public interface MemberScoreMapper {

    void update(MemberScore memberScore);

    MemberScore select(@Param("storeNo") String storeNo, @Param("cid") String cid);
}
