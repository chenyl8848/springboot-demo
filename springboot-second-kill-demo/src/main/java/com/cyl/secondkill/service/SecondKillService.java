package com.cyl.secondkill.service;

import com.cyl.secondkill.common.Result;

/**
 * @author cyl
 * @date 2023-02-11 16:01
 * @description 秒杀接口
 */
public interface SecondKillService {

    public Result startKillByLock(Integer userId, Integer skuId);

    public Result startKillByLockImprove(Integer userId, Integer skuId);

    public Result startKillByLockAop(Integer userId, Integer skuId);

    public Result startKillByForUpdate(Integer userId, Integer skuId);

    public Result startKillByUpdate(Integer userId, Integer skuId);

}
