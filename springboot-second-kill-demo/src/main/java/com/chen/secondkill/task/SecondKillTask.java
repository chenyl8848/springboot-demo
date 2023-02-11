package com.chen.secondkill.task;

import com.chen.secondkill.common.Result;
import com.chen.secondkill.entity.ProductOrder;
import com.chen.secondkill.queue.SecondKillQueue;
import com.chen.secondkill.service.SecondKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author cyl
 * @date 2023-02-11 21:28
 * @description 消费秒杀队列任务
 */
@Component
@Slf4j
public class SecondKillTask implements ApplicationRunner {

    @Autowired
    private SecondKillService secondKillService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(() -> {
            log.info("秒杀队列启动成功...");
            while (true) {
                try {
                    ProductOrder productOrder = SecondKillQueue.getInstance().take();
                    if (productOrder != null) {
                        Result result = secondKillService.startKillByLockAop(productOrder.getUserId(), productOrder.getSkuId());
                        if (result != null && result.getCode().equals(200)) {
                            log.info("SecondKillTask,result:{}", result);
                            log.info("SecondKillTask 从消息队列中取出用户，用户:{}", productOrder.getUserId(), "秒杀成功");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
