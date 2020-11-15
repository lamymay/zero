package com.arc.zero.test.string;


import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * @author yechao
 * @date 2020/10/13 4:29 下午
 */
public class StringTest {

    public static void main(String[] args) {
        StringBuffer stringBuffer = null;
        StringBuilder stringBuilder = null;

        stringBuffer = new StringBuffer("abcd");
        StringBuffer reverse = stringBuffer.reverse();
        System.out.println(stringBuffer.toString());
        System.out.println(reverse.toString());


        String str1 = new String("abc");
        String str2 = new String("abc");
        String str3 = new String("abc");
        System.out.println(str1 + " " + str1.hashCode());
        System.out.println(str2 + " " + str2.hashCode());
        System.out.println(str3 + " " + str3.hashCode());
        System.out.println(str1 == str2);
        System.out.println(str1 == str3);
        System.out.println(str2 == str3);

        test1();
        test2();
        test3Stream();
        test4();

    }

    private static void test4() {

    }

    private static void test3Stream()     {
        try {

            // reader 接口 与stream 是不同的
            //BufferedReader bufferedReader = new BufferedReader(new FileInputStream("a.txt"));

            //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("a.txt"));
            //GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new FileOutputStream("z.zip"));
            //ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("a.dat"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void test2() {
        Object obj = 0.6332;
        System.out.println(" 0.6332的类型 instanceof Double=" + (obj instanceof Double));//true
        System.out.println(" 0.6332的类型 instanceof Float=" + (obj instanceof Float));//false
    }

    private static void test1() {
        String a = "hello";
        String b = "he" + "llo";

        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(a == b);//true
    }
}


