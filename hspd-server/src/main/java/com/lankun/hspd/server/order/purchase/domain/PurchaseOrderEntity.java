package com.lankun.hspd.server.order.purchase.domain;

import java.math.BigDecimal;
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
 * 采购订单主表
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("biz_purchase_order")
@Schema(description="采购订单主表")
public class PurchaseOrderEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "订单编号（PO2025110001）")
    private String orderNo;

    @Schema(name = "医共体成员ID（发起单位）")
    private Long medicalUnionId;

    @Schema(name = "申请人ID")
    private Long applicantId;

    @Schema(name = "订单类型(来源)")
    private String orderType;

    @Schema(name = "实际走的类型，实际采购模式（可能与order_type不同）")
    private String procurementMode;

    @Schema(name = "是否紧急订单")
    private Boolean isUrgent;

    @Schema(name = "供应商ID")
    private Long supplierId;

    @Schema(name = "供应商名称（冗余防变更）")
    private String supplierName;

    @Schema(name = "订单总金额")
    private BigDecimal totalAmount;

    @Schema(name = "币种")
    private String currency;

    private String orderStatus;

    @Schema(name = "申请时间")
    private LocalDateTime applyTime;

    @Schema(name = "提交时间")
    private LocalDateTime submitTime;

    @Schema(name = "最终审批通过时间")
    private LocalDateTime approvalTime;

    @Schema(name = "完成时间（全部收货+结算）")
    private LocalDateTime completedTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Schema(name = "来源类型：application/batch_import/manual")
    private String sourceType;

    @Schema(name = "来源ID（如申报批次ID）")
    private Long sourceId;

    @Schema(name = "BPM流程实例ID")
    private String bpmProcessInstanceId;


}
