package Print1_99;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Jacob
 * @Description: 三个线程交替打印 1-99，使用 ReentrantLock
 * @Date: 2024-07-25 20:49
 * @Version: 1.0
 */
public class ReentrantLockPrint1_99 {

    private static int count = 1;

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition1 = lock.newCondition();
    private static Condition condition2 = lock.newCondition();
    private static Condition condition3 = lock.newCondition();

    static class Work implements Runnable {
        private int threadId;
        private Condition condition1;
        private Condition condition2;

        public Work(int threadId, Condition condition1, Condition condition2) {
            this.threadId = threadId;
            this.condition1 = condition1;
            this.condition2 = condition2;
        }

        @Override
        public void run() {
            try {
                while (true){
                    lock.lock();
                    try {
                        while (count % 3 != (threadId - 1)) {
                            condition1.await();
                        }
                        if (count > 99) {
                            condition2.signal();
                            break;
                        }
                        System.out.println("线程-" + threadId + ": " + count++);
                        condition2.signal();
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Work(1, condition1, condition2)).start();
        new Thread(new Work(2, condition2, condition3)).start();
        new Thread(new Work(3, condition3, condition1)).start();
    }

}
