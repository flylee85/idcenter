/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-11-28 20:58 创建
 */
package org.antframework.idcenter.biz.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antframework.common.util.facade.EmptyResult;
import org.antframework.idcenter.dal.dao.IdProducerDao;
import org.antframework.idcenter.dal.dao.IderDao;
import org.antframework.idcenter.dal.entity.IdProducer;
import org.antframework.idcenter.dal.entity.Ider;
import org.antframework.idcenter.facade.order.DeleteIderOrder;
import org.bekit.service.annotation.service.Service;
import org.bekit.service.annotation.service.ServiceExecute;
import org.bekit.service.engine.ServiceContext;

import java.util.List;

/**
 * 删除id提供者服务
 */
@Service(enableTx = true)
@AllArgsConstructor
@Slf4j
public class DeleteIderService {
    // id提供者dao
    private final IderDao iderDao;
    // id生产者dao
    private final IdProducerDao idProducerDao;

    @ServiceExecute
    public void execute(ServiceContext<DeleteIderOrder, EmptyResult> context) {
        DeleteIderOrder order = context.getOrder();

        Ider ider = iderDao.findLockByIderId(order.getIderId());
        if (ider == null) {
            return;
        }
        // 删除id生产者
        List<IdProducer> idProducers = idProducerDao.findLockByIderIdOrderByIndexAsc(order.getIderId());
        for (IdProducer idProducer : idProducers) {
            log.info("删除id生产者：{}", idProducer);
            idProducerDao.delete(idProducer);
        }
        // 删除id提供者
        iderDao.delete(ider);
        log.info("删除id提供者：{}", ider);
    }
}
