package com.arc.zero.controller.data.file;

import com.arc.core.config.annotations.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 计划采用新的写法实现高效率文件下载
 * NIO?零拷贝下载文件
 *
 * @author yechao
 * @date 2020/10/3 4:19 下午
 */
@Slf4j
@RestController
@RequestMapping({"/v3/file"})
public class FileDownloadByChannelController {

    /**
     * //http://192.168.2.103:8002/zero/v2/files?path"H:/bcdboot开机.txt"
     *
     * @param response HttpServletResponse
     */
    @GetMapping("/download-test")
    @Note("v2-文件下载-测试")
    public static void downloadFileTest(HttpServletResponse response) {
        OutputStream os = null;
        try {
            //File sourceFile = ResourceUtils.getFile("classpath:static/image/IMG_1.png");
            //InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("excleTemplate/test.xlsx");
            File sourceFile = new File("./IMG_1.png");
            // 取得输出流
            os = response.getOutputStream();
            String contentType = Files.probeContentType(Paths.get(sourceFile.getAbsolutePath()));
            response.setHeader("Content-Type", contentType);
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(sourceFile.getName().getBytes("utf-8"), "ISO8859-1"));
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            WritableByteChannel writableByteChannel = Channels.newChannel(os);
            FileChannel fileChannel = fileInputStream.getChannel();
            fileChannel.transferTo(0, fileChannel.size(), writableByteChannel);
            fileChannel.close();
            os.flush();
            writableByteChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //文件的关闭放在finally中
        finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //---------

    @GetMapping("/id/{id}")
    public void fileDownloadById(@PathVariable("id") String id, HttpServletResponse response) throws Exception {
        // fileDownloadByIdOrCode(id, response);
    }

    @GetMapping("/code/{code}")
    public void fileDownloadByCode(@PathVariable("code") String code, HttpServletResponse response) {
        //fileDownloadByIdOrCode(code, response);
    }


    /**
     * //http://192.168.2.103:8002/zero/v2/files?path"H:/bcdboot开机.txt"
     *
     * @param response HttpServletResponse
     */
    @GetMapping("/download")
    @Note("文件下载v2")
    public static void downloadFile(HttpServletResponse response, @RequestParam String path) {
        OutputStream os = null;
        try {
            File sourceFile = new File(path);
            // 取得输出流
            os = response.getOutputStream();
            String contentType = Files.probeContentType(Paths.get(sourceFile.getAbsolutePath()));
            response.setHeader("Content-Type", contentType);
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(sourceFile.getName().getBytes("utf-8"), "ISO8859-1"));
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            WritableByteChannel writableByteChannel = Channels.newChannel(os);
            FileChannel fileChannel = fileInputStream.getChannel();
            fileChannel.transferTo(0, fileChannel.size(), writableByteChannel);
            fileChannel.close();
            os.flush();
            writableByteChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //文件的关闭放在finally中
        finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//
//
//    public void fileDownload2(HttpServletResponse response) throws Exception {
//        //文件所在路径
//        //读取文件并返回
//        String path = "H:\\REMOVE\\test.txt";
//        System.out.println("path=" + path);
//
//        String separator = File.separator;
//        System.out.println("路径分隔符=" + separator);
//
//        //本地文件
//        File localFile = new File(path);
//
//        InputStream inputStream = null;
//        inputStream = new FileInputStream(localFile);
//
//        FileInputStream sourceFile = new FileInputStream(path);
//        FileChannel fcin = sourceFile.getChannel();
//
//        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 50);
//
//        while (true) {
//            buffer.clear();
//            int flag = fcin.read(buffer);
//            if (flag == -1) {
//                break;
//            }
//            buffer.flip();
//            // FileOutputStream fout = new FileOutputStream("d:\\temp\\" + Math.random() + ".log");
//            OutputStream outputStream = response.getOutputStream();){
//
////                FileChannel fcout = outputStream.getChannel();
////                fcout.write(buffer);
//            }
//
//
//            response.setContentType("application/octet-stream");
//            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(localFile.getName(), "UTF-8"));
//            FileUploadDownloadController.copy(inputStream, outputStream);
//            outputStream.flush();
//
//            //            throw new BizException(ProjectCodeEnum.FAILURE);
//        }
//    }


//public final Buffer clear()
// 2    {
// 3        position = 0;
// 4        limit = capacity;
// 5        mark = -1;
// 6        return this;
// 7    }
// 8
// 9 public final Buffer flip()
//10    {
//11        limit = position;
//12        position = 0;
//13        mark = -1;
//14        return this;
//15    }

    public static void main(String[] args) throws IOException {
        String fileUri = ".";
        String filename = "test.txt";
        InputStream in = new URL(fileUri).openStream();
        long copy = Files.copy(in, Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
        System.out.println(copy);
    }

}
