package com.arc.zero.test.ee;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试hashMap
 *
 * @author yechao
 * @date 2021/1/7 10:27 上午
 */
public class HashMapTest {


    public static void main(String[] args) {
        testPutAll();
        testPut();
        testPutIfAbsent();
    }

    private static void testPutAll() {
        Map<String, Object> existMap = new HashMap<>();
        Map<String, Object> newMap = new HashMap<>();

        existMap.put("name", "Java");
        existMap.put("age", 16);
        existMap.put("pwd", "1234");
        newMap.put("name", "Go");
        newMap.put("age", 1);
        newMap.put("pwd", "ABCD_1234");
        newMap.put("avatar", "JPEG");

        System.out.println("1 测试putAll 是否是以新的map去覆盖");
        //putAll全部时候 a.putAll(b)  a的相同key会被b覆盖掉
        newMap.putAll(existMap);
        System.out.println("老map" + existMap);
        System.out.println("新map" + newMap);

    }

    private static void testPut() {

        // key
        String name = "name";
        String email = "email";

        // 初始化两个测试map
        Map<String, Object> existMap = new HashMap<>();
        Map<String, Object> newMap = new HashMap<>();

        existMap.put("name", "Java");
        existMap.put("age", 16);
        existMap.put("pwd", "1234");

        newMap.put("name", "Go");
        newMap.put("age", 1);
        newMap.put("pwd", "ABCD_1234");
        newMap.put(email, "2320158601@qq.com");

        System.out.println("2 测试put 是否是以新的map去覆盖");
        //putAll全部时候 a.put(b)  a和b的相同key时候,a的key会被b覆盖掉
        newMap.putIfAbsent(name, existMap.get(name));
        newMap.put(email, existMap.get(email));
        System.out.println(newMap);
    }

    private static void testPutIfAbsent() {

        // key
        String name = "name";
        String email = "email";

        // 初始化两个测试map
        Map<String, Object> existMap = new HashMap<>();
        Map<String, Object> newMap = new HashMap<>();

        existMap.put("name", "Java");
        existMap.put("age", 16);
        existMap.put("pwd", "1234");

        newMap.put("name", "Go");
        newMap.put("age", 1);
        newMap.put("pwd", "ABCD_1234");
        newMap.put(email, "2320158601@qq.com");

        System.out.println("3 测试 testPutIfAbsent 是否是以新的map去覆盖");
        // a.putIfAbsent(key,value)  key在a中存在则a中key对应的value不变 [如果缺席就放]
        newMap.putIfAbsent(name, "Python");
        newMap.putIfAbsent("name2", "Python");
        System.out.println(newMap);
    }
}
