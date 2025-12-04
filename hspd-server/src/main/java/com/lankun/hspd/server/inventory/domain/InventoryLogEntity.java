package com.lankun.hspd.server.inventory.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("center_inventory_log")
@Schema(value="InventoryLogEntity对象", description="")
public class InventoryLogEntity implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "关联inventory.id")
    private Long inventoryId;

    private Long itemId;

    private Long warehouseId;

    private String batchNo;

    @Schema(name = "变动数量（正为入，负为出）")
    private BigDecimal changeQty;

    @Schema(name = "变动后余额")
    private BigDecimal balanceQty;

    private String bizType;

    @Schema(name = "关联业务单据ID（如purchase_order.id）")
    private Long bizId;

    @Schema(name = "操作人")
    private Long operatorUserId;

    private String remark;

    private LocalDateTime createdAt;


}
