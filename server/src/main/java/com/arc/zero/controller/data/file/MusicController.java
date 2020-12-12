package com.arc.zero.controller.data.file;

import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.config.properties.file.FileProperties;
import com.arc.zero.service.file.MusicFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yechao
 * @date 2020/8/26 6:05 下午
 */
@Slf4j
@Controller
@RequestMapping({"music"})
public class MusicController {

    /**
     * 文件持久化等相关参数配置
     */
    @Resource
    private FileProperties fileProperties;


    @Resource
    private MusicFileService musicFileService;


    //####################################### 文件上传 & 下载 ###################################

//    /**
//     * 单文件上传
//     * 记录日志
//     * 判合法性，非空，大小，格式
//     * 1、文件写入磁盘,注意文件不会被覆盖，因为不存在同名文件
//     * 2、描述信息记录数据库
//     *
//     * @param file MultipartFile
//     * @return flag=这里是返回文件在磁盘的路径，便于测试
//     */
//    //上传文件的问题  持久化操作出错，记录时候出错
//    //正常情况：变量名称，文件大小，文件格式，文件名称重复时候处理，文件在磁盘上的路径处理，文件路径记录到数据库，生成唯一编号
//    //异常情况：文件为空判断，路径为空或者不合法判断，不支持的文件类型判断，文件重复判断，写入磁盘异常判断，写入磁盘成功，但是信息记录数据库出错，造成数据耗着磁盘，但是应用检索数据库时候并无法发现该文件， 需要做补偿来删除脏数据
//    @PostMapping("/upload")
//    @Note("单个文件上传")
//    public ResponseEntity singleFileUpload(MultipartFile file) {
//        //需求判断文件是否为空 大小已经在yml中做了配置
//        if (file == null || file.isEmpty()) {
//            log.info(ProjectCodeEnum.UPLOAD_FAILURE.getMsg());
//            return ResponseEntity.ok(ResponseVo.success(ProjectCodeEnum.UPLOAD_FAILURE));
//        }
//        log.debug("文件上传入参: 类型={}，名称={}，尺寸={} bytes", file.getContentType(), file.getOriginalFilename(), file.getSize());
//        String flag = fileService.writeFileToDiskAndRecord(file, fileProperties.getFilePersistenceDirectory());
//        return flag == null ? ResponseEntity.status(500).body(ResponseVo.failure(ProjectCodeEnum.UPLOAD_FAILURE)) : ResponseEntity.ok(ResponseVo.success(flag));
//    }


    //todo 测试两种方法是否有效
    @RequestMapping(value = "/delete/{code}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ResponseVo deleteByIdOrCode(@PathVariable String code) {
        return ResponseVo.success(musicFileService.deleteByCode(code));
    }


//    @GetMapping("/rename/change/profile")
//    public ResponseEntity rename() {
//        List<SysFile> list = sysFileService.list();
//        return ResponseEntity.ok(list);
//    }

    @PostMapping("/init")
    public ResponseEntity init(@RequestBody Map<String, Object> map) {
        //文件扫描后入库 返回成功的数量
        Integer success = musicFileService.init(map);
        return ResponseEntity.ok(success);
    }


    @PostMapping("/init/2")
    public ResponseEntity init2(String path) {
        //文件扫描后入库 返回成功的数量
        HashMap<String, Object> map = new HashMap<>();
        map.put("path", path);
        Integer success = musicFileService.init(map);
        return ResponseEntity.ok(success);
    }
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

}
