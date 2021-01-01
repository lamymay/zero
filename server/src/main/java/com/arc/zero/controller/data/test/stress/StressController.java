package com.arc.zero.controller.data.test.stress;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    /**
     * 是否压测
     */
    boolean doStress = false;

    @RequestMapping("/switch")
    public ResponseEntity switch1() {
        System.out.println("压测开启=" + doStress);
        doStress = !doStress;
        System.out.println("压测开启=" + doStress);
        return ResponseEntity.ok(doStress);
    }

    // http://127.0.0.1:8000/zero/test/stress/test1
    @RequestMapping("/test1")
    public ResponseEntity test1() {
        Map<String, Object> map = new HashMap<>();
        try {
            long t1 = System.currentTimeMillis();
            if (doStress) {
                long beforeSleep = System.currentTimeMillis();

                Thread.sleep(3000);
                long afterSleep = System.currentTimeMillis();
                map.put("睡眠时间是", (float) (afterSleep - beforeSleep) / 1000F);

            }

            long t2 = System.currentTimeMillis();
            map.put("耗时", (t2 - t1));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(map);
    }


    @RequestMapping("/test2")
    public ResponseEntity test2() {
        long t1 = System.currentTimeMillis();
        int total = 0;
        int success = 0;

        while (doStress) {
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

    public static void main(String[] args) throws InterruptedException {
        int batTotal = 100000;
        int times = batTotal / 3000;
        System.out.println(times);
//        for (int i = 0; i < times; i++) {
//            //System.out.println(i);
//        }


        System.out.println((times *3) / 60 + "min");
    }

}


//      System.out.println("4yG-7T6w0tG12a0DMDVQTQ".length());
//                System.out.println("_NDkI0333kIqLEJCoeEj0w".length());
//                System.out.println("7VNUMirSUEEER0QK8x1Uig".length());
//                System.out.println("k49BfJI1NHfpQl4n_nti1Q".length());
//                System.out.println("ECL--ZWtZ2ViithPw0vvIQ".length());
//                System.out.println("bfkg8zxw-lQbcRgMk6iSZg".length());