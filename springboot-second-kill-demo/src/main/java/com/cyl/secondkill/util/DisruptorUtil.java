package com.cyl.secondkill.util;

import com.cyl.secondkill.event.SecondKillEvent;
import com.cyl.secondkill.event.SecondKillEventConsumer;
import com.cyl.secondkill.event.SecondKillEventFactory;
import com.cyl.secondkill.event.SecondKillEventProducer;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.ThreadFactory;

/**
 * @author cyl
 * @date 2023-02-11 22:14
 * @description
 */
public class DisruptorUtil {

    static Disruptor<SecondKillEvent> disruptor;

    static {
        SecondKillEventFactory secondKillEventFactory = new SecondKillEventFactory();
        int ringBufferSize = 1024;
        ThreadFactory threadFactory = runnable -> new Thread(runnable);
        disruptor = new Disruptor<SecondKillEvent>(secondKillEventFactory, ringBufferSize, threadFactory);
        disruptor.handleEventsWith(new SecondKillEventConsumer());
        disruptor.start();
    }

    public static void producer(SecondKillEvent secondKillEvent) {
        RingBuffer<SecondKillEvent> ringBuffer = disruptor.getRingBuffer();
        SecondKillEventProducer secondKillEventProducer = new SecondKillEventProducer(ringBuffer);
        secondKillEventProducer.secondKill(secondKillEvent.getUserId(), secondKillEvent.getSkuId());
    }
}
