package com.arc.zero.utils.disk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;


/**
 * 检查本机磁盘空间是否可用工具类
 *
 * @author yechao
 * @date 2020/12/8 2:35 下午
 */
public class CheckDiskUtil extends Thread {

    /**
     * 获得当前操作系统的名称
     */
    private static final String osName = System.getProperty("os.name").toUpperCase(Locale.getDefault());

    /**
     * 系统用户所在分区默认剩余空间
     */
    private static String diskAvail = "10240000";

    /**
     * 是否启动
     */
    private final boolean runFlag = true;

    public static String getDiskAvail() {
        return diskAvail;
    }

    public static void setDiskAvail(String diskAvail) {
        CheckDiskUtil.diskAvail = diskAvail;
    }

    @Override
    public void run() {
        if (!osName.startsWith("WINDOWS")) {
            while (runFlag) {
                BufferedReader breader = null;
                String diskInfo;
                String[] content = null;

                try {
                    sleep(1000);

                    String command = "df " + "/home/djk";

                    //执行查询磁盘空间命令
                    Process process = Runtime.getRuntime().exec(command);

                    //获得执行命令的输入流
                    breader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                    //过滤无用信息
                    breader.readLine();

                    /*
                     * 离线文件目录磁盘分区信息
                     * 第一种情况:本地磁盘
                     * Filesystem           1K-blocks      Used Available Use% Mounted on
                     * /dev/sda2             20972152  18964652   2007500  91% /
                     *
                     * 第二种情况:挂载磁盘
                     * Filesystem           1K-blocks      Used Available Use% Mounted on
                     * 10.70.136.21:/home/umlog
                     *                        41944384  17026880  24917504  41% /home/downbilldir
                     */
                    diskInfo = breader.readLine();

                    if (null != diskInfo) {
                        content = diskInfo.split("\\s+");

                        //保证取到磁盘"可利用的"空间信息
                        while (null != content && content.length < 3) {
                            diskInfo = breader.readLine();
                            if (null != diskInfo) {
                                content = diskInfo.split("\\s+");
                            }
                        }
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != breader) {
                        try {
                            breader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (null != content && content.length >= 3) {
                    //设置实际的磁盘剩余空间大小
                    setDiskAvail(content[3]);
                }
            }
        } else {
            System.out.println("not support windows os....");
        }
    }


}
