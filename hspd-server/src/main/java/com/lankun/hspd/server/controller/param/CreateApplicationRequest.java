package com.lankun.hspd.server.controller.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "新增申报实体")
@Data
public class CreateApplicationRequest {

    @Schema(description = "申报id", requiredMode = Schema.RequiredMode.AUTO, example = "1")
    private Long id;

    @Schema(description = "医疗机构id", requiredMode = Schema.RequiredMode.REQUIRED, example = "研发部")
    private Long medicalUnionId;

    @Schema(description = "医疗机构名称（冗余）", requiredMode = Schema.RequiredMode.REQUIRED, example = "研发部")
    private String medicalUnionName;

    @Schema(description = "申请科室id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long departmentId;

    @Schema(description = "申请科室名称（冗余）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String departmentName;

    @Schema(description = "负责人的用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long leaderUserId;

    @Schema(description = "部门状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status; // 参见 CommonStatusEnum 枚举
}
