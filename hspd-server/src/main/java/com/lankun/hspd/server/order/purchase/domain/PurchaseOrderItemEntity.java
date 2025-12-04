package com.lankun.hspd.server.order.purchase.domain;

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
 * 采购订单明细表
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("biz_purchase_order_item")
@Schema(description="采购订单明细表")
public class PurchaseOrderItemEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    @Schema(name = "物品ID（指向item主表）")
    private Long itemId;

    private String itemType;

    private String itemName;

    private String specification;

    private String unit;

    @Schema(name = "订购数量（基本单位）")
    private BigDecimal quantity;

    @Schema(name = "已收货数量")
    private BigDecimal receivedQuantity;

    @Schema(name = "单价")
    private BigDecimal unitPrice;

    @Schema(name = "总价 = quantity * unit_price")
    private BigDecimal totalPrice;

    @Schema(name = "期望到货日期")
    private LocalDate expectedDeliveryDate;

    @Schema(name = "送货地址")
    private String deliveryAddress;

    private LocalDateTime createdAt;


}
