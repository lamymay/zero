package com.arc.zero.test.breakpoint.test1;

/**
 * @author yechao
 * @since 2020/8/14 12:51 下午
 */
public class BreakPointThredTest {


    public static void main(String[] args) {
        //Box box = new Box();
        //Producer producer = new Producer(box);
        //Consumer consumer = new Consumer(box);

        Thread thread1 = new Thread("生成者");
        Thread thread2 = new Thread("消费者");

        thread1.start();
        thread2.start();
    }
}

