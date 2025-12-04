package com.lankun.hspd.server.order.purchase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lankun.hspd.server.order.purchase.domain.PurchaseOrderEntity;

/**
 * <p>
 * 采购订单主表 服务类
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
public interface PurchaseOrderService extends IService<PurchaseOrderEntity> {
    Long createOrderFromApplications(Long medicalUnionId, java.util.List<Long> appIds);
    void submitOrderForApproval(Long orderId, String bpmProcessInstanceId);
    void completeOrderExecution(Long orderId);

}
