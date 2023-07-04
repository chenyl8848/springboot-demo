package com.cyl.secondkill.event;

import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;

/**
 * @author cyl
 * @date 2023-02-11 21:56
 * @description 使用 translator 方式生产者
 */
public class SecondKillEventProducer {

    private static final EventTranslatorVararg<SecondKillEvent> translator = ((secondKillEvent, seq, objects) -> {
       secondKillEvent.setUserId((Integer) objects[0]);
       secondKillEvent.setSkuId((Integer) objects[1]);
    });

    private final RingBuffer<SecondKillEvent> ringBuffer;

    public SecondKillEventProducer(RingBuffer<SecondKillEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void secondKill(Integer secondKillId, Integer userId) {
        this.ringBuffer.publishEvent(translator, secondKillId, userId);
    }
}
