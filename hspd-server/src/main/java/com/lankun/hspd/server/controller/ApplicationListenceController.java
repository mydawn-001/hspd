package com.lankun.hspd.server.controller;

import com.lankun.hspd.server.order.application.domain.dto.ApplicationCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 申报监听管理控制器
 */
@Tag(name = "订单管理 - 申报监听管理控制器")
@RestController
@RequestMapping("/order/listence/application")
public class ApplicationListenceController {


    @PostMapping("/create")
    @Operation(summary = "创建申报")
    public CommonResult<Long> createApplication(@Valid @RequestBody ApplicationCreateDTO applicationDTO) {
        Long applicationId = applicationService.createApplication(applicationDTO);
        return success(applicationId);
    }

    @PostMapping("/{applicationId}/submit")
    @Operation(summary = "提交申报审核")
    public CommonResult<Boolean> submitApplication(@PathVariable Long applicationId) {
        // 实现提交审核逻辑
        return success(true);
    }}
