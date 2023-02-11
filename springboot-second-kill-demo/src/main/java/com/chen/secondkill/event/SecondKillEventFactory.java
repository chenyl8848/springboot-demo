package com.chen.secondkill.event;

import com.lmax.disruptor.EventFactory;

/**
 * @author cyl
 * @date 2023-02-11 21:51
 * @description 秒杀事件生成工厂
 */
public class SecondKillEventFactory implements EventFactory<SecondKillEvent> {
    @Override
    public SecondKillEvent newInstance() {
        return new SecondKillEvent();
    }
}
