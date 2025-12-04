// com/lankun/hspd/server/order/application/enums/ApplicationTypeEnum.java
package com.lankun.hspd.server.order.application.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationTypeEnum {

    DRUG("drug", "药品申报"),
    CONSUMABLE("consumable", "耗材申报"),
    SPECIAL("special", "特殊申报");

    private final String code;
    private final String description;
}
