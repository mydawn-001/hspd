package com.lankun.hspd.module.bpm.dal.mysql.definition;

import com.lankun.hspd.framework.common.pojo.PageResult;
import com.lankun.hspd.framework.mybatis.core.mapper.BaseMapperX;
import com.lankun.hspd.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lankun.hspd.module.bpm.controller.admin.definition.vo.listener.BpmProcessListenerPageReqVO;
import com.lankun.hspd.module.bpm.dal.dataobject.definition.BpmProcessListenerDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * BPM 流程监听器 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface BpmProcessListenerMapper extends BaseMapperX<BpmProcessListenerDO> {

    default PageResult<BpmProcessListenerDO> selectPage(BpmProcessListenerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BpmProcessListenerDO>()
                .likeIfPresent(BpmProcessListenerDO::getName, reqVO.getName())
                .eqIfPresent(BpmProcessListenerDO::getType, reqVO.getType())
                .eqIfPresent(BpmProcessListenerDO::getEvent, reqVO.getEvent())
                .eqIfPresent(BpmProcessListenerDO::getStatus, reqVO.getStatus())
                .orderByDesc(BpmProcessListenerDO::getId));
    }

}