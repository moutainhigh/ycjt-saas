package com.beitu.saas.collection.dao;

import com.beitu.saas.collection.param.CollectionOrderQueryParam;
import com.beitu.saas.collection.entity.SaasCollectionOrderEntity;
import com.beitu.saas.collection.vo.CollectionOrderInfoDetailVo;
import com.fqgj.common.api.Page;
import com.fqgj.common.base.BaseMapper;

import java.util.List;


/**
 * User: fenqiguanjia
 * Date: 2018-03-21
 * Time: 20:58:19.360
 */

public interface SaasCollectionOrderDao extends BaseMapper<SaasCollectionOrderEntity> {
    SaasCollectionOrderEntity selectSaasCollectionOrderEntity(String orderNo);

    List<CollectionOrderInfoDetailVo> selectCollectionOrderListByPage(CollectionOrderQueryParam collectionOrderQueryParam, Page page);

    Integer queryTotalCollectionOrderListCount(CollectionOrderQueryParam collectionOrderQueryParam);

    Integer queryCollectionOrderCount(String orderNo);

}