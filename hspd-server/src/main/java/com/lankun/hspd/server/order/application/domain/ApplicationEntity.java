package com.lankun.hspd.server.order.application.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 统一申报主表
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("biz_application")
@Schema(description="统一申报主表")
public class ApplicationEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "申报编号")
    private String applicationNo;

    @Schema(name = "医共体成员ID")
    private Long medicalUnionId;

    @Schema(name = "科室id")
    private Long deptId;

    @Schema(name = "申请人ID")
    private Long applicantUserId;

    @Schema(name = "申报时间")
    private LocalDateTime applicationTime;

    @Schema(name = "状态")
    private String status;

    @Schema(name = "申报原因")
    private String applicationReason;

    private LocalDateTime createdAt;

    @Schema(name = "申报类型：drug/consumable/special")
    private String type;

    @Schema(name = "药械id")
    private Long itemId;

    @Schema(name = "药械名称（冗余）防止名称更新")
    private String itemName;

    @Schema(name = "紧急程度")
    private String urgencyLevel;

    private String specification;

    private BigDecimal applicationQuantity;

    private String bpmProcessInstanceId;
}
