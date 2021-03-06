package com.beitu.saas.borrower.service.impl;

import com.beitu.saas.borrower.client.SaasBorrowerIdentityInfoService;
import com.beitu.saas.borrower.dao.SaasBorrowerIdentityInfoDao;
import com.beitu.saas.borrower.domain.SaasBorrowerIdentityInfoVo;
import com.beitu.saas.borrower.entity.SaasBorrowerIdentityInfo;
import com.fqgj.common.base.AbstractBaseService;
import com.fqgj.common.base.NameSpace;
import com.fqgj.common.utils.CollectionUtils;
import com.fqgj.log.enhance.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * User: jungle
 * Date: 2018-03-22
 * Time: 15:56:46.986
 */
@Module(value = "SAAS借款人身份证信息表服务模块")
@NameSpace("com.beitu.saas.borrower.dao.impl.SaasBorrowerIdentityInfoDaoImpl")
@Service
public class SaasBorrowerIdentityInfoServiceImpl extends AbstractBaseService implements SaasBorrowerIdentityInfoService {

    @Autowired
    private SaasBorrowerIdentityInfoDao saasBorrowerIdentityInfoDao;


    @Override
    public int countByBorrowerCodeAndOrderNumb(String borrowerCode, String orderNumb) {
        return saasBorrowerIdentityInfoDao.countByBorrowerCodeAndOrderNumb(borrowerCode, orderNumb);
    }

    @Override
    public SaasBorrowerIdentityInfoVo getByBorrowerCodeAndOrderNumb(String borrowerCode, String orderNumb) {
        SaasBorrowerIdentityInfo saasBorrowerIdentityInfo = saasBorrowerIdentityInfoDao.selectByBorrowerCodeAndOrderNumb(borrowerCode, orderNumb);
        return SaasBorrowerIdentityInfoVo.convertEntityToVO(saasBorrowerIdentityInfo);
    }

    @Override
    public Boolean updateOrderNumbByBorrowerCode(String orderNumb, String borrowerCode) {
        return saasBorrowerIdentityInfoDao.updateOrderNumbByBorrowerCode(orderNumb, borrowerCode) == 1;
    }

}


