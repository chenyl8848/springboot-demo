package com.chen.secondkill.controller;

import com.chen.secondkill.common.Result;
import com.chen.secondkill.entity.ProductOrder;
import com.chen.secondkill.event.SecondKillEvent;
import com.chen.secondkill.queue.SecondKillQueue;
import com.chen.secondkill.service.SecondKillService;
import com.chen.secondkill.util.DisruptorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cyl
 * @date 2023-02-11 11:51
 * @description 秒杀服务接口
 */
@Api(value = "秒杀服务接口", tags = "秒杀服务接口")
@Slf4j
@RestController
@RequestMapping("/second-kill")
public class SecondKillController {

    private static Lock lock = new ReentrantLock(true);

    @Autowired
    private SecondKillService secondKillService;

    @ApiOperation(value = "秒杀实现方式--Lock加锁")
    @PostMapping("/start/lock")
    public Result startLock(Integer skuId) {
        try {
            log.info("开始秒杀...Lock加锁...");
            Integer userId = new Random().nextInt() * (99999 - 10000 + 1) + 10000;
            Result result = secondKillService.startKillByLock(userId, skuId);
            if (result != null) {
                log.info("用户:{}--{}", userId, result.getMessage());
            } else {
                log.info("用户:{}--{}", userId, "人太多了，请稍后");
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("程序出现错误,请联系系统管理员");
        } finally {

        }

    }

    /**
     * 在 controller 层进行加锁，把加锁步骤提前
     *
     * @param skuId
     * @return
     */
    @ApiOperation(value = "秒杀实现方式--Lock加锁(改进版)")
    @PostMapping("/start/lockImprove")
    public Result startLockImprove(Integer skuId) {
        // 加锁
        lock.lock();
        try {
            log.info("开始秒杀...Lock加锁(改进版)...");
            Integer userId = new Random().nextInt() * (99999 - 10000 + 1) + 10000;
            Result result = secondKillService.startKillByLockImprove(userId, skuId);
            if (result != null) {
                log.info("用户:{}--{}", userId, result.getMessage());
            } else {
                log.info("用户:{}--{}", userId, "人太多了，请稍后");
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("程序出现错误,请联系系统管理员");
        } finally {
            lock.unlock();
        }

    }

    /**
     * @param skuId
     * @return
     */
    @ApiOperation(value = "秒杀实现方式--Lock加锁(AOP版)")
    @PostMapping("/start/lockAop")
    public Result startLockAop(Integer skuId) {
        try {
            log.info("开始秒杀...Lock加锁(AOP版)...");
            Integer userId = new Random().nextInt() * (99999 - 10000 + 1) + 10000;
            Result result = secondKillService.startKillByLockAop(userId, skuId);
            if (result != null) {
                log.info("用户:{}--{}", userId, result.getMessage());
            } else {
                log.info("用户:{}--{}", userId, "人太多了，请稍后");
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("程序出现错误,请联系系统管理员");
        } finally {
        }

    }

    /**
     * @param skuId
     * @return
     */
    @ApiOperation(value = "秒杀实现方式--悲观锁(for update版)")
    @PostMapping("/start/forUpdate")
    public Result startForUpdate(Integer skuId) {
        try {
            log.info("开始秒杀...悲观锁(for update版)...");
            Integer userId = new Random().nextInt() * (99999 - 10000 + 1) + 10000;
            Result result = secondKillService.startKillByForUpdate(userId, skuId);
            if (result != null) {
                log.info("用户:{}--{}", userId, result.getMessage());
            } else {
                log.info("用户:{}--{}", userId, "人太多了，请稍后");
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("程序出现错误,请联系系统管理员");
        } finally {
        }

    }

    /**
     * @param skuId
     * @return
     */
    @ApiOperation(value = "秒杀实现方式--悲观锁(update版)")
    @PostMapping("/start/update")
    public Result startUpdate(Integer skuId) {
        try {
            log.info("开始秒杀...悲观锁(update版)...");
            Integer userId = new Random().nextInt() * (99999 - 10000 + 1) + 10000;
            Result result = secondKillService.startKillByUpdate(userId, skuId);
            if (result != null) {
                log.info("用户:{}--{}", userId, result.getMessage());
            } else {
                log.info("用户:{}--{}", userId, "人太多了，请稍后");
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("程序出现错误,请联系系统管理员");
        } finally {
        }

    }

    /**
     * @param skuId
     * @return
     */
    @ApiOperation(value = "秒杀实现方式--阻塞队列")
    @PostMapping("/start/blockQueue")
    public Result startBlockQueue(Integer skuId) {
        String message = "";
        try {
            log.info("开始秒杀...阻塞队列...");
            Integer userId = new Random().nextInt() * (99999 - 10000 + 1) + 10000;

            ProductOrder productOrder = new ProductOrder();
            productOrder.setUserId(userId);
            productOrder.setSkuId(skuId);

            Boolean offer = SecondKillQueue.getInstance().offer(productOrder);

            if (offer) {
                log.info("用户:{}--{}", userId, "秒杀成功");
                message = "秒杀成功";
            } else {
                log.info("用户:{}--{}", userId, "人太多了，请稍后");
                message = "人太多了，请稍后";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("程序出现错误,请联系系统管理员");
        } finally {
        }
        return Result.success(message);

    }

    /**
     * @param skuId
     * @return
     */
    @ApiOperation(value = "秒杀实现方式--Disruptor队列")
    @PostMapping("/start/disruptor")
    public Result startDisruptor(Integer skuId) {
        try {
            log.info("开始秒杀...Disruptor队列...");
            Integer userId = new Random().nextInt() * (99999 - 10000 + 1) + 10000;

            SecondKillEvent secondKillEvent = new SecondKillEvent();
            secondKillEvent.setUserId(userId);
            secondKillEvent.setSkuId(skuId);
            DisruptorUtil.producer(secondKillEvent);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("程序出现错误,请联系系统管理员");
        } finally {
        }
        return Result.success("秒杀成功");

    }
}
