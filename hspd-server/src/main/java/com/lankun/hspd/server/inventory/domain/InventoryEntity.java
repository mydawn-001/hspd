package com.lankun.hspd.server.inventory.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
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
@TableName("center_inventory")
@Schema(description="")
public class InventoryEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "物品唯一编码（药品/耗材/器械通用ID）")
    private String itemId;

    @Schema(name = "类型")
    private String itemType;

    @Schema(name = "仓库ID")
    private Long warehouseId;

    @Schema(name = "批号")
    private String batchNo;

    @Schema(name = "单位（片、支、台等）")
    private String unit;

    @Schema(name = "生产日期")
    private LocalDate productionDate;

    @Schema(name = "有效期至")
    private LocalDate expireDate;

    @Schema(name = "当前库存量")
    private BigDecimal stockQty;

    @Schema(name = "锁定数量（如已分配未出库）")
    private BigDecimal lockedQty;

    @Schema(name = "预留数量")
    private BigDecimal reservedQty;

    @Schema(name = "最后入库时间")
    private LocalDateTime lastInDate;

    @Schema(name = "最后出库时间")
    private LocalDateTime lastOutDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
