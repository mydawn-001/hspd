package com.lankun.hspd.server.order.purchase.strategy;

import com.lankun.hspd.server.order.purchase.dto.PurchaseOrderCreateDTO;

/**
 * 采购策略接口 - 策略模式
 */
public interface PurchaseStrategy {

    /**
     * 创建采购订单
     * @param orderDTO 订单信息
     * @return 订单ID
     */
    Long createPurchaseOrder(PurchaseOrderCreateDTO orderDTO);

    /**
     * 执行采购
     * @param orderId 订单ID
     * @return 是否执行成功
     */
    boolean executePurchase(Long orderId);

    /**
     * 获取采购类型
     * @return 采购类型
     */
    String getType();
}
