package com.beitu.saas.credit.service.impl;
import com.beitu.saas.credit.client.SaasCreditBmpDetailService;
import com.beitu.saas.credit.dao.SaasCreditBmpDetailDao;
import com.fqgj.common.base.AbstractBaseService;
import com.fqgj.common.base.NameSpace;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.fqgj.log.enhance.Module;

/**
* User: jungle
* Date: 2018-04-06
* Time: 18:11:44.620
*/
@Module(value = "电话邦匹配数据详情表服务模块")
@NameSpace("com.beitu.saas.credit.dao.impl.SaasCreditBmpDetailDaoImpl")
@Service
public class SaasCreditBmpDetailServiceImpl extends AbstractBaseService implements SaasCreditBmpDetailService {

    @Autowired
    private SaasCreditBmpDetailDao saasCreditBmpDetailDao;
}


