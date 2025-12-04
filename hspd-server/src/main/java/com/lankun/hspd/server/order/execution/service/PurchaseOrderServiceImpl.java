// com/lankun/hspd/server/order/purchase/service/PurchaseOrderServiceImpl.java
package com.lankun.hspd.server.order.purchase.service;

import com.lankun.hspd.server.order.purchase.dto.PurchaseOrderCreateDTO;
import com.lankun.hspd.server.order.purchase.template.PurchaseTemplate;
import org.springframework.stereotype.Service;

/**
 * 采购订单服务实现
 */
@Service
public class PurchaseOrderServiceImpl extends PurchaseTemplate implements PurchaseOrderService {

    @Override
    public Long createPurchaseOrder(PurchaseOrderCreateDTO orderDTO) {
        return processPurchaseOrder(orderDTO);
    }

    @Override
    public boolean executePurchaseOrder(Long orderId) {
        return executePurchase(orderId);
    }

    @Override
    protected boolean validateData(PurchaseOrderCreateDTO orderDTO) {
        // 基础数据验证
        return orderDTO != null &&
                orderDTO.getSupplierId() != null;
    }

    @Override
    protected com.lankun.hspd.server.order.purchase.strategy.PurchaseStrategy selectStrategy(PurchaseOrderCreateDTO orderDTO) {
        String type = orderDTO.isEmergency() ?
                "emergency" : "regular";
        return strategyMap.get(type + "PurchaseStrategy");
    }

    @Override
    protected PurchaseOrderCreateDTO getOrderInfo(Long orderId) {
        // 从数据库获取订单信息
        return new PurchaseOrderCreateDTO(); // 模拟返回
    }

    @Override
    protected void postProcess(Long orderId, PurchaseOrderCreateDTO orderDTO) {
        // 后续处理逻辑
        System.out.println("采购订单后续处理完成: " + orderId);
    }

    @Override
    protected void postExecuteProcess(Long orderId) {
        // 执行后处理逻辑
        System.out.println("采购执行后续处理完成: " + orderId);
    }
}
