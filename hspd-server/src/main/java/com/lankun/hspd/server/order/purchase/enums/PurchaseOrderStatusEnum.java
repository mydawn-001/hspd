// com/lankun/hspd/server/order/purchase/enums/PurchaseOrderStatusEnum.java
package com.lankun.hspd.server.order.purchase.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PurchaseOrderStatusEnum {

    DRAFT("draft", "草稿"),
    PENDING_APPROVAL("pending_approval", "待审批"),
    APPROVED("approved", "已批准"),
    REJECTED("rejected", "已拒绝"),
    IN_EXECUTION("in_execution", "执行中"),
    COMPLETED("completed", "已完成"),
    CANCELLED("cancelled", "已取消");

    private final String code;
    private final String description;
}
