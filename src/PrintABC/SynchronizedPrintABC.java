package PrintABC;

/**
 * @Author: Jacob
 * @Description: 三个线程交替打印 ABC，使用 synchronized 和 wait/notify 实现
 * @Date: 2024-07-24 21:55
 * @Version: 1.0
 */
public class SynchronizedPrintABC {
    // 共享变量，表示当前应该打印哪个字母
    private static int state = 0;

    // 共享对象，作为锁和通信的媒介
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // 创建三个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 循环100次
                    for (int i = 0; i < 100; i++) {
                        // 获取锁
                        synchronized (lock) {
                            // 判断是否轮到自己执行
                            while (state % 3 != 0) {
                                // 不是则等待
                                lock.wait();
                            }
                            // 打印字母
                            System.out.println("A");
                            // 修改状态
                            state++;
                            // 唤醒下一个线程
                            lock.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 100; i++) {
                        synchronized (lock) {
                            while (state % 3 != 1) {
                                lock.wait();
                            }
                            System.out.println("B");
                            state++;
                            lock.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 100; i++) {
                        synchronized (lock) {
                            while (state % 3 != 2) {
                                lock.wait();
                            }
                            System.out.println("C");
                            state++;
                            lock.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
