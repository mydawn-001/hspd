// com/lankun/hspd/server/order/application/strategy/SpecialApplicationStrategy.java
package com.lankun.hspd.server.order.application.strategy;

import com.lankun.hspd.server.enums.ProcessStatusEnum;
import com.lankun.hspd.server.inventory.service.InventoryService;
import com.lankun.hspd.server.order.application.domain.ApplicationEntity;
import com.lankun.hspd.server.order.application.domain.dto.ApplicationCreateDTO;
import com.lankun.hspd.server.order.application.enums.ApplicationTypeEnum;
import com.lankun.hspd.server.order.application.service.ApplicationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 特殊申报策略
 */
@Component
public class SpecialApplicationStrategy implements ApplicationStrategy {

    @Resource
    private InventoryService inventoryService;

    @Resource
    private ApplicationService applicationService;

    @Override
    public Long handleApplication(ApplicationCreateDTO applicationDTO) {
        // 实现特殊申报逻辑
        // 1. 创建申报记录
        ApplicationEntity application = new ApplicationEntity();
        BeanUtils.copyProperties(applicationDTO, application);
        application.setStatus(ProcessStatusEnum.PENDING_APPROVAL.getCode());
        applicationService.save(application);

        // 2. 智能预占库存（考虑先进先出、效期管理等）
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
        // 实现特殊申报验证逻辑
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

        // 4. 验证特殊申报信息完整性
        if (applicationDTO.getSpecialReason() == null || applicationDTO.getSpecialReason().isEmpty()) {
            return false;
        }
        return applicationDTO.getSpecial();
    }

    @Override
    public String getType() {
        return ApplicationTypeEnum.SPECIAL.getCode();
    }
}
