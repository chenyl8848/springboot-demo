package com.cyl.secondkill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyl.secondkill.entity.ProductOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author cyl
 * @date 2023-02-11 16:00
 * @description 商品订单 mapper
 */
@Mapper
public interface ProductOrderMapper extends BaseMapper<ProductOrder> {
}
