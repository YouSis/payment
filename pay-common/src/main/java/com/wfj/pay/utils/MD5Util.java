package com.wfj.pay.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by wjg on 2017/6/23.
 */
public class MD5Util {

    private static final Logger logger= LoggerFactory.getLogger(MD5Util.class);
    public static String md5(String text) {

        return DigestUtils.md5Hex(getContentBytes(text, "utf-8"));

    }

    public static String md5(String text, String inputCharset) {

        return DigestUtils.md5Hex(getContentBytes(text, inputCharset));

    }

    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }

        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.toString(), e);
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
}
