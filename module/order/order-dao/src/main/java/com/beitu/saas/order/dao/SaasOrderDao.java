package com.beitu.saas.order.dao;

import com.fqgj.common.base.BaseMapper;
import com.beitu.saas.order.entity.SaasOrder;

import java.util.List;
import java.util.Map;

/**
 * User: jungle
 * Date: 2018-03-23
 * Time: 15:18:54.630
 */

public interface SaasOrderDao extends BaseMapper<SaasOrder> {

    int countByConditions(Map<String, Object> conditions);

    List<SaasOrder> selectByConditions(Map<String, Object> conditions);

    SaasOrder selectByBorrowerCodeAndChannelCode(String borrowerCode, String channelCode);

    SaasOrder selectByOrderNumbAndMerchantCode(String orderNumb, String merchantCode);

    int updateOrderStatus(Map<String, Object> params);

    int updateOrderRemark(Map<String, Object> params);

    List<SaasOrder> selectByBorrowerCodeAndOrderStatusList(String borrowerCode, List<Integer> orderStatusList);

    int updatePreliminaryReviewerCode(Long orderId, String operatorCode);

    int updateFinalReviewerCode(Long orderId, String operatorCode);

    List<String> selectOrderNumbByParams(String merchantCode, Integer orderStatus);

    SaasOrder selectMainSaasOrderByOrderNumb(String orderNumb);

}