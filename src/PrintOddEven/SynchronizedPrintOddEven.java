package PrintOddEven;

/**
 * @Author: Jacob
 * @Description: 交替打印奇偶数，采用 synchronized 关键字
 * @Date: 2024-07-24 20:58
 * @Version: 1.0
 */
public class SynchronizedPrintOddEven {
    private static int count = 0;
    //临界资源
    private static final Object lock = new Object();

    public static void main(String[] args) {
        //新建两个线程,第1个只处理偶数，第二个只处理奇数（用位运算），用synchronized来通讯
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < 100) {
                    synchronized (lock) {
                        //count & 1 一个数字把它和1做位与的操作，1再二进制就是1，count转换位二进制，和1去与，
                        // 就是取出count二进制的最低位，最低位是1代表奇数，0代表是偶数，比count%2 == 0 效率高
                        //因为线程是随机抢锁的,可能会出现同一个线程多次进入,但是不满足条件,并不会执行count++.
                        if ((count & 1) == 0) {
                            System.out.println(Thread.currentThread() + ":" + count++);
                        }
                    }
                }
            }
        }, "偶数").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < 100) {
                    synchronized (lock) {
                        //count & 1 一个数字把它和1做位与的操作，1再二进制就是1，count转换位二进制，和1去与，
                        // 就是取出count二进制的最低位，最低位是1代表奇数，0代表是偶数，比count%2 == 0 效率高
                        if ((count & 1) == 1) {
                            System.out.println(Thread.currentThread() + ":" + count++);
                        }
                    }
                }
            }
        }, "奇数").start();
    }
}
