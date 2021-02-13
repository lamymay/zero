package com.arc.zero.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;

/**
 * 下载文件
 *
 * @author 叶超
 * @since 2019/2/25 17:07
 */
@Deprecated
@Slf4j
public class DownloadUtil {

    /**
     * 文件下载相关代码
     * //     * @param fileWithPath 文件路径以及名称
     * 从本地文件系统下载文件
     *
     * @param file     本地文件系统的文件
     * @param response HttpServletResponse
     * @return
     */
    public void downloadFile(File file, HttpServletResponse response) {
        if (file == null || response == null) {
            log.info("发生错误文件未完成下载，原因 file == null || response == null，时间={}", LocalDate.now());
            return;
        }
        if (!file.exists()) {
            log.info("所要下载的本地文件系统的文件不存在，时间={}", LocalDate.now());
            return;
        }

        String fileName = null;
        InputStream inputStream = null;
        try {
            fileName = file.getName();
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        downloadFile(fileName, inputStream, response);
    }


    /**
     * 从输入流中下载文件
     *
     * @param filename
     * @param inputStream
     * @param response
     */
    public void downloadFile(String filename, InputStream inputStream, HttpServletResponse response) {
        if (inputStream == null || response == null) {
            log.info("发生错误文件未完成下载，原因 inputStream == null || response == null，时间={}", LocalDate.now());
            return;
        }
        //filename
        try {
            filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("文件名称获取或者二次编码出错，下载将继续进行，只是文件名称乱码={}", e);
        }

        response.reset();
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + filename);// 设置文件名

        //输出
        byte[] buffer = new byte[1024];
        try (OutputStream outputStream = response.getOutputStream();
             InputStream targetInputStream = inputStream;
             BufferedInputStream bufferedInputStream = new BufferedInputStream(targetInputStream);) {
            int i = bufferedInputStream.read(buffer);
            while (i != -1) {
                outputStream.write(buffer, 0, i);
                i = bufferedInputStream.read(buffer);
            }
            log.debug("下载成功，时间={}", LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 匹配 不同浏览器Header的userAgent，返回重新编码后的文件名称
     *
     * @param fileName  文件名称
     * @param userAgent 浏览器Header
     * @return 新编码后的文件名称
     */
    public static String getFileNameByUserAgent(String fileName, String userAgent) {
        if (userAgent != null) {
            try {
                //匹配userAgent，使用对应的编码格式
                String fireFox = "FireFox";
                String chromeOrSafari = "AppleWebKit";
                String edge = "MSIE";
                String trident = "Trident";
                String ie11 = "rv:11";
                String ie10 = "rv:10";
                String others = "Unknown";
                if (userAgent.indexOf(fireFox) > 0) {
                    log.debug("浏览器名称={}", fireFox);
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                } else if (userAgent.indexOf(chromeOrSafari) > 0) {
                    log.debug("浏览器名称={}", chromeOrSafari);
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                } else if (userAgent.indexOf(edge) > 0) {
                    log.debug("浏览器名称={}", edge);
                    //fileName = URLEncoder.encode(fileName, "UTF-8");
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                } else if (userAgent.indexOf(ie11) > 0 && userAgent.indexOf(trident) > 0) {
                    log.debug("浏览器名称={}", ie11);
                }
//                else if (userAgent.indexOf(ie10) > 0 && userAgent.indexOf(trident) > 0) {
//                    log.debug("浏览器名称={}", ie10);
//                } else if (userAgent.indexOf(trident) > 0 && userAgent.indexOf(edge) > 0) {
//                    log.debug("浏览器名称={}", "ie");
//                } else if (userAgent.indexOf(trident) > 0) {
//                    log.debug("浏览器名称={}", trident);
//                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
//                } else if (userAgent.indexOf(ie11) > 0) {
//                    log.debug("浏览器名称={}", ie11);
//                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
//                }
                else {
                    //其他
                    log.debug("浏览器名称={}", others);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                log.error(".getBytes() error cause by {}", e);
            }
        }
        return fileName;
    }

    //IE7               Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko
    //Opera         Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36 OPR/57.0.3098.116
    // Chrome       Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36
    //FireFox         Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0

}
