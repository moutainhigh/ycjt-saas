package com.beitu.saas.rest.controller.order;

import com.beitu.saas.app.api.ApiResponse;
import com.beitu.saas.app.api.DataApiResponse;
import com.beitu.saas.app.api.ModuleApiResponse;
import com.beitu.saas.app.application.order.OrderApplication;
import com.beitu.saas.app.application.order.OrderBillDetailApplication;
import com.beitu.saas.app.application.order.vo.QueryOrderBillDetailVo;
import com.beitu.saas.app.common.RequestLocalInfo;
import com.beitu.saas.auth.entity.SaasAdmin;
import com.beitu.saas.order.enums.OrderStatusEnum;
import com.beitu.saas.rest.controller.order.request.*;
import com.beitu.saas.rest.controller.order.response.AfterLendOrderDetailResponse;
import com.beitu.saas.rest.controller.order.response.AfterLendOrderListResponse;
import com.fqgj.common.api.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author linanjun
 * @create 2018/3/27 下午9:44
 * @description
 */
@Controller
@RequestMapping("/order/after/lend")
@Api(description = "贷后管理模块")
public class AfterLendManageController {

    @Autowired
    private OrderApplication orderApplication;

    @Autowired
    private OrderBillDetailApplication orderBillDetailApplication;

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "贷后管理订单查询", response = AfterLendOrderListResponse.class)
    public ModuleApiResponse<AfterLendOrderListResponse> query(@RequestBody @Valid AfterLendOrderQueryRequest req, Page page) {
        SaasAdmin saasAdmin = RequestLocalInfo.getCurrentAdmin().getSaasAdmin();
        QueryOrderBillDetailVo queryOrderBillDetailVo = new QueryOrderBillDetailVo();
        queryOrderBillDetailVo.setMerchantCode(saasAdmin.getMerchantCode());
        BeanUtils.copyProperties(req, queryOrderBillDetailVo);
        return new ModuleApiResponse(new AfterLendOrderListResponse(orderBillDetailApplication.listAfterLendManageOrder(queryOrderBillDetailVo, page)), page);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "贷后管理订单详情查看", response = AfterLendOrderDetailResponse.class)
    public DataApiResponse<AfterLendOrderDetailResponse> detail(@RequestBody @Valid AfterLendOrderDetailRequest req) {
        String adminCode = RequestLocalInfo.getCurrentAdmin().getSaasAdmin().getCode();
        AfterLendOrderDetailResponse response = new AfterLendOrderDetailResponse();
        response.setOrderNumb(req.getOrderNumb());
        return new DataApiResponse<>(response);
    }

    @RequestMapping(value = "/extend", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "展期", response = ApiResponse.class)
    public ApiResponse extend(@RequestBody @Valid AfterLendManagerExtendOrderRequest req) {
        String adminCode = RequestLocalInfo.getCurrentAdmin().getSaasAdmin().getCode();
        orderApplication.extendOrder(adminCode, req.getOrderNumb(), req.getRepaymentDt(), req.getExtendInterestRatio());
        return new ApiResponse("操作成功");
    }

    @RequestMapping(value = "/destroy", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "核销", response = ApiResponse.class)
    public ApiResponse destroy(@RequestBody @Valid AfterLendManagerDestroyOrderRequest req) {
        String adminCode = RequestLocalInfo.getCurrentAdmin().getSaasAdmin().getCode();
        orderApplication.destroyOrder(adminCode, req.getOrderNumb());
        return new ApiResponse("操作成功");
    }

}
