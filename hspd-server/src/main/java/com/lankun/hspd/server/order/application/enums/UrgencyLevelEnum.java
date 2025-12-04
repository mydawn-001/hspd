// com/lankun/hspd/server/order/application/enums/UrgencyLevelEnum.java
package com.lankun.hspd.server.order.application.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UrgencyLevelEnum {

    LOW("low", "低"),
    MEDIUM("medium", "中"),
    HIGH("high", "高"),
    URGENT("urgent", "紧急");

    private final String code;
    private final String description;
}
