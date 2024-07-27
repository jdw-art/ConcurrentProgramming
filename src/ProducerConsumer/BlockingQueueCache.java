package ProducerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: Jacob
 * @Description: 生产者-消费者模型，BlockingQueue
 * @Date: 2024-07-27 18:03
 * @Version: 1.0
 */
public class BlockingQueueCache {
    private int maxSize;

    private BlockingQueue cache = new LinkedBlockingQueue();

    public BlockingQueueCache(int maxSize) {
        this.maxSize = maxSize;
    }

    public void consume() {
        try {
//            while (cache.size() == 0) {
//                System.out.println(Thread.currentThread().getName() + " 当前缓冲区为空，等待生产...");
//            }
            Integer value = (Integer) cache.take();
            System.out.println(Thread.currentThread().getName() + " 消费成功：" + value.toString() + " 当前缓冲区大小：" + cache.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void produce(Integer value) {
        try {
//            while (cache.size() == maxSize) {
//                System.out.println(Thread.currentThread().getName() + " 当前缓冲区已满，等待消费...");
//            }
            cache.put(value);
            System.out.println(Thread.currentThread().getName() + " 生产成功: " + value.toString() + " 当前缓冲区大小：" + cache.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
