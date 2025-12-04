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
 * 采购执行记录
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("biz_procurement_execution")
@Schema(description="采购执行记录")
public class ProcurementExecutionEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 执行类型
     */
    private String executionType;

    /**
     * 执行人（采购员）
     */
    @Schema(name = "执行人（采购员）")
    private Long executorId;

    /**
     * 执行人姓名
     */
    private String executorName;

    /**
     * 执行状态
     */
    private String executionStatus;

    /**
     * 执行进度
     */
    private BigDecimal executionProgress;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 完成时间
     */
    private LocalDateTime completedTime;

    /**
     * 失败原因
     */
    @Schema(name = "失败原因")
    private String failureReason;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;


}
