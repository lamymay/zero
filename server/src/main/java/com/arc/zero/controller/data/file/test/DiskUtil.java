package com.arc.zero.controller.data.file.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yechao
 * @date 2020/12/8 2:49 下午
 */

public class DiskUtil {

    //linux 查询磁盘空间命令为df -h
    //查询某一个文件系统的容量
    //
    //df -h 名称

    public static void main(String[] args) {
        System.getProperties().list(System.out);
        System.out.println(getDiskInfo("D:\\"));
        System.out.println(isWindow());
        getDiskInfoByPath();
    }

    public static Map getDiskInfo(String path) {
        //判断是否为linux 系统
        if (isLinux()) {
            //是linux系统
            return getLinuxDiskInfo(path);
        } else if (isWindow()) {
            //是window
            return getWindowDiskInfo(path);
        }
        return null;
    }

    private static void getDiskInfoByPath() {
        File[] disks = File.listRoots();
        for (File file : disks) {
            System.out.print(file.getPath() + "    ");
            // 空闲空间
            System.out.print("空闲未使用 = " + file.getFreeSpace() / 1024 / 1024 + "M" + "    ");
            // 已用空间
            System.out.print("已经使用 = " + file.getUsableSpace() / 1024 / 1024 + "M" + "    ");
            // 总空间
            System.out.print("总容量 = " + file.getTotalSpace() / 1024 / 1024 + "M" + "    ");
            System.out.println();
        }
    }

    @SuppressWarnings("unchecked")
    private static Map getWindowDiskInfo(String path) {
        File file = new File(path);
        Map map = new HashMap(16);
        // 空闲空间
        map.put("free", file.getFreeSpace() / 1024 / 1024);
        // 已用空间
        map.put("used", (file.getTotalSpace() - file.getFreeSpace()) / 1024 / 1024);
        // 总空间
        map.put("total", file.getTotalSpace() / 1024 / 1024);
        return map;
    }

    @SuppressWarnings("unchecked")
    private static Map getLinuxDiskInfo(String path) {
        Map map = new HashMap(5);
        try {
            Runtime rt = Runtime.getRuntime();
            // df -hl 查看硬盘空间
            Process p = rt.exec("df -hl" + path);
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(
                        p.getInputStream()));
                String str;
                String[] strArray;
                int line = 0;
                while ((str = in.readLine()) != null) {
                    line++;
                    if (line != 2) {
                        continue;
                    }
                    int m = 0;
                    strArray = str.split(" ");
                    for (String para : strArray) {
                        if (para.trim().length() == 0) {
                            continue;
                        }
                        ++m;
                        if (para.endsWith("G") || para.endsWith("Gi")) {
                            // 目前的服务器
                            if (m == 2) {
                                map.put("total", para);
                            }
                            if (m == 3) {
                                map.put("used", para);
                            }
                        }
                        if (para.endsWith("%")) {
                            if (m == 5) {
                                map.put("use_rate", para);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 判断是否为linux系统
     */
    private static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    /**
     * 判断是否为window系统
     */
    private static boolean isWindow() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
