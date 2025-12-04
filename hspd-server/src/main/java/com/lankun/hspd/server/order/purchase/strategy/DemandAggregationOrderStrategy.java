package com.lankun.hspd.server.order.purchase.strategy;

import com.lankun.hspd.server.application.dao.ApplicationMapper;
import com.lankun.hspd.server.purchase.dao.PurchaseOrderItemMapper;
import com.lankun.hspd.server.purchase.dao.PurchaseOrderMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 需求汇总订单生成服务
 */
@Component
public class DemandAggregationOrderStrategy {

    @Resource
    private ApplicationMapper applicationMapper;
    @Resource
    private PurchaseOrderMapper purchaseOrderMapper;
    @Resource
    private PurchaseOrderItemMapper purchaseOrderItemMapper;

    /**
     * 自动汇总成员单位需求，智能审核生成订单
     */
    @Transactional
    public Long generateAggregatedOrder(DemandAggregationReqVO req) {
        // 1. 收集成员单位的申报需求
        List<ApplicationEntity> applications = collectMemberApplications(req);

        if (CollectionUtils.isEmpty(applications)) {
            throw new ServiceException("没有可汇总的申报需求");
        }

        // 2. 按药品/耗材分组汇总需求
        Map<String, List<ApplicationEntity>> groupedApplications =
                groupApplicationsByItem(applications);

        // 3. 智能审核（检查库存、价格等）
        Map<String, List<ApplicationEntity>> approvedApplications =
                smartReviewApplications(groupedApplications);

        // 4. 生成采购订单
        PurchaseOrderEntity order = createPurchaseOrder(req, approvedApplications);

        // 5. 创建订单明细
        createPurchaseOrderItems(order, approvedApplications);

        // 6. 更新申报状态
        updateApplicationStatus(approvedApplications, order.getId());

        return order.getId();
    }

    private List<ApplicationEntity> collectMemberApplications(DemandAggregationReqVO req) {
        LambdaQueryWrapper<ApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApplicationEntity::getStatus, "approved") // 已批准的申报
                .eq(ApplicationEntity::getMedicalUnionId, req.getMedicalUnionId())
                .ge(ApplicationEntity::getCreatedAt, req.getStartTime())
                .le(ApplicationEntity::getCreatedAt, req.getEndTime());

        if (CollectionUtils.isNotEmpty(req.getMemberIds())) {
            queryWrapper.in(ApplicationEntity::getApplicantOrgId, req.getMemberIds());
        }

        return applicationMapper.selectList(queryWrapper);
    }

    private Map<String, List<ApplicationEntity>> groupApplicationsByItem(
            List<ApplicationEntity> applications) {
        return applications.stream()
                .collect(Collectors.groupingBy(app ->
                        app.getItemId() + "_" + app.getItemType()));
    }

    private Map<String, List<ApplicationEntity>> smartReviewApplications(
            Map<String, List<ApplicationEntity>> groupedApplications) {
        Map<String, List<ApplicationEntity>> approved = new HashMap<>();

        for (Map.Entry<String, List<ApplicationEntity>> entry : groupedApplications.entrySet()) {
            List<ApplicationEntity> apps = entry.getValue();

            // 智能审核逻辑
            if (smartReviewSingleItem(apps)) {
                approved.put(entry.getKey(), apps);
            }
        }

        return approved;
    }

    private boolean smartReviewSingleItem(List<ApplicationEntity> applications) {
        // 实现智能审核逻辑，如：
        // 1. 检查总需求量是否合理
        // 2. 检查价格是否在合理范围内
        // 3. 检查是否有足够库存支持
        // ...
        return true; // 简化示例
    }

    private PurchaseOrderEntity createPurchaseOrder(DemandAggregationReqVO req,
                                                    Map<String, List<ApplicationEntity>> approvedApplications) {
        PurchaseOrderEntity order = new PurchaseOrderEntity();
        order.setOrderNo("PO" + System.currentTimeMillis());
        order.setMedicalUnionId(req.getMedicalUnionId());
        order.setOrderType("aggregated"); // 汇总订单
        order.setProcurementMode("unified"); // 统一采购
        order.setOrderStatus("draft");
        order.setApplyTime(LocalDateTime.now());
        order.setCreatedAt(LocalDateTime.now());

        // 计算总金额
        BigDecimal totalAmount = calculateTotalAmount(approvedApplications);
        order.setTotalAmount(totalAmount);

        purchaseOrderMapper.insert(order);
        return order;
    }

    private void createPurchaseOrderItems(PurchaseOrderEntity order,
                                          Map<String, List<ApplicationEntity>> approvedApplications) {
        for (List<ApplicationEntity> apps : approvedApplications.values()) {
            PurchaseOrderItemEntity item = new PurchaseOrderItemEntity();
            ApplicationEntity firstApp = apps.get(0);

            item.setOrderId(order.getId());
            item.setItemId(firstApp.getItemId());
            item.setItemType(firstApp.getItemType());
            item.setItemName(firstApp.getItemName());
            item.setSpecification(firstApp.getSpecification());
            item.setUnit(firstApp.getUnit());

            // 汇总数量
            BigDecimal totalQuantity = apps.stream()
                    .map(ApplicationEntity::getApplicationQuantity)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            item.setQuantity(totalQuantity);

            // 平均单价（简化处理）
            BigDecimal avgPrice = apps.stream()
                    .map(ApplicationEntity::getEstimatedPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(new BigDecimal(apps.size()), 2, RoundingMode.HALF_UP);
            item.setUnitPrice(avgPrice);

            item.setTotalPrice(totalQuantity.multiply(avgPrice));
            item.setCreatedAt(LocalDateTime.now());

            purchaseOrderItemMapper.insert(item);
        }
    }

    private void updateApplicationStatus(Map<String, List<ApplicationEntity>> approvedApplications,
                                         Long orderId) {
        for (List<ApplicationEntity> apps : approvedApplications.values()) {
            for (ApplicationEntity app : apps) {
                app.setStatus("ordered");
                app.setRelatedOrderId(orderId);
                applicationMapper.updateById(app);
            }
        }
    }

    private BigDecimal calculateTotalAmount(Map<String, List<ApplicationEntity>> approvedApplications) {
        BigDecimal total = BigDecimal.ZERO;
        for (List<ApplicationEntity> apps : approvedApplications.values()) {
            for (ApplicationEntity app : apps) {
                total = total.add(
                        app.getApplicationQuantity().multiply(app.getEstimatedPrice()));
            }
        }
        return total;
    }


}
