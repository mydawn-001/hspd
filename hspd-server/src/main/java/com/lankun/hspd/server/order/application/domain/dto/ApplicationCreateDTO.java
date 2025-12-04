// com/lankun/hspd/server/order/application/dto/ApplicationCreateDTO.java
package com.lankun.hspd.server.order.application.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "申报创建 DTO")
public class ApplicationCreateDTO {

    @Schema(description = "医共体成员ID", required = true)
    @NotNull(message = "医共体成员ID不能为空")
    private Long medicalUnionId;

    @Schema(description = "科室ID")
    private Long deptId;

    @Schema(description = "申请人ID", required = true)
    @NotNull(message = "申请人ID不能为空")
    private Long applicantId;

    @Schema(description = "申报时间")
    private LocalDateTime applicationTime;

    @Schema(description = "申报原因")
    private String applicationReason;

    @Schema(description = "申报类型：drug/consumable/special", required = true)
    @NotBlank(message = "申报类型不能为空")
    private String type;

    @Schema(description = "药械ID", required = true)
    @NotNull(message = "药械ID不能为空")
    private Long itemId;

    @Schema(description = "药械名称")
    private String itemName;

    @Schema(description = "紧急程度")
    private String urgencyLevel;

    @Schema(description = "规格")
    private String specification;

    @Schema(description = "申报数量", required = true)
    @NotNull(message = "申报数量不能为空")
    private BigDecimal applicationQuantity;

    @Schema(description = "是否特殊申报")
    private Boolean special = false;

    @Schema(description = "特殊申报理由")
    private String specialReason;
}
