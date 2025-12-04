package com.lankun.hspd.server.order.purchase.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 采购收货明细
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("biz_purchase_receipt_item")
@Schema(description="采购收货明细")
public class PurchaseReceiptItemEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long receiptId;

    private Long orderItemId;

    private String batchNo;

    private LocalDate productionDate;

    private LocalDate expireDate;

    private BigDecimal receivedQty;

    @Schema(name = "拒收数量")
    private BigDecimal rejectedQty;

    @Schema(name = "拒收原因")
    private String rejectReason;


}
