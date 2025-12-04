package com.lankun.hspd.server.order.purchase.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lankun.hspd.server.purchase.domain.PurchaseOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseOrderMapper extends BaseMapper<PurchaseOrder> {}