package ProducerConsumer;

import java.util.Random;

/**
 * @Author: Jacob
 * @Description: 生产者-消费者模型，ReentrantLock 测试
 * @Date: 2024-07-27 17:52
 * @Version: 1.0
 */
public class ReentrantLockCacheTest {

    static class Consumer implements Runnable {
        private ReentrantLockCache cache;

        public Consumer(ReentrantLockCache cache) {
            this.cache = cache;
        }

        @Override
        public void run() {
            cache.consume();
        }
    }

    static class Producer implements Runnable {
        private ReentrantLockCache cache;

        private Random random = new Random();

        public Producer(ReentrantLockCache cache) {
            this.cache = cache;
        }

        @Override
        public void run() {
            cache.produce(random.nextInt());
        }
    }

    public static void main(String[] args) {
        ReentrantLockCache cache = new ReentrantLockCache(10);

        for (int i = 0; i < 10; ++i) {
            new Thread(new Consumer(cache)).start();
        }

        for (int i = 0; i < 10; ++i) {
            new Thread(new Producer(cache)).start();
        }
    }

}
