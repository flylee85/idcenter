/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-11-28 20:53 创建
 */
package org.antframework.idcenter.facade.order;

import lombok.Getter;
import lombok.Setter;
import org.antframework.common.util.facade.AbstractOrder;

import javax.validation.constraints.NotBlank;

/**
 * 删除id提供者order
 */
@Getter
@Setter
public class DeleteIderOrder extends AbstractOrder {
    // id提供者的id（id编码）
    @NotBlank
    private String iderId;
}
