package com.lankun.hspd.server.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lankun.hspd.server.inventory.dao.InventoryMapper;
import com.lankun.hspd.server.inventory.domain.InventoryEntity;
import com.lankun.hspd.server.inventory.service.InventoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, InventoryEntity> implements InventoryService {

    /**
     * 智能预占库存
     * 根据先进先出原则，从可用库存中预占指定数量的商品
     *
     * @param itemId   商品ID
     * @param itemType 商品类型
     * @param quantity 需要预占的数量
     * @return 是否预占成功
     */
    @Transactional
    @Override
    public boolean reserveStock(Long itemId, String itemType, BigDecimal quantity) {
        // 查找符合条件的可用库存（未过期且有库存的记录，按过期时间升序排列）
        List<InventoryEntity> availableInventories = findAvailableInventories(itemId, itemType, quantity);

        // 剩余需要预占的数量
        BigDecimal remainingQuantity = quantity;

        // 遍历可用库存，逐个预占直到满足需求或无库存可预占
        for (InventoryEntity inventory : availableInventories) {
            // 如果已经预占完所需数量，则退出循环
            if (remainingQuantity.compareTo(BigDecimal.ZERO) <= 0) break;

            // 计算当前库存记录中可预占的数量（总库存 - 已锁定库存）
            BigDecimal allocatable = inventory.getStockQty().subtract(inventory.getReservedQty());

            // 确定本次预占的数量（取剩余需预占量和可预占量的较小值）
            BigDecimal toReserve = remainingQuantity.min(allocatable);

            // 更新库存记录的预留数量
            inventory.setReservedQty(inventory.getReservedQty().add(toReserve));
            this.updateById(inventory);

            // 记录预占日志
            //logReservation(itemId, itemType, toReserve, inventory.getId());

            // 更新剩余需要预占的数量
            remainingQuantity = remainingQuantity.subtract(toReserve);
        }

        // 返回是否完全预占了所需的数量
        return remainingQuantity.compareTo(BigDecimal.ZERO) <= 0;
    }


    /**
     * 将预占转换为锁定
     */
    @Transactional
    @Override
    public InventoryEntity convertReservationToLock(Long applicationId, BigDecimal quantity) {
        // 查找预占记录并转换为锁定
        LambdaQueryWrapper<InventoryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InventoryEntity::getId, applicationId)
                .gt(InventoryEntity::getReservedQty, BigDecimal.ZERO);

        InventoryEntity inventory = this.getOne(queryWrapper);
        if (inventory != null && inventory.getReservedQty().compareTo(quantity) >= 0) {
            // 将预占数量转换为锁定数量
            inventory.setReservedQty(inventory.getReservedQty().subtract(quantity));
            inventory.setLockedQty(inventory.getLockedQty().add(quantity));
            this.updateById(inventory);
            return inventory;
        }

        return null;
    }

    /**
     * 释放预占库存
     */
    @Transactional
    @Override
    public void releaseReservedStock(Long applicationId) {
        // 查找并释放预占库存
        LambdaQueryWrapper<InventoryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InventoryEntity::getId, applicationId)
                .gt(InventoryEntity::getReservedQty, BigDecimal.ZERO);

        InventoryEntity inventory = this.getOne(queryWrapper);
        if (inventory != null) {
            // 将预占数量归零，释放预占库存
            inventory.setReservedQty(BigDecimal.ZERO);
            this.updateById(inventory);
        }
    }

    /**
     * 正式扣减库存
     */
    @Transactional
    @Override
    public boolean deductStock(Long itemId, String itemType, BigDecimal quantity) {
        List<InventoryEntity> inventories = findLockedInventories(itemId, itemType, quantity);

        BigDecimal remainingQuantity = quantity;
        for (InventoryEntity inventory : inventories) {
            if (remainingQuantity.compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal lockedQty = inventory.getLockedQty();
            BigDecimal toDeduct = remainingQuantity.min(lockedQty);

            inventory.setLockedQty(inventory.getLockedQty().subtract(toDeduct));
            inventory.setStockQty(inventory.getStockQty().subtract(toDeduct));
            this.updateById(inventory);

            // 记录出库日志
            //logOutbound(itemId, itemType, toDeduct, inventory.getId());

            remainingQuantity = remainingQuantity.subtract(toDeduct);
        }

        return remainingQuantity.compareTo(BigDecimal.ZERO) <= 0;
    }

    /**
     * 释放锁定库存（审批拒绝等情况）
     */
    @Transactional
    @Override
    public void releaseLockedStock(Long itemId, String itemType, BigDecimal quantity) {
        List<InventoryEntity> inventories = findLockedInventories(itemId, itemType, quantity);

        BigDecimal remainingQuantity = quantity;
        for (InventoryEntity inventory : inventories) {
            if (remainingQuantity.compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal lockedQty = inventory.getLockedQty();
            BigDecimal toRelease = remainingQuantity.min(lockedQty);

            inventory.setLockedQty(inventory.getLockedQty().subtract(toRelease));
            inventory.setReservedQty(inventory.getReservedQty().add(toRelease)); // 转回预占状态
            this.updateById(inventory);

            remainingQuantity = remainingQuantity.subtract(toRelease);
        }
    }

    private List<InventoryEntity> findAvailableInventories(Long itemId, String itemType, BigDecimal requiredQty) {
        LambdaQueryWrapper<InventoryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InventoryEntity::getItemId, itemId)
                .eq(InventoryEntity::getItemType, itemType)
                .gt(InventoryEntity::getExpireDate, LocalDate.now())
                .gt(InventoryEntity::getStockQty, new BigDecimal("0"))
                .orderByAsc(InventoryEntity::getExpireDate); // 先进先出原则

        return this.list(queryWrapper);
    }

    private List<InventoryEntity> findLockedInventories(Long itemId, String itemType, BigDecimal requiredQty) {
        // 查找已锁定的库存记录
        // 实现细节略...
        return Collections.emptyList();
    }

}
