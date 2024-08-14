package com.codechen.secondkill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codechen.secondkill.mapper.ProductOrderMapper;
import com.codechen.secondkill.service.ProductOrderService;
import com.codechen.secondkill.entity.ProductOrder;
import org.springframework.stereotype.Service;

/**
 * @author cyl
 * @date 2023-02-11 16:01
 * @description 商品订单 接口
 */
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrder> implements ProductOrderService {
}
