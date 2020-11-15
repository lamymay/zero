package com.arc.zero.test.thread.pool.noresponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yechao
 * @date 2020/10/12 4:00 下午
 */
public class NewFixedThreadPoolTest {


    public static void main(String[] args) {
        //创建固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 6; i++) {
            executor.submit(new Task(i));
        }
        //线程池在程序结束的时候要关闭。使用shutdown()方法关闭线程池的时候，它会等待正在执行的任务先完成，然后再关闭。
        // shutdownNow()会立刻停止正在执行的任务，
        // awaitTermination()则会等待指定的时间让线程池关闭。
        executor.shutdown();//关闭线程池的时候，它会等待正在执行的任务先完成，然后再关闭。
        //executor.shutdownNow();//会立刻停止正在执行的任务，  固定现成大小定义4个,任务提交6个,立即关闭线程池,则前4个并发执行,第5,6两个就无法执行了

    }


    private static class Task implements Runnable {

        private int name;

        public Task(int name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("### START TASK " + name + " ###");
            System.out.println(
                    "name=" + Thread.currentThread().getName()
                            + "  id=" + Thread.currentThread().getId()
                            + " state=" + Thread.currentThread().getState());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
            }
            System.out.println("### END TASK " + name + " ###");
        }
    }

}
