package com.arc.zero.controller.data.test.file;

import com.arc.zero.utils.IOUtils;

import java.io.*;

/**
 * @author yechao
 * @date 2020/11/13 4:23 下午
 */
public class RandomAccessFileTest {


    public static void main(String[] args) throws Exception {
        File sourceFile = new File("./RandomAccessFileTest.class");
        RandomAccessFile randomAccessFile = new RandomAccessFile(sourceFile, "rw");
        System.out.println("测试 randomAccessFile=" + randomAccessFile);
        System.out.println("测试 randomAccessFile.length=" + randomAccessFile.length());


        File tempFile = File.createTempFile("abc_1234_您好", ".txt");

        System.out.println("测试 createTempFile=" + tempFile);
        System.out.println("测试 createTempFile.canRead=" + tempFile.canRead());
        System.out.println("测试 createTempFile.getPath=" + tempFile.getPath());
        System.out.println("测试 createTempFile.canWrite=" + tempFile.canWrite());
        System.out.println("测试 createTempFile.canRead=" + tempFile.canRead());
        System.out.println("测试 createTempFile.getParent=" + tempFile.getParent());
        System.out.println("测试 createTempFile.getName=" + tempFile.getName());
        System.out.println("测试 createTempFile.getAbsoluteFile=" + tempFile.getAbsoluteFile());
        System.out.println("测试 createTempFile.getTotalSpace=" + tempFile.getTotalSpace());
        System.out.println("测试 createTempFile.exists=" + tempFile.exists());
        System.out.println("测试 createTempFile.delete=" + tempFile.delete());
        System.out.println("测试 createTempFile.exists=" + tempFile.exists());


    }

    public static final String PREFIX = "stream2file";
    public static final String SUFFIX = ".tmp";

    public static File stream2file(InputStream in) throws IOException {
        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }
}
