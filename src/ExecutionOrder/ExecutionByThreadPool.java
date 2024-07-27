package ExecutionOrder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Jacob
 * @Description: 按序执行线程，使用单线程池 SingleThreadPool
 * @Date: 2024-07-25 20:16
 * @Version: 1.0
 */
public class ExecutionByThreadPool {

    private static int count = 1;

    static class Work implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " " + "Thread-" + count++);
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Work());
        Thread t2 = new Thread(new Work());
        Thread t3 = new Thread(new Work());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(t1);
        executorService.submit(t2);
        executorService.submit(t3);
        executorService.shutdown();
    }

}
