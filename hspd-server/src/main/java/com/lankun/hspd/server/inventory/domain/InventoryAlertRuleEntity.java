package com.lankun.hspd.server.inventory.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

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
@TableName("center_inventory_alert_rule")
@Schema(description="")
public class InventoryAlertRuleEntity implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "NULL=全局规则")
    private Long itemId;

    @Schema(name = "NULL=所有仓库")
    private Long warehouseId;

    @Schema(name = "最低库存")
    private BigDecimal minStock;

    @Schema(name = "最高库存")
    private BigDecimal maxStock;

    private String alertType;

    @Schema(name = "临期预警天数（仅EXPIRE_SOON有效）")
    private Integer expireDays;

    private Boolean isActive;


}
