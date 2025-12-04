// com/lankun/hspd/server/order/execution/dto/ExecutionCreateDTO.java
package com.lankun.hspd.server.order.execution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Schema(description = "执行创建 DTO")
public class ExecutionCreateDTO {

    @Schema(description = "采购订单ID", required = true)
    @NotNull(message = "采购订单ID不能为空")
    private Long purchaseOrderId;

    @Schema(description = "执行人ID", required = true)
    @NotNull(message = "执行人ID不能为空")
    private Long executorId;

    @Schema(description = "执行时间")
    private LocalDateTime executionTime;

    @Schema(description = "执行类型")
    private String executionType;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "是否紧急执行")
    private Boolean isEmergency = false;
}
