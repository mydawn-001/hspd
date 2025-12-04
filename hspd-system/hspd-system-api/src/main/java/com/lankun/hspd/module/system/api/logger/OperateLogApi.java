package com.lankun.hspd.module.system.api.logger;

import com.lankun.hspd.framework.common.biz.system.logger.OperateLogCommonApi;
import com.lankun.hspd.framework.common.pojo.CommonResult;
import com.lankun.hspd.framework.common.pojo.PageResult;
import com.lankun.hspd.module.system.api.logger.dto.OperateLogPageReqDTO;
import com.lankun.hspd.module.system.api.logger.dto.OperateLogRespDTO;
import com.lankun.hspd.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 操作日志")
public interface OperateLogApi extends OperateLogCommonApi {

    String PREFIX = ApiConstants.PREFIX + "/operate-log";

    @GetMapping(PREFIX + "/page")
    @Operation(summary = "获取指定模块的指定数据的操作日志分页")
    CommonResult<PageResult<OperateLogRespDTO>> getOperateLogPage(@SpringQueryMap OperateLogPageReqDTO pageReqDTO);

}
