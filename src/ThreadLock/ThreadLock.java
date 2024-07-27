package ThreadLock;

/**
 * @Author: Jacob
 * @Description: 线程死锁
 * @Date: 2024-07-25 21:47
 * @Version: 1.0
 */
public class ThreadLock {

    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName() + " get resource 1");

                System.out.println(Thread.currentThread().getName() + " waiting for resource 2");

                synchronized (resource2) {
                    System.out.println(Thread.currentThread().getName() + " get resource 2");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (resource2) {
                System.out.println(Thread.currentThread().getName() + " get resource 2");

                System.out.println(Thread.currentThread().getName() + " waiting for resource 1");

                synchronized (resource1) {
                    System.out.println(Thread.currentThread().getName() + " get resource 1");
                }
            }
        }).start();
    }
}
