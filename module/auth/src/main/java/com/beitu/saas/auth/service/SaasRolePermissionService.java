package com.beitu.saas.auth.service;
import com.beitu.saas.auth.entity.SaasRolePermission;
import com.fqgj.common.base.BaseService;
import com.fqgj.common.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* User: xiaochong
* Date: 2018-03-22
* Time: 15:36:13.696
*/
public interface SaasRolePermissionService<T extends BaseEntity> extends BaseService<T> {

    List<SaasRolePermission> getMenuPermissionByRoleId(Integer roleId);

    List<SaasRolePermission> getButtonPermissionByRoleId(Integer roleId);

    SaasRolePermission replace(SaasRolePermission record);

    Boolean addPermissionToRole(Integer roleId, List menuIds, List buttonIds);

    Boolean replacePermissionToRole(Integer roleId, List menuIds, List buttonIds);
}