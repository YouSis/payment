package com.wfj.pay.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 类说明 : 支付订单类.
 */
public class OrderRequestDTO implements java.io.Serializable {
    /**
     * 序列化版本.
     */
    private static final long serialVersionUID = 130403101548156L;

    /**
     * 支付方式(PayChannelEnum枚举类型).
     */
    @NotEmpty
    private String payType;
    /**
     * 商品名称.
     */
    @NotEmpty
    @Length(min = 1, max = 100)
    private String goodsName;
    /**
     * 订单备注.
     */
    @Length( max = 200)
    private String remark;
    /**
     * 支付金额.
     */
    @NotNull
    @Digits(integer = 8,fraction = 2)
    private Double totalFee;
    /**
     * 业务平台ID.
     */
    @NotEmpty
    @Length(min = 1, max = 10)
    private String bpId;
    /**
     * 业务平台订单流水号.
     */
    @NotEmpty
    @Length(min = 1, max = 100)
    @Pattern(regexp = "^\\d+$")
    private String bpOrderId;
    /**
     * 订单内容.
     */
    @NotEmpty
    @Length(min = 1, max = 100)
    private String content;
    /**
     * 订单初始化终端标识（01:PC端，02:手机端，03：PAD，04：POS）
     */
    @NotEmpty
    private String initOrderTerminal;

    /**
     * 支付服务.用于后端级联下拉查询分类.网银直连为1 、第三方渠道为2  、银行直连为3
     */
    @NotEmpty
    private String payService;

    /**
     * 签约商户编码
     */
    @NotEmpty
    @Length(max = 100)
    private String merCode;

    /**
     * OMS商品信息，用于计算费率的促销分摊 格式：0000000000101^99.50^2|0000000000101^18.98^3
     */
    @Length(max=4000)
    private String goodsContent;
    /**
     * 签名
     */
    @NotEmpty
    private String sign;
    /**
     * 防钓鱼时间戳
     */
    @NotEmpty
    private String antiPhishingKey;
    /**
     * 收银员号
     */
    @NotEmpty
    private String cashier;
    /**
     * 支付授权码
     */
    @NotEmpty
    private String authCode;
    /**
     * 客户端请求IP
     */
    private String payIp;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
    }

    public String getBpOrderId() {
        return bpOrderId;
    }

    public void setBpOrderId(String bpOrderId) {
        this.bpOrderId = bpOrderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInitOrderTerminal() {
        return initOrderTerminal;
    }

    public void setInitOrderTerminal(String initOrderTerminal) {
        this.initOrderTerminal = initOrderTerminal;
    }

    public String getPayService() {
        return payService;
    }

    public void setPayService(String payService) {
        this.payService = payService;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getGoodsContent() {
        return goodsContent;
    }

    public void setGoodsContent(String goodsContent) {
        this.goodsContent = goodsContent;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAntiPhishingKey() {
        return antiPhishingKey;
    }

    public void setAntiPhishingKey(String antiPhishingKey) {
        this.antiPhishingKey = antiPhishingKey;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getPayIp() {
        return payIp;
    }

    public void setPayIp(String payIp) {
        this.payIp = payIp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OrderRequestDTO{");
        sb.append("payType='").append(payType).append('\'');
        sb.append(", goodsName='").append(goodsName).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", totalFee=").append(totalFee);
        sb.append(", bpId='").append(bpId).append('\'');
        sb.append(", bpOrderId='").append(bpOrderId).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", initOrderTerminal='").append(initOrderTerminal).append('\'');
        sb.append(", payService='").append(payService).append('\'');
        sb.append(", merCode='").append(merCode).append('\'');
        sb.append(", goodsContent='").append(goodsContent).append('\'');
        sb.append(", sign='").append(sign).append('\'');
        sb.append(", antiPhishingKey='").append(antiPhishingKey).append('\'');
        sb.append(", cashier='").append(cashier).append('\'');
        sb.append(", authCode='").append(authCode).append('\'');
        sb.append(", payIp='").append(payIp).append('\'');
        sb.append('}');
        return sb.toString();
    }
}