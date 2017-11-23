/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-11-21 21:04 创建
 */
package org.antframework.ids.biz.provider;

import org.antframework.ids.facade.api.manage.IderManageService;
import org.antframework.ids.facade.order.AddOrModifyIderOrder;
import org.antframework.ids.facade.order.ModifyIderCurrentIdOrder;
import org.antframework.ids.facade.order.ModifyIderCurrentPeriodOrder;
import org.antframework.ids.facade.result.AddOrModifyIderResult;
import org.antframework.ids.facade.result.ModifyIderCurrentIdResult;
import org.antframework.ids.facade.result.ModifyIderCurrentPeriodResult;
import org.bekit.service.ServiceEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * id提供者管理服务提供者
 */
@Service
public class IderManageServiceProvider implements IderManageService {
    @Autowired
    private ServiceEngine serviceEngine;

    @Override
    public AddOrModifyIderResult addOrModifyIder(AddOrModifyIderOrder order) {
        return serviceEngine.execute("addOrModifyIderService", order);
    }

    @Override
    public ModifyIderCurrentPeriodResult modifyIderCurrentPeriod(ModifyIderCurrentPeriodOrder order) {
        return serviceEngine.execute("modifyIderCurrentPeriodService", order);
    }

    @Override
    public ModifyIderCurrentIdResult modifyIderCurrentId(ModifyIderCurrentIdOrder order) {
        return serviceEngine.execute("modifyIderCurrentIdService", order);
    }
}