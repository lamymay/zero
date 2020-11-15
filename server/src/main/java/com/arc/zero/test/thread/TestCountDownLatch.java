package com.arc.zero.test.thread;

/**
 * @author may
 * @since 2019/12/10 13:17
 */

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new Work2(countDownLatch));
            thread.setName("线程-" + (i + 1));
            thread.start();
            countDownLatch.countDown();
        }
    }

}

class Work2 implements Runnable {

    private final CountDownLatch countDownLatch;

    public Work2(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName() + "启动时间是" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
