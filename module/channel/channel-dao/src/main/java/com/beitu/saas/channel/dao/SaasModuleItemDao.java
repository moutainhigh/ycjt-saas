package com.beitu.saas.channel.dao;

import com.beitu.saas.channel.entity.SaasModuleItemEntity;
import com.fqgj.common.base.BaseMapper;

import java.util.List;

/**
* User: fenqiguanjia
* Date: 2018-03-21
* Time: 20:58:19.390
*/

public interface SaasModuleItemDao  extends BaseMapper<SaasModuleItemEntity> {
    List<SaasModuleItemEntity> selectModuleItemEntityList(String moduleCode);
}