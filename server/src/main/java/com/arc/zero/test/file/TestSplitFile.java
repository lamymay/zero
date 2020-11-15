package com.arc.zero.test.file;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/**
 * @author May
 * @since 2020/1/9 17:16
 */
public class TestSplitFile {

    /**
     * 实现对大文件的切割与合并。 按指定个数切（如把一个文件切成10份）或按指定大小切（如每份最大不超过10M），这两种方式都可以。
     */
    public static void main(String[] args) {
        JFileChooser jfc = new JFileChooser();// Swing中的选择文件
// 选择文件
        int result = jfc.showOpenDialog(null);// 显示框架用于选择文件
        File file;// 要切割的文件
        File dest;// 目的地文件
        try {
            if (result == JFileChooser.APPROVE_OPTION) {// 选中文件
// 切割文件
                file = jfc.getSelectedFile();// 用户选择的文件
                dest = new File(file.getParent(), "spliFile");
                cutingFile(file, dest);// 切割方法
// 2合并(运行时，直接对刚才切割的那些文件碎片进行合并)
                String fileName = file.getName();
                mergeDemo(dest, fileName);// 合并文件
            }
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void mergeDemo(File dest, String fileName)
            throws IOException {
// 健壮性防护(用File对象去开道)
        if (!dest.exists()) {
            throw new RuntimeException("文件不存在");
        }
// 用一个文件数组将里面的文件都装起来
        File[] parth = dest.listFiles();// 返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
        if (parth != null || parth.length == 0) {
            throw new RuntimeException("碎片不存在");
        }
// y用序列流来合并
        ArrayList<FileInputStream> list = new ArrayList<>();
// for (int i = 0; i < parth.length; i++) {
// list.add(new FileInputStream(parth[i]));//不能这样，这样合并出来的文件是顺序乱的
// }
        for (int i = 0; i < parth.length; i++) {
            list.add(new FileInputStream(new File(dest, fileName + (i + 1)
                    + "part")));// 套接技术，文件加的顺序要和原文件一样
        }
// 枚举对象接口
        Enumeration<FileInputStream> en = Collections.enumeration(list);
        SequenceInputStream sq = new SequenceInputStream(en);
// 写入到新文件中
        FileOutputStream fou = new FileOutputStream(new File(dest, fileName));
        byte[] buf = new byte[1024];
        sq.read(buf);
        int len;
        while ((len = sq.read(buf)) > 0) {
            fou.write(buf, 0, len);
        }
        fou.close();
        sq.close();
    }

    private static void cutingFile(File source, File dest) {
// 切割
        try {
            FileInputStream fis = new FileInputStream(source);
            if (!dest.exists()) {// 文件操作IO流要判断文件是否存在。
                dest.mkdir();
            }
            byte[] buf = new byte[1024 * 1024];// 1M
            fis.read(buf);
            int len;
            int cout = 1;
            while ((len = fis.read(buf)) != -1) {
// 用OUT流来切割文件
                FileOutputStream fout = new FileOutputStream(new File(dest,
                        source.getName() + (cout++) + "part"));
                fout.write(buf, 0, len);
                fout.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//参考 https://www.jb51.net/article/142952.htm
//本文实例讲述了Java实现大文件的切割与合并操作。分享给大家供大家参考，具体如下：
//
//这里实现对大文件的切割与合并。
//
//按指定个数切（如把一个文件切成10份）或按指定大小切（如每份最大不超过10M），这两种方式都可以。
//
//在这里我只是给大家写下我自己的一点简单的代码：

//文件切割：把一个文件切割成多个碎片，每个碎片的大小不超过1M。自己可把功能进一步扩展:切割前的文件名、长度，切割后的碎片个数、文件名等信息可写到第一个碎片中或另外用properties把这些写到配置文件中。
//
//文件合并：这里简单假设已知被合并目录的File对象和原文件的名字。其实这些完全可以做成活的,如把这些信息保存在碎片文件或配置文件，也可以同样用文件选择对话框来读取用户的选择。
