package com.beitu.saas.user.client;

import com.beitu.saas.user.domain.SaasUserEsignAuthorizationVo;
import com.fqgj.common.base.BaseService;
import com.fqgj.common.entity.BaseEntity;

/**
 * User: jungle
 * Date: 2018-04-02
 * Time: 14:27:05.997
 */
public interface SaasUserEsignAuthorizationService<T extends BaseEntity> extends BaseService<T> {

    Boolean isSuccessAuthorization(String userCode);

    String getSealUrlByUserCode(String userCode);

    void create(SaasUserEsignAuthorizationVo saasUserEsignAuthorizationVo);

}