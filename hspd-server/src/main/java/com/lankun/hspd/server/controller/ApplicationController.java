package com.lankun.hspd.server.controller;

import com.lankun.hspd.framework.common.pojo.CommonResult;
import com.lankun.hspd.server.order.application.domain.dto.ApplicationCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 申报管理控制器
 */
@Tag(name = "订单管理 - 申报管理")
@RestController
@RequestMapping("/order/application")
public class ApplicationController {


    @PostMapping("/create")
    @Operation(summary = "创建申报")
    public CommonResult<Long> createApplication(@Valid @RequestBody ApplicationCreateDTO applicationDTO) {
        Long applicationId = applicationService.createApplication(applicationDTO);
        return CommonResult.success(applicationId);
    }

}
