package Print1_99;

/**
 * @Author: Jacob
 * @Description: 三个线程交替打印 1-99，使用 Synchronized + wait/notify
 * @Date: 2024-07-25 21:01
 * @Version: 1.0
 */
public class SynchronizedPrint1_99 {

    private static int count = 1;
    private static final Object lock = new Object();

    static class Work implements Runnable {
        private int threadId;
        public Work(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    synchronized (lock) {
                        while (count % 3 != (threadId - 1)) {
                            lock.wait();
                        }
                        if (count > 99) {
                            lock.notifyAll();
                            break;
                        }
                        System.out.println("线程-" + threadId + ": " + count++);
                        lock.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Work(1)).start();
        new Thread(new Work(2)).start();
        new Thread(new Work(3)).start();
    }

}
