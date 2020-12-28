package com.arc.zero.controller.data.test.stress;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yechao
 * @date 2020/12/19 3:12 下午
 */
@Slf4j
@RestController
@RequestMapping("/test/stress")
public class StressController {


    boolean doStresss = false;

    @RequestMapping("/switch")
    public ResponseEntity test1() {
        System.out.println("压测开启=" + doStresss);
        doStresss = !doStresss;
        System.out.println("压测开启=" + doStresss);
        return ResponseEntity.ok(doStresss);
    }

    @RequestMapping("/on")
    public ResponseEntity test2(Date date) {
        long t1 = System.currentTimeMillis();

        int total = 0;
        int success = 0;
        while (doStresss) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            total = total + 1;
            Long response = getSelectResult();
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

    private Long getSelectResult() {
        long currentTimeMillis = System.currentTimeMillis();
//        if (currentTimeMillis % 10 == 0) {
//            return null;
//        }
        return currentTimeMillis;
    }

}
