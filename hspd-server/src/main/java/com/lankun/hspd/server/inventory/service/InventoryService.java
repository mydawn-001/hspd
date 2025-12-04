package com.lankun.hspd.server.inventory.service;

import com.lankun.hspd.server.inventory.domain.InventoryEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
public interface InventoryService extends IService<InventoryEntity> {

    @Transactional
    boolean reserveStock(Long itemId, String itemType, BigDecimal quantity);

    @Transactional
    InventoryEntity convertReservationToLock(Long applicationId, BigDecimal quantity);

    @Transactional
    void releaseReservedStock(Long applicationId);

    @Transactional
    boolean deductStock(Long itemId, String itemType, BigDecimal quantity);

    @Transactional
    void releaseLockedStock(Long itemId, String itemType, BigDecimal quantity);
}
