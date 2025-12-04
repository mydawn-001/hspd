package com.lankun.hspd.server.order.application.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lankun.hspd.module.bpm.api.task.BpmProcessInstanceApi;
import com.lankun.hspd.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import com.lankun.hspd.server.controller.param.CreateApplicationRequest;
import com.lankun.hspd.server.order.application.dao.ApplicationMapper;
import com.lankun.hspd.server.order.application.domain.ApplicationEntity;
import com.lankun.hspd.server.order.application.domain.dto.ApplicationCreateDTO;
import com.lankun.hspd.server.order.application.service.ApplicationService;
import com.lankun.hspd.server.order.application.strategy.ApplicationStrategy;
import com.lankun.hspd.server.order.application.template.ApplicationTemplate;
import com.lankun.hspd.server.purchase.service.PurchaseOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 统一申报主表 服务实现类
 * </p>
 *
 * @author xiaopeng
 * @since 2025-11-30
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, ApplicationEntity> implements ApplicationService {


}
