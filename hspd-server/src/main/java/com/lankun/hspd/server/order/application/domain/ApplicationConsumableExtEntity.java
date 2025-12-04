package com.lankun.hspd.server.order.application.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 耗材申报扩展表
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("biz_application_consumable_ext")
@Schema(description="耗材申报扩展表")
public class ApplicationConsumableExtEntity implements Serializable {

    private static final long serialVersionUID=1L;

    private Long applicationId;

    @Schema(name = "耗材类型：ordinary/high_value")
    private String consumableType;


}
