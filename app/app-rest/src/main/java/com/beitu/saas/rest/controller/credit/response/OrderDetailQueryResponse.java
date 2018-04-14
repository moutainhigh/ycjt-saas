package com.beitu.saas.rest.controller.credit.response;

import com.beitu.saas.app.application.order.vo.SaasOrderDetailVo;
import com.beitu.saas.common.utils.DateUtil;
import com.beitu.saas.risk.helpers.CollectionUtils;
import com.fqgj.common.api.ResponseData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author linanjun
 * @create 2018/4/4 上午11:12
 * @description
 */
@ApiModel(value = "订单详情")
public class OrderDetailQueryResponse implements ResponseData {

    @ApiModelProperty(value = "查看合同URL")
    private String viewContractUrl;

    @ApiModelProperty(value = "下载合同URL")
    private String downloadContractUrl;

    @ApiModelProperty
    private Boolean extend;

    @ApiModelProperty(value = "当前账单信息")
    private SaasOrderDetailVo mainOrderDetailVo;

    @ApiModelProperty(value = "原交易账单信息")
    private SaasOrderDetailVo originalOrderDetailVo;

    @ApiModelProperty(value = "展期账单信息")
    private List<SaasOrderDetailVo> extendOrderDetailVoList;

    public OrderDetailQueryResponse(List<SaasOrderDetailVo> allOrderBillDetail, String viewContractUrl, String downloadContractUrl) {
        if (CollectionUtils.isEmpty(allOrderBillDetail)) {
            return;
        }
        this.originalOrderDetailVo = allOrderBillDetail.get(0);
        this.extend = Boolean.FALSE;
        if (allOrderBillDetail.size() > 1) {
            this.extend = Boolean.TRUE;
            this.extendOrderDetailVoList = allOrderBillDetail.subList(1, allOrderBillDetail.size());
        }
        this.mainOrderDetailVo = new SaasOrderDetailVo();
        BeanUtils.copyProperties(allOrderBillDetail.get(allOrderBillDetail.size() - 1), mainOrderDetailVo);
        this.mainOrderDetailVo.setTotalInterestRatio(originalOrderDetailVo.getTotalInterestRatio());
        this.mainOrderDetailVo.setCreatedDate(originalOrderDetailVo.getCreatedDate());
        this.mainOrderDetailVo.setOverdueDuration(allOrderBillDetail.stream().collect(Collectors.summingInt(SaasOrderDetailVo::getOverdueDuration)));
        try {
            this.mainOrderDetailVo.setBorrowingDuration(DateUtil.countDays(this.mainOrderDetailVo.getRepaymentDate(), this.mainOrderDetailVo.getCreatedDate()));
        } catch (Exception e) {
        }
        this.viewContractUrl = viewContractUrl;
        this.downloadContractUrl = downloadContractUrl;
    }

    public String getViewContractUrl() {
        return viewContractUrl;
    }

    public void setViewContractUrl(String viewContractUrl) {
        this.viewContractUrl = viewContractUrl;
    }

    public String getDownloadContractUrl() {
        return downloadContractUrl;
    }

    public void setDownloadContractUrl(String downloadContractUrl) {
        this.downloadContractUrl = downloadContractUrl;
    }

    public Boolean getExtend() {
        return extend;
    }

    public void setExtend(Boolean extend) {
        this.extend = extend;
    }

    public SaasOrderDetailVo getMainOrderDetailVo() {
        return mainOrderDetailVo;
    }

    public void setMainOrderDetailVo(SaasOrderDetailVo mainOrderDetailVo) {
        this.mainOrderDetailVo = mainOrderDetailVo;
    }

    public SaasOrderDetailVo getOriginalOrderDetailVo() {
        return originalOrderDetailVo;
    }

    public void setOriginalOrderDetailVo(SaasOrderDetailVo originalOrderDetailVo) {
        this.originalOrderDetailVo = originalOrderDetailVo;
    }

    public List<SaasOrderDetailVo> getExtendOrderDetailVoList() {
        return extendOrderDetailVoList;
    }

    public void setExtendOrderDetailVoList(List<SaasOrderDetailVo> extendOrderDetailVoList) {
        this.extendOrderDetailVoList = extendOrderDetailVoList;
    }
}