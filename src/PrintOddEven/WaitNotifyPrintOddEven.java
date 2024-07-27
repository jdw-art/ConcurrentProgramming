package PrintOddEven;

/**
 * @Author: Jacob
 * @Description: 交替打印奇偶数，采用 wait/notify
 * @Date: 2024-07-24 21:12
 * @Version: 1.0
 */
public class WaitNotifyPrintOddEven {
    private static int count = 0;
    //当前线程必须拥有此对象的锁，才能调用某个对象的wait()方法能让当前线程阻塞
    private static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(new TurningRunner(), "偶数").start();
        new Thread(new TurningRunner(), "奇数").start();
    }

    //拿到锁，我们就打印,一旦打印完唤醒其他线程就休眠
    static class TurningRunner implements Runnable {
        @Override
        public void run() {
            while (count <= 100) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread() + ":" + count++);
                    lock.notify();
                    if (count <= 100) {
                        try {
                            //如果任务没结束，唤醒其他线程，自己休眠
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
