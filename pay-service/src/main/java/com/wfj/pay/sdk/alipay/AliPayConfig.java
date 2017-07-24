package com.wfj.pay.sdk.alipay;

/**
 * Created by kongqf on 2017/7/17.
 */
public class AliPayConfig {
    //URL支付网关（固定）
    private String URL = "https://openapi.alipay.com/gateway.do";
    //APPID即创建应用后生成	获取见上面创建应用并获取APPID
    private String APP_ID;
    //私钥(开发者应用私钥，由开发者自己生成	获取详见上面配置密钥)
    private String APP_PRIVATE_KEY;
    //参数返回格式，只支持json	json（固定）
    private String FORMAT = "json";
    //请求和签名使用的字符编码格式，支持GBK和UTF-8
    private String CHARSET = "UTF-8";
    //支付宝公钥
    private String ALIPAY_PUBLIC_KEY;
    //商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
    private String SIGN_TYPE = "RSA";
    //是否测试环境
    private Boolean IS_TEST = true;

    public AliPayConfig(String APP_ID, String APP_PRIVATE_KEY, String ALIPAY_PUBLIC_KEY) {
        this.APP_ID = APP_ID;
        this.APP_PRIVATE_KEY = APP_PRIVATE_KEY;
        this.ALIPAY_PUBLIC_KEY = ALIPAY_PUBLIC_KEY;
    }

    public String getURL() {
        if (!getIS_TEST())//如果是测试环境，采用沙箱测试地址
            URL = "https://openapi.alipaydev.com/gateway.do";//沙箱测试链接地址
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getAPP_PRIVATE_KEY() {
        return APP_PRIVATE_KEY;
    }

    public void setAPP_PRIVATE_KEY(String APP_PRIVATE_KEY) {
        this.APP_PRIVATE_KEY = APP_PRIVATE_KEY;
    }

    public String getFORMAT() {
        return FORMAT;
    }

    public void setFORMAT(String FORMAT) {
        this.FORMAT = FORMAT;
    }

    public String getCHARSET() {
        return CHARSET;
    }

    public void setCHARSET(String CHARSET) {
        this.CHARSET = CHARSET;
    }

    public String getALIPAY_PUBLIC_KEY() {
        return ALIPAY_PUBLIC_KEY;
    }

    public void setALIPAY_PUBLIC_KEY(String ALIPAY_PUBLIC_KEY) {
        this.ALIPAY_PUBLIC_KEY = ALIPAY_PUBLIC_KEY;
    }

    public String getSIGN_TYPE() {
        return SIGN_TYPE;
    }

    public void setSIGN_TYPE(String SIGN_TYPE) {
        this.SIGN_TYPE = SIGN_TYPE;
    }

    public Boolean getIS_TEST() {
        return IS_TEST;
    }

    public void setIS_TEST(Boolean IS_TEST) {
        this.IS_TEST = IS_TEST;
    }
}
