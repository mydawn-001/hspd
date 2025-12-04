package com.lankun.hspd.server.inventory.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
@TableName("center_inventory_alert_event")
@Schema(description="")
public class InventoryAlertEventEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long ruleId;

    private Long itemId;

    private Long warehouseId;

    private BigDecimal currentQty;

    private LocalDate expireDate;

    private String alertMsg;

    private Boolean isResolved;

    private Long resolvedBy;

    private LocalDateTime createdAt;


}
