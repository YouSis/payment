package com.wfj.pay.utils;

import java.util.UUID;

/**
 * Created by wjg on 2017/6/27.
 */
public class UUIDUtil {
    /**
     * 生成UUID
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
