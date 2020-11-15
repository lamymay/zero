package com.arc.zero.test.thread.pool.future;

import java.util.concurrent.CompletableFuture;

/**
 * 使用Future获得异步执行结果时，要么调用阻塞方法get()，要么轮询看isDone()是否为true，这两种方法都不是很好，因为主线程也会被迫等待。
 * 从Java 8开始引入了CompletableFuture，它针对Future做了改进，可以传入回调对象，当异步任务完成或者发生异常时，自动调用回调对象的回调方法。
 * 我们以获取股票价格为例，看看如何使用CompletableFuture：
 * CompletableFuture的优点是：
 * 异步任务结束时，会自动回调某个对象的方法；
 * 异步任务出错时，会自动回调某个对象的方法；
 * 主线程设置好回调后，不再关心异步任务的执行。
 * 和JavaScript的promise机制很像啊？？？
 *
 * @author yechao
 * @date 2020/10/12 5:14 下午
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> completableFuture = CompletableFuture.supplyAsync(CompletableFutureTest::fetchPrice);
        // 如果执行成功:
        completableFuture.thenAccept((result) -> {
            System.out.println("### 执行成功");
            System.out.println("price: " + result);
        })
        // 如果执行异常:
         .exceptionally((e) -> {
            System.out.println("### 异常");
            e.printStackTrace();
            return null;
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static Double fetchPrice() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price failed!");
        }
        return 5 + Math.random() * 20;
    }
}
