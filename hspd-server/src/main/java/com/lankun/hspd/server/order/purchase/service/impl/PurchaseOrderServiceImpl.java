package com.lankun.hspd.server.order.purchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lankun.hspd.server.inventory.domain.InventoryEntity;
import com.lankun.hspd.server.inventory.service.InventoryService;
import com.lankun.hspd.server.order.application.dao.ApplicationMapper;
import com.lankun.hspd.server.order.purchase.dao.PurchaseOrderItemMapper;
import com.lankun.hspd.server.order.purchase.dao.PurchaseOrderMapper;
import com.lankun.hspd.server.order.purchase.domain.PurchaseOrderEntity;
import com.lankun.hspd.server.order.purchase.domain.PurchaseOrderItemEntity;
import com.lankun.hspd.server.order.purchase.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 采购订单主表 服务实现类
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
@Service
public class PurchaseOrderServiceImpl extends ServiceImpl<PurchaseOrderMapper, PurchaseOrderEntity> implements PurchaseOrderService {
    @Autowired
    private PurchaseOrderMapper orderMapper;
    @Autowired
    private PurchaseOrderItemMapper itemMapper;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private InventoryService inventoryService;

    @Override
    @Transactional
    public Long createOrderFromApplications(Long medicalUnionId, List<Long> appIds) {
        // 1. 创建订单主表
        PurchaseOrderEntity order = new PurchaseOrderEntity();
        order.setOrderNo("PO" + System.currentTimeMillis());
        order.setMedicalUnionId(medicalUnionId);
        order.setApplicantId(apps.get(0).getApplicantId()); // 取第一个申请人
        order.setOrderType("unified"); // 示例
        order.setProcurementMode("unified");
        order.setSupplierId(1L); // 示例
        order.setSupplierName("XX供应商");
        order.setOrderStatus("draft");
        order.setApplyTime(LocalDateTime.now());
        order.setCreatedAt(LocalDateTime.now());
        orderMapper.insert(order);

        // 3. 创建订单明细 + 锁定库存（关键！）
        BigDecimal total = BigDecimal.ZERO;
        for (ApplicationEntity app : apps) {
            // 查找可用库存（简化：取第一个有效批次）
            InventoryEntity inv = findAvailableInventory(app.getItemId(), app.getItemType());
            if (inv == null || !inventoryService.lockStock(inv.getId(), app.getApplicationQuantity())) {
                throw new RuntimeException("库存不足，无法锁定：" + app.getItemName());
            }

            PurchaseOrderItemEntity item = new PurchaseOrderItemEntity();
            item.setOrderId(order.getId());
            item.setItemId(app.getItemId());
            item.setItemType(app.getItemType());
            item.setItemName(app.getItemName());
            item.setSpecification(app.getSpecification());
            item.setUnit("盒"); // 示例
            item.setQuantity(app.getApplicationQuantity());
            item.setUnitPrice(BigDecimal.valueOf(10.0)); // 示例
            item.setTotalPrice(app.getApplicationQuantity().multiply(item.getUnitPrice()));
            item.setCreatedAt(LocalDateTime.now());
            itemMapper.insert(item);

            total = total.add(item.getTotalPrice());
        }
        order.setTotalAmount(total);
        orderMapper.updateById(order);

        return order.getId();
    }

    private InventoryEntity findAvailableInventory(Long itemId, String itemType) {
        // 简化：查询未过期、有库存的记录
        LambdaQueryWrapper<InventoryEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(InventoryEntity::getItemId, itemId)
                .eq(InventoryEntity::getItemType, itemType)
                .gt(InventoryEntity::getExpireDate, java.time.LocalDate.now())
                .gt(InventoryEntity::getStockQty, 0);
        return inventoryService.getOne(qw);
    }

    @Override
    @Transactional
    public void submitOrderForApproval(Long orderId, String bpmProcessInstanceId) {
        PurchaseOrderEntity order = orderMapper.selectById(orderId);
        order.setOrderStatus("pending_approval");
        order.setSubmitTime(LocalDateTime.now());
        order.setBpmProcessInstanceId(bpmProcessInstanceId); // 关键：关联 BPM
        orderMapper.updateById(order);
    }

    @Override
    @Transactional
    public void completeOrderExecution(Long orderId) {
        PurchaseOrderEntity order = orderMapper.selectById(orderId);
        if (!"approved".equals(order.getOrderStatus())) {
            throw new RuntimeException("订单未批准，不能执行");
        }

        // 扣减库存（从锁定转为实际出库）
        LambdaQueryWrapper<PurchaseOrderItemEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(PurchaseOrderItemEntity::getOrderId, orderId);
        List<PurchaseOrderItemEntity> items = itemMapper.selectList(qw);

        for (PurchaseOrderItemEntity item : items) {
            // 查找对应库存记录（需匹配批次等，此处简化）
            InventoryEntity inv = findInventoryByItem(item.getItemId(), item.getItemType());
            if (inv == null || !inventoryService.deductStock(inv.getId(), item.getQuantity())) {
                throw new RuntimeException("库存扣减失败：" + item.getItemName());
            }
        }

        order.setOrderStatus("completed");
        order.setCompletedTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }

    private InventoryEntity findInventoryByItem(Long itemId, String itemType) {
        // 简化实现
        LambdaQueryWrapper<InventoryEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(InventoryEntity::getItemId, itemId)
                .eq(InventoryEntity::getItemType, itemType)
                .gt(InventoryEntity::getLockedQty, 0);
        return inventoryService.getOne(qw);
    }
}
