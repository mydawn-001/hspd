package com.lankun.hspd.server.controller;

import com.lankun.hspd.server.purchase.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 采购订单控制器
 */
@Tag(name = "订单管理 - 采购管理")
@RestController
@RequestMapping("/order/purchase")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建采购订单")
    public CommonResult<Long> createPurchaseOrder(@Valid @RequestBody PurchaseOrderCreateDTO orderDTO) {
        Long orderId = purchaseOrderService.createPurchaseOrder(orderDTO);
        return success(orderId);
    }

    @PostMapping("/{orderId}/execute")
    @Operation(summary = "执行采购订单")
    public CommonResult<Boolean> executePurchaseOrder(@PathVariable Long orderId) {
        boolean result = purchaseOrderService.executePurchaseOrder(orderId);
        return success(result);
    }

    @PostMapping("/{orderId}/approve")
    @Operation(summary = "审批采购订单")
    public CommonResult<Boolean> approvePurchaseOrder(@PathVariable Long orderId) {
        // 实现审批逻辑
        return success(true);
    }

    @PostMapping("/create")
    public Long createOrder(@RequestBody CreateOrderRequest req) {
        return orderService.createOrderFromApplications(req.getMedicalUnionId(), req.getAppIds());
    }

    @PostMapping("/{orderId}/submit")
    public void submitForApproval(@PathVariable Long orderId, @RequestParam String bpmProcessInstanceId) {
        orderService.submitOrderForApproval(orderId, bpmProcessInstanceId);
    }

    @PostMapping("/{orderId}/execute")
    public void executeOrder(@PathVariable Long orderId) {
        orderService.completeOrderExecution(orderId);
    }




}
