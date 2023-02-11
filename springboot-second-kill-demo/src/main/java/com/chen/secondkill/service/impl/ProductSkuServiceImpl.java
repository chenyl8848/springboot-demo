package com.chen.secondkill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.secondkill.entity.ProductSku;
import com.chen.secondkill.mapper.ProductSkuMapper;
import com.chen.secondkill.service.ProductSkuService;
import org.springframework.stereotype.Service;

/**
 * @author cyl
 * @date 2023-02-11 16:01
 * @description 商品sku 接口实现类
 */
@Service
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements ProductSkuService {
    @Override
    public ProductSku getByIdForUpdate(Integer skuId) {
        return baseMapper.getByIdForUpdate(skuId);
    }

    @Override
    public Integer updateByIdWithStorage(Integer skuId) {
        return baseMapper.updateByIdWithStorage(skuId);
    }
}
