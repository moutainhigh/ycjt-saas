package com.beitu.saas.finance.client;
import com.beitu.saas.finance.entity.SaasMerchantBalanceInfoEntity;
import com.fqgj.common.base.BaseService;
import com.fqgj.common.entity.BaseEntity;

import java.math.BigDecimal;

/**
* User: fenqiguanjia
* Date: 2018-03-28
* Time: 11:41:55.204
*/
public interface SaasMerchantBalanceInfoService<T extends BaseEntity> extends BaseService<T> {
    SaasMerchantBalanceInfoEntity getMerchantBalanceInfoByMerchantCode(String merchantCode);

    Boolean increase(String merchantCode, BigDecimal value);

    Boolean increase(String merchantCode, Double value);

    Boolean decrease(String merchantCode, BigDecimal value);

    Boolean decrease(String merchantCode, Double value);

    BigDecimal getBalanceByMerchantCode(String merchantCode);
}