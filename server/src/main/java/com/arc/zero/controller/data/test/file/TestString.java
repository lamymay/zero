package com.arc.zero.controller.data.test.file;

import com.arc.utils.file.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * @author 叶超
 * @since 2020/2/20 17:38
 */
@Slf4j
public class TestString {

    public static void main(String[] args) {
//        changePathToParent();
//        System.exit(0);
//        fun1();
//        fun2();
        File file = new File("T:\\music2\\张靓颖\\张靓颖《The One》华谊唱片 首创[整轨WAV]\\张靓颖 - 未知标题.wav");

        long size = file.length();

        System.out.println(size + "B");
        System.out.println(size / (1024) + "KB");
        System.out.println((float) ((float) size / (1024 * 1024)) + "MB");

        System.out.println("B to KB need to be divided  by " + 1024);
        System.out.println("B to MB need to be divided  by " + 1024 * 1024);
        System.out.println("B to GB need to be divided  by " + 1024 * 1024 * 1024);

        Object result = isMoreThan200MB(file.length());
        System.out.println(result);
    }

    //    private static boolean isMoreThan200MB(File file) {
    private static String isMoreThan200MB(long size) {
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        // 因为还没有到达要使用另一个单位的时候
        // 接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            // 因为如果以MB为单位的话，要保留最后1位小数，
            // 因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
        } else {
            // 否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
        }
    }


    private static void fun2() {
        String path = "T:\\music2\\【64bit192khz 母带wav】\\Britney Jean Spears-悲伤布兰妮\\Sting-你的每次呼吸.wav";
        File file = new File(path);

        String father = file.getParent();
        System.out.println("修正前文件的父级路径" + father);
        File grandfather = new File(father).getParentFile();
        String grandfatherPath = grandfather.getPath();
        System.out.println("grandfatherPath=" + grandfatherPath);

        System.out.println("修正后文件的父级路径=" + grandfatherPath);

        //文件名称操作
        String name = file.getName();

        log.warn("file name={}", name);
        System.out.println("文件原名字name=" + name);
        String var = "[母带wav]";
        String changedName = FileUtil.appendStringToEndName(name, var);
        System.out.println("使用自带方法改变名称" + changedName);


        String target = grandfatherPath + changedName;
        System.out.println("文件的当前目录=" + file.getPath());
        System.out.println("文件的目标目录target=" + target);


    }


    private static void fun1() {
        String fileName = "测试音乐.mp3.ogg";
        fileName = "测试音乐.mp3.flac";
//          fileName = "测试音乐";
        System.out.println(fileName.length());
        System.out.println(fileName.lastIndexOf("."));
        int lastIndexOf = fileName.lastIndexOf(".");

        if (lastIndexOf == -1) {
            throw new RuntimeException("文件没有格式");
        }
        String substring = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

        substring = substring.toUpperCase();
        System.out.println(substring);
        List<String> soundSuffixes = FileUtil.getSoundSuffixes();

        boolean contains = soundSuffixes.contains(substring);
        System.out.println(contains);
        System.out.println(contains);
    }

    private static void changePathToParent() {

        List<File> files = FileUtil.listFileByDir("T:\\music2\\【64bit192khz 母带wav】");

        for (File file : files) {

            String father = file.getParent();
            int lastIndexOf = father.lastIndexOf("\\");
            System.out.println(lastIndexOf);


            String current = file.getPath();
            System.out.println("文件的当前目录" + current);

            System.out.println("文件的当前目录" + current);

        }

    }


}
