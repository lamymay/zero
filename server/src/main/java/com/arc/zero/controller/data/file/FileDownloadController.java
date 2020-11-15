//package com.arc.zero.controller.data.file;
//
//import com.arc.core.config.annotations.Note;
//import com.arc.core.enums.system.ProjectCodeEnum;
//import com.arc.core.exception.BizException;
//import com.arc.core.model.domain.system.SysFile;
//import com.arc.utils.file.FileUtil;
//import com.arc.zero.service.system.SysFileService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.net.URL;
//import java.net.URLEncoder;
//
///**
// * @author May
// * @since 2020/4/3 10:26
// */
//@Slf4j
//@RequestMapping({"/files"})
//@RestController
//public class FileDownloadController {
//
//
//    /**
//     * 文件记录操作
//     */
//    @Resource
//    private SysFileService sysFileService;
//
//    //#######################################   下载文件/预览图片 ###################################
//
//    /**
//     * 文件下载，或者说叫预览，总之就是文件传给用户
//     *
//     * @param code     接受 code / id
//     * @param response
//     */
//    @GetMapping("/{code}")
//    @Note("文件下载")
//    public void fileDownloadByIdOrCode(@PathVariable("code") String code, HttpServletResponse response) {
//        //根据文件id信息检索文件条目
//        //获得文件所在路径
//        //读取文件并返回
//        //获取文件在数据库中记录的信息条目
//        log.debug("文件下载，参数接受 code / id={}", code);
//        Long id = null;
//        SysFile sysFile = null;
//        try {
//            id = Long.valueOf(code);
//            sysFile = sysFileService.get(id);
//        } catch (Exception e) {
//            log.error("文件下载中，code 转换为 id error=", e);
//        }
//
//        if (sysFile == null) {
//            // 尝试用 code去精确匹配
//            sysFile = sysFileService.getByCode(code);
//        }
//        if (sysFile == null) return;
//
//        //下载文件最基本的方法是java IO，使用URL类打开待下载文件的连接。为有效读取文件，我们使用openStream() 方法获取 InputStream:
//        //BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream())
//        //当从InputStream读取文件时，强烈建议使用BufferedInputStream去包装InputStream，用于提升性能。
//        //使用缓存可以提升性能。read方法每次读一个字节，每次方法调用意味着系统调用底层的文件系统。当JVM调用read()方法时，程序执行上下文将从用户模式切换到内核模式并返回。
//        //
//        //从性能的角度来看，这种上下文切换非常昂贵。当我们读取大量字节时，由于涉及大量上下文切换，应用程序性能将会很差。
//
//        try (InputStream inputStream = new BufferedInputStream(new URL(sysFile.getPath()).openStream());
//             OutputStream outputStream = response.getOutputStream();) {
//
//            String fileName = URLEncoder.encode(sysFile.getName(), "UTF-8");
//            if (FileUtil.isImage(sysFile.getSuffix())) {
//                response.setContentType("image/jpg");
//                response.setHeader("content-disposition", "attachment;filename=" + fileName);
//                response.setHeader("filename", fileName);
//                response.setHeader("Access-Control-Expose-Headers ", "Content-Disposition,filename");
//
//            } else {
//                response.setContentType("application/octet-stream");
//                response.setHeader("content-disposition", "attachment;filename=" + fileName);
//                response.setHeader("filename", fileName);
//                response.setHeader("Access-Control-Expose-Headers ", "Content-Disposition,filename");
//            }
//            copy(inputStream, outputStream);
//            outputStream.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new BizException(ProjectCodeEnum.FAILURE);
//        }
//    }
//
//    /**
//     * @param input
//     * @param output
//     * @return
//     * @throws IOException
//     */
//    public static long copy(InputStream input, OutputStream output) throws IOException {
//        return copy(input, output, 8024);
//    }
//
//    /**
//     * @param input
//     * @param output
//     * @param buffersize
//     * @return
//     * @throws IOException
//     */
//    public static long copy(InputStream input, OutputStream output, int buffersize) throws IOException {
//        if (buffersize < 1) {
//            throw new IllegalArgumentException("buffersize must be bigger than 0");
//        } else {
//            byte[] buffer = new byte[buffersize];
//            //int n = false;
//            long count;
//            int n;
//            for (count = 0L; -1 != (n = input.read(buffer)); count += (long) n) {
//                output.write(buffer, 0, n);
//            }
//            return count;
//        }
//    }
//}
