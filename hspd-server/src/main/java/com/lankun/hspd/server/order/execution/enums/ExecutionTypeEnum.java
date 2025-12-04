// com/lankun/hspd/server/order/execution/enums/ExecutionTypeEnum.java
package com.lankun.hspd.server.order.execution.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExecutionTypeEnum {

    REGULAR("regular", "常规执行"),
    EMERGENCY("emergency", "紧急执行");

    private final String code;
    private final String description;
}
