package com.arc.zero.controller.data.test.field;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试静态变量控制
 *
 * @author may
 * @since 2019/7/3 19:44
 */
@Slf4j
@RestController
@RequestMapping("/test/field")
public class TestStaticFieldController {

    public static String name = "";
    public static Map<String, Object> testMap = new HashMap<>();

    @PostMapping("/1")
    public static ResponseEntity rename1(@RequestBody Map<String, Object> map) throws IOException {
        testMap = map;
        return ResponseEntity.ok(testMap);
    }


    /**
     * 重命名文件
     *
     * @param var
     * @return
     * @throws IOException
     */
    @PostMapping("/1/{var}")
    public static ResponseEntity rename2(@PathVariable String var) throws IOException {
        name = var;
        return ResponseEntity.ok(name);
    }

    //================================= 测试 =================================
    @GetMapping("/get/{type}")
    public static ResponseEntity get(@PathVariable Integer type) throws IOException {

        switch (type) {
            case 1:

                //Object[] objs=new Object[] {};
                testMap.put("msg", "传入参数,构造对象,处理逻辑,返回结果");
                testMap.put("1", 1);
                return ResponseEntity.ok(testMap);
            case 2:
                return ResponseEntity.ok(2);
            default:
                return ResponseEntity.ok("默认");
        }
    }

    /**
     * https://mp.weixin.qq.com/s/CMjs36IXp1E2EyKiaCz80gr
     */
    private static void test() {
        Integer i = null;

        // false
        if(i instanceof Number){
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        // false
        if(null instanceof Number){
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    public static void main(String[] args) {
        test();

    }
}

