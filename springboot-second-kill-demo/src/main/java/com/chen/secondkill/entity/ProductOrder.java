package com.chen.secondkill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.sql.Timestamp;

/**
 * @author cyl
 * @date 2023-02-11 15:57
 * @description 商品订单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "product_order")
public class ProductOrder {

    /** 订单id */
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    /** 用户id */
    private Integer userId;

    /** 商品skuId */
    private Integer skuId;

    /** 订单状态 */
    private Integer orderState;

    /** 创建日期 */
    private Timestamp createTime;

}
