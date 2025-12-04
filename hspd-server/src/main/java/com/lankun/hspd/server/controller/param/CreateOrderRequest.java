package com.lankun.hspd.server.controller.param;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    private Long medicalUnionId;
    private List<Long> appIds;
}
