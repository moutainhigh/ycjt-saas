package com.beitu.saas.app.application.order;

import com.beitu.saas.app.application.order.vo.H5OrderListVo;
import com.beitu.saas.app.application.order.vo.OrderDetailVo;
import com.beitu.saas.app.enums.BorrowerOrderApplyStatusEnum;
import com.beitu.saas.app.enums.H5OrderBillDetailViewTypeEnum;
import com.beitu.saas.borrower.client.SaasBorrowerRealInfoService;
import com.beitu.saas.borrower.domain.SaasBorrowerRealInfoVo;
import com.beitu.saas.order.client.SaasOrderApplicationService;
import com.beitu.saas.order.client.SaasOrderBillDetailService;
import com.beitu.saas.order.client.SaasOrderService;
import com.beitu.saas.order.client.SaasOrderStatusHistoryService;
import com.beitu.saas.order.domain.SaasOrderApplicationVo;
import com.beitu.saas.order.domain.SaasOrderBillDetailVo;
import com.beitu.saas.order.domain.SaasOrderVo;
import com.beitu.saas.order.entity.SaasOrder;
import com.beitu.saas.order.entity.SaasOrderBillDetail;
import com.beitu.saas.order.entity.SaasOrderStatusHistory;
import com.beitu.saas.order.enums.OrderErrorCodeEnum;
import com.beitu.saas.order.enums.OrderStatusEnum;
import com.fqgj.common.api.Page;
import com.fqgj.common.utils.CollectionUtils;
import com.fqgj.common.utils.DateUtil;
import com.fqgj.exception.common.ApplicationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author linanjun
 * @create 2018/3/21 下午4:00
 * @description
 */
@Service
public class OrderApplication {

    @Autowired
    private SaasOrderApplicationService saasOrderApplicationService;

    @Autowired
    private SaasOrderService saasOrderService;

    @Autowired
    private SaasOrderBillDetailService saasOrderBillDetailService;

    @Autowired
    private OrderCalculateApplication orderCalculateApplication;

    @Autowired
    private SaasBorrowerRealInfoService saasBorrowerRealInfoService;

    @Autowired
    private SaasOrderStatusHistoryService saasOrderStatusHistoryService;

    public BorrowerOrderApplyStatusEnum getOrderApplyStatus(String borrowerCode, String channelCode) {
        if (saasOrderApplicationService.getByBorrowerCode(borrowerCode) != null) {
            return BorrowerOrderApplyStatusEnum.REVIEWING;
        } else if (saasOrderService.isReviewRefuse(borrowerCode, channelCode)) {
            return BorrowerOrderApplyStatusEnum.REFUSE;
        }
        return BorrowerOrderApplyStatusEnum.NO_SUBMIT;
    }

    @Transactional
    public SaasOrder createOrder(SaasOrderApplicationVo saasOrderApplicationVo, String orderNumb) {
        SaasOrder saasOrder = new SaasOrder();
        BeanUtils.copyProperties(saasOrderApplicationVo, saasOrder);
        saasOrder.setOrderNumb(orderNumb);
        saasOrder.setCreatedDt(saasOrderApplicationVo.getGmtCreate());
        saasOrder.setOrderStatus(OrderStatusEnum.SUBMIT_PRELIMINARY_REVIEW.getCode());
        saasOrder.setExpireDate(DateUtil.addDate(new Date(), 20));
        saasOrder.setTotalInterestFee(orderCalculateApplication.getInterest(saasOrder.getRealCapital(), saasOrder.getTotalInterestRatio(), saasOrder.getCreatedDt(), saasOrder.getRepaymentDt()));
        saasOrderService.create(saasOrder);
        return saasOrder;
    }

    @Transactional
    public SaasOrderBillDetail createOrderBillDetail(SaasOrderVo saasOrderVo) {
        return new SaasOrderBillDetail();
    }

    public List<H5OrderListVo> listH5Order(String borrowerCode, String merchantCode) {
        List<SaasOrderBillDetailVo> saasOrderBillDetailVoList = saasOrderBillDetailService.listByBorrowerCodeAndMerchantCode(borrowerCode, merchantCode);
        if (CollectionUtils.isEmpty(saasOrderBillDetailVoList)) {
            return null;
        }
        List<H5OrderListVo> results = new ArrayList<>(saasOrderBillDetailVoList.size());
        saasOrderBillDetailVoList.forEach(saasOrderBillDetailVo -> {
            H5OrderListVo h5OrderListVo = new H5OrderListVo();
            h5OrderListVo.setAmount(orderCalculateApplication.getAmount(saasOrderBillDetailVo).toString());
            h5OrderListVo.setRepaymentDt(DateUtil.convertDateToString(saasOrderBillDetailVo.getRepaymentDt()));
            h5OrderListVo.setOrderStatus(saasOrderService.getOrderStatusByOrderNumb(saasOrderBillDetailVo.getOrderNumb()).getCode());
            h5OrderListVo.setViewType(getH5ViewTypeByOrderStatus(h5OrderListVo.getOrderStatus()));
            results.add(h5OrderListVo);
        });
        return results;
    }

