package com.wfj.pay.sdk.wxpay;

import java.io.*;

/**
 * Created by wjg on 2017/6/27.
 */
public class MyWxPayConfig extends  WXPayConfig {

    private String appID;
    private String mchID;
    private String key;
    private String certPath;

    public MyWxPayConfig(String appID, String mchID, String key, String certPath) {
        this.appID = appID;
        this.mchID = mchID;
        this.key = key;
        this.certPath = certPath;
    }

    @Override
    String getAppID() {
        return this.appID;
    }

    @Override
    String getMchID() {
        return this.mchID;
    }

    @Override
    String getKey() {
        return this.key;
    }

    @Override
    InputStream getCertStream() {
        File file = new File(certPath);
        try {
            InputStream certStream = new FileInputStream(file);
            byte[] certData = new byte[(int) file.length()];
            certStream.read(certData);
            certStream.close();
            return  new ByteArrayInputStream(certData);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("路径为："+certPath+"下不存在微信安全证书,请检查");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }
}
