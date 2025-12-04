package com.lankun.hspd.server.order.application.strategy;

import com.alibaba.fastjson.JSON;
import com.lankun.hspd.framework.common.pojo.CommonResult;
import com.lankun.hspd.module.bpm.api.task.BpmProcessInstanceApi;
import com.lankun.hspd.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import com.lankun.hspd.server.enums.ProcessStatusEnum;
import com.lankun.hspd.server.inventory.service.InventoryService;
import com.lankun.hspd.server.order.application.domain.ApplicationEntity;
import com.lankun.hspd.server.order.application.domain.dto.ApplicationCreateDTO;
import com.lankun.hspd.server.order.application.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 普通申报策略实现
 * 处理常规药品/耗材申报业务逻辑
 */
@Component
@Slf4j
public class NormalApplicationStrategy implements ApplicationStrategy {

    @Resource
    private InventoryService inventoryService;

    @Resource
    private ApplicationService applicationService;

    @Resource
    private BpmProcessInstanceApi bpmProcessInstanceApi; // 引入BPM服务

    @Override
    public Long createHandleApplication(ApplicationCreateDTO applicationDTO) {
        // 实现普通申报逻辑
        // 1. 创建申报记录
        ApplicationEntity application = new ApplicationEntity();
        BeanUtils.copyProperties(applicationDTO, application);
        application.setStatus(ProcessStatusEnum.PENDING_APPROVAL.getCode());
        applicationService.save(application);

        // 3. 智能预占库存（考虑先进先出、效期管理等）
        boolean reserved = inventoryService.reserveStock(
                application.getItemId(),
                application.getType(),
                application.getApplicationQuantity()
        );

        if (!reserved) {
            throw new RuntimeException("库存不足，无法预占");
        }

        // 3. 记录预占日志
        //inventoryService.logReservation(application.getId(), application.getApplicationQuantity());

        return application.getId();
    }

    /**
     * 取消申报并释放预占库存
     */
    @Transactional
    public void cancelApplication(Long appId) {
        ApplicationEntity application = applicationService.getById(appId);
        if (application == null) {
            throw new RuntimeException("申报记录不存在");
        }

        // 释放预占库存
        inventoryService.releaseReservedStock(appId);

        // 更新状态
        application.setStatus("cancelled");
        applicationService.updateById(application);
    }

    @Override
    public boolean validate(ApplicationCreateDTO applicationDTO) {
        // 实现普通申报验证逻辑
        // 1. 验证申报数据不为空
        if (applicationDTO == null) {
            return false;
        }
        
        // 2. 验证申报项列表不为空
        if (applicationDTO.getItemId() == null) {
            return false;
        }
        
        // 3. 验证必要字段(医共体成员ID、申请人ID等)
        if (applicationDTO.getMedicalUnionId() == null || applicationDTO.getApplicantId() == null) {
            return false;
        }
        return true;
    }

    @Override
    public String getType() {
        // 返回普通申报类型标识
        return "normal";
    }
}
