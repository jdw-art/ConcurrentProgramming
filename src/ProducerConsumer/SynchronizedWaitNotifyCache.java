package ProducerConsumer;

import java.util.LinkedList;

/**
 * @Author: Jacob
 * @Description: 生产者-消费者模型，Synchronized + wait/notify
 * @Date: 2024-07-27 16:12
 * @Version: 1.0
 */
public class SynchronizedWaitNotifyCache {

    private int maxSize;

    private LinkedList<Integer> cache = new LinkedList<>();

    public SynchronizedWaitNotifyCache(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void consume() {
        while (cache.size() == 0) {
            System.out.println(Thread.currentThread().getName() + " 当前缓冲区为空，等待生产...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Integer value = cache.removeFirst();
        System.out.println(Thread.currentThread().getName() + " 消费成功：" + value.toString() + " 当前缓冲区size = " + cache.size());
        notifyAll();
    }

    public synchronized void produce(Integer val) {
        while (cache.size() == maxSize) {
            System.out.println(Thread.currentThread().getName() + " 当前缓冲区已满，等待消费...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cache.add(val);
        System.out.println(Thread.currentThread().getName() + " 生产成功：" + val.toString() + " 当前缓冲区size = " + cache.size());
        notifyAll();
    }

}
