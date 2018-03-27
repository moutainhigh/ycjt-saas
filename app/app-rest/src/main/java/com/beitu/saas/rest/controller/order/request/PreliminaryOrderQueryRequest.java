package com.beitu.saas.rest.controller.order.request;

import com.fqgj.common.api.ParamsObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @author linanjun
 * @create 2018/3/21 下午3:28
 * @description
 */
@ApiModel(description = "初审列表查询参数")
public class PreliminaryOrderQueryRequest extends ParamsObject {

    @ApiModelProperty(value = "处理状态")
    @NotBlank(message = "处理状态不能为空")
    private String dealStatus;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "客户姓名")
    private String userName;

    @ApiModelProperty(value = "身份证号")
    private String identityCode;

    @ApiModelProperty(value = "渠道")
    private String channelCode;

    @ApiModelProperty(value = "状态")
    private String orderStatus;

    @ApiModelProperty(value = "申请时长")
    private Integer applyDuration;

    @ApiModelProperty(value = "申请结束时间")
    private Date applyEndDate;

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getApplyDuration() {
        return applyDuration;
    }

    public void setApplyDuration(Integer applyDuration) {
        this.applyDuration = applyDuration;
    }

    public Date getApplyEndDate() {
        return applyEndDate;
    }

    public void setApplyEndDate(Date applyEndDate) {
        this.applyEndDate = applyEndDate;
    }

    @Override
    public void validate() {

    }

}