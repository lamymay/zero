package com.arc.zero.test.thread.pool.noresponse;

import java.util.concurrent.*;

/**如果我们想把线程池的大小限制在4～10个之间动态调整怎么办？我们查看Executors.newCachedThreadPool()方法的源码：
 * 创建指定动态范围的线程池，可以这么写：
 * @author yechao
 * @date 2020/10/12 4:34 下午
 */
public class NewCachedThreadPoolTest {

    public static void main(String[] args) {
        //创建固定大小的线程池
        ExecutorService executor = null;
        executor = new ThreadPoolExecutor(4,
                7,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>()
        );


        for (int i = 0; i < 7; i++) {
            executor.submit(new RunnableTask(i));
        }
        executor.shutdown();
    }

}
