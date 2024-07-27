package ProducerConsumer;

import java.util.Random;

/**
 * @Author: Jacob
 * @Description: 生产者-消费者模型，BlockingQueue 测试
 * @Date: 2024-07-27 18:12
 * @Version: 1.0
 */
public class BlockingQueueCacheTest {

    static class Consumer implements Runnable {

        private BlockingQueueCache cache;

        public Consumer(BlockingQueueCache cache) {
            this.cache = cache;
        }

        @Override
        public void run() {
            cache.consume();
        }
    }

    static class Producer implements Runnable {

        private BlockingQueueCache cache;
        private Random random = new Random();

        public Producer(BlockingQueueCache cache) {
            this.cache = cache;
        }

        @Override
        public void run() {
            cache.produce(random.nextInt());
        }
    }

    public static void main(String[] args) {
        BlockingQueueCache cache = new BlockingQueueCache(10);

        for (int i = 0; i < 10; ++i) {
            new Thread(new Consumer(cache)).start();
        }

        for (int i = 0; i < 10; ++i) {
            new Thread(new Producer(cache)).start();
        }
    }

}
