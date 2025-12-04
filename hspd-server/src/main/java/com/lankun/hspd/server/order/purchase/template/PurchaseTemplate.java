package com.lankun.hspd.server.order.purchase.template;

import com.lankun.hspd.server.order.purchase.dto.PurchaseOrderCreateDTO;
import com.lankun.hspd.server.order.purchase.strategy.PurchaseStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 采购模板 - 模板方法模式
 */
@Slf4j
@Component
public abstract class PurchaseTemplate {

    @Autowired
    protected Map<String, PurchaseStrategy> strategyMap;

    /**
     * 采购订单模板方法
     * @param orderDTO 订单信息
     * @return 订单ID
     */
    public final Long processPurchaseOrder(PurchaseOrderCreateDTO orderDTO) {
        // 1. 数据验证
        if (!validateData(orderDTO)) {
            throw new IllegalArgumentException("采购订单数据验证失败");
        }

        // 2. 选择策略
        PurchaseStrategy strategy = selectStrategy(orderDTO);

        // 3. 创建订单
        Long orderId = strategy.createPurchaseOrder(orderDTO);

        // 4. 后续处理
        postProcess(orderId, orderDTO);

        log.info("采购订单处理完成，订单ID: {}", orderId);
        return orderId;
    }

    /**
     * 执行采购模板方法
     * @param orderId 订单ID
     * @return 是否执行成功
     */
    public final boolean executePurchase(Long orderId) {
        // 1. 获取订单信息
        PurchaseOrderCreateDTO orderDTO = getOrderInfo(orderId);

        // 2. 选择策略
        PurchaseStrategy strategy = selectStrategy(orderDTO);

        // 3. 执行采购
        boolean result = strategy.executePurchase(orderId);

        // 4. 后续处理
        if (result) {
            postExecuteProcess(orderId);
        }

        log.info("采购执行完成，订单ID: {}, 结果: {}", orderId, result);
        return result;
    }

    /**
     * 数据验证
     * @param orderDTO 订单信息
     * @return 是否验证通过
     */
    protected abstract boolean validateData(PurchaseOrderCreateDTO orderDTO);

    /**
     * 选择策略
     * @param orderDTO 订单信息
     * @return 采购策略
     */
    protected abstract PurchaseStrategy selectStrategy(PurchaseOrderCreateDTO orderDTO);

    /**
     * 获取订单信息
     * @param orderId 订单ID
     * @return 订单信息
     */
    protected abstract PurchaseOrderCreateDTO getOrderInfo(Long orderId);

    /**
     * 后续处理
     * @param orderId 订单ID
     * @param orderDTO 订单信息
     */
    protected abstract void postProcess(Long orderId, PurchaseOrderCreateDTO orderDTO);

    /**
     * 执行后处理
     * @param orderId 订单ID
     */
    protected abstract void postExecuteProcess(Long orderId);
}
