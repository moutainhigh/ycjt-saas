package com.beitu.saas.rest.controller.order.request;

import com.fqgj.common.api.ParamsObject;
import com.fqgj.common.api.exception.ApiIllegalArgumentException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author linanjun
 * @create 2018/3/27 上午11:33
 * @description
 */
@ApiModel(description = "贷后管理订单展期操作")
public class AfterLendManagerExtendOrderRequest extends ParamsObject {

    @ApiModelProperty(value = "订单号", required = true)
    @NotBlank(message = "订单号不能为空")
    private String orderNumb;

    @ApiModelProperty(value = "展期结束日期", required = true)
    @NotNull(message = "展期结束日期不能为空")
    private Date repaymentDt;

    @ApiModelProperty(value = "展期利率", required = true)
    @NotNull(message = "展期利率不能为空")
    private BigDecimal extendInterestRatio;

    @ApiModelProperty(value = "手续费")
    private BigDecimal serviceFee;

    public String getOrderNumb() {
        return orderNumb;
    }

    public void setOrderNumb(String orderNumb) {
        this.orderNumb = orderNumb;
    }

    public Date getRepaymentDt() {
        return repaymentDt;
    }

    public void setRepaymentDt(Date repaymentDt) {
        this.repaymentDt = repaymentDt;
    }

    public BigDecimal getExtendInterestRatio() {
        return extendInterestRatio;
    }

    public void setExtendInterestRatio(BigDecimal extendInterestRatio) {
        this.extendInterestRatio = extendInterestRatio;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    @Override
    public void validate() {
        if (this.extendInterestRatio.compareTo(new BigDecimal("0")) < 0 || this.extendInterestRatio.compareTo(new BigDecimal("24")) > 0) {
            throw new ApiIllegalArgumentException("展期利率不正确");
        }
        if (this.serviceFee != null && this.serviceFee.compareTo(this.serviceFee.setScale(0, BigDecimal.ROUND_HALF_UP)) != 0) {
            throw new ApiIllegalArgumentException("手续费请输入整数");
        }
    }

}