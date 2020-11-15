package com.arc.zero.test.file.rename;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yechao
 * @date 2020/10/19 3:51 下午
 */
public class RenameFileTest {


    public static void main(String[] args) {
        //对指定目录下的文件 按一定规则批量重命名
        //
        String parentPath = "/Users/may/Desktop/7/pdf/new";
        Map<String, String> replaceMap = getReplaceMap();
        boolean replace = renameFilename(parentPath, replaceMap);
        System.out.println("命令执行完毕=" + replace);
    }

    private static Map<String, String> getReplaceMap() {
        Map<String, String> replaceMap = new HashMap<>();
        //值得收藏的电子书：《客户驱动的产品开发》作者 特拉维斯·鲁德米克

        replaceMap.put("值得收藏的电子书：","");
        replaceMap.put("《","");
        replaceMap.put("》","");
        replaceMap.put("作者","");
        return replaceMap;
    }

    private static boolean renameFilename(String parentPath, Map<String, String> replaceMap) {
        try {
            // 1 list files


            File patentFile = new File(parentPath);
            File[] files = patentFile.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    // 2 rename
                    String filename = file.getName();
                    String rename = getTargetName(filename, replaceMap);

                    String path = file.getPath();
                    System.out.println("path="+path);
                    File targetFile = new File(file.getParent()+File.separator+rename);
                    file.renameTo(targetFile);
                }
                return true;
            }
        } catch (Exception exception) {

            return false;
        }
        return false;
    }

    private static String getTargetName(String filename, Map<String, String> replaceMap) {
        if (filename == null) {
            return null;
        }
        System.out.println("source name =" + filename);
        for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
            filename = filename.replaceAll(entry.getKey(), entry.getValue());
        }
        System.out.println("target name =" + filename);
        return filename;
    }
}
