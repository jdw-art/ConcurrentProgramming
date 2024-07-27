package ExecutionOrder;

/**
 * @Author: Jacob
 * @Description: 按序执行线程，使用 join
 * @Date: 2024-07-25 20:07
 * @Version: 1.0
 */
public class ExecutionByJoin {

    static class Work implements Runnable {
        private Thread beforeThread;

        public Work(Thread beforeThread) {
            this.beforeThread = beforeThread;
        }

        @Override
        public void run() {
            try {
                if (beforeThread != null) {
                    beforeThread.join();
                    System.out.println(Thread.currentThread().getName());
                } else {
                    System.out.println(Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Work(null));
        Thread t2 = new Thread(new Work(t1));
        Thread t3 = new Thread(new Work(t2));
        t1.start();
        t2.start();
        t3.start();
    }
}
