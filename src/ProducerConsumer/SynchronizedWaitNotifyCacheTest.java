package ProducerConsumer;

import java.util.Random;

/**
 * @Author: Jacob
 * @Description: 生产者-消费者模型，Synchronized + wait/notify 测试
 * @Date: 2024-07-27 16:19
 * @Version: 1.0
 */
public class SynchronizedWaitNotifyCacheTest {

    static class Consumer implements Runnable {
        private SynchronizedWaitNotifyCache cache;

        public Consumer(SynchronizedWaitNotifyCache cache) {
            this.cache = cache;
        }

        @Override
        public void run() {
            cache.consume();
        }
    }

    static class Producer implements Runnable {

        private SynchronizedWaitNotifyCache cache;
        Random random = new Random();

        public Producer(SynchronizedWaitNotifyCache cache) {
            this.cache = cache;
        }

        @Override
        public void run() {
            cache.produce(random.nextInt());
        }
    }

    public static void main(String[] args) {

        SynchronizedWaitNotifyCache cache = new SynchronizedWaitNotifyCache(10);

        for (int i = 0; i < 10; ++i) {
            new Thread(new Consumer(cache)).start();
        }

        for (int i = 0; i < 10; ++i) {
            new Thread(new Producer(cache)).start();
        }
    }

}
