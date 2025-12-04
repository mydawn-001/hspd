package com.lankun.hspd.server.order.application.template;

import com.lankun.hspd.server.order.application.domain.dto.ApplicationCreateDTO;
import com.lankun.hspd.server.order.application.strategy.ApplicationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 申报模板 - 模板方法模式
 */
@Slf4j
@Component
public abstract class ApplicationTemplate {

    @Autowired
    protected Map<String, ApplicationStrategy> strategyMap;

    /**
     * 申报模板方法
     * @param applicationDTO 申报信息
     * @return 申报ID
     */
    public final Long processApplication(ApplicationCreateDTO applicationDTO) {
        // 1. 数据验证
        if (!validateData(applicationDTO)) {
            throw new IllegalArgumentException("申报数据验证失败");
        }

        // 2. 选择策略
        ApplicationStrategy strategy = selectStrategy(applicationDTO);

        // 3. 策略验证
        if (!strategy.validate(applicationDTO)) {
            throw new IllegalArgumentException("策略验证失败");
        }

        // 4. 执行申报
        Long applicationId = strategy.handleApplication(applicationDTO);

        // 5. 后续处理
        postProcess(applicationId, applicationDTO);

        log.info("申报处理完成，申报ID: {}", applicationId);
        return applicationId;
    }

    /**
     * 数据验证
     * @param applicationDTO 申报信息
     * @return 是否验证通过
     */
    protected abstract boolean validateData(ApplicationCreateDTO applicationDTO);

    /**
     * 选择策略
     * @param applicationDTO 申报信息
     * @return 申报策略
     */
    protected abstract ApplicationStrategy selectStrategy(ApplicationCreateDTO applicationDTO);

    /**
     * 后续处理
     * @param applicationId 申报ID
     * @param applicationDTO 申报信息
     */
    protected abstract void postProcess(Long applicationId, ApplicationCreateDTO applicationDTO);
}