    private Integer getH5ViewTypeByOrderStatus(Integer orderStatus) {
        if (OrderStatusEnum.HAS_BEEN_DESTROY.getCode().equals(orderStatus) ||
                OrderStatusEnum.HAS_BEEN_PAYMENT.getCode().equals(orderStatus)) {
            return H5OrderBillDetailViewTypeEnum.FINISHED.getCode();
        } else if (OrderStatusEnum.FOR_REIMBURSEMENT.getCode().equals(orderStatus)) {
            return H5OrderBillDetailViewTypeEnum.FOR_REIMBURSEMENT.getCode();
        } else if (OrderStatusEnum.TO_CONFIRM_EXTEND.getCode().equals(orderStatus)) {
            return H5OrderBillDetailViewTypeEnum.TO_CONFIRM_EXTEND.getCode();
        } else if (OrderStatusEnum.OVERDUE.getCode().equals(orderStatus)) {
            return H5OrderBillDetailViewTypeEnum.OVERDUE.getCode();
        }
        return H5OrderBillDetailViewTypeEnum.FOR_REIMBURSEMENT.getCode();
    }

    public OrderDetailVo getOrderDetailVoByOrderNumb(String orderNumb) {
        List<SaasOrderVo> saasOrderVoList = saasOrderService.listEffectiveOrderByOrderNumb(orderNumb);
        if (CollectionUtils.isEmpty(saasOrderVoList)) {
            return null;
        }
        SaasOrderVo saasOrderVo = saasOrderVoList.get(0);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        BeanUtils.copyProperties(saasOrderVo, orderDetailVo);
        SaasBorrowerRealInfoVo realInfoVo = saasBorrowerRealInfoService.getBorrowerRealInfoByBorrowerCode(saasOrderVo.getBorrowerCode());
        orderDetailVo.setBorrowerName(realInfoVo.getName());
        orderDetailVo.setBorrowerIdentityCode(realInfoVo.getIdentityCode());
        return orderDetailVo;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void updateOrderStatus(String operatorCode, String orderNumb, OrderStatusEnum updateOrderStatus, String remark) {
        SaasOrderVo saasOrderVo = saasOrderService.getByOrderNumb(orderNumb);
        OrderStatusEnum currentOrderStatus = OrderStatusEnum.getByCode(saasOrderVo.getOrderStatus());
        switch (updateOrderStatus) {
            case IN_PRELIMINARY_REVIEWER:
                if (OrderStatusEnum.SUBMIT_PRELIMINARY_REVIEW.equals(currentOrderStatus)) {
                    updateOrderStatus(operatorCode, saasOrderVo.getSaasOrderId(), saasOrderVo.getOrderNumb(), currentOrderStatus, updateOrderStatus, remark);
                }
                break;
            case PRELIMINARY_REVIEWER_GET_ORDER:
                if (OrderStatusEnum.IN_PRELIMINARY_REVIEWER.equals(currentOrderStatus)
                        || OrderStatusEnum.SUBMIT_PRELIMINARY_REVIEW.equals(currentOrderStatus)
                        || OrderStatusEnum.PRELIMINARY_REVIEWER_REJECT.equals(currentOrderStatus)
                        || OrderStatusEnum.PRELIMINARY_REVIEWER_REFUSE.equals(currentOrderStatus)) {
                    updateOrderStatus(operatorCode, saasOrderVo.getSaasOrderId(), saasOrderVo.getOrderNumb(), currentOrderStatus, updateOrderStatus, remark);
                }
                break;
            case PRELIMINARY_REVIEWER_REJECT:
                if (OrderStatusEnum.IN_PRELIMINARY_REVIEWER.equals(currentOrderStatus)
                        || OrderStatusEnum.PRELIMINARY_REVIEWER_GET_ORDER.equals(currentOrderStatus)) {
                    updateOrderStatus(operatorCode, saasOrderVo.getSaasOrderId(), saasOrderVo.getOrderNumb(), currentOrderStatus, updateOrderStatus, remark);
                }
                break;
            case PRELIMINARY_REVIEWER_REFUSE:
                if (OrderStatusEnum.IN_PRELIMINARY_REVIEWER.equals(currentOrderStatus)
                        || OrderStatusEnum.PRELIMINARY_REVIEWER_GET_ORDER.equals(currentOrderStatus)) {
                    updateOrderStatus(operatorCode, saasOrderVo.getSaasOrderId(), saasOrderVo.getOrderNumb(), currentOrderStatus, updateOrderStatus, remark);
                }
                break;
            case IN_FINAL_REVIEWER:
                if (OrderStatusEnum.SUBMIT_FINAL_REVIEW.equals(currentOrderStatus)) {
                    updateOrderStatus(operatorCode, saasOrderVo.getSaasOrderId(), saasOrderVo.getOrderNumb(), currentOrderStatus, updateOrderStatus, remark);
                }
                break;
        }
        throw new ApplicationException(OrderErrorCodeEnum.ILLEGAL_OPERATION_ORDER_STATUS);
    }

    private void updateOrderStatus(String operatorCode, Long orderId, String orderNumb, OrderStatusEnum currentOrderStatus, OrderStatusEnum updateOrderStatus, String remark) {
        SaasOrderStatusHistory saasOrderStatusHistory = new SaasOrderStatusHistory();
        saasOrderStatusHistory.setOrderId(orderId);
        saasOrderStatusHistory.setOrderNumb(orderNumb);
        saasOrderStatusHistory.setCurrentOrderStatus(currentOrderStatus.getCode());
        saasOrderStatusHistory.setUpdateOrderStatus(updateOrderStatus.getCode());
        saasOrderStatusHistory.setRemark(remark);
        saasOrderStatusHistory.setOperatorCode(operatorCode);
        saasOrderStatusHistoryService.create(saasOrderStatusHistory);
        if (!saasOrderService.updateOrderStatus(orderId, currentOrderStatus, updateOrderStatus)) {
            throw new ApplicationException(OrderErrorCodeEnum.ORDER_STATUS_UPDATE_FAILURE);
        }
    }

}
