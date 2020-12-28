package com.arc.zero.controller.data.test.stress;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yechao
 * @date 2020/12/19 3:12 下午
 */
@Slf4j
@RestController
@RequestMapping("/test/stress2")
public class StressController2 {


    boolean doStresss = false;

    @RequestMapping("/switch")
    public ResponseEntity test1() {
        System.out.println("压测开启=" + doStresss);
        doStresss = !doStresss;
        System.out.println("压测开启=" + doStresss);
        return ResponseEntity.ok(doStresss);
    }

    @RequestMapping("/on")
    public ResponseEntity test2() {
        long t1 = System.currentTimeMillis();

        int total = 0;
        int success = 0;
        while (doStresss) {
            total = total + 1;
            Long response = work();
            if (response != null) {
                success = success + 1;
            }
        }
        long t2 = System.currentTimeMillis();

        Map<String, Object> map = new HashMap<>();
        map.put("总测试次数", total);
        map.put("耗时", (t2 - t1));
        map.put("次数/时间", (long) total / (t2 - t1));
        map.put("总测试成功次数", success);

        return ResponseEntity.ok(map);
    }

    private Long work() {
        ExecutorService executor = new ThreadPoolExecutor(4,
                7,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>()
        );

        int i = 1;
        while (i < 100) {
            i += i;
            String message = "" +i;
            executor.execute(new ArcTask(message));
        }
        executor.shutdown();//关闭线程池的时候，它会等待正在执行的任务先完成，然后再关闭。
        return 1L;

    }

//    private Long getSelectResult() {
//        long currentTimeMillis = System.currentTimeMillis();
//        if (currentTimeMillis % 10 == 0) {
//            return null;
//        }
//        return currentTimeMillis;
//    }

    public static void main(String[] args) {
        StressController2 stressController2 = new StressController2();
        Long work = stressController2.work();
        System.out.println(work);

    }

}
