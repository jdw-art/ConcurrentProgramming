package ProducerConsumer;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Jacob
 * @Description: 生产者-消费者模型，ReentrantLock
 * @Date: 2024-07-27 17:44
 * @Version: 1.0
 */
public class ReentrantLockCache {

    private int maxSize;
    private ReentrantLock lock = new ReentrantLock();

    private Condition consumer = lock.newCondition();
    private Condition producer = lock.newCondition();

    private LinkedList cache = new LinkedList();

    public ReentrantLockCache(int maxSize) {
        this.maxSize = maxSize;
    }

    public void consume() {
        lock.lock();
        try {
            while (cache.size() == 0) {
                System.out.println(Thread.currentThread().getName() + " 当前缓冲区为空，等待生产...");
                try {
                    consumer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Integer value = (Integer) cache.removeFirst();
            System.out.println(Thread.currentThread().getName() + " 消费成功：" + value.toString() + " 当前缓冲区大小: " + cache.size());
            producer.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void produce(Integer value) {
        lock.lock();
        try {
            while (cache.size() == maxSize) {
                System.out.println(Thread.currentThread().getName() + " 当前缓冲区已满，等待消费...");
                try {
                    producer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cache.add(value);
            System.out.println(Thread.currentThread().getName() + " 消费成功" + value.toString() + " 当前缓冲区大小：" + cache.size());
            consumer.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
