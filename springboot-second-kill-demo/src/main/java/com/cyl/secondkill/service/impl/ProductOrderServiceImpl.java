package com.cyl.secondkill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyl.secondkill.entity.ProductOrder;
import com.cyl.secondkill.mapper.ProductOrderMapper;
import com.cyl.secondkill.service.ProductOrderService;
import org.springframework.stereotype.Service;

/**
 * @author cyl
 * @date 2023-02-11 16:01
 * @description 商品订单 接口
 */
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrder> implements ProductOrderService {
}
