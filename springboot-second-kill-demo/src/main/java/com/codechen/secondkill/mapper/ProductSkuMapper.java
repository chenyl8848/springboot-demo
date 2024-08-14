package com.codechen.secondkill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codechen.secondkill.entity.ProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author cyl
 * @date 2023-02-11 16:00
 * @description 商品 sku mapper
 */
@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    /**
     * 使用 for update 一定要加上事务，当事务处理完后，for update 才会将行级锁解除
     * for update 进行对查询数据加锁，加的是行锁
     *
     * @param skuId
     * @return
     */
    @Select("select * from product_sku where sku_id = #{skuId} for update")
    ProductSku getByIdForUpdate(Integer skuId);

    @Update(value = "update product_sku set sku_storage = sku_storage - 1 where sku_id = #{skuId} and sku_storage > 0")
    Integer updateByIdWithStorage(Integer skuId);
}
