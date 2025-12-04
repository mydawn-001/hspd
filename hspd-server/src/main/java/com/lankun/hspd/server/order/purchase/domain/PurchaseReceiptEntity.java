package com.lankun.hspd.server.order.purchase.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 采购收货单
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("biz_purchase_receipt")
@Schema(description="采购收货单")
public class PurchaseReceiptEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "收货单号（GR2025110001）")
    private String receiptNo;

    private Long orderId;

    @Schema(name = "收货仓库")
    private Long warehouseId;

    @Schema(name = "收货人")
    private Long receiverId;

    private LocalDateTime receivedAt;

    private String status;

    private String remark;

    private LocalDateTime createdAt;


}
