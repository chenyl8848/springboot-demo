package com.codechen.secondkill.event;

import com.codechen.secondkill.service.SecondKillService;
import com.codechen.secondkill.common.Result;
import com.codechen.secondkill.util.SpringContextUtil;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cyl
 * @date 2023-02-11 22:02
 * @description 秒杀处理器 消费者
 */
@Slf4j
public class SecondKillEventConsumer implements EventHandler<SecondKillEvent> {

    private SecondKillService secondKillService = SpringContextUtil.getBean(SecondKillService.class);

    @Override
    public void onEvent(SecondKillEvent secondKillEvent, long seq, boolean bool) throws Exception {
        Result result = secondKillService.startKillByLockAop(secondKillEvent.getUserId(), secondKillEvent.getSkuId());
        if (result != null && result.getCode().equals(200)) {
            log.info("用户:{}{}", secondKillEvent.getUserId(), "秒杀成功");
        }
    }
}
