package com.beitu.saas.credit.dao;

import com.fqgj.common.base.BaseMapper;
import com.beitu.saas.credit.entity.SaasCreditCarrierBill;

import java.util.List;

/**
 * User: jungle
 * Date: 2018-04-06
 * Time: 18:11:44.642
 */

public interface SaasCreditCarrierBillDao extends BaseMapper<SaasCreditCarrierBill> {

    int batchAddSaasCreditCarrierBill(List<SaasCreditCarrierBill> saasCreditCarrierBillList);

}