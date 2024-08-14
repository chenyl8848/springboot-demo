package com.codechen.secondkill.service.impl;

import com.codechen.secondkill.annotation.SecondKillLock;
import com.codechen.secondkill.service.ProductOrderService;
import com.codechen.secondkill.service.ProductSkuService;
import com.codechen.secondkill.service.SecondKillService;
import com.codechen.secondkill.common.Result;
import com.codechen.secondkill.entity.ProductOrder;
import com.codechen.secondkill.entity.ProductSku;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cyl
 * @date 2023-02-11 16:09
 * @description 秒杀接口实现类
 */
@Service
@Slf4j
public class SecondKillServiceImpl implements SecondKillService {

    private static Lock lock = new ReentrantLock();

    @Autowired
    private ProductSkuService productSkuService;

    @Autowired
    private ProductOrderService productOrderService;

    /**
     * 会出现超卖的情况
     * 这里在业务方法开始加了锁，在业务方法结束后释放了锁。
     * 但这里的事务提交却不是这样的，有可能在事务提交之前，就已经把锁释放了，这样会导致商品超卖现象。（事务提交是在整个方法执行完）
     * 所以加锁的时机很重要！
     * - 可以在controller层进行加锁
     * - 可以使用Aop在业务方法执行之前进行加锁
     *
     * @param userId
     * @param skuId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result startKillByLock(Integer userId, Integer skuId) {
        // 加锁
        lock.lock();
        try {
            // 1.校验库存
            ProductSku productSku = productSkuService.getById(skuId);
            if (productSku == null) {
                return Result.fail("商品不存在");
            }
            Integer storage = productSku.getSkuStorage();
            if (storage > 0) {
                // 2.扣库存
                productSku.setSkuStorage(storage - 1);
                productSkuService.updateById(productSku);

                // 3.创建订单
                ProductOrder productOrder = new ProductOrder();
                productOrder.setUserId(userId);
                productOrder.setSkuId(skuId);
                productOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
                productOrderService.save(productOrder);

            } else {
                return Result.fail("秒杀失败");
            }

        } catch (Exception e) {
            throw e;
        } finally {
            // 解锁
            lock.unlock();
        }
        return Result.success("秒杀成功");
    }

    @Override
    @Transactional
    public Result startKillByLockImprove(Integer userId, Integer skuId) {
        try {
            // 1.校验库存
            ProductSku productSku = productSkuService.getById(skuId);
            if (productSku == null) {
                return Result.fail("商品不存在");
            }
            Integer storage = productSku.getSkuStorage();
            if (storage > 0) {
                // 2.扣库存
                productSku.setSkuStorage(storage - 1);
                productSkuService.updateById(productSku);

                // 3.创建订单
                ProductOrder productOrder = new ProductOrder();
                productOrder.setUserId(userId);
                productOrder.setSkuId(skuId);
                productOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
                productOrderService.save(productOrder);

            } else {
                return Result.fail("秒杀失败");
            }

        } catch (Exception e) {
            throw e;
        } finally {

        }
        return Result.success("秒杀成功");
    }

    @Override
    @SecondKillLock
    @Transactional
    public Result startKillByLockAop(Integer userId, Integer skuId) {
        try {
            // 1.校验库存
            ProductSku productSku = productSkuService.getById(skuId);
            if (productSku == null) {
                return Result.fail("商品不存在");
            }
            Integer storage = productSku.getSkuStorage();
            if (storage > 0) {
                // 2.扣库存
                productSku.setSkuStorage(storage - 1);
                productSkuService.updateById(productSku);

                // 3.创建订单
                ProductOrder productOrder = new ProductOrder();
                productOrder.setUserId(userId);
                productOrder.setSkuId(skuId);
                productOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
                productOrderService.save(productOrder);

            } else {
                return Result.fail("秒杀失败");
            }

        } catch (Exception e) {
            throw e;
        } finally {

        }
        return Result.success("秒杀成功");
    }

    /**
     * 悲观锁，什么是悲观锁呢？通俗的说，在做任何事情之前，都要进行加锁确认。这种数据库级加锁操作效率较低。
     * 如果请求数和秒杀商品数量一致，会出现少卖
     *
     * @param userId
     * @param skuId
     * @return
     */
    @Override
    @Transactional
    public Result startKillByForUpdate(Integer userId, Integer skuId) {
        try {
            // 1.校验库存
            ProductSku productSku = productSkuService.getByIdForUpdate(skuId);
            if (productSku == null) {
                return Result.fail("商品不存在");
            }
            Integer storage = productSku.getSkuStorage();
            if (storage > 0) {
                // 2.扣库存
                productSku.setSkuStorage(storage - 1);
                productSkuService.updateById(productSku);

                // 3.创建订单
                ProductOrder productOrder = new ProductOrder();
                productOrder.setUserId(userId);
                productOrder.setSkuId(skuId);
                productOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
                productOrderService.save(productOrder);

            } else {
                return Result.fail("秒杀失败");
            }

        } catch (Exception e) {
            throw e;
        } finally {

        }
        return Result.success("秒杀成功");
    }

    /**
     * 利用 update 更新命令来加表锁
     *
     * @param userId
     * @param skuId
     * @return
     */
    @Override
    @Transactional
    public Result startKillByUpdate(Integer userId, Integer skuId) {
        try {
            // 1.不校验直接扣减库存
            Integer storage = productSkuService.updateByIdWithStorage(skuId);
            if (storage > 0) {

                // 2.创建订单
                ProductOrder productOrder = new ProductOrder();
                productOrder.setUserId(userId);
                productOrder.setSkuId(skuId);
                productOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
                productOrderService.save(productOrder);

            } else {
                return Result.fail("秒杀失败");
            }

        } catch (Exception e) {
            throw e;
        } finally {

        }
        return Result.success("秒杀成功");
    }
}
