package com.example.demo.test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Oliver.liu
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2022/11/721:01
 */
/**
 * 使用Lock锁的生产者与消费者
 */
public class ConditionConsumerAndProducer {
    /**
     * 队列长度
     */
    private static final int MAX_LEN = 5;
    /**
     * 队列
     */
    private Queue<Integer> queue = new LinkedList<>();
    /**
     * 获取lock锁
     */
    private final Lock lock = new ReentrantLock();

    private final Condition condition = lock.newCondition();

    /**
     * 生产者
     */
    class Producer extends Thread {
        @Override
        public void run() {
            producer();
        }
        private void producer() {
            while (true) {
                //加锁
                lock.lock();
                try {
                    while (queue.size() == MAX_LEN) {
                        System.out.println("当前队列已满");
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //添加数据
                    queue.add(1);
                    System.out.println("当前生产者，产生了一条数据，当前队列长度为：" + queue.size());
                    //唤醒其他线程来消费
                    condition.signal();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    //释放锁
                    lock.unlock();
                }
            }
        }
    }
    /**
     * 消费者
     */
    class Consumer extends Thread {
        @Override
        public void run() {
            consumer();
        }
        private void consumer() {
            while (true) {
                //加锁
                lock.lock();
                try {
                    while (queue.size() == 0) {
                        System.out.println("当前队列已空");
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    System.out.println("当前消费者，消费了一条数据，当前队列长度为：" + queue.size());
                    condition.signal();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    //释放锁
                    lock.unlock();
                }
            }
        }
    }
    public static void main(String[] args) {
        ConditionConsumerAndProducer consumerAndProducer = new ConditionConsumerAndProducer();
        Producer producer = consumerAndProducer.new Producer();
        Consumer consumer = consumerAndProducer.new Consumer();
        producer.start();
        consumer.start();
    }
}
