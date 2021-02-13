package com.arc.zero.controller.data.test.file;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 检查电脑磁盘最大一个
 *
 * @author yechao
 * @date 2020/12/7 1:18 下午
 */
public class SystemDeskUtil {

    /**
     * 系统名称key
     */
    public static String OS_NAME = "os.name";
    public static String LINUX = "LINUX";
    public static String WINDOWS = "WINDOWS";
    public static String MAC = "MAC";

    /**
     * @return 判断是否是windows系统
     */
    private static boolean isWindow() {
        return isOS(WINDOWS);
    }

    /**
     * @return 判断是否是非windows系统
     */
    private static boolean isNotWindow() {
        return !isWindow();
    }

    /**
     * @return 判断是否是Linux系统
     */
    private static boolean isLinux() {
        return isOS(LINUX);
    }

    /**
     * @return 判断是否是unix系统
     */
    private static boolean isUnix() {
        return isOS(LINUX) | isOS(MAC);
    }

    /**
     * @return 判断是否是mac系统
     */
    private static boolean isMac() {
        return isOS(MAC);
    }

    /**
     * 是什么系统
     *
     * @param osType 系统类型名称,例如:LINUX/windows/MAC
     * @return boolean
     */
    private static boolean isOS(String osType) {
        return System.getProperty(OS_NAME).toUpperCase().contains(osType);
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

    //@SuppressWarnings("unchecked")
    private static Map getLinuxDiskInfo(String path) {
        Map map = new HashMap(16);
        try {
            Runtime runtime = Runtime.getRuntime();
            // df -hl 查看硬盘空间
            Process process = runtime.exec("df -hl" + path);
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String str = null;
                String[] strArray = null;
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
                if(in!=null){
                    in.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static void main(String[] args) {

        int temp = 200000000 * 100;
        System.out.println("200000000长度=" + "200000000".length());
        System.out.println("temp 长度=" + (temp + "").length());
        System.out.println(temp);

        //32
        int maxValue = Integer.MAX_VALUE;
        int minValue = Integer.MIN_VALUE;
        int maxValue2 = Integer.MAX_VALUE - 2;

        System.out.println(maxValue);//2147483647
        String a = "2147483647";
        String b = "2000000000";
        System.out.println(a.length());
        System.out.println(b.length());

        System.out.println(minValue);//-2147483648
        System.out.println(maxValue2);

        System.out.println(System.getProperty("os.name"));
        System.out.println(isLinux());
        getDiskInfoByPath();

    }

}
