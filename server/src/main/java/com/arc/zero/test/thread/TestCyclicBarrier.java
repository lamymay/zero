package com.arc.zero.test.thread;

/**
 * @author may
 * @since 2019/12/10 13:13
 */

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TestCyclicBarrier {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new Work(cyclicBarrier));
            thread.setName("线程-" + (i + 1));
            thread.start();
        }
    }
}

class Work implements Runnable {

    private final CyclicBarrier cyclicBarrier;

    public Work(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        /**
         * CyclicBarrier类的await()方法对当前线程（运行cyclicBarrier.await()代码的线程）进行加锁，然后进入await状态；
         * 当进入CyclicBarrier类的线程数（也就是调用cyclicBarrier.await()方法的线程）等于初始化CyclicBarrier类时配置的线程数时；
         * 然后通过signalAll()方法唤醒所有的线程。
         */
        try {
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName() + "启动时间是" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}
