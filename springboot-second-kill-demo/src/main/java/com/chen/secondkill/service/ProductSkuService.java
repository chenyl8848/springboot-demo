package com.chen.secondkill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.secondkill.entity.ProductSku;

/**
 * @author cyl
 * @date 2023-02-11 16:01
 * @description 商品sku 接口
 */
public interface ProductSkuService extends IService<ProductSku> {

    ProductSku getByIdForUpdate(Integer skuId);

    Integer updateByIdWithStorage(Integer skuId);
}
