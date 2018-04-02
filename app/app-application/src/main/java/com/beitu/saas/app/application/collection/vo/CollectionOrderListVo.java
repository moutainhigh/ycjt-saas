package com.beitu.saas.app.application.collection.vo;

import com.fqgj.common.api.ResponseData;
import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;

/**
 * @author linanjun
 * @create 2018/4/1 下午8:39
 * @description
 */
@ApiModel(value = "催收列表展示信息")
public class CollectionOrderListVo implements ResponseData {

    private String orderNo;
    private String name;
    private String mobile;
    private BigDecimal realCapital;
    private BigDecimal shouldRepayCapital;
    private String createDt;
    private String repaymentDt;
    private Boolean extend;
    private String overdueDuration;
    private String channelName;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getRealCapital() {
        return realCapital;
    }

    public CollectionOrderListVo setRealCapital(BigDecimal realCapital) {
        this.realCapital = realCapital;
        return this;
    }

    public BigDecimal getShouldRepayCapital() {
        return shouldRepayCapital;
    }

    public CollectionOrderListVo setShouldRepayCapital(BigDecimal shouldRepayCapital) {
        this.shouldRepayCapital = shouldRepayCapital;
        return this;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getRepaymentDt() {
        return repaymentDt;
    }

    public void setRepaymentDt(String repaymentDt) {
        this.repaymentDt = repaymentDt;
    }

    public Boolean getExtend() {
        return extend;
    }

    public void setExtend(Boolean extend) {
        this.extend = extend;
    }

    public String getOverdueDuration() {
        return overdueDuration;
    }

    public void setOverdueDuration(String overdueDuration) {
        this.overdueDuration = overdueDuration;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
