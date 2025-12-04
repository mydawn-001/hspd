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
 * 订单审批流程记录
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("biz_order_approval_flow")
@Schema(description="订单审批流程记录")
public class OrderApprovalFlowEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    @Schema(name = "当前审批节点")
    private String nodeKey;

    @Schema(name = "审批人ID")
    private Long approverId;

    @Schema(name = "审批人姓名（冗余）")
    private String approverName;

    @Schema(name = "审批意见")
    private String approvalOpinion;

    private String approvalResult;

    private LocalDateTime approvalTime;

    private String ipAddress;

    private LocalDateTime createdAt;


}
