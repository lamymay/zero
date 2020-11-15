package com.arc.zero.test.file;

import org.springframework.data.redis.connection.util.ByteArraySet;

import java.nio.ByteBuffer;
import java.sql.BatchUpdateException;
import java.util.*;

/**
 * @author 叶超
 * @since 2019/12/4 16:54
 */
public class TestFile {


    public static void main(String[] args) throws BatchUpdateException {
//        0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
        System.out.println("0000000000".length());
        System.out.println("0000000000".length());
        System.out.println("0000000000".length());
        System.out.println("0000000000".length());

        String zero = "  0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000";
        System.out.println(zero.length());
        zero = zero.replaceAll(" ", "");
        zero = zero.replaceAll("-", "");
        System.out.println(zero);//0000000000000000000000000000000000000000000000000000000000000000     --64bit  0
        System.out.println(zero.length());

//        fun1(2);
//        fun2();
        fun3();
        fun4();
        fun5();

    }

    private static void fun5() {
//        如何用Java分配一段连续的1G的内存空间？需要注意些什么？
        ByteBuffer.allocateDirect(1024 * 1024 * 1024);
    }

    private static void fun4() {
//        StringBuilder 为什么线程不安全？
        StringBuilder stringBuilder = new StringBuilder();
        StringBuffer stringBuffer = new StringBuffer();


    }

    private static void fun3() {

        char word = '字';
        System.out.println("字".getBytes().length);
    }

    private static void fun2() {
        HashSet hashSet = new HashSet();
        TreeSet<Object> objects = new TreeSet<>();
        LinkedHashSet<Object> objects1 = new LinkedHashSet<>();
        ByteArraySet byteArrayWrappers = new ByteArraySet();


        List list = new ArrayList();
        LinkedList<Object> objects2 = new LinkedList<>();
    }

    private static Object fun1(Integer id) throws BatchUpdateException {

        if (id == 1) {
            throw new RuntimeException();
        } else if (id == 2) {
            throw new BatchUpdateException();
        }

        return 1;
    }

    class Test2 {
        private String name;

    }

    public class Test3 {
        private String name;

    }

     public static class  Test4 {
        private String name;

    }

}


class TestClz {
    private String name;
    private String password;
}
