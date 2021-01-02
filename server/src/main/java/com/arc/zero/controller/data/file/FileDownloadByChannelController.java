package com.arc.zero.controller.data.file;

import com.arc.core.config.annotations.Note;
import com.arc.core.enums.system.ProjectCodeEnum;
import com.arc.core.model.domain.system.SysFile;
import com.arc.core.model.request.system.file.SysFileRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.utils.Assert;
import com.arc.zero.service.system.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
 * 包含业务操作的
 *
 * @author yechao
 * @date 2020/10/3 4:19 下午
 */
@Slf4j
@RestController
@RequestMapping({"/v3/file"})
public class FileDownloadByChannelController {

    /**
     *文件记录表相关服务
     */
    @Autowired
    private SysFileService fileService;

    /**
     * //http://192.168.2.103:8002/zero/v3/file?path"H:/bcdboot开机.txt"
     * 104.160.45.23:8000/zero/v3/file/download-test
     *
     * @param response HttpServletResponse
     */
    @GetMapping("/download-test")
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

    //---------

    @GetMapping("/id/{id}")
    public void fileDownloadById(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        SysFileRequest req = new SysFileRequest(id);
        fileDownload(req, response);
    }

    @GetMapping("/code/{code}")
    public void fileDownloadByCode(@PathVariable("code") String code, HttpServletResponse response) {
        SysFileRequest req = new SysFileRequest(code);
        fileDownload(req, response);
    }

    /**
     * 文件下载服务
     * @param req 请求参数目的是去查询一个文件
     * @param response 返回流
     */
    private void fileDownload(SysFileRequest req, HttpServletResponse response) {
        Assert.notNull(req);
        SysFile dbFile = fileService.getByRequest(req);
        Assert.notNull(dbFile);

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

    /**
     * 临时目录，注意你电脑上是否有该目录
     */
    @Value("${web.upload.file.path:/data/upload}")
    private String uploadDir;

    /**
     * 单文件上传
     * 记录日志
     * 判合法性，非空，大小，格式
     * 1、文件写入磁盘,注意文件不会被覆盖，因为不存在同名文件
     * 2、描述信息记录数据库
     *
     * @param file MultipartFile
     * @return flag=这里是返回文件在磁盘的路径，便于测试
     */
    //上传文件的问题  持久化操作出错，记录时候出错
    //正常情况：变量名称，文件大小，文件格式，文件名称重复时候处理，文件在磁盘上的路径处理，文件路径记录到数据库，生成唯一编号
    //异常情况：文件为空判断，路径为空或者不合法判断，不支持的文件类型判断，文件重复判断，写入磁盘异常判断，写入磁盘成功，但是信息记录数据库出错，造成数据耗着磁盘，但是应用检索数据库时候并无法发现该文件， 需要做补偿来删除脏数据
    @PostMapping("/upload")
    @Note("单个文件上传")
    public ResponseEntity singleFileUpload(MultipartFile file) {
        //需求判断文件是否为空 大小已经在yml中做了配置
        if (file == null || file.isEmpty()) {
            log.info(ProjectCodeEnum.UPLOAD_FAILURE.getMsg());
            return ResponseEntity.ok(ResponseVo.success(ProjectCodeEnum.UPLOAD_FAILURE));
        }
        log.debug("文件上传入参: 类型={}，名称={}，尺寸={} bytes", file.getContentType(), file.getOriginalFilename(), file.getSize());
        String flag = fileService.writeFileToDiskAndRecord(file, uploadDir);
        return flag == null ? ResponseEntity.status(500).body(ResponseVo.failure(ProjectCodeEnum.UPLOAD_FAILURE)) : ResponseEntity.ok(ResponseVo.success(flag));
    }


    public static void main(String[] args) throws IOException {
        String fileUri = ".";
        String filename = "test.txt";
        InputStream in = new URL(fileUri).openStream();
        long copy = Files.copy(in, Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
        System.out.println(copy);
    }

}
