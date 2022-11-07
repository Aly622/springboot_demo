package com.example.demo.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Oliver.liu
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2022/11/721:04
 */
/**
 * 使用BlockingQueue完成生产者与消费者
 */
public class BlockQueueConsumerAndProducer {
    private BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    class Producer extends Thread {
        @Override
        public void run() {
            producer();
        }
        private void producer() {
            while (true) {
                try {
                    /**
                     * 底层使用lock锁来实现
                     */
                    queue.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("生产者生产一条数据，当前队列长度为：" + queue.size());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            consumer();
        }
        private void consumer() {
            while (true) {
                try {
                    /**
                     * 底层使用lock锁来实现
                     */
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者消费一条数据，当前队列长度为：" + queue.size());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        for (int i = 0; i < 10; i++) {
            try {
                queue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("queue长度为：" + queue.size());
        try {
            Integer take = queue.poll();
            System.out.println("获取到的元素为：" + take);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("执行take方法后，queue的长度为：" + queue.size());

        BlockQueueConsumerAndProducer consumerAndProducer = new BlockQueueConsumerAndProducer();
        Producer producer = consumerAndProducer.new Producer();
        Consumer consumer = consumerAndProducer.new Consumer();
        producer.start();
        consumer.start();
    }
}