package com.arc.zero.controller.data.test.file;

import com.arc.utils.file.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 叶超
 * @since 2020/2/21 11:59
 */
@Slf4j
public class RenameFileRemoveSerialNumber {

    /**
     * 分隔符
     */
    static final String segmentation = " - ";


    public static void main(String[] args) {
        //changePathToParent();
        //System.exit(2);

        List<File> files = FileUtil.listFileByDir("T:\\music2\\降央卓玛\\DTS- 《梁静茹 美丽人生》");

        //01=01
        //3=01.
        //4=01 -
        Map<String, String> map = getParameterMap(1);

//        map.put("张震岳 - ", "");

        for (File file : files) {
            String name = file.getName();
            //跳过非音频 格式的文件
            String toUpperCase = name.substring(name.lastIndexOf(".") + 1, name.length()).toUpperCase();
            if (!FileUtil.getSoundSuffixes().contains(toUpperCase)) {
                continue;
            }
            //跳过特别大[200M]的文件  length 单位是 B  KB MB GB
            long size = file.length();
            if (size > (200 * 1024 * 1024)) {
                log.debug("跳过特别大[200M]的文件");
                System.out.println("跳过特别大[200M]的文件");
                continue;
            }

//            FileUtil.replaceFilenameByMap(file, getParameterMap(3));
            FileUtil.replaceFilenameByMap(file, getParameterMap(4));
            for (Map.Entry<String, String> entry : map.entrySet()) {
                name = name.replaceAll(entry.getKey(), entry.getValue());
                log.debug("名字中去除所有匹配到的字符串 {} 后为 {}", entry.getKey(), name);
            }
            String singer = "梁静茹";
            name = singer + segmentation + name;
            //重命名
            file.renameTo(new File(file.getParent() + File.separator + name));


        }
//        for (File file : files) {
//            System.out.println(file);
//            FileUtil.replaceFilenameStartWithStringByMap(file, getParameterMapForStart());
//        }

    }

    private static void changePathToParent() {
        String var = "[母带wav]";
        List<File> files = FileUtil.listFileByDir("T:\\music2\\【64bit192khz 母带wav】\\1Friend-Emil Wakin Chau[母带wav]");

        for (File file : files) {
            if (file.isFile()) {
                String name = file.getName();
                String changedName = FileUtil.appendStringToEndName(name, var);
                String parent = file.getParent();
                name = parent + changedName;
                file.renameTo(new File(name));
            }
        }
    }

    /**
     * 获取替换参数
     * 用 map 的value 替换key
     *
     * @return Map
     */
    public static Map getParameterMap(int no) {
        Map<String, String> map = null;
        switch (no) {
            case 1:
                map = getReplaceSerialNumber();
                break;
            case 2:
                map = getReplaceSerialNumber2();
                break;
            case 3:
                map = getReplaceSerialNumber3();
                break;
            case 4:
                map = getReplaceSerialNumber4();
                break;
            default:
                map = Collections.emptyMap();
        }
        return map;
    }

    public static Map getParameterMapForStart() {
        Map<String, String> map = new HashMap<>();
//        map.put(" - ", "");
        map.put(". ", "");
        return map;
    }


    /**
     * 获取 替换参数  1
     *
     * @return
     */
    public static Map<String, String> getReplaceSerialNumber() {
        Map<String, String> map = new HashMap<>();
        map.put("01", "");
        map.put("02", "");
        map.put("03", "");
        map.put("04", "");
        map.put("05", "");
        map.put("06", "");
        map.put("07", "");
        map.put("08", "");
        map.put("09", "");
        map.put("10", "");
        map.put("11", "");
        map.put("12", "");
        map.put("13", "");
        map.put("14", "");
        map.put("15", "");
        map.put("16", "");
        map.put("17", "");
        map.put("18", "");
        map.put("19", "");
        map.put("20", "");
        return map;
    }

