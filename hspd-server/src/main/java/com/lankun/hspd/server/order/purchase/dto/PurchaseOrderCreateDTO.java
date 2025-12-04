// com/lankun/hspd/server/order/purchase/dto/PurchaseOrderCreateDTO.java
package com.lankun.hspd.server.order.purchase.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "采购订单创建 DTO")
public class PurchaseOrderCreateDTO {

    @Schema(description = "医共体成员ID", required = true)
    @NotNull(message = "医共体成员ID不能为空")
    private Long medicalUnionId;

    @Schema(description = "申请人ID", required = true)
    @NotNull(message = "申请人ID不能为空")
    private Long applicantId;

    @Schema(description = "订单类型", required = true)
    @NotBlank(message = "订单类型不能为空")
    private String orderType;

    @Schema(description = "采购模式", required = true)
    @NotBlank(message = "采购模式不能为空")
    private String procurementMode;

    @Schema(description = "是否紧急订单")
    private Boolean isUrgent = false;

    @Schema(description = "供应商ID", required = true)
    @NotNull(message = "供应商ID不能为空")
    private Long supplierId;

    @Schema(description = "供应商名称", required = true)
    @NotBlank(message = "供应商名称不能为空")
    private String supplierName;

    @Schema(description = "币种")
    private String currency = "CNY";

    @Schema(description = "申报ID列表")
    private List<Long> applicationIds;

    @Schema(description = "订单项列表")
    private List<OrderItemDTO> items;

    @Data
    @Schema(description = "订单项 DTO")
    public static class OrderItemDTO {
        @Schema(description = "药械ID", required = true)
        @NotNull(message = "药械ID不能为空")
        private Long itemId;

        @Schema(description = "药械类型", required = true)
        @NotBlank(message = "药械类型不能为空")
        private String itemType;

        @Schema(description = "药械名称", required = true)
        @NotBlank(message = "药械名称不能为空")
        private String itemName;

        @Schema(description = "规格")
        private String specification;

        @Schema(description = "单位")
        private String unit;

        @Schema(description = "数量", required = true)
        @NotNull(message = "数量不能为空")
        private BigDecimal quantity;

        @Schema(description = "单价")
        private BigDecimal unitPrice;

        @Schema(description = "总价")
        private BigDecimal totalPrice;
    }
}
