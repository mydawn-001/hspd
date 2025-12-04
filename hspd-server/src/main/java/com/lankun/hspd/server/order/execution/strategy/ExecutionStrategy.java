package com.lankun.hspd.server.order.execution.strategy;

import com.lankun.hspd.server.order.execution.dto.ExecutionCreateDTO;

/**
 * 执行策略接口 - 策略模式
 */
public interface ExecutionStrategy {

    /**
     * 执行采购订单
     * @param executionDTO 执行信息
     * @return 执行ID
     */
    Long executeOrder(ExecutionCreateDTO executionDTO);

    /**
     * 完成执行
     * @param executionId 执行ID
     * @return 是否完成
     */
    boolean completeExecution(Long executionId);

    /**
     * 获取执行类型
     * @return 执行类型
     */
    String getType();
}