    public static Map<String, String> getReplaceSerialNumber3() {
        Map<String, String> map = new HashMap<>();
        map.put("01.", "");
        map.put("02.", "");
        map.put("03.", "");
        map.put("04.", "");
        map.put("05.", "");
        map.put("06.", "");
        map.put("07.", "");
        map.put("08.", "");
        map.put("09.", "");
        map.put("10.", "");
        map.put("11.", "");
        map.put("12.", "");
        map.put("13.", "");
        map.put("14.", "");
        map.put("15.", "");
        map.put("16.", "");
        map.put("17.", "");
        map.put("18.", "");
        map.put("19.", "");
        map.put("20.", "");
        return map;
    }

    /**
     * >01 - <
     *
     * @return
     */
    public static Map<String, String> getReplaceSerialNumber4() {

        Map<String, String> map = new HashMap<>();
        map.put("01 - ", "");
        map.put("02 - ", "");
        map.put("03 - ", "");
        map.put("04 - ", "");
        map.put("05 - ", "");
        map.put("06 - ", "");
        map.put("07 - ", "");
        map.put("08 - ", "");
        map.put("09 - ", "");
        map.put("10 - ", "");
        map.put("11 - ", "");
        map.put("12 - ", "");
        map.put("13 - ", "");
        map.put("14 - ", "");
        map.put("15 - ", "");
        map.put("16 - ", "");
        map.put("17 - ", "");
        map.put("18 - ", "");
        map.put("19 - ", "");
        map.put("20 - ", "");
        map.put("21 - ", "");
        map.put("22 - ", "");
        map.put("23 - ", "");
        map.put("24 - ", "");
        map.put("25 - ", "");
        map.put("26 - ", "");
        map.put("27 - ", "");
        map.put("28 - ", "");
        map.put("29 - ", "");
        map.put("30 - ", "");
        map.put("31 - ", "");
        map.put("32 - ", "");
        map.put("33 - ", "");
        map.put("34 - ", "");
        map.put("35 - ", "");
        map.put("36 - ", "");
        map.put("37 - ", "");
        map.put("38 - ", "");
        map.put("39 - ", "");
        map.put("40 - ", "");
        map.put("41 - ", "");
        map.put("42 - ", "");
        map.put("43 - ", "");
        map.put("44 - ", "");
        map.put("45 - ", "");
        map.put("46 - ", "");
        map.put("47 - ", "");
        map.put("48 - ", "");
        map.put("49 - ", "");
        map.put("50 - ", "");
        map.put("51 - ", "");
        map.put("52 - ", "");
        map.put("53 - ", "");
        map.put("54 - ", "");
        map.put("55 - ", "");
        map.put("56 - ", "");
        map.put("57 - ", "");
        map.put("58 - ", "");
        map.put("59 - ", "");
        map.put("60 - ", "");
        return map;
    }

    public static Map<String, String> getReplaceSerialNumber2() {
        Map<String, String> map = new HashMap<>();
        map.put("01 ", "");
        map.put("02 ", "");
        map.put("03 ", "");
        map.put("04 ", "");
        map.put("05 ", "");
        map.put("06 ", "");
        map.put("07 ", "");
        map.put("08 ", "");
        map.put("09 ", "");
        map.put("10 ", "");
        map.put("11 ", "");
        map.put("12 ", "");
        map.put("13 ", "");
        map.put("14 ", "");
        map.put("15 ", "");
        map.put("16 ", "");
        map.put("17 ", "");
        map.put("18 ", "");
        map.put("19 ", "");
        map.put("20 ", "");
        return map;
    }

    /**
     * 添加 替换参数  2
     *
     * @return map
     */
    public static Map<String, String> getVar() {
        Map<String, String> map = new HashMap<>();

        //regex正则  \\s* 可以匹配空格、制表符、换页符等空白字符的其中任意一个
        //replaceAll("\\d", "*")把一个字符串所有的数字字符都换成星号;
        //        map.put("singer", "侃侃");
        //todo  去除数字         map.put("number", "\\d");
        //map.put("space", "\\s*");
        //        map.put("minus", "-");

        //        map.put("mqms2", "");
        //        map.put("bracketsLeft", " \\[");
        //        map.put("bracketsRight", "\\]");
        //        map.put("parenthesesLeft", "\\(");
        //        map.put("parenthesesRight", "\\)");
        //        map.put("parenthesesLeftCN", "\\（");
        //        map.put("parenthesesRightCN", "\\）");
        return map;
    }

}
