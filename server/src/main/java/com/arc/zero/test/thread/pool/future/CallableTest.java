package com.arc.zero.test.thread.pool.future;

import java.util.concurrent.*;

/**
 * Runnable接口有个问题，它的方法没有返回值。如果任务需要一个返回结果，那么只能保存到变量，还要提供额外的方法读取，非常不便。所以，Java标准库还提供了一个Callable接口，和Runnable接口比，它多了一个返回值：
 *
 * @author yechao
 * @date 2020/10/12 4:43 下午
 */
public class CallableTest implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "HELLO "+System.currentTimeMillis();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        // 定义任务:
        // 提交任务并获得Future:
        Future<String> future1 = executor.submit(new CallableTest());
        Future<String> future2 = executor.submit(new CallableTest());
        Future<String> future3 = executor.submit(new CallableTest());
        // 从Future获取异步执行返回的结果:
         // 可能阻塞
        System.out.println(future1.get());
        System.out.println(future2.get());
        System.out.println(future3.get());
        //关闭线程池
        executor.shutdown();

        //get()：获取结果（可能会等待）
        //get(long timeout, TimeUnit unit)：获取结果，但只等待指定的时间；
        //cancel(boolean mayInterruptIfRunning)：取消当前任务；
        //isDone()：判断任务是否已完成。


        //当我们提交一个Callable任务后，我们会同时获得一个Future对象，然后，我们在主线程某个时刻调用Future对象的get()方法，就可以获得异步执行的结果。
        // 在调用get()时，如果异步任务已经完成，我们就直接获得结果。如果异步任务还没有完成，那么get()会阻塞，直到任务完成后才返回结果。

    }
}
