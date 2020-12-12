package com.arc.zero.controller.data.file;

import com.arc.core.config.annotations.Note;
import com.arc.core.enums.system.ProjectCodeEnum;
import com.arc.core.exception.BizException;
import com.arc.core.model.domain.system.SysFile;
import com.arc.core.model.vo.ResponseVo;
import com.arc.utils.file.FileUtil;
import com.arc.zero.service.system.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

//import org.apache.commons.compress.utils.IOUtils;

/**
 * 文件下载测试API
 *
 * @author yechao
 * @since 0.0.1
 */
@Slf4j
@RestController
@Deprecated
@RequestMapping({"file"})
public class FileUploadDownloadController {

    @Resource
    private SysFileService fileService;

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

    //-------------------------------------------------------------------------------------------------------------------------------
    //                                                                              下载文件/预览图片
    //-------------------------------------------------------------------------------------------------------------------------------

    /**
     * 文件下载，或者说叫预览，总之就是文件传给用户
     *
     * @param code     code
     * @param response HttpServletResponse
     */
    @GetMapping("/{code}")
    @Note("文件下载")
    public void fileDownload(@PathVariable("code") String code, HttpServletResponse response) {
        //根据文件id信息检索文件条目
        //获得文件所在路径
        //读取文件并返回
        //获取文件在数据库中记录的信息条目
        SysFile sysFile = fileService.getByCode(code);
        if (sysFile == null) {
            return;
        }

        try (InputStream inputStream = new FileInputStream(new File(sysFile.getPath()));
             OutputStream outputStream = response.getOutputStream()) {
            if (FileUtil.isImage(sysFile.getSuffix())) {
                response.setContentType("image/jpg");
            } else {
                response.setContentType("application/octet-stream");
                response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(sysFile.getName(), "UTF-8"));
            }
            copy(inputStream, outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(ProjectCodeEnum.FAILURE);
        }
    }


    /**
     * 文件下载，或者说叫预览，总之就是文件传给用户
     *
     * @param id       id
     * @param response HttpServletResponse
     */
    @GetMapping("/id/{id}")
    @Note("文件下载")
    public void fileDownloadById(@PathVariable("id") Long id, HttpServletResponse response) {
        //根据文件id信息检索文件条目
        //获得文件所在路径
        //读取文件并返回
        //获取文件在数据库中记录的信息条目
        SysFile sysFile = fileService.get(id);
        if (sysFile == null) {
            return;
        }
        try (InputStream inputStream = new FileInputStream(new File(sysFile.getPath()));
             OutputStream outputStream = response.getOutputStream()) {
            if (FileUtil.isImage(sysFile.getSuffix())) {
                response.setContentType("image/jpg");
            } else {
                response.setContentType("application/octet-stream");
                response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(sysFile.getName(), "UTF-8"));
            }
            copy(inputStream, outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(ProjectCodeEnum.FAILURE);
        }
    }


    public static long copy(InputStream input, OutputStream output) throws IOException {
        return copy(input, output, 8024);
    }

    public static long copy(InputStream input, OutputStream output, int buffersize) throws IOException {
        if (buffersize < 1) {
            throw new IllegalArgumentException("buffersize must be bigger than 0");
        } else {
            byte[] buffer = new byte[buffersize];
            //int n = false;
            long count;
            int n;
            for (count = 0L; -1 != (n = input.read(buffer)); count += n) {
                output.write(buffer, 0, n);
            }
            return count;
        }
    }

}
