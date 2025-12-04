package com.lankun.hspd.server.order.application.strategy;

import com.lankun.hspd.server.order.application.domain.dto.ApplicationCreateDTO;

/**
 * 申报策略接口 - 策略模式
 */
public interface ApplicationStrategy {

    /**
     * 处理申报
     * @param applicationDTO 申报信息
     * @return 申报ID
     */
    Long handleApplication(ApplicationCreateDTO applicationDTO);

    /**
     * 验证申报数据
     * @param applicationDTO 申报信息
     * @return 是否验证通过
     */
    boolean validate(ApplicationCreateDTO applicationDTO);

    /**
     * 获取申报类型
     * @return 申报类型
     */
    String getType();
}
