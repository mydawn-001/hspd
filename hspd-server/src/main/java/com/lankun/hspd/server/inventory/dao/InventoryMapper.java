package com.lankun.hspd.server.inventory.dao;

import com.lankun.hspd.server.inventory.domain.InventoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
public interface InventoryMapper extends BaseMapper<InventoryEntity> {
    /**
     * 原子锁定库存（防超卖）
     * @return 影响行数 > 0 表示成功
     */
    @Update("UPDATE inventory " +
            "SET locked_qty = locked_qty + #{lockQty} " +
            "WHERE id = #{inventoryId} " +
            "AND (stock_qty - locked_qty) >= #{lockQty}")
    int lockStock(@Param("inventoryId") Long inventoryId, @Param("lockQty") java.math.BigDecimal lockQty);

    /**
     * 扣减库存（出库时调用）
     */
    @Update("UPDATE inventory " +
            "SET stock_qty = stock_qty - #{qty}, " +
            "    locked_qty = locked_qty - #{qty} " +
            "WHERE id = #{inventoryId} " +
            "AND locked_qty >= #{qty}")
    int deductStock(@Param("inventoryId") Long inventoryId, @Param("qty") java.math.BigDecimal qty);

    /**
     * 释放锁定（取消订单时）
     */
    @Update("UPDATE inventory " +
            "SET locked_qty = locked_qty - #{qty} " +
            "WHERE id = #{inventoryId} " +
            "AND locked_qty >= #{qty}")
    int releaseLock(@Param("inventoryId") Long inventoryId, @Param("qty") java.math.BigDecimal qty);

}
