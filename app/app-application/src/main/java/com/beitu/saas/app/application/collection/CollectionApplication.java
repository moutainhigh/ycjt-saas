package com.beitu.saas.app.application.collection;

import com.beitu.saas.app.application.collection.vo.CollectionOrderListVo;
import com.beitu.saas.collection.client.SaasCollectionOrderService;
import com.beitu.saas.collection.param.CollectionOrderQueryParam;
import com.beitu.saas.collection.vo.CollectionOrderInfoDetailVo;
import com.beitu.saas.common.utils.identityNumber.vo.IdcardInfoExtractor;
import com.beitu.saas.order.client.SaasOrderStatusHistoryService;
import com.beitu.saas.order.entity.SaasOrderStatusHistory;
import com.fqgj.common.api.Page;
import com.fqgj.log.factory.LogFactory;
import com.fqgj.log.interfaces.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: WatsonQiu
 * Date: 2018/3/27
 * Time: 下午8:56
 */
@Component
public class CollectionApplication {
    private static final Log LOGGER = LogFactory.getLog(CollectionApplication.class);
    @Autowired
    private SaasCollectionOrderService saasCollectionOrderService;
    @Autowired
    private SaasOrderStatusHistoryService saasOrderStatusHistoryService;

    public List<CollectionOrderListVo> getCollectionOrderListByPage(CollectionOrderQueryParam collectionOrderQueryParam, Page page) {
        List<CollectionOrderInfoDetailVo> collectionOrderInfoDetailVos = saasCollectionOrderService.getCollectionOrderListByPage(collectionOrderQueryParam, page);
        collectionOrderInfoDetailVos.stream().forEach(x -> {
            IdcardInfoExtractor idcardInfoExtractor = new IdcardInfoExtractor(x.getIdentityCode());
            x.setAge(idcardInfoExtractor.getAge());

            SaasOrderStatusHistory saasOrderStatusHistory = saasOrderStatusHistoryService.getOrderStatusHistoryByOrderNumb(x.getOrderNo());
            if (saasOrderStatusHistory != null) {
                x.setRemark(saasOrderStatusHistory.getRemark());
            }
        });


        List<CollectionOrderListVo> collectionOrderListVos = new ArrayList<>();
        collectionOrderInfoDetailVos.stream().forEach(x -> {
            CollectionOrderListVo collectionOrderListVo = new CollectionOrderListVo();
            BeanUtils.copyProperties(x, collectionOrderListVo);

            collectionOrderListVos.add(collectionOrderListVo);
        });
        return collectionOrderListVos;
    }

}
