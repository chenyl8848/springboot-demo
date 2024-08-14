package com.codechen.secondkill.queue;

import com.codechen.secondkill.entity.ProductOrder;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author cyl
 * @date 2023-02-11 21:16
 * @description 秒杀阻塞队列：思想就是将接收到的请求按顺序存放到队列中，消费者线程逐一从队列里取数据进行处理
 */
public class SecondKillQueue {

    // 阻塞队列的大小
    static final int QUEUE_MAX_SIZE = 500;

    // 用于并发请求下的队列
    static BlockingDeque<ProductOrder> blockingDeque = new LinkedBlockingDeque<>(QUEUE_MAX_SIZE);

    // 使用内部静态类的方式，实现单例模式
    private SecondKillQueue() {

    }

    public static class SingletonHolder {
        // 静态初始化器，由 JVM 来保证线程安全
        private static final SecondKillQueue secondKillQueue = new SecondKillQueue();
    }

    /**
     * 获取单例队列
     *
     * @return
     */
    public static SecondKillQueue getInstance() {
        return SingletonHolder.secondKillQueue;
    }

    /**
     * 入队
     * add(e) 队列未满时，返回true；队列满则抛出IllegalStateException(“Queue full”)异常——AbstractQueue
     * put(e) 队列未满时，直接插入没有返回值；队列满时会阻塞等待，一直等到队列未满时再插入。
     * offer(e) 队列未满时，返回true；队列满时返回false。非阻塞立即返回。
     * offer(e, time, unit) 设定等待的时间，如果在指定时间内还不能往队列中插入数据则返回false，插入成功返回true。
     *
     * @param productOrder
     * @return
     */
    public Boolean offer(ProductOrder productOrder) {
        return blockingDeque.offer(productOrder);
    }

    /**
     * 出队
     * poll() 获取并移除队首元素，在指定的时间内去轮询队列看有没有首元素有则返回，否者超时后返回 null
     * take() 与带超时时间的 poll 类似，不同在于 take 时候如果当前队列空了它会一直等待其他线程调用，notEmpty.signal()才会被唤醒
     *
     * @return
     * @throws InterruptedException
     */
    public ProductOrder take() throws InterruptedException {
        return blockingDeque.take();
    }

    /**
     * 获取队列大小
     *
     * @return
     */
    public int getQueueSize() {
        return blockingDeque.size();
    }


}
