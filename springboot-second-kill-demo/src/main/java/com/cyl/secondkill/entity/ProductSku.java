package com.cyl.secondkill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cyl
 * @date 2023-02-11 15:55
 * @description 商品 sku
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "product_sku")
public class ProductSku {

    /** 商品id */
    @TableId
    private Integer skuId;

    /** 商品名称 */
    private String skuName;

    /** 商品库存 */
    private Integer skuStorage;
}
