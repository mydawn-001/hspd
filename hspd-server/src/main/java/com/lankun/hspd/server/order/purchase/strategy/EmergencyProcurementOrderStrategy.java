package com.lankun.hspd.server.order.purchase.strategy;
/**
 * 临时采购订单生成服务
 */
@Service
public class EmergencyProcurementOrderStrategy {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private PurchaseOrderItemMapper purchaseOrderItemMapper;
    @Autowired
    private ApplicationMapper applicationMapper;

    /**
     * 根据特殊申报生成临时采购订单
     */
    @Transactional
    public Long generateEmergencyOrder(EmergencyProcurementReqVO req) {
        // 1. 验证特殊申报
        ApplicationEntity application = applicationMapper.selectById(req.getApplicationId());
        if (application == null || !"special_approved".equals(application.getStatus())) {
            throw new ServiceException("无效的特殊申报或未批准");
        }

        // 2. 创建紧急采购订单
        PurchaseOrderEntity order = new PurchaseOrderEntity();
        order.setOrderNo("EMER-" + System.currentTimeMillis());
        order.setMedicalUnionId(application.getMedicalUnionId());
        order.setApplicantOrgId(application.getApplicantOrgId());
        order.setApplicantId(application.getApplicantId());
        order.setOrderType("emergency"); // 紧急采购
        order.setProcurementMode("unified");
        order.setSupplierId(req.getSupplierId());
        order.setSupplierName(req.getSupplierName());
        order.setOrderStatus("emergency_pending"); // 紧急待处理
        order.setApplyTime(LocalDateTime.now());
        order.setEmergencyReason(req.getEmergencyReason());
        order.setEmergencyLevel(req.getEmergencyLevel());
        order.setCreatedAt(LocalDateTime.now());

        // 设置金额
        order.setTotalAmount(
                application.getApplicationQuantity().multiply(application.getEstimatedPrice()));

        purchaseOrderMapper.insert(order);

        // 3. 创建订单明细（基于特殊申报）
        PurchaseOrderItemEntity item = new PurchaseOrderItemEntity();
        item.setOrderId(order.getId());
        item.setItemId(application.getItemId());
        item.setItemType(application.getItemType());
        item.setItemName(application.getItemName());
        item.setSpecification(application.getSpecification());
        item.setUnit(application.getUnit());
        item.setQuantity(application.getApplicationQuantity());
        item.setUnitPrice(application.getEstimatedPrice());
        item.setTotalPrice(order.getTotalAmount());
        item.setCreatedAt(LocalDateTime.now());
        purchaseOrderItemMapper.insert(item);

        // 4. 更新申报状态
        application.setStatus("emergency_ordered");
        application.setRelatedOrderId(order.getId());
        applicationMapper.updateById(application);

        // 5. 触发紧急采购流程
        triggerEmergencyProcurementProcess(order.getId());

        return order.getId();
    }

    private void triggerEmergencyProcurementProcess(Long orderId) {
        // 触发紧急采购流程，如：
        // 1. 发送紧急通知给相关人员
        // 2. 启动快速审批流程
        // 3. 优先安排供应商
        // ...
    }
}
