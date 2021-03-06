package com.beitu.saas.borrower.client;

import com.beitu.saas.borrower.domain.SaasBorrowerIdentityInfoVo;
import com.fqgj.common.base.BaseService;
import com.fqgj.common.entity.BaseEntity;

/**
 * User: jungle
 * Date: 2018-03-22
 * Time: 15:56:46.984
 */
public interface SaasBorrowerIdentityInfoService<T extends BaseEntity> extends BaseService<T> {
    
    int countByBorrowerCodeAndOrderNumb(String borrowerCode, String orderNumb);
    
    SaasBorrowerIdentityInfoVo getByBorrowerCodeAndOrderNumb(String borrowerCode, String orderNumb);
    
    Boolean updateOrderNumbByBorrowerCode(String orderNumb, String borrowerCode);
    
}