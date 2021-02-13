package com.arc.zero.utils.disk;


import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 测试linux系统下获取文件路径
 *
 * @author yechao
 * @date 2020/12/8 2:23 下午
 */
public class CheckDiskUtilForLinux {

    public static Desk getDeskUsage() {
        Desk desk = new Desk();
        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec("df -hl");// df -hl 查看硬盘空间
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(p.getInputStream()));
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
                        if (para.trim().length() == 0){
                            continue;
                        }
                        ++m;
                        if (para.endsWith("G") || para.endsWith("Gi")) {
                            // 目前的服务器
                            if (m == 2) {
                                desk.setTotal(para);
                            }
                            if (m == 3) {
                                desk.setUsed(para);
                            }
                        }
                        if (para.endsWith("%")) {
                            if (m == 5) {
                                desk.setUse_rate(para);
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
        return desk;
    }

    public static class Desk {
        private String total;
        private String used;
        private String use_rate;

        @Override
        public String toString() {
            return "总磁盘空间：" + total + "，已使用：" + used + "，使用率达：" + use_rate;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public String getUse_rate() {
            return use_rate;
        }

        public void setUse_rate(String use_rate) {
            this.use_rate = use_rate;
        }

    }

    public static void main(String[] args) {
        System.out.println(getDeskUsage());
//        打印结果，博主的电脑是mac，所以显示Gi
//        总磁盘空间：234Gi，已使用：100Gi，使用率达：44%
    }

}
