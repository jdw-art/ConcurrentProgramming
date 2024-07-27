package ExecutionOrder;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: Jacob
 * @Description: 按序执行线程，使用 CompletableFuture
 * @Date: 2024-07-25 20:20
 * @Version: 1.0
 */
public class ExecutionByCompletableFuture {

    static class Work implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Work());
        Thread t2 = new Thread(new Work());
        Thread t3 = new Thread(new Work());

        CompletableFuture.runAsync(() -> t1.start())
                .thenRun(() -> t2.start())
                .thenRun(() -> t3.start());
    }

}
