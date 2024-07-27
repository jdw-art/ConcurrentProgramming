package ExecutionOrder;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: Jacob
 * @Description: 按序执行线程，使用 CountDownLatch
 * @Date: 2024-07-25 20:12
 * @Version: 1.0
 */
public class ExecutionByCountDownLatch {

    static class Work implements Runnable {
        private CountDownLatch c1;
        private CountDownLatch c2;

        public Work(CountDownLatch c1, CountDownLatch c2) {
            this.c1 = c1;
            this.c2 = c2;
        }

        @Override
        public void run() {
            try {
                c1.await();
                System.out.println(Thread.currentThread().getName());
                c2.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CountDownLatch c1 = new CountDownLatch(0);
        CountDownLatch c2 = new CountDownLatch(1);
        CountDownLatch c3 = new CountDownLatch(1);

        Thread t1 = new Thread(new Work(c1, c2));
        Thread t2 = new Thread(new Work(c2, c3));
        Thread t3 = new Thread(new Work(c3, c3));
        t1.start();
        t2.start();
        t3.start();
    }
}
