package com.beitu.saas.rest.controller.h5.response;

import com.beitu.saas.borrower.domain.SaasBorrowerPersonalInfoVo;
import com.fqgj.common.api.ResponseData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author linanjun
 * @create 2018/3/22 下午4:49
 * @description
 */
@ApiModel(value = "风控模块个人信息")
public class CreditPersonalInfoResponse implements ResponseData {

    /**
     * QQ
     */
    @ApiModelProperty(value = "QQ")
    private String qq;
    /**
     * 学历
     */
    @ApiModelProperty(value = "学历")
    private Integer education;
    /**
     * 居住地址
     */
    @ApiModelProperty(value = "居住地址")
    private String address;
    /**
     * 居住时长
     */
    @ApiModelProperty(value = "居住时长")
    private String liveDuration;
    /**
     * 婚姻状况
     */
    @ApiModelProperty(value = "婚姻状况")
    private Integer maritalStatus;

    @ApiModelProperty(value = "芝麻分")
    private Integer zmCreditScore;

    public CreditPersonalInfoResponse(SaasBorrowerPersonalInfoVo saasBorrowerPersonalInfoVo) {
        if (saasBorrowerPersonalInfoVo != null) {
            this.qq = saasBorrowerPersonalInfoVo.getWechatCode();
            this.education = saasBorrowerPersonalInfoVo.getEducation();
            this.address = saasBorrowerPersonalInfoVo.getAddress();
            this.liveDuration = saasBorrowerPersonalInfoVo.getLiveDuration();
            this.maritalStatus = saasBorrowerPersonalInfoVo.getMaritalStatus();
            this.zmCreditScore = saasBorrowerPersonalInfoVo.getZmCreditScore();
        }
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLiveDuration() {
        return liveDuration;
    }

    public void setLiveDuration(String liveDuration) {
        this.liveDuration = liveDuration;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getZmCreditScore() {
        return zmCreditScore;
    }

    public void setZmCreditScore(Integer zmCreditScore) {
        this.zmCreditScore = zmCreditScore;
    }
}