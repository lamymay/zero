//package com.arc.zero.test.file;
//
//import com.sun.net.httpserver.HttpContext;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.util.concurrent.CountDownLatch;
//
///**
// * @author yechao
// * @date 2020/10/12 7:35 下午
// */
//@Slf4j
//public class DownloadThreadTest extends Thread {
//
//
//    /**
//     * 待下载的文件
//     */
//
//    private String url = null;
//
//    /**
//     * 本地文件名
//     */
//
//    private String fileName = null;
//
//    /**
//     * 偏移量
//     */
//
//    private long offset = 0;
//
//    /**
//     * 分配给本线程的下载字节数
//     */
//
//    private long length = 0;
//
//    private CountDownLatch end;
//
//    private HttpContext context;
//
//    /**
//     * @param url      下载文件地址
//     * @param fileName 另存文件名
//     * @param offset   本线程下载偏移量
//     * @param length   本线程下载长度
//     * @author Angus.wang
//     */
//
//    public DownloadThreadTest(String url, String fileName, long offset, long length, CountDownLatch end, CloseableHttpClient httpClient) {
//        this.url = url;
//        this.fileName = file;
//        this.offset = offset;
//        this.length = length;
//        this.end = end;
//        this.httpClient = httpClient;
//        this.context = new BasicHttpContext();
//        log.debug("偏移量=" + offset + ";字节数=" + length);
//    }
//
//    @Override
//    public void run() {
//        try {
//            HttpGet httpGet = new HttpGet(this.url);
//            httpGet.addHeader("Range", "bytes=" + this.offset + "-" + (this.offset + this.length - 1));
//            CloseableHttpResponse response = httpClient.execute(httpGet, context);
//            BufferedInputStream bis = new BufferedInputStream(response.getEntity().getContent());
//            byte[] buff = new byte[1024];
//            int bytesRead;
//            File newFile = new File(fileName);
//            RandomAccessFile raf = new RandomAccessFile(newFile, "rw");
//            while ((bytesRead = bis.read(buff, 0, buff.length)) != -1) {
//                raf.seek(this.offset);
//                raf.write(buff, 0, bytesRead);
//                this.offset = this.offset + bytesRead;
//            }
//            raf.close();
//            bis.close();
//        } catch (IOException e) {
//            log.error("DownloadThread exception msg:{}", e);
//        } catch (Exception e) {
//            log.error("DownloadThread exception msg:{}", e);
//        } finally {
//            end.countDown();
//            log.info(end.getCount() + " is go on!");
//            System.out.println(end.getCount() + " is go on!");on
//        }
//    }
//
//}