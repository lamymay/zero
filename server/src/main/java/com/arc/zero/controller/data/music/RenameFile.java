package com.arc.zero.controller.data.music;

import com.arc.utils.file.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * 批量重命名b站提取到的缓存文件
 *
 * @author may
 * @since 2019/7/3 19:44
 */
@Slf4j
@RestController
@RequestMapping("/test/rename")
public class RenameFile {


    @PostMapping("/1")
    public static Object rename(@RequestBody String path) throws IOException {
        log.info("所操纵文件所在路径={}", path);
        boolean reNameFile = renameFileSuffix(path, ".blv", ".flv");
        boolean deleteFileBySuffix = deleteFileBySuffix(new File(path), "sum");
        return "" + reNameFile + deleteFileBySuffix;
    }


    /**
     * 重命名
     *
     * @param dir       指定目录
     * @param suffix    旧的文件后缀（当前的）
     * @param newSuffix 新的文件后缀（修改后的）
     * @return
     * @throws FileNotFoundException
     */
    public static boolean renameFileSuffix(String dir, String suffix, String newSuffix) throws FileNotFoundException {
        File root = new File(dir);
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            if (null == files || files.length == 0) {
                //throw new FileNotFoundException("指定文件路径下不存在没有文件!路径为：" + dir);
                System.out.println("指定文件路径下不存在没有文件!路径为：" + dir);
            }
            //指定路径下有文件
            int success = 0;
            for (File file : files) {
                if (file.isDirectory()) {
                    //是文件夹，继续递归，如果需要重命名文件夹，这里可以做处理
                    System.out.println("文件夹:" + file.getAbsolutePath() + "，继续递归！");
                    renameFileSuffix(file.getPath(), suffix, newSuffix);
                } else {
                    String fileName = file.getName();
                    System.out.println("文件名称为：" + fileName);
                    //public String substring(int beginIndex)
                    //或
                    //public String substring(int beginIndex, int endIndex)
                    //参数    [beginIndex=0,endIndex)
                    // beginIndex -- 起始索引（包括）, 索引从 0 开始。
                    // endIndex -- 结束索引（不包括）。
                    System.out.println(fileName.length());
                    int endIndex = fileName.lastIndexOf(suffix);
                    System.out.println(endIndex);
                    if (endIndex > 0) {
                        String namePrefix = fileName.substring(0, endIndex);
                        String newFileName = namePrefix + newSuffix;
                        File newFile = new File(file.getParent() + File.separator + newFileName);//创建新名字的抽象文件
                        System.out.println("重命名后的文件名称为：" + newFileName);
                        System.out.println(newFile.getName());
                        if (file.renameTo(newFile)) {
                            success++;
                        } else {
                            System.out.println("重命名失败！新文件名已存在");
                        }
                    }
                    System.err.println("---------------------------------");
                }
            }
        } else {
            throw new FileNotFoundException("指定文件路径下不存在!路径为：" + dir);
        }
        return true;
    }

    /**
     * 在指定目录下，递归删除指定后缀的文件，
     *
     * @param root   目录
     * @param suffix 指定的后缀
     * @return
     * @throws FileNotFoundException
     */
    public static boolean deleteFileBySuffix(File root, String suffix) throws FileNotFoundException {
        //File root = new File(dir);
        if (!root.exists()) {
            throw new FileNotFoundException("指定文件路径不存在!路径为：" + root.getPath());
        } else {
            //是一个文件
            if (root.isFile()) {
                if (root.getName().endsWith(suffix)) {
                    if (root.delete()) {
                        System.out.println(root.getName() + "  删除成功！");
                    } else {
                        System.out.println(root.getName() + "  删除失败！");
                    }
                }
            } else {
                for (File file : root.listFiles()) {
                    deleteFileBySuffix(file, suffix);
                }
            }
        }
        return true;
    }


    /**
     * 重命名文件
     *
     * @param map
     * @return
     * @throws IOException
     */
    @PostMapping("/2")
    public static ResponseEntity rename2(@RequestBody Map<String, String> map) throws IOException {
        String path = map.get("path");
        String removedStr = map.get("removedStr");
        log.debug("所操纵文件所在路径={},需要删除的字符串={}", path, removedStr);

        //    “ [mqms2]”
        String remove = " [mqms2]";
        log.debug("==结果={}", remove == removedStr);
        log.debug("equals结果={}", remove.equals(removedStr));
        String result = "C:\\Users\\z\\Desktop\\新建文件夹";
        return ResponseEntity.ok(1);
    }


    /**
     * 分隔符
     */
    static final String segmentation = " - ";

    @PostMapping("/3")
    public static ResponseEntity rename3(@RequestBody Map<String, String> map) throws IOException {
        String dir = map.get("dir");
        String removedStr = map.get("removedStr");
        String singer = map.get("singer");
//        if (singer != null) {
//            singer = singer + segmentation;
//        }

        System.out.println("#####################################################");
        //    “ [mqms2]”
        String remove = " [mqms2]";
        log.debug("==结果={}", remove == removedStr);
        log.debug("equals结果={}", remove.equals(removedStr));

        log.debug("所操纵文件所在路径={},需要删除的字符串={}", dir, removedStr);
        String path = "C:\\Users\\z\\Desktop\\新建文件夹";
        log.debug("==结果={}", path == dir);
        log.debug("equals结果={}", path.equals(dir));

        System.out.println("#####################################################");


        List<File> files = FileUtil.listFileByDir(dir);
        for (File file : files) {
            boolean result = rename(file, map);
        }

        return ResponseEntity.ok(files.size());
    }

    //================================= 测试 =================================

    /**
     * 删除特殊编号
     * 例如：
     * 01 蝴蝶.wav
     * 02.青春碎语.wav
     * 03 网络情缘.wav
     * 04 谢幕.wav
     * 05 爱情啊.wav
     * to
     * 蝴蝶.wav
     * 青春碎语.wav
     * 网络情缘.wav
     * 谢幕.wav
     * 爱情啊.wav
     * <p>
     * // 1、读取文件【绝对路径+文件名称】
     * // 2、组装新名称 【歌手+分割+名称+后缀】
     * // 3、重命名，注意捕获异常
     * 或者在歌曲前面加上一个分割    【孙露 - 】
     *
     * @param file
     * @param map
     * @return
     */
    private static boolean rename(File file, Map<String, String> map) {
        String name = file.getName();

        for (String value : map.values()) {
            name = name.replaceAll(value, "");
            log.debug("name {} 去除  {}  后的name  {}", name, value, name);

        }

        //组装新文件名称
        String singer = map.get("singer");
        if (singer == null) {
            name = file.getParent() + File.separator + name;
        } else {
            name = file.getParent() + File.separator + singer + segmentation + name;
        }


        //重命名
        file.renameTo(new File(name));

        return true;
    }


    /**
     * 重命名
     *
     * @param dir       指定目录
     * @param suffix    旧的文件后缀（当前的）
     * @param newSuffix 新的文件后缀（修改后的）
     * @return
     * @throws FileNotFoundException
     */
    public static boolean renameFileName(String dir, String suffix, String newSuffix) throws FileNotFoundException {
        File root = new File(dir);
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            if (null == files || files.length == 0) {
                //throw new FileNotFoundException("指定文件路径下不存在没有文件!路径为：" + dir);
                System.out.println("指定文件路径下不存在没有文件!路径为：" + dir);
            }
            //指定路径下有文件
            int success = 0;
            for (File file : files) {
                if (file.isDirectory()) {
                    //是文件夹，继续递归，如果需要重命名文件夹，这里可以做处理
                    System.out.println("文件夹:" + file.getAbsolutePath() + "，继续递归！");
                    renameFileSuffix(file.getPath(), suffix, newSuffix);
                } else {
                    String fileName = file.getName();
                    System.out.println("文件名称为：" + fileName);
                    //public String substring(int beginIndex)
                    //或
                    //public String substring(int beginIndex, int endIndex)
                    //参数    [beginIndex=0,endIndex)
                    // beginIndex -- 起始索引（包括）, 索引从 0 开始。
                    // endIndex -- 结束索引（不包括）。
                    System.out.println(fileName.length());
                    int endIndex = fileName.lastIndexOf(suffix);
                    System.out.println(endIndex);
                    if (endIndex > 0) {
                        String namePrefix = fileName.substring(0, endIndex);
                        String newFileName = namePrefix + newSuffix;
                        File newFile = new File(file.getParent() + File.separator + newFileName);//创建新名字的抽象文件
                        System.out.println("重命名后的文件名称为：" + newFileName);
                        System.out.println(newFile.getName());
                        if (file.renameTo(newFile)) {
                            success++;
                        } else {
                            System.out.println("重命名失败！新文件名已存在");
                        }
                    }
                    System.err.println("---------------------------------");
                }
            }
        } else {
            throw new FileNotFoundException("指定文件路径下不存在!路径为：" + dir);
        }
        return true;
    }


    /**
     * the main method is used for test something.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //fun0();
        //fun1();
        //fun2();
        fun3();
    }

    // 获取指定目录下全部文件 后按照规则替换文件名称中的字符
    private static void fun3() {
        String dir = "T:\\music\\qq\\11\\";
        List<File> files = FileUtil.listFileByDir(dir);


        for (File file : files) {
            String name = file.getName();

            //是音乐格式的文件
            String toUpperCase = name.substring(name.lastIndexOf(".") + 1, name.length()).toUpperCase();
            if (FileUtil.getSoundSuffixes().contains(toUpperCase)) {
                HashMap<String, String> map = new HashMap<>();
//                map
                FileUtil.replaceFilenameByMap(file, map);
            }
        }
    }


    /**
     * 添加 替换参数  1
     *
     * @param map
     */
    public static void addReplaceSerialNumber(HashMap<String, String> map) {
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
        map.put("15", "");
        map.put("17", "");
        map.put("18", "");
        map.put("19", "");
        map.put("20", "");
    }

    private static void fun2() throws IOException {
        String dir = "C:\\Users\\z\\Desktop\\新建文件夹\\TEST\\";
        HashMap<String, String> map = new HashMap<>();
        map.put("dir", dir);
        map.put("removedStr", "mqms2");
        map.put("singer", "侃侃");
        rename3(map);
    }

    private static void fun1() {
        //recursiveTraversalFolder(dir);//递归遍历此路径下所有文件夹
        String path = "C:\\Users\\z\\Desktop\\新建文件夹\\侃侃 - 飘摇 [mqms2].flac";

        List<File> files = FileUtil.listFileByDir(new File(path));
        files.forEach(s -> {
            System.out.println(s);
        });
        System.out.println(files.size());
    }

    private static void fun0() {
        String path = "H:\\b\\download\\23890827\\2\\lua.flv720.bili2api.64";

        File parentFile = new File(path).getParentFile();
        System.out.println("name=" + parentFile.getName());
        System.out.println("父级路径=" + parentFile.getParent());//获取所在的文件夹的名字
        System.out.println(parentFile);
        System.out.println("getPath=" + parentFile.getPath());
        System.out.println("判断是否可读=" + parentFile.canRead());    //判断是否可读
        System.out.println("判断是否可写=" + parentFile.canWrite());    //判断是否可写
        System.out.println("获取文件的绝对路径，getAbsoluteFile返回File类对象=" + parentFile.getAbsoluteFile());//获取文件的绝对路径，getAbsoluteFile返回File类对象
        System.out.println("获取文件的绝对路径，getAbsolutePath返回代表路径的字符串=" + parentFile.getAbsolutePath()); //获取文件的绝对路径，getAbsolutePath返回代表路径的字符串

    }


}


//图片格式  http://www.kamilet.cn/image-file-formats/
//BMP格式：位图BMP是一种与硬件设备无关的图像文件格式，使用非常广。它采用位映射存储格式，除了图像深度可选以外，不采用其他任何压缩，因此，BMP文件所占用的空间很大。BMP文件的图像深度可选lbit、4bit、8bit及24bit。
//
//GIF格式：图形交换格式是CompuServe公司在 1987年开发的图像文件格式。GIF文件的数据，是一种基于LZW算法的连续色调的无损压缩格式。其压缩率一般在50%左右，它不属于任何应用程序。
//
//JPEG：也是最常见的一种图像格式，它是由联合照片专家组，文件后辍名为"．jpg"或"．jpeg"，是最常用的图像文件格式，是一种有损压缩格式，能够将图像压缩在很小的储存空间，图像中重复或不重要的资料会被丢失，因此容易造成图像数据的损伤。
//
//PNG格式：便携式网络图形，是网上接受的最新图像文件格式。PNG能够提供长度比GIF小30%的无损压缩图像文件。它同时提供 24位和48位真彩色图像支持以及其他诸多技术性支持。
