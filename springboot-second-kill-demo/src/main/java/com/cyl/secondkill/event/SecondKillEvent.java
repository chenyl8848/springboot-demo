package com.cyl.secondkill.event;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cyl
 * @date 2023-02-11 21:52
 * @description 秒杀事件
 */
@Data
public class SecondKillEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer skuId;

}
