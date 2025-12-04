package com.lankun.hspd.server.order.purchase.strategy;
/**
 * 自行采购订单上传服务
 */
@Service
public class SelfProcurementOrderStrategy {
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private PurchaseOrderItemMapper purchaseOrderItemMapper;

    /**
     * 成员单位上传自行采购订单
     */
    @Transactional
    public Long uploadSelfProcurementOrder(SelfProcurementOrderUploadReqVO req) {
        // 1. 解析上传文件（Excel/OCR识别）
        List<SelfProcurementOrderItemDTO> items = parseUploadFile(req.getFileData());

        // 2. 验证数据完整性
        validateOrderItems(items);

        // 3. 创建采购订单主表
        PurchaseOrderEntity order = new PurchaseOrderEntity();
        order.setOrderNo(req.getOrderNo());
        order.setMedicalUnionId(req.getMedicalUnionId());
        order.setApplicantOrgId(req.getApplicantOrgId());
        order.setApplicantId(req.getApplicantId());
        order.setOrderType("self"); // 自行采购
        order.setProcurementMode("self");
        order.setSupplierId(req.getSupplierId());
        order.setSupplierName(req.getSupplierName());
        order.setOrderStatus("uploaded");
        order.setApplyTime(LocalDateTime.now());
        order.setCreatedAt(LocalDateTime.now());

        // 计算总金额
        BigDecimal totalAmount = items.stream()
                .map(item -> item.getQuantity().multiply(item.getUnitPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);

        purchaseOrderMapper.insert(order);

        // 4. 创建订单明细
        for (SelfProcurementOrderItemDTO itemDto : items) {
            PurchaseOrderItemEntity item = new PurchaseOrderItemEntity();
            BeanUtils.copyProperties(itemDto, item);
            item.setOrderId(order.getId());
            item.setTotalPrice(item.getQuantity().multiply(item.getUnitPrice()));
            item.setCreatedAt(LocalDateTime.now());
            purchaseOrderItemMapper.insert(item);
        }

        // 5. 记录上传日志
        logOrderUpload(order.getId(), req.getApplicantId(), req.getFileData());

        return order.getId();
    }

    private List<SelfProcurementOrderItemDTO> parseUploadFile(byte[] fileData) {
        // 实现文件解析逻辑（Excel/OCR）
        // ...
        return new ArrayList<>();
    }

    private void validateOrderItems(List<SelfProcurementOrderItemDTO> items) {
        // 实现数据验证逻辑
        // 1. 检查必填字段
        // 2. 验证数量和价格格式
        // 3. 验证药品/耗材编码有效性
        // ...
    }

    private void logOrderUpload(Long orderId, Long applicantId, byte[] fileData) {
        // 记录上传日志
        // ...
    }
}
