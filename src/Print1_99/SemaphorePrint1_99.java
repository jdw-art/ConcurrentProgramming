package Print1_99;

import java.util.concurrent.Semaphore;

/**
 * @Author: Jacob
 * @Description: 三个线程交替打印 1-99，使用 Semaphore
 * @Date: 2024-07-25 21:08
 * @Version: 1.0
 */
public class SemaphorePrint1_99 {
    private static int count = 1;
    private static final Object lock = new Object();

    static class Work implements Runnable {

        private int threadId;
        private Semaphore s1;
        private Semaphore s2;

        public Work(int threadId, Semaphore s1, Semaphore s2) {
            this.threadId = threadId;
            this.s1 = s1;
            this.s2 = s2;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    s1.acquire();
                    if (count > 99) {
                        s2.release();
                        break;
                    }
                    System.out.println("线程-" + threadId + ": " + count++);
                    s2.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(0);
        Semaphore s3 = new Semaphore(0);

        new Thread(new Work(1, s1, s2)).start();
        new Thread(new Work(2, s2, s3)).start();
        new Thread(new Work(3, s3, s1)).start();
    }
}
